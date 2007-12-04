package org.sharewi.opt.webapp.action.form;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.model.event.Event;
import org.sharewi.opt.service.EventManager;
import org.sharewi.opt.service.exceptions.EventExistsException;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 16, 2007
 * Time: 4:37:35 AM
 */
public class EventForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -8851261180967072353L;

    private EventManager eventManager = null;           //spring c-arg
    private Event event = null;                         //object

    public EventForm(EventManager eventManager) {
        this.eventManager = eventManager;

        String id = getParameter("id");
        if (id != null && event == null) {
            log.debug("Fetching event with id: [" + id + "]");

            try {
                event = eventManager.get(Long.valueOf(id));
            }
            catch (Exception e) {
                log.warn(e.getMessage());
                try {
                    getResponse().sendRedirect("/404.jsp");
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            event = new Event();
        }

        log.debug("Created EventForm object");
    }


    //Getters
    public Event getEvent() {
        return event;
    }

    //Setters
    public void setEvent(Event event) {
        this.event = event;
    }


    //Action Methods
    public void save() {
        try {
            event = eventManager.saveEvent(event);
        } catch (EventExistsException e) {
            log.debug(e.getMessage());
        }

    }

}