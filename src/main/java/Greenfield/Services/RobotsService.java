package Greenfield.Services;


import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("robots")
public class RobotsService {

    //private static Map<Integer,Robot> robots = new HashMap<Integer,Robot>();

    //GET: The list of the cleaning robots currently located in Greenfield
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotsList(){
        return Response.ok(Robots.getInstance()).build();
    }

    //POST: request to be added to the city
    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addRobot(Robot r){
        if(Robots.getInstance().add(r)){
            RobotResponseData robotResponseData = new RobotResponseData(Robots.getInstance().generatePosition(r), Robots.getInstance());
            return Response.ok(robotResponseData).type("application/json").build();
        }
        else return Response.status(Response.Status.CONFLICT).build();
    }


    //DELETE: request to remove a cleaning robot from the Greenfield city
    @Path("{id}")
    @DELETE
    @Consumes({"application/json", "application/xml"})
    public Response deleteRobot(@PathParam("id") int id){
        if(Robots.getInstance().removeById(id)){
            return Response.ok().type("application/json").build();
        }
        else return Response.status(Response.Status.NOT_MODIFIED).build();
    }




}