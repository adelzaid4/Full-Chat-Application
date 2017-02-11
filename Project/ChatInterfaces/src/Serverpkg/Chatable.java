/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serverpkg;

import Entities.User;
import Entities.Message;
import Clientpkg.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alaa
 */
public interface Chatable extends Remote{
    
    void sendOfflineMessages (Message msg) throws RemoteException;// save in xml for offline messages in server 
    ClientInterface getClientReference(User user)throws RemoteException; // return instance of the client he wants to chat with
     
}
