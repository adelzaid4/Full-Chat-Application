/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverui;

import Controller.ServerController;
import Controller.StartServer;
import Entities.User;
import Model.DBHandlerpkg.DBAdminHandler;
import Model.DBHandlerpkg.DBUserHandler;
import Model.PrivateDAO.Admin;
import Model.Properties.UserProberty;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 *
 * @author MIDO
 */
public class FXMLDocumentController implements Initializable {

    boolean imgChosen;
    boolean fnameFlag = false, lnameFlag = false, emailFlag = false, passwordFlag = false, bd = false;
    boolean adminNameFlag = false, adminPassFlag = false, adminConfirmFlag = false;
    StartServer startServer;
    boolean startFlag = false;
    DBUserHandler dBUserHandler;
    DBAdminHandler dBAdminHandler;

    ServerController serverController;
    ArrayList<User> allUsers;
    ArrayList<User> onnlinUsers;
    ArrayList<User> offlineUsers;
    ArrayList<Integer> egyptList;
    ArrayList<Integer> usaList;
    ArrayList<Integer> germanyList;
    int EgyptList = 0;
    int UsaList = 0;
    int GermanList = 0;
    int FemaleList = 0;
    int maleList = 0;
    int childList = 0;
    int teenList = 0;
    int youthList = 0;
    int oldList = 0;
    List<Image> img;
    @FXML
    TableView<UserProberty> userTable;
    @FXML
    private PieChart pie;
    @FXML
    private PieChart pieGender;
    @FXML
    private BarChart countryBarChart;
    @FXML
    private BarChart GenderBarChart;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfConfirmPassword;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private ComboBox<String> cbCountry;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private Button btnCreate;
    @FXML
    private ImageView imgProfile;
    @FXML
    private TextArea taMessage;

    @FXML
    private TextField tfAdminName;

    @FXML
    private TextField tfAdminPassword;

    @FXML
    private TextField tfConfirmAdminPass;

    @FXML
    private Button btnAdminCreate;

    @FXML
    private Button btnStartServer;

    @FXML
    private Button btnStopServer;
    @FXML
    private Button btnSend;

    @FXML
    private Label firstNameValidation;

    @FXML
    private Label lastNameValidation;

    @FXML
    private Label emailValidation;

    @FXML
    private Label passwordValidation;

    @FXML
    private Label confirmPasswordValidation;

    @FXML
    private Label adminNameLabel;
    @FXML
    private Label adminConfirmLabel;

    @FXML
    private Label adminPassLabel;

    public FXMLDocumentController() {
        img = new ArrayList<>();
        serverController = new ServerController();
        dBUserHandler = new DBUserHandler();
        dBAdminHandler = new DBAdminHandler();
        startServer = new StartServer();
        onnlinUsers = new ArrayList<>();

    }

    private ObservableList<XYChart.Series<String, Integer>> getChartData() {

        ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();
        Series<String, Integer> aSeries = new Series<String, Integer>();
        //  Series<String, Double> cSeries = new Series<String, Double>();
//        aSeries.setName("a");
//        cSeries.setName("C");
//        

        for (User user : allUsers) {
            if (user.getCountry() != null) {
                if (user.getCountry().equalsIgnoreCase("egypt")) {
                    EgyptList++;
                } else if (user.getCountry().equalsIgnoreCase("us")) {
                    UsaList++;
                } else if (user.getCountry().equalsIgnoreCase("german")) {
                    GermanList++;
                }
            }
        }

        aSeries.setName("Countries");
        aSeries.getData().add(new XYChart.Data("Egypt", EgyptList));
        aSeries.getData().add(new XYChart.Data("USA", UsaList));
        aSeries.getData().add(new XYChart.Data("Germany", GermanList));

        // cSeries.getData().add(new XYChart.Data(Integer.toString(i), cValue));
        //  cValue = cValue + Math.random() - .5;
        answer.add(aSeries);
        return answer;
    }

