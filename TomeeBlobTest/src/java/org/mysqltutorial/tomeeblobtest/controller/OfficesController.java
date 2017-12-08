package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Offices;
import org.mysqltutorial.tomeeblobtest.entity.Employees;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.OfficesFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "officesController")
@ViewScoped
public class OfficesController extends AbstractController<Offices> {

    // Flags to indicate if child collections are empty
    private boolean isEmployeesListEmpty;

    public OfficesController() {
        // Inform the Abstract parent controller of the concrete Offices Entity
        super(Offices.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsEmployeesListEmpty();
    }

    public boolean getIsEmployeesListEmpty() {
        return this.isEmployeesListEmpty;
    }

    private void setIsEmployeesListEmpty() {
        Offices selected = this.getSelected();
        if (selected != null) {
            OfficesFacade ejbFacade = (OfficesFacade) this.getFacade();
            this.isEmployeesListEmpty = ejbFacade.isEmployeesListEmpty(selected);
        } else {
            this.isEmployeesListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Employees entities that
     * are retrieved from Offices and returns the navigation outcome.
     *
     * @return navigation outcome for Employees page
     */
    public String navigateEmployeesList() {
        Offices selected = this.getSelected();
        if (selected != null) {
            OfficesFacade ejbFacade = (OfficesFacade) this.getFacade();
            List<Employees> selectedEmployeesList = ejbFacade.findEmployeesList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Employees_items", selectedEmployeesList);
        }
        return "/app/employees/index";
    }

}
