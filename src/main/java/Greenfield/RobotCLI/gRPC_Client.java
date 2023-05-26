package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.gRPCServiceGrpc;
import Greenfield.gRPCServiceGrpc.gRPCServiceStub;
import Greenfield.GRPCService.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Map;

public class gRPC_Client extends Thread{

    private final static String IP = "localhost";
    private final Robot robot;

    private gRPCMessage mess;
    private final CleaningRobotController robotController;

    private volatile boolean stopCondition = false;

    protected gRPC_Client(Robot robot, CleaningRobotController rc) {
        this.robot = robot;
        this.robotController = rc;
    }

    protected void stopMeGently() {
        stopCondition = true;
    }

    @Override
    public void run(){
        super.run();

        //opening a connection with robot
        final ManagedChannel channel = ManagedChannelBuilder.forTarget(IP+":"+robot.getPort()).usePlaintext().build();

        //creating the asynchronous stub
        gRPCServiceStub stub = gRPCServiceGrpc.newStub(channel);

        //the stub returns a stream (to communicate with the server, and thus with all the other clients).
        //the argument is the stream of messages which are transmitted by the server.
        StreamObserver<gRPCMessage> serverStream = stub.grpc(new StreamObserver<gRPCMessage>() {

            //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
            public void onNext(gRPCMessage grpcMessage) {
                int id = grpcMessage.getId();
                String message = grpcMessage.getMessage();
                StreamObserver<gRPCMessage> responseObserver = robotController.getClientRobotConnection().get(robotController.getClientRobotId().get(id));
                System.out.println("\nCallback from robot: " +id+ " message: " +message);

                if(message.equals("OK")){
                    if(!robotController.getMechanicOk().contains(id))
                        robotController.getMechanicOk().add(grpcMessage.getId());
                    System.out.println("Callback received ok");
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
                        System.out.println("Callback ok sent, i dont't want mechanic");
                    }
                    else if (robotController.isMechanic()){
                        robotController.getMechanicQueue().add(responseObserver);
                        System.out.println("Callback added to queue");
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
                            System.out.println("Callback ok sent, i wait to go to mechanic");
                        } else {
                            robotController.getMechanicQueue().add(responseObserver);
                            System.out.println("Callback added to queue, i'm first");
                        }

                    } else {
                        System.out.println("Callback mechanic something wrong in gRPC_Client");
                    }
                }
            }
            public void onError(Throwable throwable) {
                System.out.println("onError Callback");
                channel.shutdownNow();

            }
            public void onCompleted() {
                System.out.println("onCompleted Callback");
                channel.shutdownNow();
            }
        });

        robotController.getClientRobotConnection().put(this, serverStream);

        //while loop which reads the message from robotController
        while(!stopCondition){

            try {
                synchronized (this){
                    this.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //we use the stream to communicate to the server our message.
            serverStream.onNext(mess);

        }
    }

    protected Robot getRobot() {
        return robot;
    }

    protected void setMessage(String m, Robot r){
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        mess = gRPCMessage.newBuilder()
                .setId(r.getId())
                .setPort(r.getPort())
                .setMessage(m)
                .setTimestamp(time)
                .build();
    }


}

