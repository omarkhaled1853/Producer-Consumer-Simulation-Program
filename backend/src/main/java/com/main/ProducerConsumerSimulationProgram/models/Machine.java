package com.main.ProducerConsumerSimulationProgram.models;

import java.util.List;

public class Machine implements Runnable, Observer{
    private final String name;
    private final long time;
    private String color =  "rgb(128, 128, 128)";
    private Boolean isReady = true;
    private final List<Queue> prevQueues;
    private final Queue nextQueue;

    private final Originator originator;
    private final CareTaker careTaker;

    public Machine(String name, long time, List<Queue> prevQueues, Queue nextQueue, Originator originator, CareTaker careTaker) {
        this.name = name;
        this.time = time;
        this.prevQueues = prevQueues;
        this.nextQueue = nextQueue;
        this.originator = originator;
        this.careTaker = careTaker;
    }

    @Override
    public void run() {
        while (true){
            for (Queue queue: prevQueues){
                if (!queue.isEmpty()){
                        try {
                            Product product = queue.dequeue();
                            color = product.getColor();
                            isReady = false;
//                            System.out.println(name + " " + prevQueues + " " + nextQueue);
                            save();
                            Thread.sleep(time);
                            isReady = true;
                            color = "rgb(128, 128, 128)";
                            nextQueue.enqueue(product);
                            update();
                            save();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }

    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Queue getNextQueue() {
        return nextQueue;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void save(){
        careTaker.add(originator.saveStateToMemento());
    }

    @Override
    public void update() {
        if (this.isReady) nextQueue.notifyObservers();
    }

    @Override
    public String toString() {
        return "Machine{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
