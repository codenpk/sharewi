package org.sharewi.opt.dao;

import org.sharewi.opt.model.event.TruckCompany;
import org.appfuse.dao.GenericDao;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 30, 2007
 * Time: 9:17:31 PM
 */
public interface TruckCompanyDao extends GenericDao<TruckCompany,Long> {

    public TruckCompany saveTruckCompany(TruckCompany truckCompany);
    public TruckCompany mergeTruckCompany(TruckCompany truckCompany);

    public List<TruckCompany> getAllTruckCompanies();

    public List<TruckCompany> findByName(String name);

}
