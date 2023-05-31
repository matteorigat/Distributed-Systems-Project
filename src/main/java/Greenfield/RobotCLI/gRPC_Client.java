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

    private Hello mess;
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
        StreamObserver<Hello> serverStream = stub.hello(new StreamObserver<Hello>() {

            //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
            public void onNext(Hello hello) {
                System.out.println("onNext Callback");
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

    protected void setHello(Robot r){
        mess = Hello.newBuilder()
                .setId(r.getId())
                .setPort(r.getPort())
                .setX(r.getPosition().x)
                .setY(r.getPosition().y)
                .build();
    }

    /*protected void setMechanic(Robot r){
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        mess = Mechanic.newBuilder()
                .setId(r.getId())
                .setTimestamp(time)
                .build();
    }*/


}

