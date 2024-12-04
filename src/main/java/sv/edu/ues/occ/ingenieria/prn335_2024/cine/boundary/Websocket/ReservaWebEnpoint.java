package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.Websocket;

import jakarta.inject.Inject;
import jakarta.websocket.OnOpen;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/socketreserva")
public class ReservaWebEnpoint implements Serializable {

    @Inject
    SesionWeb sesionWeb;

    private static final Logger LOGGER=Logger.getLogger(ReservaWebEnpoint.class.getName());

    public void inicializarSesion(Session session) {
        if (session != null) {
            sesionWeb.newSesion(session);
        } else {
            LOGGER.warning("Se produjo un error al iniciar la sesión.");
        }
    }



    @OnOpen
    public void open(Session session) {
        inicializarSesion(session);
    }


    public void generarMensajeProgramable(Session sec, String mensaje) {
        if (sec != null && sec.isOpen()) {
            try {
                RemoteEndpoint.Basic remote = sec.getBasicRemote();
                if (remote != null) {
                    // Enviar el mensaje
                    remote.sendText(mensaje);
                } else {
                    System.out.println("Error: RemoteEndpoint no disponible.");
                }
            } catch (IOException e) {
                System.out.println("Error al enviar el mensaje: " + e.getMessage());
            }
        } else {
            System.out.println("La sesión no está abierta o es nula.");
        }
    }

    @OnOpen
    public void close(Session sesions){
        if(sesions !=null){
            sesionWeb.deleteSesion(sesions);
            LOGGER.info("Se ha cerrado la secion: "+sesions.getId());
        }
    }


    public void enviarMensajeNotificacion(String mensaje) {
        for (Session sesion : sesionWeb.getsesion()) {
            if (sesion.isOpen()) {
                try {
                    RemoteEndpoint.Basic remote = sesion.getBasicRemote();
                    if (remote != null) {
                        remote.sendText(mensaje);
                    } else {
                        Logger.getLogger(getClass().getName()).log(Level.WARNING,
                                "La sesión no tiene un RemoteEndpoint.Basic válido.");
                    }
                } catch (IOException e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al enviar mensaje", e);
                }
            }
        }
    }
}