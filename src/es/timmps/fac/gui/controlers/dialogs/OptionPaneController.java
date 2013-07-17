/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.controlers.dialogs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author aperalta
 */
public class OptionPaneController implements Initializable {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button actionButton;

    @FXML
    private HBox actionParent;

    @FXML
    private Button cancelButton;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button okButton;

    @FXML
    private HBox okParent;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
         assert actionButton != null : "fx:id=\"actionButton\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert actionParent != null : "fx:id=\"actionParent\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert detailsLabel != null : "fx:id=\"detailsLabel\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'AlertDialog.fxml'.";
        assert okParent != null : "fx:id=\"okParent\" was not injected: check your FXML file 'AlertDialog.fxml'.";
    }

    public void setMensage(String msg, String mms){
        this.messageLabel.setText(msg);
        this.detailsLabel.setText(mms);
    }
}
  

