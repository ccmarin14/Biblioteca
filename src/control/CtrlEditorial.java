package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasEditorial;
import modelo.Editorial;
import vista.ModuloEditorial;

public class CtrlEditorial implements ActionListener{
    
    private Editorial edit;
    private ConsultasEditorial consultas; 
    private ModuloEditorial modulo;
    
    public  CtrlEditorial(Editorial edit, ConsultasEditorial consultas, ModuloEditorial modulo){
        this.edit = edit;
        this.consultas = consultas;
        this.modulo = modulo;
        this.modulo.btnConsultar.addActionListener(this);
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
        
    }
    
        //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Editoriales");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
    }
    
        public void limpiar () {
        modulo.txtId.setText(null);
        modulo.txtNombre.setText(null);
        modulo.txtPais.setText(null);
    }
        
        //Metodo para validar que botón se presiona
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto editorial
        if (e.getSource() == modulo.btnGuardar) {
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            edit.setNombre(modulo.txtNombre.getText());
            edit.setPais(modulo.txtPais.getText());
            //Consulta para almacenar los datos del editorial en base de datos
            if (consultas.registrar(edit)) {
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
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(edit)) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
                limpiar();
            }
        }
        //Botón para consultar
        if (e.getSource() == modulo.btnConsultar) {
            edit.setId_editorial(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.consultar(edit)) {
                modulo.txtNombre.setText(edit.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "Error al consultar");
                limpiar();
            }            
        }
    }

}
