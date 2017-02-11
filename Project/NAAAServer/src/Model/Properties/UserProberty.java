/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Properties;

import Entities.User;
import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alaa
 */
public class UserProberty {

    //Properties 
    private SimpleStringProperty fname = new SimpleStringProperty();
    private SimpleStringProperty lname = new SimpleStringProperty();
    private SimpleStringProperty mail = new SimpleStringProperty();
    private SimpleStringProperty gender = new SimpleStringProperty();
    private SimpleStringProperty country = new SimpleStringProperty();
    private SimpleStringProperty bio = new SimpleStringProperty();
    private SimpleStringProperty status = new SimpleStringProperty();
   // private SimpleStringProperty birthDate = new SimpleStringProperty();
    private SimpleIntegerProperty offline = new SimpleIntegerProperty();
    //picPath
    //image

    public UserProberty(User user) {
        setFname(user.getfirstName());
        setLname(user.getlastName());
        setMail(user.getMail());
        setGender(user.getGender());
        setCountry(user.getCountry());
        setBio(user.getBio());
        setStatus(user.getStatus());
      //  setBirthDate(user.getBirthDate());
        setOffline(user.isOffline());
    }

    public String getFname() {
        return fname.getValue();
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public String getGender() {
        return gender.getValue();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCountry() {
        return country.getValue();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getBio() {
        return bio.getValue();
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

//    public String getBirthDate() {
//        return birthDate.getValue().toString();
//    }
//
//    public void setBirthDate(Date birthDate) {
//        this.birthDate.set(birthDate.toString());
//    }

    public int getOffline(){
        return offline.getValue();
    }

    public void setOffline(int offline) {
        this.offline.set(offline);
    }

   
    public String getLname() {
        return lname.getValue();
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public String getMail() {
        return mail.getValue();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

}
