public class Ruta {
    private String nombre;
    private double precio;
    private int capacidad;
    private int vendidos;

    public Ruta(String nombre, double precio, int capacidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.capacidad = capacidad;
        this.vendidos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getVendidos() {
        return vendidos;
    }

    public int getDisponibles() {
        return capacidad - vendidos;
    }

    public boolean tieneCupos(int cantidad) {
        return vendidos + cantidad <= capacidad;
    }

    public void vender(int cantidad) {
        vendidos += cantidad;
    }
}