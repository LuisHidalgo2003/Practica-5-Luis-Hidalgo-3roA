package controlador.Dao;

import busqueda.modelo.Libro;
import controlador.listas.ListaEnlazada;
import java.io.IOException;

public class LibroDao extends AdaptadorDAO<Libro>{
    private Libro libro;

    public LibroDao() {
        super(Libro.class);
    }

    public Libro getLibro() {
        if(libro == null){
            libro = new Libro();
        }
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    public void guardar() throws IOException {
        this.guardar(libro);
    }

    public void modificar(Integer pos) throws IOException {
        this.modificar(libro, pos);
    }

    public ListaEnlazada<Libro> listar(){
        return super.listar();        
    }
}
