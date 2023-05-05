package Greenfield.Services;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RobotResponseData {
    private Robot.Coordinates pos;
    private Robots robo;

    public RobotResponseData(){}

    public RobotResponseData(Robot.Coordinates position, Robots robots) {
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
