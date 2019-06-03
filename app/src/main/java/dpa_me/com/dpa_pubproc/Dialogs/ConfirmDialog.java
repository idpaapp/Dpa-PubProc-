package dpa_me.com.dpa_pubproc.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.CustomViews.StrokeTextView;
import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class ConfirmDialog extends Dialog {

    private boolean avoidDismiss = false;
    private String mMessage;
    private String mYes;
    private String mNo;
    private boolean mHideNo;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
        mYes = getContext().getString(R.string.lblYes);
        mNo = getContext().getString(R.string.lblNo);
    }

    public ConfirmDialog(@NonNull Context context, String message) {
        super(context);
        mYes = getContext().getString(R.string.lblYes);
        mNo = getContext().getString(R.string.lblNo);
        this.mMessage = message;
    }

    public ConfirmDialog(@NonNull Context context, int message) {
        super(context);
        mYes = getContext().getString(R.string.lblYes);
        mNo = getContext().getString(R.string.lblNo);
        this.mMessage = getContext().getString(message);
    }

    public interface IOpration {
        void onBtnYesClick();
        void onBtnNoClick();
    }

    public interface IYesOpration{
        void onBtnYesClick();
    }

    private IOpration onOpration = null;
    private IYesOpration onYesOpration = null;

    public ConfirmDialog setOnClickBtns(IOpration onOpration){
        this.onOpration = onOpration;
        return this;
    }

    public ConfirmDialog setOnClickBtns(IYesOpration onOpration){
        this.onYesOpration = onOpration;
        return this;
    }

    public ConfirmDialog setAvoidDismiss(boolean avoidDismiss){
        this.avoidDismiss = avoidDismiss;
        return this;
    }

    public ConfirmDialog hideNo(Boolean hideno){
        this.mHideNo = hideno;
        return this;
    }

    public ConfirmDialog setMessage(String message){
        this.mMessage = message;
        return this;
    }

    public ConfirmDialog setMessage(int message){
        this.mMessage = getContext().getString(message);
        return this;
    }

    public ConfirmDialog setCaptions(String yesCaption, String noCaption){
        mYes = yesCaption;
        mNo = noCaption;
        return this;
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.questiondialog);
        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        ((TextView) findViewById(R.id.txt_dia)).setText(mMessage);

        if (findViewById(R.id.btn_yes) instanceof StrokeTextView) {
            ((StrokeTextView) findViewById(R.id.btn_yes)).setText(mYes);
            ((StrokeTextView) findViewById(R.id.btn_no)).setText(mNo);
        }

        if (findViewById(R.id.btn_yes) instanceof Button) {
            ((Button) findViewById(R.id.btn_yes)).setText(mYes);
            ((Button) findViewById(R.id.btn_no)).setText(mNo);
        }

        if (mHideNo)
            findViewById(R.id.btn_no).setVisibility(View.GONE);

        findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpration != null)
                    onOpration.onBtnYesClick();

                if (onYesOpration != null)
                    onYesOpration.onBtnYesClick();

                if (!avoidDismiss)
                    dismiss();
            }
        });

        findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpration != null)
                    onOpration.onBtnNoClick();

                if (!avoidDismiss)
                    dismiss();
            }
        });

        PubProc.HandleViewAndFontSize.overrideFonts(getContext(), findViewById(R.id.MainLayout));

        ImageView logo = findViewById(R.id.Logo);
        if (logo != null)
            PubProc.HandleImagesAndAnimations.JumpInAnimation(logo, 300, 0);

        View btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
