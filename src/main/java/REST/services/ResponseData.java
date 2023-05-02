package REST.services;

import REST.Beans.Robot;
import REST.Beans.Robots;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseData {
    private Robot.Coordinates pos;
    private Robots robo;

    public ResponseData(){}

    public ResponseData(Robot.Coordinates position, Robots robots) {
        this.pos = position;
        this.robo = robots;
    }

    @XmlElement
    public Robot.Coordinates getPosition() {
        return pos;
    }

    @XmlElement
    public Robots getRobots() {
        return robo;
    }
}
