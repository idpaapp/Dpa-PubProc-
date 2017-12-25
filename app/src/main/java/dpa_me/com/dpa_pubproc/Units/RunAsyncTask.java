package dpa_me.com.dpa_pubproc.Units;

import android.os.AsyncTask;

public class RunAsyncTask extends AsyncTask<Void, Void, String> {
    private Runnable mAfterExecute;
    private Runnable mOnBackground;
    private Runnable mPreExecute;

    public RunAsyncTask(Runnable OnBackground) {
        super();
        mOnBackground = OnBackground;
        mAfterExecute = null;
        mPreExecute = null;
    }

    public RunAsyncTask(Runnable OnBackground, Runnable AfterExecute) {
        super();
        mAfterExecute = AfterExecute;
        mOnBackground = OnBackground;
        mPreExecute = null;
    }

    public RunAsyncTask(Runnable OnBackground, Runnable AfterExecute, Runnable PreExecute) {
        super();
        mAfterExecute = AfterExecute;
        mOnBackground = OnBackground;
        mPreExecute = PreExecute;
    }

    protected void onPreExecute() {
        if (mPreExecute != null)
            mPreExecute.run();
    }

    protected String doInBackground(Void... params) {
        try {
            mOnBackground.run();
            return "Complete";
        }
        catch (Exception ex) {
            return "NotComplete";
        }
    }

    protected void onPostExecute(String result) {
        if (result.equals("Complete")) {
            if (mAfterExecute != null)
                mAfterExecute.run();
        }
    }
}
