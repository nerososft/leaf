package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/7.
 */

public class Leaf implements Serializable {
    private String species_id;
    private String baike_url;
    private String name;
    private String description;
    private String example_images;
    private String genus;
    private String species;
    private String genus_id;
    private String accuracy;

    public Leaf(String species_id, String baike_url, String name, String description, String example_images, String genus, String species, String genus_id, String accuracy) {
        this.species_id = species_id;
        this.baike_url = baike_url;
        this.name = name;
        this.description = description;
        this.example_images = example_images;
        this.genus = genus;
        this.species = species;
        this.genus_id = genus_id;
        this.accuracy = accuracy;
    }

    public String getSpecies_id() {
        return species_id;
    }

    public void setSpecies_id(String species_id) {
        this.species_id = species_id;
    }

    public String getBaike_url() {
        return baike_url;
    }

    public void setBaike_url(String baike_url) {
        this.baike_url = baike_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample_images() {
        return example_images;
    }

    public void setExample_images(String example_images) {
        this.example_images = example_images;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGenus_id() {
        return genus_id;
    }

    public void setGenus_id(String genus_id) {
        this.genus_id = genus_id;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "Leaf{" +
                "species_id='" + species_id + '\'' +
                ", baike_url='" + baike_url + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", example_images='" + example_images + '\'' +
                ", genus='" + genus + '\'' +
                ", species='" + species + '\'' +
                ", genus_id='" + genus_id + '\'' +
                ", accuracy='" + accuracy + '\'' +
                '}';
    }
}
