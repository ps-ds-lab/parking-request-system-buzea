package ro.utcluj.sd.parking.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyViewModel {

    private ObservableList<String> theData = FXCollections.observableArrayList();

    private int counter;
    private StringProperty lastElementAdded = new SimpleStringProperty();

    public ObservableList<String> getTheData() {
        return theData;
    }

    public void setTheData(ObservableList<String> theData) {
        this.theData = theData;
    }

    public void addNewElement() {
        String val = "Element " + counter;
        theData.add(val);
        counter++;
        lastElementAdded.setValue(val);
    }

    public StringProperty getLastElementAdded() {
        return lastElementAdded;
    }

    public void removeElement() {

    }
}
