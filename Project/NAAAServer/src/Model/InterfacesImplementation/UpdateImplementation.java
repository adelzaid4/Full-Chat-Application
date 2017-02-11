/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Clientpkg.ClientInterface;
import Entities.User;
import Model.DBHandlerpkg.DBUserHandler;
import Serverpkg.Updatable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class UpdateImplementation extends UnicastRemoteObject implements Updatable{
    DBUserHandler dBUserHandler;
    public UpdateImplementation() throws RemoteException{
        dBUserHandler=new DBUserHandler();
    }

    @Override
    public boolean updateProfile(User user) throws RemoteException {
        return dBUserHandler.updateUser(user);
    }

    @Override
    public boolean notifyChangeStatues(User user) throws RemoteException {
          ArrayList<ClientInterface> listOfClientInterfaces = ServerImplementation.getOnlineClient();
        for (ClientInterface listOfClientInterface : listOfClientInterfaces) {
            if (listOfClientInterface.getUser().getId() == user.getId()) {
                listOfClientInterface.notifyStatusChangedToOthers(user);
                dBUserHandler.updateUserStatus(user,user.isOffline());
                return true;
            }
        }
        return false;
    } 
}
