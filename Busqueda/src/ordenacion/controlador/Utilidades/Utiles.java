package ordenacion.controlador.Utilidades;

import java.lang.reflect.Field;

import ordenacion.modelo.AbecedarioMinusculas;

public class Utiles {
    public Boolean termino;

    public Boolean getTermino() {
        return termino;
    }

    public void setTermino(Boolean termino) {
        this.termino = termino;
    }
    
    public static Integer[] valorLetrasString(String cadena) {
        char arrayCadena[] = cadena.toLowerCase().toCharArray();
        Integer a[] = new Integer[arrayCadena.length];
        for (int i = 0; i < arrayCadena.length; i++) {
            for (AbecedarioMinusculas letra : AbecedarioMinusculas.values()) {
                if (arrayCadena[i] == letra.toString().charAt(0)) {
                    a[i] = letra.getValor();
                }
            }
        }
        return a;
    }
    
    public static Integer valorCharacter(Character caracter) {
        Integer valor = null;
        for (AbecedarioMinusculas letra : AbecedarioMinusculas.values()) {
            if (caracter.toLowerCase(caracter) == letra.toString().charAt(0)) {
                valor = letra.getValor();
            }
        }
        return valor;
    }
    
    public static Boolean isNumber(Class clazz){
        return clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number");
    }
    
    public static Boolean isString(Class clazz){
        return clazz.getSimpleName().equalsIgnoreCase("String");
    }
    
    public static Boolean isCharacter(Class clazz){
        return clazz.getSimpleName().equalsIgnoreCase("Character");
    }
    
    public static Boolean isBoolean(Class clazz){
        return clazz.getSimpleName().equalsIgnoreCase("Boolean");
    }
    
    public static Boolean isObject(Class clazz){
        return (!isBoolean(clazz) && !isCharacter(clazz) && !isNumber(clazz) && !isString(clazz) && !isPrimitive(clazz));
    }
    
    public static Boolean isPrimitive(Class clazz){
        return clazz.isPrimitive();
    }
    
    public static Field obtenerAtributos(Class clazz, String nombre){
        Field atributo = null;
        for (Field aux:clazz.getDeclaredFields()) {
            if(nombre.equalsIgnoreCase(aux.getName())){
                atributo = aux;
                break;
            }
        }
        return atributo;
    }   
}
