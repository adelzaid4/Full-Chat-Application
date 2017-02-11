/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg;

import Clientpkg.ClientInterface;
import Controllerpkg.Controller;
import Entities.Category;
import Entities.Group;
import Entities.Message;
import Entities.Notification;
import Entities.Request;
import Modelpkg.ConfigurationInfo;
import Entities.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewpkg.categorypkg.FXMLCategoryController;
import viewpkg.editProfile.FXMLeditProfileController;
import viewpkg.grouppkg.FXMLGroupController;
import viewpkg.signuppage.FXMLRegistrationController;
import viewpkg.loginpage.FXMLLoginControllerr;
import viewpkg.notificationpage.NotificationsWindowController;
import viewpkg.pendingpage.PendingsWindowController;
import viewpkg.profilepage.ProfileWindowController;
import viewpkg.requestpage.RequestsWindowController;

/**
 *
 * @author Nour
 */
public class ViewController extends Application {

    Controller clientController;
    Stage stage;
    ProfileWindowController profileControl;
    private static double xOffset = 0;
    private static double yOffset = 0;

    public ViewController() {
        clientController = new Controller(this);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        profileControl = null;
        boolean c = connect();
        if (c) //connected to server
        {
            loadSignInWindow();
        }
    }

    private boolean connect() {
        Dialog<ConfigurationInfo> dialog = new Dialog<>();
        dialog.setTitle("Server Information");
        dialog.setHeaderText("");
        dialog.setResizable(true);
        Label label1 = new Label("IP Address: ");
        Label label2 = new Label("Port: ");
        TextField text1 = new TextField();
        //text1.setText("172.16.2.159"); //at iti
        text1.setText("127.0.0.1"); //local
        //text1.setText("192.168.1.2"); //home//adel
        TextField text2 = new TextField();
        text2.setText("5050");
        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                return new ConfigurationInfo(text1.getText(), Integer.parseInt(text2.getText()));
            }
            return null;
        });
