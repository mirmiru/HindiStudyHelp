package com.example.milja.languageapp.database;

/**
 * Created by Milja on 2018-02-05.
 */

public class Gender {

    private long genderId;
    private String genderGroup;

    //Constructors
    public Gender() {}

    public Gender(long genderId, String genderGroup) {
        this.genderId = genderId;
        this.genderGroup = genderGroup;
    }

    //Getters and setters
    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long id) {
        this.genderId = id;
    }

    public String getGenderGroup() {
        return genderGroup;
    }

    public void setGenderGroup(String genderGroup) {
        this.genderGroup = genderGroup;
    }
}
