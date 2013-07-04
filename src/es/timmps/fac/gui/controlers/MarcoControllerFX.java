/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author aperalta
 */
public class MarcoControllerFX implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
        for(String st : rb.keySet()){
            System.out.println(st);
        }
        }catch(Exception e){
            //e.printStackTrace();
        }
    }    
}