//        DialogPane dialogPane = dialog.getDialogPane();
//        dialogPane.getStylesheets().add(
//                getClass().getResource("cssbg.css").toExternalForm());
//        dialogPane.getStyleClass().add("bgGradient");
        Optional<ConfigurationInfo> result = dialog.showAndWait();
        if (result != null) {
            return clientController.connectToServer(result.get());
        } else {
            return false;
        }
    }

    public boolean signUp(User u) {
        return clientController.SignUp(u);
    }

    public User signIn(User u) {
        return clientController.SignIn(u);
    }

    public void signOut() {
        clientController.signOut();
    }

    public boolean updateUser(User u) {
        return clientController.UpdateUser(u);
    }

    public ArrayList<Notification> getNotifications() {
        return clientController.getNotifications();
    }

    public ArrayList<Request> getRequests() {
        return clientController.getRequests();
    }

    public ArrayList<User> getBlocks(String type) {
        return clientController.getBlocks(type);
    }

    public ArrayList<User> getFriends() {
        return clientController.getFrieds();
    }

    public void removeNotification(Notification n) {
        clientController.removeNotification(n);
    }

    public void setUser(User userInfo) {
        clientController.setUser(userInfo);
    }

    public User getUser() {
        return clientController.getUser();
    }

    public ClientInterface getFriendInterface(User u) {
        return clientController.getFriendInterface(u);
    }

    public ClientInterface getMyInterface() {
        return clientController.getMyInterface();
    }

    public void displayMsg(Message msg, ClientInterface clientInterface, User client) {
        profileControl.displayMsg(msg, clientInterface, client);
    }

    public boolean sendRequest(Request r) {
       return clientController.sendRequest(r);
    }

    public void loadRequestsWindow() {
        FXMLLoader requestLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = requestLoader.load(getClass().getResource(".//requestpage//RequestsWindow.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestsWindowController requestController = requestLoader.getController();
        requestController.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadCreateGroupWindow() {
        FXMLLoader categoryLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = categoryLoader.load(getClass().getResource(".//grouppkg//FXMLGroup.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLGroupController groupControl = categoryLoader.getController();
        groupControl.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        sc.getRoot().getStyleClass().add("tooltip");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadPendingsWindow() {
        FXMLLoader pendingLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = pendingLoader.load(getClass().getResource(".//pendingpage//PendingsWindow.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PendingsWindowController pendingController = pendingLoader.getController();
        pendingController.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadNotificationsWindow() {
        FXMLLoader notificationLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = notificationLoader.load(getClass().getResource(".//notificationpage//NotificationsWindow.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        NotificationsWindowController notificationController = notificationLoader.getController();
        notificationController.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadSingUpWindow() {
        try {
            FXMLLoader signuploader = new FXMLLoader();
            Parent root = signuploader.load(getClass().getResource("signuppage//FXML-Registration.fxml").openStream());
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });
            root.setOnMouseDragged((MouseEvent event1) -> {
                stage.setX(event1.getScreenX() + xOffset);
                stage.setY(event1.getScreenY() + yOffset);
            });
            FXMLRegistrationController signupControl = signuploader.getController();
            signupControl.setController(this);
            Scene sc = new Scene(root);
            //sc.getRoot().getStyleClass().add("rootPane");
            sc.getRoot().getStyleClass().add("tooltip");
            stage.setScene(sc);
            stage.setX(300.0);
            stage.setY(10.0);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadSignInWindow() {
        FXMLLoader signinloader = new FXMLLoader();
        Parent root = null;
        try {
            root = signinloader.load(getClass().getResource("..//viewpkg//loginpage//FXML-Login.fxml").openStream());
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });
            root.setOnMouseDragged((MouseEvent event1) -> {
                stage.setX(event1.getScreenX() + xOffset);
                stage.setY(event1.getScreenY() + yOffset);
            });
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLLoginControllerr signinControl = signinloader.getController();
        signinControl.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        sc.getRoot().getStyleClass().add("tooltip");
        stage.setScene(sc);
        stage.show();
    }

    public void loadEditProfileWindow() {
        FXMLLoader editProfileLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = editProfileLoader.load(getClass().getResource(".//editProfile//FXM-EditProfile.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLeditProfileController editProfileControl = editProfileLoader.getController();
        editProfileControl.setController(this);
        User me=clientController.getUser();
        editProfileControl.setUser(me);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        sc.getRoot().getStyleClass().add("tooltip");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadCreateCategoryWindow() {
        FXMLLoader categoryLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = categoryLoader.load(getClass().getResource(".//categorypkg//FXMLCategory.fxml").openStream());
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLCategoryController categoryControl = categoryLoader.getController();
        categoryControl.setController(this);
        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        sc.getRoot().getStyleClass().add("tooltip");
        Stage st = new Stage();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = st.getX() - event.getScreenX();
            yOffset = st.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            st.setX(event1.getScreenX() + xOffset);
            st.setY(event1.getScreenY() + yOffset);
        });
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.setScene(sc);
        st.show();
    }

    public void loadProfileWindow() {
        FXMLLoader profileLoader = new FXMLLoader();
        BorderPane root = null;
        try {
            root = (BorderPane) profileLoader.load(getClass().getResource(".//profilepage//ProfileWindow.fxml").openStream());
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });
            root.setOnMouseDragged((MouseEvent event1) -> {
                stage.setX(event1.getScreenX() + xOffset);
                stage.setY(event1.getScreenY() + yOffset);
            });
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profileControl = profileLoader.getController();
        profileControl.setController(this);

        Scene sc = new Scene(root);
        //sc.getRoot().getStyleClass().add("rootPane");
        stage.setY(10.0);
        stage.setX(100.0);
        stage.setScene(sc);
        stage.show();
    }

    public void cancelRequest(Request request) {
        clientController.cancelRequest(request);
    }

    public ArrayList<Group> getGroups() {
        return clientController.getGroups();
    }

    public ArrayList<Category> getCategories() {
        return clientController.getCategories();
    }

    public void acceptRequest(Request request) {
        clientController.acceptRequest(request);
    }

    public void updateStatus(User user) {
        clientController.updateStatus(user);
    }

    public boolean recieveFile(File file, ClientInterface friend) {
        return profileControl.recieveFile(file, friend);
    }

    public void hideTab(ClientInterface client) {
        profileControl.hideTab(client);
    }

    public void changeStatus(User u) {
        profileControl.changeStatus(u);
    }

    public void showServerMsg(Message msg, Image img) {
        profileControl.showServerMsg(msg, img);
    }

    public void blockUser(User u) {
        clientController.blockUser(u);
    }

    public void deleteFriend(User u) {
        clientController.deleteFriend(u);
    }

    public void createGroup(Group g) {
        clientController.createGroup(g);
        profileControl.createGroup(g);
    }

    public void createCaegory(Category c) {
        clientController.createCategory(c);
    }

    public void userBecomeOnline(User u) {
        profileControl.changeOnline(u);
    }

    public void notifyMsg(Notification n) {
        profileControl.notifyMsg(n);
    }
}
