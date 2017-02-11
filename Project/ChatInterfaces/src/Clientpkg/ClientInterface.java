/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clientpkg;

import Entities.Group;
import Entities.Message;
import Entities.Notification;
import Entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.scene.image.Image;

/**
 *
 * @author alaa
 */
public interface ClientInterface extends Remote {

    boolean recieve(Message msg, ClientInterface client ,User user) throws RemoteException;

    void recieveGroupMsg(Message msg, Group group) throws RemoteException;

    User getUser() throws RemoteException;

    void setUser(User user) throws RemoteException;

    void recieveBroadcastMsg(Message msg, Image image) throws RemoteException;

    void notifyStatusChangedToOthers(User user) throws RemoteException;

    void userBecomeOnline(User u) throws RemoteException;

    boolean recieveFile(String fileName, byte[] data, int len, ClientInterface friend) throws RemoteException;
    
    void notify(Notification n) throws RemoteException;
}
