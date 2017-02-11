/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InterfacesImplementation.ServerImplementation;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adelz
 */
public class StartServer {
    Registry reg;
    ServerImplementation serverImplementation;
    public void start(){
      try {

             serverImplementation = new ServerImplementation();
             reg = LocateRegistry.createRegistry(5050);
            reg.rebind("ChatService", serverImplementation);
            
            System.out.println("ServerStarted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void stop(){
        try {
            reg.unbind("ChatService");
            System.out.println("ServerStoped");

        } catch (RemoteException ex) {
            Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
        
    }
   
}
}
