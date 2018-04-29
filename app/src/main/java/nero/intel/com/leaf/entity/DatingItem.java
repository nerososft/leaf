package nero.intel.com.leaf.entity;

import java.io.Serializable;

public class DatingItem implements Serializable{

            private String comment;
            private String species_id;
            private String uuid;
            private String reco_uuid;
            private String userNickname;
            private String love_count;
            private String create_time;
            private String userAvatar;
            private String address;
            private String img_uuid;
            private String user_uuid;
            private String leaveName;
            private String accuracy;


    public DatingItem() {
    }

    public DatingItem(String comment, String species_id, String uuid, String reco_uuid, String userNickname, String love_count, String create_time, String userAvatar, String address, String img_uuid, String user_uuid, String leaveName, String accuracy) {
        this.comment = comment;
        this.species_id = species_id;
        this.uuid = uuid;
        this.reco_uuid = reco_uuid;
        this.userNickname = userNickname;
        this.love_count = love_count;
        this.create_time = create_time;
        this.userAvatar = userAvatar;
        this.address = address;
        this.img_uuid = img_uuid;
        this.user_uuid = user_uuid;
        this.leaveName = leaveName;
        this.accuracy = accuracy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSpecies_id() {
        return species_id;
    }

    public void setSpecies_id(String species_id) {
        this.species_id = species_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReco_uuid() {
        return reco_uuid;
    }

    public void setReco_uuid(String reco_uuid) {
        this.reco_uuid = reco_uuid;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getLove_count() {
        return love_count;
    }

    public void setLove_count(String love_count) {
        this.love_count = love_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg_uuid() {
        return img_uuid;
    }

    public void setImg_uuid(String img_uuid) {
        this.img_uuid = img_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "DatingItem{" +
                "comment='" + comment + '\'' +
                ", species_id='" + species_id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", reco_uuid='" + reco_uuid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", love_count='" + love_count + '\'' +
                ", create_time='" + create_time + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", address='" + address + '\'' +
                ", img_uuid='" + img_uuid + '\'' +
                ", user_uuid='" + user_uuid + '\'' +
                ", leaveName='" + leaveName + '\'' +
                ", accuracy='" + accuracy + '\'' +
                '}';
    }
}
