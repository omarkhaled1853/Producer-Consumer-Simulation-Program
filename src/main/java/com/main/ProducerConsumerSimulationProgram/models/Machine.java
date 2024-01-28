package com.main.ProducerConsumerSimulationProgram.models;

import com.main.ProducerConsumerSimulationProgram.service.Simulator;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Machine implements Runnable, Observer{
    private final String id;
    private final long time;
    private String color =  "rgb(128, 128, 128)";
    private Boolean isReady = true;
    private final List<Queue> prevQueues;
    private final Queue nextQueue;
    Simulator tool;

    public Machine(String id, long time, List<Queue> prevQueues, Queue nextQueue) {
        this.id = id;
        this.time = time;
        this.prevQueues = prevQueues;
        this.nextQueue = nextQueue;
    }

    @Override
    public void run() {
        while (true){
            for (Queue queue: prevQueues){
                if (!queue.isEmpty()){
                    synchronized (queue){
                        try {
                            Product product = queue.dequeue();
                            color = product.getColor();
                            isReady = false;
                            queue.notifyAll();
                            Thread.sleep(time);
                            isReady = true;
                            color = "rgb(128, 128, 128)";
                            nextQueue.enqueue(product);
                            nextQueue.notifyObservers();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Queue getNextQueue() {
        return nextQueue;
    }

    @Override
    public void update() {
        if (isReady) run();
    }
}
