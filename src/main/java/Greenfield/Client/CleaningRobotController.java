package Greenfield.Client;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.Services.ResponseData;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CleaningRobotController {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;

        // POST add request to the city
        String postPath = "/robots/add";
        Robot robot = new Robot(1234,8888);
        clientResponse = postRequest(client,serverAddress+postPath,robot);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            ResponseData resp = clientResponse.getEntity(ResponseData.class);
            robot.setPosition(resp.getPosition().x, resp.getPosition().y);
            System.out.println("position: " + robot.getPosition().x + "," + robot.getPosition().y);
            System.out.println("Robots List: num of elem... " + resp.getRobots().getRobotslist().size());
            for (Robot r : resp.getRobots().getRobotslist()){
                System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
            }
        } else {
            System.out.println("POST request failed.");
        }

        //GET the list of all the robots in the city
        String getPath = "/robots";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        if(clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            Robots robots = clientResponse.getEntity(Robots.class);
            System.out.println("Robots List");
            for (Robot r : robots.getRobotslist()){
                System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
            }
        } else {
            System.out.println("GET request failed.");
        }




        MqttClient MQTTclient;
        String broker = "tcp://localhost:1883";
        String clientId = MqttClient.generateClientId();
        String topic = "greenfield/pollution/district1";
        int qos = 2;

        //brew services start mosquitto

        try {
            MQTTclient = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            //connOpts.setUserName(username); // optional
            //connOpts.setPassword(password.toCharArray()); // optional
            //connOpts.setWill("this/is/a/topic","will message".getBytes(),1,false);  // optional
            //connOpts.setKeepAliveInterval(60);  // optional

            // Connect the client
            System.out.println(clientId + " Connecting Broker " + broker);
            MQTTclient.connect(connOpts);
            System.out.println(clientId + " Connected");

            String payload = String.valueOf(0 + (Math.random() * 10)); // create a random number between 0 and 10
            MqttMessage message = new MqttMessage(payload.getBytes());

            // Set the QoS on the Message
            message.setQos(qos);
            System.out.println(clientId + " Publishing message: " + payload + " ...");
            MQTTclient.publish(topic, message);
            System.out.println(clientId + " Message published");

            if (MQTTclient.isConnected())
                MQTTclient.disconnect();
            System.out.println("Publisher " + clientId + " disconnected");



        } catch (MqttException me ) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }






    }

    public static ClientResponse postRequest(Client client, String url, Robot r){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(r);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    public static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }


}
