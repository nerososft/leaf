package nero.intel.com.leaf.fragment.leaf;

import android.app.Fragment;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.adapter.RecognizeImgListAdapter;
import nero.intel.com.leaf.adapter.RecognizeListAdapter;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.ImageUpload;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.model.ImageModel;
import nero.intel.com.leaf.view.HorizontalListView;

/**
 * Created by ny on 2018/3/7.
 */

public class RecognizeFragment extends Fragment implements View.OnClickListener {

    private Handler activityHandler;
    private Handler mHandler;
    ImageView bt_fanhui;

    TextView name;
    TextView description;
    TextView genus;
    TextView species;
    TextView accuracy;

    ImageView recImg;
    Button baike;
    Button dating;

    ListView recList;
    HorizontalListView recImgList;

    Bundle data;
    Result<ImageShiBie> shi;

    ImageModel imageModel;

    byte[] getimage;

    RecognizeListAdapter recognizeListAdapter;
    RecognizeImgListAdapter recognizeImgListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recognize_fragment, container, false);

        init_view(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void init_view(View view){
        bt_fanhui = view.findViewById(R.id.fanhui_shibie);
        shi  = (Result<ImageShiBie>) data.getSerializable("shibie_success");

        name        = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        genus       = view.findViewById(R.id.genus);
        species     = view.findViewById(R.id.species);
        accuracy    = view.findViewById(R.id.accuracy);
        recImg  = view.findViewById(R.id.rec_img);
        baike       = view.findViewById(R.id.baike);
        dating = view.findViewById(R.id.dating);

        recImgList = view.findViewById(R.id.rec_example_img_list);
        recognizeImgListAdapter = new RecognizeImgListAdapter(shi.getData().getResult(),getActivity());
        recImgList.setAdapter(recognizeImgListAdapter);

        recList = view.findViewById(R.id.rec_list);
        recognizeListAdapter = new RecognizeListAdapter(shi.getData().getResult(),getActivity());
        recList.setAdapter(recognizeListAdapter);
        setListViewHeightBasedOnChildren(recList);



        imageModel = new ImageModel();
        set_handler();
        imageModel.getImg(shi.getData().getImg_uuid());

        name.setText(shi.getData().getResult().get(0).getName());
        description.setText(shi.getData().getResult().get(0).getDescription());
        genus.setText(shi.getData().getResult().get(0).getGenus());
        species.setText(shi.getData().getResult().get(0).getSpecies());
        accuracy.setText(String.valueOf(Double.valueOf(shi.getData().getResult().get(0).getAccuracy())*100.0)+"%");

        baike.setOnClickListener(this);
        dating.setOnClickListener(this);
        bt_fanhui.setOnClickListener(this);


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
                    case "getimage":
                        getimage = msg.getData().getByteArray("getimage");
                        Bitmap bmp = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        recImg.setImageBitmap(bmp);
                        break;
                    case "list_refreshed":
                        recognizeListAdapter.notifyDataSetChanged();
                        break;

                    case "list_img_refreshed":
                        recognizeImgListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }

            }
        };
        imageModel.setHandler(mHandler);
        recognizeListAdapter.setFragmentHandler(mHandler);
        recognizeImgListAdapter.setFragmentHandler(mHandler);

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
            case R.id.baike:
                Message message1 = new Message();
                Bundle bundle1 = new Bundle();
                bundle1.putString("page","baike");
                bundle1.putString("baike",shi.getData().getResult().get(0).getBaike_url());
                message1.setData(bundle1);
                activityHandler.sendMessage(message1);
                break;
            case R.id.dating:
                Message message2 = new Message();
                Bundle bundle2 = new Bundle();
                bundle2.putString("page","share");
                bundle2.putSerializable("share",shi.getData());
                message2.setData(bundle2);
                activityHandler.sendMessage(message2);
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

    public void setData(Bundle data) {
        this.data = data;
    }
}
