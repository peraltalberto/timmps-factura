/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.components;

import es.timmps.fac.persistencia.Aplicaciones;
import javafx.scene.control.Button;

/**
 *
 * @author aperalta
 */
public class ButtonAplicacion extends Button {
    
    Aplicaciones aplicacion = null;

    public Aplicaciones getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicaciones aplicacion) {
        this.aplicacion = aplicacion;
    }

    public ButtonAplicacion(Aplicaciones ap) {
        this.aplicacion = ap;
    }

    
    
    
    
}
