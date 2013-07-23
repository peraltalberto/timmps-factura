/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac;

import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Sessiones;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.fac.persistencia.models.CfGlobalJpaController;
import es.timmps.fac.persistencia.models.CfTipoValoresJpaController;
import es.timmps.fac.persistencia.models.EmpresasJpaController;
import es.timmps.fac.persistencia.models.RegistroLogJpaController;
import es.timmps.fac.persistencia.models.SessionesJpaController;
import es.timmps.fac.persistencia.models.TipoLogJpaController;
import es.timmps.fac.persistencia.models.UsuariosJpaController;
import es.timmps.fac.persistencia.models.exceptions.NonexistentEntityException;
import es.timmps.fac.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aperalta
 */
public class MainContext {
    
    private  static EntityManagerFactory emf = Persistence.createEntityManagerFactory("fratimmpPU");
    private static UsuariosJpaController usuarios = null;
    private static EmpresasJpaController empresas = null;
    private static CfGlobalJpaController globales = null;
    private static CfTipoValoresJpaController t_datos = null;
    private static TipoLogJpaController t_log = null;
    private static RegistroLogJpaController r_log = null;
    private static SessionesJpaController sessiones = null;
    private static Usuarios usuario = MainContext.getUsuarios().findUsuarios("ADMIN");
    private static Empresas empresa = null;
    private static Sessiones session = null;
    private static Stage primaryStage = null;
    public static EntityManagerFactory getEmf() {
        if(emf == null)
            emf = Persistence.createEntityManagerFactory("fratimmpPU");
        
        return emf;
    }

    public static void setEmf(EntityManagerFactory emf) {
        MainContext.emf = emf;
    }

    public static UsuariosJpaController getUsuarios() {
        if(usuarios == null)
        usuarios = new UsuariosJpaController(emf);
        
        return usuarios;
    }

    public static void setUsuarios(UsuariosJpaController usus) {
        MainContext.usuarios = usus;
    }

    public static RegistroLogJpaController getR_log() {
         if(r_log == null)
            r_log = new RegistroLogJpaController(emf);
        
        return r_log;
    }

    public static void setR_log(RegistroLogJpaController r_log) {
        MainContext.r_log = r_log;
    }

    public static EmpresasJpaController getEmpresas() {
        if(empresas == null)
            empresas = new EmpresasJpaController(emf);
        
        return empresas;
    }

    public static void setEmpresas(EmpresasJpaController empresas) {
        MainContext.empresas = empresas;
    }

    public static TipoLogJpaController getT_log() {
       if(t_log == null)
            t_log = new TipoLogJpaController(emf);
        return t_log;
    }

    public static void setT_log(TipoLogJpaController t_log) {
        MainContext.t_log = t_log;
    }

    public static CfGlobalJpaController getGlobales() {
        if(globales == null)
            globales = new CfGlobalJpaController(emf);
        return globales;
    }

    public static void setGlobales(CfGlobalJpaController globales) {
        MainContext.globales = globales;
    }

    public static CfTipoValoresJpaController getT_datos() {
        if(t_datos == null)
            t_datos = new CfTipoValoresJpaController(emf);
        return t_datos;
    }

    public static void setT_datos(CfTipoValoresJpaController t_datos) {
        MainContext.t_datos = t_datos;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        MainContext.primaryStage = primaryStage;
        MainContext.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent t) {
                if(session != null){
                    try {
                        Log.Set(Log.LOG.info, "Salida de el programa del usuario :"+usuario.getId());
                        sessiones.destroy(session.getId());
                    } catch (NonexistentEntityException ex) {
                       Log.Set(Log.LOG.info, "Salida del programa con errores");
                    }
                }else{
                    
                    Log.Set(Log.LOG.info, "Salida de el programa sin sesion ");
                        
                }
            }
        });
    }

    public static Usuarios getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuarios usuario) {
        MainContext.usuario = usuario;
    }

    public static Empresas getEmpresa() {
        return empresa;
    }

    public static void setEmpresa(Empresas empresa) {
        MainContext.empresa = empresa;
    }

    public static SessionesJpaController getSessiones() {
         if(sessiones == null)
            sessiones = new SessionesJpaController(emf);
        
        return sessiones;
    }

    public static void setSessiones(SessionesJpaController sessiones) {
        MainContext.sessiones = sessiones;
    }

    public static Sessiones getSession() {
        return session;
    }

    public static void setSession(Sessiones session) {
        MainContext.session = session;
    }
  
  
}
