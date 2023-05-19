package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
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
    private HashSet<StreamObserver> observers = new LinkedHashSet<StreamObserver>();

    private HashMap<StreamObserver, Integer> clientRobotIds = new HashMap<>();
    private HashMap<StreamObserver, gRPC_Client> clientRobotConnection = new HashMap<>();

    private Robots robots;

    private ArrayList<gRPC_Client> arrgrpc;

    protected gRPC_ServiceImpl(Robots robots, ArrayList<gRPC_Client> arrgrpc) {
        this.robots = robots;
        this.arrgrpc = arrgrpc;
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
                    robots.add(r);
                    gRPC_Client grpcClient = new gRPC_Client(r);
                    grpcClient.start();
                    arrgrpc.add(grpcClient);
                    clientRobotIds.put(responseObserver, id);
                    clientRobotConnection.put(responseObserver, grpcClient);
                }
            }

            //if there is an error (client abruptly disconnect) we remove the client.
            public void onError(Throwable throwable) {
                System.out.println("\n\nonError: client abruptly disconnect");
                int id = clientRobotIds.get(responseObserver);
                robots.removeById(id);
                deleteRobotFromServer(id);
                arrgrpc.remove(clientRobotConnection.get(responseObserver));
                synchronized (observers) {
                    observers.remove(responseObserver);
                }

            }

            //if the client explicitly terminated, we remove it from the hashset.
            public void onCompleted() {
                System.out.println("\n\nonCompleted: client explicitly terminated");
                robots.removeById(clientRobotIds.get(responseObserver));
                arrgrpc.remove(clientRobotConnection.get(responseObserver));
                synchronized (observers) {
                    observers.remove(responseObserver);
                }
            }
        };
    }
}
