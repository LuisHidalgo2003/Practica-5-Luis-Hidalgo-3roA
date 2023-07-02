package controlador.Dao;

import controlador.listas.ListaEnlazada;
import java.io.IOException;


public interface InterfazDAO <T>{
    /**
     * Metodo que permite guardar
     * @param obj Objeto del modelo
     */
    public void guardar(T obj) throws IOException;
    /**
     * Permite modificar los datos en un repositorio de datos
     * @param obj Objeto a modificar
     * @param pos posicion del arreglo
     */
    public void modificar(T obj, Integer pos) throws IOException;
    /**
     * Permite listar los elementos
     * @return una lista enlazada
     */
    public ListaEnlazada<T> listar();
    /**
     * Permite obtener un elemento del arreglo
     * @param id Posicion
     * @return returna un dato
     */
    public T obtener(Integer id);
      
}