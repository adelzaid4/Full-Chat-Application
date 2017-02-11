/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Clientpkg.ClientInterface;
import Entities.Message;
import Entities.User;
import Serverpkg.Chatable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class ChatImplementation extends UnicastRemoteObject implements Chatable {

    public ChatImplementation() throws RemoteException {
    }

    @Override
    public void sendOfflineMessages(Message msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClientInterface getClientReference(User user) throws RemoteException {
        ArrayList<ClientInterface> listOfClientInterfaces = ServerImplementation.getOnlineClient();
        for (ClientInterface listOfClientInterface : listOfClientInterfaces) {
            if (listOfClientInterface.getUser().getId() == user.getId()) {
                return listOfClientInterface;
            }
        }
        return null;
    }

}
