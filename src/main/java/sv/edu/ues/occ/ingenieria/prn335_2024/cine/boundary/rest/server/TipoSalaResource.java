package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("tiposala")
public class TipoSalaResource implements Serializable {

    @Inject
    TipoSalaBean tsBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("first")
            @DefaultValue("0")
            int firstResult,
            @QueryParam("max")
            @DefaultValue("50")
            int maxRresult){

        try {
           if(firstResult >=0 && maxRresult > 0 && maxRresult<= 50){
               List<TipoSala> encontrados =tsBean.findRange(firstResult, maxRresult);
               Long total= (long) tsBean.count();
               Response.ResponseBuilder builder=Response.ok(encontrados)
                       .header("Total-Records",total)
                       .type(MediaType.APPLICATION_JSON);
               return builder.build();
           }else {
               // 422: contenido no procesable
               // findById: 333 -> 404
               return  Response.status(422).header("Wrong-Parameter","first:"+firstResult+",max:"+maxRresult).build();
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
                TipoSala encontrado=tsBean.findById(id);
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
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(TipoSala tipoSala, @Context UriInfo uriInfo){
        if(tipoSala != null && tipoSala.getIdTipoSala()==null){
            try {
                tsBean.create(tipoSala );
                if(tipoSala.getIdTipoSala()!=null){
                    UriBuilder uriBuilder=uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(tipoSala.getIdTipoSala()));
                    return Response.created(uriBuilder.build()).build();
                }
                return  Response.status(500).header("Process-Error","Record couldt be created").build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return  Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter","tiposala:"+tipoSala).build();
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
            TipoSala tipoSala = tsBean.findById(id);
            if (tipoSala != null) {
                tsBean.getEntityManager().remove(tipoSala);
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
