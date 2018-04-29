package nero.intel.com.leaf.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.model.ImageModel;
import nero.intel.com.leaf.utils.AdvancedCountdownTimer;

import static android.content.ContentValues.TAG;
/**
 * Created by ny on 2018/3/6.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jump;
    AdvancedCountdownTimer countdownTimer;
    ImageModel imageModel;

    private Handler handler;

    private LinearLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        splash = findViewById(R.id.splash);

        jump = findViewById(R.id.jump);
        jump.setOnClickListener(this);
        startCustomCountDownTime(5);

        imageModel = new ImageModel();
        set_handler();

        imageModel.getSplash();
    }
    public void set_handler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getApplicationContext(),"网络开小差了...",Toast.LENGTH_SHORT).show();
                        break;
                    case "splash":
                        byte[] getimage = msg.getData().getByteArray("splash");
                        Bitmap bmp = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        Drawable d = new BitmapDrawable(getResources(), bmp);
                        splash.setBackground(d);
                        break;
                    default:
                        break;
                }
            }
        };
        imageModel.setHandler(handler);
    }

    private void startCustomCountDownTime(long time) {
        countdownTimer = new AdvancedCountdownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
                Log.d(TAG, "onTick   " + millisUntilFinished / 1000);
                jump.setText("跳过 ("+millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                jump.setText("跳过");
                Intent intent =new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        countdownTimer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.jump:

                Intent intent =new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                countdownTimer.cancel();
                break;
            default:break;
        }
    }
}
