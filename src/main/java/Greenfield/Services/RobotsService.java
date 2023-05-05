package Greenfield.Services;


import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("robots")
public class RobotsService {

    int[] numOfRobots;

    //private static Map<Integer,Robot> robots = new HashMap<Integer,Robot>();

    //restituisce la lista di tutti i robot presenti
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotsList(){
        return Response.ok(Robots.getInstance()).build();
    }

    //permette di inserire un nuovo robot
    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addRobot(Robot r){
        if(Robots.getInstance().add(r)){
            ResponseData responseData = new ResponseData(generatePosition(r), Robots.getInstance());
            return Response.ok(responseData).type("application/json").build();
        }
        else return Response.status(Response.Status.CONFLICT).build();
    }


    private Robot.Coordinates generatePosition(Robot r){
        if(numOfRobots == null){
            numOfRobots = new int[4];
            for(int i=0; i<4; i++)
                numOfRobots[i] = 0;
        }
        int min = numOfRobots[0];
        int max = -1;
        int district = 0;
        int x,y;

        for(int i=1; i<4; i++)
            if(min > numOfRobots[i]){
                min = numOfRobots[i];
                district = i;
            }

        switch (district){
            case 0: case 3:
                min = 0;
                max = 4;
                break;
            case 1: case 2:
                min = 5;
                max = 9;
                break;
            default: return null;
        }

        x = (int)(min + (Math.random()*(max - min + 1)));

        switch (district){
            case 0: case 1:
                min = 0;
                max = 4;
                break;
            case 2: case 3:
                min = 5;
                max = 9;
                break;
            default: return null;
        }

        y = (int)(Math.floor(Math.random() *(max - min + 1) + min));

        r.setPosition(x, y);
        return r.getPosition();
    }

}