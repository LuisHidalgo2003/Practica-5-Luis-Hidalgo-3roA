package controlador.Dao;

import busqueda.modelo.Cliente;
import controlador.listas.ListaEnlazada;

public class MapeoClientes {
    ListaEnlazada<Cliente> lista = new ListaEnlazada<>();

    public MapeoClientes() {
    }
    
    public MapeoClientes(ListaEnlazada<Cliente> lista) {
        this.lista = lista;
    }  

    public ListaEnlazada<Cliente> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Cliente> lista) {
        this.lista = lista;
    }
}
