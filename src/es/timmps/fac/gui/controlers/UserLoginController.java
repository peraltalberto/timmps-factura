/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.controlers;

import es.timmps.fac.MainContext;
import es.timmps.fac.persistencia.CfGlobal;
import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.persistencia.models.exceptions.PreexistingEntityException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.plugin2.ipc.windows.WindowsEvent;

/**
 * FXML Controller class
 *
 * @author aperalta
 */
public class UserLoginController implements Initializable {

    
    
    @FXML
    ComboBox cbEmpresas;
    @FXML
    TextField txUser;
   @FXML
    PasswordField  txPass;  
    @FXML
    ProgressBar pbUser;
    @FXML
    Label txError;
     @FXML
    private AnchorPane rootP;
    
    
    CfGlobal empDef;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       pbUser.setProgress(-1);
       cbEmpresas.getItems().clear();
       cbEmpresas.setItems(FXCollections.observableList(MainContext.getEmpresas().findEmpresasEntities()));
       try{
            empDef =   MainContext.getGlobales().findCfGlobal("EmpresaDef");
            cbEmpresas.getSelectionModel().select(MainContext.getEmpresas().findEmpresas(empDef.getValor()));
        }catch(NullPointerException e){
           try {
               empDef = new CfGlobal();
               empDef.setCodigo("EmpresaDef");
               empDef.setDescripcion("Empresa por defecto en el arranque");
               empDef.setTipoDato(MainContext.getT_datos().findCfTipoValores(1));
               empDef.setValor("");
               MainContext.getGlobales().create(empDef);
           } catch (PreexistingEntityException ex) {
               Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (Exception ex) {
               Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        }
    }    
    public void cancel(){
        System.exit(0);
    }
    public void aceptar(){
       
        try{
            
         Usuarios usu = MainContext.getUsuarios().findUsuarios(this.txUser.getText());
        if(txPass.getText().equals(usu.getPassword())){
            if(!usu.getEmpresasCollection().contains(cbEmpresas.getSelectionModel().getSelectedItem())){
                txError.setText("Empresa no corresponde a tu nivel");
            }else{
                txError.setText("Todo Correcto cargando");
                try {
                    empDef.setValor(((Empresas)cbEmpresas.getSelectionModel().getSelectedItem()).getCodigo());
                    MainContext.getGlobales().edit(empDef);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                pbUser.setProgress(1);
               
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/timmps/fac/gui/Marco.fxml"));
             Parent  root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
             Stage st = new Stage();
             st.setScene(scene);
             
    st.getIcons().addAll(MainContext.getPrimaryStage().getIcons());
   st.widthProperty().addListener(new ChangeListener<Number>() {
    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
        System.out.println("Width: " + newSceneWidth);
    }

                });
st.heightProperty().addListener(new ChangeListener<Number>() {
    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
        System.out.println("Height: " + newSceneHeight);
    }
});
    
             st.show();
              Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    st.setX(bounds.getMinX());
    st.setY(bounds.getMinY());
    st.setWidth(bounds.getWidth());
    st.setHeight(bounds.getHeight());

             MainContext.getPrimaryStage().close();
             MainContext.setPrimaryStage(st);
                
            }
        }else{
             txError.setText("Error de pass");
        }
        }catch(NullPointerException e){
            txError.setText("Nombre de usuario y contraseña Errorneo");
        } catch (IOException ex) {
            Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    public void selEmpresa(){
       // System.exit(0);
    }
}
