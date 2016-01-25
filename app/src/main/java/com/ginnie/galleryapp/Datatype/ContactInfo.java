package com.ginnie.galleryapp.Datatype;

/**
 * Created by su on 31/12/15.
 */
public class ContactInfo {
    public String id;
    public String details;
    public String location;
    public String added_on;

    public ContactInfo(String id, String details, String location, String added_on) {
        this.id = id;
        this.details = details;
        this.location = location;
        this.added_on = added_on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }
}