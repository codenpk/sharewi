package org.sharewi.opt.webapp.action.util;

import org.appfuse.webapp.action.BasePage;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Oct 12, 2007
 * Time: 3:41:59 AM
 */
public class Messages extends BasePage implements Serializable {
    private static final long serialVersionUID = -8215262449596999074L;

    private List<String> errors = null;
    private List<String> messages = null;


    //Constructor

    public Messages() {
        if (getSession().getAttribute("errors") != null ) {
            errors = new ArrayList<String>((List) getSession().getAttribute("errors"));
            getSession().removeAttribute("errors");
        }

        if (getSession().getAttribute("messages") != null ) {
            messages = new ArrayList<String>((List) getSession().getAttribute("messages"));
            getSession().removeAttribute("messages");
        }

    }


    //Getters

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getMessages() {
        return messages;
    }

}