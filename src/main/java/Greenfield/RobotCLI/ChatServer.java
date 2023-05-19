package Greenfield.RobotCLI;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {

    final static int PORT = 1337;

    public static void main(String[] args) {
        try {

            System.out.println("[BOOT] Launching chat service on port "+PORT);

            Server server = ServerBuilder.forPort(PORT).addService(new ChatServiceImpl()).build();

            server.start();

            System.out.println("[BOOT] Server started!");

            server.awaitTermination();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

}
