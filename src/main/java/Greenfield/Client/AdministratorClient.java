package Greenfield.Client;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AdministratorClient {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;

        // POST EXAMPLE
        String postPath = "/users/add";
        Robot user = new Robot(1234,7777);
        clientResponse = postRequest(client,serverAddress+postPath,user);
        System.out.println(clientResponse.toString());

        //The list of the cleaning robots currently located in Greenfield
        String getPath = "/robots";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        Robots users = clientResponse.getEntity(Robots.class);
        System.out.println("Robots List");
        for (Robot r : users.getRobotslist()){
            System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
        }

        //The average of the last n air pollution levels sent to the server by a given robot
        int robotPort = 7777;
        int n = 5; //last n measurements
        String getUserPath = "/robots/get/" + robotPort;
        clientResponse = getRequest(client,serverAddress+getUserPath);
        System.out.println(clientResponse.toString());
        Robot userResponse = clientResponse.getEntity(Robot.class);
        System.out.println("Name: " + userResponse.getId() + " Surname: " + userResponse.getPort());

    }

    public static ClientResponse postRequest(Client client, String url, Robot u){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(u);
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
