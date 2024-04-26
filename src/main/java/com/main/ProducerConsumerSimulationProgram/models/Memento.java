package com.main.ProducerConsumerSimulationProgram.models;

import java.util.List;

public class Memento {
    private final List<Machine> machineList;
    private final List<Queue> queueList;

    public Memento(List<Machine> machineList, List<Queue> queueList) {
        this.machineList = machineList;
        this.queueList = queueList;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public List<Queue> getQueueList() {
        return queueList;
    }

    @Override
    public String toString() {
        return "machineList=" + machineList +
                ", queueList=" + queueList;
    }
}
