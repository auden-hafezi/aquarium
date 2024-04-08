package model;

import java.util.ArrayList;
import java.util.List;

// Represents a class that can be observed by Observer classes.
public abstract class Observable {
    private List<Observer> observers;

    // EFFECTS: creates a new observable
    public Observable() {
        this.observers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // EFFECTS:
    public void notifyObservers(Object event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }


}
