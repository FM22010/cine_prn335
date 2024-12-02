package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tipo_pago", schema = "public")
public class TipoPago {
    @Id
    @Column(name = "id_tipo_pago", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPago;

    @NotBlank(message = "El nombre no es valido ")
    @Size(max = 155, min = 3, message = "Debe agregar un nombre entre 3 y 155 caracteres")
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idTipoPago")
    public List<Pago> PagoList;

    public TipoPago() {}
    public TipoPago(Integer idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public Integer getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Integer id) {
        this.idTipoPago = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Pago> getPagoList() {
        return PagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        PagoList = pagoList;
    }
}