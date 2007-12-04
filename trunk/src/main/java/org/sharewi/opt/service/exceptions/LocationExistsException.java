package org.sharewi.opt.service.exceptions;

import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 25, 2007
 * Time: 5:44:20 PM
 * Note: An exception that is thrown by classes wanting to trap unique
 * constraint violations.  This is used to wrap Spring's 
 * DataIntegrityViolationException so it's checked in the web layer.
 */
public class LocationExistsException extends Exception {
    private static final long serialVersionUID = -2065843379576952437L;


    /**
     * Constructor for LocationsExistsException.
     *
     * @param message exception message
     */
    public LocationExistsException(String message) {
        super(message);
    }
}

