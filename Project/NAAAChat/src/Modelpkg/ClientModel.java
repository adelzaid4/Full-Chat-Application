/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelpkg;

import Clientpkg.ClientInterface;
import Controllerpkg.Controller;
import Entities.Group;
import Entities.Message;
import Entities.Notification;
import Entities.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Nour
 */
public class ClientModel extends UnicastRemoteObject implements ClientInterface {

    ArrayList<User> myChattingFriends;
    private User currentUser;
    Controller control;

    public ClientModel(Controller control) throws RemoteException {
        this.control = control;
        myChattingFriends = new ArrayList<>();
        currentUser = new User();
    }

    @Override
    public boolean recieve(Message msg, ClientInterface clientInterface, User client) throws RemoteException {
        control.displayMsg(msg, clientInterface, client);
        return true;
    }

    @Override
    public User getUser() throws RemoteException {
        User copy = new User();
        copy = currentUser;
        return copy;
    }

    @Override
    public void setUser(User user) throws RemoteException {
        currentUser = user;
    }

    @Override
    public void recieveGroupMsg(Message msg, Group group) throws RemoteException {

    }

    @Override
    public void userBecomeOnline(User u) throws RemoteException {
        control.userBecomeOnline(u); // 1 > offline , 0 > online
    }

    @Override
    public void recieveBroadcastMsg(Message msg, Image img) throws RemoteException {
        control.showServerMsg(msg, img);
    }

    @Override
    public boolean recieveFile(String fileName, byte[] data, int len, ClientInterface friend) throws RemoteException {
        try {
            File f = new File(fileName);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, true);
            out.write(data, 0, len);
            out.flush();
            out.close();
            System.out.println("Done writing data...");
            return control.recieveFile(f, friend);
        } catch (IOException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void notifyStatusChangedToOthers(User user) throws RemoteException {
        control.changeStatus(user);
    }

    @Override
    public void notify(Notification n) throws RemoteException {
        control.notifyMsg(n);
    }
}
