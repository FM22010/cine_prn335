package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "pelicula", schema = "public")
public class Pelicula {
    @Id
    @Column(name = "id_pelicula", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelicula;

    @NotBlank(message = "El nombre no es valido ")
    @Size(max = 155, min = 3, message = "Debe agregar un nombre entre 3 y 155 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "sinopsis")
    private String sinopsis;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idPelicula")
    public List<PeliculaCaracteristica> PeliculaCaracteristicaList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idPelicula")
    public List<Programacion> ProgramacionList;

    @Transient
    private String autocompletado;

    public Pelicula (long idPelicula)
    {
        this.idPelicula = idPelicula;
    }
    public Pelicula()
{

}
    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long id) {
        this.idPelicula = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public List<PeliculaCaracteristica> getPeliculaCaracteristicaList() {
        return PeliculaCaracteristicaList;
    }

    public void setPeliculaCaracteristicaList(List<PeliculaCaracteristica> peliculaCaracteristicaList) {
        PeliculaCaracteristicaList = peliculaCaracteristicaList;
    }

    public List<Programacion> getProgramacionList() {
        return ProgramacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        ProgramacionList = programacionList;
    }

    public String getAutocompletado() {
        return autocompletado;
    }

    public void setAutocompletado(String autocompletado) {
        this.autocompletado = autocompletado;
    }
}