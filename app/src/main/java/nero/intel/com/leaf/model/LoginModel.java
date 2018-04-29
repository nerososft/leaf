package nero.intel.com.leaf.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.api.ApiClient;
import nero.intel.com.leaf.entity.Reg;
import nero.intel.com.leaf.entity.Result;
import nero.intel.com.leaf.entity.Sms;
import nero.intel.com.leaf.entity.Username;
import nero.intel.com.leaf.entity.uuid;

import static nero.intel.com.leaf.utils.TGJson.fromJson;

/**
 * Created by ny on 2018/3/6.
 */

public class LoginModel {
    private ApiClient apiClient;
    private Handler handler;



    public LoginModel() {
        this.apiClient = new ApiClient();
    }



    public void getSmsCode(String phone){
        System.out.println("URL "+apiClient.getSms(phone));
        apiClient.get(apiClient.getSms(phone), null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(responseString);
                System.out.println(throwable);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                Result<Sms> smsResult = fromJson(responseString,Sms.class);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","sms");
                bundle.putSerializable("sms",smsResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
    public void regByCode(String phone,String code,String bussinessid){
        System.out.println("URL "+apiClient.loginWithPhoneSms(phone,code,bussinessid));
        apiClient.get(apiClient.loginWithPhoneSms(phone,code,bussinessid), null, new TextHttpResponseHandler() {
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
                Result<Reg> smsResult = fromJson(responseString,Reg.class);

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("type","reg");
                bundle.putSerializable("reg",smsResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void setUserName(String UUID,String token,String username){
        System.out.println("URL "+apiClient.setUserName(UUID,token,username));
        apiClient.get(apiClient.setUserName(UUID,token,username), null, new TextHttpResponseHandler() {
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
                Result<Username> usernameResult = fromJson(responseString,Username.class);

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("type","setusername");
                bundle.putSerializable("setusername",usernameResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void setPassword(String UUID,String token,String password){
        System.out.println("URL "+apiClient.setPassword(UUID,token,password));
        apiClient.get(apiClient.setPassword(UUID,token,password), null, new TextHttpResponseHandler() {
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
                Result<uuid> uuidResult = fromJson(responseString,uuid.class);

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("type","setpassword");
                bundle.putSerializable("setpassword",uuidResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
    public void loginWithUsername(String username, String password) {
        System.out.println("URL "+apiClient.loginWithUsernamePassword(username,password));
        apiClient.get(apiClient.loginWithUsernamePassword(username,password), null, new TextHttpResponseHandler() {
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
                Result<Reg> usernameResult = fromJson(responseString,Reg.class);

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("type","loginusername");
                bundle.putSerializable("loginusername",usernameResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
    public void loginWithPhone(String phone, String password) {
        System.out.println("URL "+apiClient.loginWithPhonePassword(phone,password));
        apiClient.get(apiClient.loginWithPhonePassword(phone,password), null, new TextHttpResponseHandler() {
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
                Result<Reg> phoneResult = fromJson(responseString,Reg.class);

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("type","loginpassword");
                bundle.putSerializable("loginpassword",phoneResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
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
