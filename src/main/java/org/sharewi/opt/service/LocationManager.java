package org.sharewi.opt.service;

import org.appfuse.service.GenericManager;

import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.model.location.Location;
import org.sharewi.opt.util.PathDwrWrapper;
import org.sharewi.opt.service.exceptions.LocationExistsException;

import java.util.List;
import java.util.Set;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 4, 2007
 * Time: 11:13:14 PM
 */

public interface LocationManager extends GenericManager<Location, Long> {

    /* Save */
    public <LOC extends Location> LOC saveLocation(LOC location) throws LocationExistsException;


    /* Retrive */
    public List<Location> getAllLocations();
    public List<Location> getOtherLocations(Long id);

    public <LOC extends Location> List<LOC> getAll(Class<LOC> _class);


    /* Find */
    public <L extends Location> List<L> find(Class<L> _class,
                                                     Set<String> states, Set<String> cities, Set<String> postalCodes);

    
    /* Path Management Methods */
    public List<Path> prepareAssociatedPaths(Long id);
    public List<PathDwrWrapper> preparePathWrappers(Long id);

}