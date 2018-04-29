package nero.intel.com.leaf.fragment;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.ImageUpload;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Username;
import nero.intel.com.leaf.entity.uuid;
import nero.intel.com.leaf.model.LeafModel;
import nero.intel.com.leaf.view.CameraPreview;

import static android.content.ContentValues.TAG;

/**
 * Created by ny on 2018/3/6.
 */

public class LeafFragment extends Fragment implements View.OnClickListener {

    private Camera mCamera;
    private CameraPreview mPreview;
    FrameLayout preview;


    private Handler mHandler;
    private Handler activityHandler;


    ImageView photo;
    ImageView yulan;

    LeafModel leafModel;
    View view;


    Result<ImageUpload> uploadImageResult;
    Result<ImageShiBie> shibieImageResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private boolean safeCameraOpenInView(View view) {
        boolean qOpened = false;
        releaseCameraAndPreview();
        mCamera = getCameraInstance();
        qOpened = (mCamera != null);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int x = metrics.widthPixels;//获取了屏幕的宽度
            mPreview = new CameraPreview(getActivity().getBaseContext(), mCamera, x);
            preview = view.findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        return qOpened;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.leaf_fragment, container, false);


        safeCameraOpenInView(view);


        leafModel = new LeafModel();

        yulan = view.findViewById(R.id.yulan);
        photo = view.findViewById(R.id.photo);
        photo.setOnClickListener(this);

        set_handler();
        return view;
    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "uploadimage":
                        uploadImageResult = (Result<ImageUpload>) msg.getData().getSerializable("uploadimage");
                        if (uploadImageResult.getCode() != 0) {
                            Toast.makeText(getActivity(), uploadImageResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), uploadImageResult.getMsg(), Toast.LENGTH_SHORT).show();
                            leafModel.shibieImage(uploadImageResult.getData().getUuid());
                        }
                        break;
                    case "shibieimage":
                        shibieImageResult = (Result<ImageShiBie>) msg.getData().getSerializable("shibieimage");
                        System.out.println(shibieImageResult.toString());
                        if (shibieImageResult.getCode() != 0) {
                            Toast.makeText(getActivity(), shibieImageResult.getMsg(), Toast.LENGTH_SHORT).show();
                            photo.setEnabled(true);
                            safeCameraOpenInView(view);
                        } else {
                            Toast.makeText(getActivity(), shibieImageResult.getMsg(), Toast.LENGTH_SHORT).show();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("page", "shibie_success");
                            bundle.putSerializable("shibie_success", shibieImageResult);
                            message.setData(bundle);
                            activityHandler.sendMessage(message);
                            photo.setEnabled(true);
                            safeCameraOpenInView(view);
                        }
                        break;

                    default:
                        break;
                }

            }
        };
        leafModel.setHandler(mHandler);
    }

    private void getPreViewImage() {

        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                try {
                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                    if (image != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
                        Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

                        //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
                        rotateMyBitmap(bmp);
                        stream.close();
                    }
                } catch (Exception ex) {
                    Log.e("Sys", "Error:" + ex.getMessage());
                }
            }
        });
    }

    public void rotateMyBitmap(Bitmap bmp) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);


        releaseCameraAndPreview();

        yulan.setImageBitmap(nbmp2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        String uuid = sharedPreferences.getString("uuid", "uuid");

        leafModel.uploadImage(nbmp2, "recognize", uuid);

        photo.setEnabled(false);
    }

    ;

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void releaseCameraAndPreview() {
        mPreview = null;
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo:
                getPreViewImage();
                break;
            default:
                break;
        }
    }


    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }
}
