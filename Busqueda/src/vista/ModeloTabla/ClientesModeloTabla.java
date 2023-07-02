package vista.ModeloTabla;

import busqueda.modelo.Cliente;
import controlador.listas.Exepciones.ListaVaciaException;
import controlador.listas.Exepciones.PosicionNoEncontradaException;
import controlador.listas.ListaEnlazada;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class ClientesModeloTabla extends AbstractTableModel{
    private ListaEnlazada<Cliente> lista = new ListaEnlazada<>();

    public ListaEnlazada<Cliente> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Cliente> lista) {
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
            case 0: return "Nombre";
            case 1: return "Apellido";
            default: return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = null;
        try {
            c = lista.obtener(rowIndex);
        } catch (ListaVaciaException ex) {
            Logger.getLogger(ClientesModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PosicionNoEncontradaException ex) {
            Logger.getLogger(ClientesModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(columnIndex){
            case 0:
                return (c != null) ? c.getNombre() : "No definido";
            case 1:
                return (c != null) ? c.getApellido() : "No definido";
            default:
                return null;
        }
    }
}
