/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Entities.User;
import Clientpkg.ClientInterface;
import Model.DBHandlerpkg.*;
import Serverpkg.HelloServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * q
 *
 * @author adelz
 */
public class HelloImplementation extends UnicastRemoteObject implements HelloServer {

    DBUserHandler dBUserHandler;

    public HelloImplementation() throws RemoteException {
        dBUserHandler = new DBUserHandler();
    }

    @Override
    public boolean signUp(User user) throws RemoteException {
        if (!dBUserHandler.checkUserExistance(user)) {
            if (dBUserHandler.addUser(user)) {
                return dBUserHandler.closeConnection();
            } else {
                return false;
            }
        } else {
            dBUserHandler.closeConnection();
            return false;
        }

    }

    @Override
    public User signIn(User user, ClientInterface clientInterface) throws RemoteException {
        User userRetrievedUser=new User();
        if (!dBUserHandler.checkUserExistance(user)) {
            userRetrievedUser = dBUserHandler.getSingleUser(user.getMail());
            userRetrievedUser.setOffline(0);
            dBUserHandler.updateUserStatus(userRetrievedUser,0);
            System.out.println(userRetrievedUser.isOffline());
            ArrayList<ClientInterface> list=ServerImplementation.onlineClients;
            for(int i=0;i<list.size();i++)
                list.get(i).userBecomeOnline(userRetrievedUser);
            register(clientInterface);
        }
        return userRetrievedUser;
    }

    @Override
    public boolean signOut(User user, ClientInterface clientInterface) throws RemoteException {
        user.setOffline(1);
        dBUserHandler.updateUserStatus(user,1);
        unRegister(clientInterface);
        return true;
    }

    @Override
    public boolean register(ClientInterface clientRef) throws RemoteException {
        return ServerImplementation.setClient(clientRef);
    }

    @Override
    public boolean unRegister(ClientInterface clientRef) throws RemoteException {
        return ServerImplementation.removeClient(clientRef);
    }

}
