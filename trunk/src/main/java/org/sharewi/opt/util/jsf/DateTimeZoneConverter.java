package org.sharewi.opt.util.jsf;


import org.joda.time.DateTimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 9, 2007
 * Time: 3:20:54 AM
 */
public class DateTimeZoneConverter implements Converter {
    public static final String CONVERTER_ID = "DateTimeZoneJSF";

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (value == null) return null;

        return DateTimeZone.forID(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (!(value instanceof DateTimeZone)) return null;

        return value.toString();
    }
}