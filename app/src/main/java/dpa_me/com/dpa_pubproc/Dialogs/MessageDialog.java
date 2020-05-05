package dpa_me.com.dpa_pubproc.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class MessageDialog extends Dialog {

    private boolean avoidDismiss = false;
    private String mMessage;
    private String mYes;
    private String mOption;
    private String mOption2;
    private TextView btn_yes;
    private TextView btn_Option;
    private TextView btn_Option_2;
    private TextView txt_dia;
    private int MessageType;

    public static final int SUCCESS = 1;
    public static final int WARNING = 3;
    public static final int ERROR = 2;

    public MessageDialog(@NonNull Context context, String message, int message_type) {
        super(context);
        this.mMessage = message;
        mYes = getContext().getString(R.string.lblConfrirm);
        this.MessageType = message_type;
    }

    public MessageDialog(@NonNull Context context, int message, int message_type) {
        super(context);
        this.mMessage = getContext().getString(message);
        this.MessageType = message_type;
        mYes = getContext().getString(R.string.lblConfrirm);
    }

    public interface IOpration{
        void onBtnYesClick();
    }

    public interface IOptionOpration{
        void onBtnClick();
    }

    public interface IOption2Opration{
        void onBtnClick();
    }

    private IOpration onOpration = null;
    private IOptionOpration onOptionOpration = null;
    private IOption2Opration onOption2Opration = null;

    public MessageDialog setOnClickBtns(IOpration onOpration){
        this.onOpration = onOpration;
        return this;
    }

    public MessageDialog setAvoidDismiss(boolean avoidDismiss){
        this.avoidDismiss = avoidDismiss;
        return this;
    }

    public MessageDialog setCaptions(String btnCaption){
        mYes = btnCaption;
        return this;
    }

    public MessageDialog setOption(String btnCaption, IOptionOpration opration){
        mOption = btnCaption;
        onOptionOpration = opration;
        return this;
    }

    public MessageDialog setOption2(String btnCaption, IOption2Opration opration){
        mOption2 = btnCaption;
        onOption2Opration = opration;
        return this;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.showmessagedialog);
        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        btn_Option_2 = findViewById(R.id.btn_Option_2);
        btn_Option = findViewById(R.id.btn_Option);
        btn_yes = findViewById(R.id.btn_yes);
        txt_dia = findViewById(R.id.txt_dia);

        txt_dia.setText(mMessage);

        btn_yes.setText(mYes);
        btn_Option.setText(mOption);
        btn_Option_2.setText(mOption2);

        if (onOptionOpration != null)
            btn_Option.setVisibility(View.VISIBLE);

        if (onOption2Opration != null)
            btn_Option_2.setVisibility(View.VISIBLE);

        btn_Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionOpration.onBtnClick();

                if (!avoidDismiss)
                    dismiss();
            }
        });

        btn_Option_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOption2Opration.onBtnClick();

                if (!avoidDismiss)
                    dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpration != null)
                    onOpration.onBtnYesClick();

                if (!avoidDismiss)
                    dismiss();
            }
        });

        PubProc.HandleViewAndFontSize.overrideFonts(getContext(), findViewById(R.id.MainLayout));
        AppCompatImageView logo = findViewById(R.id.Logo);

        switch (MessageType) {
            case 1: {
                btn_yes.setBackgroundResource(R.color.OKColor);
                logo.setImageResource(R.drawable.success);
                break;
            }
            case 2: {
                btn_yes.setBackgroundResource(R.color.CancelColor);
                logo.setImageResource(R.drawable.error);
                break;
            }
            case 3: {
                btn_yes.setBackgroundResource(R.color.WarningColor);
                logo.setImageResource(R.drawable.warning);
                break;
            }
        }

        if (logo != null)
            PubProc.HandleImagesAndAnimations.JumpInAnimation(logo, 300, 0);
    }
}

