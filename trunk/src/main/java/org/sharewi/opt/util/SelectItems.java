package org.sharewi.opt.util;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.sharewi.opt.model.event.Fee;

import java.io.Serializable;
import java.util.*;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Jun 18, 2007
 * Time: 9:30:33 PM
 * Note: Map binding: Label, Value
 */
public final class SelectItems implements Serializable {
    private static final long serialVersionUID = -161797903359752354L;

    private SortedMap<String, String> provinces = null;

    private Map<String, Integer> hours = null;
    private Map<String, Integer> minutes = null;
    private Map<String, DateTimeZone> timeZones = null;
    private Map<String, Integer> daysOfWeek = null;

    private Map<String, Boolean> pickup = null;

    private Map<String, Integer> feeTypes = null;

    // Getters

    public Map<String, String> getProvinces() {
        if (provinces == null) {
            provinces = new TreeMap<String, String>();

            provinces.put("Alabama", "AL");
            provinces.put("Alaska", "AK");
            provinces.put("Arizona", "AZ");
            provinces.put("Arkansas", "AR");
            provinces.put("California", "CA");
            provinces.put("Colorado", "CO");
            provinces.put("Connecticut", "CT");
            provinces.put("Delaware", "DE");
            provinces.put("Florida", "FL");
            provinces.put("Georgia", "GA");
            provinces.put("Hawaii", "HI");
            provinces.put("Idaho", "ID");
            provinces.put("Illinois", "IL");
            provinces.put("Indiana", "IN");
            provinces.put("Iowa", "IA");
            provinces.put("Kansas", "KS");
            provinces.put("Kentucky", "KY");
            provinces.put("Louisiana", "LA");
            provinces.put("Maine", "ME");
            provinces.put("Maryland", "MD");
            provinces.put("Massachusetts", "MA");
            provinces.put("Michigan", "MI");
            provinces.put("Minnesota", "MN");
            provinces.put("Mississippi", "MS");
            provinces.put("Missouri", "MO");
            provinces.put("Montana", "MT");
            provinces.put("Nebraska", "NE");
            provinces.put("Nevada", "NV");
            provinces.put("New Hampshire", "NH");
            provinces.put("New Jersey", "NJ");
            provinces.put("New Mexico", "NM");
            provinces.put("New York", "NY");
            provinces.put("North Carolina", "NC");
            provinces.put("North Dakota", "ND");
            provinces.put("Ohio", "OH");
            provinces.put("Oklahoma", "OK");
            provinces.put("Oregon", "OR");
            provinces.put("Pennsylvania", "PA");
            provinces.put("Rhode Island", "RI");
            provinces.put("South Carolina", "SC");
            provinces.put("South Dakota", "SD");
            provinces.put("Tennessee", "TN");
            provinces.put("Texas", "TX");
            provinces.put("Utah", "UT");
            provinces.put("Vermont", "VT");
            provinces.put("Virginia", "VA");
            provinces.put("Washington", "WA");
            provinces.put("West Virginia", "WV");
            provinces.put("Wisconsin", "WI");
            provinces.put("Wyoming", "WY");
        }
        return provinces;
    }

    public Map<String, Integer> getHours() {
        if (hours == null) {
            hours = new LinkedHashMap<String, Integer>();

            DateTimeFormatter fmt = DateTimeFormat.forPattern("hh a");
            MutableDateTime dateTime = new MutableDateTime(0);
            MutableDateTime.Property hour = dateTime.hourOfDay();
            hour.set(5);
            for (int i = hour.getMinimumValueOverall(); i <= hour.getMaximumValueOverall(); i++) {
                hour.add(1);
                hours.put(fmt.print(dateTime), hour.get());
            }
        }
        return hours;
    }

    public Map<String, Integer> getMinutes() {
        if (minutes == null) {
            minutes = new LinkedHashMap<String, Integer>();

            DateTimeFormatter fmt = DateTimeFormat.forPattern(":mm");
            MutableDateTime dateTime = new MutableDateTime(0);
            MutableDateTime.Property minute = dateTime.minuteOfHour();
            for (int i = 0; i < 60; i += 15) {
                minute.set(i);
                minutes.put(fmt.print(dateTime), minute.get());
            }
        }
        return minutes;
    }

    public Map<String, Integer> getDaysOfWeek() {
        if (daysOfWeek == null) {
            daysOfWeek = new LinkedHashMap<String, Integer>();

            Chronology c = ISOChronology.getInstanceUTC();
            for (int i = 1; i < 8; i++) {
                daysOfWeek.put(c.dayOfWeek().getAsShortText(i, Locale.getDefault()), i);
            }
        }
        return daysOfWeek;
    }

    public Map<String, DateTimeZone> getTimeZones() {
        if (timeZones == null) {
            timeZones = new LinkedHashMap<String, DateTimeZone>();

            timeZones.put("Eastern", DateTimeZone.forID("-05:00"));
            timeZones.put("Central", DateTimeZone.forID("-06:00"));
            timeZones.put("Mountain", DateTimeZone.forID("-07:00"));
            timeZones.put("Pacific", DateTimeZone.forID("-08:00"));
        }
        return timeZones;
    }

    public Map<String, Boolean> getPickup() {
        if (pickup == null) {
            pickup = new LinkedHashMap<String, Boolean>();

            pickup.put("Delivery", Boolean.FALSE);
            pickup.put("Pickup", Boolean.TRUE);
        }
        return pickup;
    }

    public Map<String, Integer> getFeeTypes() {
        if (feeTypes == null) {
            feeTypes = new LinkedHashMap<String, Integer>();

            for (Map.Entry<Integer, String> entry : Fee.feeTypeNames.entrySet()) {
                feeTypes.put(entry.getValue(), entry.getKey());
            }
        }
        return feeTypes;
    }
}