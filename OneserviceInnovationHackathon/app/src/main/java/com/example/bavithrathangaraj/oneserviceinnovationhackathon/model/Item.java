package com.example.bavithrathangaraj.oneserviceinnovationhackathon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable{

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
    private Date datePosted;

    public Item(String pic, String name, String details, String location, String[] tags, Date datePosted) {
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

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
