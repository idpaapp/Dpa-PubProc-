package dpa_me.com.dpa_pubproc.Dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class QuestionDialogClass extends Dialog implements
    View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no;
  public TextView TxtMessage; 
  private String mMessage = "";
  public ImageView Logo;
  private View.OnClickListener mBtnOnClick;

  public QuestionDialogClass(Activity a, String Message, View.OnClickListener BtnOnClick) {
    super(a);
    mMessage = Message;
    mBtnOnClick = BtnOnClick;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.questiondialog);
    getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
    getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    setCancelable(false);
    
    TxtMessage = (TextView) findViewById(R.id.txt_dia);
    TxtMessage.setText(mMessage);
    yes = (Button) findViewById(R.id.btn_yes);
    no = (Button) findViewById(R.id.btn_no);
    yes.setOnClickListener(mBtnOnClick);
    no.setOnClickListener(mBtnOnClick);

    PubProc.HandleViewAndFontSize.overrideFonts(getContext(), findViewById(R.id.MainLayout));

    Logo = (ImageView) findViewById(R.id.Logo);
    PubProc.HandleImagesAndAnimations.JumpInAnimation(Logo, 300, 0);

    findViewById(R.id.MainLayout).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });

  }

  @Override
	public void onClick(View v) {

	  }
}