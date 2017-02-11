/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Clientpkg.ClientInterface;
import Entities.Group;
import Entities.Message;
import Entities.User;
import Model.DBHandlerpkg.DBGroupHandler;
import Serverpkg.Groupable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class GroupImplementation extends UnicastRemoteObject implements Groupable {

    DBGroupHandler dBGroupHandler;

    public GroupImplementation() throws RemoteException {
        dBGroupHandler = new DBGroupHandler();
    }

    @Override
    public boolean createGroup(Group group) throws RemoteException {
        group.setCreatorUnique(dBGroupHandler.addGroup(group));
        if (group.getCreatorUnique()!=null) {
           Group newGroup=dBGroupHandler.getSingleGroup(group.getCreatorUnique());
           newGroup.setListUsers(group.getListUsers());
            return dBGroupHandler.addUsersOfGroup(newGroup);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteGroup(Group group) throws RemoteException {
        return dBGroupHandler.deleteGroup(group);
    }

    @Override
    public boolean leaveGroup(User leavingUser, Group group) throws RemoteException {
        group.getListUsers().remove(leavingUser);
        return dBGroupHandler.updateGroup(group);
    }

    @Override
    public boolean addUserToGroup(User newUser, Group group) throws RemoteException {
        group.getListUsers().add(newUser);
        return dBGroupHandler.updateGroup(group);
    }

    @Override
    public void sendGroupMessage(Message message, Group group) throws RemoteException {//3laa by2ool akeed hatdrab

        ArrayList<ClientInterface> listOfClientInterfaces = ServerImplementation.getOnlineClient();
        ArrayList<User> listOfUsers = group.getListUsers();
        ArrayList<ClientInterface> onlineGroupClientInterfaces = new ArrayList<>();
        for (int i = 0; i < listOfClientInterfaces.size(); i++) {
            if (listOfClientInterfaces.get(i).getUser().getId() == listOfUsers.get(i).getId()) {
                onlineGroupClientInterfaces.add(listOfClientInterfaces.get(i));
            }
        }
        for (int i = 0; i < onlineGroupClientInterfaces.size(); i++) {
            onlineGroupClientInterfaces.get(i).recieveGroupMsg(message, group);
        }

    }

    @Override
    public ArrayList<Group> getGroups(User user) throws RemoteException {
        return dBGroupHandler.getGroupsContainingUser(user);
    }

}
