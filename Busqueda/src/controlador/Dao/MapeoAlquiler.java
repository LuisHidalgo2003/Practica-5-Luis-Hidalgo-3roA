package controlador.Dao;

import busqueda.modelo.AlquilerLibro;
import controlador.listas.ListaEnlazada;

public class MapeoAlquiler {
    ListaEnlazada<AlquilerLibro> lista = new ListaEnlazada<>();

    public MapeoAlquiler() {
    }
    
    public MapeoAlquiler(ListaEnlazada<AlquilerLibro> lista) {
        this.lista = lista;
    }  

    public ListaEnlazada<AlquilerLibro> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<AlquilerLibro> lista) {
        this.lista = lista;
    }
}
