package dpa_me.com.dpa_pubproc.Dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import dpa_me.com.dpa_pubproc.Activitys.CameraTempActivity;
import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class CameraOrGallryDialog extends DialogFragment  {

    public Context mContext;
    private Uri picUri;
    LinearLayout imgTakeCam;
    LinearLayout imgTakeTrash;
    LinearLayout imgTakeGallery;
    boolean mHasDelete = false;
    int mX, mY;
    private boolean LockOpr;

    String mFileName = "temporary_holder";

    public interface IOprationDone{
        void onPictureTaken(Uri imageUri);
        void onPictureDelete();
    }

    private IOprationDone onOprationDone = null;

    public CameraOrGallryDialog setOnOprationDone(IOprationDone onOprationDone){
        this.onOprationDone = onOprationDone;
        return this;
    }

    public CameraOrGallryDialog setHasDelete(boolean onHasDelete){
        this.mHasDelete = onHasDelete;
        return this;
    }

    public CameraOrGallryDialog setXY(int x, int y){
        this.mX = x;
        this.mY = y;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        LockOpr = false;
        mContext = getActivity();
        View view = inflater.inflate(R.layout.cameraorgallerylayout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        imgTakeCam = view.findViewById(R.id.imgTakeCam);
        imgTakeGallery = view.findViewById(R.id.imgTakeGallery);
        imgTakeTrash = view.findViewById(R.id.imgTakeTrash);

        if (mHasDelete) imgTakeTrash.setVisibility(View.VISIBLE);

        imgTakeCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
                dismissAllowingStateLoss();
            }
        });

        imgTakeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
                dismissAllowingStateLoss();
            }
        });
        imgTakeTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOprationDone != null)
                    onOprationDone.onPictureDelete();
                dismissAllowingStateLoss();
            }
        });

        return view;
    }

    private void gallery() {
        if (!LockOpr) {
            LockOpr = true;
            new CameraTempActivity.PickerBuilder(getActivity(), CameraTempActivity.PickerBuilder.SELECT_FROM_GALLERY)
                    .setOnImageReceivedListener(new CameraTempActivity.PickerBuilder.onImageReceivedListener() {
                        @Override
                        public void onImageReceived(Uri imageUri) {
                            picUri = imageUri;

                            if (onOprationDone != null)
                                onOprationDone.onPictureTaken(imageUri);
                            dismissAllowingStateLoss();
                        }
                    })
                    .setMaxCropSize(mX, mY)
                    .setImageName(getString(R.string.app_name) + PubProc.HandleApplication.CreateRandomNumber())
                    .start();
            LockOpr = false;
        }
    }

    private void camera() {
        if (!LockOpr) {
            LockOpr = true;
            new CameraTempActivity.PickerBuilder(getActivity(), CameraTempActivity.PickerBuilder.SELECT_FROM_CAMERA)
                    .setOnImageReceivedListener(new CameraTempActivity.PickerBuilder.onImageReceivedListener() {
                        @Override
                        public void onImageReceived(Uri imageUri) {
                            picUri = imageUri;

                            if (onOprationDone != null)
                                onOprationDone.onPictureTaken(imageUri);
                            dismissAllowingStateLoss();
                        }
                    })

                    .setMaxCropSize(mX, mY)
                    .setImageName(getString(R.string.app_name) + PubProc.HandleApplication.CreateRandomNumber())
                    .start();
            LockOpr = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }



}