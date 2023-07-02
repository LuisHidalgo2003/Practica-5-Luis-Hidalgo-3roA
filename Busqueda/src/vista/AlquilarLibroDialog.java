package vista;

import busqueda.controlador.Binaria.BusquedaBinaria;
import busqueda.controlador.LinealBinaria.BusquedaLinealBinaria;
import busqueda.modelo.AlquilerLibro;
import busqueda.modelo.Cliente;
import busqueda.modelo.Libro;
import controlador.Dao.AlquilerLibroDao;
import controlador.Dao.ClienteDao;
import controlador.Dao.LibroDao;
import controlador.listas.Exepciones.ListaVaciaException;
import controlador.listas.Exepciones.PosicionNoEncontradaException;
import controlador.listas.ListaEnlazada;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ordenacion.Excepciones.AtributoNoEncontradoException;
import ordenacion.controlador.MergeSort.MetodoMergeSort;
import vista.ModeloTabla.ClientesModeloTabla;
import vista.ModeloTabla.LibrosTableModel;

public class AlquilarLibroDialog extends javax.swing.JDialog {
    private ListaEnlazada<Cliente> clientes = new ListaEnlazada<>();
    private ClienteDao clienteDao = new ClienteDao();
    private ListaEnlazada<Libro> libros = new ListaEnlazada<>();
    private LibroDao libroDao = new LibroDao();
    private ListaEnlazada<AlquilerLibro> alquileres = new ListaEnlazada<>();
    private AlquilerLibroDao alquilerDao = new AlquilerLibroDao();
    private ClientesModeloTabla cmt = new ClientesModeloTabla();
    private LibrosTableModel ltm = new LibrosTableModel();
    private BusquedaBinaria bb = new BusquedaBinaria<>();
    private BusquedaLinealBinaria blb = new BusquedaLinealBinaria();
    private ListaEnlazada<Integer> posiciones = new ListaEnlazada<>();
    private MetodoMergeSort mms = new MetodoMergeSort();

