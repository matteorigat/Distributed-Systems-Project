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
        System.out.println("\n--> ROBOT ADDED - " + robot.getId());
        return true;
    }

    public synchronized boolean removeById(int id){
        System.out.println("\n--> ROBOT REMOVED - " + id);
        return robotslist.removeIf(r -> r.getId() == id);
    }

    public int getDistrictById(int id){
        for(Robot r : robotslist)
            if(r.getId() == id)
                return calculateDistrict(r.getPosition().x, r.getPosition().y);

        return -1;

    }

    private int calculateDistrict(int x, int y){
        if(x <= 4 && y <= 4)
            return 0;
        else if (x >= 5 && y <= 4)
            return 1;
        else if (x >= 5 && y >= 5)
            return 2;
        else if (x <= 4 && y >= 5)
            return 3;

        return -1;
    }

}