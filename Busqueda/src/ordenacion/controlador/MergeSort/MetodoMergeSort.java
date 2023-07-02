package ordenacion.controlador.MergeSort;

import controlador.listas.ListaEnlazada;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import ordenacion.Excepciones.AtributoNoEncontradoException;
import ordenacion.controlador.Utilidades.Utiles;

public class MetodoMergeSort<E> {

    public static Integer ASCENDENTE = 1;
    public static Integer DESCENDENTE = 2;

    public ListaEnlazada<E> mergeSort(ListaEnlazada<E> lista, String atributo, Integer orden) throws AtributoNoEncontradoException, IllegalArgumentException, IllegalAccessException {
        Class clazz = null;
        E[] matriz = lista.toArray();
        if (lista.getTamanio() > 0) {
            clazz = (Class) lista.getCabecera().getDato().getClass();
            Boolean isObject = Utiles.isObject(clazz);
            if (isObject) {
                matriz = mergeSortObject(matriz, 0, matriz.length - 1, orden, atributo);
            } else {
                if (Utiles.isNumber(clazz)) {
                    if (orden == DESCENDENTE) {
                        matriz = mergeSortNumberDescendente(matriz, 0, matriz.length - 1);
                    } else {
                        matriz = mergeSortNumberAscendente(matriz, 0, matriz.length - 1);
                    }
                } else {
                    if (orden == DESCENDENTE) {
                        matriz = mergeSortStringDescendente(matriz, 0, matriz.length - 1);
                    } else {
                        matriz = mergeSortStringAscendente(matriz, 0, matriz.length - 1);
                    }
                }
            }
        }
        lista = lista.toList(matriz);
        return lista;
    }

    private E[] mergeSortObject(E array[], Integer ini, Integer fin, Integer orden, String atributo) throws AtributoNoEncontradoException, IllegalArgumentException, IllegalAccessException {
        Class clazz = array[0].getClass();
        Field field = Utiles.obtenerAtributos(clazz, atributo);
        if (field == null) {
            throw new AtributoNoEncontradoException();
        }
        field.setAccessible(true);

        Object arrayAtributos[] = new Object[array.length];

        for (int i = 0; i < array.length; i++) {
            E dato = array[i];
            arrayAtributos[i] = field.get(dato);
        }

        if (orden == DESCENDENTE) {
            array = mergeSortObjectDescendente(array, ini, fin, arrayAtributos, atributo);
        } else {
            array = mergeSortObjectAscendente(array, ini, fin, arrayAtributos, atributo);
        }

        return array;
    }

