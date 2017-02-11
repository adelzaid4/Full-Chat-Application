/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serverpkg;

import Entities.Block;
import Entities.User;
import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author adelz
 */
public interface Blockable extends Remote{
    ArrayList<User> getUsersIBlocked(User user) throws RemoteException;
    ArrayList<User> getUsersBlockedMe(User user) throws RemoteException;
    boolean addBlock(Block block) throws RemoteException;
    boolean removeBlock(Block block) throws RemoteException;
}
