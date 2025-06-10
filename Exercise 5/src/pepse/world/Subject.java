package pepse.world;

/**
 * A Subject interface, the subject notifies the observers. Implemented by the way we learnt in class.
 */
public interface Subject {
    /**
     * Registers a new observer
     * @param observer the observer to add to the subjects list.
     * @return true if successful, false otherwise
     */
    boolean registerObserver(Observer observer);

    /**
     * Unregisters observer
     * @param observer the observer to remove from the subjects list.
     * @return true if successful, false otherwise
     */
    boolean unregisterObserver(Observer observer);

    /**
     * Notifies all observers with a new jump event.
     */
    void notifyObservers();
}
