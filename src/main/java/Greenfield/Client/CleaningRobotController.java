package Greenfield.Client;

import Greenfield.ActorOuterClass;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.RobotResponseData;
import Greenfield.Simulator.PM10Simulator;
import Greenfield.Simulator.SimulatorInterface;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class CleaningRobotController {

    public static void main(String args[]) throws IOException {
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;
        Robots robotsList = null;

        Scanner in = new Scanner(System.in);
        String input;

        do{
            System.out.print("\nInsert the robot id\n > ");
            input = in.nextLine();
        } while(isNotNumeric(input));
        int robotId = Integer.parseInt(input);
        do{
            System.out.print("\nInsert the robot port\n > ");
            input = in.nextLine();
        } while(isNotNumeric(input));
        int robotPort = Integer.parseInt(input);

        Robot robot = new Robot(robotId,robotPort);


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


        //######################################################################################

        //############################## Start Simulator #######################################

        //######################################################################################

        SimulatorInterface sim = new SimulatorInterface();
        PM10Simulator pm10 = new PM10Simulator(sim);
        pm10.start();

        //######################################################################################

        //############################## gRPC ##################################################

        //######################################################################################

        gRPC_Client gRPCClient = new gRPC_Client(robotPort);
        gRPCClient.start();


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Socket s = new Socket("localhost", robotPort);

        ActorOuterClass.Actor actor =
                ActorOuterClass.Actor.newBuilder()
                        .setName("Christian")
                        .setSurname("Bale")
                        .setSex(ActorOuterClass.Actor.Sex.MALE)
                        .addMovie(ActorOuterClass.Actor.Movie.newBuilder()
                                .setTitle("The Prestige")
                                .setYear(2006))
                        .addMovie(ActorOuterClass.Actor.Movie.newBuilder()
                                .setTitle("The Dark Knight")
                                .setYear(2008))
                        .build();

        actor.writeTo(s.getOutputStream());

        s.close();



        //######################################################################################

        //############################## MQQT ##################################################

        //######################################################################################


        MQTT_Client mqtt = new MQTT_Client(sim, robot.getId());
        mqtt.start();



        System.out.print("\nWelcome to the RobotClient!");
        while (!input.equals("quit")){
            System.out.println("\n\nPress:");
            System.out.println("'fix': To go to the mechanic now.");
            System.out.print("'quit': To quit :)\n > ");
            input = in.nextLine();
        }


        try {
            mqtt.join();
            pm10.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized void delay() throws InterruptedException {
        wait(10000);
    }

    private boolean probMechanic(){
        return Math.random() < 0.1;
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


    private static boolean isNotNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }


}
