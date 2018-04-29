package nero.intel.com.leaf.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.ImageShiBie;
import nero.intel.com.leaf.entity.ImageUpload;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Sms;
import nero.intel.com.leaf.utils.Bitmap2File;

import static nero.intel.com.leaf.utils.TGJson.fromJson;

/**
 * Created by ny on 2018/3/7.
 */

public class LeafModel {
    private ApiClient apiClient;
    private Handler handler;


    public void uploadImage(Bitmap bitmap,String type,String uuid){
        System.out.println("URL "+apiClient.uploadImage());
        RequestParams params = new RequestParams();
        params.put("type",type);
        params.put("uuid",uuid);
        File file = Bitmap2File.saveBitmapFile(bitmap, Environment.getExternalStorageDirectory()+"/leaf_tmp.jpg");
        if(file!=null) {
            try {
                params.put("img", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        apiClient.post(apiClient.uploadImage(), params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                Result<ImageUpload> imageUploadResult = fromJson(responseString,ImageUpload.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","uploadimage");
                bundle.putSerializable("uploadimage",imageUploadResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void shibieImage(String uuid) {
        System.out.println("URL "+apiClient.shibieImage(uuid));
        apiClient.get(apiClient.shibieImage(uuid), null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                Result<ImageShiBie> imageImageShiBieResult = fromJson(responseString,ImageShiBie.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","shibieimage");
                bundle.putSerializable("shibieimage",imageImageShiBieResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }



    public LeafModel() {
        this.apiClient = new ApiClient();
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


}
