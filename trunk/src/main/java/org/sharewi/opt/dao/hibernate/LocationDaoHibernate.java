package org.sharewi.opt.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;

import org.sharewi.opt.dao.LocationDao;
import org.sharewi.opt.model.location.Location;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.Criteria;

import java.util.List;
import java.util.Set;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 4, 2007
 * Time: 10:43:39 PM
 */
public class LocationDaoHibernate extends GenericDaoHibernate<Location, Long> implements LocationDao {

    public LocationDaoHibernate() {
        super(Location.class);
    }

//  ################################################

    /**
     * Overridden simply to call the save method. This is happenening
     * because saveLocation flushes the session and saveObject of BaseDaoHibernate
     * does not.
     */
    @Override
    public Location save(Location baseLocation) {
        return this.saveLocation(baseLocation);
    }
    public <LOC extends Location> LOC saveLocation(LOC location) {
        log.debug("location's id: " + location.getId());
        getHibernateTemplate().saveOrUpdate(location);
        // necessary to throw a DataIntegrityViolation and catch it in LocationManager
        getHibernateTemplate().flush();
        return location;
    }

    @SuppressWarnings("unchecked")
    public <LOC extends Location> List<LOC> getAll(Class<LOC> _class) {
        DetachedCriteria criteria = DetachedCriteria.forClass(_class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }


//  ################################################

    @SuppressWarnings("unchecked")
    public List<Location> getAllLocations() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Location.class)
                .addOrder(Order.asc("id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public List<Location> getOtherLocations(Long id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Location.class)
                .add(Restrictions.ne("id", id))
                .addOrder(Order.asc("id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }

//  ################################################

    @SuppressWarnings("unchecked")
    public <L extends Location> List<L> find(Class<L> _class,
                                                     Set<String> states, Set<String> cities, Set<String> postalCodes) {

        DetachedCriteria criteria = DetachedCriteria.forClass(_class);

        if (states != null && !states.isEmpty()) criteria.add( Restrictions.in("address.province", states) );
        if (cities != null && !cities.isEmpty()) criteria.add( Restrictions.in("address.city", cities) );
        if (postalCodes != null && !postalCodes.isEmpty()) criteria.add(Restrictions.in("address.postalCode", postalCodes));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(criteria);
    }

}
