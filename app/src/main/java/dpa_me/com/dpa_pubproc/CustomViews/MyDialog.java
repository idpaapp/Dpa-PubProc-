package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import dpa_me.com.dpa_pubproc.R;

public class MyDialog extends FrameLayout {

    AppCompatImageView md_img_header;
    LinearLayout md_lay_main;
    StrokeTextView md_txt_title;
    PushImageView md_btn_left;
    PushImageView md_btn_center;
    PushImageView md_btn_right;

    public MyDialog(Context context) {
        super(context);
        init(null);
    }

    public MyDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void initViews(View view){
        md_img_header = view.findViewById(R.id.md_img_header);
        md_lay_main = view.findViewById(R.id.md_lay_main);
        md_txt_title = view.findViewById(R.id.md_txt_title);
        md_btn_right = view.findViewById(R.id.md_btn_right);
        md_btn_center = view.findViewById(R.id.md_btn_center);
        md_btn_left = view.findViewById(R.id.md_btn_left);
    }

    private void init(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.my_dialog, null);
        initViews(view);

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyDialog);

            if (attributes.getInt(R.styleable.MyDialog_mdHeaderType, 0) == 0)
                md_img_header.setImageResource(R.drawable.dialog_header_small);
            else
                md_img_header.setImageResource(R.drawable.dialog_header);

            if (attributes.getInt(R.styleable.MyDialog_mdCaption, 0) == 0)
                view = inflate(getContext(), R.layout.myradiogroup, null);
            else
                view = inflate(getContext(), R.layout.myradiolayout, null);

            md_txt_title.setText(attributes.getString(R.styleable.MyDialog_mdCaption));

            if (attributes.getInt(R.styleable.MyDialog_mdBtns, 0) == 0)
                md_btn_left.setVisibility(VISIBLE);

            if (attributes.getInt(R.styleable.MyDialog_mdBtns, 0) == 1)
                md_btn_center.setVisibility(VISIBLE);

            if (attributes.getInt(R.styleable.MyDialog_mdBtns, 0) == 2)
                md_btn_right.setVisibility(VISIBLE);

            attributes.recycle();
        }

        addView(view);
    }

}
