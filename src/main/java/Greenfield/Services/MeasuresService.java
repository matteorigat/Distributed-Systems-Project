package Greenfield.Services;


import Greenfield.Beans.Measures;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("measures")
public class MeasuresService {



    // The average of the last n air pollution levels sent to the server by a given robot
    @Path("get/{id}/{n}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getLastMeasuresById(@PathParam("id") int id, @PathParam("n") int n){
        double r = Measures.getInstance().getAverageLastNById(id, n);
        if(!Double.isNaN(r))
            return Response.ok(String.valueOf(r)).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }


    // The average of the air pollution levels sent by all the robots to the server
    // and occurred from timestamps t1 and t2
    @Path("timeget/{t1}/{t2}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getMeasurest1t2(@PathParam("t1") long t1, @PathParam("t2") long t2){
        double r = Measures.getInstance().getAveraget1t2(t1, t2);
        if(!Double.isNaN(r))
            return Response.ok(String.valueOf(r)).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}