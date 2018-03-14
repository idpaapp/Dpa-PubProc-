package dpa_me.com.dpa_pubproc.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.CustomViews.StrokeTextView;
import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class ShowMessageDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public View yes;
    public View BtnOption;
    StrokeTextView title;
    public ImageView Logo;
    public TextView TxtMessage;
    private String mMessage = "";
    private String mBtnCaption = "";
    private String mOptionCaption = "";
    public int mMessageType = 0;
    private View.OnClickListener mBtnOnClick;
    private View.OnClickListener mOptionOnClick;

    public ShowMessageDialogClass(Activity a, String Message, int MessageType, View.OnClickListener BtnOnClick) {
        super(a);
        mMessage = Message;
        mBtnOnClick = BtnOnClick;
        mBtnCaption = getContext().getString(R.string.txtOk);
        mOptionCaption = "";
        mMessageType = MessageType;
    }

    public ShowMessageDialogClass(Activity a, String Message, int MessageType, String BtnCaption, View.OnClickListener BtnOnClick) {
        super(a);
        mMessage = Message;
        mBtnOnClick = BtnOnClick;
        mBtnCaption = BtnCaption;
        mOptionCaption = "";
        mMessageType = MessageType;
    }

    public ShowMessageDialogClass(Activity a, String Message, int MessageType, String BtnCaption, String OptionCaption,
                                  View.OnClickListener BtnOnClick, View.OnClickListener OptionOnClick) {
        super(a);
        mMessage = Message;
        mBtnOnClick = BtnOnClick;
        mBtnCaption = BtnCaption;
        mOptionCaption = OptionCaption;
        mOptionOnClick = OptionOnClick;
        mMessageType = MessageType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.showmessagedialog);
        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        PubProc.HandleViewAndFontSize.overrideFonts(getContext(), findViewById(R.id.MainLayout));

        TxtMessage = findViewById(R.id.txt_dia);
        TxtMessage.setText(mMessage);

        yes = findViewById(R.id.btn_yes);
        yes.setOnClickListener(mBtnOnClick);

        BtnOption = findViewById(R.id.btn_Option);
        BtnOption.setOnClickListener(mOptionOnClick);

        if (yes instanceof TextView) {
            ((TextView) yes).setText(mBtnCaption);
            ((TextView) BtnOption).setText(mOptionCaption);
        }

        if (yes instanceof StrokeTextView) {
            ((StrokeTextView) yes).setText(mBtnCaption);
            ((StrokeTextView) BtnOption).setText(mOptionCaption);
        }

        if (!mOptionCaption.equals("")) {
            BtnOption.setVisibility(View.VISIBLE);
            yes.setVisibility(View.VISIBLE);
        }

        Logo = findViewById(R.id.Logo);

        if (Logo != null)
            PubProc.HandleImagesAndAnimations.JumpInAnimation(Logo, 300, 200);

        if (yes instanceof StrokeTextView) {
            title = findViewById(R.id.title);
            View btn_close = findViewById(R.id.btn_close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            switch (mMessageType) {
                case 1: {
                    title.setText(getContext().getString(R.string.app_name));
                    break;
                }
                case 2: {
                    title.setText(getContext().getString(R.string.messError));
                    break;
                }
                case 3: {
                    title.setText(getContext().getString(R.string.messWarning));
                    break;
                }
            }
        } else {
            switch (mMessageType) {
                case 1: {
                    yes.setBackgroundResource(R.color.OKColor);
                    Logo.setImageResource(R.drawable.success);
                    break;
                }
                case 2: {
                    yes.setBackgroundResource(R.color.CancelColor);
                    Logo.setImageResource(R.drawable.error);
                    break;
                }
                case 3: {
                    yes.setBackgroundResource(R.color.WarningColor);
                    Logo.setImageResource(R.drawable.warning);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
    }
}