package com.main.ProducerConsumerSimulationProgram.models;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    ArrayList<List<Thread>> threads = new ArrayList<>();
    public void save(List<Thread> machineThreads){
        threads.add(machineThreads);
    }
    public void replay(){
        List<Thread> thread = threads.get(threads.size() - 1);
        for (Thread thread1: thread)
            thread1.start();
    }
}
