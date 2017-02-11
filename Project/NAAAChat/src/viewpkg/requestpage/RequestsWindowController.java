/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.requestpage;

import Entities.Request;
import Entities.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import viewpkg.ViewController;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class RequestsWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Pagination requestsPage;

    @FXML
    ImageView requestImg;

    ViewController mainViewController;

    public void setController(ViewController vc) {
        mainViewController = vc;
        ArrayList<Request> requests = getRequests();
        if (requests == null || requests.isEmpty()) {
            System.out.println("no new requests");
        } else {
            requestsPage.setPageCount(requests.size());
            requestsPage.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer param) {
                    return createPage(param);
                }

                private VBox createPage(Integer param) {
                    //Image img = requests.get(param).getFromUser().getImage();
                    Image img ;
                    if("m".equals(requests.get(param).getFromUser().getGender()))
                         img = new Image(getClass().getResource("male.png").toString());
                    else
                        img = new Image(getClass().getResource("female.png").toString());
                    ImageView iv = new ImageView(img);
                    iv.setFitHeight(150);
                    iv.setFitWidth(150);
                    iv.setSmooth(true);
                    Label name = new Label(requests.get(param).getFromUser().getFullName());
                    name.setStyle("-fx-text-fill: white;-fx-font-size:18px;-fx-font-weight: bold;");
                    FlowPane pane = new FlowPane();
                    pane.setAlignment(Pos.CENTER);
                    Button acceptbtn = new Button("", new ImageView(getClass().getResource("accept.png").toString()));
                    acceptbtn.setOnAction((ActionEvent event) -> {
                        mainViewController.acceptRequest(requests.get(param));
                        requests.remove(requests.get(param));
                        Stage s = (Stage) requestImg.getScene().getWindow();
                        s.close();
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Friend Request Stauts");
                        ArrayList<Request> req = mainViewController.getRequests();
                        tray.setMessage("accepted");
                        tray.setNotificationType(NotificationType.SUCCESS);
                        tray.showAndDismiss(Duration.seconds(2.0));
                    });
                    acceptbtn.setStyle("-fx-background-color: transparent;");
                    Button rejectbtn = new Button("", new ImageView(getClass().getResource("cancel.png").toString()));
                    rejectbtn.setOnAction((ActionEvent event) -> {
                        mainViewController.cancelRequest(requests.get(param));
                        requests.remove(requests.get(param));
                        Stage s = (Stage) requestImg.getScene().getWindow();
                        s.close();
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Friend Request Stauts");
                        ArrayList<Request> req = mainViewController.getRequests();
                        tray.setMessage("rejected");
                        tray.setNotificationType(NotificationType.SUCCESS);
                        tray.showAndDismiss(Duration.seconds(2.0));
                    });
                    rejectbtn.setStyle("-fx-background-color: transparent;");
                    pane.getChildren().setAll(acceptbtn, rejectbtn);
                    VBox box = new VBox(name, iv, pane);
                    box.setAlignment(Pos.CENTER);
                    box.setSpacing(10);
                    return box;
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        requestImg.setImage(new Image(getClass().getResource("req.png").toString()));
        requestImg.setFitHeight(50);
        requestImg.setFitWidth(50);
        requestImg.setSmooth(true);
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

    @FXML
    public void ExitButton(ActionEvent e) {
        Stage s = (Stage) requestImg.getScene().getWindow();
        s.close();
    }

}
