package com.example.milja.languageapp.database;

/**
 * Created by Milja on 2018-02-05.
 */

public class Type {

    private long typeId;
    private String typeGroup;

    public Type() {}

    public Type(long typeId, String typeGroup) {
        this.typeId = typeId;
        this.typeGroup = typeGroup;
    }

    //Getters and setters
    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(String typeGroup) {
        this.typeGroup = typeGroup;
    }

    @Override
    public String toString() {
        return getTypeGroup();
    }
}
