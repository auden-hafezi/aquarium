package model;

// Represents a class that observes an Observable class.
public interface Observer {

    // EFFECTS: updates the observer
    void update(Object event);
}
