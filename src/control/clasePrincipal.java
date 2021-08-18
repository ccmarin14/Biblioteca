
package control;

import modelo.*;
import vista.*;

public class clasePrincipal {

    public static void main(String[] args) {
        
        Libro ejemplar = new Libro();
        ConsultasLibro cEjemplar = new ConsultasLibro();
        ModuloLibro mEjemplar = new ModuloLibro();
        
        CtrlLibro ctrlLibro = new CtrlLibro(ejemplar, cEjemplar, mEjemplar);
        ctrlLibro.iniciar();
        mEjemplar.setVisible(true);
    } 
}
