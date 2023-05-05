package Greenfield.Client;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.RobotResponseData;
import Greenfield.Simulator.Measurement;
import Greenfield.Simulator.PM10Simulator;
import Greenfield.Simulator.SimulatorInterface;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.List;

public class CleaningRobotController {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;
        Robot robot = new Robot(1134,8888);
        Robots robotsList = null;


        //######################################################################################

        //############################## REST ##################################################

        //######################################################################################

        // POST request to be added to the city
        String postPath = "/robots/add";
        clientResponse = postRequest(client,serverAddress+postPath,robot);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            RobotResponseData resp = clientResponse.getEntity(RobotResponseData.class);
            robot.setPosition(resp.getPosition().x, resp.getPosition().y);
            robotsList = resp.getRobots();
        } else {
            System.out.println("POST request failed.");
            return;
        }

        /*
        //GET the list of all the robots in the city
        String getPath = "/robots";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            Robots robots = clientResponse.getEntity(Robots.class);
            System.out.println("Robots List");
            for (Robot r : robots.getRobotslist()){
                System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
            }
        } else {
            System.out.println("GET request failed.");
        }
         */


        //######################################################################################

        //############################## Start Simulator #######################################

        //######################################################################################

        SimulatorInterface sim = new SimulatorInterface();
        PM10Simulator pm10 = new PM10Simulator(sim);
        pm10.start();


        //######################################################################################

        //############################## MQQT ##################################################

        //######################################################################################


        MQTT_Client mqtt = new MQTT_Client(sim, robot.getId());
        mqtt.start();



        //######################################################################################

        //############################## gRPC ##################################################

        //######################################################################################

    }

    public static ClientResponse postRequest(Client client, String url, Robot r){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(r);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    public static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }


}
