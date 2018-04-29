package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/8.
 */

public class ShareToDating implements Serializable {

    private String reco_uuid;
    private String user_uuid;
    private String  share_uuid;

    public ShareToDating(String reco_uuid, String user_uuid, String share_uuid) {
        this.reco_uuid = reco_uuid;
        this.user_uuid = user_uuid;
        this.share_uuid = share_uuid;
    }

    public String getReco_uuid() {
        return reco_uuid;
    }

    public void setReco_uuid(String reco_uuid) {
        this.reco_uuid = reco_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getShare_uuid() {
        return share_uuid;
    }

    public void setShare_uuid(String share_uuid) {
        this.share_uuid = share_uuid;
    }

    @Override
    public String toString() {
        return "ShareToDating{" +
                "reco_uuid='" + reco_uuid + '\'' +
                ", user_uuid='" + user_uuid + '\'' +
                ", share_uuid='" + share_uuid + '\'' +
                '}';
    }
}
