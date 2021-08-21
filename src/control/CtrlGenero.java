package control;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.ModuloGenero;

public class CtrlGenero implements ActionListener{

    private Genero gen;
    private ConsultasGenero consultas;
    private ModuloGenero modulo;
    private DefaultTableModel modelo;
    private List<Genero> lstGen;

    public CtrlGenero(Genero gen, ConsultasGenero consultas, ModuloGenero modulo) {
        this.gen = gen;
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
        modulo.setTitle("Generos");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
        //Consultar lista
        listar();
        //Desactivar botones
        modulo.btnActualizar.setEnabled(false);
        modulo.btnEliminar.setEnabled(false);
    }
        
    //Limpiar texto
    public void limpiar () {
        modulo.txtId.setText(null);
        modulo.txtNombre.setText(null);
    }

    //Listar en una tabla.
    public void listar (){
        //Se asigna la tabla de la vista al modelo.
        modelo =  (DefaultTableModel)modulo.tableGenero.getModel();
        lstGen = consultas.consultar();
        //Arrays de objetos con la cantidad de columnas a traer de la tabla
        Object[] object = new Object[2];
       
        for (int i = 0; i < lstGen.size(); i++) {
            object[0] = lstGen.get(i).getId_genero();
            object[1] = lstGen.get(i).getNombre();
            modelo.addRow(object);
        }
        //Paso del modelo a la vista
        modulo.tableGenero.setModel(modelo);
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
        for (int i = 0; i < modulo.tableGenero.getRowCount(); i++) {
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
        
    //Metodo para validar boton presionado
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto genero
        if (e.getSource() == modulo.btnGuardar) {
            //Validar campo númerico para idGenero
            try {
            Integer.parseInt(modulo.txtId.getText());
            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(null, "Por favor inserte un valor númerico para el ID Genero");
                limpiar();
            }
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            gen.setNombre(modulo.txtNombre.getText());
            //Consulta para almacenar los datos del genero en base de datos
            if (consultas.registrar(gen)) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
                ajustar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
                limpiar();
            }
        }
        // Botón para actualizar datos de texto en objeto genero
        if (e.getSource() == modulo.btnActualizar) {
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            gen.setNombre(modulo.txtNombre.getText());
            //Consulta para almacenar los datos del genero en base de datos
            if (consultas.modificar(gen)) {
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
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(gen)) {
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
            int fila = modulo.tableGenero.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(modulo, "Debe seleccionar una fila");
            } else {
                //Asignar valores de la tabla a las variables
                gen.setId_genero(Integer.parseInt((String)modulo.tableGenero.getValueAt(fila,0).toString()));
                gen.setNombre((String)modulo.tableGenero.getValueAt(fila,1));
                
                //Mostrar en campos de texto las variables
                modulo.txtId.setText(Integer.toString(gen.getId_genero()));
                modulo.txtNombre.setText(gen.getNombre());
                
                activarBotones(false);
            }
        }
    } 
}
