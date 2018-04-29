package nero.intel.com.leaf.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.adapter.DatingListAdapter;
import nero.intel.com.leaf.adapter.RecognizeImgListAdapter;
import nero.intel.com.leaf.adapter.RecognizeListAdapter;
import nero.intel.com.leaf.entity.DatingItem;
import nero.intel.com.leaf.entity.DatingList;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.ShareToDating;
import nero.intel.com.leaf.model.DatingModel;

/**
 * Created by ny on 2018/3/6.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class GroundFragment extends Fragment implements View.OnScrollChangeListener {


    private Handler activityHandler;
    private Handler mHandler;

    private DatingModel datingModel;


    Result<DatingList> datingListResult;

    List<DatingItem> datingItemList;

    ListView recList;
    DatingListAdapter datingListAdapter;

    LinearLayout loadmore;
    ScrollView maincon;
    TextView loadmore_text;
    Integer page = 1;
    Integer count = 8;
    Boolean isGet = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ground_fragment, container, false);

        init_view(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init_view(View view) {

        loadmore = view.findViewById(R.id.load_more);
        loadmore_text = view.findViewById(R.id.load_more_text);

        maincon = view.findViewById(R.id.main_con);
        maincon.setOnScrollChangeListener(this);

        datingItemList = new ArrayList<>();


        datingModel = new DatingModel();
        datingModel.listDaTing(new Integer(count).toString(), new Integer(page).toString(),"refresh");

        recList = view.findViewById(R.id.share_list);
        datingListAdapter = new DatingListAdapter(getActivity());
        recList.setAdapter(datingListAdapter);
        //setListViewHeightBasedOnChildren(recList);
        set_handler();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(getActivity(), "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;

                    case "dt_list":
                        datingListResult = (Result<DatingList>) msg.getData().getSerializable("dt_list");
                        System.out.println(datingListResult.toString());
                        if (datingListResult.getCode() != 0) {
                            Toast.makeText(getActivity(), datingListResult.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            if(msg.getData().getString("action").equals("refresh")) {
                                List<DatingItem> list = (List<DatingItem>) ((Result<DatingList>) msg.getData().getSerializable("dt_list")).getData().getResult();
                                datingListAdapter.setData(list);
                                datingListAdapter.notifyDataSetChanged();
                                loadmore_text.setText("下拉刷新···");
                                maincon.setSmoothScrollingEnabled(true);
                                isGet = true;
                                System.out.println("scroll");
                                maincon.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        maincon.smoothScrollTo(0, 180);
                                    }
                                });
                            }else if(msg.getData().getString("action").equals("nextpage")){
                                List<DatingItem> list = (List<DatingItem>) ((Result<DatingList>) msg.getData().getSerializable("dt_list")).getData().getResult();
                                datingListAdapter.addData(list);
                                datingListAdapter.notifyDataSetChanged();
                                isGet = true;
                            }

                        }
                        break;
                    case "list_refreshed":
                        datingListAdapter.notifyDataSetChanged();

                    default:
                        break;
                }

            }
        };
        datingModel.setHandler(mHandler);
        datingListAdapter.setFragmentHandler(mHandler);
    }


    @Override
    public void onPause() {
        super.onPause();
        page = 1;
    }


    public Handler getActivityHandler() {
        return activityHandler;
    }

    public void setActivityHandler(Handler activityHandler) {
        this.activityHandler = activityHandler;
    }

    public Result<DatingList> getDatingListResult() {
        return datingListResult;
    }

    public void setDatingListResult(Result<DatingList> datingListResult) {
        this.datingListResult = datingListResult;
    }

    @Override
    public void onScrollChange(View view, int l, int t, int oldl, int oldtop) {
        switch (view.getId()) {
            case R.id.main_con:
                if(t==0){
                    if(isGet) {
                        loadmore_text.setText("加载中···");
                        page = 1;
                        datingModel.listDaTing(new Integer(count).toString(), new Integer(1).toString(),"refresh");
                        isGet = false;
                    }
                }

                if (maincon.getChildAt(0).getHeight() - maincon.getHeight()
                        == maincon.getScrollY()-48){
                    if(isGet) {
                        page++;
                        datingModel.listDaTing(new Integer(count).toString(), new Integer(page).toString(), "nextpage");
                        isGet = false;
                    }
                }

                break;
            default:
                break;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


    @Override
    public void onResume() {
        super.onResume();
        page = 1;
    }

}
