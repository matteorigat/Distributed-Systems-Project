package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class gRPC_Server extends Thread{

    private final Robot robot;

    private final CleaningRobotController robotController;

    private volatile boolean stopCondition = false;

    protected gRPC_Server(Robot robot, CleaningRobotController cr) {
        this.robot = robot;
        this.robotController = cr;
    }

    protected void stopMeGently() {
        stopCondition = true;
    }

    @Override
    public void run(){
        super.run();

        try {
            Server server = ServerBuilder.forPort(robot.getPort()).addService(new gRPC_ServiceImpl(robot, robotController)).build();
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
