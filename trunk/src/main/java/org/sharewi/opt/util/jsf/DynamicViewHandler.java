package org.sharewi.opt.util.jsf;

import com.sun.facelets.FaceletViewHandler;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import com.sun.facelets.util.ParameterCheck;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 21, 2007
 * Time: 5:14:11 PM
 */
public class DynamicViewHandler extends FaceletViewHandler {

    public DynamicViewHandler(ViewHandler viewHandler) {
        super(viewHandler);
    }

    public String getActionURL(FacesContext facesContext, String s) {
        String result = s;
        return null;
    }
}
