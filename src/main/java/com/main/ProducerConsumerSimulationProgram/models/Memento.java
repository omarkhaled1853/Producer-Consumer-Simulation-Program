package com.main.ProducerConsumerSimulationProgram.models;


import ch.qos.logback.core.joran.sanity.Pair;

import java.util.List;
import java.util.Map;

public class Memento {
    private Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> state;

    public Memento(Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> state) {
        this.state = state;
    }

    public Pair<Map<Queue, List<Machine>>, Map<Machine, Queue>> getState() {
        return state;
    }
}