    private E[] mergeSortObjectAscendente(E[] matriz, Integer ini, Integer fin, Object[] atributos, String atributo) throws IllegalArgumentException, IllegalAccessException {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortObjectAscendente(matriz, ini, medio, atributos, atributo);
            mergeSortObjectAscendente(matriz, (medio + 1), fin, atributos, atributo);
            mezclarObjectAscendente(matriz, ini, medio, fin, atributos, atributo);
        }
        return matriz;
    }

    private E[] mezclarObjectAscendente(E[] matriz, Integer ini, Integer medio, Integer fin, Object[] atributos, String atributo) throws IllegalArgumentException, IllegalAccessException {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        E[] auxOBJ = (E[]) Array.newInstance(matriz[0].getClass(), matriz.length);
        if (Utiles.isNumber(atributos[0].getClass())) {            
            Number[] aux = new Number[matriz.length];
            while (izquierda <= medio && derecha <= fin) {
                if (((Number) atributos[izquierda]).doubleValue() < ((Number) atributos[derecha]).doubleValue()) {
                    aux[i] = (Number) atributos[izquierda];
                    auxOBJ[i] = matriz[izquierda];
                    izquierda++;
                    i++;
                } else {
                    aux[i] = (Number) atributos[derecha];
                    auxOBJ[i] = matriz[derecha];
                    derecha++;
                    i++;
                }
            }

            for (int j = derecha; j <= fin; j++) {
                aux[i] = (Number) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = izquierda; j <= medio; j++) {
                aux[i] = (Number) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = 0; j < i; j++) {
                atributos[ini + j] = (Object) aux[j];
                matriz[ini + j] = auxOBJ[j];
            }
        } else {
            String[] aux = new String[matriz.length];
            while (izquierda <= medio && derecha <= fin) {
                if (((String) atributos[izquierda]).compareToIgnoreCase((String) atributos[derecha]) < 0) {
                    aux[i] = (String) atributos[izquierda];
                    auxOBJ[i] = matriz[izquierda];
                    izquierda++;
                    i++;
                } else {
                    aux[i] = (String) atributos[derecha];
                    auxOBJ[i] = matriz[derecha];
                    derecha++;
                    i++;
                }
            }

            for (int j = derecha; j <= fin; j++) {
                aux[i] = (String) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = izquierda; j <= medio; j++) {
                aux[i] = (String) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = 0; j < i; j++) {
                atributos[ini + j] = (E) aux[j];
                matriz[ini + j] = auxOBJ[j];
            }
        }

        return matriz;
    }
    
    private E[] mergeSortObjectDescendente(E[] matriz, Integer ini, Integer fin, Object[] atributos, String atributo) throws IllegalArgumentException, IllegalAccessException {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortObjectDescendente(matriz, ini, medio, atributos, atributo);
            mergeSortObjectDescendente(matriz, (medio + 1), fin, atributos, atributo);
            mezclarObjectDescendente(matriz, ini, medio, fin, atributos, atributo);
        }
        return matriz;
    }

    private E[] mezclarObjectDescendente(E[] matriz, Integer ini, Integer medio, Integer fin, Object[] atributos, String atributo) throws IllegalArgumentException, IllegalAccessException {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        E[] auxOBJ = (E[]) Array.newInstance(matriz[0].getClass(), matriz.length);
        if (Utiles.isNumber(atributos[0].getClass())) {            
            Number[] aux = new Number[matriz.length];
            while (izquierda <= medio && derecha <= fin) {
                if (((Number) atributos[izquierda]).doubleValue() > ((Number) atributos[derecha]).doubleValue()) {
                    aux[i] = (Number) atributos[izquierda];
                    auxOBJ[i] = matriz[izquierda];
                    izquierda++;
                    i++;
                } else {
                    aux[i] = (Number) atributos[derecha];
                    auxOBJ[i] = matriz[derecha];
                    derecha++;
                    i++;
                }
            }

            for (int j = derecha; j <= fin; j++) {
                aux[i] = (Number) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = izquierda; j <= medio; j++) {
                aux[i] = (Number) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = 0; j < i; j++) {
                atributos[ini + j] = (Object) aux[j];
                matriz[ini + j] = auxOBJ[j];
            }
        } else {
            String[] aux = new String[matriz.length];
            while (izquierda <= medio && derecha <= fin) {
                if (((String) atributos[izquierda]).compareToIgnoreCase((String) atributos[derecha]) > 0) {
                    aux[i] = (String) atributos[izquierda];
                    auxOBJ[i] = matriz[izquierda];
                    izquierda++;
                    i++;
                } else {
                    aux[i] = (String) atributos[derecha];
                    auxOBJ[i] = matriz[derecha];
                    derecha++;
                    i++;
                }
            }

            for (int j = derecha; j <= fin; j++) {
                aux[i] = (String) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = izquierda; j <= medio; j++) {
                aux[i] = (String) atributos[j];
                auxOBJ[i] = matriz[j];
                i++;
            }

            for (int j = 0; j < i; j++) {
                atributos[ini + j] = (E) aux[j];
                matriz[ini + j] = auxOBJ[j];
            }
        }

        return matriz;
    }

    private E[] mergeSortNumberAscendente(E[] matriz, Integer ini, Integer fin) {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortNumberAscendente(matriz, ini, medio);
            mergeSortNumberAscendente(matriz, (medio + 1), fin);
            mezclarNumberAscendente(matriz, ini, medio, fin);
        }
        return matriz;
    }

    private E[] mezclarNumberAscendente(E[] matriz, Integer ini, Integer medio, Integer fin) {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        Number[] aux = new Number[matriz.length];
        while (izquierda <= medio && derecha <= fin) {
            if (((Number) matriz[izquierda]).doubleValue() < ((Number) matriz[derecha]).doubleValue()) {
                aux[i] = (Number) matriz[izquierda];
                izquierda++;
                i++;
            } else {
                aux[i] = (Number) matriz[derecha];
                derecha++;
                i++;
            }
        }

        for (int j = derecha; j <= fin; j++) {
            aux[i] = (Number) matriz[j];
            i++;
        }

        for (int j = izquierda; j <= medio; j++) {
            aux[i] = (Number) matriz[j];
            i++;
        }

        for (int j = 0; j < i; j++) {
            matriz[ini + j] = (E) aux[j];
        }

        return matriz;
    }

    private E[] mergeSortNumberDescendente(E[] matriz, Integer ini, Integer fin) {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortNumberDescendente(matriz, ini, medio);
            mergeSortNumberDescendente(matriz, (medio + 1), fin);
            mezclarNumberDescendente(matriz, ini, medio, fin);
        }
        return matriz;
    }

    private E[] mezclarNumberDescendente(E[] matriz, Integer ini, Integer medio, Integer fin) {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        Number[] aux = new Number[matriz.length];
        while (izquierda <= medio && derecha <= fin) {
            if (((Number) matriz[izquierda]).doubleValue() > ((Number) matriz[derecha]).doubleValue()) {
                aux[i] = (Number) matriz[izquierda];
                izquierda++;
                i++;
            } else {
                aux[i] = (Number) matriz[derecha];
                derecha++;
                i++;
            }
        }

        for (int j = derecha; j <= fin; j++) {
            aux[i] = (Number) matriz[j];
            i++;
        }

        for (int j = izquierda; j <= medio; j++) {
            aux[i] = (Number) matriz[j];
            i++;
        }

        for (int j = 0; j < i; j++) {
            matriz[ini + j] = (E) aux[j];
        }

        return matriz;
    }

    private E[] mergeSortStringAscendente(E[] matriz, Integer ini, Integer fin) {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortStringAscendente(matriz, ini, medio);
            mergeSortStringAscendente(matriz, (medio + 1), fin);
            mezclarStringAscendente(matriz, ini, medio, fin);
        }
        return matriz;
    }

    private E[] mezclarStringAscendente(E[] matriz, Integer ini, Integer medio, Integer fin) {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        String[] aux = new String[matriz.length];
        while (izquierda <= medio && derecha <= fin) {
            if (((String) matriz[izquierda]).compareToIgnoreCase((String) matriz[derecha]) < 0) {
                aux[i] = (String) matriz[izquierda];
                izquierda++;
                i++;
            } else {
                aux[i] = (String) matriz[derecha];
                derecha++;
                i++;
            }
        }

        for (int j = derecha; j <= fin; j++) {
            aux[i] = (String) matriz[j];
            i++;
        }

        for (int j = izquierda; j <= medio; j++) {
            aux[i] = (String) matriz[j];
            i++;
        }

        for (int j = 0; j < i; j++) {
            matriz[ini + j] = (E) aux[j];
        }

        return matriz;
    }

    private E[] mergeSortStringDescendente(E[] matriz, Integer ini, Integer fin) {
        if (ini < fin) {
            Integer medio = ((ini + fin) / 2);
            mergeSortStringDescendente(matriz, ini, medio);
            mergeSortStringDescendente(matriz, (medio + 1), fin);
            mezclarStringDescendente(matriz, ini, medio, fin);
        }
        return matriz;
    }

    private E[] mezclarStringDescendente(E[] matriz, Integer ini, Integer medio, Integer fin) {
        Integer izquierda = ini;
        Integer derecha = medio + 1;
        Integer i = 0;
        String[] aux = new String[matriz.length];
        while (izquierda <= medio && derecha <= fin) {
            if (((String) matriz[izquierda]).compareToIgnoreCase((String) matriz[derecha]) > 0) {
                aux[i] = (String) matriz[izquierda];
                izquierda++;
                i++;
            } else {
                aux[i] = (String) matriz[derecha];
                derecha++;
                i++;
            }
        }

        for (int j = derecha; j <= fin; j++) {
            aux[i] = (String) matriz[j];
            i++;
        }

        for (int j = izquierda; j <= medio; j++) {
            aux[i] = (String) matriz[j];
            i++;
        }

        for (int j = 0; j < i; j++) {
            matriz[ini + j] = (E) aux[j];
        }

        return matriz;
    }
}
