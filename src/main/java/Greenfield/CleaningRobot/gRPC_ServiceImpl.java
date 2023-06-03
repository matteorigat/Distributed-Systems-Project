package Greenfield.CleaningRobot;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.gRPCServiceGrpc.gRPCServiceImplBase;
import Greenfield.GRPCService.*;
import io.grpc.stub.StreamObserver;

public class gRPC_ServiceImpl extends gRPCServiceImplBase {

    private final Robot robot;
    private final CleaningRobotController robotController;

    protected gRPC_ServiceImpl(Robot robot, CleaningRobotController robotController) {
        this.robot = robot;
        this.robotController = robotController;
    }

    @Override
    public void hello(HelloRequest request, StreamObserver<MechanicResponse> responseObserver){

        int id = request.getId();
        int port = request.getPort();
        System.out.println("\nHello from " + id);


        //adding the new robot to the network view of this.robot
        Robot r = new Robot(id, port);
        r.setX(request.getX());
        r.setY(request.getY());
        Robots.getInstance().add(r);
        gRPC_Client grpcClient = new gRPC_Client(r, robotController);
        grpcClient.start();
        synchronized (robotController.getClientRobotIdLock()){
            robotController.getClientRobotId().put(id, grpcClient);
        }

        //if this.robot wants the mechanic it sends the request to the new robot
        //an ok callback is expected
        if(robotController.WantMechanic()){
            MechanicResponse reply = MechanicResponse.newBuilder()
                    .setId(robot.getId())
                    .setTimestamp(robotController.getMyTimestamp())
                    .build();

            responseObserver.onNext(reply);
        }
    }

    @Override
    public void mechanic(MechanicRequest request, StreamObserver<OkResponse> responseObserver){

        //if this.robot doesn't need mechanic it replies with Ok message
        if(!robotController.isMechanic() && !robotController.WantMechanic()){
            OkResponse reply = OkResponse.newBuilder()
                    .setId(robot.getId())
                    .build();

            responseObserver.onNext(reply);
            System.out.println("ok sent to " + request.getId() + ", i dont't want mechanic");
        }
        //if this.robot is at the mechanic it queues the request
        else if (robotController.isMechanic()){
            synchronized (robotController.getMechanicQueueLock()){
                robotController.getMechanicQueue().put(request.getId(), responseObserver);
            }
            System.out.println(request.getId() + " added to queue");
        }
        else if(robotController.WantMechanic() && !robotController.isMechanic()){
            //if the requesting robot sent the request earlier, this.robot replies with ok
            if(request.getTimestamp() < robotController.getMyTimestamp()){
                OkResponse reply = OkResponse.newBuilder()
                        .setId(robot.getId())
                        .build();

                responseObserver.onNext(reply);
                System.out.println("ok sent to " + request.getId() + ", i wait to go to mechanic");
            }
            //else this.robot queues the request
            else {
                synchronized (robotController.getMechanicQueueLock()){
                    robotController.getMechanicQueue().put(request.getId(), responseObserver);
                }
                System.out.println(request.getId() + " added to queue, i'm first");
            }
        }
    }

    @Override
    public void ok(OkResponse request, StreamObserver<OkResponse> responseObserver){
        //if a robot is joining the network, it sends the ok response here
        synchronized (robotController.getMechanicOkLock()){
            robotController.getMechanicOk().add(request.getId());
            robotController.getMechanicOkLock().notify();
        }
        System.out.println("Received ok2 from: " + request.getId());

    }

    @Override
    public void quit(QuitRequest request, StreamObserver<QuitRequest> responseObserver){

        int id = request.getId();
        System.out.println("Quit from: " + id);
        Robots.getInstance().removeById(id);
        synchronized (robotController.getClientRobotIdLock()){
            robotController.getClientRobotId().remove(id);
        }
        synchronized (robotController.getMechanicQueueLock()){
            robotController.getMechanicQueue().remove(id);
        }
        synchronized (robotController.getMechanicOkLock()){
            robotController.getMechanicOk().remove(id);
            robotController.getMechanicOkLock().notify();
        }

    }


    @Override
    public StreamObserver<Alive> alive(StreamObserver<Alive> responseObserver){

        return new StreamObserver<Alive>() {
            public void onNext(Alive clientRequest) {
                //System.out.println("onNext alive\n");
            }

            public void onError(Throwable throwable) {
                //System.out.println("onError alive\n");
            }

            public void onCompleted() {
                //System.out.println("onCompleted alive\n");
            }
        };
    }

}
