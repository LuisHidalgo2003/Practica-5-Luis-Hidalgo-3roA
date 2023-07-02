package controlador.Dao;

import busqueda.modelo.AlquilerLibro;
import controlador.listas.ListaEnlazada;
import java.io.IOException;

public class AlquilerLibroDao extends AdaptadorDAO<AlquilerLibro>{
    private AlquilerLibro alquiler;
    
    public AlquilerLibroDao() {
        super(AlquilerLibro.class);
    }

    public AlquilerLibro getAlquiler() {
        if (this.alquiler == null) {
            this.alquiler = new AlquilerLibro();
        }
        return alquiler;
    }

    public void setAlquiler(AlquilerLibro alquiler) {
        this.alquiler = alquiler;
    }
    
    public void guardar() throws IOException {
        this.guardar(alquiler);
    }

    public void modificar(Integer pos) throws IOException {
        this.modificar(alquiler, pos);
    }

    public ListaEnlazada<AlquilerLibro> listar(){
        return super.listar();        
    }
}
