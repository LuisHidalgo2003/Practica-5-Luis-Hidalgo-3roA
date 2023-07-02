package vista.ModeloTabla;

import busqueda.modelo.Libro;
import controlador.listas.Exepciones.ListaVaciaException;
import controlador.listas.Exepciones.PosicionNoEncontradaException;
import controlador.listas.ListaEnlazada;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class LibrosTableModel extends AbstractTableModel{
    private ListaEnlazada<Libro> lista = new ListaEnlazada<>();

    public ListaEnlazada<Libro> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Libro> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getColumnCount(){
        return 2;
    }
    
    @Override 
    public int getRowCount(){
        return lista.getTamanio();
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "Titulo";
            case 1: return "Autor";
            default: return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro l = null;
        try {
            l = lista.obtener(rowIndex);
        } catch (ListaVaciaException ex) {
            Logger.getLogger(LibrosTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PosicionNoEncontradaException ex) {
            Logger.getLogger(LibrosTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(columnIndex){
            case 0:
                return (l != null) ? l.getTitulo(): "No definido";
            case 1:
                return (l != null) ? l.getAutor(): "No definido";
            default:
                return null;
        }
    }
}
