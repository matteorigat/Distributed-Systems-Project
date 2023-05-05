package Greenfield.Beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Robots {

    @XmlElement(name="Greenfield_robots")
    private List<Robot> robotslist;

    private static Robots instance;

    private Robots() {
        robotslist = new ArrayList<Robot>();
    }

    //singleton
    public synchronized static Robots getInstance(){
        if(instance==null)
            instance = new Robots();
        return instance;
    }
    public synchronized List<Robot> getRobotslist() {
        return new ArrayList<>(robotslist);
    }


    public synchronized boolean add(Robot robot){

        for(Robot r : robotslist)
            if(r.getId() == robot.getId())
                return false;

        robotslist.add(robot);
        System.out.println("Robot successfully added");
        return true;
    }

    public synchronized Boolean removeById(int id){
        return robotslist.removeIf(r -> r.getId() == id);
    }

}