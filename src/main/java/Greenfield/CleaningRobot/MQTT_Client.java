package Greenfield.CleaningRobot;

import Greenfield.Beans.Robot;
import Greenfield.Simulator.SimulatorInterface;
import org.eclipse.paho.client.mqttv3.*;


public class MQTT_Client extends Thread{

    private MqttClient MQTTclient;
    private final String broker = "tcp://localhost:1883";
    private final String clientId = MqttClient.generateClientId();
    private String topic = "greenfield/pollution/district";
    private final int qos = 2;

    private volatile boolean stopCondition = false;

    private final int id;

    private final SimulatorInterface sim;

    protected MQTT_Client(SimulatorInterface sim, Robot robot) {
        this.sim = sim;
        this.id = robot.getId();
        this.topic += calculateDistrict(robot.getX(), robot.getY());
    }

    protected void stopMeGently() {
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

            AsyncReadFromSimulator arfs = new AsyncReadFromSimulator(sim);
            arfs.start();

            String payload = null;
            MqttMessage message = null;

            while(!stopCondition) {
                if(!arfs.isEmptyList()){
                    payload = id  + "§" + arfs.getListOfMeasurementsAndClean() + "§" + System.currentTimeMillis();
                    message = new MqttMessage(payload.getBytes());

                    // Set the QoS on the Message
                    message.setQos(qos);
                    //System.out.println(clientId + " Publishing message: " + payload + " ...");
                    MQTTclient.publish(topic, message);
                    //System.out.println(clientId + " Message published");
                }

                synchronized (this){
                    this.wait(15000);
                }
            }
            arfs.stopMeGently();
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
        else if (x >= 5)
            return "3";
        else
            return "4";
    }

}
