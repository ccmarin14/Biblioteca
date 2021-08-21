package control;

import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class CtrlLibro implements ActionListener{

    private Libro ejemplar;
    private ConsultasLibro consultas;
    private ConsultasEditorial consultasEdt;
    private ConsultasGenero consultasGen;
    private ConsultasAsignacion consultasAsg;
    private ModuloLibro modulo;
    private DefaultTableModel modelo;
    private List<Libro> lstLibro;

    public CtrlLibro(Libro ejemplar, ConsultasLibro consultas, ModuloLibro modulo, ConsultasEditorial consultasEdt, ConsultasGenero consultasGen, ConsultasAsignacion consultasAsg) {
        this.ejemplar = ejemplar;
        this.consultas = consultas;
        this.modulo = modulo;
        this.consultasEdt = consultasEdt;
        this.consultasGen = consultasGen;
        this.consultasAsg = consultasAsg;
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
        this.modulo.btnActualizar.addActionListener(this);
        this.modulo.btnEditar.addActionListener(this);
        this.modulo.btnEditarEditoriales.addActionListener(this);
        this.modulo.btnEditarGeneros.addActionListener(this);
        this.modulo.btnActEdit.addActionListener(this);
        this.modulo.btnActGen.addActionListener(this);
    }
    
    //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Libros");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
        //Consultar lista
        listar();
        //Desactivar botones
        modulo.btnActualizar.setEnabled(false);
        modulo.btnEliminar.setEnabled(false);
    }
    
    //Listar en una tabla.
    public void listar (){
        //Se asigna la tabla de la vista al modelo.
        modelo =  (DefaultTableModel)modulo.tblLibro.getModel();
        lstLibro = consultas.consultar();
        //Arrays de objetos con la cantidad de columnas a traer de la tabla
        Object[] object = new Object[7];
       
        for (int i = 0; i < lstLibro.size(); i++) {

            object[0] = lstLibro.get(i).getIsbn();
            object[1] = lstLibro.get(i).getNombre();
            object[2] = lstLibro.get(i).getAutor();
            object[3] = consultas.importarGeneros(lstLibro.get(i).getIsbn());
            object[4] = consultas.importarEditorial(lstLibro.get(i).getN_editorial());
            object[5] = lstLibro.get(i).getCalificacion();
            object[6] = lstLibro.get(i).getCantidad();

            modelo.addRow(object);
        }        
        //Paso del modelo a la vista
        modulo.tblLibro.setModel(modelo);
        
        //Listar editoriales
        modulo.listEditorial.removeAllItems();
        List<Editorial> lstEditorial = consultasEdt.consultar();
        for (int i = 0; i < lstEditorial.size(); i++){
            modulo.listEditorial.insertItemAt(lstEditorial.get(i).getNombre(), i);
        }
        /*modulo.listGenero.setSelectedIndex(0);*/
        //listar generos
        List<Genero> lstGenero = consultasGen.consultar();
        String[] generos = new String[lstGenero.size()];
        for (int i = 0; i < lstGenero.size(); i++){
            generos[i] = lstGenero.get(i).getNombre();
        }
        modulo.listGenero.setListData(generos);
        
    }
        
    //Limpiar campos texto
        public void limpiar () {
            modulo.txtISBN.setText(null);
            modulo.txtNombre.setText(null);
            modulo.txtCalificacion.setText(null);
            modulo.txtCantidad.setText(null);
            modulo.txtAutor.setText(null);
            modulo.txtISBN.setEditable(true);
        }
    
        public void activarBotones(boolean estado) {
        if (estado) {
            modulo.txtISBN.setEditable(true);
            modulo.btnGuardar.setEnabled(true);
            modulo.btnActualizar.setEnabled(false);
            modulo.btnEliminar.setEnabled(false);
        } else {
            modulo.txtISBN.setEditable(false);
            modulo.btnGuardar.setEnabled(false);
            modulo.btnActualizar.setEnabled(true);
            modulo.btnEliminar.setEnabled(true);
        }
    }
        
   public void limpiarTabla() {
        for (int i = 0; i < modulo.tblLibro.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
        
    //Consolidación de metodos
    public void ajustar () {
        limpiarTabla();
        limpiar();
        listar();
    }
        
    //Metodo para validar que botón se presiona
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto Libro

        if (e.getSource() == modulo.btnGuardar) {
            //Validaciones de campos
            try {
            Long.parseLong(modulo.txtISBN.getText());
            Float.parseFloat(modulo.txtCalificacion.getText());
            Integer.parseInt(modulo.txtCantidad.getText());
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(null, "Por favor verifique que los campos: ISBN, Calificación o Cantidad sean númericos");
                limpiar();
            }
            
            //Asignar valores a los atributos del objeto
            ejemplar.setIsbn(Long.parseLong(modulo.txtISBN.getText())); 
            ejemplar.setNombre(modulo.txtNombre.getText());
            ejemplar.setAutor(modulo.txtAutor.getText());
            ejemplar.setN_editorial(consultasEdt.exportarEditorial((String) modulo.listEditorial.getSelectedItem()));
            //Lista de generos seleccionados
            List<String> generos = modulo.listGenero.getSelectedValuesList();
            ejemplar.setCalificacion(Float.parseFloat(modulo.txtCalificacion.getText()));
            ejemplar.setCantidad(Integer.parseInt(modulo.txtCantidad.getText()));
            //Consulta para almacenar los datos del Libro en base de datos
            if (consultas.registrar(ejemplar, generos, consultasAsg, consultasGen)) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
                ajustar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
                limpiar();
            }
        }
        //Botón para limpiar texto
        if (e.getSource() == modulo.btnLimpiar) {
           limpiar();
           activarBotones(true);
        }
       
        //Botón para eliminar
        if (e.getSource() == modulo.btnEliminar) {
            ejemplar.setIsbn(Long.parseLong(modulo.txtISBN.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(Long.parseLong(modulo.txtISBN.getText())) && consultasAsg.eliminar(Long.parseLong(modulo.txtISBN.getText()))) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                ajustar();
                activarBotones(true);
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
                limpiar();
            }
        }
        
        // Botón para actualizar datos de texto en objeto genero
        if (e.getSource() == modulo.btnActualizar) {
            ejemplar.setIsbn(Long.parseLong(modulo.txtISBN.getText()));
            ejemplar.setNombre(modulo.txtNombre.getText());
            ejemplar.setAutor(modulo.txtAutor.getText());
            ejemplar.setN_editorial(consultasEdt.exportarEditorial((String) modulo.listEditorial.getSelectedItem()));
            //Lista de generos seleccionados
            List<String> generos = modulo.listGenero.getSelectedValuesList();
            ejemplar.setCalificacion(Float.parseFloat(modulo.txtCalificacion.getText()));
            ejemplar.setCantidad(Integer.parseInt(modulo.txtCantidad.getText()));
            //Consulta para almacenar los datos del Libro en base de datos
            if (consultas.modificar(ejemplar, generos, consultasAsg, consultasGen)) {
                JOptionPane.showMessageDialog(null, "Registro actualizado guardado");
                ajustar();
                activarBotones(true);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar");
                limpiar();
            }
        }
        
        //Botón para seleccionar y habilitar la edición de un registro
        if (e.getSource() == modulo.btnEditar){
            int fila = modulo.tblLibro.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(modulo, "Debe seleccionar una fila");
            } else {
                //Asignar valores de la tabla a las variables
                ejemplar.setIsbn(Long.parseLong((String)modulo.tblLibro.getValueAt(fila,0).toString()));
                ejemplar.setNombre((String)modulo.tblLibro.getValueAt(fila,1));
                ejemplar.setAutor((String)modulo.tblLibro.getValueAt(fila,2));
                ejemplar.setCalificacion(Float.parseFloat((String)modulo.tblLibro.getValueAt(fila,5).toString()));
                ejemplar.setCantidad(Integer.parseInt((String)modulo.tblLibro.getValueAt(fila,6).toString()));

                //Mostrar en campos de texto las variables
                modulo.txtISBN.setText(Long.toString(ejemplar.getIsbn()));
                modulo.txtNombre.setText(ejemplar.getNombre());
                modulo.txtAutor.setText(ejemplar.getAutor());
                modulo.txtCalificacion.setText(Float.toString(ejemplar.getCalificacion()));
                modulo.txtCantidad.setText(Integer.toString(ejemplar.getCantidad()));
                
                //Asignación de valores a campos especiales
                modulo.listEditorial.setSelectedItem((String)modulo.tblLibro.getValueAt(fila, 4));
                               
                activarBotones(false);
            }
        }
        //Botón para editar el genero
        if (e.getSource() == modulo.btnEditarGeneros){
            Genero gen = new Genero();
            ConsultasGenero cGen = new ConsultasGenero();
            ModuloGenero mGen = new ModuloGenero();
            CtrlGenero ctrlGen = new CtrlGenero(gen, cGen, mGen);

            ctrlGen.iniciar();
            mGen.setVisible(true);
        }
        
        //Botón para editar la editorial
        if (e.getSource() == modulo.btnEditarEditoriales){
            Editorial edit = new Editorial();
            ConsultasEditorial cEdit = new ConsultasEditorial();
            ModuloEditorial mEdit = new ModuloEditorial();
            CtrlEditorial ctrlEdit = new CtrlEditorial(edit, cEdit, mEdit);
            
            ctrlEdit.iniciar();
            mEdit.setVisible(true);
        }
        
        //Botón para actualizar lista editorial o lista genero
        if (e.getSource() == modulo.btnActEdit || e.getSource() == modulo.btnActGen){
            
            ajustar();
        }
    } 
}
