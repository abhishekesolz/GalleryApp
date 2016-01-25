package com.ginnie.galleryapp.Datatype;

/**
 * Created by su on 25/11/15.
 */
public class FollowAllitemRow {

    String team_list_id;
    String member_id;
    String lead_id;
    String image;
    String name;
    String phone;
    String location;
    String group_id;
    String dialog_boxid;
    String quickboxid;
    String group_creator_id;
    String group_creator_name;
    String group_creator_phone;
    String group_creator_image;
    String group_creator_quickboxid;


    public String getTeam_list_id() {
        return team_list_id;
    }

    public void setTeam_list_id(String team_list_id) {
        this.team_list_id = team_list_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getDialog_boxid() {
        return dialog_boxid;
    }

    public void setDialog_boxid(String dialog_boxid) {
        this.dialog_boxid = dialog_boxid;
    }

    public String getQuickboxid() {
        return quickboxid;
    }

    public void setQuickboxid(String quickboxid) {
        this.quickboxid = quickboxid;
    }

    public String getGroup_creator_id() {
        return group_creator_id;
    }

    public void setGroup_creator_id(String group_creator_id) {
        this.group_creator_id = group_creator_id;
    }

    public String getGroup_creator_name() {
        return group_creator_name;
    }

    public void setGroup_creator_name(String group_creator_name) {
        this.group_creator_name = group_creator_name;
    }

    public String getGroup_creator_phone() {
        return group_creator_phone;
    }

    public void setGroup_creator_phone(String group_creator_phone) {
        this.group_creator_phone = group_creator_phone;
    }

    public String getGroup_creator_image() {
        return group_creator_image;
    }

    public void setGroup_creator_image(String group_creator_image) {
        this.group_creator_image = group_creator_image;
    }

    public String getGroup_creator_quickboxid() {
        return group_creator_quickboxid;
    }

    public void setGroup_creator_quickboxid(String group_creator_quickboxid) {
        this.group_creator_quickboxid = group_creator_quickboxid;
    }

    public FollowAllitemRow(String team_list_id, String member_id, String lead_id, String image, String name, String phone, String location, String group_id
            , String dialog_boxid, String quickboxid, String group_creator_id, String group_creator_name, String group_creator_phone, String group_creator_image, String group_creator_quickboxid) {
        this.team_list_id = team_list_id;
        this.member_id = member_id;
        this.lead_id = lead_id;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.group_id = group_id;
        this.dialog_boxid=dialog_boxid;
        this.quickboxid=quickboxid;
        this.group_creator_id=group_creator_id;
        this.group_creator_name=group_creator_name;
        this.group_creator_phone=group_creator_phone;
        this.group_creator_image=group_creator_image;
        this.group_creator_quickboxid=group_creator_quickboxid;
    }


}
