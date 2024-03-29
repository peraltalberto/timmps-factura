/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.controlers;

import es.timmps.fac.MainContext;
import es.timmps.fac.persistencia.models.UsuariosJpaController;
import es.timmps.fac.secure.SessionControler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author aperalta
 */
public class MarcoControllerFX implements Initializable {

    @FXML
    private TabPane tab;
    @FXML
    private SplitPane sppanel;
    @FXML
    private  ProgressIndicator tabajando;
    @FXML
    private Accordion menuAplication;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       MainContext.setSession(SessionControler.createSession(MainContext.getUsuario(),MainContext.getEmpresa()));
       MainContext.getAplicationLoad().addAdministracion(menuAplication);
    }   
    
    
     
    public void clic_b(ActionEvent evt){
        System.out.println(sppanel.getDividerPositions()[0]);
        ObservableList<Tab> tabs = tab.getTabs();
        boolean singleton = true;
        for(Tab t : tabs){
            if(t.getId().equals("Admin_Usuarios")){
                singleton = false;
                SingleSelectionModel<Tab> sM = tab.getSelectionModel();
                sM.select(t);
                break;
            }
        }
        if(singleton){
            try {
                Tab t = new Tab("Admin_Usuarios");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/timmps/fac/gui/TabUsuarios.fxml"));
                        AnchorPane  rt =  (AnchorPane) fxmlLoader.load();
                        t.setContent(rt);
                tabs.add(t);
                SingleSelectionModel<Tab> sM = tab.getSelectionModel();
                    sM.select(t);
            } catch (IOException ex) {
                Logger.getLogger(MarcoControllerFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public  void trabajando(Boolean strart){
        if(strart){
            tabajando.setProgress(-1);
        }else{
            tabajando.setProgress(0);
        }
    }   
}
