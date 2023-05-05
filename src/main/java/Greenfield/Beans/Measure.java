package Greenfield.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Measure {

    private int id;
    private double value;
    private long timestamp;

    public Measure(){}

    public Measure(int id, double value, long timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
