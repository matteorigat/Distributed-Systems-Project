/*package Greenfield.RobotCLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketClientConnection implements  Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private gRPC_Server2 server;

    private boolean active = true;


    public SocketClientConnection(Socket socket, gRPC_Server2 server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }


    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    public synchronized void closeConnection() {
        send("Connection closed, another player left the match!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }


    public void run() {
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());


            while(isActive()){

                read = in.readObject();
                notify((String)read);
            }
        } catch (Exception e) {
            System.err.println("Error! " + e.getMessage());
        } finally{
            close();
        }

    }
}

*/