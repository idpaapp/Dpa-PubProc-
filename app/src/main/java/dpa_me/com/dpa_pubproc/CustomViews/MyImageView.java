package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class MyImageView extends ImageView {

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyImageView(Context context) {
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
