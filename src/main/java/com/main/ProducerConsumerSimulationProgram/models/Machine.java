package com.main.ProducerConsumerSimulationProgram.models;

import java.util.List;

public class Machine implements Runnable, Observer{
    private final String id;
    private final long time;
    private String color =  "rgb(128, 128, 128)";
    private Boolean isReady = true;
    private final List<Queue> prevQueues;
    private final Queue nextQueue;

    private final Originator originator;
    private final CareTaker careTaker;

    public Machine(String id, long time, List<Queue> prevQueues, Queue nextQueue, Originator originator, CareTaker careTaker) {
        this.id = id;
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
//                    synchronized (queue){
                        try {
                            Product product = queue.dequeue();
                            color = product.getColor();
                            isReady = false;
//                            queue.notifyAll();
                            System.out.println(originator.saveStateToMemento());
                            save();
                            Thread.sleep(time);
                            isReady = true;
                            color = "rgb(128, 128, 128)";
                            nextQueue.enqueue(product);
                            nextQueue.notifyObservers();
                            System.out.println(originator.saveStateToMemento());
                            save();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

//                    }
                }
            }
            if(nextQueue.getSize() == 5) {
                break;
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

    public Boolean getReady() {
        return isReady;
    }

    public void save(){
        careTaker.add(originator.saveStateToMemento());
    }

    @Override
    public void update() {
        if (isReady) run();
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
