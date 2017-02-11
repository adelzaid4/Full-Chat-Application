/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverui;

import Controller.ServerController;
import Model.DBHandlerpkg.DBAdminHandler;
import Model.DBHandlerpkg.DBUserHandler;
import Model.PrivateDAO.Admin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alaa
 */
public class FXMLloginuserController implements Initializable {
    
    @FXML
    TextField email;
    @FXML
    PasswordField password;

    ServerController main;
    DBAdminHandler dBAdminHandler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dBAdminHandler = new DBAdminHandler();
        
    }

    @FXML
    public void login() {
        // check username avalability
        // if true {
        Admin admin = new Admin();
        if (!email.getText().equals("") && !password.getText().equals("")) {
            admin.setUserName(email.getText());
            admin.setPassword(password.getText());

            if (dBAdminHandler.checkAdminExistance(admin)) {
                getMain().LoadMainWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(email.getText() + " Admin is not found!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please,Enter the required data!");
            alert.show();
        }
    }

    public ServerController getMain() {
        return main;
    }

    public void setMain(ServerController aThis) {
        main = aThis;
    }

}
