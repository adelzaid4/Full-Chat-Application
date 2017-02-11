/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serverpkg;

import Entities.Category;
import Entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Nour
 */
public interface Categorable extends Remote{
    boolean createCategory(Category c)throws RemoteException;
    boolean removeCategory(Category c)throws RemoteException;
    boolean updateCategory(Category c)throws RemoteException;
    boolean addUsertoCategory(User user,Category c) throws RemoteException;
    boolean removeUserFromCategory(User u,Category c) throws RemoteException;
    ArrayList<Category> getCategories(User u)throws RemoteException;
    
}
