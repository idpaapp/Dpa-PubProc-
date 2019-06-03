package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.util.AttributeSet;

public class RectangleImageView extends android.support.v7.widget.AppCompatImageView {

    public RectangleImageView(Context context) {
        super(context);
    }

    public RectangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectangleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, ((widthMeasureSpec/5)*3));
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, ((size/5)*3));
    }
}
