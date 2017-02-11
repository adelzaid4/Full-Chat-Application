/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import Entities.User;
import Entities.Group;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nour
 */
public class DBGroupHandler{

    int rows;
    DBHandler dBHandler;

    public DBGroupHandler() {
        dBHandler = new DBHandler();
        rows = 0;
    }

    public String addGroup(Group newGroup) {

        try {
            String creatorUnique = new String(newGroup.getName() + newGroup.getgroupCreator().getfirstName() + newGroup.getgroupCreator().getId());
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Insert into \"GROUP\" (NAME,CREATOR,CREATORUNIQUE) values('" + newGroup.getName() + "' ," + newGroup.getgroupCreator().getId() + ",'" + creatorUnique + "')"));

            rows = dBHandler.getPreparedStatement().executeUpdate();

            if (rows != 0) {
                //dBHandler.closeConnection();
                return creatorUnique;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return null;
        }
    }

    public boolean deleteGroup(Group group) {
        try {

            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Delete from \"GROUP\" where ID=" + group.getId() + ""));
            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
               ////dBHandler.closeConnection();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return false;
        }
    }

    public boolean updateGroup(Group group) {
        try {
            String creatorUnique = new String(group.getName() + group.getgroupCreator().getfirstName() + group.getgroupCreator().getId());
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Update \"GROUP\" set NAME='" + group.getName() + "',CREATOR=" + group.getgroupCreator().getId() + ",CREATORUNIQUE='" + creatorUnique + "' where ID=" + group.getId() + ""));
            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
                //dBHandler.closeConnection();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBGroupHandler.class.getName()).log(Level.SEVERE, null, ex);
            //dBHandler.closeConnection();
            return false;
        }
    }

    public Group getSingleGroup(Group group) {
        Group result = new Group();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Select * from \"GROUP\" Where ID=" + group.getId() + ""));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                result.setId(dBHandler.getResultSet().getInt("ID"));
                result.setName(dBHandler.getResultSet().getString("NAME"));
                result.setgroupCreator(u.getSingleUser(dBHandler.getResultSet().getInt("CREATOR")));
                result.setCreatorUnique(dBHandler.getResultSet().getString("CREATORUNIQUE"));
            }
            //dBHandler.closeConnection();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
           ////dBHandler.closeConnection();
            return result;
        }
    }
    
    public Group getSingleGroup(String str) {
        Group result = new Group();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Select * from \"GROUP\" Where CREATORUNIQUE='" + str + "'"));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                result.setId(dBHandler.getResultSet().getInt("ID"));
                result.setName(dBHandler.getResultSet().getString("NAME"));
                result.setgroupCreator(u.getSingleUser(dBHandler.getResultSet().getInt("CREATOR")));
                result.setCreatorUnique(dBHandler.getResultSet().getString("CREATORUNIQUE"));
            }
            //dBHandler.closeConnection();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
           ////dBHandler.closeConnection();
            return result;
        }
    }

    public ArrayList<Group> getGroupsOfUser(String creatorUnique) {
        ArrayList<Group> listOfGroups = new ArrayList<>();
        Group result = new Group();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Select * from \"GROUP\" Where CREATORUNIQUE='" + creatorUnique + "'"));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                result = new Group();
                result.setId(dBHandler.getResultSet().getInt("ID"));
                result.setName(dBHandler.getResultSet().getString("NAME"));
                result.setgroupCreator(u.getSingleUser(dBHandler.getResultSet().getInt("CREATOR")));
                result.setCreatorUnique(dBHandler.getResultSet().getString("CREATORUNIQUE"));
                listOfGroups.add(result);
            }
            //dBHandler.closeConnection();
            return listOfGroups;
        } catch (SQLException ex) {
            ex.printStackTrace();
           ////dBHandler.closeConnection();
            return listOfGroups;
        }
    }
    
    public ArrayList<Group> getGroupsContainingUser(User user) {
        ArrayList<Group> listOfGroups = new ArrayList<>();
        Group result = new Group();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Select * from \"GROUP_USERS\" Where USER_ID=" + user.getId() + ""));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                Group grp=new Group();
                grp.setId(dBHandler.getResultSet().getInt("GROUP_ID"));
                result =getSingleGroup(grp);
                listOfGroups.add(result);
            }
            //dBHandler.closeConnection();
            return listOfGroups;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listOfGroups;
        }
    }

    public List<Group> getAllGroups() {
        Group group = new Group();
        ArrayList<Group> listGroups = new ArrayList<>();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"GROUP\"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                group = new Group();
                group.setId(dBHandler.getResultSet().getInt("ID"));
                group.setName(dBHandler.getResultSet().getString("NAME"));
                group.setgroupCreator(u.getSingleUser(dBHandler.getResultSet().getInt("CREATOR")));
                group.setCreatorUnique(dBHandler.getResultSet().getString("CREATORUNIQUE"));
                listGroups.add(group);
            }
           ////dBHandler.closeConnection();
            return listGroups;

        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listGroups;
        }
    }

    public boolean addUsersOfGroup(Group group) {
        int count = 0;
        try {
            for (int i = 0; i < group.getListUsers().size(); i++) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"GROUP_USERS\" values (" + group.getId() + "," + group.getListUsers().get(i).getId() + ")", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

                count += dBHandler.getPreparedStatement().executeUpdate();
            }
            if (count != 0) {
              // //dBHandler.closeConnection();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return false;
        }
    }

    public boolean updateUsersOfGroup(Group group) {
        int count = 0;
        try {
            for (int i = 0; i < group.getListUsers().size(); i++) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("update \"GROUP_USERS\" set GROUP_ID=" + group.getId() + ",USER_ID" + group.getListUsers().get(i).getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

                count += dBHandler.getPreparedStatement().executeUpdate();
            }
            if (count != 0) {
               ////dBHandler.closeConnection();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           ////dBHandler.closeConnection();
            return false;
        }
    }
}
