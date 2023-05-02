package REST.services;


import REST.Beans.Robot;
import REST.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("robots")
public class RobotsService {

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
        Robots.getInstance().add(r);
        return Response.ok().build();
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