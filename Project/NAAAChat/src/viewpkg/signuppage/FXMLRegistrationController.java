/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.signuppage;

import Entities.Request;
import Entities.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import viewpkg.ViewController;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author alaa
 */
public class FXMLRegistrationController implements Initializable {

    ViewController mainViewController;
    @FXML
    ComboBox<String> genderChooser;
    @FXML
    ComboBox countryChooser;
    @FXML
    DatePicker dateChooser;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    Label firstNameValidation;
    @FXML
    Label lastNameValidation;
    @FXML
    Label emailValidation;
    @FXML
    Label passwordValidation;
    @FXML
    Label confirmPasswordValidation;
    @FXML
    ImageView userImg;
    @FXML
    Button backbtn;
    @FXML
    Button exitbtn;

    boolean imgChosen;
    boolean fname = false, lname = false, email = false, password = false;

    public FXMLRegistrationController() {
    }

    public void setController(ViewController vc) {
        mainViewController = vc;
    }

    @FXML
    public void removeImage() { // include the X image then make this on the 
        userImg.setImage(null);
        if ("Male".equals(genderChooser.getValue())) {
            userImg.setImage(new Image(getClass().getResource("male.png").toString()));
        } else {
            userImg.setImage(new Image(getClass().getResource("female.png").toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateChooser.setValue(LocalDate.now());
        dateChooser.setDayCellFactory((DatePicker param) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

            }

        });
        userImg.setImage(new Image(getClass().getResource("male.png").toString()));
        imgChosen = false;
        genderChooser.getItems().addAll(
                "Male",
                "Female"
        );
        genderChooser.setCellFactory((param) -> {
            return new ListCell<String>() {
                @Override
                public void updateSelected(boolean selected) {
                    super.updateSelected(selected);
                    if (selected) {
                        if (!imgChosen) {
                            if ("Male".equals(getItem())) {
                                userImg.setImage(new Image(getClass().getResource("male.png").toString()));
                            } else {
                                userImg.setImage(new Image(getClass().getResource("female.png").toString()));
                            }
                        }
                    }
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        Text t = new Text(item);
                        setGraphic(t);
                    }
                }

            };
        });
        genderChooser.getSelectionModel().selectFirst();
        countryChooser.getItems().addAll(
                "Egypt",
                "US",
                "German"
        );
        countryChooser.getSelectionModel().selectFirst();
        userImg.setOnMouseClicked((MouseEvent event) -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    imgChosen = true;
                    userImg.setImage(new Image(file.toURI().toURL().toExternalForm()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        backbtn.setOnMouseClicked((MouseEvent event) -> {
            if (InfoFilled("any")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancel Registeration");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to cancel?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("cssbg.css").toExternalForm());
                dialogPane.getStyleClass().add("bgGradient");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    mainViewController.loadSignInWindow();
                }
            } else {
                mainViewController.loadSignInWindow();
            }
        });
        exitbtn.setOnAction((event) -> {
            if (InfoFilled("any")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Application");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to exit?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("cssbg.css").toExternalForm());
                dialogPane.getStyleClass().add("bgGradient");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Stage s = (Stage) exitbtn.getScene().getWindow();
                    s.close();
                    Platform.exit();
                    System.exit(0);
                }
            } else {
                Stage s = (Stage) exitbtn.getScene().getWindow();
                s.close();
                Platform.exit();
                System.exit(0);
            }
        });
        firstName.focusedProperty().addListener((observable) -> {
            if (!firstName.isFocused()) {
                if (!textFieldIsNull(firstName, firstNameValidation)) {
                    if (inValidName(firstName, firstNameValidation)) {
                        fname = false;
                    } else {
                        fname = true;
                    }
                } else {
                    fname = false;
                }
            }
        });
        lastName.focusedProperty().addListener((observable) -> {
            if (!lastName.isFocused()) {
                if (!textFieldIsNull(lastName, lastNameValidation)) {
                    if (inValidName(lastName, lastNameValidation)) {
                        lname = false;
                    } else {
                        lname = true;
                    }
                } else {
                    lname = false;
                }
            }
        });
        emailField.focusedProperty().addListener((observable) -> {
            if (!emailField.isFocused()) {
                if (!textFieldIsNull(emailField, emailValidation)) {
                    inValidMail(emailField, emailValidation);
                }
            }
        });
        passwordField.focusedProperty().addListener((observable) -> {
            if (!passwordField.isFocused()) {
                if (!textFieldIsNull(passwordField, passwordValidation)) {
                    passwordStatus(passwordField, passwordValidation);
                }
            }
        });
        confirmPasswordField.focusedProperty().addListener((observable) -> {
            if (!confirmPasswordField.isFocused()) {
                if (!confirmPasswordField.getText().equals(passwordField.getText())) {
                    confirmPasswordValidation.setText("Not the same password");
                    confirmPasswordField.setStyle("-fx-background-color: red");
                    password = false;
                } else {
                    confirmPasswordValidation.setText("");
                    confirmPasswordField.setStyle("-fx-background-color: transparent, #194d4d;");
                    password = true;
                }
            }
        });
    }

    @FXML
    void SignUpAction(ActionEvent event) {
        if (InfoFilled("all")) {
            User newUser = new User();
            newUser.setfirstName(firstName.getText());
            newUser.setlastName(lastName.getText());
            newUser.setBio("new account");
            newUser.setPassword(passwordField.getText());
            Date d = Date.valueOf(dateChooser.getValue());
            newUser.setBirthDate(d);
            String g = genderChooser.getValue();
            if ("Female".equals(g)) {
                newUser.setGender("f");
            } else {
                newUser.setGender("m");
            }
            newUser.setCountry(countryChooser.getValue().toString());
            newUser.setMail(emailField.getText());
            newUser.setMobile(0);
            BufferedImage image = SwingFXUtils.fromFXImage(userImg.getImage(), null);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", byteArrayOutputStream);
            } catch (IOException ex) {
                Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            }

            newUser.setImage(byteArrayOutputStream.toByteArray());

            Image tempImg = userImg.getImage();
            int index = tempImg.impl_getUrl().lastIndexOf('\\');
            String name = tempImg.impl_getUrl().substring(index + 1);
            int newIndex = name.lastIndexOf('.');
            String extension = name.substring(newIndex + 1);

            newUser.setExtension(extension);
            newUser.setCity("");
            if (mainViewController.signUp(newUser)) {
                System.out.println("Added Successfully");
                mainViewController.loadSignInWindow();
            } else //email already exists
            {
                System.out.println("not added");
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Registration Error");
                ArrayList<Request> req = mainViewController.getRequests();
                tray.setMessage("User already exists");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.seconds(4.0));
            }
        } else {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Registration Error");
            ArrayList<Request> req = mainViewController.getRequests();
            tray.setMessage("Please fill in all fields first");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(4.0));
        }
    }

    public boolean textFieldIsNull(TextField tf, Label label) {
        boolean isNull = false;

        if (tf.getText().isEmpty()) {
            isNull = true;
            label.setText("Please Enter a value");
            tf.setStyle("-fx-background-color: red");
        } else {
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
            label.setText("");
        }
        return isNull;
    }

    public boolean inValidMail(TextField tf, Label label) {
        boolean isNotValid = false;
        if (!tf.getText().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            isNotValid = true;
            label.setText("Invalid Email format");
            tf.setStyle("-fx-background-color: red");
            email = false;
        } else {
            label.setText("");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
            email = true;
        }
        return isNotValid;
    }

    public boolean inValidName(TextField tf, Label label) {
        boolean isNotValid = false;
        if (!tf.getText().matches("^[a-zA-Z]+$")) {
            isNotValid = true;
            label.setText("please Enter CHaracters");
            tf.setStyle("-fx-background-color: red");
        } else {
            label.setText("");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        }
        return isNotValid;
    }

    public boolean passwordStatus(TextField tf, Label label) {
        boolean isNull = false;
        if (tf.getText().matches("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{7,}$")) { //mix of numbers , lowercases , special chars and uppercases  at least 7 chars
            label.setText("Strong");
            label.setStyle(" -fx-text-fill: #4abc36;");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else if (tf.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{5,}$")) { //mix of numbers , lowercases and uppercases  at least 5 chars
            label.setText("Medium");
            label.setStyle(" -fx-text-fill: #b0b235;");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else if (tf.getText().matches("^(?=.*[0-9])(?=.*[a-z]).{3,}$")) {  //mix of numbers and lowercases at least 3 chars
            label.setText("Weak");
            label.setStyle(" -fx-text-fill: #b29b35;");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
        } else {
            isNull = true;
            label.setText("Invalid password format");
            tf.setStyle("-fx-background-color: red");
        }
        return isNull;
    }

    private boolean InfoFilled(String type) {
        if ("all".equals(type)) {
            if (fname && lname && email && password) {
                return true;
            } else {
                return false;
            }
        } else {
            if (fname || lname || email || password) {
                return true;
            } else {
                return false;
            }
        }
    }

    @FXML
    public void BackButton(ActionEvent e) {
        if (InfoFilled("any")) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Cancel Registration");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to cancel?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                mainViewController.loadSignInWindow();
            }
        } else {
            mainViewController.loadSignInWindow();
        }
    }

    @FXML
    public void ExitButton(ActionEvent e) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage s = (Stage) backbtn.getScene().getWindow();
            s.close();
            Platform.exit();
            System.exit(0);
        }

    }
}
