package Greenfield.Client;

import it.ewlab.actor.ActorOuterClass.Actor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class gRPC_Client extends Thread{

    private final int robotPort;

    public gRPC_Client(int robotPort) {
        this.robotPort = robotPort;
    }


    @Override
    public void run() {
        super.run();


        ServerSocket serverSocket = null;
        Socket s = null;
        Actor actor = null;
        try {
            serverSocket = new ServerSocket(robotPort);
            System.out.println("The gRPC server is running...\n");
            s = serverSocket.accept();
            actor = Actor.parseFrom(s.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(actor);
    }
}
