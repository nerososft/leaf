package nero.intel.com.leaf.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.DatingList;
import nero.intel.com.leaf.entity.HistoryList;
import nero.intel.com.leaf.entity.HistroyItem;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.ShareToDating;

import static nero.intel.com.leaf.utils.TGJson.fromJson;

/**
 * Created by ny on 2018/3/8.
 */

public class HistoryModel {

    private ApiClient apiClient;
    private Handler handler;

    public HistoryModel() {
        apiClient = new ApiClient();
    }

    public void listHistory(String uuid,String token,Integer count, Integer page, final String action){
        System.out.println("URL "+apiClient.listHistroy(uuid,token,count, page));
        apiClient.get(apiClient.listHistroy(uuid,token,count, page), new TextHttpResponseHandler() {
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
                Result<HistoryList> datingListResult = fromJson(responseString,HistoryList.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","hs_list");
                bundle.putString("action",action);
                bundle.putSerializable("hs_list",datingListResult);
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
