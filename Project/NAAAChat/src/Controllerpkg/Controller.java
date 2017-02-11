/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllerpkg;

import Entities.Friend;
import Entities.Block;
import Entities.Category;
import Entities.Notification;
import Entities.User;
import Entities.Request;
import Entities.Message;
import Entities.Group;
import Modelpkg.ConfigurationInfo;
import Clientpkg.ClientInterface;
import Modelpkg.ClientModel;
import Serverpkg.ServerInterface;
import java.io.File;
import viewpkg.ViewController;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

/**
 *
 * @author Nour
 */
public class Controller {

    ClientModel model;
    ViewController controller;
    ServerInterface server;

    public Controller(ViewController controller) {
        try {
            model = new ClientModel(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controller = controller;
    }

    public boolean connectToServer(ConfigurationInfo info) {
        try {
            Registry reg = LocateRegistry.getRegistry(info.getIp(), info.getPort());
            for (String list : reg.list()) {
                System.out.println(list);
            }
            server = (ServerInterface) reg.lookup("ChatService");
            return true;
        } catch (RemoteException | NotBoundException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return false;
        }
    }

    public boolean SignUp(User newUser) {
        try {
            return server.getHelloServer().signUp(newUser);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return false;
        }
    }

    public boolean UpdateUser(User u) {
        try {
            return server.getUpdate().updateProfile(u);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return false;
        }
    }

    public User SignIn(User u) {
        try {
            return server.getHelloServer().signIn(u, (ClientInterface) model);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public void close() {
        try {
            server.getHelloServer().unRegister((ClientInterface) model);
        } catch (RemoteException ex) {
           // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }

    }

    public void setUser(User userInfo) {
        try {
            model.setUser(userInfo);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public User getUser() {
        try {
            return model.getUser();
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ArrayList<Notification> getNotifications() {
        try {
            return server.getNotificationable().getNotification(model.getUser());
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ArrayList<Request> getRequests() {
        try {
            return server.getRequestable().getRequests(model.getUser());
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ArrayList<Group> getGroups() {
        try {
            return server.getGroupable().getGroups(model.getUser());
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ArrayList<User> getFrieds() {
        try {
            return server.getFriendable().getFriends(model.getUser());
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ArrayList<User> getBlocks(String type) {
        if ("Iblock".equals(type)) {
            try {
                return server.getBolckable().getUsersIBlocked(model.getUser());
            } catch (RemoteException ex) {
                //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                closeApplication();
                return null;
            }
        } else {
            try {
                return server.getBolckable().getUsersBlockedMe(model.getUser());
            } catch (RemoteException ex) {
                //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                closeApplication();
                return null;
            }
        }
    }

    public ClientInterface getFriendInterface(User u) {
        try {
            return server.getChatable().getClientReference(u);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public ClientInterface getMyInterface() {
        return (ClientInterface) model;
    }

    public void displayMsg(Message msg, ClientInterface clientInterface,User client) {
        controller.displayMsg(msg, clientInterface,client);
    }

    public boolean sendRequest(Request r) {
        try {
            return server.getRequestable().addRequest(r);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return false;
        }
    }

    public void signOut() {
        try {
            server.getHelloServer().signOut(model.getUser(), model);
        } catch (RemoteException ex) {
           // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void removeNotification(Notification n) {
        try {
            server.getNotificationable().removeNotification(model.getUser(), n);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void cancelRequest(Request request) {
        try {
            server.getRequestable().cancelRequest(request);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void acceptRequest(Request request) {
        try {
            server.getRequestable().acceptRequest(request);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void updateStatus(User user) {
        try {
            server.getUpdate().updateProfile(user);
            server.getUpdate().notifyChangeStatues(user);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }

    }

    void closeApplication() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Application Failure");
        alert.setHeaderText(null);
        alert.setContentText("Sorry\nServer is Down");
        alert.show();
        controller.loadSignInWindow();
    }

    public boolean recieveFile(File file, ClientInterface friend) {
        return controller.recieveFile(file, friend);
    }

    public ArrayList<Category> getCategories() {
        try {
            return server.getCatrgorable().getCategories(model.getUser());
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
            return null;
        }
    }

    public void changeStatus(User u) {
        controller.changeStatus(u);
    }

    public void showServerMsg(Message msg, Image img) {
        controller.showServerMsg(msg, img);
    }

    public void blockUser(User u) {
        try {
            Block block = new Block();
            block.setFromUser(model.getUser());
            block.setToUser(u);
            server.getBolckable().addBlock(block);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void deleteFriend(User u) {
        try {
            Friend friend=new Friend();
            friend.setFirstUser(u);
            friend.setSecondUser(model.getUser());
            server.getFriendable().removeFriend(friend);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void createGroup(Group g) {
        try {
            g.setgroupCreator(model.getUser());
            g.getListUsers().add(g.getgroupCreator());
            server.getGroupable().createGroup(g);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }

    public void createCategory(Category c) {
        try {
            c.setCategoryCreator(model.getUser());
            server.getCatrgorable().createCategory(c);
        } catch (RemoteException ex) {
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            closeApplication();
        }
    }
    
    public void userBecomeOnline(User u) {
        controller.userBecomeOnline(u);
    }

    public void notifyMsg(Notification n) {
        controller.notifyMsg(n);
    }

}
