package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("programacion")
public class ProgrmacionResource implements Serializable {
    @Inject
    ProgramacionBean prBean;

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
                List<Programacion> encontrados =prBean.findRange(fristResult, maxResult);
                Long total=(long) prBean.count();
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
    public Response findById(@PathParam("id") Long id){
        if(id!=null){
            try {
                Programacion encontrado=prBean.findById( id);
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
    public Response create(Programacion programacion, @Context UriInfo uriInfo){
        if(programacion != null && programacion.getIdProgramacion()==null){
            try {
                prBean.create(programacion);
                if(programacion.getIdProgramacion()!=null){
                    UriBuilder uriBuilder=uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(programacion.getIdProgramacion()));
                    return Response.created(uriBuilder.build()).build();
                }
                return  Response.status(500).header("Process-Error","Record couldt be created").build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return  Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter","tiposala:"+programacion).build();
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
            Programacion programacion = prBean.findById(id);
            if (programacion!= null) {
                prBean.getEntityManager().remove(programacion);
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


    /**
     * resive el desde y hasta y devuelve las programaciones que esten entre esas fechas
     */
    @GET
    @Path("/date")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findByDate(
            @QueryParam("desde")
            String desde,
            @QueryParam("hasta")
            String hasta){
        if(desde!=null && hasta!=null){
            try {
                List<Programacion> encontrados=prBean.findByDate(desde,hasta);
                if(encontrados!=null){
                    Response.ResponseBuilder builder=Response.ok(encontrados).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found","desde:"+desde+",hasta:"+hasta).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return  Response.status(422).header("Wrong-Parameter","desde:"+desde+",hasta:"+hasta).build();
    }




}
