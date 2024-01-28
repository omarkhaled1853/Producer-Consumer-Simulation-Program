package com.main.ProducerConsumerSimulationProgram.models;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.List;
import java.util.Map;

public class Originator {
    private Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> state;

    public Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> getState() {
        return state;
    }

    public void setState(Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> state) {
        this.state = state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateToMemento(Memento memento){
        state = memento.getState();
    }
}
