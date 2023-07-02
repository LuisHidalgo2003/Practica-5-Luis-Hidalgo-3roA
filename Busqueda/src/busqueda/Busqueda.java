package busqueda;

import busqueda.modelo.AlquilerLibro;
import busqueda.modelo.Libro;
import controlador.Dao.LibroDao;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Busqueda {

    public static void main(String[] args) {
        /*Libro libro = new Libro();
        libro.setTitulo("the mobi dig");
        
        LibroDao librodao = new LibroDao();
        librodao.setLibro(libro);
        try {
            librodao.guardar();
        } catch (IOException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        librodao.listar();*/
        
        LocalDate fecha = LocalDate.now();
        LocalDate modificada = fecha.plusDays(15);
        System.out.println(fecha);
        System.out.println(modificada);
    }
    
}
