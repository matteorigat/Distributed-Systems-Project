package Greenfield.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Robot {

    private int id;
    private int port;
    private Coordinates position;

    public Robot(){}
    public Robot(int id, int port) {
        this.id = id;
        this.port = port;
        position = new Coordinates();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public static class Coordinates{
        public int x,y;

        public Coordinates(){
            x=-1;
            y=-1;
        }
    }


}