package dpa_me.com.dpa_pubproc.Units;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RenameOnServer extends AsyncTask<Void, Integer, String> {
    private String msFileName;
    private String mdFileName;
    private String mdFolderPath;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public RenameOnServer(String FolderPath, String sFileName, String dFileName)
    {
        super();
        mdFolderPath = FolderPath;
        msFileName = mdFolderPath + "/" +sFileName+".jpg";
        mdFileName = mdFolderPath + "/" +dFileName+".jpg";
    }

    @Override
    protected String doInBackground(Void... params) {
        String data= null;
        StringBuilder sb = new StringBuilder();

        try {
            data = URLEncoder.encode("SourceName","UTF8")+"="+ URLEncoder.encode(msFileName,"UTF8");
            data += "&" + URLEncoder.encode("DestName","UTF8")+"="+ URLEncoder.encode(mdFileName,"UTF8");
            URLConnection connect = new URL(PubProc.HttpRootAddress + "Rename.php").openConnection();
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
        return "Done";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
