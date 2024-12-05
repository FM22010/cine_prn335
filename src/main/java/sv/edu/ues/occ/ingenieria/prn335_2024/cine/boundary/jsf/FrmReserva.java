package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Named("frmReserva")
@ViewScoped
public class FrmReserva extends AbstractfrmImplementacion<Reserva> implements Serializable{

    @PersistenceContext
    private EntityManager em;

    private FacesContext facesContext=FacesContext.getCurrentInstance();

    private int pasoActual=1;
    private int paso=1;
    private TipoReserva tipoReservaSelecionado;

    private String diaSleccionado;
    private Pelicula peliculaSeleccionada;
    private Programacion programacionSelecionada;
    private String detallesPelicula;
    private List<Asiento> asientosDisponible;
    private List<Asiento> asientosReservado;

    private Asiento asientoSelecionado;
    private String comentarios;


    private List<Asiento> asientosDisponibles;
    private List<Asiento> asientosRerservados;
    private List<TipoReserva> listaTipoReserva;
    private List<Programacion> listaProgramaciones;
    private Pelicula peliculaSeleccionadaDetalle;
    private Reserva reservaConfirmada;
    private OffsetDateTime fechaSeleccionada;


    private List<Programacion> programacionesDisponible;
    private boolean mostrarDetallePelicula = false;

    private List<Long> asientosReservadosIds;

    @Inject
    private TipoReservaBean tipoReservaBean;

    @Inject
    private ReservaBean reservaBean;

    @Inject
    private AsientoBean asientoBean;

    @EJB
    private ProgramacionBean programacionBean;

    private List<Asiento> asientos;
    private List<Pelicula> peliculas;

    @Override
    public FacesContext getFacesContext(){
        return facesContext;
    }
    @Override
    public String getIdPorObjeto(Reserva reserva){
        return  reserva !=null ? reserva.getIdReserva().toString():null;
    }

    @Override
    public Reserva getObjetoPorId(String id){
        ReservaBean reservaBean=getDataAccess();
        return reservaBean.findReservaById(Long.parseLong(id));
    }

    @Override
    public ReservaBean getDataAccess(){
        return this.reservaBean;
    }

    @Override
    public void instanciarRegistro(){
        this.setRegistro(new Reserva());
    }

    public FrmReserva() {
        this.reservaBean=new ReservaBean();
        this.asientosDisponibles = new ArrayList<>();
        this.asientosReservado = new ArrayList<>();
        cargarAsientos();
    }



    // Método para obtener la lista de tipos de reserva
    public List<TipoReserva> getListaTipoReserva(){
        if (listaTipoReserva==null){
                listaTipoReserva=tipoReservaBean.obtenerListaTiposReserva();
        }
        return listaTipoReserva;
    }

    // En el bean o servicio TipoReservaBean
    public List<TipoReserva> obtenerListaTiposReserva() {
        return em.createQuery("SELECT t FROM TipoReserva t", TipoReserva.class)
                .getResultList();
    }
    // Método para obtener programaciones en función de la fecha seleccionada
    private List<Programacion> obtenerProgramacionPorFecha(String fecha) {
        // Aquí deberías implementar la lógica de consulta a la base de datos
        // para obtener todas las programaciones en la fecha seleccionada
        return programacionBean.obtenerProgramaciones(fecha); // Ejemplo
    }


    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    public void avanzarPaso() {
        if (paso< 3) {
            paso ++;
        }
    }

    public void irAPestanaAnterior() {
        if (paso > 0) {
            paso--; // Cambia a la pestaña anterior
        }
    }



    // Método para avanzar al siguiente paso
    public void retrocederPaso(){
        pasoActual--;
    }


    public List<Asiento> obtenerAsientosDisponibles(Programacion programacionSeleccionada) {
        if (programacionSeleccionada != null) {
            // Accedemos a la sala de la programación seleccionada
            Sala sala = programacionSeleccionada.getIdSala();

            // Obtenemos los asientos disponibles de la sala
            String query = "SELECT a FROM Asiento a WHERE a.sala = :sala AND a.reservado = false AND a.activo = true";
            return em.createQuery(query, Asiento.class)
                    .setParameter("sala", sala)
                    .getResultList();
        }
        return new ArrayList<>();
    }

