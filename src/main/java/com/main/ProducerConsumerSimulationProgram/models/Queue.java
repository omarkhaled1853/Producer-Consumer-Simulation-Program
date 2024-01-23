package com.main.ProducerConsumerSimulationProgram.models;

import java.util.concurrent.BlockingQueue;

public class Queue {
    private final String id;
    private BlockingQueue<Product> queue;

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
