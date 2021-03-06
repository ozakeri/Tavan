package com.example.tavanyab.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CHILD".
 */
@Entity
public class Child {

    @Id(autoincrement = true)
    private Long id;
    private int child_id;
    private String first_name;
    private String last_name;
    private String birth_date;
    private String date_creation;
    private String doctor_name;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Child() {
    }

    public Child(Long id) {
        this.id = id;
    }

    @Generated
    public Child(Long id, int child_id, String first_name, String last_name, String birth_date, String date_creation, String doctor_name) {
        this.id = id;
        this.child_id = child_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.date_creation = date_creation;
        this.doctor_name = doctor_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
