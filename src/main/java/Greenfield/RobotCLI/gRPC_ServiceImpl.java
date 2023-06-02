package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;
import Greenfield.gRPCServiceGrpc.gRPCServiceImplBase;
import Greenfield.GRPCService.*;
import io.grpc.stub.StreamObserver;

public class gRPC_ServiceImpl extends gRPCServiceImplBase {

    private final Robot robot;
    private final CleaningRobotController robotController;

    public gRPC_ServiceImpl(Robot robot, CleaningRobotController robotController) {
        this.robot = robot;
        this.robotController = robotController;
    }

    @Override
    public void hello(HelloRequest request, StreamObserver<MechanicResponse> responseObserver){

        //unwrapping message
        int id = request.getId();
        int port = request.getPort();
        System.out.println("\nHello from " + id);

        Robot r = new Robot(id, port);
        r.setX(request.getX());
        r.setY(request.getY());
        Robots.getInstance().add(r);
        gRPC_Client grpcClient = new gRPC_Client(r, robotController);
        grpcClient.start();
        robotController.getClientRobotId().put(id, grpcClient);

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

        if(!robotController.isMechanic() && !robotController.WantMechanic()){
            OkResponse reply = OkResponse.newBuilder()
                    .setId(robot.getId())
                    .build();

            responseObserver.onNext(reply);
            System.out.println("ok sent to " + request.getId() + ", i dont't want mechanic");
        }
        else if (robotController.isMechanic()){
            robotController.getMechanicQueue().put(request.getId(), responseObserver);
            System.out.println(request.getId() + " added to queue");
        }
        else if(robotController.WantMechanic() && !robotController.isMechanic()){
            if(request.getTimestamp() < robotController.getMyTimestamp()){
                OkResponse reply = OkResponse.newBuilder()
                        .setId(robot.getId())
                        .build();

                responseObserver.onNext(reply);
                System.out.println("ok sent to " + request.getId() + ", i wait to go to mechanic");
            } else {
                robotController.getMechanicQueue().put(request.getId(), responseObserver);
                System.out.println(request.getId() + " added to queue, i'm first");
            }

        } else {
            System.out.println("mechanic something wrong in gRPC_ServiceImpl");
        }
    }

    @Override
    public void ok(OkResponse request, StreamObserver<OkResponse> responseObserver){

        if(!robotController.getMechanicOk().contains(request.getId()))
            robotController.getMechanicOk().add(request.getId());
        System.out.println("Received ok2 from: " + request.getId());


    }

    @Override
    public void quit(QuitRequest request, StreamObserver<QuitRequest> responseObserver){

        int id = request.getId();
        System.out.println("Quit from: " + id);
        robotController.getClientRobotId().remove(id);
        robotController.getMechanicOk().remove(id);
        robotController.getMechanicQueue().remove(id);
        Robots.getInstance().removeById(id);

    }


    @Override
    public StreamObserver<Alive> alive(StreamObserver<Alive> responseObserver){

        //it returns the stream that will be used by the clients to send messages. The client will write on this stream
        return new StreamObserver<Alive>() {
            public void onNext(Alive clientRequest) {
                System.out.println("onNext alive\n");
            }

            public void onError(Throwable throwable) {
                System.out.println("onError alive\n");
            }

            public void onCompleted() {
                System.out.println("onCompleted alive\n");
            }
        };
    }

}
