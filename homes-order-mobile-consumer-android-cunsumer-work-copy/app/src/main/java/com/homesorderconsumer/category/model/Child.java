package com.homesorderconsumer.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class Child implements Serializable {

    @SerializedName("id")
    @Expose
    private String id="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("nameAR")
    @Expose
    private String nameAR="";

    @SerializedName("image")
    @Expose
    private String image="";

    @SerializedName("child")
    @Expose
    private List<Child> child=new ArrayList<>();

    @SerializedName("adds")
    @Expose
    private List<Adds> adds=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public List<Adds> getAdds() {
        return adds;
    }

    public void setAdds(List<Adds> adds) {
        this.adds = adds;
    }
}
