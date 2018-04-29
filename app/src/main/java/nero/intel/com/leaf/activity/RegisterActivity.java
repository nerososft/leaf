package nero.intel.com.leaf.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.fragment.login.LoginWithPhoneFragment;
import nero.intel.com.leaf.fragment.register.SmsFragment;
import nero.intel.com.leaf.fragment.register.UserSetFragment;

/**
 * Created by ny on 2018/3/6.
 */

public class RegisterActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private Handler activityHandler;

    SmsFragment smsFragment;
    UserSetFragment userSetFragment;

    LoginWithPhoneFragment loginWithPhoneFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        smsFragment = new SmsFragment();
        userSetFragment = new UserSetFragment();
        loginWithPhoneFragment = new LoginWithPhoneFragment();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.register_fragment_container, loginWithPhoneFragment);
        fragmentTransaction.commit();

        set_handler();
    }

    private void set_handler() {
        activityHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("page")) {
                    case "next":
                        Result<Reg> regResult = (Result<Reg>) msg.getData().getSerializable("data");
                        System.out.println(regResult);
                        if(regResult.getData().getIs_new()) {
                            SharedPreferences pref = RegisterActivity.this.getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("islogin","login");
                            editor.putString("uuid",regResult.getData().getUuid());
                            editor.putString("token",regResult.getData().getToken());
                            editor.putString("phone",regResult.getData().getPhone());
                            editor.putString("nickname",regResult.getData().getNickname());
                            editor.putString("avatar",regResult.getData().getAvatar());
                            editor.commit();

                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.register_fragment_container, userSetFragment);
                            fragmentTransaction.commit();
                        }else{
                            SharedPreferences pref = RegisterActivity.this.getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("islogin","login");
                            editor.putString("uuid",regResult.getData().getUuid());
                            editor.putString("token",regResult.getData().getToken());
                            editor.putString("phone",regResult.getData().getPhone());
                            editor.putString("nickname",regResult.getData().getNickname());
                            editor.putString("avatar",regResult.getData().getAvatar());
                            editor.commit();

                            Intent intent =new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case "main":
                        Intent intent =new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case "login":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.register_fragment_container,loginWithPhoneFragment);
                        fragmentTransaction.commit();
                        break;
                    case "reg":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.register_fragment_container,smsFragment);
                        fragmentTransaction.commit();
                        break;
                    case "phonelogin":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.register_fragment_container,loginWithPhoneFragment);
                        fragmentTransaction.commit();
                        break;
                    default:
                            break;

                }
            }
        };

        smsFragment.setActivityHandler(activityHandler);
        userSetFragment.setActivityHandler(activityHandler);
        loginWithPhoneFragment.setActivityHandler(activityHandler);
    }

}
