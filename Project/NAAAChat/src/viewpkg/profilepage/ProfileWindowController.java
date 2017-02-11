/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.profilepage;

import Clientpkg.ClientInterface;
import Entities.Category;
import Entities.Group;
import Entities.Message;
import Entities.Notification;
import Entities.Request;
import Entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import viewpkg.ViewController;
import viewpkg.chattingpage.ChatingWindowController;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class ProfileWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ImageView profile;
    @FXML
    ComboBox<String> status;
    @FXML
    TreeView<Object> friendsTree;
    @FXML
    TabPane chatsPane;
    @FXML
    TreeView<Group> groupsView;
    @FXML
    MenuItem reqitem;
    @FXML
    MenuItem penditem;
    @FXML
    MenuItem notitem;
    @FXML
    MenuItem edprfitem;
    @FXML
    MenuItem logoutitem;
    @FXML
    TextField bio;
    @FXML
    Label friendsNum;
    @FXML
    AnchorPane startpane;
    @FXML
    MenuItem catitem;

    ViewController mainViewController;
    ArrayList<User> friends;
    ArrayList<User> blocked;
    ArrayList<Group> groups;
    ArrayList<Category> categories;
    ObservableList<TreeItem<Object>> categoriesObservable;
    ObservableList<Category> categoriesList;
    ArrayList<Group> chattingGroups;
    ArrayList<ChatingWindowController> groupControllers;
    User me;
    ArrayList<User> chattingFrineds;
    ArrayList<ChatingWindowController> chattingFrinedsControllers;
    ArrayList<Tab> chattingObjects;

    public void setController(ViewController vc) {
        mainViewController = vc;
        getInfo();
        friendsTree.setCellFactory((TreeView<Object> param) -> new TreeCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    if (item instanceof User) {
                        User u = (User) item;
                        BorderPane borderPane = new BorderPane();
                        BorderPane statusPane = new BorderPane();
                        // Center Border
                        VBox middleBox = new VBox();
                        middleBox.spacingProperty().set(5);
                        Label nameLabel = new Label();
                        nameLabel.getStyleClass().add("text");
                        nameLabel.setText(" " + u.getFullName());
                        statusPane.setTop(nameLabel);
                        Label status = new Label();
                        status.setText(u.getStatus());
                        status.getStyleClass().add("text");
                        Circle statusCircle = new Circle(30, 30, 5);
                        if (u.isOffline() == 1) {
                            statusCircle.setFill(Color.RED);
                        } else {
                            statusCircle.setFill(Color.GREEN);
                        }
                        HBox statusBox = new HBox();
                        statusBox.spacingProperty().set(3);
                        statusBox.getChildren().addAll(statusCircle, status);
                        statusBox.setAlignment(Pos.CENTER);
                        statusPane.setCenter(statusBox);

                        borderPane.setCenter(statusPane);
                        if (u.isOffline() == 1) {
                            middleBox.setDisable(true);
                        }
                        //left border
                        ImageView imagView;
                        if ("m".equals(u.getGender())) {
                            imagView = new ImageView(getClass().getResource("male.png").toString());
                        } else {
                            imagView = new ImageView(getClass().getResource("female.png").toString());
                        }
                        imagView.setFitHeight(25);
                        imagView.setFitWidth(20);
                        borderPane.setLeft(imagView);
                        //right border
                        MenuItem blockItem = new MenuItem("Block");
                        blockItem.setOnAction((event) -> {
                            mainViewController.blockUser(u);
                        });
                        MenuItem removeItem = new MenuItem("Remove");
                        removeItem.setOnAction((event) -> {
                            mainViewController.deleteFriend(u);
                            friends.remove(u);
                        });
                        MenuButton buttonMenu = new MenuButton("", null, blockItem, removeItem);
                        buttonMenu.setTextFill(Color.WHITE);
                        buttonMenu.setStyle("font-size:1px");
                        buttonMenu.getStyleClass().add("settings");
                        borderPane.setRight(buttonMenu);

                        borderPane.getStyleClass().add("node");
                        setGraphic(borderPane);
                    }
                    if (item instanceof Category) {
                        Category c = (Category) item;
                        Label categoryName = new Label(c.getName());
                        setGraphic(categoryName);
                    }
                } else {
                    setGraphic(null);
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    if (getItem() instanceof User) {
                        User u = (User) getItem();
                        if (u.isOffline() == 0) {
                            System.out.println("in openchat select");
                            newChat(u);
                        }
                    }
                }
            }

        });
        friendsTree.getStyleClass().add("ListViewStyle");
        groupsView.setCellFactory((TreeView<Group> param) -> new TreeCell<Group>() {
            @Override
            protected void updateItem(Group item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    Label GroupName = new Label(item.getName());
                    setGraphic(GroupName);
                } else {
                    setGraphic(null);
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                Group g = getItem();
                openGroup(g);
            }
        });

        ObservableList<TreeItem<Group>> groupsOb = getGroups();
        TreeItem<Group> rootGroup = new TreeItem<>(new Group());
        rootGroup.setExpanded(true);
        rootGroup.getChildren().addAll(groupsOb);
        groupsView.setRoot(rootGroup);

        ObservableList<TreeItem<Object>> categoriesOb = getCategory();

        TreeItem<Object> rootItem = new TreeItem<>();
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(categoriesOb);
        friendsTree.setRoot(rootItem);
        status.setCellFactory((ListView<String> param) -> new ListCell<String>() {
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    mainViewController.getUser().setStatus(getItem());
                    mainViewController.updateStatus(mainViewController.getUser());
                }
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new Text(item));
                }
            }

        });
        bio.focusedProperty().addListener((observable) -> {
            if (!bio.isFocused()) {
                if (!bio.getText().equals(me.getBio())) {
                    me.setBio(bio.getText());
                    mainViewController.updateUser(me);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chatsPane.getTabs().get(0).setId("0");
        chatsPane.getStyleClass().add("rootPane");
        chatsPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        chattingGroups = new ArrayList<>();
        groupControllers = new ArrayList<>();
        chattingFrineds = new ArrayList<>();
        chattingFrinedsControllers = new ArrayList<>();
        chattingObjects = new ArrayList<>();
        status.getItems().setAll("available", "busy", "away", "appear offline");
        reqitem.setOnAction((ActionEvent event) -> {
            mainViewController.loadRequestsWindow();
        });
        penditem.setOnAction((ActionEvent event) -> {
            mainViewController.loadPendingsWindow();
        });
        notitem.setOnAction((ActionEvent event) -> {
            mainViewController.loadNotificationsWindow();
        });
        edprfitem.setOnAction((ActionEvent event) -> {
            mainViewController.loadEditProfileWindow();
        });
        catitem.setOnAction((ActionEvent event) -> {
            mainViewController.loadCreateCategoryWindow();
        });
        logoutitem.setOnAction((ActionEvent event) -> {
            mainViewController.signOut();
            mainViewController.loadSignInWindow();
        });
        profile.setOnMouseClicked((MouseEvent event) -> {
            Stage s = new Stage();
            ImageView fullimg = new ImageView(profile.getImage());
            BorderPane pane = new BorderPane(fullimg);
            Scene sc = new Scene(pane);
            s.setTitle("Personal Photo");
            s.setScene(sc);
            s.show();
        });

    }

    @FXML
    void addFriend() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add new Friend");
        dialog.setHeaderText(null);
        dialog.setContentText("Friend Email:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
            User to = new User();
            to.setMail(result.get());
            Request r = new Request();
            r.setToUser(to);
            r.setFromUser(mainViewController.getUser());
            if (!mainViewController.sendRequest(r)) {
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Friend Request Failure");
                ArrayList<Request> req = mainViewController.getRequests();
                tray.setMessage("This email doesn't exist");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.seconds(2.0));
            } else {
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Friend Request Stauts");
                ArrayList<Request> req = mainViewController.getRequests();
                tray.setMessage("your request is sent");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.seconds(2.0));
            }

        }

    }

    @FXML
    void addGroup() {
        mainViewController.loadCreateGroupWindow();

    }

    void openGroup(Group g) {
        if (chattingGroups.indexOf(g) == -1) {
            chattingGroups.add(g);
            FXMLLoader chatLoader = new FXMLLoader();
            try {
                Parent root = chatLoader.load(getClass().getResource("..//chattingpage//ChatingWindow.fxml").openStream());

                ChatingWindowController chatController = chatLoader.getController();
                chatController.setFriend(null, null);
                groupControllers.add(chatController);
                chatController.setController(mainViewController, false, null, null);

                Stage st = new Stage();
                Scene sc = new Scene(root);
                //sc.getRoot().getStyleClass().add("rootPane");
                st.setScene(sc);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void newChat(User u) {
        if (checkChatOpen(u) == -1) {
            if (!blockingMe(u)) {
                Tab t = new Tab(u.getFullName());
                t.setId(String.valueOf(u.getId()));
                FXMLLoader chatLoader = new FXMLLoader();
                try {
                    Parent root = chatLoader.load(getClass().getResource("..//chattingpage//ChatingWindow.fxml").openStream());
                    ChatingWindowController chatController = chatLoader.getController();
                    ClientInterface inter = mainViewController.getFriendInterface(u);
                    chattingFrineds.add(u);
                    chattingFrinedsControllers.add(chatController);
                    chatController.setFriend(inter, u);
                    chatController.setController(mainViewController, false, null, null);
                    //t.getStyleClass().add("rootPane");
                    t.setUserData(chatController);
                    t.setContent(root);
                    t.setClosable(true);
                    t.setOnCloseRequest((Event arg0) -> {
                        chattingFrineds.remove(u);
                        chattingFrinedsControllers.remove(chatController);
                        chatsPane.getTabs().remove(t);
                    });
                    chattingObjects.add(t);
                } catch (IOException ex) {
                    Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                chatsPane.getTabs().add(t);
                chatsPane.getSelectionModel().select(t);
            }
        }
    }

    private void getInfo() {
        me = mainViewController.getUser();
        blocked = mainViewController.getBlocks("blockMe");
        friends = mainViewController.getFriends();
        groups = mainViewController.getGroups();
        categories = mainViewController.getCategories();
        //profile.setImage(me.getImage());
        if ("m".equals(me.getGender())) {
            profile.setImage(new Image(getClass().getResource("male.png").toString()));
        } else {
            profile.setImage(new Image(getClass().getResource("female.png").toString()));
        }
        status.getSelectionModel().select(me.getStatus());
        bio.setText(me.getBio());
        friendsNum.setText(friends.size() + " Friends");

        TrayNotification tray = new TrayNotification();
        tray.setTitle("Welcome " + me.getfirstName());
        ArrayList<Request> req = getRequests();
        tray.setMessage("You have " + req.size() + " new Requests");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.seconds(4.0));
        final URL notify = getClass().getResource("notify.mp3");
        final AudioClip clip = new AudioClip(notify.toString());
        clip.play();

    }

    private boolean blockingMe(User u) {
        for (int i = 0; i < blocked.size(); i++) {
            if (blocked.get(i) == u) {
                return true;
            }
        }
        return false;
    }

    private int checkChatOpen(User u) {
        for (int i = 0; i < chattingFrineds.size(); i++) {
            if (chattingFrineds.get(i).getId() == u.getId()) {
                return i;
            }
        }
        return -1;
    }

    public void displayMsg(Message msg, ClientInterface clientInterface, User client) {
        final URL notify = getClass().getResource("notify.mp3");
        final AudioClip clip = new AudioClip(notify.toString());
        clip.play();
        int index = checkChatOpen(client);
        if (index != -1) {
            ChatingWindowController cont = chattingFrinedsControllers.get(index);
            cont.reloadWeb(msg, "you");
            chattingFrinedsControllers.add(index, cont);
        } else {
            final Tab t = new Tab(client.getFullName());
            t.setId(String.valueOf(client.getId()));
            FXMLLoader chatLoader = new FXMLLoader();
            Platform.runLater(() -> {
                Parent root = null;
                try {
                    root = chatLoader.load(ProfileWindowController.this.getClass().getResource("..//chattingpage//ChatingWindow.fxml").openStream());
                    //root.getStyleClass().add("rootPane");
                } catch (IOException ex) {
                    Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ChatingWindowController chatController = chatLoader.getController();
                chatController.setFriend(clientInterface, client);
                chatController.setController(mainViewController, true, msg, "you");

                chattingFrineds.add(client);
                chattingFrinedsControllers.add(chatController);
                chattingObjects.add(t);
                t.setUserData(chatController);
                t.setContent(root);
                //t.getStyleClass().add("rootPane");
                t.setClosable(true);
                t.setOnCloseRequest((Event arg0) -> {
                    chattingFrineds.remove(client);
                    chattingFrinedsControllers.remove(chatController);
                    chatsPane.getTabs().remove(t);
                });
                chatsPane.getTabs().add(t);
            });
        }
    }

    public boolean recieveFile(File file, ClientInterface clientInterface) {
        boolean res = false;
        try {
            User client = clientInterface.getUser();
            int index = checkChatOpen(client);
            if (index != -1) {
                ChatingWindowController cont = chattingFrinedsControllers.get(index);
                cont.showFile(file, "you");
                res = true;
            } else {
                Tab t = new Tab(client.getFullName());
                t.setId(String.valueOf(client.getId()));
                FXMLLoader chatLoader = new FXMLLoader();
                try {
                    Parent root = chatLoader.load(getClass().getResource("..//chattingpage//ChatingWindow.fxml").openStream());
                    // root.getStyleClass().add("rootPane");
                    ChatingWindowController chatController = chatLoader.getController();
                    chatController.setFriend(clientInterface, client);
                    chatController.setController(mainViewController, false, null, null);
                    chatController.showFile(file, "you");
                    t.setUserData(chatController);
                    t.setContent(root);
                    t.setClosable(true);
                    chattingFrineds.add(client);
                    chattingFrinedsControllers.add(chatController);
                    chattingObjects.add(t);
                    t.setOnCloseRequest((Event arg0) -> {
                        chattingFrineds.remove(client);
                        chattingFrinedsControllers.remove(chatController);
                        chatsPane.getTabs().remove(t);
                    });
                    res = true;
                } catch (IOException ex) {
                    Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);

                }
                chatsPane.getTabs().add(t);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public void hideTab(ClientInterface client) {
    }

    // Lolii's
    private ObservableList<TreeItem<Object>> getCategory() {
        ObservableList<TreeItem<Object>> categoriesObservable = FXCollections.observableArrayList();
        //Others Category
        Category others = new Category();
        others.setCategoryCreator(me);
        others.setName("Others");
        others.setCategoryMembers(getUnCategorizegFriends());
        others.setCreatorUnique(null);
        categories.add(others);
        for (int i = 0; i < categories.size(); i++) {
            TreeItem<Object> treeItemCategory = new TreeItem(categories.get(i));
            for (int j = 0; j < categories.get(i).getCategoryMembers().size(); j++) {
                TreeItem<Object> treeItemFriend = new TreeItem(categories.get(i).getCategoryMembers().get(j));
                if (categories.get(i).getName().equals("Others")) {
                    treeItemCategory.setExpanded(true);
                }
                treeItemCategory.getChildren().add(treeItemFriend);
            }
            categoriesObservable.add(treeItemCategory);
        }
        return categoriesObservable;
    }

    void refreshFriends() {
        Platform.runLater(() -> {
            friends = mainViewController.getFriends();
            ObservableList<TreeItem<Object>> categoriesOb = getCategory();

            TreeItem<Object> rootItem = new TreeItem<>();
            rootItem.setExpanded(true);
            rootItem.getChildren().addAll(categoriesOb);
            friendsTree.setRoot(rootItem);
        });

    }

    ArrayList<User> getUnCategorizegFriends() {
        ArrayList<User> unCategorized = new ArrayList<>();
        for (int f = 0; f < friends.size(); f++) {
            User friend = friends.get(f);
            boolean found = false;
            for (int c = 0; c < categories.size(); c++) {
                for (int l = 0; l < categories.get(c).getCategoryMembers().size(); l++) {
                    User u = categories.get(c).getCategoryMembers().get(l);
                    if (u.getMail().equals(friend.getMail())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                unCategorized.add(friend);
            }
        }
        return unCategorized;
    }

    //Lolii's
    private ObservableList<TreeItem<Group>> getGroups() {
        ObservableList<TreeItem<Group>> groupsObservable = FXCollections.observableArrayList();
        for (int i = 0; i < groups.size(); i++) {
            TreeItem<Group> treeItemCategory = new TreeItem(groups.get(i));
            groupsObservable.add(treeItemCategory);
        }
        return groupsObservable;
    }

    public void changeOnline(User u) {
        boolean found = false;
        if (categoriesList != null) {
            for (int i = 0; i < categoriesList.size(); i++) {
                Category get = categoriesList.get(i);
                for (int j = 0; j < get.getCategoryMembers().size(); j++) {
                    User get1 = get.getCategoryMembers().get(j);
                    if (get1.getId() == u.getId()) {
                        get1.setOffline(u.isOffline());
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            ArrayList<User> notcategories = getUnCategorizegFriends();
            if (notcategories != null) {
                for (int i = 0; i < notcategories.size(); i++) {
                    User get = notcategories.get(i);
                    if (get.getId() == u.getId()) {
                        get.setOffline(u.isOffline());
                        found = true;
                    }
                }
            }
        }

        if (found && u.isOffline() == 0) {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Friend Status");
            tray.setMessage("your friend " + u.getFullName() + " is Online");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(4.0));
        }
        refreshFriends();
    }

    public void changeStatus(User user) {
        for (int i = 0; i < categoriesList.size(); i++) {
            Category get = categoriesList.get(i);
            for (int j = 0; j < get.getCategoryMembers().size(); j++) {
                User get1 = get.getCategoryMembers().get(j);
                if (get1.getId() == user.getId()) {
                    get1.setStatus(user.getStatus());
                }
            }
        }
        refreshFriends();
    }

    public void showServerMsg(Message msg, Image img) {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("NAAA Adimin Message");
            tray.setMessage(msg.getBody());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(4.0));

            final URL notify = getClass().getResource("notify.mp3");
            final AudioClip clip = new AudioClip(notify.toString());
            clip.play();
        });
    }

    public void createGroup(Group g) {
        groups.add(g);
    }

    public void notifyMsg(Notification n) {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("New Notification");
            tray.setMessage(n.getContent());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(4.0));

            final URL notify = getClass().getResource("notify.mp3");
            final AudioClip clip = new AudioClip(notify.toString());
            clip.play();
        });
        refreshFriends();
    }

    private ArrayList<Request> getRequests() {
        User me = mainViewController.getUser();
        ArrayList<Request> allreq = mainViewController.getRequests();
        ArrayList<Request> result = new ArrayList<>();
        if (allreq != null) {
            for (int i = 0; i < allreq.size(); i++) {
                if (allreq.get(i).getToUser().getId() == me.getId()) {
                    result.add(allreq.get(i));
                }
            }
        }
        return result;
    }

} // end class
