package org.sharewi.opt.service.impl;

import org.appfuse.service.impl.GenericManagerImpl;
import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.service.PathManager;
import org.sharewi.opt.dao.PathDao;
import org.sharewi.opt.util.PathDwrWrapper;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 8:43:23 PM
 */
public class PathManagerImpl extends GenericManagerImpl<Path, Long> implements PathManager {

    private PathDao pathDao;

//  ###########################################

    public PathManagerImpl(PathDao pathDao) {
        super(pathDao);
        this.pathDao = pathDao;
    }

//  ###########################################

    public void remove(Long id) {
        log.debug("removing path: " + id);
        super.remove(id);
    }

    //  ###########################################

    public List<Path> getAllPaths() {
        return pathDao.getAllPaths();
    }

    public List<Path> getPathsFromLocation(Long id) {
        return this.pathDao.getPathsFromLocation(id);
    }

    public List<Path> getPathsToLocation(Long id) {
        return this.pathDao.getPathsToLocation(id);
    }

    public List<Path> getPathsFromTo(Long srcId, Long dstId) {
        return this.pathDao.getPathsFromTo(srcId, dstId);
    }

    public List<Path> getPathsByModel(Path path) {
        return this.pathDao.getPathsByModel(path);
    }

    public List<Path> getPathsByModelInverse(Path path) {
        return this.pathDao.getPathsByModelInverse(path);
    }

    public void updatePath(Long srcId, Long dstId, Double dist, Double time) {
        this.pathDao.updatePath(srcId, dstId, dist, time);
    }

    public void updatePathsFromWrappers(List<PathDwrWrapper> pathDwrWrappers) {

        for (PathDwrWrapper pathDwrWrapper : pathDwrWrappers) {

            // TODO: perhaps do some checking here that the source / destination is correct
            Path path = pathDao.get(pathDwrWrapper.getId());
            path.setDist(pathDwrWrapper.getDist());
            path.setTime(pathDwrWrapper.getTime());
            path = pathDao.save(path);
        }
    }
}
