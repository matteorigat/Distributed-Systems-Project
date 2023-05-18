package Greenfield;

import Greenfield.Beans.Measure;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.ClientResponseData;
import com.sun.jersey.api.client.*;

import java.util.Scanner;

public class AdministratorClient {

    public static void main(String[] args){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;
        String getPath;
        Robots robots;
        ClientResponseData clientResponseData;

        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("\n\u001B[31mWelcome to the AdministratorClient!\u001B[0m");

        while(true){
            System.out.println("\n\nPress:");
            System.out.println("1: To get the list of the cleaning robots currently located in Greenfield.");
            System.out.println("2: The average of the last n air pollution levels sent to the server by a given robot.");
            System.out.println("3: The average of the air pollution levels sent by all the robots to the server and occurred from timestamps t1 and t2.");
            System.out.println("4: Remove cleaning robot from the Greenfield city.");
            System.out.print("\nAny other button to quit.\n\033[31m --> \033[0m");
            input = in.nextLine();
            switch (input){

                //GET: The list of the cleaning robots currently located in Greenfield
                case "1":
                    getPath = "/robots";
                    clientResponse = getRequest(client,serverAddress+getPath);
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        robots = clientResponse.getEntity(Robots.class);
                        System.out.println("\n\nRobots List -- n:" + robots.getRobotslist().size());
                        for (Robot r : robots.getRobotslist())
                            System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
                    }
                    else {
                        System.out.println("\nGET request failed.\n" + clientResponse.toString());
                    }
                    break;


                //GET: The average of the last n air pollution levels sent to the server by a given robot
                case "2":
                    do{
                        System.out.print("\nInsert the robot id\n\033[31m --> \033[0m");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    int robotId = Integer.parseInt(input);
                    do {
                        System.out.print("\nInsert how many records you want to display\n\033[31m --> \033[0m");
                        input = in.nextLine();
                    } while(isNotNumeric(input));
                    int n = Integer.parseInt(input); //last n measurements


                    getPath = "/measures/get/" + robotId + "/" + n;
                    clientResponse = getRequest(client,serverAddress+getPath);
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        clientResponseData = clientResponse.getEntity(ClientResponseData.class);
                        System.out.println("\n\nMeasurements -- n:" + clientResponseData.getMeasurements().size());
                        for(Measure m: clientResponseData.getMeasurements())
                            System.out.println("id: " + m.getId() + " value: " + m.getValue() + " timestamp: " + m.getTimestamp());
                    }
                    else {
                        System.out.println("\nGET request failed.\n" + clientResponse.toString());
                    }
                    break;


                //GET: The average of the air pollution levels sent by all the robots to the server
                //     and occurred from timestamps t1 and t2
                case "3":
                    do {
                        System.out.print("\nInsert the first timestamp\n\033[31m --> \033[0m");
                        input = in.nextLine();
                    } while(isNotTimestamp(input));
                    long t1 = Long.parseLong(input.trim());
                    do {
                        System.out.print("\nInsert the second timestamp\n\033[31m --> \033[0m");
                        input = in.nextLine();
                    } while(isNotTimestamp(input));
                    long t2 = Long.parseLong(input.trim());


                    getPath = "/measures/timeget/" + t1 + "/" + t2;
                    clientResponse = getRequest(client,serverAddress+getPath);
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        clientResponseData = clientResponse.getEntity(ClientResponseData.class);
                        System.out.println("\n\nMeasurements -- n:" + clientResponseData.getMeasurements().size());
                        for(Measure m: clientResponseData.getMeasurements())
                            System.out.println("id: " + m.getId() + " value: " + m.getValue() + " timestamp: " + m.getTimestamp());
                    }
                    else {
                        System.out.println("\nGET request failed.\n" + clientResponse.toString());
                    }
                    break;



                // Remove cleaning robot from the Greenfield city.
                case "4":
                    do{
                        System.out.print("\nInsert the robot id\n\033[31m --> \033[0m");
                        input = in.nextLine();
                    } while(isNotNumeric(input));

                    String postPath = "/robots/" + input;
                    clientResponse = deleteRequest(client,serverAddress+postPath);
                    if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                        System.out.println("\nDELETE request completed.\n" + clientResponse.toString());
                    } else {
                        System.out.println("\nDELETE request failed.\n" + clientResponse.toString());
                    }
                    break;


                default: return;
            }
        }
    }


    private static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    private static ClientResponse deleteRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").delete(ClientResponse.class);
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

    private static boolean isNotTimestamp(String str) {
        try {
            Long.parseLong(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
}
