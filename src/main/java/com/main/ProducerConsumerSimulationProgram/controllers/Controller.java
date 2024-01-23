package com.main.ProducerConsumerSimulationProgram.controllers;

import com.main.ProducerConsumerSimulationProgram.helperClasses.SourceDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.main.ProducerConsumerSimulationProgram.service.Simulator;
import com.main.ProducerConsumerSimulationProgram.service.UsedObject;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class Controller {
    private final Simulator simulator;

    @Autowired
    public Controller(Simulator simulator) {
        this.simulator = simulator;
    }

    @PostMapping ("/start")
    public void start(@RequestBody List<SourceDestination> list) throws InterruptedException {
        simulator.setGraph(simulator.getGraph(list));
        simulator.start();
    }

    @GetMapping ("/update")
    public List<UsedObject> update(){
        System.out.println(simulator.update());
        return simulator.update();
    }

    @GetMapping("/replay")
    public void replay(){
        simulator.replay();
    }
}
