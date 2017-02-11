/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Clientpkg.ClientInterface;
import Serverpkg.Blockable;
import Serverpkg.Categorable;
import Serverpkg.Chatable;
import Serverpkg.Friendable;
import Serverpkg.Groupable;
import Serverpkg.HelloServer;
import Serverpkg.Notificationable;
import Serverpkg.Requestable;
import Serverpkg.ServerInterface;
import Serverpkg.Updatable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author adelz
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {

    static  ArrayList<ClientInterface> onlineClients = new ArrayList<>();

    public ServerImplementation() throws RemoteException {

    }

    @Override
    public Chatable getChatable() throws RemoteException {
        return new ChatImplementation();
    }

    @Override
    public Updatable getUpdate() throws RemoteException {
        return new UpdateImplementation();
    }

    @Override
    public Blockable getBolckable() throws RemoteException {
        return new BlockImplementation();
    }

    @Override
    public Categorable getCatrgorable() throws RemoteException {
        return new CategoryImplementation();
    }

    @Override
    public Friendable getFriendable() throws RemoteException {
        return new FriendsImplementation();
    }

    @Override
    public Groupable getGroupable() throws RemoteException {
        return new GroupImplementation();
    }

    @Override
    public HelloServer getHelloServer() throws RemoteException {
        return new HelloImplementation();
    }

    @Override
    public Notificationable getNotificationable() throws RemoteException {
        return new NotificationImplementation();
    }

    @Override
    public Requestable getRequestable() throws RemoteException {
        return new RequestImplementation();
    }

    public static boolean setClient(ClientInterface clientInterface) {
        onlineClients.add(clientInterface);
        return true;
    }

    public static boolean removeClient(ClientInterface clientInterface) {
        onlineClients.remove(clientInterface);
        return true;
    }

    public static  ArrayList<ClientInterface> getOnlineClient() {
        return onlineClients;
    }

}
