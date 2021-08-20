
package control;

import modelo.*;
import vista.*;

public class clasePrincipal {

    public static void main(String[] args) {
        
        Libro ejemplar = new Libro();
        ConsultasLibro cEjemplar = new ConsultasLibro();
        ModuloLibro mEjemplar = new ModuloLibro();
        ConsultasEditorial cEdit = new ConsultasEditorial();
        ConsultasGenero cGen = new ConsultasGenero();
        ConsultasAsignacion cAsg = new ConsultasAsignacion();
        
        CtrlLibro ctrlLibro = new CtrlLibro(ejemplar, cEjemplar, mEjemplar, cEdit, cGen, cAsg);
        ctrlLibro.iniciar();
        mEjemplar.setVisible(true);
    } 
}
