package org.sharewi.opt.service.impl;

import org.sharewi.opt.dao.LocationDao;
import org.sharewi.opt.model.location.Location;

import org.sharewi.opt.service.PathManager;
import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.util.PathDwrWrapper;

import org.sharewi.opt.service.LocationManager;

import org.sharewi.opt.service.exceptions.LocationExistsException;
import javax.persistence.EntityExistsException;
import org.springframework.dao.DataIntegrityViolationException;

import org.appfuse.service.impl.GenericManagerImpl;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 4, 2007
 * Time: 11:19:00 PM
 */

public class LocationManagerImpl extends GenericManagerImpl<Location, Long> implements LocationManager {

    private LocationDao locationDao;
    private PathManager pathManager;


    //Constructor
    public LocationManagerImpl(LocationDao locationDao, PathManager pathManager) {
        super(locationDao);
        this.locationDao = locationDao;
        this.pathManager = pathManager;
    }


    /* Save */
    public <LOC extends Location> LOC saveLocation(LOC location) throws LocationExistsException {
        try {
            return locationDao.saveLocation(location);
        } catch (DataIntegrityViolationException e) {
            throw new LocationExistsException("Location '" + location.getAddress() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            throw new LocationExistsException("Location '" + location.getAddress() + "' already exists!");
        }
    }


    /* Remove */
    @Override
    public void remove(Long id) {
        log.debug("removing location: " + id);
        for (Path path : prepareAssociatedPaths(id)) {
            pathManager.remove(path.getId());
        }
        super.remove(id);
    }


    /* Retreive */
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }
    public List<Location> getOtherLocations(Long id) {
        return locationDao.getOtherLocations(id);
    }

    public <LOC extends Location> List<LOC> getAll(Class<LOC> _class) {
        return locationDao.getAll(_class);
    }


    /* Find */
    public <L extends Location> List<L> find(Class<L> _class,
                                                     Set<String> states, Set<String> cities, Set<String> postalCodes) {
        return locationDao.find(_class, states, cities, postalCodes);
    }


    //Path Management Methods
    //TODO: Remove setDist / setTime for non-nullable db validation
    public List<Path> prepareAssociatedPaths(Long id) {

        Location srcLocation = this.locationDao.get(id);
        List<Path> paths = new LinkedList<Path>();

        for (Location dstLocation : locationDao.getOtherLocations(id)) {

            List<Path> resultFrom = pathManager.getPathsFromTo(id, dstLocation.getId());
            List<Path> resultTo = pathManager.getPathsFromTo(dstLocation.getId(), id);

            if (resultFrom.size() > 0) {
                paths.addAll(resultFrom);
            }
            else {
                Path path = new Path();
                path.setSource(srcLocation);
                path.setDestination(dstLocation);
                path.setDist(0.);
                path.setTime(0.);
                path = pathManager.save(path);

                paths.add(path);

            }

            if (resultTo.size() > 0) {
                paths.addAll(resultTo);
            }
            else {
                Path path = new Path();
                path.setSource(dstLocation);
                path.setDestination(srcLocation);
                path.setDist(0.);
                path.setTime(0.);
                path = pathManager.save(path);

                paths.add(path);
            }
        }

        return paths;
    }
    public List<PathDwrWrapper> preparePathWrappers(Long id) {
        List<PathDwrWrapper> result = new LinkedList<PathDwrWrapper>();

        for (Path path : this.prepareAssociatedPaths(id)) {
            result.add(
                    new PathDwrWrapper(
                            path.getId(),
                            path.getSource().getId(),
                            path.getDestination().getId(),
                            path.getSource().getGeocode(),
                            path.getDestination().getGeocode(),
                            path.getDist(),
                            path.getTime()
                    )
            );
        }

        return result;
    }

}
