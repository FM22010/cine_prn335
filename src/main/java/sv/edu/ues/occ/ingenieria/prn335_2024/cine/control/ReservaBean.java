package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;
import java.io.Serializable;

@LocalBean
@Stateless
public class ReservaBean extends AbstractDataPersistence<Reserva> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public ReservaBean() {
        super(Reserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
