/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.pendingpage;

import Entities.Request;
import Entities.User;
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
public class PendingsWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ImageView pendingimg;

    @FXML
    ListView<Request> pendingsList;

    ArrayList<Request> users;
    ViewController mainViewController;

    public void setController(ViewController vc) {
        mainViewController = vc;
        users = getPendings();
        if (users == null || users.isEmpty()) {
            System.out.println("no pendings");
        } else {
            pendingsList.setCellFactory((ListView<Request> param) -> new ListCell<Request>() {
                @Override
                protected void updateItem(Request item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        BorderPane pane = new BorderPane();
                        //Image img = item.getToUser().getImage();
                        Image img=new Image(getClass().getResource("pending.png").toString());
                        ImageView iv = new ImageView(img);
                        iv.setFitHeight(50);
                        iv.setFitWidth(50);
                        iv.setSmooth(true);
                        Label l = new Label("You requested to add " + item.getToUser().getFullName());
                        l.setStyle(" -fx-text-fill: #194D4D;");
                        Button cancelbtn = new Button("", new ImageView(getClass().getResource("cancel.png").toString()));
                        cancelbtn.setOnAction((ActionEvent event) -> {
                            pendingsList.getItems().remove(item);
                            mainViewController.cancelRequest(item);
                        });
                        cancelbtn.setStyle("-fx-background-color: transparent;");
                        pane.setLeft(iv);
                        pane.setRight(cancelbtn);
                        pane.setCenter(l);
                        pane.setStyle("-fx-background-color: transparent;");
                        setGraphic(pane);
                    }
                }

            });
            pendingsList.getItems().setAll(users);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pendingimg.setImage(new Image(getClass().getResource("pending.png").toString()));
        pendingimg.setFitHeight(50);
        pendingimg.setFitWidth(50);
        pendingimg.setSmooth(true);
        users = null;
    }

    private ArrayList<Request> getPendings() {
        User u = mainViewController.getUser();
        ArrayList<Request> req = mainViewController.getRequests();
        ArrayList<Request> result = new ArrayList<>();
        if (req != null) {
            for (int i = 0; i < req.size(); i++) {
                if (req.get(i).getFromUser().getId() == u.getId()) {
                    result.add(req.get(i));
                }
            }
        }
        return result;
    }

    @FXML
    void ExitButton(ActionEvent e) {
        Stage s = (Stage) pendingimg.getScene().getWindow();
        s.close();
    }

}
