package dpa_me.com.dpa_pubproc.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class CheckInternetDialog extends Dialog implements View.OnClickListener {
    private Activity mActivity;
    private Class mMainActivity, mSplashActivity;

    public CheckInternetDialog(Activity activity, Runnable afterRun, Class MainActivity, Class SplashActivity) {
        super(PubProc.mActivity, R.style.full_screen_dialog);
        mActivity = activity;
        mMainActivity = MainActivity;
        mSplashActivity = SplashActivity;
    }

    @Override
    public void onBackPressed() {
        mActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_internet_layout);

        RelativeLayout MainLayout = (RelativeLayout) findViewById(R.id.MainLayout);
        ViewGroup.LayoutParams param = MainLayout.getLayoutParams();
        param.width = PubProc.xdpi;
        MainLayout.setLayoutParams(param);

        RelativeLayout btnRetry = (RelativeLayout) findViewById(R.id.BtnRetry);
        RelativeLayout btnWifi = (RelativeLayout) findViewById(R.id.BtnWifi);
        RelativeLayout btnMobile = (RelativeLayout) findViewById(R.id.BtnMobile);

        btnRetry.setOnClickListener(this);
        btnWifi.setOnClickListener(this);
        btnMobile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.BtnRetry) {
            if (PubProc.HandleApplication.CheckInternetConnection(getContext())) {
                if (mActivity.getClass().equals(mMainActivity) || mActivity.getClass().equals(mSplashActivity)) {
                    getContext().startActivity(new Intent(getContext(), mSplashActivity));
                } else mActivity.finish();

            } else
                Toast.makeText(getContext(), getContext().getString(R.string.messNoInternetConnection), Toast.LENGTH_SHORT).show();

        } else if (i == R.id.BtnWifi) {
            getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

        } else if (i == R.id.BtnMobile) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            getContext().startActivity(intent);

        }
    }
}
