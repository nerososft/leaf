package nero.intel.com.leaf.entity;

import java.io.Serializable;

public class HistroyItem implements Serializable {
            private String species_id;
            private String img_uuid;
            private String reco_uuid;
            private String name;
            private String accuracy;


    public HistroyItem() {
    }

    public HistroyItem(String species_id, String img_uuid, String reco_uuid, String name, String accuracy) {
        this.species_id = species_id;
        this.img_uuid = img_uuid;
        this.reco_uuid = reco_uuid;
        this.name = name;
        this.accuracy = accuracy;
    }

    public String getSpecies_id() {
        return species_id;
    }

    public void setSpecies_id(String species_id) {
        this.species_id = species_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "HistroyItem{" +
                "species_id='" + species_id + '\'' +
                ", img_uuid='" + img_uuid + '\'' +
                ", reco_uuid='" + reco_uuid + '\'' +
                ", name='" + name + '\'' +
                ", accuracy='" + accuracy + '\'' +
                '}';
    }
}
