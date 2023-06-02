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
    private Robots robo;
    private int xpos;
    private int ypos;

    public RobotResponseData(){}

    public RobotResponseData(Robots robo, int x, int y) {
        this.robo = robo;
        this.xpos = x;
        this.ypos = y;
    }

    @XmlElement
    public int getX() {
        return xpos;
    }

    @XmlElement
    public int getY() {
        return ypos;
    }

    @XmlElement
    public Robots getRobots() {
        return robo;
    }
}
