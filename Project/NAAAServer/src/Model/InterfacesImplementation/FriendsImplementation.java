/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Entities.Friend;
import Entities.User;
import Model.DBHandlerpkg.DBFriendsHandler;
import Serverpkg.Friendable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author adelz
 */
public class FriendsImplementation extends UnicastRemoteObject implements Friendable{
    DBFriendsHandler dBFriendsHandler;
    public FriendsImplementation() throws RemoteException{
        dBFriendsHandler=new DBFriendsHandler();
    }

    
    @Override
    public ArrayList<User> getFriends(User user) throws RemoteException {
      return (ArrayList<User>) dBFriendsHandler.getFriendsOfUser(user);
    }

    @Override
    public void removeFriend(Friend friend) throws RemoteException {
        dBFriendsHandler.deleteFriend(friend);
    }
  
}
