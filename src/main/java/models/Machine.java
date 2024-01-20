package models;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Machine implements Runnable{
    private String id;
    private Product product;

    private Boolean state = true;
    private Long time;

    private final List<BlockingQueue<Product>> prevQueues;
    private final BlockingQueue<Product> nextQueue;

    public Machine(String id, Long time, List<BlockingQueue<Product>> prevQueues, BlockingQueue<Product> nextQueue) {
        this.id = id;
        this.time = time;
        this.prevQueues = prevQueues;
        this.nextQueue = nextQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < prevQueues.size(); i++) {
                    if (!prevQueues.get(i).isEmpty()) {
                        synchronized (prevQueues.get(i)) {
                            state = false;
                            product = prevQueues.get(i).take();

                            prevQueues.get(i).notifyAll();

                            Thread.sleep(time);

                            nextQueue.put(product);

                            state = true;
                            product.setColor("black");
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
