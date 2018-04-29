package nero.intel.com.leaf.fragment.login;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Sms;
import nero.intel.com.leaf.entity.Username;
import nero.intel.com.leaf.model.LoginModel;
import nero.intel.com.leaf.utils.MD5;

/**
 * Created by ny on 2018/3/6.
 */

public class LoginWithPhoneFragment extends Fragment implements View.OnClickListener {

    private Handler mHandler;
    private Handler activityHandler;
    LoginModel loginModel;

    TextView regBt;

    EditText phone;
    EditText pwd;
    Button login;

    Result<Reg> phoneResult;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_phone_fragment, container, false);
        loginModel = new LoginModel();
        initView(view);
        return view;
    }

    private void initView(View view) {
        regBt = view.findViewById(R.id.login_reg);

        phone = view.findViewById(R.id.ET_login_phone);
        pwd = view.findViewById(R.id.ET_login_pwd);
        login = view.findViewById(R.id.bt_phone_login);


        set_handler();

        regBt.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "loginusername":
                        phoneResult = (Result<Reg>) msg.getData().getSerializable("loginusername");
                        System.out.println(phoneResult);
                        if (phoneResult.getCode() != 0) {
                            Toast.makeText(getActivity(), phoneResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("page", "next");
                            bundle.putSerializable("data",phoneResult);
                            message.setData(bundle);
                            activityHandler.sendMessage(message);
                        }
                        break;
                    default:
                        break;
                }


            }
        };
        loginModel.setHandler(mHandler);
    }


    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_reg:
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("page", "reg");
                message.setData(bundle);
                activityHandler.sendMessage(message);
                break;
            case R.id.bt_phone_login:
                loginModel.loginWithUsername(phone.getText().toString(), MD5.getMD5(pwd.getText().toString()));
                break;
            default:
                break;
        }
    }
}
