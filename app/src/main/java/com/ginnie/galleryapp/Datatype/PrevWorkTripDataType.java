package com.ginnie.galleryapp.Datatype;

/**
 * Created by su on 22/8/15.
 */
public class PrevWorkTripDataType {


   String vacation_id;
    String vacation_name;
    String vacation_type;
    String vacation_start_date;
    String vacation_end_date;
    String vacation_end;

    public PrevWorkTripDataType(String vacation_id, String vacation_name, String vacation_type, String vacation_start_date, String vacation_end_date, String vacation_end) {
        this.vacation_id = vacation_id;
        this.vacation_name = vacation_name;
        this.vacation_type = vacation_type;
        this.vacation_start_date = vacation_start_date;
        this.vacation_end_date = vacation_end_date;
        this.vacation_end = vacation_end;
    }


    public String getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(String vacation_id) {
        this.vacation_id = vacation_id;
    }

    public String getVacation_name() {
        return vacation_name;
    }

    public void setVacation_name(String vacation_name) {
        this.vacation_name = vacation_name;
    }

    public String getVacation_type() {
        return vacation_type;
    }

    public void setVacation_type(String vacation_type) {
        this.vacation_type = vacation_type;
    }

    public String getVacation_start_date() {
        return vacation_start_date;
    }

    public void setVacation_start_date(String vacation_start_date) {
        this.vacation_start_date = vacation_start_date;
    }

    public String getVacation_end_date() {
        return vacation_end_date;
    }

    public void setVacation_end_date(String vacation_end_date) {
        this.vacation_end_date = vacation_end_date;
    }

    public String getVacation_end() {
        return vacation_end;
    }

    public void setVacation_end(String vacation_end) {
        this.vacation_end = vacation_end;
    }
}
