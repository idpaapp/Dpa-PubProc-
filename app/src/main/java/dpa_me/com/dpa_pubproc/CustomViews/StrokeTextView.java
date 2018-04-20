package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class StrokeTextView extends FrameLayout {
    ImageView mainText;
    ImageView strokeText;
    RelativeLayout back_layout;

    float strokeWidth = 0.0f;

    int topMargin = 0;
    int bottomMargin = 0;
    int leftMargin = 0;
    int rightMargin = 0;

    private float xOffset;
    private float yOffset;
    private float xPivote;
    private float yPivote;

    private TypedArray attributes;
    private String mText;
    private boolean clickable;


    public StrokeTextView(Context context) {
        super(context);
        init(null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setText(String text){
        mText = text;

        mainText.setImageBitmap(textAsBitmapNoStorke(text,
                attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stTextSize, 14),
                attributes.getColor(R.styleable.StrokeTextView_stTextColor,getResources().getColor(R.color.light_text))));

        strokeText.setImageBitmap(textAsBitmap(text,
                attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stTextSize, 14),
                attributes.getColor(R.styleable.StrokeTextView_stStrokeColor,getResources().getColor(R.color.primary_text))));
    }

    public void setStBackground(int resourceid){
        back_layout.setBackgroundResource(resourceid);
    }

    public String getText(){
        return mText;
    }

    private void init(AttributeSet attrs) {
        final View view = inflate(getContext(), R.layout.stroke_text_view, null);
        mainText = view.findViewById(R.id.mainText);
        strokeText = view.findViewById(R.id.strokeText);
        back_layout = view.findViewById(R.id.back_layout);

        if (attrs != null) {
            attributes = getContext().obtainStyledAttributes(attrs, R.styleable.StrokeTextView);

            strokeWidth = attributes.getFloat(R.styleable.StrokeTextView_stStorkWidth, 1f);
            mainText.setImageBitmap(textAsBitmapNoStorke(attributes.getString(R.styleable.StrokeTextView_stCaption),
                    attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stTextSize, 14),
                    attributes.getColor(R.styleable.StrokeTextView_stTextColor,getResources().getColor(R.color.light_text))));

            strokeText.setImageBitmap(textAsBitmap(attributes.getString(R.styleable.StrokeTextView_stCaption),
                    attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stTextSize, 14),
                    attributes.getColor(R.styleable.StrokeTextView_stStrokeColor,getResources().getColor(R.color.primary_text))));

            mText = attributes.getString(R.styleable.StrokeTextView_stCaption);

            switch (attributes.getInt(R.styleable.StrokeTextView_stScaleType, 0)){
                case 0:
                    mainText.setScaleType(ImageView.ScaleType.CENTER);
                    strokeText.setScaleType(ImageView.ScaleType.CENTER);
                    break;
                case 1:
                    mainText.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    strokeText.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case 2:
                    mainText.setScaleType(ImageView.ScaleType.FIT_END);
                    strokeText.setScaleType(ImageView.ScaleType.FIT_END);
                    break;
                case 3:
                    mainText.setScaleType(ImageView.ScaleType.FIT_START);
                    strokeText.setScaleType(ImageView.ScaleType.FIT_START);
                    break;
            }

            bottomMargin = attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stBottomMargin, 0);
            topMargin = attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stTopMargin, 0);
            leftMargin = attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stLeftMargin, 0);
            rightMargin = attributes.getDimensionPixelSize(R.styleable.StrokeTextView_stRightMargin, 0);

            xOffset = attributes.getFloat(R.styleable.StrokeTextView_stxOffset, 1f);
            yOffset = attributes.getFloat(R.styleable.StrokeTextView_styOffset, 1f);
            xPivote = attributes.getFloat(R.styleable.StrokeTextView_stxPivote, 0.5f);
            yPivote = attributes.getFloat(R.styleable.StrokeTextView_styPivote, 0.5f);
            clickable = attributes.getBoolean(R.styleable.StrokeTextView_stClickable, true);

            RelativeLayout.LayoutParams mtparams = (RelativeLayout.LayoutParams) mainText.getLayoutParams();
            RelativeLayout.LayoutParams stparams = (RelativeLayout.LayoutParams) strokeText.getLayoutParams();
            mtparams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            stparams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mainText.setPaddingRelative(leftMargin, topMargin, rightMargin, bottomMargin);
                strokeText.setPaddingRelative(leftMargin, topMargin, rightMargin, bottomMargin);
            }

            mainText.setLayoutParams(mtparams);
            strokeText.setLayoutParams(stparams);

            back_layout.setBackgroundResource(attributes.getResourceId(R.styleable.StrokeTextView_stBackground, Color.TRANSPARENT));

            if (clickable) {
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Animation anim;
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                anim = new ScaleAnimation(
                                        1f, xOffset,
                                        1f, yOffset,
                                        Animation.RELATIVE_TO_SELF, xPivote,
                                        Animation.RELATIVE_TO_SELF, yPivote);
                                anim.setFillAfter(true);
                                anim.setDuration(300);
                                v.startAnimation(anim);
                                break;

                            case MotionEvent.ACTION_MOVE:
                                // touch move code
                                break;

                            case MotionEvent.ACTION_UP:
                                anim = new ScaleAnimation(
                                        xOffset, 1f,
                                        yOffset, 1f,
                                        Animation.RELATIVE_TO_SELF, xPivote,
                                        Animation.RELATIVE_TO_SELF, yPivote);
                                anim.setFillAfter(true);
                                anim.setDuration(300);
                                anim.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        clearAnimation();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                v.startAnimation(anim);
                                callOnClick();
                                break;
                        }
                        return true;
                    }
                });
            }
        }

        addView(view);
    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        text = PubProc.HandleString.ISNULL(text, "");
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "BTrafcBd.ttf"));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(strokeWidth);

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + strokeWidth); // round
        int height = (int) (baseline + paint.descent());
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public Bitmap textAsBitmapNoStorke(String text, float textSize, int textColor) {
        text = PubProc.HandleString.ISNULL(text, "");
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "BTrafcBd.ttf"));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + strokeWidth); // round
        int height = (int) (baseline + paint.descent());
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
