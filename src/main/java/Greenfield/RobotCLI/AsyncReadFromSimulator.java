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
                averageCalculator();
                synchronized (this){
                    this.wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized void averageCalculator(){
        double average = 0;
        int i=0;

        for(Measurement m : sim.readAllAndClean()){
            average += m.getValue();
            i++;
        }
        if(i > 0)
            listOfMeasurements.add(average/i);
    }

    public synchronized List<Double> getListOfMeasurementsAndClean() {
        List<Double> ml = new ArrayList<>(listOfMeasurements);
        listOfMeasurements.clear();
        return ml;
    }

    public boolean isEmptyList() {
        return listOfMeasurements.isEmpty();
    }
}
