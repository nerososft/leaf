package nero.intel.com.leaf.fragment.leaf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.ShareToDating;
import nero.intel.com.leaf.model.DatingModel;
import nero.intel.com.leaf.model.ImageModel;

import static nero.intel.com.leaf.utils.TGJson.fromJson;

/**
 * Created by ny on 2018/3/8.
 */

public class ShareFragment extends Fragment implements View.OnClickListener {

    private Handler activityHandler;
    private Handler mHandler;

    private Button btShare;
    private TextView textViewMyName;
    private ImageView myAvatar;

    private ImageView MyPic;
    private TextView iWantSay;


    private String img_uuid;
    private ImageShiBie imageShiBie;

    String uuid;
    String token;
    Result<ShareToDating> imageUploadResult;

    private ImageModel imageModel;
    private DatingModel datingModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_fragment, container, false);

        init_view(view);
        return view;
    }


    private void init_view(View view){
        btShare = view.findViewById(R.id.i_want_share);
        textViewMyName = view.findViewById(R.id.my_name);
        myAvatar = view.findViewById(R.id.my_avatar);
        MyPic = view.findViewById(R.id.uploaded_img);
        iWantSay = view.findViewById(R.id.i_want_say);
        btShare.setOnClickListener(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        uuid = sharedPreferences.getString("uuid", "");
        token = sharedPreferences.getString("token", "");
        String nickname = sharedPreferences.getString("nickname", "");

        textViewMyName.setText(nickname);


        imageModel = new ImageModel();
        datingModel = new DatingModel();

        set_handler();
        //获取当前识别的图片
        imageModel.getImg(imageShiBie.getImg_uuid());
        imageModel.getAvatar(sharedPreferences.getString("avatar",""));
    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getimage":
                        byte[] getimage = msg.getData().getByteArray("getimage");
                        Bitmap bmp = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        MyPic.setImageBitmap(bmp);
                        break;
                    case "getavatar":
                        byte[] getimage1 = msg.getData().getByteArray("getavatar");
                        Bitmap bmp1 = BitmapFactory.decodeByteArray(getimage1, 0, getimage1.length);
                        myAvatar.setImageBitmap(bmp1);
                        break;
                    case "share_dt":
                        imageUploadResult = (Result<ShareToDating>) msg.getData().getSerializable("share_dt");
                        System.out.println(imageUploadResult.toString());
                        if (imageUploadResult.getCode() != 0) {
                            Toast.makeText(getActivity(), imageUploadResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Message message2 = new Message();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("page","dating");
                            bundle2.putSerializable("dating",imageUploadResult.getData());
                            message2.setData(bundle2);
                            activityHandler.sendMessage(message2);
                        }

                        break;
                    default:
                        break;
                }

            }
        };
        imageModel.setHandler(mHandler);
        datingModel.setHandler(mHandler);
    }
    @Override
    public void onPause() {
        super.onPause();
    }


    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.i_want_share:
                datingModel.shareToDaTing(uuid,token,imageShiBie.getReco_uuid(),iWantSay.getText().toString(),"中国");
                break;
            default:
                break;
        }
    }


    public String getImg_uuid() {
        return img_uuid;
    }

    public void setImg_uuid(String img_uuid) {
        this.img_uuid = img_uuid;
    }

    public ImageShiBie getImageShiBie() {
        return imageShiBie;
    }

    public void setImageShiBie(ImageShiBie imageShiBie) {
        this.imageShiBie = imageShiBie;
    }
}