    public void cargarAsientos() {
        try {
            asientosDisponibles = asientoBean.obtenerAsientosDisponibles();
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al capturar los asientos",e.getMessage());
            facesContext.addMessage(null,message);
        }
    }

    private void reservarAsiento(Asiento asiento){
        if (asiento !=null){
            asientosDisponibles.remove(asiento);
            asientosRerservados.add(asiento);
        }
    }

    public void  eliminarAsientosReservados(Asiento asiento){
        if (asiento !=null){
            asientosRerservados.remove(asiento);
            asientosDisponibles.add(asiento);
        }
    }


    public void generarResumen() {
        // Crear un resumen de la reserva, con los detalles de la función, película, asientos reservados, etc.
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen de la Reserva:\n")
                .append("Tipo de Reserva: ").append(getRegistro().getIdTipoReserva().getNombre()).append("\n")
                .append("Fecha: ").append(getRegistro().getFechaReserva()).append("\n")
                .append("Película: ").append(programacionSelecionada.getIdPelicula().getNombre()).append("\n")
                .append("Sala: ").append(programacionSelecionada.getIdSala().getNombre()).append("\n")
                .append("Horario: ").append(programacionSelecionada.getDesde()).append(" - ")
                .append(programacionSelecionada.getHasta()).append("\n")
                .append("Asientos Reservados:\n");

        // Listar los asientos reservados en el resumen
        for (Asiento asiento : asientosRerservados) {
            resumen.append(" - Asiento: ").append(asiento.getNombre()).append("\n");
        }

        // Mostrar el resumen en la interfaz de usuario, por ejemplo, como un mensaje de información en la pantalla
        FacesMessage mensajeResumen = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resumen de la Reserva", resumen.toString());
        facesContext.addMessage(null, mensajeResumen);
    }

    @PostConstruct
    public void init() {
        listaTipoReserva =obtenerListaTiposReserva(); // Método para obtener los tipos de reserva
        listaProgramaciones = obtenerProgramaciones();
        cargarAsientos();// Método para obtener las programaciones
    }


