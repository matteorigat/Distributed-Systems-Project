package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Simulator.Measurement;
import Greenfield.Simulator.SimulatorInterface;
import org.eclipse.paho.client.mqttv3.*;

public class MQTT_Client extends Thread{

    private MqttClient MQTTclient;
    private String broker = "tcp://localhost:1883";
    private String clientId = MqttClient.generateClientId();
    private String topic = "greenfield/pollution/district";
    private int qos = 2;

    private volatile boolean stopCondition = false;

    private int id;

    private SimulatorInterface sim;

    public MQTT_Client(SimulatorInterface sim, Robot robot) {
        this.sim = sim;
        this.id = robot.getId();
        this.topic += calculateDistrict(robot.getPosition().x, robot.getPosition().y);
    }

    public void stopMeGently() {
        stopCondition = true;
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

            while(!stopCondition) {

                payload = id + " " + averageCalculator() + " " + System.currentTimeMillis();
                message = new MqttMessage(payload.getBytes());

                // Set the QoS on the Message
                message.setQos(qos);
                //System.out.println(clientId + " Publishing message: " + payload + " ...");
                MQTTclient.publish(topic, message);
                //System.out.println(clientId + " Message published");

                sleep(15000);  // busy waiting
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

    private String calculateDistrict(int x, int y){
        if(x <= 4 && y <= 4)
            return "1";
        else if (x >= 5 && y <= 4)
            return "2";
        else if (x >= 5 && y >= 5)
            return "3";
        else if (x <= 4 && y >= 5)
            return "4";

        return null;
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
