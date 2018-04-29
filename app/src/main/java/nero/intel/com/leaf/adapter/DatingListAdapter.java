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
import nero.intel.com.leaf.entity.DatingList;
import nero.intel.com.leaf.entity.Leaf;
import nero.intel.com.leaf.model.ImageModel;

public class DatingListAdapter extends BaseAdapter {


    private List<DatingItem> mData;
    ImageModel imageModel;

    Handler mHandler;
    byte[] getimage;
    Integer index;

    List<Bitmap> bitmaps;
    List<Bitmap> logos;

    Handler fragmentHandler;
    private Context mContext;
    private List<Boolean> isGet;
    private List<Boolean> isGetLogos;


    public DatingListAdapter(Context mContext) {
        this.mData = new ArrayList<>();
        this.mContext = mContext;
        imageModel = new ImageModel();
        bitmaps = new ArrayList<>();
        logos = new ArrayList<>();
        isGet = new ArrayList<>();
        isGetLogos = new ArrayList<>();
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
                    case "getavatar":
                        getimage = msg.getData().getByteArray("getavatar");
                        index = msg.getData().getInt("index");
                        Bitmap bmp1 = BitmapFactory.decodeByteArray(getimage, 0, getimage.length);
                        logos.set(index,bmp1);

                        Message message1 = new Message();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("type","list_refreshed");
                        message1.setData(bundle1);
                        fragmentHandler.sendMessage(message1);
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.share_item,parent,false);
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


        final ImageView myLogo = convertView.findViewById(R.id.my_logo);
        if(!isGetLogos.get(position)) {
            String uuid = mData.get(position).getUserAvatar();
            imageModel.getAvatar(uuid,position);
            isGetLogos.set(position,true);
        }
        if(logos.get(position)!=null) {
            myLogo.setImageBitmap(logos.get(position));
        }

        final TextView rec_item_accuracy = convertView.findViewById(R.id.leafaccur);


        final TextView leafname = convertView.findViewById(R.id.leafname);
        leafname.setText(mData.get(position).getLeaveName());
        final TextView leafaccur = convertView.findViewById(R.id.leafaccur);
        leafaccur.setText(String.valueOf(Double.valueOf(mData.get(position).getAccuracy())*100.0)+"%");


        final TextView username = convertView.findViewById(R.id.u_username);
        username.setText(mData.get(position).getUserNickname());

        final TextView u_createtime = convertView.findViewById(R.id.u_createtime);
        u_createtime.setText(mData.get(position).getCreate_time());

        final TextView comment = convertView.findViewById(R.id.u_comment);
        if("".equals(mData.get(position).getComment())){
            comment.setText("无fuck说···");
        }else {
            comment.setText(mData.get(position).getComment());
        }

        return convertView;
    }

    public List<DatingItem> getmData() {
        return mData;
    }

    public void setData(List<DatingItem> mData) {
        this.mData = mData;
        bitmaps = new ArrayList<>();
        logos = new ArrayList<>();
        isGet = new ArrayList<>();
        isGetLogos = new ArrayList<>();

        for(int i = 0;i<mData.size();i++){
            bitmaps.add(null);
            logos.add(null);
            isGet.add(false);
            isGetLogos.add(false);
        }
    }
    public void addData(List<DatingItem> mData) {
        this.mData.addAll(mData);
        for(int i = 0;i<mData.size();i++){
            bitmaps.add(null);
            logos.add(null);
            isGet.add(false);
            isGetLogos.add(false);
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
