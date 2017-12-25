package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;


public class MyRadioButton extends FrameLayout {
    TextView textView;
    RadioButton radioButton;
    View ClickView;
    String radioID;

    public MyRadioButton(Context context) {
        super(context);
        init(null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final View view = inflate(getContext(), R.layout.myradiobtn, null);
        textView = view.findViewById(R.id.textView);
        radioButton = view.findViewById(R.id.radioButton);
        ClickView = view.findViewById(R.id.ClickView);

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyRadioGroup);
            textView.setText(attributes.getString(R.styleable.MyRadioGroup_caption));
            textView.setTextColor(attributes.getColor(R.styleable.MyRadioGroup_textColor,
                    getResources().getColor(R.color.primary_text)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.MyRadioGroup_textSize, 14));
            attributes.recycle();
        }
        addView(view);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener occl){
        radioButton.setOnCheckedChangeListener(occl);
    }
    public void setRadioID(String radioid){radioID = radioid;}

    public String getRadioID(){ return radioID;}

    public void setChecked(boolean checked){
        radioButton.setChecked(checked);
    }

    public void setOnClick(OnClickListener ocl){
        ClickView.setOnClickListener(ocl);
    }

    public void setTextColor(int color){
        textView.setTextColor(color);
    }
}
