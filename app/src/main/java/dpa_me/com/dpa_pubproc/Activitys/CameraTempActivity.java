package dpa_me.com.dpa_pubproc.Activitys;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yalantis.ucrop.UCrop;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import dpa_me.com.dpa_pubproc.R;

import static com.yalantis.ucrop.UCrop.REQUEST_CROP;

public class CameraTempActivity extends AppCompatActivity {

    PickerManager pickerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        this.pickerManager = GlobalHolder.getInstance().getPickerManager();
        this.pickerManager.setActivity(CameraTempActivity.this);
        this.pickerManager.pickPhotoWithPermission();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }

        switch (requestCode) {
            case PickerManager.REQUEST_CODE_SELECT_IMAGE:
                Uri uri;
                if (data != null)
                    uri = data.getData();
                else
                    uri = pickerManager.getImageFile();

                pickerManager.setUri(uri);
                pickerManager.startCropActivity();
                break;
            case REQUEST_CROP:
                if (data != null) {
                    pickerManager.handleCropResult(data);
                } else
                    finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PickerManager.REQUEST_CODE_IMAGE_PERMISSION)
            pickerManager.handlePermissionResult(grantResults);
        else
            finish();

    }

    public static class GlobalHolder {

        private PickerManager pickerManager;

        private static GlobalHolder ourInstance = new GlobalHolder();

        public static GlobalHolder getInstance() {
            return ourInstance;
        }

        private GlobalHolder() {
        }


        public PickerManager getPickerManager() {
            return pickerManager;
        }

        public void setPickerManager(PickerManager pickerManager) {
            this.pickerManager = pickerManager;
        }
    }

    public static class PickerBuilder {
        public static final int SELECT_FROM_GALLERY = 0;
        public static final int SELECT_FROM_CAMERA = 1;
        private Activity activity;
        private onPermissionRefusedListener permissionRefusedListener;
        protected onImageReceivedListener imageReceivedListener;
        private PickerManager pickerManager;

        public PickerBuilder(Activity activity, int type) {
            this.activity = activity;
            pickerManager = (type == PickerBuilder.SELECT_FROM_GALLERY) ? new ImagePickerManager(activity) : new CameraPickerManager(activity);

        }


        public interface onPermissionRefusedListener {
            void onPermissionRefused();
        }

        public interface onImageReceivedListener {
            void onImageReceived(Uri imageUri);
        }


        public void start() {
            Intent intent = new Intent(activity, CameraTempActivity.class);
            activity.startActivity(intent);

            GlobalHolder.getInstance().setPickerManager(pickerManager);

        }

        public PickerBuilder setOnImageReceivedListener(onImageReceivedListener listener) {
            pickerManager.setOnImageReceivedListener(listener);
            return this;
        }

        public PickerBuilder setOnPermissionRefusedListener(onPermissionRefusedListener listener) {
            pickerManager.setOnPermissionRefusedListener(listener);
            return this;
        }

        public PickerBuilder setCropScreenColor(int cropScreenColor) {
            pickerManager.setCropActivityColor(cropScreenColor);
            return this;
        }

        public PickerBuilder setImageName(String imageName) {
            pickerManager.setImageName(imageName);
            return this;
        }

        public PickerBuilder withTimeStamp(boolean withTimeStamp) {
            pickerManager.withTimeStamp(withTimeStamp);
            return this;
        }

        public PickerBuilder setImageFolderName(String folderName) {
            pickerManager.setImageFolderName(folderName);
            return this;
        }

        public PickerBuilder setCustomizedUcrop(UCrop ucrop) {
            pickerManager.setCustomizedUcrop(ucrop);
            return this;
        }

        public PickerBuilder setMaxCropSize(int x, int y) {
            pickerManager.setMaxCropSize(x, y);
            return this;
        }
    }

    public static class CameraPickerManager extends PickerManager {

        public CameraPickerManager(Activity activity) {
            super(activity);
        }

        protected void sendToExternalApp() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            mProcessingPhotoUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mProcessingPhotoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }

    }

    public static class ImagePickerManager extends PickerManager {

        public ImagePickerManager(Activity activity) {
            super(activity);
        }

        protected void sendToExternalApp() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");

            activity.startActivityForResult(Intent.createChooser(intent, "انتخاب عکس..."),
                    REQUEST_CODE_SELECT_IMAGE);
        }

        @Override
        public void setUri(Uri uri) {
            mProcessingPhotoUri = uri;
        }

    }

    public static abstract class PickerManager {
        public static final int REQUEST_CODE_SELECT_IMAGE = 200;
        public static final int REQUEST_CODE_IMAGE_PERMISSION = 201;
        protected Uri mProcessingPhotoUri;
        private boolean withTimeStamp = true;
        private String folder = null;
        private String imageName;
        private int mX;
        private int mY;
        protected Activity activity;
        private UCrop uCrop;
        protected PickerBuilder.onImageReceivedListener imageReceivedListener;
        protected PickerBuilder.onPermissionRefusedListener permissionRefusedListener;
        private int cropActivityColor = R.color.primary;

        public PickerManager setOnImageReceivedListener(PickerBuilder.onImageReceivedListener listener) {
            this.imageReceivedListener = listener;
            return this;
        }

        public PickerManager setOnPermissionRefusedListener(PickerBuilder.onPermissionRefusedListener listener) {
            this.permissionRefusedListener = listener;
            return this;
        }

        public PickerManager(Activity activity) {
            this.activity = activity;
            this.imageName = activity.getString(R.string.app_name);
        }


        public void pickPhotoWithPermission() {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(activity.findViewById(android.R.id.content),
                        "لطفا اجازه دسترسی به حافظه را بدهید",
                        Snackbar.LENGTH_INDEFINITE).setAction("تایید دسترسی",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA},
                                        REQUEST_CODE_IMAGE_PERMISSION);
                            }
                        }).show();

            } else
                sendToExternalApp();
        }

        public void handlePermissionResult(int[] grantResults) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted
                sendToExternalApp();

            } else {

                // permission denied
                if (permissionRefusedListener != null)
                    permissionRefusedListener.onPermissionRefused();
                activity.finish();
            }
        }


        protected abstract void sendToExternalApp();

        protected Uri getImageFile() {
            String imagePathDownLoad = Environment.getExternalStorageDirectory() + "/" +
                    (folder == null ?
                            Environment.DIRECTORY_DOWNLOADS :folder);

            File pathDownload = new File(imagePathDownLoad);
            if (!pathDownload.exists()) {
                pathDownload.mkdir();
            } else {
                try {
                    FileUtils.cleanDirectory(pathDownload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String imagePathStr = Environment.getExternalStorageDirectory() + "/" +
                    (folder == null ?
                            Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) :
                            folder);

            File path = new File(imagePathStr);
            if (!path.exists()) {
                path.mkdir();
            } else {
                try {
                    FileUtils.cleanDirectory(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String finalPhotoName = imageName + ".jpg";
            File photo = new File(path, finalPhotoName);
            return Uri.fromFile(photo);
        }

        public void setUri(Uri uri) {

        }


        public void startCropActivity() {
            if (uCrop == null) {
                Uri filename = getImageFile();
                uCrop = UCrop.of(mProcessingPhotoUri, filename);
                uCrop = uCrop.withAspectRatio(mX, mY);
                uCrop = uCrop.withMaxResultSize(mX, mY);
                UCrop.Options options = new UCrop.Options();
                options.setHideBottomControls(true);
                options.setLogoColor(activity.getResources().getColor(R.color.primary_text));
                options.setToolbarColor(activity.getResources().getColor(R.color.primary_dark));
                options.setStatusBarColor(activity.getResources().getColor(R.color.primary_dark));
                options.setActiveWidgetColor(activity.getResources().getColor(R.color.primary));
                options.setToolbarTitle("برش تصویر");
                uCrop = uCrop.withOptions(options);
            }

            uCrop.start(activity);
        }

        public void handleCropResult(Intent data) {
            Uri resultUri = UCrop.getOutput(data);
            if (imageReceivedListener != null)
                imageReceivedListener.onImageReceived(resultUri);

            activity.finish();
        }


        public PickerManager setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public PickerManager setImageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public PickerManager setCropActivityColor(int cropActivityColor) {
            this.cropActivityColor = cropActivityColor;
            return this;
        }

        public PickerManager withTimeStamp(boolean withTimeStamp) {
            this.withTimeStamp = withTimeStamp;
            return this;
        }

        public PickerManager setImageFolderName(String folder) {
            this.folder = folder;
            return this;
        }

        public PickerManager setCustomizedUcrop(UCrop customizedUcrop) {
            this.uCrop = customizedUcrop;
            return this;
        }

        public PickerManager setMaxCropSize(int x, int y) {
            this.mX = x;
            this.mY = y;
            return this;
        }
    }

}
