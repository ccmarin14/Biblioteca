
package control;

import modelo.*;
import vista.*;

public class clasePrincipal {

    public static void main(String[] args) {
        
        Genero gen = new Genero();
        ConsultasGenero cGen = new ConsultasGenero();
        ModuloGenero mGen = new ModuloGenero();
        
        CtrlGenero ctrlGen = new CtrlGenero(gen, cGen, mGen);
        ctrlGen.iniciar();
        mGen.setVisible(true);
    }
    
}
