package control;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.ModuloEditorial;

public class CtrlEditorial implements ActionListener{
    
    private Editorial edit;
    private ConsultasEditorial consultas; 
    private ModuloEditorial modulo;
    private DefaultTableModel modelo;
    private List<Editorial> lstEdit;
    
    public  CtrlEditorial(Editorial edit, ConsultasEditorial consultas, ModuloEditorial modulo){
        this.edit = edit;
        this.consultas = consultas;
        this.modulo = modulo;
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
        this.modulo.btnEditar.addActionListener(this);
        this.modulo.btnActualizar.addActionListener(this);
        
    }
    
        //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Editoriales");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
        //Consultar lista
        listar();
        //Desactivar botones
        modulo.btnActualizar.setEnabled(false);
        modulo.btnEliminar.setEnabled(false);
        
    }
    
    public void limpiar () {
        modulo.txtId.setText(null);
        modulo.txtNombre.setText(null);
        modulo.txtPais.setText(null);
    }
    
            //Listar en una tabla.
    public void listar (){
        //Se asigna la tabla de la vista al modelo.
        modelo =  (DefaultTableModel)modulo.tablaEditorial.getModel();
        lstEdit = consultas.consultar();
        //Arrays de objetos con la cantidad de columnas a traer de la tabla
        Object[] object = new Object[3];
       
        for (int i = 0; i < lstEdit.size(); i++) {
            object[0] = lstEdit.get(i).getId_editorial();
            object[1] = lstEdit.get(i).getNombre();
            object[2] = lstEdit.get(i).getPais();
            modelo.addRow(object);
        }
        //Paso del modelo a la vista
        modulo.tablaEditorial.setModel(modelo);
    }
    
    public void activarBotones(boolean estado) {
        if (estado) {
            modulo.txtId.setEditable(true);
            modulo.btnGuardar.setEnabled(true);
            modulo.btnActualizar.setEnabled(false);
            modulo.btnEliminar.setEnabled(false);
        } else {
            modulo.txtId.setEditable(false);
            modulo.btnGuardar.setEnabled(false);
            modulo.btnActualizar.setEnabled(true);
            modulo.btnEliminar.setEnabled(true);
        }
    }
    
    public void limpiarTabla() {
        for (int i = 0; i < modulo.tablaEditorial.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
    
    public void ajustar () {
        limpiarTabla();
        limpiar();
        listar();
    }
        
        //Metodo para validar que botón se presiona
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto editorial
        if (e.getSource() == modulo.btnGuardar) {
            //Validar campo númerico para idEditorial
            try {
            Integer.parseInt(modulo.txtId.getText());
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(null, "Por favor inserte un valor númerico para el ID Editorial");
                limpiar();
            }
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            edit.setNombre(modulo.txtNombre.getText());
            edit.setPais(modulo.txtPais.getText());
            //Consulta para almacenar los datos del editorial en base de datos
            if (consultas.registrar(edit)) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
                ajustar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
                limpiar();
            }
        }
        
        if (e.getSource() == modulo.btnActualizar) {
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            edit.setNombre(modulo.txtNombre.getText());
            edit.setPais(modulo.txtPais.getText());
            //Consulta para almacenar los datos del genero en base de datos
            if (consultas.modificar(edit)) {
                JOptionPane.showMessageDialog(null, "Registro actualizado");
                activarBotones(true);
                ajustar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar");
                limpiar();
            }
        }        
        
        //Botón para limpiar texto
        if (e.getSource() == modulo.btnLimpiar) {
            activarBotones(true);
            limpiar();
        }
        //Botón para eliminar
        if (e.getSource() == modulo.btnEliminar) {
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(edit)) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                activarBotones(true);
                ajustar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
                limpiar();
            }
        }

        //Botón para seleccionar y habilitar la edición de un registro
        if (e.getSource() == modulo.btnEditar){
            int fila = modulo.tablaEditorial.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(modulo, "Debe seleccionar una fila");
            } else {
                //Asignar valores de la tabla a las variables
                edit.setId_editorial(Integer.parseInt((String)modulo.tablaEditorial.getValueAt(fila,0).toString()));
                edit.setNombre((String)modulo.tablaEditorial.getValueAt(fila,1));
                edit.setPais((String)modulo.tablaEditorial.getValueAt(fila,2));
                
                //Mostrar en campos de texto las variables
                modulo.txtId.setText(Integer.toString(edit.getId_editorial()));
                modulo.txtNombre.setText(edit.getNombre());
                modulo.txtPais.setText(edit.getPais());
                activarBotones(false);
            }
        }
    }
}
