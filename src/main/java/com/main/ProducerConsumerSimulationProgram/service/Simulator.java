package com.main.ProducerConsumerSimulationProgram.service;

import com.main.ProducerConsumerSimulationProgram.helperClasses.SourceDestination;
import com.main.ProducerConsumerSimulationProgram.models.Machine;
import com.main.ProducerConsumerSimulationProgram.models.Memento;
import com.main.ProducerConsumerSimulationProgram.models.Observer;
import com.main.ProducerConsumerSimulationProgram.models.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class Simulator {
    ArrayList<Machine> machines = new ArrayList<>();
    Map<String, BlockingQueue<Product>> queues = new HashMap<>();
    List<Thread> threads = new ArrayList<>();
    Map<String, List<String>> graph;
    BlockingQueue<Product> q0;
    char maxQueueNum = '0';
    Long productNum = generateRandomProducts();

    Memento memento = new Memento();
    Observer observer = new Observer();

    public void start() throws InterruptedException {
        setProduct();
        setQueues();
        setMachines();
        startOperation();
    }

    public void replay(){
        memento.replay();
    }

    public Map<String, List<String>> getGraph(List<SourceDestination> list){
        Map<String, List<String>> g = new HashMap<>();
        for (SourceDestination sourceDestination : list){
            g.put(sourceDestination.getSource(), sourceDestination.getDestination());
        }
        return g;
    }

    public void setGraph(Map<String, List<String>> g){
        graph = g;
    }

    private void setProduct() throws InterruptedException {
        q0 = new LinkedBlockingQueue<>();
        for (int i = 1; i <= productNum; i++){
            Product product = new Product(generateRandomColor());
            q0.put(product);
        }
    }
    private void setQueues(){
        maxQueueNum = '0';
        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
            if(entry.getKey().contains("q") && !queues.containsKey(entry.getKey())){
                maxQueueNum = (char) Math.max(maxQueueNum, entry.getKey().charAt(1));

                BlockingQueue<Product> queue = new LinkedBlockingQueue<>();
                queues.put(entry.getKey(), queue);
            }
        }
        BlockingQueue<Product> queue = new LinkedBlockingQueue<>();
        maxQueueNum = (char)('0' + ((maxQueueNum - '0') + 1));
        queues.put("q" + maxQueueNum, queue);
    }
    private void setMachines(){
        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
            if(entry.getKey().contains("m")){
                List<BlockingQueue<Product>> prevQueues = setPrevious(entry.getKey());
                BlockingQueue<Product> nextQueue = queues.get(entry.getValue().get(0));
                Machine machine = new Machine(entry.getKey(), generateRandomTime(), prevQueues, nextQueue, this);
                Thread consumerThread = new Thread(machine);
                threads.add(consumerThread);
                machines.add(machine);
            }
        }
        memento.save(threads);
    }
    private void startOperation(){
        for (Thread thread: threads)
            thread.start();
    }
    public List<UsedObject> update(){
        return observer.update(machines, graph, q0);
    }
    private List<BlockingQueue<Product>> setPrevious(String machineId){
        List<BlockingQueue<Product>> prevQueue = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
            if(entry.getKey().contains("q")){
                for (String mach: entry.getValue()){
                    if(mach.equals(machineId))
                        if(entry.getKey().equals("q0"))
                            prevQueue.add(q0);
                        else
                            prevQueue.add(queues.get(entry.getKey()));
                }
            }
        }
        return prevQueue;
    }
    public boolean doneOperation(){
        return queues.get("q"+maxQueueNum).size() == productNum;
    }
    public void endOperation(){
        for (Thread thread: threads)
            thread.interrupt();
    }
    private long generateRandomTime(){
        return ThreadLocalRandom.current().nextInt(1000, 5000);
    }

    private Long generateRandomProducts(){
        return ThreadLocalRandom.current().nextLong(5, 20);
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
