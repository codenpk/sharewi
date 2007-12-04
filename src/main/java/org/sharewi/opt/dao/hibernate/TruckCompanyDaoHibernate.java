package org.sharewi.opt.dao.hibernate;

import org.sharewi.opt.model.event.TruckCompany;
import org.sharewi.opt.dao.TruckCompanyDao;
import org.appfuse.dao.hibernate.GenericDaoHibernate;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 30, 2007
 * Time: 11:06:09 PM
 */
public class TruckCompanyDaoHibernate extends GenericDaoHibernate<TruckCompany, Long> implements TruckCompanyDao {

    public TruckCompanyDaoHibernate() {
        super(TruckCompany.class);
    }

    
    @Override
    public TruckCompany save(TruckCompany truckCompany) {
        return saveTruckCompany(truckCompany);
    }

    public TruckCompany saveTruckCompany(TruckCompany truckCompany) {
        log.debug("truckCompany's id: " + truckCompany);
        getHibernateTemplate().saveOrUpdate(truckCompany);
        getHibernateTemplate().flush();
        return truckCompany;
    }

    public TruckCompany mergeTruckCompany(TruckCompany truckCompany) {
        getHibernateTemplate().merge(truckCompany);
        return truckCompany;
    }

    @SuppressWarnings("unchecked")
    public List<TruckCompany> getAllTruckCompanies() {
        return getHibernateTemplate().find("from TruckCompany tc order by tc.id");
    }

    @SuppressWarnings("unchecked")
    public List<TruckCompany> findByName(String name) {
        return getHibernateTemplate().find("from TruckCompany tc where tc.name=?", name);
    }

}
