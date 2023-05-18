package Greenfield.Beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Measure {

    private int id;
    private List<Double> values = new ArrayList<>();
    private long timestamp;

    public Measure(){}

    public Measure(int id, List<Double> values, long timestamp) {
        this.id = id;
        this.values = values;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
