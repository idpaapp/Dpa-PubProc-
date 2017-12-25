package dpa_me.com.dpa_pubproc.Units;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFileAsync extends AsyncTask<String, Void, String> {
    private String mSourceFileUri;
    private String mThumbSourceFileUri;
    private String mFilePath;
    private Runnable mAfterUpload;
    private Runnable mPreUpload;
    private Bitmap mImageBitmap;
    private boolean mHasThumbnail;

    public UploadFileAsync(String SourceFileUri, Bitmap ImageBitmap, String FilePath, Runnable PreUpload , Runnable AfterUpload){
        mSourceFileUri = SourceFileUri;
        mFilePath = FilePath;
        mAfterUpload = AfterUpload;
        mPreUpload = PreUpload;
        mImageBitmap = ImageBitmap;
        mHasThumbnail = false;
    }

    public UploadFileAsync(String SourceFileUri, Bitmap ImageBitmap, String FilePath, Runnable PreUpload , Runnable AfterUpload, boolean HasThumbnail){
        mSourceFileUri = SourceFileUri;
        mFilePath = FilePath;
        mAfterUpload = AfterUpload;
        mPreUpload = PreUpload;
        mImageBitmap = ImageBitmap;
        mHasThumbnail = HasThumbnail;
    }

    @Override
    protected String doInBackground(String... params) {
        mThumbSourceFileUri = mSourceFileUri + "_Thumbnail";
        if (mImageBitmap != null) {
            PubProc.HandleDisk.SetLocalPath();
            mSourceFileUri = PubProc.HandleDisk.SaveBitmapToFile(mImageBitmap, mSourceFileUri);
        }

        UploadFile(mSourceFileUri);

        if (mHasThumbnail){

            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mSourceFileUri), 200, 200);
            mThumbSourceFileUri = PubProc.HandleDisk.SaveBitmapToFile(ThumbImage, mThumbSourceFileUri);
            UploadFile(mThumbSourceFileUri);
        }
        return "Executed";
    }

    private void UploadFile(String SourceFileUri){
        try {
            int serverResponseCode = 0;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(SourceFileUri);

            if (sourceFile.isFile()) {

                try {
                    String upLoadServerUri = PubProc.HttpRootAddress + "Upload_"+mFilePath+"_Pic.php";

                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("bill", SourceFileUri);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                            + SourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode == 200) {
                    }
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        if (mAfterUpload != null && result.equals("Executed"))
            mAfterUpload.run();
    }

    @Override
    protected void onPreExecute() {
        if (mPreUpload != null)
            mPreUpload.run();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}