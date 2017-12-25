package ir.smartlab.persindatepicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class NumberPicker extends android.widget.NumberPicker {
    private Typeface tfs;
   public NumberPicker(Context context, AttributeSet attrs) {
     super(context, attrs);
     tfs = Typeface.createFromAsset(context.getAssets(),"BTrafcBd.ttf");     
   }  
   
   @Override
   public void addView(View child) {
     super.addView(child);
     updateView(child);
   }  

   @Override
   public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
     super.addView(child, index, params);
     updateView(child);
   }

   @Override
   public void addView(View child, android.view.ViewGroup.LayoutParams params) {
     super.addView(child, params);
     updateView(child);
   }
      

   private void updateView(View view) {
     if(view instanceof EditText){
       tfs = Typeface.createFromAsset(getContext().getAssets(),"BTrafcBd.ttf");
       ((EditText) view).setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
       ((EditText) view).setTypeface(tfs);       
       ((EditText) view).setTextColor(Color.BLACK);
     }
   }

 }