    private ObservableList<XYChart.Series<String, Integer>> getChart2Data() {

        ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();
        Series<String, Integer> aSeries = new Series<String, Integer>();
        //  Series<String, Double> cSeries = new Series<String, Double>();
//        aSeries.setName("a");
//        cSeries.setName("C");
//        

        for (User user2 : allUsers) {
            if (user2.getBirthDate() != null) {
                if (((LocalDate.now().getYear()) - (user2.getBirthDate().toLocalDate().getYear())) < 12) {
                    childList++;
                } else if (LocalDate.now().getYear() - user2.getBirthDate().toLocalDate().getYear() < 21) {
                    teenList++;
                } else if (LocalDate.now().getYear() - user2.getBirthDate().toLocalDate().getYear() < 35) {
                    youthList++;
                } else {
                    oldList++;
                }
            }
        }

        aSeries.setName("Ages");
        aSeries.getData().add(new XYChart.Data("Children", childList));
        aSeries.getData().add(new XYChart.Data("Teens", teenList));
        aSeries.getData().add(new XYChart.Data("Youths", youthList));
        aSeries.getData().add(new XYChart.Data("olders", oldList));

        // cSeries.getData().add(new XYChart.Data(Integer.toString(i), cValue));
        //  cValue = cValue + Math.random() - .5;
        answer.add(aSeries);
        return answer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        allUsers = dBUserHandler.getAllUSERS();
        onnlinUsers = dBUserHandler.getAllOnlineUsers();
        offlineUsers = dBUserHandler.getAllOfflineUsers();
        taMessage.setDisable(true);
        btnSend.setDisable(true);
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Onnline", onnlinUsers.size()),
                        new PieChart.Data("Offline", offlineUsers.size())
                );

        pie.setData(pieChartData);

        for (User user : allUsers) {
            if (user.getGender().equalsIgnoreCase("f")) {
                FemaleList++;
            } else if (user.getGender().equalsIgnoreCase("m")) {
                maleList++;
            }

        }
        ObservableList<PieChart.Data> pieChartData2
                = FXCollections.observableArrayList(
                        new PieChart.Data("Male", maleList),
                        new PieChart.Data("Female", FemaleList)
                );

        pieGender.setData(pieChartData2);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        countryBarChart.setData(getChartData());
//            
//           CategoryAxis xAxis2 = new CategoryAxis();
//          NumberAxis yAxis2 = new NumberAxis();        

        GenderBarChart.setData(getChart2Data());

        cbGender.getItems().addAll(
                "Male",
                "Female"
        );

        cbCountry.getItems().addAll(
                "Egypt",
                "US",
                "German"
        );
        dpDateOfBirth.setValue(LocalDate.now());
        dpDateOfBirth.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

        imgProfile.setImage(new Image(getClass().getResource("male.png").toString()));
        imgChosen = false;

