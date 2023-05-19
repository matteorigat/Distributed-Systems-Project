package Greenfield.RobotCLI;

import Greenfield.Beans.Robots;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class gRPC_Server extends Thread{

    private final int robotPort;

    private final Robots robots;

    private final ArrayList<gRPC_Client> arrgrpc;

    private volatile boolean stopCondition = false;

    protected gRPC_Server(int robotPort, Robots robots, ArrayList<gRPC_Client> arrgrpc) {
        this.robotPort = robotPort;
        this.robots = robots;
        this.arrgrpc = arrgrpc;
    }

    protected void stopMeGently() {
        stopCondition = true;
    }

    @Override
    public void run(){
        super.run();

        try {
            Server server = ServerBuilder.forPort(robotPort).addService(new gRPC_ServiceImpl(robots, arrgrpc)).build();
            server.start();
            System.out.println("The gRPC server is running...\n");
            while (!stopCondition){
                synchronized (this){
                    this.wait();
                }
            }
            server.shutdown();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("The gRPC server has stopped...\n");
    }
}
