package ordenacion.Excepciones;

public class AtributoNoEncontradoException extends Exception{

    public AtributoNoEncontradoException(String string) {
        super(string);
    }

    public AtributoNoEncontradoException() {
        super("No se puede encontrar el atributo");
    }
    
    
}
