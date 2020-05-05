package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v7.widget.AppCompatImageView;

import com.lb.auto_fit_textview.AutoResizeTextView;
import com.squareup.picasso.Picasso;

import dpa_me.com.dpa_pubproc.R;

public class DrawableTextView extends FrameLayout {
    AutoResizeTextView textView;
    AppCompatImageView imageview;

    public DrawableTextView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public DrawableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DrawableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.drawable_textview, null);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        if (attrs != null) {

            switch (attributes.getInt(R.styleable.DrawableTextView_icnSize, 1)){
                case 0: view = inflate(getContext(), R.layout.drawable_textview_small, null); break;
                case 1: view = inflate(getContext(), R.layout.drawable_textview, null); break;
                case 2: view = inflate(getContext(), R.layout.drawable_textview_large, null); break;
            }

            textView = view.findViewById(R.id.Label);
            imageview = view.findViewById(R.id.Icon);

            textView.setText(attributes.getString(R.styleable.DrawableTextView_titleText));
            textView.setTextColor(attributes.getColor(R.styleable.DrawableTextView_textFontColor,
                    getResources().getColor(R.color.primary_text)));
            imageview.setImageDrawable(attributes.getDrawable(R.styleable.DrawableTextView_iconDrawable));
        }

        addView(view);
        attributes.recycle();
    }

    public void setText(String text) {
        textView.setText(text);
    }
    public String getText() {
        return textView.getText().toString();
    }
    public void setIcon(int resourceid){ imageview.setImageResource(resourceid);};
    public void setIcon(Drawable drawable){ imageview.setImageDrawable(drawable);};
    public void setIcon(String filename){
        Picasso.with(getContext()).load(filename).into(imageview);};
}
