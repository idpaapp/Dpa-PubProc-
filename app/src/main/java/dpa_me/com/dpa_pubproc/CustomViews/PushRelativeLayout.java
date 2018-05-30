package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

import static dpa_me.com.dpa_pubproc.Units.PubProc.mLastClickTime;

public class PushRelativeLayout extends RelativeLayout {
    private float xOffset;
    private float yOffset;
    private float xPivote;
    private float yPivote;

    public PushRelativeLayout(Context context) {
        super(context);
    }

    public PushRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PushRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PushRelativeLayout);
            xOffset = attributes.getFloat(R.styleable.PushRelativeLayout_plxOffset, 1f);
            yOffset = attributes.getFloat(R.styleable.PushRelativeLayout_plyOffset, 1f);
            xPivote = attributes.getFloat(R.styleable.PushRelativeLayout_plxPivote, 0.5f);
            yPivote = attributes.getFloat(R.styleable.PushRelativeLayout_plyPivote, 0.5f);
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Animation anim;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        anim = new ScaleAnimation(
                                1f, xOffset,
                                1f, yOffset,
                                Animation.RELATIVE_TO_SELF, xPivote,
                                Animation.RELATIVE_TO_SELF, yPivote);
                        anim.setFillAfter(true);
                        anim.setDuration(300);
                        v.startAnimation(anim);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // touch move code
                        break;

                    case MotionEvent.ACTION_UP:
                        anim = new ScaleAnimation(
                                xOffset, 1f,
                                yOffset, 1f,
                                Animation.RELATIVE_TO_SELF, xPivote,
                                Animation.RELATIVE_TO_SELF, yPivote);
                        anim.setFillAfter(true);
                        anim.setDuration(300);
                        v.startAnimation(anim);
                        try {
                            if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                                callOnClick();
                                mLastClickTime = SystemClock.elapsedRealtime();
                            }
                        }catch (Exception ex) {}
                        PubProc.HandleSounds.playSound(PubProc.mContext, R.raw.click, PubProc.HandleSounds.SoundType.SOUND);
                        break;
                    default:
                        anim = new ScaleAnimation(
                                xOffset, 1f,
                                yOffset, 1f,
                                Animation.RELATIVE_TO_SELF, xPivote,
                                Animation.RELATIVE_TO_SELF, yPivote);
                        anim.setFillAfter(true);
                        anim.setDuration(300);
                        v.startAnimation(anim);
                        break;
                }
                return true;
            }
        });
    }

}
