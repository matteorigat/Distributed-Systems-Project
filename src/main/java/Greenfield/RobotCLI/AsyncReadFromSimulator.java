package Greenfield.RobotCLI;

import Greenfield.Simulator.Measurement;
import Greenfield.Simulator.SimulatorInterface;

import java.util.ArrayList;
import java.util.List;

public class AsyncReadFromSimulator extends Thread{

    private List<Double> listOfMeasurements = new ArrayList<>();
    private final SimulatorInterface sim;

    private volatile boolean stopCondition = false;

    public AsyncReadFromSimulator(SimulatorInterface sim) {
        this.sim = sim;
        sim.setArfs(this);
    }

    public void stopMeGently() {
        stopCondition = true;
    }

    @Override
    public void run() {
        super.run();

        try {
            while(!stopCondition){
                listOfMeasurements.add(averageCalculator());
                System.out.println("add avarage: " + listOfMeasurements);
                synchronized (this){
                    this.wait();
                }
                System.out.println("after wait");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private double averageCalculator(){
        double average = 0;
        int i=0;

        for(Measurement m : sim.readAllAndClean()){
            average += m.getValue();
            i++;
        }
        System.out.println("avarage calc: " + average + " " + i);
        return average/i;
    }

    public synchronized List<Double> getListOfMeasurements() {
        return listOfMeasurements;
    }
}
