/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import Entities.User;
import Entities.Notification;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alaa
 */
public class DBNotificationHandler extends DBHandler {

    int rows;
    DBHandler dBHandler;

    public DBNotificationHandler() {
        dBHandler = new DBHandler();
        rows = 0;
    }

    public boolean addNotification(Notification notification) {
        int count = 0;
        try {
            String unqieField = "Notification" + (getLastID() + 1) + notification.getUser().getId();
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"NOTIFICATION\" (DATERECIEVED,CONTENT,USER_ID,UNIQUEFIELD) values (?,'" + notification.getContent() + "'," + notification.getUser().getId() + ",'" + unqieField + "')"));
            dBHandler.getPreparedStatement().setTimestamp(1, notification.getDate());

            count = dBHandler.getPreparedStatement().executeUpdate();
            if (count != 0) {
                //dBHandler.closeConnection();
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

    public int deleteNotification(Notification notification) {
        int count = 0;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Delete \"NOTIFICATION\" Where ID = " + notification.getId() + ""));
            count = dBHandler.getPreparedStatement().executeUpdate();
            if (count != 0) {
                //dBHandler.closeConnection();
                return count;
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return -1;
        }
    }

    public int updateNotification(Notification notification) {
        int count = 0;
        try {
            String unqieField = "Notification" + (getLastID() + 1) + notification.getUser().getId();
            String query = "update \"NOTIFICATION\" set DATERECIEVED=" + "TO_TIMESTAMP('" + notification.getDate() + "','YYYY-MM-DD HH24:MI:SS.FF')" + ", CONTENT='" + notification.getContent() + "' , USER_ID=" + notification.getUser().getId() + ",UNIQUEFIELD='" + unqieField + "' where ID=" + notification.getId() + "";
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(query));

            count = dBHandler.getPreparedStatement().executeUpdate();
            if (count != 0) {
               ////dBHandler.closeConnection();
                return count;
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return -1;
        }
    }

    public ArrayList<Notification> getNotificationForUser(User user) {
        ArrayList<Notification> listNotifications = new ArrayList<>();
        DBUserHandler u = new DBUserHandler();
        Notification notification;
        try {
            String selectAllQuery = new String("select * from \"NOTIFICATION\" where USER_ID='" + user.getId() + "'");
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(selectAllQuery));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                notification = new Notification();
                notification.setId(dBHandler.getResultSet().getInt("ID"));
                notification.setDate(dBHandler.getResultSet().getTimestamp("DATERECIEVED"));
                notification.setContent(dBHandler.getResultSet().getString("CONTENT"));
                notification.setUser(u.getSingleUser(dBHandler.getResultSet().getInt("USER_ID")));
                notification.setUniqueField(dBHandler.getResultSet().getString("UNIQUEFIELD"));
                listNotifications.add(notification);
                deleteNotification(notification);
            }
            //dBHandler.closeConnection();
            return listNotifications;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listNotifications;
        }
    }

    public Notification getNotificationForUser(String uniqueField) {
        DBUserHandler u = new DBUserHandler();
        Notification notification = null;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"NOTIFICATION\" where UNIQUEFIELD='" + uniqueField + "'"));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                notification = new Notification();
                notification.setId(dBHandler.getResultSet().getInt("ID"));
                notification.setDate(dBHandler.getResultSet().getTimestamp("DATERECIEVED"));
                notification.setContent(dBHandler.getResultSet().getString("CONTENT"));
                notification.setUser(u.getSingleUser(dBHandler.getResultSet().getInt("USER_ID")));
                notification.setUniqueField(dBHandler.getResultSet().getString("UNIQUEFIELD"));
            }
            //dBHandler.closeConnection();
            return notification;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return notification;
        }
    }

    public int getLastID() {
        int lastID = 0;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select MAX(ID) from \"NOTIFICATION\""));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                lastID = dBHandler.getResultSet().getInt(1);
            }
            //dBHandler.closeConnection();
            return lastID;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return lastID;
        }
    }

    public ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> listNotifications = new ArrayList();
        DBUserHandler u = new DBUserHandler();
        Notification notification;
        try {
            String selectAllQuery = new String("select * from \"NOTIFICATION\"");
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(selectAllQuery));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                notification = new Notification();
                notification.setId(dBHandler.getResultSet().getInt("ID"));
                notification.setDate(dBHandler.getResultSet().getTimestamp("DATERECIEVED"));
                notification.setContent(dBHandler.getResultSet().getString("CONTENT"));
                notification.setUser(u.getSingleUser(dBHandler.getResultSet().getInt("USER_ID")));
                notification.setUniqueField(dBHandler.getResultSet().getString("UNIQUEFIELD"));
                listNotifications.add(notification);
            }
            //dBHandler.closeConnection();
            return listNotifications;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listNotifications;
        }
    }

}
