package com.main.ProducerConsumerSimulationProgram.models;

import com.main.ProducerConsumerSimulationProgram.service.Service;

import java.util.List;

public class simulator {
    Service service = new Service();
    public void start(){
        List<Thread> threads = service.getThreads();
        for (Thread thread : threads){
            thread.start();
        }
    }

    public void update(){

    }

    public void rePlay(){

    }

    public void end(){
        List<Thread> threads = service.getThreads();
        for (Thread thread : threads){
            thread.interrupt();
        }
    }
}
