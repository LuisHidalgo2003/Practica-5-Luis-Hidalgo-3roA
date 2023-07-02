package controlador.Dao;

import controlador.listas.ListaEnlazada;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AdaptadorDAO<T> implements InterfazDAO<T> {

    private Conexion conexion;
    private Class clazz;
    private String url;
    public static Integer ASCENDENTE = 0;
    public static Integer DESCENDENTE = 1;

    public AdaptadorDAO(Class clazz) {
        this.conexion = new Conexion();
        this.clazz = clazz;
        this.url = Conexion.URL;
    }

    @Override
    public void guardar(T obj) throws IOException {
        ListaEnlazada<T> lista = listar();
        lista.insertar(obj);
        conexion.guardar(lista);
    }

    @Override
    public void modificar(T obj, Integer pos) throws IOException {
        ListaEnlazada<T> lista = listar();
        lista.modificarPoscicion(obj, pos);
        conexion.guardar(lista);
    }

    @Override
    public ListaEnlazada<T> listar() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        try {
            lista = conexion.leerJson(retornarClase());
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }
    
    private String retornarClase(){
        String clase = "";
        char [] arreglo = this.getClass().getSimpleName().toCharArray();
        
        for (int i = 0; i < arreglo.length - 3; i++) {
            clase = clase + arreglo[i];
        }
        return clase;
    }

    @Override
    public T obtener(Integer id) {
        T obj = null;
        ListaEnlazada<T> lista = listar();
        for (int i = 0; i < lista.getTamanio(); i++) {
            try {
                T dato = lista.obtener(i);
                if (id.intValue() == ((Integer)getValueField(dato)).intValue()) {
                    obj = dato;
                }
            } catch (Exception e) {
                System.out.println("Error en metodo " + e);
            }
        }
        return obj;
    }

    private Object getValueField(T dato) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method metodo = null;
        for (Method aux : this.clazz.getDeclaredMethods()) {
            if (aux.getName().toLowerCase().equalsIgnoreCase("getId")) {
                metodo = aux;
            }
        }

        if (metodo == null) {
            for (Method aux : this.clazz.getSuperclass().getDeclaredMethods()) {
                if (aux.getName().toLowerCase().equalsIgnoreCase("getId")) {
                    metodo = aux;
                }
            }
        }
        return metodo.invoke(dato);
    }

    public Integer generarId() {
        return listar().getTamanio()+ 1;
    }

}
