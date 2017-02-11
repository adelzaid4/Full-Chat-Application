/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Entities.Category;
import Entities.User;
import Model.DBHandlerpkg.DBCategoryHandler;
import Serverpkg.Categorable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class CategoryImplementation extends UnicastRemoteObject implements Categorable {

    DBCategoryHandler dBCategoryHandler;

    public CategoryImplementation() throws RemoteException {
        dBCategoryHandler = new DBCategoryHandler();
    }

    @Override
    public boolean createCategory(Category c) throws RemoteException {
        c.setCreatorUnique(dBCategoryHandler.addCategory(c));
        if (c.getCreatorUnique() != null) {
            Category cat = dBCategoryHandler.getSingleCategory(c.getCreatorUnique());
            cat.setCategoryMembers(c.getCategoryMembers());
            return dBCategoryHandler.addUsersOfCategory(cat);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeCategory(Category c) throws RemoteException {
        return dBCategoryHandler.deleteCategory(c);
    }

    @Override
    public boolean updateCategory(Category c) throws RemoteException {
        return dBCategoryHandler.updateCategory(c);
    }

    @Override
    public boolean addUsertoCategory(User user, Category c) throws RemoteException {
        c.getCategoryMembers().add(user);
        return dBCategoryHandler.updateCategory(c);
    }

    @Override
    public boolean removeUserFromCategory(User u, Category c) throws RemoteException {
        c.getCategoryMembers().remove(u);
        return dBCategoryHandler.updateCategory(c);
    }

    @Override
    public ArrayList<Category> getCategories(User u) throws RemoteException {
        return (ArrayList<Category>) dBCategoryHandler.getCategoryForUser(u);
    }

}
