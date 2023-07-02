package busqueda.controlador.Binaria;

import controlador.listas.ListaEnlazada;
import java.lang.reflect.Field;
import ordenacion.Excepciones.AtributoNoEncontradoException;
import ordenacion.controlador.MergeSort.MetodoMergeSort;
import ordenacion.controlador.Utilidades.Utiles;

public class BusquedaBinaria<E> {
    public Integer binaria(ListaEnlazada<E> lista, E dato, String atributo) throws IllegalAccessException, IllegalArgumentException, AtributoNoEncontradoException{
        Class clazz = lista.getCabecera().getDato().getClass();
        MetodoMergeSort mms = new MetodoMergeSort();
        lista = mms.mergeSort(lista, atributo, MetodoMergeSort.ASCENDENTE);
        E array[] = lista.toArray();
        Boolean isObject = Utiles.isObject(clazz);
        System.out.println("ListaOrdenada");
        lista.imprimir();
        if (isObject) {
            return binariaObject(array, dato, atributo);
        }else{
            if (Utiles.isNumber(clazz)) {
                return binariaNumber(array, dato);
            }else{
                return binariaString(array, dato);
            }
        }
    }
    
    private static Integer binariaObject(Object array[], Object dato, Object atributo) throws AtributoNoEncontradoException, IllegalAccessException{
        Field field = Utiles.obtenerAtributos(array[0].getClass(), atributo.toString());
        if (field == null) {
            throw new AtributoNoEncontradoException();
        }
        field.setAccessible(true);
        Object arrayAtributos[] = new Object[array.length];
        
        for (int i = 0; i < array.length; i++) {
            arrayAtributos[i] = field.get(array[i]);
        }
        
        if (Utiles.isNumber(arrayAtributos[0].getClass())) {
            return binariaNumber(arrayAtributos, dato);
        }else{
            return binariaString(arrayAtributos, dato);
        }
    }
    
    private static Integer binariaNumber(Object array[], Object dato){
        Integer mitad = array.length/2;
        if (((Number)array[mitad]).doubleValue() == ((Number)dato).doubleValue()) {
            return mitad;
        }else if(((Number)dato).doubleValue() < ((Number)array[mitad]).doubleValue()){
            Object arrayAux[] = new Object[mitad];
            for (int i = 0; i < arrayAux.length; i++) {
                arrayAux[i] = array[i];
            }
            return binariaNumber(arrayAux, dato);
        }else{
            Object arrayAux[] = new Object[array.length-mitad];
            for (int i = 0; i < arrayAux.length; i++) {
                arrayAux[i] = array[mitad+i];
            }
            return mitad + binariaNumber(arrayAux, dato);
        }
    }
    
    private static Integer binariaString(Object array[], Object dato){
        Integer mitad = array.length/2;
        if (array[mitad].equals(dato)) {
            return mitad;
        }else{
            //Integer valoresMitad[] = Utiles.valorLetrasString(array[mitad].toString());
            //Integer valoresDato[] = Utiles.valorLetrasString(dato.toString());
            //if(valoresDato[0] < valoresMitad[0]){
            if(array[mitad].toString().compareTo(dato.toString()) > 0){
                Object arrayAux[] = new Object[mitad];
                for (int i = 0; i < arrayAux.length; i++) {
                    arrayAux[i] = array[i];
                }
                return binariaString(arrayAux, dato);
            }else{
                Object arrayAux[] = new Object[array.length-mitad];
                for (int i = 0; i < arrayAux.length; i++) {
                    arrayAux[i] = array[mitad+i];
                }
                return mitad + binariaString(arrayAux, dato);
            }
        }
    }
    
    public static Integer binariaListInteger(ListaEnlazada<Integer> lista, Object dato) throws IllegalAccessException, AtributoNoEncontradoException{        
        MetodoMergeSort mqs = new MetodoMergeSort();
        lista = mqs.mergeSort(lista, null, MetodoMergeSort.ASCENDENTE);
        
        Object array[] = lista.toArray();
                
        System.out.println("ListaOrdenada");
        lista.imprimir();
        return binariaNumber(array,dato);
    }
    
    public static Integer binariaListFloat(ListaEnlazada<Float> lista, Object dato) throws IllegalAccessException, AtributoNoEncontradoException{    
        MetodoMergeSort mqs = new MetodoMergeSort();
        lista = mqs.mergeSort(lista, null, MetodoMergeSort.ASCENDENTE);
        
        Object array[] = lista.toArray();
                
        System.out.println("ListaOrdenada");
        lista.imprimir();
        return binariaNumber(array,dato);
    }
    
    public static Integer binariaListDouble(ListaEnlazada<Double> lista, Object dato) throws IllegalAccessException, AtributoNoEncontradoException{        
        MetodoMergeSort mqs = new MetodoMergeSort();
        lista = mqs.mergeSort(lista, null, MetodoMergeSort.ASCENDENTE);
        
        Object array[] = lista.toArray();
                
        System.out.println("ListaOrdenada");
        lista.imprimir();
        return binariaNumber(array,dato);
    }
    
    public static Integer binariaListString(ListaEnlazada<String> lista, Object dato) throws IllegalAccessException, AtributoNoEncontradoException{        
        MetodoMergeSort mqs = new MetodoMergeSort();
        lista = mqs.mergeSort(lista, null, MetodoMergeSort.ASCENDENTE);
        
        Object array[] = lista.toArray();
                
        System.out.println("ListaOrdenada");
        lista.imprimir();
        return binariaString(array,dato);
    }
}
