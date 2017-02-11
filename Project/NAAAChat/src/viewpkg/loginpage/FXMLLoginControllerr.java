/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.loginpage;

import Entities.Request;
import Entities.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import viewpkg.ViewController;

/**
 * FXML Controller class
 *
 * @author alaa
 */
public class FXMLLoginControllerr implements Initializable {

    ViewController mainViewController;

    @FXML
    Button loginbtn;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    BorderPane pane;

    /**
     * Initializes the controller class.
     *
     * @param vc
     */
    boolean em = false, pass = false;

    public void setController(ViewController vc) {
        mainViewController = vc;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pane.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.ENTER) {

            }
        });

        // validate mail
        email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (email.isFocused() == false) {
                if (!textFieldIsNull(email)) {
                    if (inValidMail(email)) {
                        em = false;
                    } else {
                        em = true;
                    }
                } else {
                    em = false;
                }
            }
        });
        password.focusedProperty().addListener((observable) -> {
            if (password.isFocused() == false) {
                if (!textFieldIsNull(password)) {
                    if (passwordStatus(password)) {
                        pass = false;
                    } else {
                        pass = true;
                    }
                } else {
                    pass = false;
                }
            }
        });
    }

    @FXML
    void openSignUpPage() {
        mainViewController.loadSingUpWindow();
    }

    @FXML
    void LogInAction() {
        if (em && pass) {
            User u = new User();
            u.setMail(email.getText());
            u.setPassword(password.getText());
            User userInfo = mainViewController.signIn(u);
            if (userInfo != null &&userInfo!=new User()) {
                System.out.println("successfully logged in");
                mainViewController.setUser(userInfo);
                mainViewController.loadProfileWindow();
            } else {
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Login Error");
                ArrayList<Request> req = mainViewController.getRequests();
                tray.setMessage("Email or Password are incorrect.\nplease check your data. ");
                tray.setNotificationType(NotificationType.NOTICE);
                tray.showAndDismiss(Duration.seconds(4.0));
                password.setText("");
            }
        } else {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Login Error");
            ArrayList<Request> req = mainViewController.getRequests();
            tray.setMessage("please fill all data first. ");
            tray.setNotificationType(NotificationType.NOTICE);
            tray.showAndDismiss(Duration.seconds(4.0));
        }
    }

    public boolean textFieldIsNull(TextField tf) {
        boolean isNull = false;
        if (tf.getText().isEmpty()) {
            isNull = true;
            tf.setStyle("-fx-background-color: red");
        } else {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        }
        return isNull;
    }

    public boolean inValidMail(TextField tf) {
        boolean isNotValid = false;
        if (!tf.getText().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            isNotValid = true;
            tf.setStyle("-fx-background-color: red");
        } else {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        }
        return isNotValid;
    }

    public boolean passwordStatus(TextField tf) {
        boolean isNull = false;
        if (tf.getText().matches("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{7,}$")) {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else if (tf.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{5,}$")) {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else if (tf.getText().matches("^(?=.*[0-9])(?=.*[a-z]).{3,}$")) {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else {
            isNull = true;
            tf.setStyle("-fx-background-color: red");
        }
        return isNull;
    }

    @FXML
    public void ExitButton(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage s = (Stage) loginbtn.getScene().getWindow();
            s.close();
            Platform.exit();
            System.exit(0);
        }
    }

}
