/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmps.exceptions;

/**
 *
 * @author aperalta
 */
public class TimmpsException extends Exception {
    public TimmpsException(String message, Throwable cause) {
        super(message, cause);
    }
    public TimmpsException(String message) {
        super(message);
    }
}
