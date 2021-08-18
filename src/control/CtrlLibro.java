package control;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.ModuloLibro;

public class CtrlLibro implements ActionListener{

    private Libro ejemplar;
    private ConsultasLibro consultas;
    private ModuloLibro modulo;
    private DefaultTableModel modelo;
    private List<Genero> lstLibro;

    public CtrlLibro(Libro ejemplar, ConsultasLibro consultas, ModuloLibro modulo) {
        this.ejemplar = ejemplar;
        this.consultas = consultas;
        this.modulo = modulo;
        this.modulo.btnConsultar.addActionListener(this);
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
        this.modulo.btnActualizar.addActionListener(this);
        this.modulo.btnEditar.addActionListener(this);
        this.modulo.btnEditarEditoriales.addActionListener(this);
        this.modulo.btnEditarrGeneros.addActionListener(this);
    }
    
    //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Libros");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
    }
        
    //Limpiar texto
    public void limpiar () {
        modulo.txtISBN.setText(null);
        modulo.txtNombre.setText(null);
    }

    //Metodo para validar que botón se presiona
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto Libro
        if (e.getSource() == modulo.btnGuardar) {
            ejemplar.setIsbn(Integer.parseInt(modulo.txtISBN.getText()));
            ejemplar.setNombre(modulo.txtNombre.getText());
            //Consulta para almacenar los datos del Libro en base de datos
            if (consultas.registrar(ejemplar)) {
                JOptionPane.showMessageDialog(null, "Registro guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
                limpiar();
            }
        }
        //Botón para limpiar texto
        if (e.getSource() == modulo.btnLimpiar) {
           limpiar();
        }
        //Botón para eliminar
        if (e.getSource() == modulo.btnEliminar) {
            ejemplar.setIsbn(Integer.parseInt(modulo.txtISBN.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(ejemplar)) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
                limpiar();
            }
        }
        //Botón para consultar
        /*if (e.getSource() == modulo.btnConsultar) {
            ejemplar.setIsbn(Integer.parseInt(modulo.txtISBN.getText()));
            //Consulta para eliminar el registro
            if (consultas.consultar()) {
                modulo.txtNombre.setText(ejemplar.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "Error al consultar");
                limpiar();
            }            
        }*/
                //Botón para seleccionar y habilitar la edición de un registro
        if (e.getSource() == modulo.btnEditar){
            int fila = modulo.tblLibro.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(modulo, "Debe seleccionar una fila");
            } else {
                //Asignar valores de la tabla a las variables
                ejemplar.setIsbn(Integer.parseInt((String)modulo.tblLibro.getValueAt(fila,0).toString()));
                ejemplar.setNombre((String)modulo.tblLibro.getValueAt(fila,1));
                
                //Mostrar en campos de texto las variables
                modulo.txtISBN.setText(Integer.toString(ejemplar.getIsbn()));
                modulo.txtNombre.setText(ejemplar.getNombre());
                
                activarBotones(false);
            }
        }
    } 
    
}
