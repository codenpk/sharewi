package org.sharewi.opt.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.dao.PathDao;
import org.hibernate.Query;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 7:40:25 PM
 */
public class PathDaoHibernate extends GenericDaoHibernate<Path, Long> implements PathDao {

//  ################################################

    public PathDaoHibernate() {
        super(Path.class);
    }

//  ################################################



//  ################################################

    /**
     * Overridden simply to call the saveUser method. This is happenening
     * because saveLocation flushes the session and saveObject of BaseDaoHibernate
     * does not.
     */
    @Override
    public Path save(Path path) {
        return this.savePath(path);
    }

    public Path savePath(Path path) {
        log.debug("path's id: " + path.getId());
        getHibernateTemplate().saveOrUpdate(path);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return path;
    }

//  ################################################

    @SuppressWarnings("unchecked")
    public List<Path> getAllPaths() {
        return getHibernateTemplate().find("from Path p order by p.source.id");
    }


//  ############ Convenience Methods ###############


    @SuppressWarnings("unchecked")
    public List<Path> getPathsFromLocation(Long id) {
        return getHibernateTemplate().find("from Path p where p.source.id=?", id);
    }

    @SuppressWarnings("unchecked")
    public List<Path> getPathsToLocation(Long id) {
        return getHibernateTemplate().find("from Path p where p.destination.id=?", id);
    }

    @SuppressWarnings("unchecked")
    public void updatePath(Long srcId, Long dstId, Double dist, Double time) {
        Query q = getSession().createQuery(
                "update Path p set p.dist = :dist, p.time = :time where p.source.id = :srcId and p.destination.id = :dstId");
        q.setDouble("time", time);
        q.setLong("srcId", srcId);
        q.setLong("dstId", dstId);
        q.setDouble("dist", dist);

        q.executeUpdate();
    }


    @SuppressWarnings("unchecked")
    public List<Path> getPathsFromTo(Long srcId, Long dstId) {
        return getHibernateTemplate().find("from Path p where p.source.id=? and p.destination.id=?",
                new Long[]{srcId, dstId});
    }

    @SuppressWarnings("unchecked")
    public List<Path> getPathsByModel(Path path) {
        return getHibernateTemplate().find("from Path p where p.source.id=? and p.destination.id=?",
                new Long[]{path.getSource().getId(), path.getDestination().getId()});
    }

    @SuppressWarnings("unchecked")
    public List<Path> getPathsByModelInverse(Path path) {
        return getHibernateTemplate().find("from Path p where p.source.id=? and p.destination.id=?",
                new Long[]{path.getDestination().getId(), path.getSource().getId()});
    }
}
