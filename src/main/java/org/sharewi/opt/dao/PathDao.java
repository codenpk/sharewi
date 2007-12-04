package org.sharewi.opt.dao;

import org.appfuse.dao.GenericDao;
import org.sharewi.opt.model.location.Path;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 7:35:19 PM
 */
public interface PathDao extends GenericDao<Path, Long> {

    public Path savePath(Path path);



    public List<Path> getAllPaths();

    public List<Path> getPathsFromLocation(Long id);

    public List<Path> getPathsToLocation(Long id);

    public List<Path> getPathsFromTo(Long srcId, Long dstId);

    public List<Path> getPathsByModel(Path path);

    public List<Path> getPathsByModelInverse(Path path);


    public void updatePath(Long srcId, Long dstId, Double dist, Double time);

}
