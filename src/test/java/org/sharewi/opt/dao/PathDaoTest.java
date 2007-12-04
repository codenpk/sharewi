package org.sharewi.opt.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.springframework.dao.DataAccessException;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 9, 2007
 * Time: 9:08:36 PM
 */
public class PathDaoTest extends BaseDaoTestCase {
    private PathDao pathDao;

    public void setPathDao(PathDao pathDao) {
        this.pathDao = pathDao;
    }

    public void testGetPathInvalid() throws Exception {
        try {
            pathDao.get(0L);
            fail("'badpath' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }
}
