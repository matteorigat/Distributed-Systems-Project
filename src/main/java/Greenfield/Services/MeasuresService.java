package Greenfield.Services;


import Greenfield.Beans.Measure;
import Greenfield.Beans.Measures;
import Greenfield.Beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("measures")
public class MeasuresService {



    // The average of the last n air pollution levels sent to the server by a given robot
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


    // The average of the air pollution levels sent by all the robots to the server
    // and occurred from timestamps t1 and t2
    @Path("timeget/{t1}/{t2}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getMeasurest1t2(@PathParam("t1") long t1, @PathParam("t2") long t2){
        ClientResponseData clientResponseData = new ClientResponseData(Measures.getInstance().getMeasurest1t2(t1, t2));
        if(clientResponseData.getMeasurements().size() != 0)
            return Response.ok(clientResponseData).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}