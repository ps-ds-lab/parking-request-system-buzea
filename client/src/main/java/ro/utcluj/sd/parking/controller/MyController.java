package ro.utcluj.sd.parking.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ro.utcluj.sd.parking.command.AddElementCommand;
import ro.utcluj.sd.parking.model.ObservableModel;
import ro.utcluj.sd.parking.viewmodel.MyViewModel;

public class MyController implements Initializable, Observer {

    @FXML
    public Button normalButton;
    @FXML
    public ListView normalList;
    @FXML
    private ListView mvListView;
    @FXML
    private Label mvLabel;

    private MyViewModel myViewModel;
    private ObservableModel observableModel;

    public void AddElementInFirstList(ActionEvent actionEvent) {
        new AddElementCommand(myViewModel).execute();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myViewModel = new MyViewModel();
        mvListView.setItems(myViewModel.getTheData());
        mvLabel.textProperty().bind(myViewModel.getLastElementAdded());

        observableModel = new ObservableModel();
        observableModel.addObserver(this);
        normalButton.setOnAction(e -> observableModel.addElement());
    }

    @Override
    public void update(Observable o, Object arg) {
        ObservableModel myModel = (ObservableModel) o;
        System.out.println(myModel);
        normalList.getItems().add(arg);
    }

    public void moveOn(ActionEvent actionEvent) {
        Scene scene = normalButton.getScene();
        Parent newFxml = null;
        try {
            newFxml = FXMLLoader.load(getClass().getResource("/secondView.fxml"));
            scene.setRoot(newFxml);
            scene.getWindow().sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
