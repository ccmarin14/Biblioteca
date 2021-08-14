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
        
    }
    
    //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Generos");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
        listar(modulo.tableGenero);
    }
        
    //Limpiar texto
    public void limpiar () {
        modulo.txtId.setText(null);
        modulo.txtNombre.setText(null);
    }

    //Listar en una tabla.
    public void listar (JTable tabla){
        //Se asigna la tabla de la vista al modelo.
        modelo =  (DefaultTableModel)tabla.getModel();
        lstGen = consultas.consultar();

        Object[]object = new Object[2];
        for (int i = 0; i < lstGen.size(); i++) {
            object[0] = lstGen.get(i).getId_genero();
            object[1] = lstGen.get(i).getNombre();
            modelo.addRow(object);
        }
        //Paso del modelo a la vista
        modulo.tableGenero.setModel(modelo);
    }
    
    //Metodo para validar que botón se presiona
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón para guardar datos de texto en objeto genero
        if (e.getSource() == modulo.btnGuardar) {
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            gen.setNombre(modulo.txtNombre.getText());
            //Consulta para almacenar los datos del genero en base de datos
            if (consultas.registrar(gen)) {
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
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.eliminar(gen)) {
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
                limpiar();
            }
        }
        //
        if (e.getSource() == modulo.btnEditar){
            
        }
    } 
    
}
