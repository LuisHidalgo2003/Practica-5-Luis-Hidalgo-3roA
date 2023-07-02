package busqueda.controlador.Lineal;

import controlador.listas.ListaEnlazada;
import java.lang.reflect.Field;
import ordenacion.Excepciones.AtributoNoEncontradoException;
import ordenacion.controlador.Utilidades.Utiles;

public class BusquedaLineal<E> {
    public ListaEnlazada<Integer> busquedaLineal(ListaEnlazada<E> lista, E dato, String atributo) throws AtributoNoEncontradoException, IllegalAccessException{
        ListaEnlazada<Integer> pos = new ListaEnlazada<>();
        Class clazz = lista.getCabecera().getDato().getClass();
        E  array[] = lista.toArray();
        Boolean isObject = Utiles.isObject(clazz);
        if (isObject) {
            pos = linealObject(lista, dato, atributo);
        }else{
            if (Utiles.isNumber(clazz)) {
                pos = linealNumber(array, dato);
            }else{
                pos = linealString(array, dato);
            }
        }
        
        return pos;
    }
    
    public ListaEnlazada<Integer> linealObject(ListaEnlazada<E> lista, E dato, String atributo) throws AtributoNoEncontradoException, IllegalAccessException{
        Class clazz = lista.getCabecera().getDato().getClass();
        Field field = Utiles.obtenerAtributos(clazz, atributo);
        if (field == null) {
            throw new AtributoNoEncontradoException();
        }
        field.setAccessible(true);
        E array[] = lista.toArray();
        Object arrayAtributos[] = new Object[array.length];
        
        for (int i = 0; i < array.length; i++) {
            arrayAtributos[i] = field.get(array[i]);
        }
        
        if (Utiles.isNumber(arrayAtributos[0].getClass())) {
            return linealNumber(arrayAtributos, dato);
        }else{
            return linealString(arrayAtributos, dato);
        }
    }
    
    public static ListaEnlazada<Integer> linealListInteger(ListaEnlazada<Integer> lista, Integer dato){
        return linealNumber(lista.toArray(), dato);
    }
    
    public ListaEnlazada<Integer> linealString(Object array[], E dato){
        ListaEnlazada<Integer> posiciones = new ListaEnlazada<>();
        
        for (int i = 0; i < array.length; i++) {
            if (array[i].toString().toLowerCase().trim().equals(dato.toString().toLowerCase().trim())) {
                posiciones.insertar(i);
            }
        }
        
        if (posiciones.estaVacia()) {
            System.out.println("El elemento no se encuenta en la lista");
        }
        
        return posiciones;
    }
    
    public static ListaEnlazada<Integer> linealListFloat(ListaEnlazada<Float> lista, Object dato){
        return linealNumber(lista.toArray(), dato);
    }
    
    public static ListaEnlazada<Integer> linealListDouble(ListaEnlazada<Double> lista, Object dato){
        return linealNumber(lista.toArray(), dato);
    }
    
    private static ListaEnlazada<Integer> linealNumber(Object array[], Object dato){
        ListaEnlazada<Integer> posiciones = new ListaEnlazada<>();
        
        for (int i = 0; i < array.length; i++) {
            if (((Number)array[i]).doubleValue() == ((Number)dato).doubleValue()) {
                posiciones.insertar(i);
            }
        }
        
        if (posiciones.estaVacia()) {
            System.out.println("El elemento no se encuenta en la lista");
        }
        
        return posiciones;
    }
}
