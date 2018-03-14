package dpa_me.com.dpa_pubproc.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class MyProgressDialog extends Dialog {

    public Dialog d;
    public TextView TxtMessage;
    ProgressWheel pbProgress;
    private String mMessage = "";
    private Activity activity;

    public MyProgressDialog(Activity a, String Message) {
        super(a, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        activity = a;
        mMessage = Message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.progressdialog);
        getWindow().setBackgroundDrawableResource(R.color.TransBackColor);

        pbProgress = findViewById(R.id.pbProgress);
        if (pbProgress != null) {
            pbProgress.setTag("1");
            pbProgress.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (v.getTag().toString().equals("1"))
                        v.setTag("2");
                    return false;
                }
            });

            pbProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pbProgress.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (v.getTag().toString().equals("3"))
                                dismiss();
                            return false;
                        }
                    });

                    if (v.getTag().toString().equals("2"))
                        v.setTag("3");
                }
            });
        }

        TxtMessage = findViewById(R.id.txtProgress);
        if (mMessage.equals(""))
            TxtMessage.setVisibility(View.GONE);
        else
            TxtMessage.setText(mMessage);

        PubProc.HandleViewAndFontSize.overrideFonts(getContext(), findViewById(R.id.MainLayout));

    }
}