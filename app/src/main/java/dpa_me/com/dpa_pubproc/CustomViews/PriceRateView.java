package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;

public class PriceRateView extends FrameLayout {
    public int RValue;
    TextView dlr_1;
    TextView dlr_2;
    TextView dlr_3;
    TextView dlr_4;
    TextView dlr_5;

    int SelectedColor;
    int UnSelectedColor;

    public PriceRateView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public PriceRateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PriceRateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        RValue = 0;
        View view = inflate(getContext(), R.layout.price_rate_view, null);

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PriceRateView);

            dlr_1 = view.findViewById(R.id.dlr_1);
            dlr_2 = view.findViewById(R.id.dlr_2);
            dlr_3 = view.findViewById(R.id.dlr_3);
            dlr_4 = view.findViewById(R.id.dlr_4);
            dlr_5 = view.findViewById(R.id.dlr_5);

            dlr_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.PriceRateView_prv_TextSize, 14));
            dlr_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.PriceRateView_prv_TextSize, 14));
            dlr_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.PriceRateView_prv_TextSize, 14));
            dlr_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.PriceRateView_prv_TextSize, 14));
            dlr_5.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.PriceRateView_prv_TextSize, 14));

            SelectedColor = attributes.getInt(R.styleable.PriceRateView_prv_SelectedColor, R.color.primary);
            UnSelectedColor = attributes.getInt(R.styleable.PriceRateView_prv_UnSelectedColor, R.color.primary_text);

            dlr_1.setTextColor(UnSelectedColor);
            dlr_2.setTextColor(UnSelectedColor);
            dlr_3.setTextColor(UnSelectedColor);
            dlr_4.setTextColor(UnSelectedColor);
            dlr_5.setTextColor(UnSelectedColor);

            attributes.recycle();
        }

        addView(view);
    }

    private void clearAll() {
        dlr_1.setTextColor(UnSelectedColor);
        dlr_2.setTextColor(UnSelectedColor);
        dlr_3.setTextColor(UnSelectedColor);
        dlr_4.setTextColor(UnSelectedColor);
        dlr_5.setTextColor(UnSelectedColor);
    }

    public void setRate(int pos) {
        clearAll();
        RValue = pos;

        switch (pos) {
            case 1:
                dlr_1.setTextColor(SelectedColor);
                break;
            case 2:
                dlr_1.setTextColor(SelectedColor);
                dlr_2.setTextColor(SelectedColor);
                break;
            case 3:
                dlr_1.setTextColor(SelectedColor);
                dlr_2.setTextColor(SelectedColor);
                dlr_3.setTextColor(SelectedColor);
                break;
            case 4:
                dlr_1.setTextColor(SelectedColor);
                dlr_2.setTextColor(SelectedColor);
                dlr_3.setTextColor(SelectedColor);
                dlr_4.setTextColor(SelectedColor);
                break;
            case 5:
                dlr_1.setTextColor(SelectedColor);
                dlr_2.setTextColor(SelectedColor);
                dlr_3.setTextColor(SelectedColor);
                dlr_4.setTextColor(SelectedColor);
                dlr_5.setTextColor(SelectedColor);
                break;
        }
    }

    public int getValue() {
        return RValue;
    }
}
