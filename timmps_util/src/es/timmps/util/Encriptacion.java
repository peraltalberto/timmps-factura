/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.util;

import es.timmps.exceptions.TimmpsException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author aperalta
 */
public class Encriptacion {
    
    
    public static String findMD5(String arg) throws TimmpsException {

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new TimmpsException("No se a encontrado el alogaritmo ",nsae);
        }
        byte[] defaultBytes = arg.getBytes();
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex.toUpperCase());
            
        }
        System.out.println(hexString.length() +"   "+hexString);
        return hexString.toString().toUpperCase();
    }
}
