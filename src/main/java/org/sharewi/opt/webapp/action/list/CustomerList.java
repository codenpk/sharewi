package org.sharewi.opt.webapp.action.list;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.model.location.Customer;
import org.sharewi.opt.service.LocationManager;

import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.*;

public class CustomerList extends BasePage implements Serializable {
    private static final long serialVersionUID = 1050967473906941140L;

    private LocationManager locationManager = null;             //spring(c-arg)

    private List<String> provinces = null;                      //jsf
    private List<String> cities = null;                         //jsf
    private List<String> postalCodes = null;                    //jsf

    private Map<String, String> provinceSelectItems = null;     //jsf
    private Map<String, String> citySelectItems = null;         //jsf
    private Map<String, String> postalCodeSelectItems = null;   //jsf


    private Set<String> findProvinces = null;                   //object
    private Set<String> findCities = null;                      //object
    private Set<String> findPostalCodes = null;                 //object

    private List<Customer> resultSet = null;                    //object

    private Boolean clearCities = Boolean.FALSE;                //bool
    private Boolean clearPostalCodes = Boolean.FALSE;           //bool


    //Constructor (Spring injected)
    public CustomerList(LocationManager locationManager) {
        this.locationManager = locationManager;

        setSortColumn("id");
        setAscending(true);

        this.provinces = new LinkedList<String>(Arrays.asList("all"));
        this.cities = new LinkedList<String>(Arrays.asList("all"));
        this.postalCodes = new LinkedList<String>(Arrays.asList("all"));

        this.findProvinces = new HashSet<String>();
        this.findCities = new HashSet<String>();
        this.findPostalCodes = new HashSet<String>();

        log.debug("Created a customerList bean");
    }


    //Getters
    @SuppressWarnings("unchecked")
    public List<Customer> getCustomers() {
        log.debug("provinces:   " + findProvinces);
        log.debug("cities:      " + findCities);
        log.debug("postalCodes: " + findPostalCodes);

        if (resultSet == null) {
            log.debug("Hitting the DB");
            resultSet = locationManager.find(Customer.class, findProvinces, findCities, findPostalCodes);
        }
        return sort(resultSet);
    }

    public List<Customer> getResultSet() {
        return resultSet;
    }

    public Map<String, String> getProvinceSelectItems() {
        if (provinceSelectItems == null) {
            log.debug("Hitting DB");
            List<Customer> results = locationManager.find(Customer.class, null, null, null);
            Map<String, String> selectItems = new TreeMap<String, String>();
            for (Customer customer : results) {
                String province = customer.getAddress().getProvince();
                selectItems.put(province, province);
            }
            this.provinceSelectItems = selectItems;
        }
        return provinceSelectItems;
    }
    public Map<String, String> getCitySelectItems() {
        if (citySelectItems == null) {
            log.debug("Hitting DB");
            List<Customer> results = locationManager.find(Customer.class, findProvinces, null, null);
            Map<String, String> selectItems = new TreeMap<String, String>();
            for (Customer customer : results) {
                String city = customer.getAddress().getCity();
                selectItems.put(city, city);
            }
            this.citySelectItems = selectItems;
        }
        return citySelectItems;
    }
    public Map<String, String> getPostalCodeSelectItems() {
        if (postalCodeSelectItems == null) {
            log.debug("Hitting DB");
            List<Customer> results = locationManager.find(Customer.class, findProvinces, findCities, null);
            Map<String, String> selectItems = new TreeMap<String, String>();
            for (Customer customer : results) {
                String postalCode = customer.getAddress().getPostalCode();
                selectItems.put(postalCode, postalCode);
            }
            this.postalCodeSelectItems = selectItems;
        }
        return postalCodeSelectItems;
    }

    public List<String> getProvinces() {
        return provinces;
    }
    public List<String> getCities() {
        return cities;
    }
    public List<String> getPostalCodes() {
        return postalCodes;
    }


    //Setters
    public void setResultSet(List<Customer> resultSet) {
        log.debug("Running t:save sets");
        this.resultSet = resultSet;
    }

    public void setProvinceSelectItems(Map<String, String> provinceSelectItems) {
        this.provinceSelectItems = provinceSelectItems;
    }
    public void setCitySelectItems(Map<String, String> citySelectItems) {
        this.citySelectItems = citySelectItems;
    }
    public void setPostalCodeSelectItems(Map<String, String> postalCodeSelectItems) {
        this.postalCodeSelectItems = postalCodeSelectItems;
    }

    public void setProvinces(List<String> provinces) {
        this.findProvinces.clear();
        if (provinces != null && !provinces.contains("all")) this.findProvinces = new HashSet<String>(provinces);

        this.provinces = provinces;

        log.debug(provinces);
    }
    public void setCities(List<String> cities) {
        this.findCities.clear();
        if (cities != null && !cities.contains("all") && !clearCities) {
            this.findCities = new HashSet<String>(cities);
        }

        this.cities = cities;

        log.debug(cities);
    }
    public void setPostalCodes(List<String> postalCodes) {
        this.findPostalCodes.clear();
        if (postalCodes != null && !postalCodes.contains("all") && !clearPostalCodes) {
            this.findPostalCodes = new HashSet<String>(postalCodes);
        }

        this.postalCodes = postalCodes;

        log.debug(postalCodes);
    }


    //Event Handlers
    public void provincesChanged(ValueChangeEvent e) {
        this.resultSet = null;
        this.citySelectItems = null;
        this.postalCodeSelectItems = null;

        List oldValues = (List<String>) e.getOldValue();
        List newValues = (List<String>) e.getNewValue();
        if (!oldValues.contains("all") && !newValues.contains("all") && !newValues.containsAll(oldValues)) {
            this.clearCities = Boolean.TRUE;
            this.clearPostalCodes = Boolean.TRUE;
        }
    }
    public void citiesChanged(ValueChangeEvent e) {
        this.resultSet = null;
        this.postalCodeSelectItems = null;

        List oldValues = (List<String>) e.getOldValue();
        List newValues = (List<String>) e.getNewValue();
        if (!oldValues.contains("all") && !newValues.contains("all") && !newValues.containsAll(oldValues)) {
            this.clearPostalCodes = Boolean.TRUE;
        }
    }
    public void postalCodesChanged(ValueChangeEvent e) {
        this.resultSet = null;
    }
}