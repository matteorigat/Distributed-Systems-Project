package Greenfield.RobotCLI;

import com.example.chat.ChatServiceGrpc.*;
import com.example.chat.ChatServiceOuterClass.*;
import io.grpc.stub.StreamObserver;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class ChatServiceImpl extends ChatServiceImplBase {

    //an hashset to store all the streams which the server uses to communicate with each client
    HashSet<StreamObserver> observers = new LinkedHashSet<StreamObserver>();

    @Override public StreamObserver<ChatMessage> chat(final StreamObserver<ChatMessage> responseObserver){

        //the stream used to communicate with a specific client is stored in a hash set (avoiding duplicates)
        synchronized (observers) {

            observers.add(responseObserver);

        }
        //it returns the stream that will be used by the clients to send messages.
        //the client will write on this stream
        return new StreamObserver<ChatMessage>() {

            //receiving a message from a specific client
            public void onNext(ChatMessage chatMessage) {

                //unwrapping message
                String message = chatMessage.getMessage();
                String from = chatMessage.getFrom();

                System.out.println("[MESSAGE RECEIVED] Received a message from "+from+": "+message);


                HashSet<StreamObserver> copy;

                synchronized (observers) {

                    copy = new HashSet<>(observers);

                }

                //iterating on all the streams to communicate with all the clients
                for(StreamObserver<ChatMessage> observer: copy){

                    //we exclude the one which is sending the message
                    if(!observer.equals(responseObserver))
                        //we simply forward the message
                        try {

                            observer.onNext(ChatMessage.newBuilder().setMessage(message).setFrom(from).build());

                        }
                        catch(io.grpc.StatusRuntimeException e){
                            //peer no longer available
                            synchronized (observers){

                                observers.remove(observer);

                            }
                        }
                }

            }

            //if there is an error (client abruptly disconnect) we remove the client.
            public void onError(Throwable throwable) {

                synchronized (observers) {

                    observers.remove(responseObserver);

                }
            }

            //if the client explicitly terminated, we remove it from the hashset.
            public void onCompleted() {
                synchronized (observers) {

                    observers.remove(responseObserver);

                }
            }
        };
    }


}
