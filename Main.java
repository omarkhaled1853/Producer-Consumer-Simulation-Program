
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<String, List<String>> graph = new HashMap<>();
        List<String> adj = new ArrayList<>();
        adj.add("m1");
        adj.add("m4");
        graph.put("q0",adj);

        List<String> adj1 = new ArrayList<>();
        adj1.add("m2");
        adj1.add("m3");
        graph.put("q1",adj1);

        List<String> adj2 = new ArrayList<>();
        adj2.add("m5");
        graph.put("q3",adj2);

        List<String> adj3 = new ArrayList<>();
        adj3.add("m6");
        adj3.add("m7");
        graph.put("q4",adj3);

        List<String> adj4 = new ArrayList<>();
        adj4.add("m6");
        adj4.add("m7");
        graph.put("q5",adj4);


        List<String> adj5 = new ArrayList<>();
        adj5.add("q1");
        graph.put("m1", adj5);

        List<String> adj6 = new ArrayList<>();
        adj6.add("q3");
        graph.put("m2", adj6);

        List<String> adj7 = new ArrayList<>();
        adj7.add("q3");
        graph.put("m3", adj7);

        List<String> adj8 = new ArrayList<>();
        adj8.add("q4");
        graph.put("m4", adj8);

        List<String> adj9 = new ArrayList<>();
        adj9.add("q5");
        graph.put("m5", adj9);

        List<String> adj10 = new ArrayList<>();
        adj10.add("q6");
        graph.put("m6", adj10);

        List<String> adj11 = new ArrayList<>();
        adj11.add("q6");
        graph.put("m7", adj11);
//
//        for (Map.Entry<String, List<String>> entry: graph.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        Simulator too = new Simulator(graph);
//        too.setProduct();
        for (int i = 0; i < 100; i++){
            Thread.sleep(500);
            for (UsedObject usedObject : too.update())
                System.out.print(usedObject.name + " " + usedObject.color + " ");
            System.out.println();
//            System.out.println(too.doneOperation());
            if(too.doneOperation())
                break;
        }

//            System.out.println(Thread.activeCount());
//        for(int i = 0; i < 50; i++) {
//            Thread.sleep(1000);
//        }

//        Map<String, BlockingQueue<Product>> queues = new HashMap<>();
//
//        BlockingQueue<Product> queue1 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue2 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue3 = new LinkedBlockingQueue<>();
//        BlockingQueue<Product> queue4 = new LinkedBlockingQueue<>();
//
//            char x = '3';
//            x = (char)('0' + ((x - '0') + 1));
//            System.out.println(x);
//            queues.put("q1", queue1);
//            queues.put("q0", queue2);
//            queues.put("q4", queue3);
//            queues.put("q3", queue4);

//        for (Map.Entry<String, BlockingQueue<Product>> entry: queues.entrySet()){
//            System.out.println(entry.getKey());
//        }
//
//        List<BlockingQueue<Product>> list = List.of(queue2, queue3);
//
//        for (int i = 1; i <= 5; i++){
//            Product product = new Product();
//            product.setColor(String.valueOf(i));
//            queue1.put(product);
//        }
//
//        Machine machine1 = new Machine("m1", 1000L, Collections.singletonList(queue1), queue2);
//        Thread consumerThread1 = new Thread(machine1);
//        Machine machine2 = new Machine("m2", 2000L, Collections.singletonList(queue1), queue3);
//        Thread consumerThread2 = new Thread(machine2);
//        Machine machine3 = new Machine("m3", 3000L, list, queue4);
//        Thread consumerThread3 = new Thread(machine3);
//
////        System.out.println(machine1.getColor());
////        System.out.println(machine2.getColor());
////        System.out.println(machine3.getColor());
//        consumerThread1.start();
//        consumerThread2.start();
//        consumerThread3.start();


    }
}
//        Thread consumerThread1 = new Thread(new Machine("m1", 1000L, Collections.singletonList(queue1), queue2));
//        Thread consumerThread2 = new Thread(new Machine("m2", 2000L, Collections.singletonList(queue1), queue3));
//        Thread consumerThread3 = new Thread(new Machine("m3", 3000L, list, queue4));
