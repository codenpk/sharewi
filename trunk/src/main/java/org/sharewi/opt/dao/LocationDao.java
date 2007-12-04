package org.sharewi.opt.dao;

import org.appfuse.dao.GenericDao;
import org.sharewi.opt.model.location.Location;

import java.util.List;
import java.util.Set;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 4, 2007
 * Time: 10:35:21 PM
 */
public interface LocationDao extends GenericDao<Location, Long> {

    /* Save */

    public <LOC extends Location> LOC saveLocation(LOC location);


    /* Retreive */

    public List<Location> getAllLocations();
    public List<Location> getOtherLocations(Long id);

    public <LOC extends Location> List<LOC> getAll(Class<LOC> _class);

    
    /* Find */

    public <L extends Location> List<L> find(Class<L> _class,
                                                     Set<String> states, Set<String> cities, Set<String> postalCodes);

}
