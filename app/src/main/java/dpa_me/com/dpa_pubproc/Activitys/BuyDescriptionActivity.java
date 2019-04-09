package dpa_me.com.dpa_pubproc.Activitys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;
import dpa_me.com.dpa_pubproc.ZarinPal.OnCallbackVerificationPaymentListener;
import dpa_me.com.dpa_pubproc.ZarinPal.PaymentRequest;
import dpa_me.com.dpa_pubproc.ZarinPal.ZarinPal;
import ir.smartlab.persindatepicker.util.PersianCalendar;

public class BuyDescriptionActivity extends AppCompatActivity {
    private Context mContext;

    private TextView abtTxtBuyDate;
    private TextView abdTxtRefID;
    private TextView abdTxtPrice;
    private TextView abdTxtDesc;
    private TextView abdTxtTitle;
    private TextView abdBtnOk;
    private ImageView abdImgIcon;
    private RelativeLayout MainLayout;

    private void initViews(){
        abtTxtBuyDate = findViewById(R.id.abtTxtBuyDate);
        abdTxtRefID = findViewById(R.id.abdTxtRefID);
        abdTxtPrice = findViewById(R.id.abdTxtPrice);
        abdTxtDesc = findViewById(R.id.abdTxtDesc);
        abdTxtTitle = findViewById(R.id.abdTxtTitle);
        abdBtnOk = findViewById(R.id.abdBtnOk);
        abdImgIcon = findViewById(R.id.abdImgIcon);
        MainLayout = findViewById(R.id.MainLayout);
    }

    private void getZarrinResponse(){
        final Uri data = getIntent().getData();
        ZarinPal.getPurchase(BuyDescriptionActivity.this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, final PaymentRequest paymentRequest) {
                abtTxtBuyDate.setText(new PersianCalendar().getPersianLongDateAndTime());
                abdTxtRefID.setText(refID);
                abdTxtPrice.setText(PubProc.HandleString.SetThousandSeparator(String.valueOf(paymentRequest.getAmount())) + " " + getString(R.string.lblToman));
                abdTxtDesc.setText(paymentRequest.getDescription());

                if (isPaymentSuccess) {
                    PubProc.InvoiceNo = refID;
                    PubProc.Authority = paymentRequest.getAuthority();
                    PubProc.HandleApplication.ShowProgressDialog(BuyDescriptionActivity.this);
                    paymentRequest.getOpration().onBankResponsed();
                    PubProc.ResultOfPayment = 100;
                } else {
                    abdImgIcon.setImageResource(R.drawable.response_error);
                    MainLayout.setBackgroundResource(R.color.CancelColor);
                    abdTxtTitle.setText(R.string.messOperationFailedOnBuy);
                    abdBtnOk.setTextColor(getResources().getColor(R.color.CancelColor));

                    PubProc.InvoiceNo = "0";
                }

                abdBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = PubProc.HandleApplication.SetActivityParams(this, R.layout.activity_buy_description, false, "");

        initViews();
        getZarrinResponse();
    }
}
