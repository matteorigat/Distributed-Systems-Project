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


    public synchronized boolean add(Measure measure){
        for(Measure m : measures)
            if(m.getId() == measure.getId())
                return false;
        measures.add(measure);
        return true;
    }

    public Measure getById(int id){
        List<Measure> measuresCopy = getMeasureslist();
        for(Measure m: measuresCopy)
            if(m.getId() == id)
                return m;
        return null;
    }

}