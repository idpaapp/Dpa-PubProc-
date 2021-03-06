package dpa_me.com.dpa_pubproc.ZarinPal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;


public class ZarinPal {


    private static final String PAYMENT_GATEWAY_URL      = "https://www.zarinpal.com/pg/StartPay/";
    private static final String PAYMENT_REQUEST_URL      = "https://www.zarinpal.com/pg/rest/WebGate/PaymentRequest.json";
    private static final String VERIFICATION_PAYMENT_URL = "https://www.zarinpal.com/pg/rest/WebGate/PaymentVerification.json";

    private static ZarinPal instance;
    private static Context        mContext;
    private PaymentRequest paymentRequest;


    public static ZarinPal getPurchase(Context context) {
        if (instance == null) {
            instance = new ZarinPal();
            mContext = context;
        }
        return instance;
    }

    public static PaymentRequest getPaymentRequest(){
        return new PaymentRequest();
    }

    private ZarinPal() {
    }

    public void verificationPayment(Uri uri, final OnCallbackVerificationPaymentListener listener) {

        if (uri == null || paymentRequest == null || !uri.isHierarchical()) {
            return;
        }


        boolean isSuccess = uri.getQueryParameter("Status").equals("OK");
        String  authority = uri.getQueryParameter("Authority");

        if (!authority.equals(paymentRequest.getAuthority())) {
            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
            return;
        }

        if (!isSuccess) {
            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
            return;
        }


        VerificationPayment verificationPayment = new VerificationPayment();
        verificationPayment.setAmount(this.paymentRequest.getAmount());
        verificationPayment.setMerchantID(this.paymentRequest.getMerchantID());
        verificationPayment.setAuthority(authority);

        try {
            new HttpRequest(mContext, VERIFICATION_PAYMENT_URL)
                    .setJson(verificationPayment.getVerificationPaymentAsJson())
                    .setRequestMethod(HttpRequest.POST)
                    .setRequestType(HttpRequest.RAW)
                    .get(new HttpRequestListener() {
                        @Override
                        public void onSuccessResponse(JSONObject jsonObject, String contentResponse) {
                            try {
                                String refID = jsonObject.getString("RefID");
                                listener.onCallbackResultVerificationPayment(true, refID, paymentRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailureResponse(int httpStatusCode, String dataError) {
                            listener.onCallbackResultVerificationPayment(false, null, paymentRequest);
                        }
                    });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startPayment(final PaymentRequest paymentRequest, final OnCallbackRequestPaymentListener listener) {

        this.paymentRequest = paymentRequest;

        try {
            new HttpRequest(mContext, PAYMENT_REQUEST_URL)
                    .setRequestType(HttpRequest.RAW)
                    .setRequestMethod(HttpRequest.POST)
                    .setJson(paymentRequest.getPaymentRequestAsJson())
                    .get(new HttpRequestListener() {
                        @Override
                        public void onSuccessResponse(JSONObject jsonObject, String contentResponse) {

                            try {
                                int    status    = jsonObject.getInt("Status");
                                String authority = jsonObject.getString("Authority");
                                Uri    uri       = Uri.parse(PAYMENT_GATEWAY_URL + authority);
                                Intent intent    = new Intent(Intent.ACTION_VIEW, uri);
                                paymentRequest.setAuthority(authority);
                                listener.onCallbackResultPaymentRequest(status, authority, uri, intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailureResponse(int httpStatusCode, String dataError) {

                            try {
                                JSONObject object = new JSONObject(dataError);
                                int        status = object.getInt("Status");
                                listener.onCallbackResultPaymentRequest(status, null, null, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
