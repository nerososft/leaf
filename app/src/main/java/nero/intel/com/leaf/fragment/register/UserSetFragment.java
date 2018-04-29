package nero.intel.com.leaf.fragment.register;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.DigestException;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Sms;
import nero.intel.com.leaf.entity.Username;
import nero.intel.com.leaf.entity.uuid;
import nero.intel.com.leaf.model.LoginModel;
import nero.intel.com.leaf.utils.MD5;

/**
 * Created by ny on 2018/3/6.
 */

public class UserSetFragment extends Fragment implements View.OnClickListener {

    private Handler mHandler;
    private Handler activityHandler;
    LoginModel loginModel;

    EditText nickNameeditText;
    EditText pwdNameeditText;
    EditText rpwdNameeditText;
    Button registerBt;

    Result<uuid> uuidResult;
    Result<Username> usernameResult;

    Boolean setU = false, setP = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reg_userset_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        nickNameeditText = view.findViewById(R.id.ET_nickname);
        pwdNameeditText = view.findViewById(R.id.ET_pwd);
        registerBt = view.findViewById(R.id.bt_reg);

        loginModel = new LoginModel();

        registerBt.setOnClickListener(this);

        set_handler();
    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "setusername":
                        usernameResult = (Result<Username>) msg.getData().getSerializable("setusername");
                        if (usernameResult.getCode() != 0) {
                            Toast.makeText(getActivity(), usernameResult.getMsg() + ", 请重试", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), usernameResult.getMsg(), Toast.LENGTH_SHORT).show();
                            setU = true;
                            if (setU && setP) {
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("page", "main");
                                bundle.putSerializable("data", usernameResult);
                                message.setData(bundle);
                                activityHandler.sendMessage(message);
                            }
                        }
                        break;
                    case "setpassword":
                        uuidResult = (Result<uuid>) msg.getData().getSerializable("setpassword");
                        if (uuidResult.getCode() != 0) {
                            Toast.makeText(getActivity(), uuidResult.getMsg() + ", 请重试", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), uuidResult.getMsg(), Toast.LENGTH_SHORT).show();
                            setP = true;
                            if (setU && setP) {
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("page", "main");
                                bundle.putSerializable("data", uuidResult);
                                message.setData(bundle);
                                activityHandler.sendMessage(message);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        loginModel.setHandler(mHandler);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_reg:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
                String uuid = sharedPreferences.getString("uuid", "");
                String token = sharedPreferences.getString("token", "");

                if((!"".equals(nickNameeditText.getText().toString()))&&(nickNameeditText.getText().toString().length()==0)) {
                    loginModel.setUserName(uuid, token, nickNameeditText.getText().toString());
                }else{
                    setU = true;
                }
                loginModel.setPassword(uuid, token, MD5.getMD5(pwdNameeditText.getText().toString()));

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
