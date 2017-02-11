/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import Entities.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author adelz
 */
public class DBUserHandler {

    int rows;
    static DBHandler dBHandler;

    public DBUserHandler() {
        dBHandler = new DBHandler();
        rows = 0;
    }

    public boolean addUser(User user) {
        try {
            if (user.getBirthDate() != null) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"USER\" (FIRST_NAME,LAST_NAME,PASSWORD,EMAIL,GENDER,BIRTHDATE,COUNTRY,BIO,PIC_URL) values('" + user.getfirstName() + "','" + user.getlastName() + "','" + user.getPassword() + "','" + user.getMail() + "','" + user.getGender() + "'," + "TO_DATE('" + user.getBirthDate() + "','yyyy/mm/dd')" + ",'" + user.getCountry() + "','" + user.getBio() + "','" + user.getPicPath() + "')", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            } else {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"USER\" (FIRST_NAME,LAST_NAME,PASSWORD,EMAIL,GENDER,BIRTHDATE,COUNTRY,BIO,PIC_URL) values('" + user.getfirstName() + "','" + user.getlastName() + "','" + user.getPassword() + "','" + user.getMail() + "','" + user.getGender() + "'," + "TO_DATE(" + user.getBirthDate() + ",'yyyy/mm/dd')" + ",'" + user.getCountry() + "','" + user.getBio() + "','" + user.getPicPath() + "')", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            }
            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
                User temp = new User();
                temp = getSingleUser(user.getMail());
                if (user.getStatus() == null) {
                    temp.setStatus("available");
                } else {
                    temp.setStatus(user.getStatus());
                }
                temp.setOffline(1);
                addUserStatus(temp);
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

    public boolean deleteUser(User user) {
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("delete from \"USER\" where ID=" + user.getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
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

    public boolean updateUser(User user) {
        try {
  //          createDir();
//            user.setPicPath(getDir() + "\\" + user.getMail() + "." + user.getExtension());
            if (user.getBirthDate() != null) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("update \"USER\" set FIRST_NAME='" + user.getfirstName() + "',LAST_NAME='" + user.getlastName() + "',PASSWORD='" + user.getPassword() + "',EMAIL='" + user.getMail() + "',GENDER='" + user.getGender() + "',BIRTHDATE=" + "TO_DATE('" + user.getBirthDate() + "','yyyy/mm/dd')" + ",COUNTRY='" + user.getCountry() + "',BIO='" + user.getBio() + "',PIC_URL='" + user.getPicPath() + "' where ID='" + user.getId() + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            } else {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("update \"USER\" set FIRST_NAME='" + user.getfirstName() + "',LAST_NAME='" + user.getlastName() + "',PASSWORD='" + user.getPassword() + "',EMAIL='" + user.getMail() + "',GENDER='" + user.getGender() + "',MOBILE='" + user.getMobile() + "',BIRTHDATE=" + "TO_DATE(" + user.getBirthDate() + ",'yyyy/mm/dd')" + ",COUNTRY='" + user.getCountry() + "',BIO='" + user.getBio() + "',PIC_URL='" + user.getPicPath() + "' where ID='" + user.getId() + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_SCROLL_INSENSITIVE));
            }

            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
                User temp = new User();
                temp.setId(user.getId());
                if (user.getStatus() == null) {
                    temp.setStatus("available");
                } else {
                    temp.setStatus(user.getStatus());
                }
                temp.setOffline(user.isOffline());
                updateUserStatus(temp,temp.isOffline());
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

    public ArrayList<User> getAllUSERS() {
        User user;
        ArrayList<User> listUSERS = new ArrayList<>();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER\"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                user = new User();
                user.setId(dBHandler.getResultSet().getInt(1));
                user.setfirstName(dBHandler.getResultSet().getString(2));
                user.setlastName(dBHandler.getResultSet().getString(3));
                user.setPassword(dBHandler.getResultSet().getString(4));
                user.setMail(dBHandler.getResultSet().getString(5));
                user.setGender(dBHandler.getResultSet().getString(6));
                user.setMobile(dBHandler.getResultSet().getInt(7));
                user.setBirthDate(dBHandler.getResultSet().getDate(8));
                user.setCity(dBHandler.getResultSet().getString(9));
                user.setCountry(dBHandler.getResultSet().getString(10));
                user.setBio(dBHandler.getResultSet().getString(11));
                user.setPicPath(dBHandler.getResultSet().getString(12));
                User user2=getStatus(user);
                listUSERS.add(user);
            }
            //dBHandler.closeConnection();
            return listUSERS;

        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listUSERS;
        }
    }

    public ArrayList<User> getAllOnlineUsers() {
        ArrayList<User> listOnnlineUsers = new ArrayList();
        DBUserHandler db = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER_STATE\"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());

            while (dBHandler.getResultSet().next()) {
                if (dBHandler.getResultSet().getInt("ISOFFLINE") == 0) {
                    listOnnlineUsers.add(db.getSingleUser(dBHandler.getResultSet().getInt("ISOFFLINE")));
                }

            }
            return listOnnlineUsers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return listOnnlineUsers;
        }

    }

    public ArrayList<User> getAllOfflineUsers() {
        ArrayList<User> listOfflinUsers = new ArrayList();
        DBUserHandler db = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER_STATE\"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());

            while (dBHandler.getResultSet().next()) {
                if (dBHandler.getResultSet().getInt("ISOFFLINE") == 1) {
                    listOfflinUsers.add(db.getSingleUser(dBHandler.getResultSet().getInt("ISOFFLINE")));
                }

            }
            return listOfflinUsers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return listOfflinUsers;
        }

    }

