package ro.utcluj.sd.parking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ObservableModel extends Observable {


    private List<String> theData = new ArrayList<>();
    private int counter;

    public void addElement() {
        String lastAddedElement = "Element In L2:" + counter;
        theData.add(lastAddedElement);
        counter++;
        setChanged();
        notifyObservers(lastAddedElement);
    }

    public List<String> getTheData() {
        return theData;
    }
}
