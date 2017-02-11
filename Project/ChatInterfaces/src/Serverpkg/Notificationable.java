/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serverpkg;

import Entities.Notification;
import Entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author alaa
 */
public interface Notificationable extends Remote {
    
    ArrayList<Notification> getNotification(User user) throws RemoteException;
    boolean sendNotification (User user , Notification n) throws RemoteException;
    boolean removeNotification (User user , Notification n)throws RemoteException;
}
