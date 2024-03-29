package Greenfield.Beans;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Robots {

    @XmlElement(name="Greenfield_robots")
    private final List<Robot> robotslist;

    //@JsonIgnore
    private int[] numOfRobots;

    private static Robots instance;

    private Robots() {
        robotslist = new ArrayList<Robot>();

        numOfRobots = new int[4];
        for(int i=0; i<4; i++)
            numOfRobots[i] = 0;
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
        System.out.println("\n\033[31m--> ROBOT ADDED -- \033[0mid: " + robot.getId());
        return true;
    }

    public synchronized boolean removeById(int id){
        int district = findDistrictById(id);
        if(robotslist.removeIf(r -> r.getId() == id)){
            System.out.println("\n\033[31m--> ROBOT REMOVED -- \033[0mid:" + id);
            if(district >= 0)
                numOfRobots[district]--;
            return true;
        }
        return false;
    }

    private int findDistrictById(int id){
        for(Robot r : robotslist)
            if(r.getId() == id)
                return calculateDistrict(r.getX(), r.getY());

        return -1;
    }

    private int calculateDistrict(int x, int y){
        if(x <= 4 && y <= 4)
            return 0;
        else if (x >= 5 && y <= 4)
            return 1;
        else if (x >= 5)
            return 2;
        else
            return 3;

    }


    public void generatePosition(Robot r){

        int min, max;
        int district = 0;
        int x,y;

        for (int i = 0; i < numOfRobots.length; i++) {
            if (numOfRobots[i] < numOfRobots[district]) {
                district = i;
            }
        }
        numOfRobots[district]++;

        switch (district){
            case 0: case 3:
                min = 0;
                max = 4;
                break;
            case 1: case 2:
                min = 5;
                max = 9;
                break;
            default:
                System.out.println("position error 1");
                return;
        }

        x = (int)(min + (Math.random()*(max - min + 1)));

        switch (district){
            case 0: case 1:
                min = 0;
                max = 4;
                break;
            case 2: case 3:
                min = 5;
                max = 9;
                break;
            default:
                System.out.println("position error 2");
                return;
        }

        y = (int)(Math.floor(Math.random() *(max - min + 1) + min));

        r.setX(x);
        r.setY(y);
    }

    public int[] getNumOfRobots() {
        return numOfRobots;
    }

    public void setNumOfRobots(int[] numOfRobots) {
        this.numOfRobots = numOfRobots;
    }
}