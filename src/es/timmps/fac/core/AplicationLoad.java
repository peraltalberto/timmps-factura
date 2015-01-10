/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.core;

import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBoxBuilder;

/**
 *
 * @author aperalta
 */
public class AplicationLoad {
    
    public AplicationLoad(){
    
    }
    
    public void addAdministracion(Accordion menu){
       TitledPane panel = new TitledPane();
       panel.setText("Administración");
       panel.setContent(VBoxBuilder.create().id("pm")
               .children(ButtonBuilder.create()
               .text("Usuarios").build()).build());
       
        menu.getPanes().add(panel);
        
    }
    
    
}
