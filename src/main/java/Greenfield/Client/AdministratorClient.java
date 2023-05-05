package Greenfield.Client;

import Greenfield.Beans.Measure;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.ClientResponseData;
import com.sun.jersey.api.client.*;

import java.io.IOException;
import java.util.Scanner;

public class AdministratorClient {

    public static void main(String args[]) throws IOException {
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;

        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("\nWelcome to the AdministratorClient!");

        while(true){
            System.out.println("\n\nPress:");
            System.out.println("1: To get the list of the cleaning robots currently located in Greenfield.");
            System.out.println("2: The average of the last n air pollution levels sent to the server by a given robot.");
            System.out.println("3: The average of the air pollution levels sent by all the robots to the server and occurred from timestamps t1 and t2.");
            System.out.print("\nAny other button to quit.\n > ");
            input = in.nextLine();
            switch (input){

                //GET: The list of the cleaning robots currently located in Greenfield
                case "1":
                    String getPath = "/robots";
                    clientResponse = getRequest(client,serverAddress+getPath);
                    System.out.println(clientResponse.toString());
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        Robots users = clientResponse.getEntity(Robots.class);
                        System.out.println("\n\nRobots List -- n:" + users.getRobotslist().size());
                        for (Robot r : users.getRobotslist())
                            System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
                    }
                    else {
                        System.out.println("GET request failed.");
                        return;
                    }
                    break;


                //GET: The average of the last n air pollution levels sent to the server by a given robot
                case "2":
                    do{
                        System.out.print("\nInsert the robot id\n > ");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    int robotId = Integer.parseInt(input);
                    do {
                        System.out.print("\nInsert how many records you want to display\n > ");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    int n = Integer.parseInt(input); //last n measurements


                    getPath = "/measures/get/" + robotId + "/" + n;
                    clientResponse = getRequest(client,serverAddress+getPath);
                    System.out.println(clientResponse.toString());
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        ClientResponseData clientResponseData = clientResponse.getEntity(ClientResponseData.class);
                        System.out.println("\n\nMeasurements -- n:" + clientResponseData.getMeasurements().size());
                        for(Measure m: clientResponseData.getMeasurements())
                            System.out.println("id: " + m.getId() + " value: " + m.getValue() + " timestamp: " + m.getTimestamp());
                    }
                    else {
                        System.out.println("GET request failed.");
                        return;
                    }
                    break;


                //GET: The average of the air pollution levels sent by all the robots to the server
                //     and occurred from timestamps t1 and t2
                case "3":
                    do {
                        System.out.print("\nInsert the first timestamp\n > ");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    long t1 = Long.parseLong(input.trim());
                    do {
                        System.out.print("\nInsert the second timestamp\n > ");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    long t2 = Long.parseLong(input.trim());


                    getPath = "/measures/timeget/" + t1 + "/" + t2;
                    clientResponse = getRequest(client,serverAddress+getPath);
                    System.out.println(clientResponse.toString());
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        ClientResponseData clientResponseData = clientResponse.getEntity(ClientResponseData.class);
                        System.out.println("\n\nMeasurements -- n:" + clientResponseData.getMeasurements().size());
                        for(Measure m: clientResponseData.getMeasurements())
                            System.out.println("id: " + m.getId() + " value: " + m.getValue() + " timestamp: " + m.getTimestamp());
                    }
                    else {
                        System.out.println("GET request failed.");
                        return;
                    }
                    break;

                default: return;
            }
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

    private static boolean isNotNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
}
