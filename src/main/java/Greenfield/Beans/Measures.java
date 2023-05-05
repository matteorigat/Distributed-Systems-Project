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
    private List<Measure> measures;

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

    public synchronized List<Measure> getLastMeasuresById(int id, int n){
        List<Measure> measuresCopy = new ArrayList<>();
        int c=0;

        for (int i = measures.size() - 1; i >= 0; i--) {
            Measure m = measures.get(i);
            if(m.getId() == id){
                c++;
                measuresCopy.add(m);
                if (c == n)
                    return measuresCopy;
            }
        }

        return measuresCopy;
    }


    public synchronized List<Measure> getMeasurest1t2(long t1, long t2){
        List<Measure> measuresCopy = new ArrayList<>();

        for (Measure m : measures)
            if(m.getTimestamp() >= t1  && m.getTimestamp() <= t2)
                measuresCopy.add(m);

        return measuresCopy;
    }

}