    private List<Programacion> obtenerProgramaciones() {
        // Realizar una consulta JPQL para obtener las programaciones
        try {
            // Suponiendo que las programaciones se ordenan por fecha (por ejemplo 'desde')
            return em.createQuery("SELECT p FROM Programacion p WHERE p.hasta >= CURRENT_TIMESTAMP ORDER BY p.desde ASC", Programacion.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones en caso de que ocurra algún error
            return Collections.emptyList();  // Retornar lista vacía si hay un error
        }
    }

    // Método para reservar un asiento
    public void reservarAsiento() {
        if (asientoSelecionado != null && !asientosReservadosIds.contains(asientoSelecionado.getIdAsiento())) {
            // Agregar el asiento a la lista de reservados
            asientosReservadosIds.add(asientoSelecionado.getIdAsiento());
            asientosDisponibles.remove(asientoSelecionado);
            asientosReservado.add(asientoSelecionado);
            // Aquí puedes actualizar en la base de datos si es necesario
        }
    }

    // Método para eliminar una reserva
    public void eliminarReserva() {
        if (asientoSelecionado != null && asientosReservadosIds.contains(asientoSelecionado.getIdAsiento())) {
            // Eliminar el asiento de la lista de reservados
            asientosReservadosIds.remove(asientoSelecionado.getIdAsiento());
            asientosReservado.remove(asientoSelecionado);
            asientosDisponibles.add(asientoSelecionado);
            // Aquí puedes actualizar en la base de datos si es necesario
        }
    }




    /**
     * Metodo para autocompletar funciones basadas en la pelicula y la fecha seleci
     * @return
     */
    public List<Programacion> completeFunciones(String query) {
        List<Programacion> result = new ArrayList<>();
        try {
            // Suponiendo que tienes una lista de todas las programaciones
            result = programacionBean.buscarProgramaciones(query); // Ajusta este método
        } catch (Exception e) {
            // Manejo de excepciones
        }
        return result;
    }



    // Método que cargaría las funciones para el día seleccionado
    public void cargarFuncionesDisponibles() {
        programacionesDisponible = obtenerProgramacionPorFecha(diaSleccionado);
    }

    private List<Programacion> obtenerFuncionesParaFecha(OffsetDateTime diaSeleccionado) {
        String jpql = "SELECT p FROM Programacion p WHERE p.desde >= :fechaInicio AND p.desde < :fechaFin";
        TypedQuery<Programacion> query = em.createQuery(jpql, Programacion.class);
        OffsetDateTime inicioDia = diaSeleccionado.toLocalDate().atStartOfDay(diaSeleccionado.getOffset()).toOffsetDateTime();
        OffsetDateTime finDia = inicioDia.plusDays(1).minusSeconds(1);

        query.setParameter("fechaInicio", inicioDia);
        query.setParameter("fechaFin", finDia);

        return query.getResultList();
    }



    public void ProgramacionSeleccionada() {
        if (programacionSelecionada!= null) {
            // Obtener la película asociada con la programación seleccionada
            peliculaSeleccionadaDetalle = programacionSelecionada.getIdPelicula();
        }
    }

    public void generarResumenReserva() {
        // Crear un resumen de la reserva, con los detalles de la función, película, asientos reservados, etc.
        StringBuilder resumen = new StringBuilder();

        // Información básica de la reserva
        resumen.append("Resumen de la Reserva:\n")
                .append("Tipo de Reserva: ").append(tipoReservaSelecionado != null ? tipoReservaSelecionado.getNombre() : "No especificado").append("\n")
                .append("Fecha de Reserva: ").append(getRegistro().getFechaReserva()).append("\n")
                .append("Película: ").append(peliculaSeleccionada != null ? peliculaSeleccionada.getNombre() : "No seleccionada").append("\n")
                .append("Sala: ").append(programacionSelecionada != null ? programacionSelecionada.getIdSala().getNombre() : "No especificada").append("\n")
                .append("Horario: ").append(programacionSelecionada != null ? programacionSelecionada.getDesde() + " - " + programacionSelecionada.getHasta() : "No disponible").append("\n");

        // Asientos reservados
        if (asientosRerservados != null && !asientosRerservados.isEmpty()) {
            resumen.append("Asientos Reservados:\n");
            for (Asiento asiento : asientosRerservados) {
                resumen.append(" - Asiento: ").append(asiento.getNombre()).append("\n");
            }
        } else {
            resumen.append("No se han reservado asientos.\n");
        }

        // Mensaje de confirmación
        resumen.append("\n¡Gracias por realizar tu reserva!");

        // Mostrar el resumen en la interfaz de usuario, por ejemplo, como un mensaje de información en la pantalla
        FacesMessage mensajeResumen = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resumen de la Reserva", resumen.toString());
        facesContext.addMessage(null, mensajeResumen);
    }

    public void confirmarReserva() {
        try {
            if (tipoReservaSelecionado ==null || peliculaSeleccionada ==null || programacionSelecionada==null ||asientosReservado.isEmpty()){
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error","POr favor complete todos los campos"));
                return;
            }

            //Crear la Reserva
            Reserva nuevaReserva=new Reserva();
            nuevaReserva.setIdTipoReserva(tipoReservaSelecionado);
            nuevaReserva.setIdProgramacion(programacionSelecionada);
            nuevaReserva.setFechaReserva(OffsetDateTime.now());
            nuevaReserva.setObservaciones(comentarios);

            //Persistir la reserva
            em.getTransaction().begin();
            em.persist(nuevaReserva);
            em.getTransaction().commit();

            //Actualizar el estado de los asientos reservados
            for (Asiento asiento:asientosRerservados){
                asiento.setActivo(true);
                em.merge(asiento);
            }

            //Confirmacion al usuario
            facesContext.addMessage(null,new FacesMessage( FacesMessage.SEVERITY_INFO,"Reserva confirmada","SU reserva ha sido realizada con exito"));
            reinicarAsistente();
        }catch (Exception e){
            em.getTransaction().rollback();
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se pudoconfirmar la reserva"+e.getMessage()));
            e.printStackTrace();
        }
    }

    private void reinicarAsistente(){
        pasoActual=1;
        setRegistro(null);
        tipoReservaSelecionado=null;
        diaSleccionado=null;
        peliculaSeleccionada=null;
        programacionSelecionada=null;
        asientosDisponibles=null;
        asientosRerservados=null;
        instanciarRegistro();
    }

    // Método para obtener la fecha actual (puedes ajustarlo según tus necesidades)
    public String getFechaActual() {
        // Obtén la fecha actual del sistema (esto es solo un ejemplo)
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    // Método para limpiar los campos de la reserva después de confirmar
    private void limpiarCamposReserva() {
        tipoReservaSelecionado = null;
        peliculaSeleccionada = null;
        programacionSelecionada = null;
        asientosReservado= null;
    }





    /*
    GETTER Y SETTER PARA PASOACTUAL .ETC
     */
    public int getPasoActual() {
        return pasoActual;
    }

    public void setPasoActual(int pasoActual) {
        this.pasoActual = pasoActual;
    }

    public String getDiaSleccionado() {
        return diaSleccionado;
    }

    public void setDiaSleccionado(String diaSleccionado) {
        this.diaSleccionado = diaSleccionado;
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    public void setPeliculaSeleccionada(Pelicula peliculaSeleccionada) {
        this.peliculaSeleccionada = peliculaSeleccionada;
    }

    public Programacion getProgramacionSelecionada() {
        return programacionSelecionada;
    }

    public void setProgramacionSelecionada(Programacion programacionSelecionada) {
        this.programacionSelecionada = programacionSelecionada;
    }

    public List<Asiento> getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(List<Asiento> asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public List<Asiento> getAsientosRerservados() {
        return asientosRerservados;
    }

    public void setAsientosRerservados(List<Asiento> asientosRerservados) {
        this.asientosRerservados = asientosRerservados;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;

    }

    public TipoReserva getTipoReservaSelecionado() {
        return tipoReservaSelecionado;
    }

    public void setTipoReservaSelecionado(TipoReserva tipoReservaSelecionado) {
        this.tipoReservaSelecionado = tipoReservaSelecionado;
    }

    public List<Programacion> getListaProgramaciones() {
        return listaProgramaciones;
    }

    public void setListaProgramaciones(List<Programacion> listaProgramaciones) {
        this.listaProgramaciones = listaProgramaciones;
    }

    public Pelicula getPeliculaSeleccionadaDetalle() {
        return peliculaSeleccionadaDetalle;
    }

    public void setPeliculaSeleccionadaDetalle(Pelicula peliculaSeleccionadaDetalle) {
        this.peliculaSeleccionadaDetalle = peliculaSeleccionadaDetalle;
    }

    public boolean isMostrarDetallePelicula() {
        return mostrarDetallePelicula;
    }

    public void setMostrarDetallePelicula(boolean mostrarDetallePelicula) {
        this.mostrarDetallePelicula = mostrarDetallePelicula;
    }

    public List<Long> getAsientosReservadosIds() {
        return asientosReservadosIds;
    }

    public List<Asiento> getAsientosReservado() {
        return asientosReservado;
    }

    public void setAsientosReservado(List<Asiento> asientosReservado) {
        this.asientosReservado = asientosReservado;
    }

    public void setAsientosReservadosIds(List<Long> asientosReservadosIds) {
        this.asientosReservadosIds = asientosReservadosIds;
    }

    public List<Asiento> getAsientosDisponible() {
        return asientosDisponible;
    }

    public void setAsientosDisponible(List<Asiento> asientosDisponible) {
        this.asientosDisponible = asientosDisponible;
    }

    public Asiento getAsientoSelecionado() {
        return asientoSelecionado;
    }

    public void setAsientoSelecionado(Asiento asientoSelecionado) {
        this.asientoSelecionado = asientoSelecionado;
    }

    public Reserva getReservaConfirmada() {
        return reservaConfirmada;
    }

    public void setReservaConfirmada(Reserva reservaConfirmada) {
        this.reservaConfirmada = reservaConfirmada;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }


    public Object getProgramacionSeleccionada() {
        return programacionSelecionada;
    }

    public OffsetDateTime getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(OffsetDateTime fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }
    private Programacion diaSeleccionado;

    public Programacion getDiaSeleccionado() {
        return diaSeleccionado;
    }

    public void setDiaSeleccionado(Programacion diaSeleccionado) {
        this.diaSeleccionado = diaSeleccionado;
    }

}
