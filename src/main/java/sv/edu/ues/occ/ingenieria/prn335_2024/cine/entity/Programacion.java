package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "programacion", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Programacion.findAll", query = "SELECT pr FROM Programacion pr ORDER BY pr.idProgramacion ASC"),
        @NamedQuery(name = "Programacion.findByDate", query ="SELECT pr FROM Programacion pr WHERE pr.desde >=:desde AND pr.hasta <:hasta")
})
public class Programacion {
    @Id
    @Column(name = "id_programacion", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    @JsonIgnore
    private Sala idSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula")
    private Pelicula idPelicula;

    @Column(name = "desde")
    private OffsetDateTime desde;

    @Column(name = "hasta")
    private OffsetDateTime hasta;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idProgramacion")
    public List<Reserva> ReservaList;


    public Programacion(Long idProgramacion)
    {
        this.idProgramacion = idProgramacion;

    }

    public Programacion()
    {

    }

    public Long getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Long id) {
        this.idProgramacion = id;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public OffsetDateTime getDesde() {
        return desde;
    }

    public void setDesde(OffsetDateTime desde) {
        this.desde = desde;
    }

    public OffsetDateTime getHasta() {
        return hasta;
    }

    public void setHasta(OffsetDateTime hasta) {
        this.hasta = hasta;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<Reserva> getReservaList() {
        return ReservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        ReservaList = reservaList;
    }

    public Duration getDuracion() {
        if (desde != null && hasta != null) {
            return Duration.between(desde, hasta);
        }
        return Duration.ZERO; // Retorna duración cero si alguna fecha es nula
    }

    public String getDuracionFormato() {
        Duration duracion = getDuracion();
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        return String.format("%d horas %d minutos", horas, minutos);
    }
}