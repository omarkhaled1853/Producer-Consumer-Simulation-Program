package com.main.ProducerConsumerSimulationProgram.models;

import com.main.ProducerConsumerSimulationProgram.services.ProducerConsumerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Simulator {
    public void start(ProducerConsumerService producerConsumerService){
        for (Thread thread : producerConsumerService.getThreads()){
            thread.start();
        }
    }

    public Memento update(ProducerConsumerService producerConsumerService){
        return new Memento(producerConsumerService.getOriginator().getMachineList(), producerConsumerService.getOriginator().getQueueList());
    }

    public void rePlay(){

    }

    public void end(ProducerConsumerService producerConsumerService){
        List<Thread> threads = producerConsumerService.getThreads();
        for (Thread thread : threads){
            thread.interrupt();
        }
    }
}
