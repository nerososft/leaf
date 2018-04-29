package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/6.
 */

public class Reg implements Serializable {
    private String username;
    private String uuid;
    private Boolean is_new;
    private String  phone;
    private String  token;
    private String avatar;
    private String  nickname;


    public Reg(String username, String uuid, Boolean is_new, String phone, String token, String avatar, String nickname) {
        this.username = username;
        this.uuid = uuid;
        this.is_new = is_new;
        this.phone = phone;
        this.token = token;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Reg{" +
                "username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                ", is_new=" + is_new +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", avatar='" + avatar +'\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
