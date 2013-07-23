/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.fac.util;

import es.timmps.fac.MainContext;
import es.timmps.fac.persistencia.RegistroLog;
import es.timmps.fac.persistencia.TipoLog;
import java.util.Date;

/**
 *
 * @author aperalta
 */

public class Log {
   public enum LOG{
    error(3),info(2),warning(4),debug(1);
    TipoLog tl;
    
    LOG(int id){
    tl = MainContext.getT_log().findTipoLog(id);
    }
    public TipoLog get(){
        return tl;
    }
    }
    static boolean registro = false;
    
    public static void Set(TipoLog t,String msg){
       if(registro){
        RegistroLog log = new RegistroLog();
        log.setFecha(new Date());
        log.setUsuario(MainContext.getUsuario());
        log.setTipo(t);
        log.setLog(msg);
         MainContext.getR_log().create(log);
       }
    }
    public static void Set(LOG t,String msg){
        Set(t.get(),msg);
    }
}
