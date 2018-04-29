package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/7.
 */

public class ImageUpload implements Serializable {

   private String type;
   private String user_uuid;
   private String uuid;

    public ImageUpload(String type, String user_uuid, String uuid) {
        this.type = type;
        this.user_uuid = user_uuid;
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ImageUpload{" +
                "type='" + type + '\'' +
                ", user_uuid='" + user_uuid + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
