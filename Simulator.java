import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
public class Simulator {
    ArrayList<Machine> machines = new ArrayList<>();
    Map<String, BlockingQueue<Product>> queues = new HashMap<>();
    List<Thread> threads = new ArrayList<>();
    Map<String, List<String>> graph;
    BlockingQueue<Product> q0;
    char maxQueueNum = '0';
    int productNum = 5;
    public Simulator(Map<String, List<String>> graph) throws InterruptedException {
        this.graph = graph;
        setProduct();
        setQueues();
        setMachines();
        startOperation();
    }
    private void setProduct() throws InterruptedException {
        RandomColor randomColor = new RandomColor();
        q0 = new LinkedBlockingQueue<>();
        for (int i = 1; i <= productNum; i++){
            Product product = new Product(randomColor.generate());
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
    }
    private void startOperation(){
        for (Thread thread: threads)
            thread.start();
    }
    public List<UsedObject> update(){
        List<UsedObject> usedObjects = new ArrayList<>();

        for (Machine machine: machines){
            UsedObject usedObject = new UsedObject(machine.getId(), machine.getColor());
            usedObjects.add(usedObject);
        }
//        for (Map.Entry<String, BlockingQueue<Product>> entry: queues.entrySet()){
//            UsedObject usedObject = new UsedObject(entry.getKey(), Integer.toString(entry.getValue().size()));
//            usedObjects.add(usedObject);
//        }
        return usedObjects;
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
}








//        System.out.println(machineId + " " + prevQueue);
//        for (Thread thread: threads)
//            thread.start();

//        RandomColor randomColor = new RandomColor();

//        BlockingQueue<Product> queue1 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue2 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue3 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue4 = new LinkedBlockingQueue<>();
//
//        List<BlockingQueue<Product>> list = List.of(queue2, queue3);
//        for (int i = 1; i <= 5; i++){
//            Product product = new Product();
//            product.setColor(randomColor.generate());
//            queue1.put(product);
//        }
//        List<Thread> threads1 = new ArrayList<>();
//        Machine machine1 = new Machine("m1", generateRandomTime(), Collections.singletonList(queue1), queue2);
//        Thread consumerThread1 = new Thread(machine1);
//        Machine machine2 = new Machine("m2", generateRandomTime(), Collections.singletonList(queue1), queue3);
//        Thread consumerThread2 = new Thread(machine2);
//        Machine machine3 = new Machine("m3", generateRandomTime(), list, queue4);
//        Thread consumerThread3 = new Thread(machine3);
//
//        threads1.add(consumerThread1);
//        threads1.add(consumerThread2);
//        threads1.add(consumerThread3);
//        machines.add(machine1);
//        machines.add(machine2);
//        machines.add(machine3);
//        for(Thread thread : threads1)
//            thread.start();
//
//        consumerThread1.start();
//        consumerThread2.start();
//        consumerThread3.start();








//        RandomColor randomColor = new RandomColor();
//        q0 = new LinkedBlockingQueue<>();
//        for (int i = 1; i <= 5; i++){
//            Product product = new Product();
//            product.setColor(randomColor.generate());
//            q0.put(product);
//        }

//        this.graph = graph;
//        char maxQueue = '0';
//        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
//            if(entry.getKey().contains("q") && !queues.containsKey(entry.getKey())){
//                maxQueue = (char) Math.max(maxQueue, entry.getKey().charAt(1));
//
//                BlockingQueue<Product> queue = new LinkedBlockingQueue<>();
//                queues.put(entry.getKey(), queue);
//            }
//        }
//        BlockingQueue<Product> queue = new LinkedBlockingQueue<>();
//        maxQueue = (char)('0' + ((maxQueue - '0') + 1));
//        queues.put("q" + maxQueue, queue);

//        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
//            if(entry.getKey().contains("m")){
//                List<BlockingQueue<Product>> prevQueues = setPrevious(entry.getKey());
//                BlockingQueue<Product> nextQueue = queues.get(entry.getValue().get(0));
////                System.out.println(entry.getKey()+" "+ generateRandomTime()+" "+ prevQueues +" "+ nextQueue);
//                Machine machine = new Machine(entry.getKey(), generateRandomTime(), prevQueues, nextQueue);
//                Thread consumerThread = new Thread(machine);
//                threads.add(consumerThread);
//                machines.add(machine);
//            }
//        }

//        for(Map.Entry<String, BlockingQueue<Product>> entry: queues.entrySet()){
//            System.out.println(entry.getKey());
//        }
//        System.out.println(queues.size());
//        System.out.println(machines.size());
//                setProduct();