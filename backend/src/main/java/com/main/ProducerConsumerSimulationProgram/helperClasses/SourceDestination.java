package com.main.ProducerConsumerSimulationProgram.helperClasses;

import java.util.List;

public class SourceDestination {
    private String source;
    private List<String> destination;

    public String getSource() {
        return source;
    }

    public List<String> getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "SourceDestination{" +
                "source='" + source + '\'' +
                ", destination=" + destination +
                '}';
    }
}
