package REST.Client;

import REST.Beans.Robot;
import REST.Beans.Robots;
import REST.services.ResponseData;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CleaningRobotController {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;

        // POST EXAMPLE
        String postPath = "/robots/add";
        Robot robot = new Robot(1224,8888);
        clientResponse = postRequest(client,serverAddress+postPath,robot);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            ResponseData resp = clientResponse.getEntity(ResponseData.class);
            robot.setPosition(resp.getPosition().x, resp.getPosition().y);
            System.out.println("position: " + robot.getPosition().x + "," + robot.getPosition().y);
            System.out.println("Robots List: num of elem... " + resp.getRobots().getRobotslist().size());
            for (Robot r : resp.getRobots().getRobotslist()){
                System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
            }
        } else {
            System.out.println("POST request failed.");
        }

        //GET REQUEST #1
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
