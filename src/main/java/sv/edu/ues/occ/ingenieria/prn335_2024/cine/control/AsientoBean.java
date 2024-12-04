package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.Websocket.ReservaWebEnpoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AsientoBean extends AbstractDataPersistence<Asiento> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public AsientoBean() {
        super(Asiento.class);
    }

    @Inject
    private ReservaWebEnpoint reservaWebEnpoint;
    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    public int countSala(final int idSala){
        try {
            TypedQuery<Long> q = em.createNamedQuery("Asiento.countByIdSala", Long.class);
            q.setParameter("idSala", idSala);
            return q.getSingleResult().intValue();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public List<Asiento> findByIdSala(final int idSala, int first, int max){

        try {
            TypedQuery<Asiento> q = em.createNamedQuery("Asiento.findByIdSala", Asiento.class);
            q.setParameter("idSala", idSala);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    /**
     * Obtiene la lista de asientos disponibles para reservar.
     * Un asiento es considerado disponible si está activo y no tiene una reserva asociada.
     *
     * @return Lista de asientos disponibles
     */
    public List<Asiento> obtenerAsientosDisponibles() {
        TypedQuery<Asiento> query = em.createQuery(
                "SELECT a FROM Asiento a WHERE a.activo = true AND NOT EXISTS " +
                        "(SELECT r FROM Reserva r WHERE r.asiento = a)", Asiento.class);
        return query.getResultList();
    }



    private List<Asiento> asientos;
    private Asiento asientoSeleccionado;


    public void ocuparAsientos(){
        if(asientoSeleccionado !=null){
            try {
                asientoSeleccionado.setActivo(true);
                em.merge(asientoSeleccionado);

                //Generar mensaje
                String mensaje ="El asiento"+ asientoSeleccionado.getNombre()+" ha sido ocupado";

                reservaWebEnpoint.enviarMensajeNotificacion(mensaje);

                Logger.getLogger(getClass().getName()).log(Level.INFO, "Asiento ocupado: {0}",
                        asientoSeleccionado.getNombre());
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Error al ocupar el asiento",e);
            }
        }else {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "No se ha selecionado un asiento para ocupar");
        }
    }

    public Asiento createEntity(Asiento entity) {
            if (entity != null) {
                try {
                    em.persist(entity);
                    return entity;
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
            return null;

    }

    public List<Asiento> findByIdPelicula(Long id) {
            return null;
    }
}
