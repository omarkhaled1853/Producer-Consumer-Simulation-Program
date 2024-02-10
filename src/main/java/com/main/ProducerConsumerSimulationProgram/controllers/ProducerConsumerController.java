package com.main.ProducerConsumerSimulationProgram.controllers;

import com.main.ProducerConsumerSimulationProgram.helperClasses.SourceDestination;
import com.main.ProducerConsumerSimulationProgram.models.Machine;
import com.main.ProducerConsumerSimulationProgram.models.Queue;
import com.main.ProducerConsumerSimulationProgram.models.Simulator;
import com.main.ProducerConsumerSimulationProgram.services.ProducerConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class ProducerConsumerController {
    private final Simulator simulator;
    private final ProducerConsumerService producerConsumerService;

    @Autowired
    public ProducerConsumerController(Simulator simulator, ProducerConsumerService producerConsumerService) {
        this.simulator = simulator;
        this.producerConsumerService = producerConsumerService;
    }

    @PostMapping ("/start")
    public int start(@RequestBody List<SourceDestination> list) throws InterruptedException {
        producerConsumerService.handler(list);
        simulator.start(producerConsumerService);
        return producerConsumerService.getProducts();
    }

    @GetMapping("/machines")
    public List<Machine> machines(){
        return simulator.update(producerConsumerService).getMachineList();
    }

    @GetMapping("/queues")
    public List<Queue> queues(){
        return simulator.update(producerConsumerService).getQueueList();
    }

    @GetMapping("/stop")
    public void stop(){
        simulator.end(producerConsumerService);
    }

//    @GetMapping("/replay")
//    public void replay(){
//
//    }
}
