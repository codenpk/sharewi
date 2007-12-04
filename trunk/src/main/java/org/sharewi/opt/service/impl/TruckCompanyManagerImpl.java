package org.sharewi.opt.service.impl;

import org.appfuse.service.impl.GenericManagerImpl;

import org.sharewi.opt.service.TruckCompanyManager;
import org.sharewi.opt.dao.TruckCompanyDao;
import org.sharewi.opt.model.event.TruckCompany;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 30, 2007
 * Time: 11:41:28 PM
 */
public class TruckCompanyManagerImpl extends GenericManagerImpl<TruckCompany, Long> implements TruckCompanyManager {

    private TruckCompanyDao truckCompanyDao = null;

    //Constructor

    public TruckCompanyManagerImpl(TruckCompanyDao truckCompanyDao) {
        super(truckCompanyDao);
        this.truckCompanyDao = truckCompanyDao;
    }


    //Methods

    public TruckCompany saveTruckCompany(TruckCompany truckCompany) {
        return truckCompanyDao.saveTruckCompany(truckCompany);
    }

    public TruckCompany mergeTruckCompany(TruckCompany truckCompany) {
        return truckCompanyDao.mergeTruckCompany(truckCompany);
    }

    public List<TruckCompany> getAllTruckCompanies() {
        return truckCompanyDao.getAllTruckCompanies();
    }

    public List<TruckCompany> findByName(String name) {
        return truckCompanyDao.findByName(name);
    }
}
