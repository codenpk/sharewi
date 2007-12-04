package org.sharewi.opt.service;

import org.appfuse.service.GenericManager;
import org.sharewi.opt.model.location.Path;
import org.sharewi.opt.util.PathDwrWrapper;

import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 8:40:53 PM
 */
public interface PathManager extends GenericManager<Path, Long> {

    public List<Path> getAllPaths();

    public List<Path> getPathsFromLocation(Long id);

    public List<Path> getPathsToLocation(Long id);

    public List<Path> getPathsFromTo(Long srcId, Long dstId);

    public List<Path> getPathsByModel(Path path);

    public List<Path> getPathsByModelInverse(Path path);

    public void updatePath(Long srcId, Long dstId, Double dist, Double time);

    public void updatePathsFromWrappers(List<PathDwrWrapper> pathDwrWrappers);

}
