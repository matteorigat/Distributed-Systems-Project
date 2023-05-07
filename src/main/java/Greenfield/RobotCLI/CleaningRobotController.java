package Greenfield.RobotCLI;

import Greenfield.RobotOuterClass;
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
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class CleaningRobotController {

    private static final Client client = Client.create();
    private static ClientResponse clientResponse = null;
    private static Robots robots = null;
    private static String input;
    private static String postPath;
    private static String serverAddress;
    private static RobotOuterClass.Robot robotgRPC;

    public static void main(String args[]) throws IOException {

        Scanner in = new Scanner(System.in);

        int robotId = -1;
        int robotPort = -1;

        do{
            System.out.print("\nInsert the robot id\n\033[31m --> \033[0m");
            input = in.nextLine();
            if(isNumeric(input))
                robotId = Integer.parseInt(input);
        } while(robotId < 0);
        do{
            System.out.print("\nInsert the robot port\n\033[31m --> \033[0m");
            input = in.nextLine();
            if(isNumeric(input))
                robotPort = Integer.parseInt(input);
        } while(robotPort  <= 1023 || robotPort  >= 65535);

        Robot robot = new Robot(robotId,robotPort);


        //######################################################################################

        //############################## REST ##################################################

        //######################################################################################

        // POST request to be added to the city
        postPath = "/robots/add";
        serverAddress = "http://localhost:1337";
        clientResponse = postRequest(client, serverAddress +postPath,robot);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            RobotResponseData resp = clientResponse.getEntity(RobotResponseData.class);
            robot.setPosition(resp.getPosition().x, resp.getPosition().y);
            robots = resp.getRobots();
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

        gRPC_Server gRPCClient = new gRPC_Server(robotPort, robots);
        gRPCClient.start();

        //Hello message to other robots in the peer to peer network
        sendgRPC(robotId,robotPort,true,false,false);

        //######################################################################################

        //############################## MQQT ##################################################

        //######################################################################################


        MQTT_Client mqtt = new MQTT_Client(sim, robot);
        mqtt.start();


        //######################################################################################

        //############################## Robot CLI ##################################################

        //######################################################################################


        System.out.print("\033[31m\nWelcome to the RobotClient!\033[0m");
        while (!input.equals("quit")){
            System.out.println("\n\nPress:");
            System.out.println("1: To display the robotList");
            System.out.println("2: To go to the mechanic now.");
            System.out.print("'quit': To leave the Greenfield city and quit the console :)\n\033[31m --> \033[0m");
            input = in.nextLine();

            switch (input) {
                case "1":
                    for (Robot r : robots.getRobotslist())
                        System.out.println(r.toString());
                    break;

                case "2":
                    //Message to other robots in the peer to peer network to go to mechanic
                    sendgRPC(robotId,robotPort,false,true,false);
                    break;

                case "quit":
                    // COMPLETE MECHANIC ????

                    //Goodbye message to other robots in the peer to peer network
                    sendgRPC(robotId, robotPort,false,false,true);
                    gRPCClient.stopMeGently();


                    //DELETE: request to remove a cleaning robot from the Greenfield city
                    deleteRobotFromServer(robotId);
                    mqtt.stopMeGently();
                    synchronized (mqtt){
                        mqtt.notify();
                    }
                    pm10.stopMeGently();
                    break;
            }
        }

        try {
            mqtt.join();
            System.out.println("mqtt join");
            pm10.join();
            System.out.println("pm10 join");
            gRPCClient.join();
            System.out.println("gRPC join");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return;
    }

    private static void sendgRPC(int robotId, int robotPort, boolean hello, boolean mechanic, boolean goodbye) throws IOException {
        RobotOuterClass.Robot robotgRPC =
                RobotOuterClass.Robot.newBuilder()
                        .setId(robotId)
                        .setPort(robotPort)
                        .setIp("localhost")
                        .setHello(hello)
                        .setMechanic(mechanic)
                        .setGoodbye(goodbye)
                        .build();

        Socket s;
        for(int i=0; i<robots.getRobotslist().size(); i++){
            if(robots.getRobotslist().get(i).getId() != robotId){
                try {
                    s = new Socket("localhost", robots.getRobotslist().get(i).getPort());
                    robotgRPC.writeTo(s.getOutputStream());
                    s.close();
                } catch (ConnectException e) {
                    robots.getRobotslist().remove(i);
                    deleteRobotFromServer(robots.getRobotslist().get(i).getId());
                    continue;
                }
            }
        }
    }

    private static void deleteRobotFromServer(int robotId){
        postPath = "/robots/" + robotId;
        clientResponse = deleteRequest(client, serverAddress+postPath);
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            System.out.println("\nDELETE request completed.\n" + clientResponse.toString());
        } else {
            System.out.println("\nDELETE request failed.\n" + clientResponse.toString());
        }
    }

    private boolean probMechanic(){
        return Math.random() <= 0.1;
    }

    private static ClientResponse postRequest(Client client, String url, Robot r){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(r);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
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


    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }



}
