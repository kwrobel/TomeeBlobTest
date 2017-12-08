package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Employees;
import org.mysqltutorial.tomeeblobtest.entity.Customers;
import org.mysqltutorial.tomeeblobtest.entity.Employees;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.EmployeesFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "employeesController")
@ViewScoped
public class EmployeesController extends AbstractController<Employees> {

    @Inject
    private EmployeesController reportsToController;
    @Inject
    private OfficesController officeCodeController;

    // Flags to indicate if child collections are empty
    private boolean isCustomersListEmpty;
    private boolean isEmployeesListEmpty;

    public EmployeesController() {
        // Inform the Abstract parent controller of the concrete Employees Entity
        super(Employees.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        reportsToController.setSelected(null);
        officeCodeController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCustomersListEmpty();
        this.setIsEmployeesListEmpty();
    }

    public boolean getIsCustomersListEmpty() {
        return this.isCustomersListEmpty;
    }

    private void setIsCustomersListEmpty() {
        Employees selected = this.getSelected();
        if (selected != null) {
            EmployeesFacade ejbFacade = (EmployeesFacade) this.getFacade();
            this.isCustomersListEmpty = ejbFacade.isCustomersListEmpty(selected);
        } else {
            this.isCustomersListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Customers entities that
     * are retrieved from Employees and returns the navigation outcome.
     *
     * @return navigation outcome for Customers page
     */
    public String navigateCustomersList() {
        Employees selected = this.getSelected();
        if (selected != null) {
            EmployeesFacade ejbFacade = (EmployeesFacade) this.getFacade();
            List<Customers> selectedCustomersList = ejbFacade.findCustomersList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Customers_items", selectedCustomersList);
        }
        return "/app/customers/index";
    }

    public boolean getIsEmployeesListEmpty() {
        return this.isEmployeesListEmpty;
    }

    private void setIsEmployeesListEmpty() {
        Employees selected = this.getSelected();
        if (selected != null) {
            EmployeesFacade ejbFacade = (EmployeesFacade) this.getFacade();
            this.isEmployeesListEmpty = ejbFacade.isEmployeesListEmpty(selected);
        } else {
            this.isEmployeesListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Employees entities that
     * are retrieved from Employees and returns the navigation outcome.
     *
     * @return navigation outcome for Employees page
     */
    public String navigateEmployeesList() {
        Employees selected = this.getSelected();
        if (selected != null) {
            EmployeesFacade ejbFacade = (EmployeesFacade) this.getFacade();
            List<Employees> selectedEmployeesList = ejbFacade.findEmployeesList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Employees_items", selectedEmployeesList);
        }
        return "/app/employees/index";
    }

    /**
     * Sets the "selected" attribute of the Employees controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareReportsTo(ActionEvent event) {
        Employees selected = this.getSelected();
        if (selected != null && reportsToController.getSelected() == null) {
            reportsToController.setSelected(selected.getReportsTo());
        }
    }

    /**
     * Sets the "selected" attribute of the Offices controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareOfficeCode(ActionEvent event) {
        Employees selected = this.getSelected();
        if (selected != null && officeCodeController.getSelected() == null) {
            officeCodeController.setSelected(selected.getOfficeCode());
        }
    }

}
