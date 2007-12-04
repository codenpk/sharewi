package org.sharewi.opt.webapp.action.list;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.service.TruckCompanyManager;

import java.io.Serializable;
import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 31, 2007
 * Time: 4:05:19 AM
 */
public class TruckCompanyList extends BasePage implements Serializable {
    private static final long serialVersionUID = 7211847333663039109L;

    private TruckCompanyManager truckCompanyManager;            //spring (c-arg)


    //Constructor (spring-injected)

    public TruckCompanyList(TruckCompanyManager truckCompanyManager) {
        this.truckCompanyManager = truckCompanyManager;

        setSortColumn("id"); setAscending(true);
    }


    //Getters

    public List getTruckCompanies() {
        List truckCompanies = truckCompanyManager.getAllTruckCompanies();
        return sort(truckCompanies);
    }

}
