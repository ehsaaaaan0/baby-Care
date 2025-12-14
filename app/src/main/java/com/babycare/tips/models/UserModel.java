package com.babycare.tips.models;


import java.io.Serializable;

//Dated: 17 aug 2022
public class UserModel implements Serializable {
    String babyName,babyGender,babyDob,motherName, encodedImage,email;

    public UserModel() {
    }

    public UserModel(String babyName, String babyGender, String babyDob, String motherName, String encodedImage, String email) {
        this.babyName = babyName;
        this.babyGender = babyGender;
        this.babyDob = babyDob;
        this.motherName = motherName;
        this.encodedImage = encodedImage;
        this.email = email;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabyGender() {
        return babyGender;
    }

    public void setBabyGender(String babyGender) {
        this.babyGender = babyGender;
    }

    public String getBabyDob() {
        return babyDob;
    }

    public void setBabyDob(String babyDob) {
        this.babyDob = babyDob;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
