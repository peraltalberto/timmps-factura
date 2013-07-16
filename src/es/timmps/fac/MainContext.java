/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac;

import es.timmps.fac.persistencia.models.CfGlobalJpaController;
import es.timmps.fac.persistencia.models.CfTipoValoresJpaController;
import es.timmps.fac.persistencia.models.EmpresasJpaController;
import es.timmps.fac.persistencia.models.UsuariosJpaController;
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

    public static EmpresasJpaController getEmpresas() {
        if(empresas == null)
            empresas = new EmpresasJpaController(emf);
        
        return empresas;
    }

    public static void setEmpresas(EmpresasJpaController empresas) {
        MainContext.empresas = empresas;
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
  
  
}
