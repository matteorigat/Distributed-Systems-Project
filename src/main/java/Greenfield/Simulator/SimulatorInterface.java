package Greenfield.Simulator;

import java.util.ArrayList;
import java.util.List;

public class SimulatorInterface implements Buffer{

    private List<Measurement> measurementList = new ArrayList<>();


    @Override
    public synchronized void addMeasurement(Measurement m) {
        measurementList.add(m);
    }

    @Override
    public synchronized List<Measurement> readAllAndClean() {
        List<Measurement> ml = new ArrayList<>(measurementList);
        measurementList.clear();
        return ml;
    }
}