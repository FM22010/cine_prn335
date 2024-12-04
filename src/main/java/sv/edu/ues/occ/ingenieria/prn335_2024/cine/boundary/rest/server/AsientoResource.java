package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.rest.server;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("asiento")
public class AsientoResource  implements Serializable {

    @Inject
    AsientoBean asientoBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("frist")
            @DefaultValue("0")
            int fristResult,
            @QueryParam("max")@DefaultValue("1000")
            int maxResult){
        try {
            if(fristResult >=0 && maxResult >0 && maxResult<=1000){
                List<Asiento> encontrados =asientoBean.findRange(fristResult, maxResult);
                Long total=(long) asientoBean.count();
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
                Asiento encontrado=asientoBean.findById(id);
                if(encontrado!=null){
                    Response.ResponseBuilder builder=Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found","id:"+id).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }else {
            return Response.status(422).header("Wrong-Parameter","id:"+id).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Asiento entity){
        if(entity!=null){
            try {
                Asiento creado=asientoBean.createEntity(entity);
                if(creado!=null){
                    Response.ResponseBuilder builder=Response.ok(creado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found","id:"+entity.getIdAsiento()).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }else {
            return Response.status(422).header("Wrong-Parameter","entity:"+entity).build();
        }
    }

    /**
     * obitone un asiento por el id de la pelicula
     */
    @GET
    @Path("/pelicula/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findByIdPelicula(@PathParam("id") Long id){
        if(id!=null){
            try {
                List<Asiento> encontrados=asientoBean.findByIdPelicula(id);
                if(encontrados!=null){
                    Response.ResponseBuilder builder=Response.ok(encontrados).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found","id:"+id).build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }else {
            return Response.status(422).header("Wrong-Parameter","id:"+id).build();
        }
    }



}
