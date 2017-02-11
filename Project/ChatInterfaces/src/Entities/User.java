/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.sql.Date;
import javafx.scene.image.Image;


/**
 *
 * @author adelz
 */
public class User implements Serializable{
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String mail;
    private String gender;
    private String country;
    private String city;
    private String bio;
    private String picPath;
    private int mobile;
    private Date birthDate;
    private byte[] image;
    private String extension;
    private int offline;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String fName) {
        this.firstName = fName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lString) {
        this.lastName = lString;
    }
    
    public String getFullName()
    {
        return firstName+" "+lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int isOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
}
