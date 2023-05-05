package Greenfield.Services;


import Greenfield.Beans.Measure;
import Greenfield.Beans.Measures;
import Greenfield.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("measures")
public class MeasuresService {


    //private static Map<Integer,Robot> robots = new HashMap<Integer,Robot>();

    //restituisce la lista di tutti i robot presenti
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotsList(){
        return Response.ok(Robots.getInstance()).build();
    }


    //permette di prelevare un utente con un determinato nome
    @Path("get/{id}/{n}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getLastMeasuresById(@PathParam("id") int id, @PathParam("n") int n){
        ClientResponseData clientResponseData = new ClientResponseData(Measures.getInstance().getLastMeasuresById(id, n));
        if(clientResponseData.getMeasurements().size() != 0)
            return Response.ok(clientResponseData).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}