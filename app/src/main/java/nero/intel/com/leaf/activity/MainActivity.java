package nero.intel.com.leaf.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.ShareToDating;
import nero.intel.com.leaf.fragment.GroundFragment;
import nero.intel.com.leaf.fragment.LeafFragment;
import nero.intel.com.leaf.fragment.MySelfFragment;
import nero.intel.com.leaf.fragment.leaf.BoswerFragment;
import nero.intel.com.leaf.fragment.leaf.RecognizeFragment;
import nero.intel.com.leaf.fragment.leaf.ShareFragment;
import nero.intel.com.leaf.model.LoginModel;
import nero.intel.com.leaf.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int bottom_text_color = 0xffa0a0a0;
    int bottom_text_color_active = 0xff1296db;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    LeafFragment leafFragment;
    GroundFragment groundFragment;
    MySelfFragment mySelfFragment;
    ShareFragment shareFragment;

    RecognizeFragment recognizeFragment;
    BoswerFragment boswerFragment;

    LinearLayout bottomButtonLeaf;
    LinearLayout bottomButtonGround;
    LinearLayout bottomButtonSelf;

    ImageView bottomButtonLeafImg;
    ImageView bottomButtonGroundImg;
    ImageView bottomButtonSelfImg;

    TextView bottomButtonLeafText;
    TextView bottomButtonGroundText;
    TextView bottomButtonSelfText;


    private Handler activityHandler;

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI21();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperAPI19();
        }


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        initFragment();
        initView();


        //申请权限
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);


        fragmentTransaction.replace(R.id.fragment_container, leafFragment);
        fragmentTransaction.commit();


        if(!is_login()){
            Intent intent =new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0){
            result = getResources().getDimensionPixelSize(resId);
        }
        return result;
    }
    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight();
        int statusColor = getResources().getColor(R.color.colorAccent);

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null &&
                mTopView.getLayoutParams().height == statusBarHeight) {
            //避免重复添加 View
            mTopView.setBackgroundColor(statusColor);
            return;
        }
        //使 ChildView 预留空间
        if (mTopView != null) {
            ViewCompat.setFitsSystemWindows(mTopView, true);
        }

        //添加假 View
        mTopView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(statusColor);
        mContentView.addView(mTopView, 0, lp);
    }

    private void setStatusBarUpperAPI21(){
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        //由于setStatusBarColor()这个API最低版本支持21, 本人的是15,所以如果要设置颜色,自行到style中通过配置文件设置
//        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {

        bottomButtonLeaf = (LinearLayout) this.findViewById(R.id.bottom_bt_leaf);
        bottomButtonSelf = (LinearLayout) this.findViewById(R.id.bottom_bt_self);
        bottomButtonGround = (LinearLayout)this.findViewById(R.id.bottom_bt_ground);


        bottomButtonLeafImg = (ImageView) this.findViewById(R.id.bottom_bt_leaf_img);
        bottomButtonGroundImg = (ImageView) this.findViewById(R.id.bottom_bt_ground_img);
        bottomButtonSelfImg = (ImageView) this.findViewById(R.id.bottom_bt_self_img);


        bottomButtonLeafText = (TextView) this.findViewById(R.id.bottom_bt_leaf_text);
        bottomButtonGroundText = (TextView) this.findViewById(R.id.bottom_bt_ground_text);
        bottomButtonSelfText = (TextView) this.findViewById(R.id.bottom_bt_self_text);


        bottomButtonLeaf.setOnClickListener(this);
        bottomButtonSelf.setOnClickListener(this);
        bottomButtonGround.setOnClickListener(this);


        bottomButtonLeafImg.setImageDrawable(getResources().getDrawable(R.drawable.leaf_blue));
        bottomButtonLeafText.setTextColor(bottom_text_color_active);

        set_handler();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void set_handler() {
        activityHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("page")) {
                    case "shibie_success":
                        recognizeFragment.setData(msg.getData());
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,recognizeFragment);
                        fragmentTransaction.commit();
                        break;

                    case "baike":
                        System.out.println(msg.getData().getString("baike"));
                        boswerFragment.setUrl(msg.getData().getString("baike"));
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,boswerFragment);
                        fragmentTransaction.commit();
                        break;
                    case "shibie":
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,leafFragment);
                        fragmentTransaction.commit();
                        break;
                    case "share":
                        ImageShiBie imageShiBie = (ImageShiBie) msg.getData().getSerializable("share");
                        fragmentTransaction = fragmentManager.beginTransaction();
                        shareFragment.setImageShiBie(imageShiBie);
                        fragmentTransaction.replace(R.id.fragment_container,shareFragment);
                        fragmentTransaction.commit();
                        break;
                    case "dating":
                        ShareToDating shareToDating = (ShareToDating) msg.getData().getSerializable("dating");
                        fragmentTransaction = fragmentManager.beginTransaction();
                        groundFragment.setPage(1);
                        fragmentTransaction.replace(R.id.fragment_container,groundFragment);
                        fragmentTransaction.commit();
                        break;
                    default:
                        break;

                }
            }
        };
        leafFragment.setActivityHandler(activityHandler);
        recognizeFragment.setActivityHandler(activityHandler);
        boswerFragment.setActivityHandler(activityHandler);
        shareFragment.setActivityHandler(activityHandler);
        groundFragment.setActivityHandler(activityHandler);
    }


    void bottomButtonClear(){
        bottomButtonLeafImg.setImageDrawable(getResources().getDrawable(R.drawable.leaf_gray));
        bottomButtonGroundImg.setImageDrawable(getResources().getDrawable(R.drawable.ground_gray));
        bottomButtonSelfImg.setImageDrawable(getResources().getDrawable(R.drawable.my_gray));

        bottomButtonLeafText.setTextColor(bottom_text_color);
        bottomButtonGroundText.setTextColor(bottom_text_color);
        bottomButtonSelfText.setTextColor(bottom_text_color);

    }
    private void initFragment() {
        leafFragment  = new LeafFragment();
        groundFragment = new GroundFragment();
        mySelfFragment = new MySelfFragment();
        recognizeFragment = new RecognizeFragment();
        boswerFragment = new BoswerFragment();
        shareFragment = new ShareFragment();
    }

    @Override
    public void onClick(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.bottom_bt_leaf:
                fragmentTransaction.replace(R.id.fragment_container,leafFragment);
                bottomButtonClear();
                bottomButtonLeafImg.setImageDrawable(getResources().getDrawable(R.drawable.leaf_blue));
                bottomButtonLeafText.setTextColor(bottom_text_color_active);
                break;
            case R.id.bottom_bt_ground:
                fragmentTransaction.replace(R.id.fragment_container,groundFragment);
                bottomButtonClear();
                bottomButtonGroundImg.setImageDrawable(getResources().getDrawable(R.drawable.ground_blue));
                bottomButtonGroundText.setTextColor(bottom_text_color_active);

                break;
            case R.id.bottom_bt_self:
                fragmentTransaction.replace(R.id.fragment_container,mySelfFragment);
                bottomButtonClear();
                bottomButtonSelfImg.setImageDrawable(getResources().getDrawable(R.drawable.my_blue));
                bottomButtonSelfText.setTextColor(bottom_text_color_active);

                break;
            default:break;
        }
        fragmentTransaction.commit();
    }

    public boolean is_login() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String islogin =sharedPreferences.getString("islogin", "");

        //使用toast信息提示框显示信息
        if(islogin.equals("")){
            return false;
        }else {
            return true;
        }
    }
}
