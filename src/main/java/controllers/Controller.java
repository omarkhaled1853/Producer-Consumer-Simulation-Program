package controllers;

import helperClasses.SourceDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.Simulator;
import service.UsedObject;

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
        return simulator.update();
    }
}
