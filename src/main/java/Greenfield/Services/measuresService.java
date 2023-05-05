package Greenfield.Services;


import Greenfield.Beans.Measures;
import Greenfield.Beans.Robot;
import Greenfield.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("measures")
public class measuresService {


    //private static Map<Integer,Robot> robots = new HashMap<Integer,Robot>();

    //restituisce la lista di tutti i robot presenti
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getMeasuresList(){
        return Response.ok(Measures.getInstance()).build();
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