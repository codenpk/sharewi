package org.sharewi.opt.service;

import org.appfuse.service.GenericManager;
import org.sharewi.opt.model.event.TruckCompany;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 30, 2007
 * Time: 11:32:49 PM
 */
public interface TruckCompanyManager extends GenericManager<TruckCompany, Long> {

    public TruckCompany saveTruckCompany(TruckCompany truckCompany);

    public List getAllTruckCompanies();

    public List findByName(String name);
    
    public TruckCompany mergeTruckCompany(TruckCompany truckCompany);

}
