package control;

import java.awt.event.ActionListener;
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
        
    }
    

    
}
