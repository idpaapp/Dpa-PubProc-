package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dpa_me.com.dpa_pubproc.R;


public class IconedLabelView extends FrameLayout {
    TextView textView;
    ImageView imageview;

    public IconedLabelView(Context context) {
        super(context);
        init(null);
    }

    public IconedLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IconedLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.iconedlabel, null);
        textView = (TextView) view.findViewById(R.id.Label);
        imageview = (ImageView) view.findViewById(R.id.Icon);

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.IconedLabelView);
        if (attrs != null) {
            textView.setText(attributes.getString(R.styleable.IconedLabelView_TitleText));
            textView.setTextColor(attributes.getColor(R.styleable.IconedLabelView_TextColor,
                    getResources().getColor(R.color.primary_text)));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, attributes.getInt(R.styleable.IconedLabelView_TextSize, 14));

            imageview.setImageDrawable(attributes.getDrawable(R.styleable.IconedLabelView_Icon));
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
    public void setIcon(String filename){Picasso.with(getContext()).load(filename).into(imageview);};
}
