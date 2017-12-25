package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;


public class MyCheckBox extends FrameLayout {
    TextView textView;
    CheckBox checkBox;
    View ClickView;
    String CheckID;

    public MyCheckBox(Context context) {
        super(context);
        init(null);
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final View view = inflate(getContext(), R.layout.mycheckbox, null);
        textView = view.findViewById(R.id.textView);
        checkBox = view.findViewById(R.id.radioButton);
        ClickView = view.findViewById(R.id.ClickView);

        ClickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyCheckBox);
            textView.setText(attributes.getString(R.styleable.MyCheckBox_txtcaption));
            textView.setTextColor(attributes.getColor(R.styleable.MyCheckBox_txttextColor,
                    getResources().getColor(R.color.primary_text)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.MyCheckBox_txttextSize, 14));
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


    public void setRadioID(String checkid){CheckID = checkid;}

    public String getRadioID(){ return CheckID;}

    public boolean getChecked(){
        return checkBox.isChecked();
    }

    public void setChecked(boolean checked){
        checkBox.setChecked(checked);
    }
}
