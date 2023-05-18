package Greenfield.RobotCLI;

import Greenfield.Beans.Robots;
import Greenfield.RobotOuterClass.Robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class gRPC_Server extends Thread{

    private final int robotPort;

    private Robots robotsList;
    private volatile boolean stopCondition = false;
    private ServerSocket serverSocket = null;


    public gRPC_Server(int robotPort, Robots robotsList) {
        this.robotPort = robotPort;
        this.robotsList = robotsList;
    }

    public void stopMeGently() {
        stopCondition = true;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void run(){
        super.run();

        try {
            serverSocket = new ServerSocket(robotPort);
            System.out.println("The gRPC server is running...\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Socket socket = null;

        while (!stopCondition){
            try {
                socket = serverSocket.accept();
                asyncReadFromSocket(socket);

            } catch (IOException e) {
                if(!serverSocket.isClosed())
                    throw new RuntimeException(e);
            }

        }

        System.out.println("The gRPC server has stopped...\n");
    }


    private void asyncReadFromSocket(final Socket s){
        Thread t = new Thread(() -> {
            Robot r = null;
            Greenfield.Beans.Robot robot;
            try {
                r = Robot.parseFrom(s.getInputStream());
                if(r.getHello()){
                    robot = new Greenfield.Beans.Robot(r.getId(), r.getPort());
                    robotsList.add(robot);
                    System.out.println("\n----- hello ----- " + r.getId() + "\n\033[31m --> \033[0m");
                }
                else if(r.getMechanic())
                    System.out.println("\n----- mechanic ----- " + r.getId() + "\n\033[31m --> \033[0m");
                else if (r.getGoodbye()) {
                    robotsList.removeById(r.getId());
                    System.out.println("\n----- goodbye ----- " + r.getId() + "\n\033[31m --> \033[0m");
                }
            } catch (Exception e){ // always raised at the end
                try {
                    s.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        t.start();
    }
}
