public class Pasajero {
    private String cedula;
    private String nombre;
    private int boletosComprados;

    public Pasajero(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.boletosComprados = 0;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getBoletosComprados() {
        return boletosComprados;
    }

    public boolean puedeComprar(int cantidad) {
        return boletosComprados + cantidad <= 5;
    }

    public void agregarBoletos(int cantidad) {
        boletosComprados += cantidad;
    }
}
