package dpa_me.com.dpa_pubproc.ZarinPal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentRequest extends Payment {


    private String merchantID;
    private long   amount;
    private String mobile;
    private String email;
    private String description;
    private String callBackURL;
    private String authority;
    private String option;
    private Runnable afterRun;


    @NonNull
    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    @NonNull
    public void setOption(String option) {
        this.option = option;
    }

    @NonNull
    public void setAfterRun(Runnable afterRun) {
        this.afterRun = afterRun;
    }

    @NonNull
    public void setCallbackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    @NonNull
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @NonNull
    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Nullable
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getAmount() {
        return amount;
    }

    public String getOption() {
        return option;
    }

    public String getDescription() {
        return description;
    }

    public Runnable getAfterRun() {
        return afterRun;
    }

    public String getMobile() {
        return mobile;
    }


    public String getMerchantID() {
        return merchantID;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthority() {
        return authority;
    }

    public JSONObject getPaymentRequestAsJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MERCHANT_ID_PARAMS, getMerchantID());
        jsonObject.put(AMOUNT_PARAMS, getAmount());
        jsonObject.put(DESCRIPTION_PARAMS, getDescription());
        jsonObject.put(CALLBACK_URL_PARAMS, getCallBackURL());
        jsonObject.put(MOBILE_PARAMS, getMobile());
        jsonObject.put(EMAIL_PARAMS, getEmail());
        jsonObject.put(OPTION_PARAMS, getOption());
        jsonObject.put(RUNNABLE_PARAMS, getAfterRun());
        return jsonObject;
    }


}
