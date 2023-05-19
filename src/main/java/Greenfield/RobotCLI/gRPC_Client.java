package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.gRPCServiceGrpc;
import Greenfield.gRPCServiceGrpc.gRPCServiceStub;
import Greenfield.GRPCService.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class gRPC_Client extends Thread{

    private final static String IP = "localhost";
    private final Robot robot;

    private gRPCMessage message;


    private volatile boolean stopCondition = false;

    protected gRPC_Client(Robot robot) {
        this.robot = robot;
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
            public void onNext(gRPCMessage grpcMessage) {}
            public void onError(Throwable throwable) {}
            public void onCompleted() {}
        });

        //System.out.println("Connected to robot id: " + robot.getId() + " port: " + robot.getPort());

        //it is a while loop which reads the message from robotController
        while(!stopCondition){

            try {
                synchronized (this){
                    this.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //we use the stream to communicate to the server our message.
            serverStream.onNext(message);

        }

        serverStream.onCompleted();
    }

    protected Robot getRobot() {
        return robot;
    }

    protected void setMessage(String m, Robot r){
        message = gRPCMessage.newBuilder()
                .setId(r.getId())
                .setPort(r.getPort())
                .setMessage(m)
                .build();
    }

}
