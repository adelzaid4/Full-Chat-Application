/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpkg.chattingpage;

import Entities.Style;
import Entities.User;
import Entities.Message;
import Clientpkg.ClientInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import mySchema.MsgsType;
import mySchema.MyMsgType;
import mySchema.ObjectFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import viewpkg.ViewController;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class ChatingWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label name;

    @FXML
    ImageView profileImg;

    @FXML
    HBox infobox;

    @FXML
    WebView web;
    @FXML
    TextArea ta;

    @FXML
    ColorPicker colorpic;

    @FXML
    ComboBox<Integer> textSize;

    @FXML
    ComboBox<String> textStyle;

    @FXML
    ComboBox<String> textFamily;

    @FXML
    Button sendbtn;

    @FXML
    Button voicebtn;

    @FXML
    Button attachmentbtn;

    @FXML
    CheckBox enterCheck;
    @FXML
    MenuItem openNewWindowItem;

    ViewController mainViewController;
    ClientInterface friendInterface;
    ClientInterface myInterface;
    User me;
    User friend;
    ArrayList<Message> messages;
    Document doc;
    XMLGregorianCalendar today = null;
    MyMsgType newMsg;

    public void setController(ViewController vc, boolean outerChat, Message msg, String type) {
        mainViewController = vc;
        me = mainViewController.getUser();
        myInterface = mainViewController.getMyInterface();
        final WebEngine webEngine = web.getEngine();

        webEngine.setUserStyleSheetLocation(getClass().getResource("bubble.css").toString());

        String str = "<body id=\"bod\" class=\"bg\"></body>";

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                doc = webEngine.getDocument();
                if (outerChat) {
                    showMsg(msg, type);
                }
            }
        });
        webEngine.loadContent(str);

    }

    public void reloadWeb(Message msg, String type) {
        final WebEngine webEngine = web.getEngine();
        Platform.runLater(() -> {
            doc = webEngine.getDocument();
            showMsg(msg, type);
        });
    }

    public void setFriend(ClientInterface ci, User u) {
        friendInterface = ci;
        friend = u;
        if("f".equals(friend.getGender()))
            profileImg.setImage(new Image(getClass().getResource("female.png").toString()));
        else
            profileImg.setImage(new Image(getClass().getResource("male.png").toString()));
        name.setText(friend.getFullName());
        Label bio = new Label(friend.getStatus());
        ImageView status = new ImageView(getStatusImg(friend));
        infobox.getChildren().addAll(status, bio);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        messages = new ArrayList<>();
        profileImg.setOnMouseClicked((MouseEvent event) -> {
            Stage s = new Stage();
            ImageView fullimg = new ImageView(profileImg.getImage());
            BorderPane pane = new BorderPane(fullimg);
            Scene sc = new Scene(pane);
            s.setScene(sc);
            s.show();
        });

        ta.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (enterCheck.isSelected()) {
                    sendMsg(prepareMsg());
                }
            }
        });

        colorpic.setValue(Color.BLACK);
        colorpic.setStyle("-fx-color-label-visible: false ;");
        colorpic.setOnAction((ActionEvent event) -> {
            ta.setStyle("-fx-text-fill: " + toRgbString(colorpic.getValue()) + ";");
        });
        textFamily.setCellFactory((ListView<String> param) -> new ListCell<String>() {
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    String item = getItem();
                    ta.setFont(Font.font(item));
                }
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    Text t = new Text(item);
                    t.setStyle("-fx-font-family:" + item);
                    setGraphic(t);
                }
            }

        });
        textFamily.setItems(getTextFamily());
        textFamily.setValue(textFamily.getItems().get(0));
        textSize.setCellFactory((ListView<Integer> param) -> new ListCell<Integer>() {
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    Integer item = getItem();
                    ta.setFont(Font.font(item));
                }
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    Text t = new Text(item.toString());
                    t.setStyle("-fx-font-size:" + item);
                    setGraphic(t);
                }
            }

        });
        textSize.setItems(getTextSizes());
        textSize.setValue(textSize.getItems().get(6));
        textStyle.setCellFactory((ListView<String> param) -> new ListCell<String>() {
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    String item = getItem();
                    if (item.equals("BOLD")) {
                        ta.setFont(Font.font(ta.getFont().getFamily(), FontWeight.valueOf(item), ta.getFont().getSize()));
                    } else {
                        ta.setFont(Font.font(ta.getFont().getFamily(), FontPosture.valueOf(item), ta.getFont().getSize()));
                    }
                }
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    if (item.equals("BOLD")) {
                        Text t = new Text(item);
                        t.setFont(Font.font(getFont().getFamily(), FontWeight.valueOf(item), getFont().getSize()));
                        setGraphic(t);
                    } else {
                        Text t = new Text(item);
                        t.setFont(Font.font(getFont().getFamily(), FontPosture.valueOf(item), getFont().getSize()));
                        setGraphic(t);
                    }
                }

            }

        });
        textStyle.setItems(getTextStyle());
        textStyle.setValue(textStyle.getItems().get(2));
        sendbtn.setOnAction((ActionEvent event) -> {
            sendMsg(prepareMsg());
        });

        attachmentbtn.setOnAction((ActionEvent ev) -> {
            FileChooser fileChooser = new FileChooser();
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {

                FileInputStream in = null;
                try {
//                ProgressForm pForm = new ProgressForm();
//
//                // In real life this task would do something useful and return
//                // some meaningful result:
//                Task<void> task = new Task<void>() {
//                    @Override
//                    public Void call() throws InterruptedException {
//                        for (int i = 0; i < 10; i++) {
//                            updateProgress(i, 10);
//                            Thread.sleep(200);
//                        }
//                        updateProgress(10, 10);
//                        return null;
//                    }
//                };
//
//                // binds progress of progress bars to progress of task:
//                pForm.activateProgressBar(task);
//
//                // in real life this method would get the result of the task
//                // and update the UI based on its value:
//                task.setOnSucceeded(event -> {
//                    pForm.getDialogStage().close();
//                });
//                pForm.getDialogStage().show();
//
//                Thread thread = new Thread(task);
//                thread.start();

                    in = new FileInputStream(file);
                    byte[] mydata = new byte[1024 * 1024];
                    int mylen = in.read(mydata);
                    while (mylen > 0) {
                        friendInterface.recieveFile(file.getName(), mydata, mylen, mainViewController.getMyInterface());
                        mylen = in.read(mydata);
                    }
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("File Status");
                    tray.setMessage("file is sent to "+friend.getFullName());
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.showAndDismiss(Duration.seconds(2.0));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ChatingWindowController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatingWindowController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ChatingWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    @FXML
    void saveChat() {
        try {
            JAXBContext context = JAXBContext.newInstance("mySchema");

            ObjectFactory factory = new ObjectFactory();
            newMsg = factory.createMyMsgType();
            List msgs = newMsg.getMsg();
            msgs.clear();
            for (Message message : messages) {

                newMsg.setHeader("Chat History");

                MsgsType msg = factory.createMsgsType();
                msg.setBody(message.getBody());
                msg.setFrom(message.getSender().getfirstName());
                msg.getTo().add(message.getReciever().getfirstName());

                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(Date.from(message.getDate().atZone(ZoneId.systemDefault()).toInstant()));
                try {
                    XMLGregorianCalendar date2;
                    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                    msg.setDate(date2);
                } catch (DatatypeConfigurationException ex) {
                    ex.printStackTrace();
                }

                int size = message.getStyle().getSize();
                BigInteger bi = BigInteger.valueOf(size);
                msg.setSize(bi);

                msg.setColor("color:" + message.getStyle().getColStr());
                msg.setFont(message.getStyle().getFontFamily());
                msg.setMe(message.isMe());
                msgs.add(msg);
            }

            JAXBElement xmlMessage = factory.createMyMsg(newMsg);
            Marshaller marsh = context.createMarshaller();

            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href='Msg.xsl' ?>");
            marsh.marshal(xmlMessage, new FileOutputStream("output.xml"));

        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void openNewWindow() {
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(sendbtn.getScene());
        openNewWindowItem.setDisable(true);
        mainViewController.hideTab(friendInterface);
    }

    public void showFile(File file, String type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("File Transformation");
        alert.setHeaderText("file name : " + file.getName());
        alert.setContentText("Do you want to save it?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("File Saved");
            tray.setMessage("path:" + file.getAbsolutePath());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(4.0));
        } else {
            file.delete();
        }
        });
    }

    class ProgressForm {

        private final Stage dialogStage;
        private final ProgressBar pb = new ProgressBar();
        private final ProgressIndicator pin = new ProgressIndicator();

        public ProgressForm() {
            dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // PROGRESS BAR
            final Label label = new Label();
            label.setText("alerto");

            pb.setProgress(-1F);
            pin.setProgress(-1F);

            final HBox hb = new HBox();
            hb.setSpacing(5);
            //hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(pb, pin);

            Scene scene = new Scene(hb);
            dialogStage.setScene(scene);
        }

        public void activateProgressBar(final Task<?> task) {
            pb.progressProperty().bind(task.progressProperty());
            pin.progressProperty().bind(task.progressProperty());
            dialogStage.show();
        }

        public Stage getDialogStage() {
            return dialogStage;
        }
    }

    private Message prepareMsg() {
        Message m = new Message();
        m.setBody(ta.getText());
        ta.clear();
        m.setReciever(friend);
        m.setSender(me);
        m.setDate(LocalDateTime.now());
        Style s = new Style();
        s.setColor(colorpic.getValue());
        s.setColStr(toRgbString(s.getColor()));
        s.setSize(textSize.getValue());
        s.setFontFamily(textFamily.getValue());
        s.setFontWeight(textStyle.getValue());
        m.setStyle(s);
        return m;
    }

    void sendMsg(Message m) {
        try {
            Message copyMsg = new Message();
            copyMsg = m;
            showMsg(m, "me");
            friendInterface.recieve(copyMsg, myInterface, me);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatingWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<Integer> getTextSizes() {
        return FXCollections.observableArrayList(8, 10, 12, 14, 16, 18, 20, 22, 24, 26);
    }

    private ObservableList<String> getTextFamily() {
        return FXCollections.observableArrayList("Arial", "Agency FB", "Cambria", "Calibri", "Verdana");
    }

    private ObservableList<String> getTextStyle() {
        return FXCollections.observableArrayList("BOLD", "ITALIC", "REGULAR");
    }

    public boolean showMsg(Message msg, String type) {
        System.out.println(msg.getBody());
        //showmsg in webview
        Element body = doc.getElementById("bod");
        Node newBubble2 = getBubble(msg, type, null);
        body.appendChild(newBubble2);
        if (msg.getSender() == me) {
            msg.setMe(true);
        } else {
            msg.setMe(false);
        }
        messages.add(msg);
        return true;
    }

    private Node getBubble(Message msg, String type, File f) {
        Node chat = doc.createElement("div");
        Attr c = doc.createAttribute("width");
        c.setNodeValue(String.valueOf(web.getWidth()) + "px");
        chat.getAttributes().setNamedItem(c);

        Node head = doc.createElement("div");
        Attr h = doc.createAttribute("style");
        h.setNodeValue("font-size: small;font-weight: bold;");
        head.getAttributes().setNamedItem(h);
        head.setTextContent(msg.getSender().getFullName());

        Node bubble = doc.createElement("div");
        Attr a = doc.createAttribute("class");
        if ("me".equals(type)) {
            a.setNodeValue("bubble you");
        } else {
            a.setNodeValue("bubble me");
        }
        bubble.getAttributes().setNamedItem(a);
        Node text = null;
        if (f == null) {
            text = doc.createElement("div");
            Attr style = doc.createAttribute("style");
            style.setNodeValue("color:" + msg.getStyle().getColStr() + ";font:" + msg.getStyle().getFontWeight()
                    + " " + msg.getStyle().getSize() + "px " + msg.getStyle().getFontFamily());
            text.setTextContent(msg.getBody());
            text.getAttributes().setNamedItem(style);
        } else {
            text = doc.createElement("a");
            Attr style = doc.createAttribute("style");
            style.setNodeValue("color:blue;font: italic ");
            Attr ref = doc.createAttribute("href");
            ref.setNodeValue(f.getAbsolutePath());
            text.setNodeValue(f.getName());
            text.getAttributes().setNamedItem(style);
            text.getAttributes().setNamedItem(ref);
        }
        Node time = doc.createElement("div");
        Attr t = doc.createAttribute("style");
        t.setNodeValue("font-size: xx-small;font-style: italic;");
        time.getAttributes().setNamedItem(t);
        time.setTextContent(msg.getDate().toString());

        bubble.appendChild(head);
        bubble.appendChild(text);
        bubble.appendChild(time);
        chat.appendChild(bubble);
        //append bubble in new line
        chat.appendChild(doc.createElement("br"));
        chat.appendChild(doc.createElement("br"));
        chat.appendChild(doc.createElement("br"));
        chat.appendChild(doc.createElement("br"));
        chat.appendChild(doc.createElement("br"));
        return chat;
    }

    private String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }

    private Image getStatusImg(User u) {
        Image img = null;
        if (u.getStatus() != null) {
            switch (u.getStatus().toLowerCase()) {
                case "available":
                    img = new Image(getClass().getResource("available.png").toString());
                    break;
                case "busy":
                    img = new Image(getClass().getResource("busy.png").toString());
                    break;
                case "away":
                    img = new Image(getClass().getResource("away.png").toString());
                    break;
                case "appear offline":
                    img = new Image(getClass().getResource("offline.png").toString());
                    break;
                default:
                    img = new Image(getClass().getResource("available.png").toString());
                    break;
            }
        } else {
            img = new Image(getClass().getResource("available.png").toString());
        }
        return img;
    }
}
