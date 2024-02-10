package com.main.ProducerConsumerSimulationProgram.services;

import com.main.ProducerConsumerSimulationProgram.helperClasses.SourceDestination;
import com.main.ProducerConsumerSimulationProgram.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProducerConsumerService {
    private Map<String, List<String>> graph;
    private final CareTaker careTaker = new CareTaker();
    private final Originator originator = new Originator();

    private final List<Machine> machines = new ArrayList<>();
    private final List<Queue> queues = new ArrayList<>();

    private final List<Thread> threads = new ArrayList<>();
    private int products;
    public void handler(List<SourceDestination> list) throws InterruptedException {
        graph = convertToMap(list);
        setQueues();
        setFirstQueue();
        setMachines();
        setObservers();
        originator.setMachineList(machines);
        originator.setQueueList(queues);
        careTaker.add(originator.saveStateToMemento());
    }
    private Map<String, List<String>> convertToMap(List<SourceDestination> list){
        Map<String, List<String>> graph = new HashMap<>();
        for (SourceDestination sourceDestination : list){
            graph.put(sourceDestination.getSource(), sourceDestination.getDestination());
        }
        return graph;
    }
    private void setQueues() {
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'q'){
                queues.add(new Queue(entry.getKey()));
            }
        }
        String id = Integer.toString(queues.size());
        queues.add(new Queue("q" + id));
    }
    private void setObservers(){
        for (Queue queue : queues){
            if(graph.containsKey(queue.getName())){
                for (String machineName : graph.get(queue.getName())){
                    for (Machine machine : machines){
                        if(machineName.equals(machine.getName())){
                            queue.addObserver(machine);
                        }
                    }
                }
            }
        }
    }
    private void setMachines(){
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'm'){
                List<Queue> prevQueues = setPrevQueues(entry.getKey());
                Queue nextQueue = setNextQueue(entry.getValue().get(0));
                Machine machine = new Machine(entry.getKey(), generateRandomTime(), prevQueues, nextQueue, originator, careTaker);
                threads.add(new Thread(machine));
                machines.add(machine);
            }
        }
    }

    private List<Queue> setPrevQueues(String id){
        List<Queue> prevQueues = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'q' && entry.getValue().contains(id)){
                for (Queue queue : queues){
                    if(queue.getName().equals(entry.getKey())){
                        prevQueues.add(queue);
                    }
                }
            }
        }
        return prevQueues;
    }
    private Queue setNextQueue(String id){
        for (Queue queue : queues){
            if(queue.getName().equals(id))
                return queue;
        }
        return null;
    }
    private void setFirstQueue() throws InterruptedException {
        for (Queue queue : queues){
            if(queue.getName().equals("q0")) {
              products = generateRandomProducts();
              for (int i = 0; i < products; i++) {
                  queue.enqueue(new Product(generateRandomColor()));
              }
            }
        }
    }

    public CareTaker getCareTaker() {
        return careTaker;
    }

    public Originator getOriginator() {
        return originator;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public int getProducts() {
        return products;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    private int generateRandomTime(){
        return ThreadLocalRandom.current().nextInt(1000, 5000);
    }

    private int generateRandomProducts(){
        return ThreadLocalRandom.current().nextInt(5, 20);
    }

    private String generateRandomColor(){
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);


        return  "rgb(" .concat(String.valueOf(r))
                .concat(", ")
                .concat(String.valueOf(g))
                .concat(", ")
                .concat(String.valueOf(b))
                .concat(")");
    }
}
