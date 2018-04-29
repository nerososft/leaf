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
import nero.intel.com.leaf.entity.Leaf;
import nero.intel.com.leaf.model.ImageModel;

/**
 * Created by ny on 2018/3/8.
 */

public class RecognizeImgListAdapter  extends BaseAdapter {

    private List<Leaf> mData;
    private Context mContext;

    ImageModel imageModel;
    Handler mHandler;
    byte[] getimage;
    Integer index;
    List<Bitmap> bitmaps;
    private List<Boolean> isGet;
    Handler fragmentHandler;

    String example_img;
    String[] imgs;


    public RecognizeImgListAdapter(List<Leaf> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        imageModel = new ImageModel();
        example_img = mData.get(0).getExample_images();
        imgs = example_img.split(",");

        bitmaps = new ArrayList<>(imgs.length);
        isGet = new ArrayList<>(imgs.length);
        for(int i = 0;i<imgs.length;i++){
            bitmaps.add(null);
            isGet.add(false);
        }
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
                        bundle.putString("type","list_img_refreshed");
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
        return imgs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rec_item_example_img,parent,false);
        }

        final ImageView rec_item_img = convertView.findViewById(R.id.rec_example_img);

        if(!isGet.get(position)) {
            String path = imgs[position];
            imageModel.getStatic(path,position);
            isGet.set(position,true);
        }
        if(bitmaps.get(position)!=null) {
            rec_item_img.setImageBitmap(bitmaps.get(position));
        }

        return convertView;
    }

    public Handler getFragmentHandler() {
        return fragmentHandler;
    }

    public void setFragmentHandler(Handler fragmentHandler) {
        this.fragmentHandler = fragmentHandler;
    }
}
