/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.secure;

import es.timmps.fac.persistencia.Empresas;
import es.timmps.fac.persistencia.Sessiones;
import es.timmps.fac.persistencia.Usuarios;
import es.timmps.util.Encriptacion;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aperalta
 */
public class SessionControler {
    
    public static Sessiones createSession(Usuarios usu,Empresas emp){
        try {
            Sessiones ses = new Sessiones();
            ses.setUsuario(usu);
            ses.setEmpresa(emp);
            ses.setInicio(new Date());
            ses.setKey(Encriptacion.findMD5(usu.getId()+emp.getCodigo()+ses.getInicio()));
            return ses;
        } catch (Exception ex) {
            return null;
        }
    }
}
