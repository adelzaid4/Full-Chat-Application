/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clientpkg.ClientInterface;
import Entities.Message;
import Entities.User;
import Model.DBHandlerpkg.DBAdminHandler;
import Model.DBHandlerpkg.DBUserHandler;
import Model.InterfacesImplementation.ServerImplementation;
import Model.PrivateDAO.Admin;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import serverui.FXMLloginuserController;

/**
 *
 * @author adelz
 */
public class ServerController extends Application {

    DBUserHandler dBUserHandler;
    DBAdminHandler dBAdminHandler;

    public ServerController() {
        dBUserHandler = new DBUserHandler();
        dBAdminHandler = new DBAdminHandler();

    }
    Stage s;

    @Override
    public void start(Stage stage) {
        s = stage;
        try {
            //   new start();
            FXMLLoader serverLoader = new FXMLLoader(getClass().getResource("..//serverui//FXML-loginuser.fxml"));
            Parent root = (Parent) serverLoader.load();
            System.out.println(serverLoader.getController() + "");
            FXMLloginuserController loginController = serverLoader.getController();
            System.out.println(loginController);
            System.out.println(this);
            loginController.setMain(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getRoot().getStyleClass().add("tooltip");
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendBroadCastMsg(String msg) {
        ArrayList<ClientInterface> ls = ServerImplementation.getOnlineClient();
        for (int i = 0; i < ls.size(); i++) {
            try {
                Image img = null;
                Message message = new Message();
                message.setBody(msg);
                message.setDate(null);
                message.setReciever(null);
                message.setSender(null);
                message.setStyle(null);
                ls.get(i).recieveBroadcastMsg(message, img);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public boolean addUser(User user) {
        return dBUserHandler.addUser(user);
    }

    public boolean addAdmin(Admin newAdmin) {
        return dBAdminHandler.addAdmin(newAdmin);

    }

    public void LoadMainWindow() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("..//serverui//FXMLDocument.fxml"));
            Scene sc = new Scene(root);
            s.setResizable(false);
            s.setScene(sc);
            s.show();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

   
}
