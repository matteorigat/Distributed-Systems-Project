package Greenfield.Beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Measures {

    @XmlElement(name="Greenfield_measure")
    private final List<Measure> measures;

    private static Measures instance;

    private Measures() {
        measures = new ArrayList<Measure>();
    }

    //singleton
    public synchronized static Measures getInstance(){
        if(instance==null)
            instance = new Measures();
        return instance;
    }
    public synchronized List<Measure> getMeasureslist() {
        return new ArrayList<>(measures);
    }



    public synchronized void add(Measure measure){
        measures.add(measure);
    }

    public synchronized double getAverageLastNById(int id, int n){
        int count = 0;
        double sum = 0;
        int isum = 0;

        for (int i = measures.size() - 1; i >= 0; i--){  // among all the measures
            Measure m = measures.get(i);
            if(m.getId() == id){   // find the ones that match id
                count++;
                for(Double d : m.getValues()){
                    sum += d;
                    isum++;
                }
                if (count == n)   // sum for n cycles
                    return sum/isum;
            }
        }

        return sum/isum;
    }


    public synchronized double getAveraget1t2(long t1, long t2){
        double sum = 0;
        int isum = 0;

        for (Measure m : measures)
            if(m.getTimestamp() >= t1  && m.getTimestamp() <= t2)
                for(Double d : m.getValues()){
                    sum += d;
                    isum++;
                }

        return sum/isum;

    }

}