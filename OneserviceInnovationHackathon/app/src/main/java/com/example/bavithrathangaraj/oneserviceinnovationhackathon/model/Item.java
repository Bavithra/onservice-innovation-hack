package com.example.bavithrathangaraj.oneserviceinnovationhackathon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable{

    @SerializedName("_id")
    private String id;
    @SerializedName("pic")
    private String pic;
    @SerializedName("name")
    private String name;
    @SerializedName("details")
    private String details;
    @SerializedName("location")
    private String location;
    @SerializedName("tags")
    private String[] tags;
    @SerializedName("datePosted")
    private String datePosted;
    @SerializedName("category")
    private String category;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private String status;

    public Item(String pic, String name, String details, String location, String[] tags, String datePosted) {
        this.pic = pic;
        this.name = name;
        this.details = details;
        this.location = location;
        this.tags = tags;
        this.datePosted = datePosted;
    }

    public Item() {

    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
