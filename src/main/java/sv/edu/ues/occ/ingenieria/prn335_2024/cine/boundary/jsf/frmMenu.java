package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class frmMenu implements Serializable {
    @Inject
    FacesContext facesContext;
    DefaultMenuModel model;

    @PostConstruct
    public void init(){
        model = new DefaultMenuModel();
        DefaultSubMenu tipos = DefaultSubMenu.builder()
                .label("Tipos")
                .expanded(true)
                .build();
        DefaultMenuItem itemPago = DefaultMenuItem.builder()
                .value("Pago")
                .icon("pi pi-credit-card")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPago.jsf')}")
                .build();
        DefaultMenuItem itemAsiento = DefaultMenuItem.builder()
                .value("Asiento")
                .icon("pi pi-cloud")  // Aquí agregas el icono
                .ajax(true)
                .command("#{frmMenu.navegar('TipoAsiento.jsf')}")
                .build();
        DefaultMenuItem itemPelicula = DefaultMenuItem.builder()
                .value("Película")
                .icon("pi pi-star")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPelicula.jsf')}")
                .build();
        DefaultMenuItem itemProducto = DefaultMenuItem.builder()
                .value("Producto")
                .icon("pi pi-shop")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoProducto.jsf')}")
                .build();
        DefaultMenuItem itemReserva = DefaultMenuItem.builder()
                .value("Reserva")
                .icon(" pi pi-calendar-plus")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoReserva.jsf')}")
                .build();
        DefaultMenuItem itemSala = DefaultMenuItem.builder()
                .value("Sala")
                .icon("pi pi-desktop")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoSala.jsf')}")
                .build();

        DefaultSubMenu Cine = DefaultSubMenu.builder()
                .label("Cine")
                .expanded(true)
                .build();
        DefaultMenuItem Peliculas_Lista = DefaultMenuItem.builder()
                .value("Pelicula")
                .icon("pi pi-list")
                .ajax(true)
                .command("#{frmMenu.navegar('Pelicula.jsf')}")
                .build();
        DefaultMenuItem sucursal = DefaultMenuItem.builder()
                .value("Sucursal")
                .icon("pi pi-home")
                .ajax(true)
                .command("#{frmMenu.navegar('Sucursal.jsf')}")
                .build();
        DefaultMenuItem sala = DefaultMenuItem.builder()
                .value("Sala")
                .icon("pi pi-desktop")
                .ajax(true)
                .command("#{frmMenu.navegar('Sala.jsf')}")
                .build();
        DefaultMenuItem reserva = DefaultMenuItem.builder()
                .value("Reserva")
                .icon("pi pi-calendar-plus")
                .ajax(true)
                .command("#{frmMenu.navegar('Reserva.jsf')}")
                .build();
        DefaultMenuItem reservaCliente = DefaultMenuItem.builder()
                .value("Reserva del Cliente")
                .icon("pi pi-calendar-plus")
                .ajax(true)
                .command("#{frmMenu.navegar('ReservaCliente.jsf')}")
                .build();

        tipos.getElements().add(itemSala);
        tipos.getElements().add(itemAsiento);
        tipos.getElements().add(itemReserva);
        tipos.getElements().add(itemPelicula);
        tipos.getElements().add(itemProducto);
        tipos.getElements().add(itemPago);
        Cine.getElements().add(Peliculas_Lista);
        Cine.getElements().add(sucursal);
        Cine.getElements().add(sala);
        Cine.getElements().add(reserva);
        Cine.getElements().add(reservaCliente);
        model.getElements().add(tipos);
        model.getElements().add(Cine);




    }

    public void navegar(String formulario) throws IOException {
        facesContext.getExternalContext().redirect(formulario);
    }

    public DefaultMenuModel getModel() {
        return model;
    }
}
