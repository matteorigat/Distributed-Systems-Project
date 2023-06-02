package Greenfield.CleaningRobot;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.GRPCService;
import Greenfield.Services.RobotResponseData;
import Greenfield.Simulator.PM10Simulator;
import Greenfield.Simulator.SimulatorInterface;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class CleaningRobotController {

    private final Client client = Client.create();
    private ClientResponse clientResponse = null;
    private String input;
    private String postPath;
    private String serverAddress;
    private Robot robot;
    private boolean mechanic = false;
    private boolean wantMechanic = false;
    private long myTimestamp;
    private final HashMap<Integer, StreamObserver<GRPCService.OkResponse>> mechanicQueue = new HashMap<>();
    private final HashSet<Integer> mechanicOk = new LinkedHashSet<Integer>();
    private final HashMap<Integer, gRPC_Client> clientRobotId = new HashMap<>();


    public void Init() throws InterruptedException {

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

        robot = new Robot(robotId,robotPort);


        //######################################################################################

        //#############################  REST  #################################################

        //######################################################################################

        // POST request to be added to the city
        postPath = "/robots/add";
        serverAddress = "http://localhost:1337";
        clientResponse = postRequest(client, serverAddress +postPath,robot);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            RobotResponseData resp = clientResponse.getEntity(RobotResponseData.class);
            robot.setX(resp.getX());
            robot.setY(resp.getY());
            for(Robot r: resp.getRobots().getRobotslist())
                Robots.getInstance().add(r);
            Robots.getInstance().setNumOfRobots(resp.getRobots().getNumOfRobots());
        } else {
            System.out.println("POST request failed.");
            return;
        }

        //######################################################################################

        //#############################  Start Simulator  ######################################

        //######################################################################################

        SimulatorInterface simulator = new SimulatorInterface();
        PM10Simulator pm10 = new PM10Simulator(simulator);
        pm10.start();

        //######################################################################################

        //#############################  gRPC  #################################################

        //######################################################################################

        gRPC_Server grpc = new gRPC_Server(robot, this);
        grpc.start();

        //Creating a parallel connection with every robot in the network
        for(int i=0; i<Robots.getInstance().getRobotslist().size(); i++) {
            if (Robots.getInstance().getRobotslist().get(i).getId() != robot.getId()) {
                gRPC_Client grpcClient = new gRPC_Client(Robots.getInstance().getRobotslist().get(i), this);
                grpcClient.start();
            }
        }

        //Hello message to other robots in the peer to peer network
        sendHelloMessage();

        //######################################################################################

        //#############################  MQQT & mechanic  ######################################

        //######################################################################################


        MQTT_Client mqtt = new MQTT_Client(simulator, robot);
        mqtt.start();


        Thread mechanicThread = asyncStartMechanic();


        //######################################################################################

        //#############################  Robot CLI  ############################################

        //######################################################################################


        System.out.print("\033[31m\nWelcome to the RobotClient!\033[0m");

        while (!input.equals("quit")){
            System.out.println("\n\nPress:");
            System.out.println("1: To display the robotList");
            System.out.println("2: To go to the mechanic now.");
            System.out.println("'quit': To leave the Greenfield city");
            input = in.nextLine();
            System.out.print("\n");

            switch (input) {
                case "1":
                    for (Robot r : Robots.getInstance().getRobotslist())
                        System.out.println(r.toString());
                    break;

                case "2":
                    //Message to other robots in the peer to peer network to go to mechanic
                    if(!mechanic && !wantMechanic)
                        mechanicSteps();
                    break;

                case "quit":
                    //Terminate the mechanic steps before quitting
                    while (isMechanic()){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    //Quit message to other robots in the peer to peer network
                    sendQuitMessage();

                    //DELETE: request to remove a cleaning robot from the Greenfield city
                    deleteRobotFromServer(robot.getId());

                    grpc.stopMeGently();
                    mqtt.stopMeGently();
                    pm10.stopMeGently();

                    synchronized (grpc){
                        grpc.notify();
                    }
                    synchronized (mqtt){
                        mqtt.notify();
                    }
                    break;
            }
        }

        try {
            mqtt.join();
            System.out.println("mqtt join");
            pm10.join();
            System.out.println("pm10 join");
            mechanicThread.join();
            System.out.println("mechanic join");
            grpc.join();
            System.out.println("gRPC join");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //######################################################################################

    //#####################   Methods Section   ############################################

    //######################################################################################



    private ClientResponse postRequest(Client client, String url, Robot r){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(r);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    protected void deleteRobotFromServer(int robotId){
        postPath = "/robots/" + robotId;
        clientResponse = deleteRequest(client, serverAddress+postPath);
        if(clientResponse != null && clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            System.out.println("\nDELETE request completed.\n" + clientResponse.toString());
        } else {
            System.out.println("\nDELETE request failed.\n" + clientResponse.toString());
        }
    }

    private ClientResponse deleteRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").delete(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    private Thread asyncStartMechanic(){
        Thread t = new Thread(() -> {
            try {
                while(!input.equals("quit")){
                    Thread.sleep(2000);  // 10% every 10 seconds //2s for testing purposes
                    if(Math.random() <= 0.1){
                        mechanicSteps();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        return t;
    }

    private void mechanicSteps() throws InterruptedException {
        System.out.println("starting mechanic steps");
        wantMechanic = true;
        mechanicOk.clear();
        sendMechanicMessage();
        while (mechanicOk.size() < Robots.getInstance().getRobotslist().size()-1){
            Thread.sleep(100);
        }
        wantMechanic = false;
        mechanic = true;
        System.out.println("\033[31mI'm at the mechanic\033[0m");
        Thread.sleep(10000);  // MECHANIC HEREEEEEE
        mechanic = false;

        GRPCService.OkResponse reply = GRPCService.OkResponse.newBuilder()
                .setId(robot.getId())
                .build();
        for(StreamObserver<GRPCService.OkResponse> s: mechanicQueue.values())
            s.onNext(reply);
        mechanicQueue.clear();
        System.out.println("\033[31mEnd mechanic\033[0m");
    }

    private void sendHelloMessage() throws InterruptedException {
        for(gRPC_Client c: clientRobotId.values()){
            while(c.isStubNull()){
                Thread.sleep(100);
            }
            c.asynchronousHello(robot);
        }
    }
    private void sendMechanicMessage() {
        for(gRPC_Client c: clientRobotId.values()){
            c.asynchronousMechanic(robot);
        }
    }

    private void sendQuitMessage() {
        for(gRPC_Client c: clientRobotId.values()){
            c.asynchronousQuit(robot.getId());
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    protected boolean isMechanic() {
        return mechanic;
    }

    protected boolean WantMechanic() {
        return wantMechanic;
    }

    protected long getMyTimestamp() {
        return myTimestamp;
    }

    protected void setMyTimestamp(long myTimestamp) {
        this.myTimestamp = myTimestamp;
    }

    protected HashMap<Integer, StreamObserver<GRPCService.OkResponse>> getMechanicQueue() {
        return mechanicQueue;
    }

    protected HashSet<Integer> getMechanicOk() {
        return mechanicOk;
    }

    protected HashMap<Integer, gRPC_Client> getClientRobotId() {
        return clientRobotId;
    }

}
