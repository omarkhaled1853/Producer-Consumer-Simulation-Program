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
