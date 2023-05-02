package REST.services;


import REST.Beans.Robot;
import REST.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("robots")
public class RobotsService {

    int[] numOfRobots;

    //private static Map<Integer,Robot> robots = new HashMap<Integer,Robot>();

    //restituisce la lista di tutti i robot presenti
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotsList(){
        for (Robot r : Robots.getInstance().getRobotslist()){
            System.out.println("Id: " + r.getId() + " Port: " + r.getPort());
        }
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

        x = (int)(Math.floor(Math.random() *(max - min + 1) + min));

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




/*

    //permette di prelevare un utente con un determinato nome
    @Path("get/{name}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getByName(@PathParam("name") int id){
        Robot r = Robots.getInstance().getById(id);
        if(r!=null)
            return Response.ok(r).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
*/
}