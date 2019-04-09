package dpa_me.com.dpa_pubproc.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import dpa_me.com.dpa_pubproc.R;

import dpa_me.com.dpa_pubproc.Units.PubProc;
import dpa_me.com.dpa_pubproc.ZarinPal.OnCallbackRequestPaymentListener;
import dpa_me.com.dpa_pubproc.ZarinPal.PaymentRequest;
import dpa_me.com.dpa_pubproc.ZarinPal.ZarinPal;

import static dpa_me.com.dpa_pubproc.Units.PubProc.mMainPageActivity;

@SuppressLint("ValidFragment")
public class PaymentDialog extends DialogFragment implements
        View.OnClickListener {

    public Context mContext;
    private String mAmount;
    private String mBuyDesc;
    private String mMobile;
    private String mEmail;

    private int REQUEST_RESULT = 0;

    TextView pdBuyDesc;
    TextView pdBuyBudget;
    TextView btn_GotoBank;
    TextView btn_cancel;


    public interface IOpration{
        void onConfirmPayment();
        void onRejectPayment();
        void onBankResponsed();
    }

    public IOpration onOpration = null;

    public PaymentDialog setOnClickBtns(IOpration onOpration){
        this.onOpration = onOpration;
        return this;
    }

    public PaymentDialog setAmount(String amount){
        this.mAmount = amount;
        return this;
    }

    public PaymentDialog setMobile(String mobile){
        this.mMobile = mobile;
        return this;
    }

    public PaymentDialog setEmail(String userEmail){
        this.mEmail = userEmail;
        return this;
    }

    public PaymentDialog setBuyDescription(String buyDescription){
        this.mBuyDesc = buyDescription;
        return this;
    }

    public PaymentDialog() {
        super();
    }

    private void initViews(View view){
        pdBuyDesc = view.findViewById(R.id.pdBuyDesc);
        pdBuyBudget = view.findViewById(R.id.pdBuyBudget);
        btn_GotoBank = view.findViewById(R.id.btn_GotoBank);
        btn_cancel = view.findViewById(R.id.btn_cancel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.paydialog, container, false);
        initViews(view);

        PubProc.HandleViewAndFontSize.overrideFonts(mContext, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        pdBuyBudget.setText(mAmount);
        pdBuyDesc.setText(mBuyDesc);

        btn_GotoBank.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_GotoBank) {
            requestPayment();
            onOpration.onConfirmPayment();
            dismiss();
        } else {
            PubProc.HandleApplication.CloseProgressDialog();
            onOpration.onRejectPayment();
            dismiss();
        }
    }

    private void requestPayment() {

        ZarinPal purchase = ZarinPal.getPurchase(getContext());
        PaymentRequest payment  = ZarinPal.getPaymentRequest();

        payment.setMerchantID("6bffb16e-9abe-11e7-975e-000c295eb8fc");
        payment.setAmount(Long.parseLong(PubProc.HandleString.ClearSigns(mAmount)));
        payment.setDescription(mBuyDesc);
        payment.setMobile(mMobile);
        payment.setEmail(mEmail);
        payment.setAfterRun(onOpration);
        payment.setCallbackURL("restook://zarrinPayment");
        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                if (status == 100) {
                    mMainPageActivity.startActivityForResult(intent, REQUEST_RESULT);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), getString(R.string.messOperationFailedOnBuy), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
