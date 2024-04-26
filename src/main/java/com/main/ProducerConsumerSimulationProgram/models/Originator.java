package com.main.ProducerConsumerSimulationProgram.models;

import java.util.List;

public class Originator {
    private List<Machine> machineList;
    private List<Queue> queueList;

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public void setQueueList(List<Queue> queueList) {
        this.queueList = queueList;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public List<Queue> getQueueList() {
        return queueList;
    }

    public Memento saveStateToMemento(){
        return new Memento(machineList, queueList);
    }

    public void getStateFromMemento(Memento memento){
        machineList = memento.getMachineList();
        queueList = memento.getQueueList();
    }

    @Override
    public String toString() {
        return "Originator{" +
                "machineList=" + machineList +
                ", queueList=" + queueList +
                '}';
    }
}
