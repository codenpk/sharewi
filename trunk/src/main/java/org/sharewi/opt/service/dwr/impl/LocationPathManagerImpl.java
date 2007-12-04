package org.sharewi.opt.service.dwr.impl;

import org.sharewi.opt.service.dwr.LocationPathManager;
import org.sharewi.opt.service.LocationManager;
import org.sharewi.opt.service.PathManager;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 10, 2007
 * Time: 3:43:24 AM
 */
public class LocationPathManagerImpl implements LocationPathManager {
    private LocationManager locationManager;
    private PathManager pathManager;

//  ###########################################

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }

//  ###########################################


}
