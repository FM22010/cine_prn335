package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
public class ProgramacionBean extends AbstractDataPersistence<Programacion> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public ProgramacionBean() {
        super(Programacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> buscarProgramaciones(String query, OffsetDateTime fecha) {
        // Aquí interactúas con el EntityManager o JPA para obtener los datos
        // Este es solo un ejemplo simple, adapta según tus necesidades

        // Crear la consulta JPQL
        String jpql = "SELECT p FROM Programacion p WHERE p.fecha = :fecha AND (LOWER(p.idPelicula.nombre) LIKE :query OR LOWER(p.idSala.nombre) LIKE :query OR LOWER(p.idSucursal.nombre) LIKE :query)";

        // Obtener la lista de programaciones
        List<Programacion> programaciones = em.createQuery(jpql, Programacion.class)
                .setParameter("fecha", fecha)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();

        return programaciones;
    }

    public List<Programacion> buscarProgramaciones(String query) {
        String jpql = "SELECT p FROM Programacion p " +
                "WHERE LOWER(p.idPelicula.nombre) LIKE :query " +
                "OR LOWER(p.idSala.nombre) LIKE :query " +
                "OR LOWER(p.idSucursal.nombre) LIKE :query";

        // Ejecutar la consulta con el término de búsqueda
        return em.createQuery(jpql, Programacion.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();
    }

    public List<Programacion> obtenerProgramaciones(TipoReserva tipoReserva, String diaSeleccionado) {
        // Aquí interactúas con el EntityManager o JPA para obtener los datos
        // Este es solo un ejemplo simple, adapta según tus necesidades

        // Crear la consulta JPQL
        String jpql = "SELECT p FROM Programacion p WHERE p.idTipoReserva = :tipoReserva AND p.fecha = :diaSeleccionado";

        // Obtener la lista de programaciones
        List<Programacion> programaciones = em.createQuery(jpql, Programacion.class)
                .setParameter("tipoReserva", tipoReserva)
                .setParameter("diaSeleccionado", diaSeleccionado)
                .getResultList();

        return programaciones;
    }

    public List<Pelicula> obtenerTodasLasPeliculas(String fecha) {
        // Consultamos la base de datos para obtener todas las películas
        String jpql = "SELECT p FROM Pelicula p";
        return em.createQuery(jpql, Pelicula.class).getResultList();
    }

    public List<Programacion> obtenerProgramaciones(String fecha) {
        try {
            // Asegúrate de usar el formato adecuado de fecha o adaptarlo según tu campo en la entidad Programacion
            String query = "SELECT p FROM Programacion p WHERE FUNCTION('DATE', p.desde) = :fecha";

            return em.createQuery(query, Programacion.class)
                    .setParameter("fecha", fecha)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void remove(Programacion programacion) {
        if (programacion != null && !em.contains(programacion)) {
            programacion = em.merge(programacion);
        }
        em.remove(programacion);
    }

    public List<Programacion> findProgramacionesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String jpql = "SELECT p FROM Programacion p WHERE p.desde >= :fechaInicio AND p.hasta <= :fechaFin";
        return em.createQuery(jpql, Programacion.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }




    public List<Programacion> findAll(int first, int max) {
        try{
            TypedQuery<Programacion> q = em.createNamedQuery("Programacion.findAll", Programacion.class);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
        return List.of();
    }

    public List<Programacion> findByDate(final OffsetDateTime desde, final OffsetDateTime hasta) {
        try {
            TypedQuery<Programacion> q = em.createNamedQuery("Programacion.findByDate", Programacion.class);
            q.setParameter("desde", desde);
            q.setParameter("hasta", hasta);
            return q.getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    public Map<Long, Programacion> getProgramacionesAsMap() {
        List<Programacion> programaciones = findAll(0,1000);
        Map<Long, Programacion> programacionesMap = new HashMap<>();

        for (Programacion programacion : programaciones) {
            programacionesMap.put(programacion.getIdProgramacion(), programacion);
        }

        return programacionesMap;
    }

    public List<Programacion> findByDate(String desde, String hasta) {
            try {
                TypedQuery<Programacion> q = em.createNamedQuery("Programacion.findByDate", Programacion.class);
                q.setParameter("desde", OffsetDateTime.parse(desde));
                q.setParameter("hasta", OffsetDateTime.parse(hasta));
                return q.getResultList();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
            }
            return List.of();
        }
}