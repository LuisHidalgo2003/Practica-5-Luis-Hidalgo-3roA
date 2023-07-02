package controlador.Dao;

import busqueda.modelo.Cliente;
import controlador.listas.ListaEnlazada;
import java.io.IOException;

public class ClienteDao extends AdaptadorDAO<Cliente>{
    private Cliente cliente;
    
    public ClienteDao() {
        super(Cliente.class);
    }

    public Cliente getCliente() {
        if(this.cliente == null){
            this.cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void guardar() throws IOException {
        this.guardar(cliente);
    }

    public void modificar(Integer pos) throws IOException {
        this.modificar(cliente, pos);
    }

    public ListaEnlazada<Cliente> listar(){
        return super.listar();        
    }
}
