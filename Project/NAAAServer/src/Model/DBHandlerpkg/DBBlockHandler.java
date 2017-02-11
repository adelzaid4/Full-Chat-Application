package Model.DBHandlerpkg;

import Entities.User;
import Entities.Block;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.driver.OracleDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MIDO
 */
public class DBBlockHandler {

    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;

    PreparedStatement pstCreate;
    PreparedStatement pstUpdate;
    PreparedStatement pstDelete;
    PreparedStatement pstSelectBlocker;
    PreparedStatement pstSelectUser;
    DBHandler dBHandler;

    public DBBlockHandler() {
        dBHandler = new DBHandler();
        try {

            pstCreate = dBHandler.getConnection().prepareStatement("insert into \"BLOCK\" (USER_ID,BLOCKER) values( ? ,?)");
            pstUpdate = dBHandler.getConnection().prepareStatement("update  \"BLOCK\" set USER_ID=? ,BLOCKER=? where ID=?");
            pstDelete = dBHandler.getConnection().prepareStatement("delete from \"BLOCK\" where USER_ID =? and BLOCKER =?");
            pstSelectUser = dBHandler.getConnection().prepareStatement("select USER_ID from \"BLOCK\" where BLOCKER=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstSelectBlocker = dBHandler.getConnection().prepareStatement("select BLOCKER from \"BLOCK\" where USER_ID=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException ex) {
            Logger.getLogger(DBFriendsHandler.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public boolean createBlock(Block block) {

        try {

            User user = block.getToUser();
            User blocker = block.getFromUser();

            pstCreate.setInt(1, user.getId());
            pstCreate.setInt(2, (blocker.getId()));

            pstCreate.executeUpdate();
           //dBHandler.closeConnection();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
           //dBHandler.closeConnection();
            return false;
        }

    }

    public boolean updateBlock(Block block) {
        //int id;
        try {

            User user = block.getFromUser();
            User blocker = block.getToUser();
            pstUpdate.setInt(1, (user.getId()));
            pstUpdate.setInt(2, (blocker.getId()));
            pstUpdate.setInt(3, (block.getId()));

            pstUpdate.executeUpdate();
           //dBHandler.closeConnection();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
           //dBHandler.closeConnection();
            return false;

        }

    }

    public boolean deleteBlock(Block block) {
        try {
            pstDelete.setInt(1, block.getToUser().getId());
            pstDelete.setInt(2, block.getFromUser().getId());

            pstDelete.executeUpdate();
           //dBHandler.closeConnection();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
           //dBHandler.closeConnection();
            return false;

        }

    }

    public ArrayList<User> selectBlockedUsers(User blocker) {
        ArrayList<User> listOfBlockedUsers =  new ArrayList<User>();
        DBUserHandler u = new DBUserHandler();
        try {
            //  User blocker = block.getFromUser();
            pstSelectUser.setInt(1, blocker.getId());

            rs2 = pstSelectUser.executeQuery();

            while (rs2.next()) {
                listOfBlockedUsers.add(u.getSingleUser(rs2.getInt(1)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       //dBHandler.closeConnection();
        return listOfBlockedUsers;
    }

    public ArrayList<User> selectBlockers(User user) {
        ArrayList<User> listOfBlockers =  new ArrayList<>();
        DBUserHandler u = new DBUserHandler();
        try {
            // User user = block.getToUser();
            pstSelectBlocker.setInt(1, user.getId());

            rs3 = pstSelectBlocker.executeQuery();

            while (rs3.next()) {
                listOfBlockers.add(u.getSingleUser(rs3.getInt(1)));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       //dBHandler.closeConnection();
        return listOfBlockers;
    }

}
