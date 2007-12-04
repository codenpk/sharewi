package org.sharewi.opt.util.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: pkatsev
 * Date: Nov 9, 2007
 * Time: 12:58:59 AM
 */
public class HibernateTimeZoneOffset implements UserType {

    public int[] sqlTypes() { return new int[]{ Hibernate.INTEGER.sqlType() }; }

    public Class returnedClass() { return Date.class; }

    public boolean isMutable() { return false; }

    public Object deepCopy(Object value) {  return value; }

    public Serializable disassemble(Object value) { return (Serializable) value; }

    public Object assemble(Serializable cached, Object owner) { return cached; }

    public Object replace(Object original, Object target, Object owner) { return original; }

    public int hashCode(Object x) { return x.hashCode(); }

    public boolean equals(Object x, Object y) { return x == y || !(x == null || y == null) && x.equals(y); }


    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException {
        Integer rawOffset = resultSet.getInt(names[0]);
        // Deferred check after first read
        if (resultSet.wasNull()) return null;
        return new SimpleTimeZone(rawOffset, "UTC" + rawOffset/3600000);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, Hibernate.INTEGER.sqlType());
        } else {
            TimeZone timeZone = (TimeZone) value;
            statement.setInt(index, timeZone.getRawOffset());
        }
    }
}