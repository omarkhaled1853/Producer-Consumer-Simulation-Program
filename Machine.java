import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Machine implements Runnable {
    private final String id;
    private Product product;

    private Boolean state = true;
    private final long time;


    private String color =  "rgb(128, 128, 128)";
    private final List<BlockingQueue<Product>> prevQueues;
    private final BlockingQueue<Product> nextQueue;
    Simulator tool;
    public Machine(String id, long time, List<BlockingQueue<Product>> prevQueues, BlockingQueue<Product> nextQueue, Simulator tool) {
        this.id = id;
        this.time = time;
        this.prevQueues = prevQueues;
        this.nextQueue = nextQueue;
        this.tool = tool;
    }

    @Override
    public void run() {
        try {
            while (!tool.doneOperation()) {
                for (int i = 0; i < prevQueues.size(); i++) {
                    if (!prevQueues.get(i).isEmpty()) {
                        synchronized (prevQueues.get(i)) {
                            state = false;
                            product = prevQueues.get(i).take();

                            this.color = product.color();
//                            System.out.println(product.color() + " from machine " + id + " machine now colored with " + this.getColor());

                            prevQueues.get(i).notifyAll();

                            Thread.sleep(time);

                            this.color = "rgb(128, 128, 128)";
                            nextQueue.put(product);

                            state = true;
                        }
                    }
                }
                if(tool.doneOperation())
                    tool.endOperation();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public String getColor() {
        return color;
    }
    public String getId() {
        return id;
    }
}
