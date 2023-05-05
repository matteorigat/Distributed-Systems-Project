package Greenfield.Client;

import Greenfield.Simulator.Measurement;
import Greenfield.Simulator.SimulatorInterface;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT_Client extends Thread{

    MqttClient MQTTclient;
    String broker = "tcp://localhost:1883";
    String clientId = MqttClient.generateClientId();
    String topic = "greenfield/pollution/district1";
    int qos = 2;

    int id;

    SimulatorInterface sim;

    public MQTT_Client(SimulatorInterface sim, int id) {
        this.sim = sim;
        this.id = id;
    }


    @Override
    public void run() {
        super.run();

        try {
            MQTTclient = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Connect the client
            //System.out.println("\n" + clientId + " Connecting Broker " + broker);
            MQTTclient.connect(connOpts);
            //System.out.println(clientId + " Connected\n");

            String payload = null;
            MqttMessage message = null;

            while(MQTTclient.isConnected()) {

                payload = id + " " + averageCalculator() + " " + System.currentTimeMillis();
                message = new MqttMessage(payload.getBytes());

                // Set the QoS on the Message
                message.setQos(qos);
                //System.out.println(clientId + " Publishing message: " + payload + " ...");
                MQTTclient.publish(topic, message);
                //System.out.println(clientId + " Message published");

                sleep(15000);
            }

            MQTTclient.disconnect();
            System.out.println("\nPublisher " + clientId + " disconnected");

        }
        catch (MqttException me ) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    private double averageCalculator(){
        double average = 0;
        int i=0;

        for(Measurement m : sim.readAllAndClean()){
            average += m.getValue();
            i++;
        }

        return average/i;
    }
}
