package Greenfield.RobotCLI;

import Greenfield.Beans.Robot;
import Greenfield.gRPCServiceGrpc;
import Greenfield.gRPCServiceGrpc.*;
import Greenfield.GRPCService.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class gRPC_Client extends Thread{

    private Robot robot;

    public gRPC_Client(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        super.run();

        System.out.println("Trying calling streamGreeting asynchronous method:\n");

        try {
            asynchronousStreamCall();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n...Done!");

    }



    //calling an asynchronous method based on stream
    public void asynchronousStreamCall() throws InterruptedException {

        //plaintext channel on the address (ip/port) which offers the GreetingService service
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:" + robot.getPort()).usePlaintext().build();

        //creating an asynchronous stub on the channel
        gRPCServiceStub stub = gRPCServiceGrpc.newStub(channel);

        //creating the HelloResponse object which will be provided as input to the RPC method
        HelloRequest request = HelloRequest.newBuilder().setName("Pippo").build();

        //calling the RPC method. since it is asynchronous, we need to define handlers
        stub.hello(request, new StreamObserver<HelloResponse>() {

            //this hanlder takes care of each item received in the stream
            public void onNext(HelloResponse helloResponse) {

                //each item is just printed
                System.out.println(helloResponse.getGreeting());

            }

            //if there are some errors, this method will be called
            public void onError(Throwable throwable) {

                System.out.println("Error! "+throwable.getMessage());

            }

            //when the stream is completed (the server called "onCompleted") just close the channel
            public void onCompleted() {

                channel.shutdownNow();

            }

        });

        //you need this. otherwise the method will terminate before that answers from the server are received
        channel.awaitTermination(10, TimeUnit.SECONDS);


    }

}
