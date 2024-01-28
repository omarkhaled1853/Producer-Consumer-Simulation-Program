package com.main.ProducerConsumerSimulationProgram.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Queue {
    private final String id;
    private BlockingQueue<Product> queue;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public Queue(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Boolean isEmpty(){
        return queue.isEmpty();
    }

    public void enqueue(Product product) throws InterruptedException {
        queue.put(product);
    }

    public Product dequeue() throws InterruptedException {
        return queue.take();
    }
}
