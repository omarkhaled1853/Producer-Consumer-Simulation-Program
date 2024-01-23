package com.main.ProducerConsumerSimulationProgram.models;

import com.main.ProducerConsumerSimulationProgram.service.UsedObject;
import java.util.concurrent.BlockingQueue;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Observer {
    public List<UsedObject> update(ArrayList<Machine> machines, Map<String, List<String>> graph, BlockingQueue<Product> q0){
        List<UsedObject> usedObjects = new ArrayList<>();
        Map<String, String> UpdatedQueues = new HashMap<>();

        for (Machine machine: machines){
            if (graph.get("q0").contains(machine.getId()))
                UpdatedQueues.put("q0", Integer.toString(q0.size()));

            UpdatedQueues.put(graph.get(machine.getId()).get(0), Integer.toString(machine.getNextQueue().size()));
        }

        for (Map.Entry<String, String> entry: UpdatedQueues.entrySet()) {
            UsedObject usedObject = new UsedObject(entry.getKey(), entry.getValue());
            usedObjects.add(usedObject);
        }

        for (Machine machine: machines){
            UsedObject usedObject = new UsedObject(machine.getId(), machine.getColor());
            usedObjects.add(usedObject);
        }

        return usedObjects;
    }
}
