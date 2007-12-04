package org.sharewi.opt.util.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class HibernateDateUTC implements UserType {

    public int[] sqlTypes() { return new int[]{ Hibernate.LONG.sqlType() }; }

    public Class returnedClass() { return Date.class; }

    public boolean isMutable() { return true; }

    public Object deepCopy(Object value) {  return value; }

    public Serializable disassemble(Object value) { return (Serializable) value; }

    public Object assemble(Serializable cached, Object owner) { return cached; }

    public Object replace(Object original, Object target, Object owner) { return original; }

    public int hashCode(Object x) { return x.hashCode(); }

    public boolean equals(Object x, Object y) { return x == y || !(x == null || y == null) && x.equals(y); }


    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException {
        Long timeInMillisec = resultSet.getLong(names[0]);
        // Deferred check after first read
        if (resultSet.wasNull()) return null;
        return new Date(timeInMillisec);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, Hibernate.LONG.sqlType());
        } else {
            Date localDate = (Date) value;
            statement.setLong(index, localDate.getTime());
        }
    }
}