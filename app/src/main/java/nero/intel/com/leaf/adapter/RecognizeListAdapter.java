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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.R;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.Leaf;
import nero.intel.com.leaf.model.ImageModel;

/**
 * Created by ny on 2018/3/7.
 */

public class RecognizeListAdapter extends BaseAdapter {

    private List<Leaf> mData;

    ImageModel imageModel;
    Handler mHandler;
    byte[] getimage;
    Integer index;

    List<Bitmap> bitmaps;
    private List<Boolean> isGet;

    Handler fragmentHandler;


    private Context mContext;

    public RecognizeListAdapter(List<Leaf> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        imageModel = new ImageModel();
        bitmaps = new ArrayList<>(mData.size());
        isGet = new ArrayList<>(mData.size());
        for(int i = 0;i<mData.size();i++){
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.recognize_list_item,parent,false);
        }
        final ImageView rec_item_img = convertView.findViewById(R.id.rec_item_img);

        if(!isGet.get(position)) {
            String path = mData.get(position).getExample_images().split(",")[0];
            imageModel.getStatic(path,position);
            isGet.set(position,true);
        }
        if(bitmaps.get(position)!=null) {
            rec_item_img.setImageBitmap(bitmaps.get(position));
        }

        final TextView rec_item_accuracy = convertView.findViewById(R.id.rec_item_accuracy);

        rec_item_accuracy.setText(String.valueOf(Double.valueOf(mData.get(position).getAccuracy())*100.0)+"%");

        final TextView rec_item_genus = convertView.findViewById(R.id.rec_item_genus);
        rec_item_genus.setText(mData.get(position).getGenus());
        final TextView rec_item_name = convertView.findViewById(R.id.rec_item_name);
        rec_item_name.setText(mData.get(position).getName());
        final TextView rec_item_species = convertView.findViewById(R.id.rec_item_species);
        rec_item_species.setText(mData.get(position).getSpecies());
        final ImageView rec_item_go = convertView.findViewById(R.id.rec_item_go);

        rec_item_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,mData.get(position).getSpecies_id(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
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
}
