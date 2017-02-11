/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author adelz
 */
public class Group implements Serializable{

    private int id;
    private String name;
    private User groupCreator;
    private String creatorUnique;
    private  ArrayList<User> listUsers;

    public String getCreatorUnique() {
        return creatorUnique;
    }

    public void setCreatorUnique(String creatorUnique) {
        this.creatorUnique = creatorUnique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getgroupCreator() {
        return groupCreator;
    }

    public void setgroupCreator(User user) {
        this.groupCreator = user;
    }

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
    }
}
