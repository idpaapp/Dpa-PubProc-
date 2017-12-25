package dpa_me.com.dpa_pubproc.Dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import dpa_me.com.dpa_pubproc.Units.PubProc;
import dpa_me.com.dpa_pubproc.R;

public class CameraOrGallryDialog extends DialogFragment  {

    public Context mContext;
    private Uri picUri;
    LinearLayout imgTakeCam;
    LinearLayout imgTakeTrash;
    LinearLayout imgTakeGallery;
    Runnable mRunOnTakePic = null;
    Runnable mRunOnDelete = null;
    boolean mHasDelete = false;
    int mX, mY;
    private boolean LockOpr;

    ImageView mSender;
    String mFileName = "temporary_holder";

    public CameraOrGallryDialog() {
        super();
    }

    public CameraOrGallryDialog(Activity a, ImageView Sender) {
        super();
        mSender = Sender;
        mX = PubProc.xdpi;
        mY = PubProc.ydpi;
    }

    public CameraOrGallryDialog(Activity a, ImageView Sender, Runnable RunOnTakePic) {
        super();
        mSender = Sender;
        mRunOnDelete = null;
        mRunOnTakePic = RunOnTakePic;
        mX = PubProc.xdpi;
        mY = PubProc.ydpi;
    }

    public CameraOrGallryDialog(Activity a, ImageView Sender, Runnable RunOnTakePic, int X, int Y) {
        super();
        mSender = Sender;
        mRunOnDelete = null;
        mRunOnTakePic = RunOnTakePic;
        mX = X;
        mY = Y;

    }

    public CameraOrGallryDialog(Activity a, ImageView Sender, Runnable RunOnTakePic, Runnable RunOnDelete, boolean HasDelete) {
        super();
        mSender = Sender;
        mRunOnDelete = RunOnDelete;
        mRunOnTakePic = RunOnTakePic;
        mHasDelete = HasDelete;
        mX = PubProc.xdpi;
        mY = PubProc.xdpi;
    }

    public CameraOrGallryDialog(Activity a, ImageView Sender, Runnable RunOnTakePic, Runnable RunOnDelete, boolean HasDelete, int X, int Y) {
        super();
        mSender = Sender;
        mRunOnDelete = RunOnDelete;
        mRunOnTakePic = RunOnTakePic;
        mHasDelete = HasDelete;
        mX = X;
        mY = Y;
    }

    public CameraOrGallryDialog(Activity a, String AttachmentPrefix, Runnable RunOnTakePic, Runnable RunOnDelete, boolean HasDelete, int X, int Y) {
        super();
        Random r = new Random();
        int randnum = r.nextInt(999999999 - 100000000) + 100000000;
        mFileName = AttachmentPrefix + String.valueOf(randnum);
        mSender = null;
        mRunOnDelete = RunOnDelete;
        mRunOnTakePic = RunOnTakePic;
        mHasDelete = HasDelete;
        mX = X;
        mY = Y;
    }

    public CameraOrGallryDialog(String AttachmentPrefix, ImageView Sender, Runnable RunOnTakePic, Runnable RunOnDelete, boolean HasDelete, int X, int Y) {
        super();
        Random r = new Random();
        int randnum = r.nextInt(999999999 - 100000000) + 100000000;
        mFileName = AttachmentPrefix + String.valueOf(randnum);
        mSender = Sender;
        mRunOnDelete = RunOnDelete;
        mRunOnTakePic = RunOnTakePic;
        mHasDelete = HasDelete;
        mX = X;
        mY = Y;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        LockOpr = false;
        mContext = getActivity();
        View view = inflater.inflate(R.layout.cameraorgallerylayout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        imgTakeCam = (LinearLayout) view.findViewById(R.id.imgTakeCam);
        imgTakeGallery = (LinearLayout) view.findViewById(R.id.imgTakeGallery);
        imgTakeTrash = (LinearLayout) view.findViewById(R.id.imgTakeTrash);

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
                if (mRunOnDelete != null)
                    mRunOnDelete.run();
                mSender.setTag("");
                dismissAllowingStateLoss();
            }
        });

        return view;
    }

    private void gallery() {
        if (!LockOpr) {
            LockOpr = true;
            new TempActivity.PickerBuilder(PubProc.mActivity, TempActivity.PickerBuilder.SELECT_FROM_GALLERY)
                    .setOnImageReceivedListener(new TempActivity.PickerBuilder.onImageReceivedListener() {
                        @Override
                        public void onImageReceived(Uri imageUri) {
                            picUri = imageUri;
                            if (mSender != null) {
                                mSender.setImageResource(R.drawable.camera);
                                mSender.setScaleType(ImageView.ScaleType.CENTER);
                                mSender.setTag(picUri);

                                mSender.setImageURI(imageUri);
                                mSender.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                            if (mRunOnTakePic != null)
                                mRunOnTakePic.run();
                            dismissAllowingStateLoss();
                        }
                    })

                    .setMaxCropSize(mX, mY)
                    .start();
            LockOpr = false;
        }
        return;
    }

    private void camera() {
        if (!LockOpr) {
            LockOpr = true;
            new TempActivity.PickerBuilder(PubProc.mActivity, TempActivity.PickerBuilder.SELECT_FROM_CAMERA)
                    .setOnImageReceivedListener(new TempActivity.PickerBuilder.onImageReceivedListener() {
                        @Override
                        public void onImageReceived(Uri imageUri) {
                            picUri = imageUri;
                            if (mSender != null) {
                                mSender.setImageResource(R.drawable.camera);
                                mSender.setScaleType(ImageView.ScaleType.CENTER);
                                mSender.setTag(picUri);

                                mSender.setImageURI(imageUri);
                                mSender.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                            if (mRunOnTakePic != null)
                                mRunOnTakePic.run();
                            dismissAllowingStateLoss();
                        }
                    })

                    .setMaxCropSize(mX, mY)
                    .start();
            LockOpr = false;
        }
        return;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

}