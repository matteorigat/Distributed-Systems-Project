package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.gRPCServiceGrpc.gRPCServiceImplBase;
import Greenfield.GRPCService.*;
import io.grpc.stub.StreamObserver;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;


public class gRPC_ServiceImpl extends gRPCServiceImplBase {

    //an hashset to store all the streams which the server uses to communicate with each client
    private final HashSet<StreamObserver> observers = new LinkedHashSet<StreamObserver>();

    private Robot robot;

    private CleaningRobotController robotController;

    protected gRPC_ServiceImpl(Robot robot, CleaningRobotController cr) {
        this.robot = robot;
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
                System.out.println("\nMessage from robot: " +id+ " message: " +message);


                if(message.equals("hello")){
                    Robot r = new Robot(id, port);
                    Robots.getInstance().add(r);
                    gRPC_Client grpcClient = new gRPC_Client(r, robotController);
                    grpcClient.start();
                    robotController.getClientRobotConnection().put(grpcClient, responseObserver);

                    if(robotController.WantMechanic()){
                        gRPCMessage reply = gRPCMessage.newBuilder()
                                .setId(robot.getId())
                                .setPort(robot.getPort())
                                .setMessage("mechanic")
                                .setTimestamp(System.currentTimeMillis())
                                .build();

                        responseObserver.onNext(reply);
                    }
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
                        System.out.println("ok sent, i dont't want mechanic");
                    }
                    else if (robotController.isMechanic()){
                        robotController.getMechanicQueue().add(responseObserver);
                        System.out.println("added to queue");
                    }
                    else if(robotController.WantMechanic() && !robotController.isMechanic()){
                        if(grpcMessage.getTimestamp() < robotController.getMyTimestamp()){
                            gRPCMessage reply = gRPCMessage.newBuilder()
                                    .setId(robot.getId())
                                    .setPort(robot.getPort())
                                    .setMessage("OK")
                                    .setTimestamp(System.currentTimeMillis())
                                    .build();

                            responseObserver.onNext(reply);
                            System.out.println("ok sent, i wait to go to mechanic");
                        } else {
                            robotController.getMechanicQueue().add(responseObserver);
                            System.out.println("added to queue, i'm first");
                        }

                    } else {
                        System.out.println("mechanic something wrong in gRPC_ServiceImpl");
                    }
                }
            }

            //if there is an error (client abruptly disconnect) we remove the client.
            public void onError(Throwable throwable) {
                System.out.println("\n\n\033[31monError: client abruptly disconnect\033[0m");
                synchronized (observers) {
                    observers.remove(responseObserver);
                }
                if(responseObserver == null)
                    System.out.println("response is null");
                System.out.println("conteins response? " + robotController.getClientRobotConnection().keySet().contains(responseObserver));
                gRPC_Client key = null;
                for(Map.Entry<gRPC_Client, StreamObserver> e: robotController.getClientRobotConnection().entrySet())
                    if(e.getValue().equals(responseObserver)){
                        System.out.println("key found");
                        key = e.getKey();
                        break;
                    }
                System.out.println(key);
                if(key != null){
                    int id = key.getRobot().getId();
                    System.out.println("id: " + id);
                    Robots.getInstance().removeById(id);
                    robotController.deleteRobotFromServer(id);// only one robot is successful
                    robotController.getMechanicQueue().remove(responseObserver);
                    robotController.getMechanicOk().remove(id);
                }
            }

            //if the client explicitly terminated, we remove it from the hashset.
            public void onCompleted() {
                System.out.println("\n\n\033[31monCompleted: client explicitly terminated\033[0m");
                synchronized (observers) {
                    observers.remove(responseObserver);
                }
                if(responseObserver == null)
                    System.out.println("response is null");
                System.out.println("conteins response? " + robotController.getClientRobotConnection().keySet().contains(responseObserver));
                gRPC_Client key = null;
                for(Map.Entry<gRPC_Client, StreamObserver> e: robotController.getClientRobotConnection().entrySet())
                    if(e.getValue().equals(responseObserver)){
                        System.out.println("key found");
                        key = e.getKey();
                        break;
                    }
                System.out.println(key);
                if(key != null){
                    int id = key.getRobot().getId();
                    System.out.println("id: " + id);
                    Robots.getInstance().removeById(id);
                    robotController.getMechanicQueue().remove(responseObserver);
                    robotController.getMechanicOk().remove(id);
                }
            }
        };
    }
}
