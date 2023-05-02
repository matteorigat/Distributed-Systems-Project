package REST.Beans;

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

    public int getPort() {
        return port;
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public Coordinates getPosition() {
        return position;
    }

    public static class Coordinates{
        public int x,y;

        public Coordinates(){}
    }


}