package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.CleaningRobot;
import Greenfield.gRPCServiceGrpc.gRPCServiceImplBase;
import Greenfield.GRPCService.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static Greenfield.RobotCLI.CleaningRobotController.deleteRobotFromServer;

public class gRPC_ServiceImpl extends gRPCServiceImplBase {

    //an hashset to store all the streams which the server uses to communicate with each client
    private final HashSet<StreamObserver> observers = new LinkedHashSet<StreamObserver>();

    private final HashMap<StreamObserver, gRPC_Client> clientRobotConnection = new HashMap<>();

    private final ArrayList<gRPC_Client> arrgrpc;

    private Robot robot;

    private ArrayList<Robot> waitingList = new ArrayList<>();

    private CleaningRobotController robotController;

    protected gRPC_ServiceImpl(Robot robot, ArrayList<gRPC_Client> arrgrpc, CleaningRobotController cr) {
        this.robot = robot;
        this.arrgrpc = arrgrpc;
        this.robotController = cr;
    }

    @Override
    public StreamObserver<gRPCMessage> grpc(final StreamObserver<gRPCMessage> responseObserver){

        //the stream used to communicate with a specific client is stored in a hash set (avoiding duplicates)
        synchronized (observers) {
            observers.add(responseObserver);
        }
        //it returns the stream that will be used by the clients to send messages.
        //the client will write on this stream
        return new StreamObserver<gRPCMessage>() {

            //receiving a message from a specific client
            public void onNext(gRPCMessage grpcMessage) {

                //unwrapping message
                int id = grpcMessage.getId();
                int port = grpcMessage.getPort();
                String message = grpcMessage.getMessage();

                System.out.println("\n\nReceived a message from robotID: " +id+ "\nmessage: " +message+ "\n\n\033[31m --> \033[0m");

                if(message.equals("hello")){
                    Robot r = new Robot(id, port);
                    Robots.getInstance().add(r);
                    gRPC_Client grpcClient = new gRPC_Client(r);
                    grpcClient.start();
                    arrgrpc.add(grpcClient);
                    clientRobotConnection.put(responseObserver, grpcClient);
                }
                else if(message.equals("mechanic")){
                    if(!robotController.isMechanic() && !robotController.WantMechanic()){
                        gRPCMessage reply = gRPCMessage.newBuilder()
                                .setId(robot.getId())
                                .setPort(robot.getPort())
                                .setMessage("OK")
                                .setTimestamp(System.currentTimeMillis())
                                .build();

                        responseObserver.onNext(reply);
                    }
                    else if (robotController.isMechanic()){
                        System.out.println("v ");
                    }
                }
            }

            //if there is an error (client abruptly disconnect) we remove the client.
            public void onError(Throwable throwable) {
                System.out.println("\n\nonError: client abruptly disconnect");
                synchronized (observers) {
                    observers.remove(responseObserver);
                }
                int id = clientRobotConnection.get(responseObserver).getRobot().getId();
                Robots.getInstance().removeById(id);
                robotController.deleteRobotFromServer(id);
                arrgrpc.remove(clientRobotConnection.get(responseObserver));

            }

            //if the client explicitly terminated, we remove it from the hashset.
            public void onCompleted() {
                System.out.println("\n\nonCompleted: client explicitly terminated");
                synchronized (observers) {
                    observers.remove(responseObserver);
                }
                int id = clientRobotConnection.get(responseObserver).getRobot().getId();
                Robots.getInstance().removeById(id);
                arrgrpc.remove(clientRobotConnection.get(responseObserver));
            }
        };
    }
}
