package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasGenero;
import modelo.Genero;
import vista.ModuloGenero;

public class CtrlGenero implements ActionListener{

    private Genero gen;
    private ConsultasGenero consultas;
    private ModuloGenero modulo;

    public CtrlGenero(Genero gen, ConsultasGenero consultas, ModuloGenero modulo) {
        this.gen = gen;
        this.consultas = consultas;
        this.modulo = modulo;
        this.modulo.btnConsultar.addActionListener(this);
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
    }
    
    //Parametrizar vista
    public void iniciar () {
        modulo.setTitle("Generos");
        //Centrar vista
        modulo.setLocationRelativeTo(null);
    }
        
    //Limpiar texto
    public void limpiar () {
        modulo.txtId.setText(null);
        modulo.txtNombre.setText(null);
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
        //Botón para consultar
        if (e.getSource() == modulo.btnConsultar) {
            gen.setId_genero(Integer.parseInt(modulo.txtId.getText()));
            //Consulta para eliminar el registro
            if (consultas.consultar(gen)) {
                modulo.txtNombre.setText(gen.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "Error al consultar");
                limpiar();
            }            
        }
    } 
    
}
