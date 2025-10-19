import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentaBoletos {
    private JPanel Principal;
    private JComboBox comboRuta;
    private JTextField txtCantidad;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JButton btnComprar;
    private JTextArea areaVentas;
    private JLabel lblVendidosGye;
    private JLabel lblVendidosCuen;
    private JLabel lblVendidosLoja;
    private JLabel lblDisponiblesGye;
    private JLabel lblDisponiblesCuen;
    private JLabel lblDisponiblesLoja;
    private JLabel lblTotalRecaudado;
    private JLabel lblruta;
    private JLabel lblcedula;
    private JLabel lblnombre;
    private JLabel lblcantidad;


    private Ruta rutaGye = new Ruta("QUITO - GUAYAQUIL", 10.50, 20);
    private Ruta rutaCuen = new Ruta("QUITO - CUENCA", 12.75, 20);
    private Ruta rutaLoja = new Ruta("QUITO - LOJA", 15.00, 20);
    private double totalRecaudado = 0;

    private List<Pasajero> pasajeros = new ArrayList<>();
    public VentaBoletos() {
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCompra();
            }
        });
    }

    private void realizarCompra() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(Principal, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(Principal, "La cédula debe contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(Principal, "Ingrese un número válido de boletos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidad < 1 || cantidad > 5) {
            JOptionPane.showMessageDialog(Principal, "Puede comprar entre 1 y 5 boletos por transacción.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pasajero pasajero = null;
        for (Pasajero p : pasajeros) {
            if (p.getCedula().equals(cedula)) {
                pasajero = p;
                break;
            }
        }

        if (pasajero == null) {
            pasajero = new Pasajero(cedula, nombre);
            pasajeros.add(pasajero);
        }

        if (!pasajero.puedeComprar(cantidad)) {
            JOptionPane.showMessageDialog(Principal,
                    "Cada cédula solo puede comprar un máximo de 5 boletos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String seleccion = comboRuta.getSelectedItem().toString();
        Ruta ruta = null;

        if (seleccion.contains("GUAYAQUIL")) ruta = rutaGye;
        else if (seleccion.contains("CUENCA")) ruta = rutaCuen;
        else if (seleccion.contains("LOJA")) ruta = rutaLoja;

        if (ruta == null) {
            JOptionPane.showMessageDialog(Principal, "Seleccione una ruta válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ruta.tieneCupos(cantidad)) {
            JOptionPane.showMessageDialog(Principal, "No hay suficientes boletos disponibles para esta ruta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = ruta.getPrecio() * cantidad;
        ruta.vender(cantidad);
        pasajero.agregarBoletos(cantidad);
        areaVentas.append(ruta.getNombre() + " | " + cantidad + " boletos | " +
                pasajero.getNombre() + " | Total: $" + String.format("%.2f", total) + "\n");

        actualizarLabels();
        limpiarCampos();
    }

    private void actualizarLabels() {
        lblVendidosGye.setText("Vendidos QUITO-GYE: " + rutaGye.getVendidos());
        lblVendidosCuen.setText("Vendidos QUITO-CUEN: " + rutaCuen.getVendidos());
        lblVendidosLoja.setText("Vendidos QUITO-LOJA: " + rutaLoja.getVendidos());

        lblDisponiblesGye.setText("Disponibles QUITO-GYE: " + rutaGye.getDisponibles());
        lblDisponiblesCuen.setText("Disponibles QUITO-CUEN: " + rutaCuen.getDisponibles());
        lblDisponiblesLoja.setText("Disponibles QUITO-LOJA: " + rutaLoja.getDisponibles());

        lblTotalRecaudado.setText(String.format("Total recaudado: $%.2f", totalRecaudado));
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentaBoletos");
        frame.setContentPane(new VentaBoletos().Principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
