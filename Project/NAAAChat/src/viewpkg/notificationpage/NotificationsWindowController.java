/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.notificationpage;

import Entities.Notification;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import viewpkg.ViewController;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class NotificationsWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ImageView notificationsImg;

    @FXML
    ListView<Notification> notificationsList;

    ViewController mainViewController;

    public void setController(ViewController vc) {
        mainViewController = vc;
        ArrayList<Notification> notArr = getNotifications();
        if (notArr == null || notArr.isEmpty()) {
            System.out.println("no new notifications");
        } else {
            notificationsList.setCellFactory((ListView<Notification> param) -> new ListCell<Notification>() {
                @Override
                protected void updateItem(Notification item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        BorderPane pane = new BorderPane();
                        Label l = new Label(item.getContent());
                        l.setStyle(" -fx-text-fill: #194D4D;");
                        Button cancelbtn = new Button("", new ImageView(getClass().getResource("cancel.png").toString()));
                        cancelbtn.setOnAction((ActionEvent event) -> {
                            mainViewController.removeNotification(item);
                            notificationsList.getItems().remove(item);
                        });

                        cancelbtn.setStyle("-fx-background-color: transparent;");
                        pane.setRight(cancelbtn);
                        pane.setLeft(l);
                        pane.getStyleClass().add("node");
                        setGraphic(pane);
                    }
                }
            });
            notificationsList.getItems().setAll(notArr);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        notificationsImg.setImage(new Image(getClass().getResource("notification.png").toString()));
        notificationsImg.setFitHeight(50);
        notificationsImg.setFitWidth(50);
        notificationsImg.setSmooth(true);
    }

    private ArrayList<Notification> getNotifications() {
        return mainViewController.getNotifications();
    }

    @FXML
    void ExitButton(ActionEvent e) {
        Stage s = (Stage) notificationsImg.getScene().getWindow();
        s.close();
    }
}
