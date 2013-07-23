/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.gui.controlers;

import es.timmps.fac.MainContext;
import es.timmps.fac.persistencia.DatosPersonales;
import es.timmps.fac.persistencia.Usuarios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author aperalta
 */
public class AdminUsersController implements Initializable {
 @FXML
    private AnchorPane rootP;

    @FXML
    private TableView<Usuarios> tabla;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TableColumn columnMonth = new TableColumn("Usuario");
      columnMonth.setCellValueFactory(
              new PropertyValueFactory<Usuarios,String>("id"));
      TableColumn cnombre = new TableColumn("Nombre");
      cnombre.setCellValueFactory(new Callback<CellDataFeatures<Usuarios, String>, ObservableValue<String>>() {
     public ObservableValue<String> call(CellDataFeatures<Usuarios, String> p) {
         return new ReadOnlyObjectWrapper(p.getValue().getCodigoPersona().getNombre());
     }
  });
      tabla.setEditable(false);
      tabla.getColumns().addAll(columnMonth,cnombre);
      tabla.setItems(FXCollections.observableArrayList(MainContext.getUsuarios().findUsuariosEntities()));
    }
    
    public void selectUser(MouseEvent e){
        if(e.getClickCount()>1){
            Stage st = (Stage) rootP.getScene().getWindow();
            System.out.println(st.getTitle());
           Dialogs.showInformationDialog(st,
                   "Usuario "+tabla.getSelectionModel().getSelectedItem().getId(), 
                   "Esta haciendo doble clic en un usuario", "Atencion!!");
         }
    }
}
