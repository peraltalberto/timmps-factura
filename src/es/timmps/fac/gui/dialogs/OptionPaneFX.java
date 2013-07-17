/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.dialogs;

import es.timmps.fac.gui.controlers.dialogs.OptionPaneController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author aperalta
 */
public class OptionPaneFX {
   
    public static void showMesage(String msg){
        try {
            
            Stage dialog = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(OptionPaneFX.class.getResource("/es/timmps/fac/gui/dialogs/AlertDialog.fxml"));
            Parent  root = (Parent) fxmlLoader.load();
            
             OptionPaneController p = fxmlLoader.getController();
             
                
                 
    dialog.initStyle(StageStyle.UTILITY);
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    root.setUserData(p);
             p.setMensage("", "");
    dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(OptionPaneFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
