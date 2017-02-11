package Serverpkg;

import Entities.Friend;
import Entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MIDO
 */
public interface Friendable extends Remote {

    ArrayList<User> getFriends(User user) throws RemoteException;

    void removeFriend(Friend friend) throws RemoteException;

}
