package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class LDObservableScrollView extends ScrollView {

    private LDObservableScrollViewListener scrollViewListener = null;

    public interface LDObservableScrollViewListener {

        void onScrollChanged(LDObservableScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    public LDObservableScrollView(Context context) {
        super(context);
    }

    public LDObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LDObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(LDObservableScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}