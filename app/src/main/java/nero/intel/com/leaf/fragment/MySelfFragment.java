package nero.intel.com.leaf.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.ImageUpload;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.model.ImageModel;

/**
 * Created by ny on 2018/3/6.
 */

@SuppressLint("ValidFragment")
public class MySelfFragment extends Fragment implements View.OnClickListener {

    private LinearLayout histroy;
    private LinearLayout about;

    private TextView user_name;
    private TextView user_phone;

    private ImageView imageView;
    private TextView abouttx;

    private Handler activityHandler;
    private Handler mHandler;

    private ImageModel imageModel;

    String uuid;
    String token;

    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ValidFragment")
    public MySelfFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_fragment, container, false);

        init_view(view);
        return view;
    }

    private void init_view(View view){
        about = view.findViewById(R.id.about_bt);
        histroy = view.findViewById(R.id.histroy);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
        uuid = sharedPreferences.getString("uuid", "");
        token = sharedPreferences.getString("token", "");
        String nickname = sharedPreferences.getString("nickname", "");
        String phone = sharedPreferences.getString("phone", "");

        user_name = view.findViewById(R.id.user_name);
        user_name.setText(nickname);
        user_phone = view.findViewById(R.id.user_phone);
        user_phone.setText(phone);


        abouttx = view.findViewById(R.id.about_tx);

        imageView = view.findViewById(R.id.user_avatar);
        imageModel = new ImageModel();



        about.setOnClickListener(this);
        histroy.setOnClickListener(this);
        abouttx.setOnClickListener(this);

        set_handler();
        imageModel.getAvatar(sharedPreferences.getString("avatar",""));
    }
    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(context, "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getavatar":
                        byte[] getimage1 = msg.getData().getByteArray("getavatar");
                        Bitmap bmp1 = BitmapFactory.decodeByteArray(getimage1, 0, getimage1.length);
                        imageView.setImageBitmap(bmp1);
                        break;
                    default:
                        break;
                }

            }
        };
        imageModel.setHandler(mHandler);
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_bt:
                Toast.makeText(context,"那棵树看起来生气了~ 你跟那棵树肯定是一伙的~",Toast.LENGTH_LONG).show();
                break;
            case R.id.histroy:
                Message message2 = new Message();
                Bundle bundle2 = new Bundle();
                bundle2.putString("page","histroy");
                message2.setData(bundle2);
                activityHandler.sendMessage(message2);
                break;
            case R.id.about_tx:
                Toast.makeText(context,"那棵树看起来生气了~ 你跟那棵树肯定是一伙的~",Toast.LENGTH_LONG).show();
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
