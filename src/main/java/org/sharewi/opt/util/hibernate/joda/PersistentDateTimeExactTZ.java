package org.sharewi.opt.util.hibernate.joda;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Persist {@link org.joda.time.DateTime} via hibernate.<br />
 * The timezone will be stored in an extra column
 */
public class PersistentDateTimeExactTZ implements CompositeUserType {

    public final static PersistentDateTimeExactTZ INSTANCE = new PersistentDateTimeExactTZ();
    

    public String[] getPropertyNames() {
        return new String[] {"millis", "timeZone"};
    }

    public Type[] getPropertyTypes() {
        return new Type[] {Hibernate.LONG, Hibernate.STRING};
    }

    public Object getPropertyValue(Object component, int property) throws HibernateException {
        DateTime dateTime = (DateTime) component;
        if (property == 0) {
            return  dateTime.getMillis();
        } else {
            return dateTime.getZone().getID();
        }
    }

    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        throw new UnsupportedOperationException("DateTime objects are immutable");
    }

    public Class returnedClass() { return DateTime.class; }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) return true;
        if (x == null || y == null) return false;

        DateTime dtx = (DateTime) x;
        DateTime dty = (DateTime) y;

        return dtx.equals(dty);
    }

    public int hashCode(Object object) throws HibernateException { return object.hashCode(); }


    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) return null;

        return new DateTime(value);
    }

    public boolean isMutable(){ return false; }

    //Composite
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Long timeMillis = rs.getLong(names[0]);
        //Delayed check
        if (rs == null) return null;
        
        String timeZone = rs.getString(names[1]);

        return new DateTime(timeMillis, DateTimeZone.forID(timeZone));
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Hibernate.LONG.sqlType());
            st.setNull(index+1, Hibernate.STRING.sqlType());
        } else {
            DateTime dt = (DateTime) value;
            st.setLong(index, dt.getMillis());
            st.setString(index+1, dt.getZone().getID());
        }
    }

    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
        return original;
    }

}