/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author adelz
 */
public class Category implements Serializable{
    private int id;
    private User categoryCreator;
    private String creatorUnique;
    private String name;
    private ArrayList<User> categoryMembers;

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

    public User getCategoryCreator() {
        return categoryCreator;
    }

    public void setCategoryCreator(User categoryCreator) {
        this.categoryCreator = categoryCreator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  public ArrayList<User> getCategoryMembers() {
        return categoryMembers;
    }

    public void setCategoryMembers(ArrayList<User> categoryMembers) {
        this.categoryMembers = categoryMembers;
    }
    
}
