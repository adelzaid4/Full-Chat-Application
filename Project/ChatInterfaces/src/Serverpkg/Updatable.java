/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serverpkg;

import Entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alaa
 */
public interface Updatable extends Remote{
    boolean updateProfile(User user) throws RemoteException;
    boolean notifyChangeStatues(User user)throws RemoteException;
}
