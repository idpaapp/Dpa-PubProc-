package dpa_me.com.dpa_pubproc.Units;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dpa_me.com.dpa_pubproc.R;

/**
 * Created by Farzad on 16/10/13.
 */
public class ServerConnection extends AsyncTask<Void,Void,String> {

    private DefaultHttpClient httpClient;
    private HttpPost httpPost;
    private List<NameValuePair> nameValuePairs;
    private String Link;
    private Runnable onAfterExecute = null;
    private Context context;
    private Timer timer = null;
    private int   Timeout = 30;
    private int   Counter = 0;
    private ServerConnection mtask = null;
    private boolean IsShowWaitDialog = true;
    private SwipeRefreshLayout refreshLayout = null;
    private boolean FShowError = true;
    public static String Result;
    private String Json = "";
    private StringEntity requestEntity = null;


    public ServerConnection(Context context, String link , Runnable afterExec , List<NameValuePair> Params) {

        this.Link           = PubProc.HttpRootAddress + link + ".php";
        this.onAfterExecute = afterExec;
        this.nameValuePairs = Params;
        this.Json           = "";
        this.context        = context;
        this.mtask          = this;

    }

    public ServerConnection(Context context, String link , Runnable afterExec , String JsonParam) {

        this.Link           = PubProc.HttpRootAddress + link + ".php";
        this.onAfterExecute = afterExec;
        this.nameValuePairs = null;
        this.Json           = JsonParam;
        this.context        = context;
        this.mtask          = this;

    }

    public ServerConnection(String ServerAddress , Context context , Runnable afterExec , String JsonParam) {

        this.Link           = ServerAddress;
        this.onAfterExecute = afterExec;
        this.nameValuePairs = null;
        this.Json           = JsonParam;
        this.context        = context;
        this.mtask          = this;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {

            PubProc.HandleDataBase.CheckConnection("");


            if (IsShowWaitDialog) {

                PubProc.HandleApplication.ShowProgressDialog(PubProc.mActivity);
            }

            TimeoutCounter();

            httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),30000);
            httpPost   = new HttpPost(Link);

            if (nameValuePairs != null)
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            else if (!Json.equals("")) {
                requestEntity = new StringEntity(Json, "UTF-8");
                httpPost.setEntity(requestEntity);
            }

        } catch (UnsupportedEncodingException e) {
            Log.e("ErrorConncetion",e.getMessage());
        }
    }

    private void TimeoutCounter() {

        Counter = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Counter++;

                if (Counter >= Timeout){

                    PubProc.HandleApplication.CloseProgressDialog();

                    if (refreshLayout != null)
                        refreshLayout.setRefreshing(false);

                    timer.cancel();

                    if (mtask != null){
                        mtask.cancel(true);

                    }

                    Handler handler = new Handler(context.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                           if (FShowError)
                               PubProc.HandleApplication.ToastMessage(context.getString(R.string.messNoInternetConnection));
                        }
                    });

                }

            }
        },1,1000);

    }

    @Override
    protected String doInBackground(Void... params) {


        try {
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + "\n");

            return sb.toString();

        } catch (IOException e) {
            Log.e("ErrorConncetion",e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            Result = s.trim();

            if (onAfterExecute != null)
                onAfterExecute.run();

            PubProc.HandleApplication.CloseProgressDialog();

            if (refreshLayout != null)
                refreshLayout.setRefreshing(false);

            if (timer != null)
                timer.cancel();
        } catch (Exception e) {
            Log.e("Error PostExec", e.getMessage());
        }
    }

    public void setShowWaitDialog(boolean showing){

        IsShowWaitDialog = showing;

    }

    public void setRefreshLayout(SwipeRefreshLayout layout){

        refreshLayout = layout;

    }

    public void setShowError(Boolean value){

        FShowError = value;

    }
}
