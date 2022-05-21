package ru.geekbrains.java_core_2.lesson_4.chat_client;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController extends Application implements Initializable {

    @FXML
    public VBox mainPanel;

    @FXML
    private TextArea chatArea;

    @FXML
    private ListView<String> contactList;

    @FXML
    private TextField inputField;

    @FXML
    private Button btnSend;

    public static void main(String[] args) {
        launch(args);
    }

    private MultipleSelectionModel<String> selectionModel;


    @Override
    public void start(Stage myStage) throws Exception {
        myStage.setTitle("JavaFX Example Application");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/ChatWindow.fxml"));
        Parent parent = loader.load();
        Scene myScene = new Scene(parent);
        myStage.setScene(myScene);
        myStage.show();

    }

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }

    public void sendMessage(ActionEvent actionEvent) {
        String text = inputField.getText();
        if (text.isEmpty()) {return;}

        ObservableList<String> selectedContacts = selectionModel.getSelectedItems();
        if (selectedContacts.size() == 0 ||
                selectedContacts.size() == contactList.getItems().size()) {
            chatArea.appendText("Broadcast: " + text + System.lineSeparator());
        } else {
            selectedContacts.forEach(s -> chatArea.appendText(s + ": " + text + System.lineSeparator()));
        }

        inputField.clear();

    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> names = List.of("Vasya", "Petya", "Jora", "Krendel", "Nastya", "Vera", "Ksenya", "Olya");
        contactList.setItems(FXCollections.observableList(names));
        selectionModel = contactList.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void helpPage(ActionEvent actionEvent) {
        getHostServices().showDocument("http://github.com/code-Production/github/blob/feature/git.md");
    }

    public void aboutPage(ActionEvent actionEvent) {
        Stage helpStage = new Stage();
        helpStage.setTitle("About...");
        GridPane gridPane = new GridPane();
        Scene helpScene = new Scene(gridPane, 600, 400);
        Label helpLabel = new Label("...Example JavaFX application about...");
        helpStage.setScene(helpScene);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(helpLabel,1,1);
        helpStage.show();
    }
}
