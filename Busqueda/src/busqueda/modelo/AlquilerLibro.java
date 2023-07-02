package busqueda.modelo;

import java.time.LocalDate;

public class AlquilerLibro {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Cliente cliente;
    private Libro libro;

    public AlquilerLibro() {
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", cliente=" + cliente.getNombre() + ", libro=" + libro.getTitulo();
    }
    
       
    
}
