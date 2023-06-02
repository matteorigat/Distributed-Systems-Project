package Greenfield.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Robot {

    private int id;
    private int port;

    private int x;
    private int y;

    public Robot(){}

    public Robot(int id, int port) {
        this.id = id;
        this.port = port;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Robot {" +
                "id: " + id +
                ", port: " + port +
                ", position: " + x + " " + y +
                '}';
    }
}