package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dpa_me.com.dpa_pubproc.R;


public class MyRadioGroup extends FrameLayout {
    TextView textView;
    MyRadioButton rb1;
    MyRadioButton rb2;
    MyRadioButton rb3;
    MyRadioButton rb4;
    MyRadioButton rb5;
    MyRadioButton rb6;
    MyRadioButton rb7;
    MyRadioButton rb8;
    MyRadioButton rb9;

    LinearLayout grpMainLayout;

    Runnable mOnClick;

    RadioGroup radioGroup;

    private ArrayList<SimpleItem> CaptionList;
    public String RValue;

    public MyRadioGroup(Context context) {
        super(context);
        init(null);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mOnClick = null;
        RValue = "0";
        View view = inflate(getContext(), R.layout.myradiolayout, null);

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyRadioGroup);
            if (attributes.getInt(R.styleable.MyRadioGroup_captionPostion, 0) == 0)
                view = inflate(getContext(), R.layout.myradiogroup, null);
            else
                view = inflate(getContext(), R.layout.myradiolayout, null);

            textView = view.findViewById(R.id.Label);
            rb1 = view.findViewById(R.id.rb1);
            rb2 = view.findViewById(R.id.rb2);
            rb3 = view.findViewById(R.id.rb3);
            rb4 = view.findViewById(R.id.rb4);
            rb5 = view.findViewById(R.id.rb5);
            rb6 = view.findViewById(R.id.rb6);
            rb7 = view.findViewById(R.id.rb7);
            rb8 = view.findViewById(R.id.rb8);
            rb9 = view.findViewById(R.id.rb9);

            radioGroup = view.findViewById(R.id.radioGroup);
            grpMainLayout = view.findViewById(R.id.grpMainLayout);

            textView.setText(attributes.getString(R.styleable.MyRadioGroup_caption));
            textView.setTextColor(attributes.getColor(R.styleable.MyRadioGroup_textColor,
                    getResources().getColor(R.color.primary_text)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.MyRadioGroup_mrgtextSize, 14));
            if (attributes.getInt(R.styleable.MyRadioGroup_groupOrientation, 0) == 0)
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            else
                radioGroup.setOrientation(LinearLayout.VERTICAL);

            if (attributes.getBoolean(R.styleable.MyRadioGroup_hasCaption, true))
                textView.setVisibility(VISIBLE);
            else textView.setVisibility(GONE);

            textView.setTextColor(attributes.getInt(R.styleable.MyRadioGroup_captionColor, R.color.TextColorLight));

            int itemColor = attributes.getInt(R.styleable.MyRadioGroup_textColor, R.color.primary_text);
            rb1.setTextColor(itemColor);
            rb2.setTextColor(itemColor);
            rb3.setTextColor(itemColor);
            rb4.setTextColor(itemColor);
            rb5.setTextColor(itemColor);
            rb6.setTextColor(itemColor);
            rb7.setTextColor(itemColor);
            rb8.setTextColor(itemColor);
            rb9.setTextColor(itemColor);

            attributes.recycle();
        }

        addView(view);
    }

    public void setOnRadioClick(Runnable OnClick){
        mOnClick = OnClick;
    }

    private void clearRadio(){
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        rb5.setChecked(false);
        rb6.setChecked(false);
        rb7.setChecked(false);
        rb8.setChecked(false);
        rb9.setChecked(false);
    }

    private void setRadioData(final MyRadioButton rb, int pos){
        rb.setText(CaptionList.get(pos).Title);
        rb.setTag(CaptionList.get(pos).id);
        rb.setVisibility(VISIBLE);
        rb.setOnClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clearRadio();
                rb.setChecked(true);
                RValue = rb.getTag().toString();

                if (mOnClick != null)
                    mOnClick.run();
            }
        });
    }

    public String getValue(){
        return RValue;
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setSelectedItem(int pos){
        clearRadio();
        pos++;
        switch (pos) {
            case 1: rb1.setChecked(true); break;
            case 2: rb2.setChecked(true); break;
            case 3: rb3.setChecked(true); break;
            case 4: rb4.setChecked(true); break;
            case 5: rb5.setChecked(true); break;
            case 6: rb6.setChecked(true); break;
            case 7: rb7.setChecked(true); break;
            case 8: rb8.setChecked(true); break;
            case 9: rb9.setChecked(true); break;
        }
    }

    public void setPos(int pos){
        clearRadio();
        pos++;
        switch (pos) {
            case 1: rb1.setChecked(true); RValue = rb1.getTag().toString(); break;
            case 2: rb2.setChecked(true); RValue = rb2.getTag().toString(); break;
            case 3: rb3.setChecked(true); RValue = rb3.getTag().toString(); break;
            case 4: rb4.setChecked(true); RValue = rb4.getTag().toString(); break;
            case 5: rb5.setChecked(true); RValue = rb5.getTag().toString(); break;
            case 6: rb6.setChecked(true); RValue = rb6.getTag().toString(); break;
            case 7: rb7.setChecked(true); RValue = rb7.getTag().toString(); break;
            case 8: rb8.setChecked(true); RValue = rb8.getTag().toString(); break;
            case 9: rb9.setChecked(true); RValue = rb9.getTag().toString(); break;
        }
    }

    public void setCaptionList(ArrayList<SimpleItem> captionList){
        CaptionList = captionList;

        if (CaptionList.size() == 1)
            setRadioData(rb1, 0);

        if (CaptionList.size() == 2) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
        }

        if (CaptionList.size() == 3) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
        }

        if (CaptionList.size() == 4) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
        }

        if (CaptionList.size() == 5) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
            setRadioData(rb5, 4);
        }

        if (CaptionList.size() == 6) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
            setRadioData(rb5, 4);
            setRadioData(rb6, 5);
        }

        if (CaptionList.size() == 7) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
            setRadioData(rb5, 4);
            setRadioData(rb6, 5);
            setRadioData(rb7, 6);
        }

        if (CaptionList.size() == 8) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
            setRadioData(rb5, 4);
            setRadioData(rb6, 5);
            setRadioData(rb7, 6);
            setRadioData(rb8, 7);
        }

        if (CaptionList.size() == 9) {
            setRadioData(rb1, 0);
            setRadioData(rb2, 1);
            setRadioData(rb3, 2);
            setRadioData(rb4, 3);
            setRadioData(rb5, 4);
            setRadioData(rb6, 5);
            setRadioData(rb7, 6);
            setRadioData(rb8, 7);
            setRadioData(rb9, 8);
        }

        setSelectedItem(0);
    }
}
