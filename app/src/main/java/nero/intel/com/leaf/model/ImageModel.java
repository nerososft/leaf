package nero.intel.com.leaf.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.BinaryHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import nero.intel.com.leaf.api.ApiClient;

/**
 * Created by ny on 2018/3/7.
 */

public class ImageModel {
    private ApiClient apiClient;
    private Handler handler;

    public ImageModel() {
        apiClient = new ApiClient();
    }

    public void getImg(String imgUuid){
        System.out.println("URL "+apiClient.getImg(imgUuid));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getImg(imgUuid), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getimage");
                bundle.putByteArray("getimage",binaryData);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void getImg(String imgUuid, final Integer position){
        System.out.println("URL "+apiClient.getImg(imgUuid));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getImg(imgUuid), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getimage");
                bundle.putByteArray("getimage",binaryData);
                bundle.putInt("index",position);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    public void getAvatar(String imgUuid){
        System.out.println("URL "+apiClient.getAvatar(imgUuid));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getAvatar(imgUuid), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getavatar");
                bundle.putByteArray("getavatar",binaryData);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
    public void getAvatar(String imgUuid, final Integer position){
        System.out.println("URL "+apiClient.getAvatar(imgUuid));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getAvatar(imgUuid), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getavatar");
                bundle.putByteArray("getavatar",binaryData);
                bundle.putInt("index",position);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
    public void getStatic(String path, final Integer position){
        System.out.println("URL "+apiClient.getStatic(path));
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getStatic(path), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","getimage");
                bundle.putByteArray("getimage",binaryData);
                bundle.putInt("index",position);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
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

    public void getSplash() {
        System.out.println("URL "+apiClient.getSplash());
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","image" };
        apiClient.get(apiClient.getSplash(), null, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","splash");
                bundle.putByteArray("splash",binaryData);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                System.out.println(statusCode);
                System.out.println(error);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("type","error");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
}
