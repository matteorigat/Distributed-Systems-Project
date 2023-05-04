package gRPC;

import it.ewlab.actor.ActorOuterClass.Actor;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket s = new Socket("localhost", 9999);

        Actor actor =
                Actor.newBuilder()
                        .setName("Christian")
                        .setSurname("Bale")
                        .setSex(Actor.Sex.MALE)
                        .addMovie(Actor.Movie.newBuilder()
                                .setTitle("The Prestige")
                                .setYear(2006))
                        .addMovie(Actor.Movie.newBuilder()
                                .setTitle("The Dark Knight")
                                .setYear(2008))
                        .build();

        actor.writeTo(s.getOutputStream());

        s.close();


    }
}
