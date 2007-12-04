package org.sharewi.opt.webapp.action.form;

import org.appfuse.webapp.action.BasePage;
import org.sharewi.opt.model.event.Fee;
import org.sharewi.opt.model.event.TruckCompany;
import org.sharewi.opt.model.event.Vehicle;
import org.sharewi.opt.service.TruckCompanyManager;

import javax.faces.component.UIData;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Class Creation info:
 * User: pkatsev
 * Date: Aug 31, 2007
 * Time: 4:05:54 AM
 */
public class TruckCompanyForm extends BasePage implements Serializable {
    private static final long serialVersionUID = 8208069761056527257L;

    private TruckCompanyManager truckCompanyManager = null;     //spring (c-arg)
    private TruckCompany truckCompany = null;                   //object

    private Fee fee = null;                                     //object
    private UIData feeDataTable = null;                         //jsf

    private Vehicle vehicle = null;                             //object
    private UIData vehicleDataTable = null;                     //jsf


    //Constructor (Spring-managed)
    public TruckCompanyForm(TruckCompanyManager truckCompanyManager) {
        this.truckCompanyManager = truckCompanyManager;

        String id = getParameter("id");
        if (id != null && truckCompany == null) {
            log.debug("Fetching a TruckCompany with id: [" + id + "]");

            try { truckCompany = truckCompanyManager.get(Long.valueOf(id)); }
            catch(Exception e) {
                log.warn(e);
                try { getResponse().sendRedirect("/404.jsp"); }
                catch (IOException e1) { e1.printStackTrace(); }
            }
        }
        else { truckCompany = new TruckCompany(); }


        fee = new Fee();

        vehicle = new Vehicle();

        setSortColumn("description");
        setAscending(true);

        log.debug("Created a new TruckCompanyForm object.");
    }


    //Getters
    public TruckCompany getTruckCompany() {
        return truckCompany;
    }

    public Fee getFee() {
        return fee;
    }
    public List getFees() {
        return sort(truckCompany.getFees());
    }
    public UIData getFeeDataTable() {
        return feeDataTable;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public List getVehicles() {
        return sort(truckCompany.getVehicles());
    }
    public UIData getVehicleDataTable() {
        return vehicleDataTable;
    }


    //Setters
    public void setTruckCompanyManager(TruckCompanyManager truckCompanyManager) {
        this.truckCompanyManager = truckCompanyManager;
    }
    public void setTruckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }
    public void setFeeDataTable(UIData feeDataTable) {
        this.feeDataTable = feeDataTable;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public void setVehicleDataTable(UIData vehicleDataTable) {
        this.vehicleDataTable = vehicleDataTable;
    }


    // Action Methods
    public String addFee() {
        log.debug(fee);
        truckCompany.addFee(fee);
        truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);

        fee = new Fee();
        return null;
    }
    public String removeFee() {
        Object dataRow = getFeeDataTable().getRowData();
        if (dataRow instanceof Fee) {
            truckCompany.removeFee((Fee) dataRow);
            truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);
        }
        return null;
    }
    public String onEditFee() {
        Object dataRow = getFeeDataTable().getRowData();
        if (dataRow instanceof Fee) {
            log.debug(dataRow);
            ( (Fee) dataRow ).setIsEditable(true);
        }
        return null;
    }
    public String offEditFee() {
        Object dataRow = getFeeDataTable().getRowData();
        if (dataRow instanceof Fee) {
            log.debug(dataRow);
            ( (Fee) dataRow ).setIsEditable(false);
        }
        return null;
    }
    public String saveEditFee() {
        log.debug("Trying to edit a fee ...");
        Object dataRow = getFeeDataTable().getRowData();
        if (dataRow instanceof Fee) {
            log.debug(dataRow);
            truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);
            ( (Fee) dataRow ).setIsEditable(false);
        }
        return null;
    }

    public String addVehicle() {
        log.debug(vehicle);
        truckCompany.addVehicle(vehicle);
        truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);

        vehicle = new Vehicle();
        return null;
    }
    public String removeVehicle() {
        Object dataRow = getVehicleDataTable().getRowData();
        if (dataRow instanceof Vehicle) {
            truckCompany.removeVehicle((Vehicle) dataRow);
            truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);
        }
        return null;
    }
    public String onEditVehicle() {
        Object dataRow = getVehicleDataTable().getRowData();
        if (dataRow instanceof Vehicle) {
            log.debug(dataRow);
            ( (Vehicle) dataRow ).setIsEditable(true);
        }
        return null;
    }
    public String offEditVehicle() {
        Object dataRow = getVehicleDataTable().getRowData();
        if (dataRow instanceof Vehicle) {
            log.debug(dataRow);
            ( (Vehicle) dataRow ).setIsEditable(false);
        }
        return null;
    }
    public String saveEditVehicle() {
        log.debug("Trying to edit a vehicle ...");
        Object dataRow = getVehicleDataTable().getRowData();
        if (dataRow instanceof Vehicle) {
            log.debug(dataRow);
            truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);
            ( (Vehicle) dataRow ).setIsEditable(false);
        }
        return null;
    }

    public String save() {
        /*Check whether customer is new*/
        boolean isNew = (truckCompany.getId() == null);

        try {
            truckCompany = truckCompanyManager.saveTruckCompany(truckCompany);
        } catch(Exception e) {
            log.error(truckCompany);
        }
        /*Add appropriate message*/
        String msg = (isNew) ? "truckCompany.added" : "truckCompany.updated";
        addMessage(msg);

        return (isNew) ? "list" : null;
    }
    public String delete() {
        truckCompanyManager.remove(truckCompany.getId());

        addMessage("truckCompany.deleted");

        return "list";
    }

}