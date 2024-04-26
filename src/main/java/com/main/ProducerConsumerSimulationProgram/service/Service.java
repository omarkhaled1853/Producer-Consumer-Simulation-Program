package com.main.ProducerConsumerSimulationProgram.service;

import com.main.ProducerConsumerSimulationProgram.helperClasses.SourceDestination;
import com.main.ProducerConsumerSimulationProgram.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Service {
    private final CareTaker careTaker = new CareTaker();
    private final Originator originator = new Originator();

    private final List<Thread> threads = new ArrayList<>();
    public void handler(List<SourceDestination> list) throws InterruptedException {
        Map<String, List<String>> graph = convertToMap(list);
        List<Queue> queues = setQueues(graph);
        List<Machine> machines = setMachines(graph);
        setThreads(machines);
        setFirstQueue(queues);
    }
    private Map<String, List<String>> convertToMap(List<SourceDestination> list){
        Map<String, List<String>> graph = new HashMap<>();
        for (SourceDestination sourceDestination : list){
            graph.put(sourceDestination.getSource(), sourceDestination.getDestination());
        }
        return graph;
    }
    private List<Queue> setQueues(Map<String, List<String>> graph) {
        List<Queue> queues = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'q'){
                queues.add(new Queue(entry.getKey()));
            }
        }
        String id = Integer.toString(Integer.parseInt(queues.get(queues.size() - 1).getId().substring(1)) + 1);
        queues.add(new Queue("q" + id));
        return queues;
    }
    private List<Machine> setMachines(Map<String, List<String>> graph){
        List<Machine> machines = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'm'){
                List<Queue> prevQueues = setPrevQueues(entry.getKey(), graph);
                Queue nextQueue = new Queue(entry.getValue().get(0));
                Machine machine = new Machine(entry.getKey(), generateRandomTime(), prevQueues, nextQueue, originator, careTaker);
            }
        }
        return machines;
    }

    private List<Queue> setPrevQueues(String id, Map<String, List<String>> graph){
        List<Queue> prevQueues = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            if (entry.getKey().charAt(0) == 'q' && entry.getValue().contains(id)){
                prevQueues.add(new Queue(entry.getKey()));
            }
        }
        return prevQueues;
    }

    private void setThreads(List<Machine> machines){
        for (Machine machine : machines){
            threads.add(new Thread(machine));
        }
    }
    private Queue getFirstQueue(List<Queue> queues){
        for (Queue queue : queues){
            if(queue.getId().equals("q0")){
                return queue;
            }
        }
        return null;
    }
    private void setFirstQueue(List<Queue> queues) throws InterruptedException {
        Queue q0 = getFirstQueue(queues);
        int products = generateRandomProducts();
        for (int i = 0; i < products; i++){
            q0.enqueue(new Product(generateRandomColor()));
        }
    }

    public CareTaker getCareTaker() {
        return careTaker;
    }

    public List<Thread> getThreads() {
        return threads;
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
