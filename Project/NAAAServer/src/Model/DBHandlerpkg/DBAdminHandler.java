/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import Model.PrivateDAO.Admin;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MIDO
 */
public class DBAdminHandler {

    int rows;
    DBHandler dBHandler;

    public DBAdminHandler() {
        dBHandler = new DBHandler();
        rows = 0;
    }

    public boolean addAdmin(Admin admin) {
        try {

            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"ADMIN\" (USER_NAME,PASSWORD) values('" + admin.getUserName() + "','" + admin.getPassword() + "')", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

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
public boolean checkAdminExistance(Admin admin) {
        boolean check = false;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"ADMIN\" where USERNAME='" + admin.getUserName() + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                if (admin.getUserName().equals(dBHandler.getResultSet().getString(2))) {
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
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return false;
        }
    }
    public boolean deleteAdmin(Admin admin) {
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("delete from \"ADMIN\" where ID=" + admin.getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
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

    public Admin getAdmin(String userName) {
        Admin admin = null;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"ADMIN\" where USER_NAME=" + userName + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                admin = new Admin();
                admin.setId(dBHandler.getResultSet().getInt(1));
                admin.setUserName(dBHandler.getResultSet().getString(2));
                admin.setPassword(dBHandler.getResultSet().getString(3));

            }
           //dBHandler.closeConnection();
            return admin;

        } catch (SQLException ex) {
            ex.printStackTrace();
           //dBHandler.closeConnection();
            return admin;
        }
    }
}
