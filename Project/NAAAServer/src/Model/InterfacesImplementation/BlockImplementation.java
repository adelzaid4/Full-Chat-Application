/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Entities.User;
import Entities.Block;
import Serverpkg.*;
import Model.DBHandlerpkg.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author MIDO
 */
public class BlockImplementation extends UnicastRemoteObject implements Blockable {

    DBBlockHandler blockHandler;

    public BlockImplementation() throws RemoteException {
        blockHandler = new DBBlockHandler();
    }

    @Override
    public ArrayList<User> getUsersIBlocked(User user) throws RemoteException {
        ArrayList<User> blockedList = new ArrayList<>();
        blockedList = blockHandler.selectBlockedUsers(user);
        return (ArrayList<User>) blockedList;
    }

    @Override
    public ArrayList<User> getUsersBlockedMe(User user) throws RemoteException {
        ArrayList<User> blockersList = new ArrayList<>();
        blockersList = blockHandler.selectBlockers(user);
        return (ArrayList<User>) blockersList;
    }

    @Override
    public boolean addBlock(Block block) throws RemoteException {
        try {
            blockHandler.createBlock(block);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeBlock(Block block) throws RemoteException {
        try {
            blockHandler.deleteBlock(block);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
