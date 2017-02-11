/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import Entities.User;
import Entities.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alaa
 */
public class DBCategoryHandler {

    DBHandler dBHandler;
    DBUserHandler dBUserHandler;
    int rows;

    public DBCategoryHandler() {
        dBHandler = new DBHandler();
        dBUserHandler = new DBUserHandler();
        rows = 0;
    }

    public ArrayList<Category> getCategoryForUser(User u) {
        ArrayList<Category> listOfCategorys = new ArrayList<>();
        Category retrievedCategory = null;
        try {
            String selectAllQuery = new String("select * from \"CATEGORY\" where CREATOR='" + u.getId() + "'");
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(selectAllQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {

                retrievedCategory = new Category();
                retrievedCategory.setId(dBHandler.getResultSet().getInt("ID"));
                retrievedCategory.setName(dBHandler.getResultSet().getString("NAME"));
                retrievedCategory.setCategoryCreator(u);
                retrievedCategory.setCreatorUnique(dBHandler.getResultSet().getString("CREATORUNIQUE"));
                retrievedCategory.setCategoryMembers(getCategoryUsers(retrievedCategory));
                listOfCategorys.add(retrievedCategory);
            }
            //dBHandler.closeConnection();
            return listOfCategorys;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listOfCategorys;
        }
    }

    private ArrayList<User> getCategoryUsers(Category category) {
        ArrayList<User> listOfUsers = new ArrayList<>();
        try {
            String selectAllQuery = new String("select * from \"CATEGORY_USERS\" where CATEGORY='" + category.getId() + "'");
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(selectAllQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                User u = new DBUserHandler().getSingleUser(dBHandler.getResultSet().getInt("USER_ID"));
                listOfUsers.add(u);
            }
            //dBHandler.closeConnection();
            return listOfUsers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listOfUsers;
        }
    }

    public boolean deleteCategory(Category category) {
        int count = 0;
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Delete from  \"CATEGORY\"  Where ID = " + category.getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
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

    public boolean updateCategory(Category category) {

        int count = 0;
        try {
            String creatorUnique = category.getName() + category.getCategoryCreator().getfirstName() + category.getCategoryCreator().getId();
            String query = "update \"CATEGORY\" set NAME='" + category.getName() + "' , CREATOR='" + category.getCategoryCreator().getId() + "',CREATORUNIQUE='" + creatorUnique + "' where ID=" + category.getId() + "";
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

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

    public String addCategory(Category category) {
        int count = 0;
        try {
            String creatorUnique = category.getName() + category.getCategoryCreator().getfirstName() + category.getCategoryCreator().getId();
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"CATEGORY\"(NAME,CREATOR,CREATORUNIQUE) values ('" + category.getName() + "'," + category.getCategoryCreator().getId() + ",'" + creatorUnique + "')", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

            count = dBHandler.getPreparedStatement().executeUpdate();
            if (count != 0) {
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

    public ArrayList<Category> getAllCategories() {
        Category category;
        ArrayList<Category> listCategories = new ArrayList<>();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("select * from \"CATEGORY\"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                category = new Category();
                category.setId(dBHandler.getResultSet().getInt(1));
                category.setName(dBHandler.getResultSet().getString(2));
                category.setCategoryCreator(dBUserHandler.getSingleUser(dBHandler.getResultSet().getInt(3)));
                listCategories.add(category);
            }
            //dBHandler.closeConnection();
            return listCategories;

        } catch (SQLException ex) {
            ex.printStackTrace();
            //dBHandler.closeConnection();
            return listCategories;
        }
    }

    public boolean addUsersOfCategory(Category category) {
        int count = 0;
        try {
            for (int i = 0; i < category.getCategoryMembers().size(); i++) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("insert into \"CATEGORY_USERS\" values (" + category.getId() + "," + category.getCategoryMembers().get(i).getId() + ")", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

                count += dBHandler.getPreparedStatement().executeUpdate();
            }

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

    public boolean updateUsersOfCategory(Category category) {
        int count = 0;
        try {
            for (int i = 0; i < category.getCategoryMembers().size(); i++) {
                dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("update \"CATEGORY_USERS\" set CATEGORY=" + category.getId() + ",USER_ID" + category.getCategoryMembers().get(i).getId() + "", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));

                count += dBHandler.getPreparedStatement().executeUpdate();
            }
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

    public Category getSingleCategory(String str) {
        Category result = new Category();
        DBUserHandler u = new DBUserHandler();
        try {
            dBHandler.setPreparedStatement(dBHandler.getConnection().prepareStatement("Select * from \"CATEGORY\" Where CREATORUNIQUE='" + str + "'"));
            dBHandler.setResultSet(dBHandler.getPreparedStatement().executeQuery());
            while (dBHandler.getResultSet().next()) {
                result.setId(dBHandler.getResultSet().getInt("ID"));
                result.setName(dBHandler.getResultSet().getString("NAME"));
                result.setCategoryCreator(u.getSingleUser(dBHandler.getResultSet().getInt("CREATOR")));
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

}
