package dpa_me.com.dpa_pubproc.Units;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Field;

public abstract class ClickGuard {

    public static final long DEFAULT_WATCH_PERIOD_MILLIS = 1000L;

    private ClickGuard() {
    }

    public static ClickGuard newGuard() {
        return newGuard(DEFAULT_WATCH_PERIOD_MILLIS);
    }

    public static ClickGuard newGuard(long watchPeriodMillis) {
        return new ClickGuardImpl(watchPeriodMillis);
    }

    public static GuardedOnClickListener wrap(OnClickListener onClickListener) {
        return wrap(newGuard(), onClickListener);
    }

    public static GuardedOnClickListener wrap(long watchPeriodMillis, OnClickListener onClickListener) {
        return newGuard(watchPeriodMillis).wrapOnClickListener(onClickListener);
    }

    public static GuardedOnClickListener wrap(ClickGuard guard, OnClickListener onClickListener) {
        return guard.wrapOnClickListener(onClickListener);
    }

    public static ClickGuard guard(View view, View... others) {
        return guard(DEFAULT_WATCH_PERIOD_MILLIS, view, others);
    }

    public static ClickGuard guard(long watchPeriodMillis, View view, View... others) {
        return guard(newGuard(watchPeriodMillis), view, others);
    }

    public static ClickGuard guard(ClickGuard guard, View view, View... others) {
        return guard.addAll(view, others);
    }

    public static ClickGuard guardAll(Iterable<View> views) {
        return guardAll(DEFAULT_WATCH_PERIOD_MILLIS, views);
    }

    public static ClickGuard guardAll(long watchPeriodMillis, Iterable<View> views) {
        return guardAll(newGuard(watchPeriodMillis), views);
    }

    public static ClickGuard guardAll(ClickGuard guard, Iterable<View> views) {
        return guard.addAll(views);
    }

    public static ClickGuard get(View view) {
        OnClickListener listener = retrieveOnClickListener(view);
        if (listener instanceof GuardedOnClickListener) {
            return ((GuardedOnClickListener) listener).getClickGuard();
        }
        throw new IllegalStateException("The view (id: 0x" + view.getId() + ") isn't guarded by ClickGuard!");
    }

    public static OnClickListener retrieveOnClickListener(View view) {
        if (view == null) {
            throw new NullPointerException("Given view is null!");
        }
        return ListenerGetter.get(view);
    }

    public ClickGuard add(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View shouldn't be null!");
        }
        OnClickListener listener = retrieveOnClickListener(view);
        if (listener == null) {
            throw new IllegalStateException("Haven't set an OnClickListener to View (id: 0x"
                    + Integer.toHexString(view.getId()) + ")!");
        }
        view.setOnClickListener(wrapOnClickListener(listener));
        return this;
    }

    public ClickGuard addAll(View view, View... others) {
        add(view);
        for (View v : others) {
            add(v);
        }
        return this;
    }

    public ClickGuard addAll(Iterable<View> views) {
        for (View v : views) {
            add(v);
        }
        return this;
    }

    public GuardedOnClickListener wrapOnClickListener(OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("onClickListener shouldn't be null!");
        }
        if (onClickListener instanceof GuardedOnClickListener) {
            throw new IllegalArgumentException("Can't wrap GuardedOnClickListener!");
        }
        return new InnerGuardedOnClickListener(onClickListener, this);
    }

    public abstract void watch();

    public abstract void rest();

    public abstract boolean isWatching();

    private static class ClickGuardImpl extends ClickGuard {
        private static final int WATCHING = 0;
        private final Handler mHandler = new Handler(Looper.getMainLooper());
        private final long mWatchPeriodMillis;

        ClickGuardImpl(long watchPeriodMillis) {
            mWatchPeriodMillis = watchPeriodMillis;
        }

        @Override
        public void watch() {
            mHandler.sendEmptyMessageDelayed(WATCHING, mWatchPeriodMillis);
        }

        @Override
        public void rest() {
            mHandler.removeMessages(WATCHING);
        }

        @Override
        public boolean isWatching() {
            return mHandler.hasMessages(WATCHING);
        }
    }

    public static abstract class GuardedOnClickListener implements OnClickListener {
        private ClickGuard mGuard;
        private OnClickListener mWrapped;

        public GuardedOnClickListener() {
            this(DEFAULT_WATCH_PERIOD_MILLIS);
        }

        public GuardedOnClickListener(long watchPeriodMillis) {
            this(newGuard(watchPeriodMillis));
        }

        public GuardedOnClickListener(ClickGuard guard) {
            this(null, guard);
        }

        GuardedOnClickListener(OnClickListener onClickListener, ClickGuard guard) {
            mGuard = guard;
            mWrapped = onClickListener;
        }

        @Override
        final public void onClick(View v) {
            if (mGuard.isWatching()) {
                // Guard is guarding, can't do anything.
                onIgnored();
                return;
            }
            // Guard is relaxing. Run!
            if (mWrapped != null) {
                mWrapped.onClick(v);
            }
            if (onClicked()) {
                // Guard becomes vigilant.
                mGuard.watch();
            }
        }

        public abstract boolean onClicked();

        /**
         * Called when a click is ignored.
         */
        public void onIgnored() {
        }

        public ClickGuard getClickGuard() {
            return mGuard;
        }
    }

    // Inner GuardedOnClickListener implementation.
    static class InnerGuardedOnClickListener extends GuardedOnClickListener {
        InnerGuardedOnClickListener(OnClickListener onClickListener, ClickGuard guard) {
            super(onClickListener, guard);
        }

        public boolean onClicked() {
            return true;
        }

        public void onIgnored() {
        }
    }

    /**
     * Class used for retrieve OnClickListener from a View.
     */
    static abstract class ListenerGetter {

        private static ListenerGetter IMPL;

        static {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                IMPL = new ListenerGetterIcs();
            } else {
                IMPL = new ListenerGetterBase();
            }
        }

        static OnClickListener get(View view) {
            return IMPL.getOnClickListener(view);
        }

        static Field getField(Class clazz, String fieldName) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                throw new RuntimeException("Can't get " + fieldName + " of " + clazz.getName());
            }
        }

        static Field getField(String className, String fieldName) {
            try {
                return getField(Class.forName(className), fieldName);
            } catch (ClassNotFoundException ignored) {
                throw new RuntimeException("Can't find class: " + className);
            }
        }

        static Object getFieldValue(Field field, Object object) {
            try {
                return field.get(object);
            } catch (IllegalAccessException ignored) {
            }
            return null;
        }

        abstract OnClickListener getOnClickListener(View view);

        private static class ListenerGetterBase extends ListenerGetter {
            private Field mOnClickListenerField;

            ListenerGetterBase() {
                mOnClickListenerField = getField(View.class, "mOnClickListener");
            }

            @Override
            public OnClickListener getOnClickListener(View view) {
                return (OnClickListener) getFieldValue(mOnClickListenerField, view);
            }
        }

        private static class ListenerGetterIcs extends ListenerGetter {
            private Field mListenerInfoField;
            private Field mOnClickListenerField;

            ListenerGetterIcs() {
                mListenerInfoField = getField(View.class, "mListenerInfo");
                mListenerInfoField.setAccessible(true);
                mOnClickListenerField = getField("android.view.View$ListenerInfo", "mOnClickListener");
            }

            @Override
            public OnClickListener getOnClickListener(View view) {
                Object listenerInfo = getFieldValue(mListenerInfoField, view);
                return listenerInfo != null ?
                        (OnClickListener) getFieldValue(mOnClickListenerField, listenerInfo) : null;
            }
        }
    }
}