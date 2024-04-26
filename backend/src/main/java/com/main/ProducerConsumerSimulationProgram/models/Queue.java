package com.main.ProducerConsumerSimulationProgram.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {
    private final String name;
    private final BlockingQueue<Product> queue = new LinkedBlockingQueue<>();
    private int size = 0;
    private final List<Observer> observers = new ArrayList<>();

    public Queue(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }


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

    public String getName() {
        return name;
    }

    public Boolean isEmpty(){
        return queue.isEmpty();
    }

    public void enqueue(Product product) throws InterruptedException {
        queue.put(product);
        size = queue.size();
    }

    public Product dequeue() throws InterruptedException {
        size = queue.size();
        return queue.take();
    }

    @Override
    public String toString() {
        return "Queue{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
