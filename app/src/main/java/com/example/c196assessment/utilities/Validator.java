package com.example.c196assessment.utilities;

import android.util.Log;

import java.util.HashMap;

public class Validator {



    public Validator() {
    }

    public HashMap<String, String> validateInput(HashMap<String, String> input) {
        BuildMessage msg;
        HashMap<String, String> result = new HashMap<>();
        for(HashMap.Entry<String, String> entry : input.entrySet()) {
            msg = checkInput(entry.getKey(), entry.getValue());
            result.put(msg.getKey(), msg.getValue());
        }
     return result;
 }

    public BuildMessage checkInput(String k, String v) {
        BuildMessage message = new BuildMessage();
        switch(k) {
            case "phone":
                message.setKey("phone");
                message.setValue(phoneValidate(v));
                break;
            case "courseName":
                message.setKey("courseName");
                message.setValue(nameValidate(v));
                break;
            case "email":
                message.setKey("email");
                message.setValue(emailValidate(v));
                break;
            case "mentorName":
                message.setKey("mentorName");
                message.setValue(nameValidate(v));
            default:

        }
        return message;
    }

    public String phoneValidate(String phone) {
        String result = "OK";
        if (!phone.matches("[0-9]+")) {
            result = "Phone number should contain only numbers";
        }
        if(phone.length() >= 11 || phone.length() <= 7) {
            result = "Phone number should be between 7 and 10 digits";
        }
        return result;
    }

    public String nameValidate(String name) {
        String result = "OK";
        if(name.length() < 1 || name.length() > 50) {
            result = "Name is a required field";
        }
        return result;
    }

    public String emailValidate(String email) {
        String result = "OK";
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(!email.matches(regex)) {
            result = "Please enter a valid email address";
        }
        return result;
    }

}
