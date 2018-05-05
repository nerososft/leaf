package nero.intel.com.leaf.fragment.leaf;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.model.ImageModel;

/**
 * Created by ny on 2018/3/7.
 */

@SuppressLint("ValidFragment")
public class BoswerFragment extends Fragment implements View.OnClickListener {

    private Handler activityHandler;
    private Handler mHandler;
    ImageView bt_fanhui;

    WebView webView;

    String url;
    Context context;
    @SuppressLint("ValidFragment")
    public BoswerFragment(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boswer_fragment, container, false);

        init_view(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init_view(View view){
        bt_fanhui = view.findViewById(R.id.fanhui_shibie);
        webView = view.findViewById(R.id.web);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载
                return false;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                }

                return false;
            }

        });
        webView.loadUrl(url);
        bt_fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fanhui_shibie:
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("page","shibie");
                bundle.putSerializable("shibie",null);
                message.setData(bundle);
                activityHandler.sendMessage(message);
                break;
            default:break;
        }
    }
    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
