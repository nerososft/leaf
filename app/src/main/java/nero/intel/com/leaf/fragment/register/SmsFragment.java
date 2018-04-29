package nero.intel.com.leaf.fragment.register;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Sms;
import nero.intel.com.leaf.model.LoginModel;
import nero.intel.com.leaf.utils.AdvancedCountdownTimer;

import static android.content.ContentValues.TAG;

/**
 * Created by ny on 2018/3/6.
 */

public class SmsFragment extends Fragment implements View.OnClickListener {

    private Handler mHandler;
    private Handler activityHandler;
    LoginModel loginModel;

    EditText phoneEditText;
    EditText codeEditText;
    Button getSmsBt;
    Button regBt;

    Result<Sms> smsResult;
    Result<Reg> regResult;

    TextView logText;

    AdvancedCountdownTimer countdownTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reg_sms_fragment, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        phoneEditText = view.findViewById(R.id.reg_phone);
        codeEditText = view.findViewById(R.id.reg_code);
        getSmsBt = view.findViewById(R.id.reg_getcode);

        logText = view.findViewById(R.id.log);

        regBt = view.findViewById(R.id.reg);

        loginModel = new LoginModel();

        getSmsBt.setOnClickListener(this);
        regBt.setOnClickListener(this);

        logText.setOnClickListener(this);


        set_handler();
    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        regBt.setEnabled(true);
                        if(countdownTimer!=null) {
                            countdownTimer.cancel();
                        }
                        break;
                    case "sms":
                        smsResult = (Result<Sms>) msg.getData().getSerializable("sms");
                        if (smsResult.getCode() != 0) {
                            Toast.makeText(getActivity(), smsResult.getMsg(), Toast.LENGTH_SHORT).show();
                            regBt.setEnabled(true);
                            if(countdownTimer!=null) {
                                countdownTimer.cancel();
                            }
                        } else {
                            Toast.makeText(getActivity(), "验证码发送成功", Toast.LENGTH_SHORT).show();
                            getSmsBt.setEnabled(false);
                            startCustomCountDownTime(60);
                        }
                        break;

                    case "reg":
                        regResult = (Result<Reg>) msg.getData().getSerializable("reg");
                        if (regResult.getCode() != 0) {
                            Toast.makeText(getActivity(), regResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), regResult.getMsg(), Toast.LENGTH_SHORT).show();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("page", "next");
                            bundle.putSerializable("data", regResult);
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

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_getcode:
                loginModel.getSmsCode(phoneEditText.getText().toString());
                break;
            case R.id.reg:
                loginModel.regByCode(phoneEditText.getText().toString(), codeEditText.getText().toString(), smsResult.getData().getBusinessId());
                break;
            case R.id.log:
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("page", "login");
                message.setData(bundle);
                activityHandler.sendMessage(message);
                break;
            default:
                break;
        }
    }

    private void startCustomCountDownTime(long time) {
        if(countdownTimer!=null) {
            countdownTimer.cancel();
            countdownTimer = null;
        }


        countdownTimer = new AdvancedCountdownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
                Log.d(TAG, "onTick   " + millisUntilFinished / 1000);
                getSmsBt.setText(millisUntilFinished / 1000 + "秒后重新获取");
            }

            @Override
            public void onFinish() {
                getSmsBt.setText("获取验证码");
                getSmsBt.setEnabled(true);
            }
        };
        countdownTimer.start();
    }


    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }
}
