package Greenfield.Client;

import Greenfield.Beans.Measure;
import Greenfield.Beans.Measures;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.ClientResponseData;
import com.google.gson.Gson;
import com.sun.jersey.api.client.*;

import java.util.ArrayList;
import java.util.List;

public class AdministratorClient {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;



        //The list of the cleaning robots currently located in Greenfield
        String getPath = "/measures";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        Robots users = clientResponse.getEntity(Robots.class);
        System.out.println("Robots List");
        for (Robot r : users.getRobotslist()){
            System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
        }

        //The average of the last n air pollution levels sent to the server by a given robot
        int robotId = 1134;
        int n = 5; //last n measurements
        String getUserPath = "/measures/get/" + robotId + "/" + n;
        clientResponse = getRequest(client,serverAddress+getUserPath);
        System.out.println(clientResponse.toString());
        ClientResponseData clientResponseData = clientResponse.getEntity(ClientResponseData.class);
        for(Measure m: clientResponseData.getMeasurements())
            System.out.println("id: " + m.getId() + " value: " + m.getValue() + " timestamp: " + m.getTimestamp());

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