    /**
     * Creates new form AlquilarLibroDialog
     */
    public AlquilarLibroDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarClientesLista();
        cargarLibrosLista();
        cargarAlquileresLista();
        cargarClientesTabla(clientes);
        cargarLibrosTabla(libros);
        try {
            cargarAlquileresTabla();
        } catch (ListaVaciaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PosicionNoEncontradaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarAlquileresLista(){
        this.alquileres = alquilerDao.listar();
    }
    
    private void cargarClientesLista(){
        this.clientes = clienteDao.listar();
    }
    
    private void cargarLibrosLista(){
        this.libros = libroDao.listar();
    }
    
    private void cargarClientesTabla(ListaEnlazada<Cliente> lista){
        cmt.setLista(lista);
        tblClientes.setModel(cmt);
        tblClientes.updateUI();
    }
    
    private void cargarLibrosTabla(ListaEnlazada<Libro> lista){
        ltm.setLista(lista);
        tblLibros.setModel(ltm);
        tblLibros.updateUI();
    }
    
    private void cargarAlquileresTabla() throws ListaVaciaException, PosicionNoEncontradaException{
        String[] data = new String[alquileres.getTamanio()];
        for (int i = 0; i < alquileres.getTamanio(); i++) {
            data[i] = alquileres.obtener(i).toString();
        }
        listAlquileres.setListData(data);
        listAlquileres.updateUI();
    }
    
    private void buscar(){
        if (!txtBusquedaClientes.getText().isEmpty()) {
            try {
                buscarClientes();
            } catch (AtributoNoEncontradoException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ListaVaciaException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PosicionNoEncontradaException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                buscarLibros();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AtributoNoEncontradoException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ListaVaciaException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PosicionNoEncontradaException ex) {
                Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void buscarClientes() throws AtributoNoEncontradoException, IllegalArgumentException, IllegalAccessException, ListaVaciaException, PosicionNoEncontradaException {
        ListaEnlazada<Cliente> temporal = new ListaEnlazada<>();
        if (rbtnCedula.isSelected()) {
            clientes = mms.mergeSort(clientes, "cedula", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(clientes, txtBusquedaClientes.getText(), "cedula");
            }else{
                temporal.insertar(clientes.obtener(bb.binaria(clientes, txtBusquedaClientes.getText(), "cedula")));
            }
        }else if(rbtnNombre.isSelected()){
            clientes = mms.mergeSort(clientes, "nombre", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(clientes, txtBusquedaClientes.getText(), "nombre");
            }else{
                temporal.insertar(clientes.obtener(bb.binaria(clientes, txtBusquedaClientes.getText(), "nombre")));
            }
        }else{
            clientes = mms.mergeSort(clientes, "apellido", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(clientes, txtBusquedaClientes.getText(), "apellido");
            }else{
                temporal.insertar(clientes.obtener(bb.binaria(clientes, txtBusquedaClientes.getText(), "apellido")));
            }
        }
        if (posiciones.getTamanio() != 0) {
            temporal = recuperarClientes();
        }
        cargarClientesTabla(temporal);
    }
    
    private ListaEnlazada<Cliente> recuperarClientes() throws ListaVaciaException, PosicionNoEncontradaException{
        ListaEnlazada<Cliente> temporal = new ListaEnlazada<>();
        
        for (int i = 0; i < posiciones.getTamanio(); i++) {
            temporal.insertar(clientes.obtener(posiciones.obtener(i)));
        }
        return temporal;
    }
    
    private ListaEnlazada<Libro> recuperarLibros() throws ListaVaciaException, PosicionNoEncontradaException{
        ListaEnlazada<Libro> temporal = new ListaEnlazada<>();
        
        for (int i = 0; i < posiciones.getTamanio(); i++) {
            temporal.insertar(libros.obtener(posiciones.obtener(i)));
        }
        
        return temporal;
    }
    
    private void buscarLibros() throws IllegalAccessException, IllegalArgumentException, AtributoNoEncontradoException, ListaVaciaException, PosicionNoEncontradaException{
        ListaEnlazada<Libro> temporal = new ListaEnlazada<>();
        if(rbtnIsbn.isSelected()){
            libros = mms.mergeSort(libros, "isbn", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(libros, txtBusquedaLibros.getText(), "isbn");
            }else{
                temporal.insertar(libros.obtener(bb.binaria(libros, txtBusquedaLibros.getText(), "isbn")));
            }
        }else if(rbtnTitulo.isSelected()){
            libros = mms.mergeSort(libros, "titulo", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(libros, txtBusquedaLibros.getText(), "titulo");
            }else{
                temporal.insertar(libros.obtener(bb.binaria(libros, txtBusquedaLibros.getText(), "titulo")));
            }
        }else{
            libros = mms.mergeSort(libros, "autor", MetodoMergeSort.ASCENDENTE);
            if (rbtnLinealBinaria.isSelected()) {
                posiciones = blb.linealBinaria(libros, txtBusquedaLibros.getText(), "autor");
            }else{
                temporal.insertar(libros.obtener(bb.binaria(libros, txtBusquedaLibros.getText(), "autor")));
            }
        }
        if (posiciones.getTamanio() != 0) {
            temporal = recuperarLibros();
        }
        cargarLibrosTabla(temporal);
    }
    
    private void alquilar() throws ListaVaciaException, PosicionNoEncontradaException, IOException{
        if (tblClientes.getSelectedRow() < 0 || tblLibros.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione la información necesaria");
        }else{
            alquilerDao.getAlquiler().setFechaInicio(LocalDate.now());
            alquilerDao.getAlquiler().setFechaFin((LocalDate.now().plusDays(15)));
            alquilerDao.getAlquiler().setCliente(clientes.obtener(tblClientes.getSelectedRow()));
            alquilerDao.getAlquiler().setLibro(libros.obtener(tblLibros.getSelectedRow()));
            alquilerDao.guardar();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbtngAtributosCliente = new javax.swing.ButtonGroup();
        rbtngAtributosLibro = new javax.swing.ButtonGroup();
        rbtngTipoBusqueda = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLibros = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAlquilar = new javax.swing.JButton();
        btnAggCliente = new javax.swing.JButton();
        btnAggLibro = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBusquedaClientes = new javax.swing.JTextField();
        rbtnApellido = new javax.swing.JRadioButton();
        rbtnCedula = new javax.swing.JRadioButton();
        rbtnNombre = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBusquedaLibros = new javax.swing.JTextField();
        rbtnIsbn = new javax.swing.JRadioButton();
        rbtnTitulo = new javax.swing.JRadioButton();
        rbtnAutor = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        rbtnBinaria = new javax.swing.JRadioButton();
        rbtnLinealBinaria = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAlquileres = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Z003", 1, 15)); // NOI18N
        jLabel1.setText("ALQUILAR LIBRO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        tblLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLibros);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, 370, 300));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblClientes);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 370, 300));

        btnAlquilar.setFont(new java.awt.Font("URW Gothic", 1, 15)); // NOI18N
        btnAlquilar.setText("ALQUILAR");
        btnAlquilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlquilarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAlquilar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 630, 100, -1));

        btnAggCliente.setFont(new java.awt.Font("URW Gothic", 1, 15)); // NOI18N
        btnAggCliente.setText("AGREGAR CLIENTE");
        btnAggCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAggClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnAggCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 170, 40));

        btnAggLibro.setFont(new java.awt.Font("URW Gothic", 1, 15)); // NOI18N
        btnAggLibro.setText("AGREGAR LIBRO");
        btnAggLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAggLibroActionPerformed(evt);
            }
        });
        jPanel1.add(btnAggLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 580, 140, 40));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Clientes"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Buscar:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        jPanel2.add(txtBusquedaClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 260, -1));

        rbtngAtributosCliente.add(rbtnApellido);
        rbtnApellido.setText("Apellido");
        jPanel2.add(rbtnApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        rbtngAtributosCliente.add(rbtnCedula);
        rbtnCedula.setText("Cedula");
        jPanel2.add(rbtnCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        rbtngAtributosCliente.add(rbtnNombre);
        rbtnNombre.setText("Nombre");
        jPanel2.add(rbtnNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 370, 130));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Libros"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Buscar:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        jPanel3.add(txtBusquedaLibros, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 260, -1));

        rbtngAtributosLibro.add(rbtnIsbn);
        rbtnIsbn.setText("ISBN");
        jPanel3.add(rbtnIsbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        rbtngAtributosLibro.add(rbtnTitulo);
        rbtnTitulo.setText("Titulo");
        jPanel3.add(rbtnTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        rbtngAtributosLibro.add(rbtnAutor);
        rbtnAutor.setText("Autor");
        jPanel3.add(rbtnAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 370, 130));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de búsqueda"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtngTipoBusqueda.add(rbtnBinaria);
        rbtnBinaria.setText("Binaria");
        jPanel4.add(rbtnBinaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        rbtngTipoBusqueda.add(rbtnLinealBinaria);
        rbtnLinealBinaria.setText("Lineal Binaria");
        jPanel4.add(rbtnLinealBinaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        btnBuscar.setFont(new java.awt.Font("URW Gothic", 1, 15)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel4.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 19, 110, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 830, 80));

        btnActualizar.setFont(new java.awt.Font("Z003", 1, 15)); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 590, 120, 40));

        jLabel4.setText("Nota: sólo se prestarán libros por 15 dias");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 640, -1, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Alquileres"));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setViewportView(listAlquileres);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 370, 480));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 410, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1281, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAlquilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlquilarActionPerformed
        try {
            // TODO add your handling code here:
            alquilar();
            cargarAlquileresTabla();
        } catch (ListaVaciaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PosicionNoEncontradaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAlquilarActionPerformed

    private void btnAggClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAggClienteActionPerformed
        // TODO add your handling code here:
        new ClienteDialog(null, true);
        cargarClientesLista();
        cargarClientesTabla(clientes);
    }//GEN-LAST:event_btnAggClienteActionPerformed

    private void btnAggLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAggLibroActionPerformed
        // TODO add your handling code here:
        new LibrosDialog(null, true);
        cargarLibrosLista();
        cargarLibrosTabla(libros);
    }//GEN-LAST:event_btnAggLibroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        posiciones = new ListaEnlazada<>();
        cargarClientesLista();
        cargarLibrosLista();
        cargarAlquileresLista();
        cargarClientesTabla(clientes);
        cargarLibrosTabla(libros);
        try {
            cargarAlquileresTabla();
        } catch (ListaVaciaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PosicionNoEncontradaException ex) {
            Logger.getLogger(AlquilarLibroDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlquilarLibroDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlquilarLibroDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlquilarLibroDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlquilarLibroDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AlquilarLibroDialog dialog = new AlquilarLibroDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAggCliente;
    private javax.swing.JButton btnAggLibro;
    private javax.swing.JButton btnAlquilar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listAlquileres;
    private javax.swing.JRadioButton rbtnApellido;
    private javax.swing.JRadioButton rbtnAutor;
    private javax.swing.JRadioButton rbtnBinaria;
    private javax.swing.JRadioButton rbtnCedula;
    private javax.swing.JRadioButton rbtnIsbn;
    private javax.swing.JRadioButton rbtnLinealBinaria;
    private javax.swing.JRadioButton rbtnNombre;
    private javax.swing.JRadioButton rbtnTitulo;
    private javax.swing.ButtonGroup rbtngAtributosCliente;
    private javax.swing.ButtonGroup rbtngAtributosLibro;
    private javax.swing.ButtonGroup rbtngTipoBusqueda;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblLibros;
    private javax.swing.JTextField txtBusquedaClientes;
    private javax.swing.JTextField txtBusquedaLibros;
    // End of variables declaration//GEN-END:variables
}
