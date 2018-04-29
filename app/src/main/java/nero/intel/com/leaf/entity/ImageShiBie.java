package nero.intel.com.leaf.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ny on 2018/3/7.
 */


public class ImageShiBie implements Serializable {

    private String img_uuid;
    private String  reco_uuid;
    private List<Leaf> result;


    public ImageShiBie(String img_uuid, String reco_uuid, List<Leaf> result) {
        this.img_uuid = img_uuid;
        this.reco_uuid = reco_uuid;
        this.result = result;
    }

    public String getImg_uuid() {
        return img_uuid;
    }

    public void setImg_uuid(String img_uuid) {
        this.img_uuid = img_uuid;
    }

    public String getReco_uuid() {
        return reco_uuid;
    }

    public void setReco_uuid(String reco_uuid) {
        this.reco_uuid = reco_uuid;
    }

    public List<Leaf> getResult() {
        return result;
    }

    public void setResult(List<Leaf> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ImageShiBie{" +
                "img_uuid='" + img_uuid + '\'' +
                ", reco_uuid='" + reco_uuid + '\'' +
                ", result=" + result +
                '}';
    }
}
