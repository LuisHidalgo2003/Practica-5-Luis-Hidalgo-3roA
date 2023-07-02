package controlador.Dao;

import busqueda.modelo.AlquilerLibro;
import busqueda.modelo.Cliente;
import busqueda.modelo.Libro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controlador.listas.ListaEnlazada;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class Conexion<T> {

    private Gson gson;
    public static String URL = "data";
    private String extension = ".json";
    private String json;

    public void guardar(ListaEnlazada<T> lista) {
        String clase = lista.getCabecera().getDato().getClass().getSimpleName();
        gson = new GsonBuilder().setPrettyPrinting().create();
        switch (clase) {
            case "Cliente":
                MapeoClientes mapeoClientes = new MapeoClientes((ListaEnlazada<Cliente>) lista);
                json = gson.toJson(mapeoClientes);
                break;
            case "Libro":
                MapeoLibros mapeoLibros = new MapeoLibros((ListaEnlazada<Libro>) lista);
                json = gson.toJson(mapeoLibros);
                break;
            default:
                MapeoAlquiler mapeoAlquiler = new MapeoAlquiler((ListaEnlazada<AlquilerLibro>) lista);
                json = gson.toJson(mapeoAlquiler);
                break;
        }
        try {
            PrintWriter escritor = new PrintWriter(new File(URL + File.separatorChar + clase + extension));
            escritor.write(json);
            escritor.flush();
            escritor.close();
            JOptionPane.showMessageDialog(null, "Se guardó");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar");
            System.out.println(e);
        }
    }

    public ListaEnlazada<T> leerJson(String clazz) throws IOException {
        Reader lector = Files.newBufferedReader(Paths.get(URL + File.separatorChar + clazz + extension));
        gson = new Gson();
        switch (clazz) {
            case "Cliente":
                MapeoClientes mapeoClientes = new MapeoClientes();
                mapeoClientes = (gson.fromJson(lector, MapeoClientes.class));
                return (ListaEnlazada<T>) mapeoClientes.getLista();
            case "Libro":
                MapeoLibros mapeoLibros = new MapeoLibros();
                mapeoLibros = (gson.fromJson(lector, MapeoLibros.class));
                return (ListaEnlazada<T>) mapeoLibros.getLista();
            default:
                MapeoAlquiler mapeoAlquiler = new MapeoAlquiler();
                mapeoAlquiler = (gson.fromJson(lector, MapeoAlquiler.class));
                return (ListaEnlazada<T>) mapeoAlquiler.getLista();
        }
    }
}
