package nero.intel.com.leaf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nero.intel.com.leaf.R;
import nero.intel.com.leaf.entity.DatingItem;
import nero.intel.com.leaf.entity.HistroyItem;
import nero.intel.com.leaf.model.ImageModel;

public class HistoryListAdapter extends BaseAdapter {


    private List<HistroyItem> mData;
    ImageModel imageModel;

    Handler mHandler;
    byte[] getimage;
    Integer index;

    List<Bitmap> bitmaps;

    Handler fragmentHandler;
    private Context mContext;
    private List<Boolean> isGet;


    public HistoryListAdapter(Context mContext) {
        this.mData = new ArrayList<>();
        this.mContext = mContext;
        imageModel = new ImageModel();
        bitmaps = new ArrayList<>();
        isGet = new ArrayList<>();
        set_handler();
    }
    private void set_handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("type")) {
                    case "error":
                        Toast.makeText(mContext, "网络似乎出问题了...", Toast.LENGTH_SHORT).show();
                        break;
                    case "getimage":
                        getimage = msg.getData().getByteArray("getimage");
                        index = msg.getData().getInt("index");
                        Bitmap bmp = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        bitmaps.set(index,bmp);

                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("type","list_refreshed");
                        message.setData(bundle);
                        fragmentHandler.sendMessage(message);
                        break;

                    default:
                        break;
                }

            }
        };
        imageModel.setHandler(mHandler);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView ==null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_item,parent,false);
        }

        final ImageView rec_item_img = convertView.findViewById(R.id.leaf_img);
        if(!isGet.get(position)) {
            String uuid = mData.get(position).getImg_uuid();
            imageModel.getImg(uuid,position);
            isGet.set(position,true);
        }
        if(bitmaps.get(position)!=null) {
            rec_item_img.setImageBitmap(bitmaps.get(position));
        }

        final TextView rec_item_accuracy = convertView.findViewById(R.id.leafaccur);
        final TextView leafname = convertView.findViewById(R.id.leafname);
        leafname.setText(mData.get(position).getName());
        final TextView leafaccur = convertView.findViewById(R.id.leafaccur);
        leafaccur.setText(String.valueOf(Double.valueOf(mData.get(position).getAccuracy())*100.0)+"%");


        return convertView;
    }

    public List<HistroyItem> getmData() {
        return mData;
    }

    public void setData(List<HistroyItem> mData) {
        this.mData = mData;
        bitmaps = new ArrayList<>();
        isGet = new ArrayList<>();
        for(int i = 0;i<mData.size();i++){
            bitmaps.add(null);
            isGet.add(false);
        }
    }
    public void addData(List<HistroyItem> mData) {
        this.mData.addAll(mData);
        for(int i = 0;i<mData.size();i++){
            bitmaps.add(null);
            isGet.add(false);
        }
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public Handler getFragmentHandler() {
        return fragmentHandler;
    }

    public void setFragmentHandler(Handler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
