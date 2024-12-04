package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.Websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
@ApplicationScoped
public class SesionWeb implements Serializable {

    private final ConcurrentHashMap<String, Session> sesion=new ConcurrentHashMap<>();

    public void newSesion(Session sesions){
        if (sesions !=null){
            sesion.put(sesions.getId(), sesions);
        }
    }

    public void deleteSesion(Session sesions){
        if(sesions !=null){
            sesion.remove(sesions.getId());
        }
    }

    public Iterable<Session> getsesion(){
        return sesion.values();
    }

}
