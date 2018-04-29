package nero.intel.com.leaf.api;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by ny on 2018/3/6.
 */

public class ApiClient extends AsyncHttpClient {



    //private final String HOST = "http://leaves.beiyang1895.com:8000/leaves/";
    private final String HOST = "http://172.24.73.72:8000/leaves/";
    //获取短信验证码，用于登录（注册）
    //http://leaves.beiyang1895.com/leaves/auth/smscode/[phone]{11}/
    public String getSms(String phone){
        return HOST+"auth/smscode/"+phone+"/";
    }

    //使用手机号和验证码登录
    //http://leaves.beiyang1895.com/leaves/auth/login/[phone]{11}/[code]{6}/[business_id]{32}/
    public String loginWithPhoneSms(String phone,String code,String bussinessId){
        return HOST+"auth/login/"+phone+"/"+code+"/"+bussinessId+"/";
    }
    //使用username和密码登录
    //http://leaves.beiyang1895.com/leaves/auth/login/[username]{6,10}/[password]{32}/
    public String loginWithUsernamePassword(String username,String password){
        return HOST+"auth/login/"+username+"/"+password+"/";
    }

    //使用手机号和密码登录：
    //http://leaves.beiyang1895.com/leaves/auth/login/[phone]{11}/[password]{32}/
    public String loginWithPhonePassword(String phone,String password){
        return HOST+"auth/login/"+phone+"/"+password+"/";
    }

    //设置username
    //http://leaves.beiyang1895.com/leaves/auth/set/username/[uuid]{32}/[token]{32}/[username]{6,10}/
    public String setUserName(String UUID,String token,String userName){
        return HOST+"auth/set/username/"+UUID+"/"+token+"/"+userName+"/";
    }

    //首次设置密码密码
    //http://leaves.beiyang1895.com/leaves/auth/set/password/[uuid]{32}/[token]{32}/[password]{32}/
    public String setPassword(String UUID,String token,String password){
        return HOST+"auth/set/password/"+UUID+"/"+token+"/"+password+"/";
    }


    //更新用户信息（昵称和头像）
    //http://leaves.beiyang1895.com/leaves/auth/update/info/[uuid]{32}/[token]{32}/[nickname]{∞}/[avatar]{32}/
    public String updateUserInfo(String UUID,String token,String nickname,String avatar){
        return HOST+"auth/update/info/"+UUID+"/"+token+"/"+nickname+"/"+avatar+"/";
    }

    //更新密码：
    //http://leaves.beiyang1895.com/leaves/auth/update/password/[uuid]{32}/[token]{32}/[old_password]{32}/[new_password]{32}/
    public String updatePassword(String UUID,String token,String oldPwd,String newPwd){
        return HOST+"auth/update/password/"+UUID+"/"+token+"/"+oldPwd+"/"+newPwd+"/";
    }



    /**
     * 上传图片
     * http://leaves.beiyang1895.com/leaves/img/upload/：
     * img : （必须）上传图片字段
     * uuid : （可选）用户uuid，修改头像时务必添加此字段，用户登录后上传图片务必添加此字段
     * type : （必须）图片类型，可选值为：avatar（头像）  recognize（识别图像） example（示例图像）
     * @return
     */
    public String uploadImage(){
        return HOST+"img/upload/";
    }

    //获取图片：
    //http://leaves.beiyang1895.com/leaves/img/get/[img_uuid]{32}/
    public String getImg(String UUID){
        return HOST+"img/get/"+UUID+"/";
    }

    //获取静态资源：
    //http://leaves.beiyang1895.com/leaves/img/get/[img_uuid]{32}/
    public String getStatic(String path){
        return HOST+"img/get/example/"+path+"/";
    }

    public String getSplash() {
        return HOST+"ad/";
    }

    //分享到大厅

    //http://leaves.beiyang1895.com/leaves/auth/[uuid]{32}/[token]{32}/share/[reco_uuid]{32}/
    /**
     * eco_uuid: 识别图片时返回的识别uuid
     * 可选字段：（使用POST方法传值）
     * comment: 对分享的描述
     address: 识别地点
     * @return
     */
    public String shareToDaTing(String uuid,String token,String reco_uuid){
        return HOST+"auth/"+uuid+"/"+token+"/share/"+reco_uuid+"/";
    }

    //获取默认头像
    //http://leaves.beiyang1895.com/leaves/img/get/avatar/
    public String getAvatar(String uuid){
        return HOST+"img/get/avatar/"+uuid+"/";
    }


    //获取用户信息：


    //识别图片：
    //http://leaves.beiyang1895.com/leaves/img/recognize/[img_uuid]{32}/
    public String shibieImage(String uuid){
        return HOST+"img/recognize/"+uuid+"/";
    }

    //获取广场信息：
    //http://leaves.beiyang1895.com/leaves/ground/[count]/[page]/
    public String  listDating(String count,String page){
        return HOST+"ground/"+count+"/"+page+"/";
    }

    //获取用户识别历史：

    public ApiClient() {

        super();
        this.setTimeout(30000);
    }


}
