package controlador.Dao;

import busqueda.modelo.Libro;
import controlador.listas.ListaEnlazada;

public class MapeoLibros {
    ListaEnlazada<Libro> lista = new ListaEnlazada<>();

    public MapeoLibros() {
    }
    
    public MapeoLibros(ListaEnlazada<Libro> lista) {
        this.lista = lista;
    }  

    public ListaEnlazada<Libro> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Libro> lista) {
        this.lista = lista;
    }
}
