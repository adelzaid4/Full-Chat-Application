/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.InterfacesImplementation;

import Clientpkg.ClientInterface;
import Entities.Friend;
import Entities.Notification;
import Entities.Request;
import Entities.User;
import Model.DBHandlerpkg.DBFriendsHandler;
import Model.DBHandlerpkg.DBNotificationHandler;
import Model.DBHandlerpkg.DBRequestHandler;
import Serverpkg.Requestable;
import com.sun.nio.sctp.AssociationChangeNotification;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

/**
 *
 * @author adelz
 */
public class RequestImplementation extends UnicastRemoteObject implements Requestable {

    DBRequestHandler dBRequestHandler;

    public RequestImplementation() throws RemoteException {
        dBRequestHandler = new DBRequestHandler();
    }

    @Override
    public ArrayList<Request> getRequests(User user) throws RemoteException {
        return (ArrayList<Request>) dBRequestHandler.getRequests(user);
    }

    @Override
    public boolean addRequest(Request request) throws RemoteException {
        return dBRequestHandler.addRequest(request);
    }

    @Override
    public boolean acceptRequest(Request request) throws RemoteException {
        DBFriendsHandler db = new DBFriendsHandler();
        Friend friend = new Friend();
        friend.setFirstUser(request.getFromUser());
        friend.setSecondUser(request.getToUser());
        db.addFriend(friend);
        dBRequestHandler.acceptRequest(request);
        Notification n=new Notification();
        n.setUser(request.getToUser());
        n.setDate(Timestamp.from(Instant.now()));
        n.setContent(request.getToUser().getFullName()+" accepted your request at "+n.getDate().toString());
        ArrayList<ClientInterface> onlinelist=ServerImplementation.getOnlineClient();
        boolean found=false;
        for(int i=0;i<onlinelist.size();i++)
        {
            if(onlinelist.get(i).getUser().getId()==request.getFromUser().getId())
            {
                onlinelist.get(i).notify(n);
                found=true;
                break;
            }
        }
        if(!found)
        {
            n.setUser(request.getFromUser());
            DBNotificationHandler dbnot=new DBNotificationHandler();
            dbnot.addNotification(n);
        }
        return true;
    }

    @Override
    public boolean cancelRequest(Request request) throws RemoteException {
        return dBRequestHandler.cancelRequest(request);
    }

}
