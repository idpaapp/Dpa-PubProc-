package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import dpa_me.com.dpa_pubproc.R;


public class PushImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;
    private float xOffset;
    private float yOffset;
    private float xPivote;
    private float yPivote;

	public PushImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
        mContext = context;
        init(attrs);
	}

	public PushImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
	}

	private void init(AttributeSet attrs){
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PushImageView);
            xOffset = attributes.getFloat(R.styleable.PushImageView_xOffset, 1f);
            yOffset = attributes.getFloat(R.styleable.PushImageView_yOffset, 1f);
            xPivote = attributes.getFloat(R.styleable.PushImageView_xPivote, 0.5f);
            yPivote = attributes.getFloat(R.styleable.PushImageView_yPivote, 0.5f);
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Animation anim;
                switch(event.getAction()) {
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
                        v.startAnimation(anim);
                        callOnClick();
                        break;
                }
                return true;
            }
        });
    }

    public PushImageView(Context context) {
		super(context);
	}

	private String Item1;
	private String Item2;
	private String Item3;
	private String Item4;
	private String Item5;

    public void SetItems(int ItemNo, String Value)
    {
        switch (ItemNo) { 
        case 5: {Item5 = Value; break;}
        case 4: {Item4 = Value; break;}
        case 3: {Item3 = Value; break;}
        case 2: {Item2 = Value; break;}
        case 1: {Item1 = Value; break;}
        }
    } 
    
    public String GetItems(int ItemNo)
    {
        switch (ItemNo) { 
        case 5: return Item5;
        case 4: return Item4;
        case 3: return Item3;
        case 2: return Item2;
        case 1: return Item1;
        default : return "";
        }
    }

}
