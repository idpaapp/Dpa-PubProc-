package dpa_me.com.dpa_pubproc.Units;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class DeleteFromServer extends AsyncTask<Void, Integer, String> {
    private String mFileName;
    private boolean AllowToDelete = true;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public DeleteFromServer(String FolderPath, String FileName)
    {
        super();
        mFileName = "images/" + FolderPath + "/" +FileName+".jpg";
    }

    public DeleteFromServer(String FolderPath, String FileName, boolean HasExtention)
    {
        super();
        mFileName = FolderPath + "/" +FileName+".jpg";

        if (HasExtention)
            mFileName = FolderPath + "/" +FileName;
        else
            mFileName = FolderPath + "/" +FileName+".jpg";

        if (FileName.contains("user_empty"))
            AllowToDelete = false;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (AllowToDelete) {
            String data = null;
            StringBuilder sb = new StringBuilder();

            try {
                data = URLEncoder.encode("FileName", "UTF8") + "=" + URLEncoder.encode(mFileName, "UTF8");
                URLConnection connect = new URL(PubProc.HttpRootAddress + "Delete.php").openConnection();
                connect.setDoOutput(true);
                OutputStreamWriter ws = new OutputStreamWriter(connect.getOutputStream());
                ws.write(data);
                ws.flush();

                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));

                String line = null;
                while ((line = reader.readLine()) != null)
                    sb.append(line);

            } catch (Exception e) {
                Log.e("JSONError", e.toString());
            }
        }
        return "Done";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
