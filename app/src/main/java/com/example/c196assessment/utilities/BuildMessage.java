package com.example.c196assessment.utilities;

public class BuildMessage {

    private String key;
    private String value;



    public BuildMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public BuildMessage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
