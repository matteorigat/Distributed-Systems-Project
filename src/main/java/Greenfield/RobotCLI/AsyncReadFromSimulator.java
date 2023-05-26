package Greenfield.RobotCLI;

import Greenfield.Simulator.Measurement;
import Greenfield.Simulator.SimulatorInterface;

import java.util.ArrayList;
import java.util.List;

public class AsyncReadFromSimulator extends Thread{

    private final List<Double> listOfMeasurements = new ArrayList<>();
    private final SimulatorInterface sim;

    private volatile boolean stopCondition = false;

    protected AsyncReadFromSimulator(SimulatorInterface sim) {
        this.sim = sim;
        sim.setArfs(this);
    }

    protected void stopMeGently() {
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

    protected synchronized List<Double> getListOfMeasurementsAndClean() {
        List<Double> ml = new ArrayList<>(listOfMeasurements);
        listOfMeasurements.clear();
        return ml;
    }

    protected boolean isEmptyList() {
        return listOfMeasurements.isEmpty();
    }
}