        cbGender.setCellFactory((param) -> {
            return new ListCell<String>() {
                @Override
                public void updateSelected(boolean selected) {
                    super.updateSelected(selected);
                    if (selected) {
                        if (!imgChosen) {
                            if ("Male".equals(getItem())) {
                                imgProfile.setImage(new Image(getClass().getResource("male.png").toString()));
                            } else {
                                imgProfile.setImage(new Image(getClass().getResource("female.png").toString()));
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

        cbGender.getSelectionModel().selectFirst();
        cbCountry.getSelectionModel().selectFirst();

        imgProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

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
                        imgProfile.setImage(new Image(file.toURI().toURL().toExternalForm()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        tfFirstName.focusedProperty().addListener((observable) -> {
            if (!tfFirstName.isFocused()) {
                if (!textFieldIsNull(tfFirstName, firstNameValidation)) {
                    if (inValidName(tfFirstName, firstNameValidation)) {
                        fnameFlag = false;
                    } else {
                        fnameFlag = true;
                    }
                } else {
                    fnameFlag = false;
                }
            }
        });

        tfAdminName.focusedProperty().addListener((observable) -> {
            if (!tfAdminName.isFocused()) {
                if (!textFieldIsNull(tfAdminName, adminNameLabel)) {
                    if (inValidName(tfAdminName, adminNameLabel)) {
                        adminNameFlag = false;
                    } else {
                        adminNameFlag = true;
                    }
                } else {
                    adminNameFlag = false;
                }
            }
        });
        tfAdminPassword.focusedProperty().addListener((observable) -> {
            if (!tfAdminPassword.isFocused()) {
                if (!textFieldIsNull(tfAdminPassword, adminPassLabel)) {
                    passwordStatus(tfAdminPassword, adminPassLabel);
                }
            }
        });

        tfConfirmAdminPass.focusedProperty().addListener((observable) -> {
            if (!tfConfirmAdminPass.isFocused()) {
                if (!tfConfirmAdminPass.getText().equals(tfAdminPassword.getText())) {
                    confirmPasswordValidation.setText("Not the same password");
                    tfConfirmAdminPass.setStyle("-fx-background-color: red");
                    adminPassFlag = false;
                } else {
                    confirmPasswordValidation.setText("");
                    tfConfirmAdminPass.setStyle("-fx-background-color: transparent, #194d4d;");
                    adminPassFlag = true;
                }
            }
        });

        tfLastName.focusedProperty().addListener((observable) -> {
            if (!tfLastName.isFocused()) {
                if (!textFieldIsNull(tfLastName, lastNameValidation)) {
                    if (inValidName(tfLastName, lastNameValidation)) {
                        lnameFlag = false;
                    } else {
                        lnameFlag = true;
                    }
                } else {
                    lnameFlag = false;
                }
            }
        });

        tfMail.focusedProperty().addListener((observable) -> {
            if (!tfMail.isFocused()) {
                if (!textFieldIsNull(tfMail, emailValidation)) {
                    inValidMail(tfMail, emailValidation);
                }
            }
        });

        tfPassword.focusedProperty().addListener((observable) -> {
            if (!tfPassword.isFocused()) {
                if (!textFieldIsNull(tfPassword, passwordValidation)) {
                    passwordStatus(tfPassword, passwordValidation);
                }
            }
        });

        tfConfirmPassword.focusedProperty().addListener((observable) -> {
            if (!tfConfirmPassword.isFocused()) {
                if (!tfConfirmPassword.getText().equals(tfPassword.getText())) {
                    confirmPasswordValidation.setText("Not the same password");
                    tfConfirmPassword.setStyle("-fx-background-color: red");
                    passwordFlag = false;
                } else {
                    confirmPasswordValidation.setText("");
                    tfConfirmPassword.setStyle("-fx-background-color: transparent, #194d4d;");
                    passwordFlag = true;
                }
            }
        });

        TableColumn Name = new TableColumn("Name");
        TableColumn fname = new TableColumn("First Name");
        fname.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("fname"));
        TableColumn lname = new TableColumn("Last Name");
        lname.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("lname"));
        Name.getColumns().addAll(fname, lname);
        TableColumn gender = new TableColumn("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("gender"));
        TableColumn country = new TableColumn("Country");
        country.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("country"));

        TableColumn bio = new TableColumn("Bio");
        bio.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("bio"));
        bio.setMaxWidth(100);
        bio.setEditable(true);
        TableColumn mail = new TableColumn("Mail");
        mail.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("mail"));
        TableColumn status = new TableColumn("Status");
        status.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("status"));
//        TableColumn birthDate = new TableColumn("Birth Date");
//        birthDate.setCellValueFactory(new PropertyValueFactory<UserProberty,String>("birthDate"));
        TableColumn offline = new TableColumn("Offline");
        offline.setCellValueFactory(new PropertyValueFactory<UserProberty, String>("offline"));

        //image
        userTable.setTableMenuButtonVisible(true);
        userTable.editableProperty().set(true);
        userTable.setEditable(true);

        for (int i = 0; i < userTable.getColumns().size(); i++) {
            //TableColumn<UserProberty> get = userTable.getColumns().get(i);

//        userTable.getColumns().get(0).setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent>() {
//            @Override
//            public void handle(Event event) {
//               
//            }
//        });
        }
        userTable.getColumns().addAll(Name, mail, country, gender, status, offline, bio);

        ArrayList<User> users = dBUserHandler.getAllUSERS();
        final ObservableList<UserProberty> usersProberty = FXCollections.observableArrayList();
        if (users == null) {

        } else {

            for (int i = 0; i < users.size(); i++) {
                UserProberty proberty = new UserProberty(users.get(i));
                usersProberty.add(proberty);
            }
        }
        userTable.setItems(usersProberty);

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
            emailFlag = false;
        } else {
            label.setText("");
            tf.setStyle("-fx-background-color: transparent, #194d4d;");
            emailFlag = true;
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

    public void createUser() {
        if (InfoFilled()) {
            User newUser = new User();
            newUser.setfirstName(tfFirstName.getText());
            newUser.setlastName(tfLastName.getText());
            newUser.setBio("new account");
            Date d = Date.valueOf(dpDateOfBirth.getValue());
            newUser.setBirthDate(d);
            String g = cbGender.getValue();
            if ("Female".equals(g)) {
                newUser.setGender("f");
            } else {
                newUser.setGender("m");
            }
            newUser.setCountry(cbCountry.getValue());
            newUser.setMail(tfMail.getText());
            newUser.setMobile(0);
            newUser.setPicPath("");
            newUser.setCity("");
            newUser.setPassword(tfPassword.getText());
            BufferedImage image = SwingFXUtils.fromFXImage(imgProfile.getImage(), null);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", byteArrayOutputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            newUser.setImage(byteArrayOutputStream.toByteArray());
            //newUser.setImage(imgProfile.getImage());
            boolean check = dBUserHandler.checkUserExistance(newUser);
            if (check) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText(null);
                alert.setContentText("User is already exist");
                alert.show();

            } else {
                if (serverController.addUser(newUser)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("DONE");
                    alert.setHeaderText(null);
                    alert.setContentText("User Created");
                    alert.show();
                    tfConfirmPassword.clear();
                    tfFirstName.clear();
                    tfLastName.clear();
                    tfPassword.clear();
                    tfMail.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setHeaderText(null);
                    alert.setContentText("There was a problem in saving the user!");
                    alert.show();
                }
            }

            //Pop up to notify server about saving user
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("Please,Enter the required data!");
            alert.show();
        }
    }

    public void createAdmin() {
        if (adminInfoFilled()) {
            Admin newAdmin = new Admin();
            newAdmin.setPassword(tfAdminPassword.getText());
            newAdmin.setUserName(tfAdminName.getText());
            boolean check = dBAdminHandler.checkAdminExistance(newAdmin);
            if (check) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText(null);
                alert.setContentText("Admin is already exist");
                alert.show();

            } else {
                serverController.addAdmin(newAdmin);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("DONE");
                alert.setHeaderText(null);
                alert.setContentText("Admin Created");
                alert.show();
                tfAdminPassword.clear();
                tfAdminName.clear();
                tfConfirmAdminPass.clear();

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("Please,Enter the required data!");
            alert.show();
        }
    }

    private boolean InfoFilled() {

        if (fnameFlag && lnameFlag && emailFlag && passwordFlag) {
            return true;
        } else {
            return false;
        }

    }

    private boolean adminInfoFilled() {

        if (adminNameFlag && adminPassFlag) {
            return true;
        } else {
            return false;
        }

    }

    public void startServer() {
        startServer.start();
        btnStopServer.setDisable(false);
        btnStartServer.setDisable(true);
        taMessage.setDisable(false);
        btnSend.setDisable(false);
        startFlag = true;
    }

    public void StopServer() {
        startServer.stop();
        btnStopServer.setDisable(true);
        taMessage.setDisable(true);
        btnSend.setDisable(true);
        btnStartServer.setDisable(false);
    }

    @FXML
    public void sendBroadMsg() {

        serverController.sendBroadCastMsg(taMessage.getText());
    }

}
