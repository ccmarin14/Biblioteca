package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasLibro;
import modelo.Libro;
import vista.ModuloLibro;

public class CtrlLibro implements ActionListener{

    private Libro ejemplar;
    private ConsultasLibro consultas;
    private ModuloLibro modulo;

    public CtrlLibro(Libro ejemplar, ConsultasLibro consultas, ModuloLibro modulo) {
        this.ejemplar = ejemplar;
        this.consultas = consultas;
        this.modulo = modulo;
        this.modulo.btnConsultar.addActionListener(this);
        this.modulo.btnEliminar.addActionListener(this);
        this.modulo.btnGuardar.addActionListener(this);
        this.modulo.btnLimpiar.addActionListener(this);
        this.modulo.btnActualizar.addActionListener(this);
        this.modulo.btnRegistrar.addActionListener(this);
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
        if (e.getSource() == modulo.btnConsultar) {
            ejemplar.setIsbn(Integer.parseInt(modulo.txtISBN.getText()));
            //Consulta para eliminar el registro
            if (consultas.consultar(ejemplar)) {
                modulo.txtNombre.setText(ejemplar.getNombre());
            } else {
                JOptionPane.showMessageDialog(null, "Error al consultar");
                limpiar();
            }            
        }
    } 
    
}
