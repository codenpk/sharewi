package org.sharewi.opt.util.hibernate.joda;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentTZ implements UserType {

    public int[] sqlTypes() { return new int[]{ Hibernate.STRING.sqlType() }; }

    public Class returnedClass() { return DateTimeZone.class; }

    public boolean isMutable() { return false; }

    public Object deepCopy(Object value) { return value; }

    public Serializable disassemble(Object value) { return (Serializable) value; }

    public Object assemble(Serializable cached, Object owner) { return cached; }

    public Object replace(Object original, Object target, Object owner) { return original; }

    public int hashCode(Object x) { return x.hashCode(); }

    public boolean equals(Object x, Object y) { return x == y || !(x == null || y == null) && x.equals(y); }


    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException {
        String tzId = resultSet.getString(names[0]);
        // Deferred check after first read
        if (resultSet.wasNull()) return null;
        return DateTimeZone.forID(tzId);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException {
        if (!(value instanceof DateTimeZone)) {
            statement.setNull(index, Hibernate.STRING.sqlType());
        } else {
            statement.setString(index, ((DateTimeZone) value).getID());
        }
    }
}