package com.logs;

import com.orders.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * The LogEntryBuffer class is responsible for storing log entries related to game actions.
 * It maintains a list of log entries and notifies observers whenever a new entry is added.
 * The class follows the Observer design pattern to allow multiple observers to track the log entries.
 * 
 * Observers can be added or removed, and the log entries can be accessed or cleared.
 */
public class LogEntryBuffer{
    /**
     * List of observers.
     */
    public List<LogObserver> observers = new ArrayList<>();
    /**
     * List of log entries.
     */
    public List<Order> logbuffer = new ArrayList<>();

    /**
     * Add observer.
     *
     * @param observer the observer
     */
    public void addObserver(LogObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove observer.
     *
     * @param observer the observer
     */
    public void removeObserver(LogObserver observer) {
        observers.remove(observer);
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(Order entry) {
        logbuffer.add(entry);
        notifyObservers();
    }

    /**
     * Notify observers.
     */
    private void notifyObservers() {
        for (LogObserver observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Get entries list.
     *
     * @return the list of log entries
     */
    public List<Order> getEntries() {
        return logbuffer;
    }

    /**
     * Clear entries.
     */
    public void clearEntries(){
        logbuffer.clear();
    }
}
