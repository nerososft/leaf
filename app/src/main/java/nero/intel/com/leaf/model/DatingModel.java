package nero.intel.com.leaf.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.DatingList;
import nero.intel.com.leaf.entity.ImageUpload;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.ShareToDating;

import static nero.intel.com.leaf.utils.TGJson.fromJson;

/**
 * Created by ny on 2018/3/8.
 */

public class DatingModel {

    private ApiClient apiClient;
    private Handler handler;

    public DatingModel() {
        apiClient = new ApiClient();
    }


    public void shareToDaTing(String uuid,String token,String reco_uuid,String comment,String address){
        System.out.println("URL "+apiClient.shareToDaTing(uuid,token,reco_uuid));
        RequestParams params = new RequestParams();
        params.put("comment",comment);
        params.put("address",address);
        apiClient.post(apiClient.shareToDaTing(uuid,token,reco_uuid), params, new TextHttpResponseHandler() {
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
                Result<ShareToDating> imageUploadResult = fromJson(responseString,ShareToDating.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","share_dt");
                bundle.putSerializable("share_dt",imageUploadResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void listDaTing(String count, String page, final String action){
        System.out.println("URL "+apiClient.listDating(count, page));
        apiClient.get(apiClient.listDating(count, page), new TextHttpResponseHandler() {
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
                Result<DatingList> datingListResult = fromJson(responseString,DatingList.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","dt_list");
                bundle.putString("action",action);
                bundle.putSerializable("dt_list",datingListResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
