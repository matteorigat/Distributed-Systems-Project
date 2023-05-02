package REST.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Robot {

    private int id;
    private int port;

    public Robot(){}
    public Robot(int id, int port) {
        this.id = id;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }


}