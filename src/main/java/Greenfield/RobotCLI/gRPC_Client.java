package Greenfield.RobotCLI;

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

    private CleaningRobotController robotController;

    private ManagedChannel channel;
    private gRPCServiceStub stub;

    private volatile boolean stopCondition = false;

    public gRPC_Client(Robot robot, CleaningRobotController robotController) {
        this.robot = robot;
        this.robotController = robotController;
        robotController.getClientRobotId().put(robot.getId(), this);
    }

    protected void stopMeGently() {
        stopCondition = true;
    }

    @Override
    public void run() {
        super.run();

        //plaintext channel on the address (ip/port) which offers the GreetingService service
        channel = ManagedChannelBuilder.forTarget(ip + ":" + robot.getPort()).usePlaintext().build();

        //creating an asynchronous stub on the channel
        stub = gRPCServiceGrpc.newStub(channel);

        Thread t0;
        try {
            t0 = asynchronousAlive();
            t0.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    //calling an asynchronous method based on stream
    protected void asynchronousHello(Robot r) throws InterruptedException {

        //creating the HelloResponse object which will be provided as input to the RPC method
        HelloRequest request = HelloRequest.newBuilder()
                .setId(r.getId())
                .setPort(r.getPort())
                .setX(r.getX())
                .setY(r.getY())
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
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

            //if there are some errors, this method will be called
            public void onError(Throwable throwable) {
                //System.out.println("onError callback Hello "+throwable.getMessage());
            }

            //when the stream is completed (the server called "onCompleted") just close the channel
            public void onCompleted() {
                //System.out.println("onCompleted callback Hello");
            }
        });
    }


    protected void asynchronousMechanic(Robot r) throws InterruptedException {

        //creating the HelloResponse object which will be provided as input to the RPC method
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        MechanicRequest request = MechanicRequest.newBuilder()
                .setId(r.getId())
                .setTimestamp(time)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        stub.mechanic(request, new StreamObserver<OkResponse>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(OkResponse response) {
                if(!robotController.getMechanicOk().contains(response.getId()))
                    robotController.getMechanicOk().add(response.getId());
                System.out.println("Received ok from: " + response.getId());
            }

            //if there are some errors, this method will be called
            public void onError(Throwable throwable) {
                //System.out.println("onError callback Mechanic " + throwable.getMessage());
            }

            //when the stream is completed (the server called "onCompleted") just close the channel
            public void onCompleted() {
                //System.out.println("onCompleted callback Mechanic");
            }
        });
    }


    protected void asynchronousOk(int id) throws InterruptedException {

        //creating the HelloResponse object which will be provided as input to the RPC method
        long time = System.currentTimeMillis();
        robotController.setMyTimestamp(time);
        OkResponse request = OkResponse.newBuilder()
                .setId(id)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        stub.ok(request, new StreamObserver<OkResponse>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(OkResponse response) {
                //System.out.println("Callback ok");
            }

            //if there are some errors, this method will be called
            public void onError(Throwable throwable) {
                //System.out.println("onError callback ok " + throwable.getMessage());
            }

            //when the stream is completed (the server called "onCompleted") just close the channel
            public void onCompleted() {
                //System.out.println("onCompleted callback ok");
            }
        });
    }

    protected void asynchronousQuit(int id) throws InterruptedException {

        //creating the HelloResponse object which will be provided as input to the RPC method
        QuitRequest request = QuitRequest.newBuilder()
                .setId(id)
                .build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        stub.quit(request, new StreamObserver<QuitRequest>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(QuitRequest response) {
                //System.out.println("Callback ok");
            }

            //if there are some errors, this method will be called
            public void onError(Throwable throwable) {
                //System.out.println("onError callback ok " + throwable.getMessage());
            }

            //when the stream is completed (the server called "onCompleted") just close the channel
            public void onCompleted() {
                channel.shutdownNow();
                //System.out.println("onCompleted callback ok");
            }
        });
    }



    public Thread asynchronousAlive() throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                //calling the RPC method. since it is asynchronous, we need to define handlers
                StreamObserver<Alive> serverStream = stub.alive(new StreamObserver<Alive>() {
                    //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.

                    //we define what to do when a message from the server arrives (just print the message)
                    public void onNext(Alive alive) {
                        System.out.println("onNext alive callback");
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
        });
        t.start();
        return t;
    }

}
