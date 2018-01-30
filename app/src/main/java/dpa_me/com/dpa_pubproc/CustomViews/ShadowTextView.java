package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;

public class ShadowTextView extends FrameLayout {
    TextView textView_shadow;
    TextView textView;

    public ShadowTextView(Context context) {
        super(context);
        init(null);
    }

    public ShadowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShadowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final View view = inflate(getContext(), R.layout.shadow_textview, null);
        textView = view.findViewById(R.id.textView);
        textView_shadow = view.findViewById(R.id.textView_shadow);

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowTextView);
            textView.setText(attributes.getString(R.styleable.ShadowTextView_stxtcaption));
            textView_shadow.setText(attributes.getString(R.styleable.ShadowTextView_stxtcaption));

            textView.setTextColor(attributes.getColor(R.styleable.ShadowTextView_stxttextColor,
                    getResources().getColor(R.color.primary_text)));

            textView_shadow.setTextColor(attributes.getColor(R.styleable.ShadowTextView_stxtshadowColor,
                    getResources().getColor(R.color.divider)));

            textView.setTextSize(attributes.getDimensionPixelSize(R.styleable.ShadowTextView_stxttextSize, 14));
            textView_shadow.setTextSize(attributes.getDimensionPixelSize(R.styleable.ShadowTextView_stxttextSize, 14));

            int leftMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    attributes.getFloat(R.styleable.ShadowTextView_shadowDx, 0.5f),
                    getResources().getDisplayMetrics());

            int topMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    attributes.getFloat(R.styleable.ShadowTextView_shadowDy, 0.5f),
                    getResources().getDisplayMetrics());

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView_shadow.getLayoutParams();
            params.setMargins(leftMargin, topMargin, 0, 0);
        }
        addView(view);
    }
}
