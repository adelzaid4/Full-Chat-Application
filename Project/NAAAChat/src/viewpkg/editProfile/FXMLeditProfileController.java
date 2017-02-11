/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.editProfile;

import Entities.User;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import viewpkg.ViewController;

/**
 * FXML Controller class
 *
 * @author alaa
 */
public class FXMLeditProfileController implements Initializable {

    ViewController mainViewController;
    User currentUser;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField emailFeild;
    @FXML
    PasswordField passwordFeild;
    @FXML
    DatePicker dateChooser;
    @FXML
    ComboBox<String> countryChooser;
    @FXML
    ImageView userImg;
    // validations 
    @FXML
    Label firstNameValidation;
    @FXML
    Label lastNameValidation;
    @FXML
    Label passwordValidation;

    boolean fname = false, lname = false, pass = false;

    public void setController(ViewController vc) {
        mainViewController = vc;
        currentUser = new User();

    }

    @FXML
    public void removeImage() {
       userImg.setImage(null);
        if ("male".equals(currentUser.getGender().toLowerCase())||"m".equals(currentUser.getGender().toLowerCase())) {
            userImg.setImage(new Image(getClass().getResource("male.png").toString()));
        } else {
            userImg.setImage(new Image(getClass().getResource("female.png").toString()));
        }
    }

    public void setUser(User u) {
        currentUser = u;
        firstName.setText(currentUser.getfirstName());
        lastName.setText(currentUser.getlastName());
        emailFeild.setText(currentUser.getMail());
        passwordFeild.setText(currentUser.getPassword());
        dateChooser.setValue(currentUser.getBirthDate().toLocalDate());
        countryChooser.setValue(currentUser.getCountry());
        if ("Male".equals(currentUser.getGender()) || "m".equals(currentUser.getGender())) {
            userImg.setImage(new Image(getClass().getResource("male.png").toString()));
        } else {
            userImg.setImage(new Image(getClass().getResource("female.png").toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countryChooser.getItems().addAll(
                "Egypt",
                "US",
                "German"
        );
        countryChooser.setEditable(true);
        dateChooser.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                    }

                };
            }
        });
//        userImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//                FileChooser fileChooser = new FileChooser();
//
//                //Set extension filter
//                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
//                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
//
//                //Show open file dialog
//                File file = fileChooser.showOpenDialog(null);
//                if (file != null) {
//                    try {
//                        userImg.setImage(new Image(file.toURI().toURL().toExternalForm()));
//
//                    } catch (MalformedURLException ex) {
//                        Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            }
//        });
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
        passwordFeild.focusedProperty().addListener((observable) -> {
            if (!passwordFeild.isFocused()) {
                if (!textFieldIsNull(passwordFeild, passwordValidation)) {

                    if (!passwordStatus(passwordFeild, passwordValidation)) {
                        pass = true;
                    } else {
                        pass = false;
                    }
                } else {
                    pass = false;
                }
            }
        });
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
            if (firstName.getText().isEmpty() && lastName.getText().isEmpty()&& passwordFeild.getText().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            if (fname || lname || pass) {
                return true;
            } else {
                return false;
            }
        }
    }

    @FXML
    void updateInfo() {
        if (InfoFilled("all")) {
            User newUser = currentUser;
            newUser.setfirstName(firstName.getText());
            newUser.setlastName(lastName.getText());
            newUser.setPassword(passwordFeild.getText());
            newUser.setCountry(countryChooser.getValue());
            Date d = Date.valueOf(dateChooser.getValue());
            newUser.setBirthDate(d);
            //newUser.setImage(userImg.getImage());
            if (userImg.getImage() != null) {
                Image tempImg = userImg.getImage();
                int index = tempImg.impl_getUrl().lastIndexOf('\\');
                String name = tempImg.impl_getUrl().substring(index + 1);
                int newIndex = name.lastIndexOf('.');
                String extension = name.substring(newIndex + 1);
                newUser.setExtension(extension);
            }
            mainViewController.updateUser(newUser);
            Stage stage = (Stage) userImg.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Update Info Error");
            alert.setHeaderText("");
            alert.setContentText("Please fill all fields first");
            alert.showAndWait();
        }
    }

    @FXML
    void ExitButton(ActionEvent e) {
        Stage s = (Stage) userImg.getScene().getWindow();
        s.close();
    }

}
