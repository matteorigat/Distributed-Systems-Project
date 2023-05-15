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
        System.out.println("\n--> ROBOT ADDED - " + robot.getId());
        return true;
    }

    public synchronized boolean removeById(int id){
        System.out.println("\n--> ROBOT REMOVED - " + id);
        removeFromDistrictById(id);
        return robotslist.removeIf(r -> r.getId() == id);
    }

    private void removeFromDistrictById(int id){
        for(Robot r : robotslist)
            if(r.getId() == id)
                numOfRobots[calculateDistrict(r.getPosition().x, r.getPosition().y)]--;
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


    public Robot.Coordinates generatePosition(Robot r){

        int min, max;
        int district = 0;
        int x,y;

        for (int i = 0; i < numOfRobots.length; i++) {
            System.out.println(i + " " + numOfRobots[i]);
            if (numOfRobots[i] < numOfRobots[district]) {
                district = i;
            }
        }

        System.out.println("\n+1 robot on district " + district);
        System.out.println(numOfRobots[district]);
        numOfRobots[district]++;
        System.out.println(numOfRobots[district]);

        switch (district){
            case 0: case 3:
                min = 0;
                max = 4;
                break;
            case 1: case 2:
                min = 5;
                max = 9;
                break;
            default: return null;
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
            default: return null;
        }

        y = (int)(Math.floor(Math.random() *(max - min + 1) + min));

        r.setPosition(x, y);
        return r.getPosition();
    }

}