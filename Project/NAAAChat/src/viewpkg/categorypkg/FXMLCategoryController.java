package viewpkg.categorypkg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entities.Category;
import Entities.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import viewpkg.ViewController;

/**
 *
 * @author alaa
 */
public class FXMLCategoryController implements Initializable {

    @FXML
    ListView<User> listViewOfFriends;
    List<User> listOfFriends;
    ViewController mainViewController;
    @FXML
    TextField categoryName;
    @FXML
    Button btn;
    ObservableList<User> selectedItems;
    public void setController(ViewController vc) {
        mainViewController = vc;
        listOfFriends=mainViewController.getFriends();
        listViewOfFriends.setCellFactory((ListView<User> param) -> new ListCell<User>() {
            FlowPane pane = new FlowPane();
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                
                if (item != null && !empty) {
                    Label name = new Label();
                    //ImageView imagView = new ImageView(item.getImage());
                    ImageView imagView = new ImageView(new Image(getClass().getResource("female.png").toString()));
                    imagView.setFitHeight(45);
                    imagView.setFitWidth(40);
                    Circle clip = new Circle(20,25,20);
                    imagView.setClip(clip);
                    
                    name.setText(" " + item.getFullName());
                    pane.getChildren().addAll(imagView,name);
                    setGraphic(pane);
                } else {
                    setGraphic(null);
                }
            }
            
        });

        listViewOfFriends.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listViewOfFriends.setOnMouseClicked((Event event) -> {
            selectedItems = listViewOfFriends.getSelectionModel().getSelectedItems();
            
            selectedItems.forEach((s) -> {
                System.out.println("selected item " + s.getfirstName());
            });
        });

        listViewOfFriends.getItems().setAll(listOfFriends);
        if (listOfFriends == null || listOfFriends.isEmpty()) {
            btn.setDisable(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listOfFriends =null;
   }
    @FXML
    void ExitButton(ActionEvent e) {
        Stage s = (Stage) listViewOfFriends.getScene().getWindow();
        s.close();
    }
    @FXML
    void createCategory()
    {
        Category c=new Category();
        c.setName(categoryName.getText());
        c.setCreatorUnique(null);
        ArrayList<User> selectedFriends = new ArrayList<User>(selectedItems);
        c.setCategoryMembers((ArrayList<User>) selectedFriends);
        mainViewController.createCaegory(c);
        Stage s = (Stage) listViewOfFriends.getScene().getWindow();
        s.close();
        
    }
}
