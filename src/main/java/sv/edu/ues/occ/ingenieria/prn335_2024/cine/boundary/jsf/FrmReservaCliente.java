package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import javax.swing.*;
import java.awt.event.*;

public class FrmReservaCliente extends JFrame {
    private JTextField txtCliente, txtFecha, txtDetalles;
    private JButton btnReservar, btnCancelar, btnBuscar;
    private JTextArea areaReservas;

    public FrmReservaCliente() {
        setTitle("Sistema de Reserva");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Inicialización de componentes
        txtCliente = new JTextField(20);
        txtFecha = new JTextField(20);
        txtDetalles = new JTextField(20);

        btnReservar = new JButton("Reservar");
        btnCancelar = new JButton("Cancelar");
        btnBuscar = new JButton("Buscar");

        areaReservas = new JTextArea(10, 30);
        areaReservas.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Cliente:"));
        panel.add(txtCliente);
        panel.add(new JLabel("Fecha:"));
        panel.add(txtFecha);
        panel.add(new JLabel("Detalles:"));
        panel.add(txtDetalles);

        panel.add(btnReservar);
        panel.add(btnCancelar);
        panel.add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane(areaReservas);
        panel.add(scrollPane);

        // Agregar acciones
        btnReservar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reservar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarReserva();
            }
        });

        add(panel);
    }

    private void reservar() {
        String cliente = txtCliente.getText();
        String fecha = txtFecha.getText();
        String detalles = txtDetalles.getText();

        if (!cliente.isEmpty() && !fecha.isEmpty() && !detalles.isEmpty()) {
            areaReservas.append("Reserva: " + cliente + ", Fecha: " + fecha + ", Detalles: " + detalles + "\n");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        limpiarCampos();
    }

    private void buscarReserva() {
        String busqueda = JOptionPane.showInputDialog("Ingrese nombre del cliente para buscar:");
        if (busqueda != null && !busqueda.isEmpty()) {
            String contenido = areaReservas.getText();
            if (contenido.contains(busqueda)) {
                JOptionPane.showMessageDialog(this, "Reserva encontrada:\n" + contenido);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna reserva para: " + busqueda);
            }
        }
    }

    private void limpiarCampos() {
        txtCliente.setText("");
        txtFecha.setText("");
        txtDetalles.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmReservaCliente().setVisible(true));
    }
}