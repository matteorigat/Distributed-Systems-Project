package Greenfield.CleaningRobot;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.gRPCServiceGrpc;
import Greenfield.gRPCServiceGrpc.*;
import Greenfield.GRPCService.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class gRPC_Client extends Thread{

    private final String ip = "localhost";

    private final Robot robot;

    private final CleaningRobotController robotController;

    private ManagedChannel channel = null;
    private gRPCServiceStub stub = null;


    protected gRPC_Client(Robot robot, CleaningRobotController robotController) {
        this.robot = robot;
        this.robotController = robotController;
        robotController.getClientRobotId().put(robot.getId(), this);
    }


    @Override
    public void run() {
        super.run();

        //plaintext channel on the address (ip/port)
        channel = ManagedChannelBuilder.forTarget(ip + ":" + robot.getPort()).usePlaintext().build();

        //creating an asynchronous stub on the channel
        stub = gRPCServiceGrpc.newStub(channel);

        //Starting a continuous heartbeat service to detect when a robot is down
        try {
            asynchronousAlive();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    //asynchronous call to send a hello message to other bot
    protected void asynchronousHello(Robot r){

        //creating the Hello request which will be provided as input to the RPC method
        HelloRequest request = HelloRequest.newBuilder()
                .setId(r.getId())
                .setPort(r.getPort())
                .setX(r.getX())
                .setY(r.getY())
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
        stub.hello(request, new StreamObserver<MechanicResponse>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(MechanicResponse response) {
                if(!robotController.isMechanic() && !robotController.WantMechanic()){
                    try {
                        asynchronousOk(r.getId());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("mechanic callback from: " + response.getId() + " ok sent");
                }
                else {
                    System.out.println("mechanic something wrong in gRPC_Client");
                }
            }

            // error already handled in alive onError
            public void onError(Throwable throwable) {
                //System.out.println("onError callback Hello "+throwable.getMessage());
            }

            // already handled in alive onCompleted
            public void onCompleted() {
                //System.out.println("onCompleted callback Hello");

            }
        });
    }


    protected void asynchronousMechanic(Robot r){

        //creating the Mechanic request which will be provided as input to the RPC method
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        MechanicRequest request = MechanicRequest.newBuilder()
                .setId(r.getId())
                .setTimestamp(time)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
        stub.mechanic(request, new StreamObserver<OkResponse>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(OkResponse response) {
                robotController.getMechanicOk().add(response.getId());
                System.out.println("Received ok from: " + response.getId());
            }

            // error already handled in alive onError
            public void onError(Throwable throwable) {
                //System.out.println("onError callback Mechanic " + throwable.getMessage());

            }

            // already handled in alive onCompleted
            public void onCompleted() {
                //System.out.println("onCompleted callback Mechanic");
            }
        });
    }


    private void asynchronousOk(int id) throws InterruptedException {

        //creating the Ok response which will be provided as input to the RPC method
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        OkResponse request = OkResponse.newBuilder()
                .setId(id)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
        stub.ok(request, new StreamObserver<OkResponse>() {

            // not used here
            public void onNext(OkResponse response) {
                //System.out.println("Callback ok");
            }

            // error already handled in alive onError
            public void onError(Throwable throwable) {
                //System.out.println("onError callback ok " + throwable.getMessage());
            }

            // already handled in alive onCompleted
            public void onCompleted() {
                //System.out.println("onCompleted callback ok");
            }
        });
    }

    protected void asynchronousQuit(int id){

        //creating the HelloResponse object which will be provided as input to the RPC method
        QuitRequest request = QuitRequest.newBuilder()
                .setId(id)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
        stub.quit(request, new StreamObserver<QuitRequest>() {

            // not used here
            public void onNext(QuitRequest response) {
                //System.out.println("Callback ok");
            }

            // error already handled in alive onError
            public void onError(Throwable throwable) {
                //System.out.println("onError callback ok " + throwable.getMessage());
            }

            public void onCompleted() {
                channel.shutdownNow();
                //System.out.println("onCompleted callback ok");
            }
        });
    }



    private void asynchronousAlive() throws InterruptedException {

        //calling the RPC method. since it is asynchronous, we need to define handlers
        //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.
        stub.alive(new StreamObserver<Alive>() {

            // not used here
            public void onNext(Alive alive) {
                //System.out.println("onNext alive callback");
            }

            public void onError(Throwable throwable) {
                int id = robot.getId();
                System.out.println("onError alive callback " + id);
                robotController.deleteRobotFromServer(id);// only one robot is successful
                robotController.getClientRobotId().remove(id);
                robotController.getMechanicOk().remove(id);
                robotController.getMechanicQueue().remove(id);
                Robots.getInstance().removeById(id);
            }

            public void onCompleted() {
                int id = robot.getId();
                System.out.println("onCompleted alive callback " + id);
                robotController.getClientRobotId().remove(id);
                robotController.getMechanicOk().remove(id);
                robotController.getMechanicQueue().remove(id);
                Robots.getInstance().removeById(id);

                channel.shutdownNow();
            }
        });
    }

    public boolean isStubNull() {
        return stub == null;
    }
}
