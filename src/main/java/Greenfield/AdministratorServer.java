package Greenfield;

import Greenfield.Beans.Measure;
import Greenfield.Beans.Measures;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdministratorServer {

    private static final String HOST = "localhost";
    private static final int PORT = 1337;


    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://"+HOST+":"+PORT+"/");
        server.start();
        System.out.println("Server running!");
        System.out.println("Server started on: http://"+HOST+":"+PORT);



        MqttClient client;
        String broker = "tcp://localhost:1883";
        String clientId = MqttClient.generateClientId();
        String topic = "greenfield/pollution/+";
        int qos = 2;

        try {
            client = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Connect the client
            System.out.println(clientId + " Connecting Broker " + broker);
            client.connect(connOpts);
            System.out.println(clientId + " Connected - Thread PID: " + Thread.currentThread().getId());

            // Callback
            client.setCallback(new MqttCallback() {

                // Called when a message arrives from the server that matches any subscription made by the client
                public void messageArrived(String topic, MqttMessage message) {

                    String[] receivedMessage = new String(message.getPayload()).split("§");
                    List<Double> values = new ArrayList<>();
                    String numbersString = receivedMessage[1].substring(1, receivedMessage[1].length() - 1); // Remove [ ]
                    for(String s : numbersString.split(", "))
                        values.add(Double.parseDouble(s));

                    Measure measure = new Measure(Integer.parseInt(receivedMessage[0]), values, Long.parseLong(receivedMessage[2]));
                    Measures.getInstance().add(measure);


                    System.out.println(//clientId +" Received a Message! - Callback - Thread PID: " + Thread.currentThread().getId() +
                            "\n\tTopic:   " + topic +
                            "\n\tRobot id:   " + receivedMessage[0] +
                            "\n\tAverages:   " + numbersString +
                            "\n\tTimestamp:   " + receivedMessage[2]  +
                            //"\n\tQoS:     " + message.getQos() +
                            "\n");

                    //System.out.println("\n ***  Press a random key to exit *** \n");

                }

                public void connectionLost(Throwable cause) {
                    System.out.println(clientId + " Connectionlost! cause:" + cause.getMessage()+ "-  Thread PID: " + Thread.currentThread().getId());
                    System.out.println(cause.getCause() + "\n" + cause.toString() + "\n" + cause.getLocalizedMessage());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used here
                }

            });
            System.out.println(clientId + " Subscribing ... - Thread PID: " + Thread.currentThread().getId());
            client.subscribe(topic,qos);
            System.out.println(clientId + " Subscribed to topics : " + topic);


            System.out.println("\n ***  Press a random key to exit *** \n");
            Scanner command = new Scanner(System.in);
            command.nextLine();
            client.disconnect();

        } catch (MqttException me ) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }


        /*
        System.out.println("Hit return to stop...");
        System.in.read();
        */
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
        System.exit(0);
    }
}
