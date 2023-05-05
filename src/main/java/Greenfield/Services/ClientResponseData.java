package Greenfield.Services;

import Greenfield.Beans.Measure;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientResponseData {
    private List<Measure> measurements;

    public ClientResponseData(){}

    public ClientResponseData(List<Measure> measurements) {
        this.measurements = measurements;
    }

    public List<Measure> getMeasurements() {
        return measurements;
    }
}
