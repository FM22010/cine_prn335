package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reserva")
public class ReservaResource implements Serializable {
    @Inject
    ReservaBean rBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("frist")
            @DefaultValue("0")
            int fristResult,
            @QueryParam("max")@DefaultValue("20")
            int maxResult){
        try {
            if(fristResult >=0 && maxResult >0 && maxResult<=20){
                List<Reserva> encontrados =rBean.findRange(fristResult, maxResult);
                Long total=(long) rBean.count();
                Response.ResponseBuilder builder=Response.ok(encontrados)
                        .header("Total-Records",total)
                        .type(MediaType.APPLICATION_JSON);
                return  builder.build();
            }else {
                // 422: contenido no procesable
                // findById: 333 -> 404
                return  Response.status(422).header("Wrong-Parameter","first:"+fristResult+",max:"+maxResult).build();
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Integer id){
        if(id!=null){
            try {
                Reserva encontrado=rBean.findById(id);
                if(encontrado!=null){
                    Response.ResponseBuilder builder=Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found","id:"+id).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return  Response.status(422).header("Wrong-Parameter","id:"+id).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Reserva reserva) {
        if (reserva == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El recurso no puede ser nulo.")
                    .build();  // Código 400
        }

        try {
            reserva.setFechaReserva(java.time.OffsetDateTime.now());
            rBean.create(reserva);
            return Response.status(Response.Status.CREATED)
                    .entity(reserva)
                    .build();  // Código 201
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();  // Código 500
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El id no puede ser nulo.")
                    .build();  // Código 400
        }

        try {
            Reserva reserva = rBean.findById(id);
            if (reserva!= null) {
                rBean.getEntityManager().remove(reserva);
                return Response.noContent().build();  // Código 204
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el recurso con id: " + id)
                        .build();  // Código 404
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno: " + e.getMessage())
                    .build();  // Código 500
        }
    }

}
