/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;
import Entities.User;
import Entities.Notification;

import Model.DBHandlerpkg.DBNotificationHandler;
import Serverpkg.Notificationable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class NotificationImplementation  extends UnicastRemoteObject implements Notificationable {
    DBNotificationHandler notificationHandler;
    public NotificationImplementation() throws RemoteException{
            notificationHandler=new DBNotificationHandler();

    }
    

    @Override
    public ArrayList<Notification> getNotification(User user) throws RemoteException {
        return notificationHandler.getNotificationForUser(user);
    }

    @Override
    public boolean sendNotification(User user, Notification n) throws RemoteException {
        try{
        //notificationHandler.
            notificationHandler.addNotification(n);
            
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
    }

    @Override
    public boolean removeNotification(User user, Notification n) throws RemoteException {
          try{
                notificationHandler.deleteNotification(n);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
        return true;
    }
    
}