    public User getSingleUser(int id) {
        User user = new User();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER\" where ID=" + id + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                user = new User();
                user.setId(dBHandler.getResultSet().getInt(1));
                user.setfirstName(dBHandler.getResultSet().getString(2));
                user.setlastName(dBHandler.getResultSet().getString(3));
                user.setPassword(dBHandler.getResultSet().getString(4));
                user.setMail(dBHandler.getResultSet().getString(5));
                user.setGender(dBHandler.getResultSet().getString(6));
                user.setMobile(dBHandler.getResultSet().getInt(7));
                user.setBirthDate(dBHandler.getResultSet().getDate(8));
                user.setCity(dBHandler.getResultSet().getString(9));
                user.setCountry(dBHandler.getResultSet().getString(10));
                user.setBio(dBHandler.getResultSet().getString(11));
                user.setPicPath(dBHandler.getResultSet().getString(12));
                user=getStatus(user);
            }
            //dBHandler.closeConnection();
            return user;

        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return user;
        }
    }

    public boolean checkUserExistance(User user) {
        boolean check = false;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER\" where ID=" + user.getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                if (user.getMail().equals(dBHandler.getResultSet().getString(5))) {
                    check = true;
                    break;
                }
            }
            if (check) {
                //dBHandler.closeConnection();
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            //ex.printStackTrace();
            //dBHandler.closeConnection();
            return false;
        }
    }

    public User getSingleUser(String mail) {
        User user = new User();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER\" where EMAIL='" + mail + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            dBHandler.getResultSet().next();
            user.setId(dBHandler.getResultSet().getInt(1));
            user.setfirstName(dBHandler.getResultSet().getString(2));
            user.setlastName(dBHandler.getResultSet().getString(3));
            user.setPassword(dBHandler.getResultSet().getString(4));
            user.setMail(dBHandler.getResultSet().getString(5));
            user.setGender(dBHandler.getResultSet().getString(6));
            user.setMobile(dBHandler.getResultSet().getInt(7));
            user.setBirthDate(dBHandler.getResultSet().getDate(8));
            user.setCity(dBHandler.getResultSet().getString(9));
            user.setCountry(dBHandler.getResultSet().getString(10));
            user.setBio(dBHandler.getResultSet().getString(11));
            user.setPicPath(dBHandler.getResultSet().getString(12));
            User u2=getStatus(user);
            return u2;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return user;
        }
    }

    public User getStatus(User user) {
        User user1 = new User();
        DBHandler dbhand=new DBHandler();
        try {
            
            dbhand.setPreparedStatement(dbhand.getConnection().prepareStatement("select * from \"USER_STATE\" where USER_ID=" + user.getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dbhand.setResultSet(dbhand.getPreparedStatement().executeQuery());
            dbhand.getResultSet().next();
            user1=user;
            user1.setStatus(dbhand.getResultSet().getString(3));
            user1.setOffline(dbhand.getResultSet().getInt(2));

            //dBHandler.closeConnection();
            return user1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return user1;
        }
    }

    public void updateStatus(User user) {
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"USER_STATE\" where USER_ID='" + user.getId() + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public int addUserStatus(User user) {
        try {

            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"USER_STATE\" (USER_ID,ISOFFLINE,STATUS) values (" + user.getId() + "," + user.isOffline() + ",'" + user.getStatus() + "' )", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {
                // //dBHandler.closeConnection();
                return rows;
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            ////dBHandler.closeConnection();
            return -1;
        }
    }

    public boolean updateUserStatus(User user,int state) {
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("update \"USER_STATE\" set ISOFFLINE='" + state + "',STATUS='" + user.getStatus().toLowerCase() + "' where USER_ID='" + user.getId() + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY));

            rows = dBHandler.getPreparedStatement().executeUpdate();
            if (rows != 0) {

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

    private void createDir() {
        Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "//User Images");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getDir() {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + "//User Images").toString();
    }

    public void save(byte[] image, String mail) {
        try {
            DBUserHandler db = new DBUserHandler();
            File file = new File(db.getDir() + "//" + mail);
            BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(image));

            System.out.println("Received " + image1.getHeight() + "x" + image1.getWidth() + ": " + System.currentTimeMillis());
            String savePath = db.getDir();
            ImageIO.write(image1, "jpg", new File(savePath + "\\" + mail));

        } catch (IOException ex) {
            Logger.getLogger(DBUserHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean closeConnection() {
        //dBHandler.closeConnection();
        return true;
    }
}
