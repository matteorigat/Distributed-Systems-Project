package Greenfield.Simulator;

import Greenfield.RobotCLI.AsyncReadFromSimulator;

import java.util.ArrayList;
import java.util.List;

public class SimulatorInterface implements Buffer{

    private List<Measurement> measurementList = new ArrayList<>();
    private List<Measurement> buffer = new ArrayList<>();

    private AsyncReadFromSimulator arfs;


    @Override
    public synchronized void addMeasurement(Measurement m) {
        buffer.add(m);
        if(buffer.size() >= 8){
            measurementList = new ArrayList<>(buffer.subList(buffer.size() - 8, buffer.size()));
            synchronized (arfs){
                arfs.notify();
            }
            buffer = new ArrayList<>(buffer.subList(buffer.size() - 4, buffer.size()));
        }
    }

    @Override
    public synchronized List<Measurement> readAllAndClean() {
        List<Measurement> ml = new ArrayList<>(measurementList);
        measurementList.clear();
        //System.out.println("read clean: " + ml);
        return ml;
    }

    public void setArfs(AsyncReadFromSimulator arfs) {
        this.arfs = arfs;
    }
}