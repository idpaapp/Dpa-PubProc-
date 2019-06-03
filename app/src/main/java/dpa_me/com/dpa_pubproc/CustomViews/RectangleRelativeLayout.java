package dpa_me.com.dpa_pubproc.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Justice on 7/19/2017.
 */

public class RectangleRelativeLayout extends FrameLayout {

    public RectangleRelativeLayout(Context context) {
        super(context);
    }

    public RectangleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RectangleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, ((widthMeasureSpec/5)*3));
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, ((size/5)*3));
    }

}