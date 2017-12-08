package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Customers;
import org.mysqltutorial.tomeeblobtest.entity.Payments;
import org.mysqltutorial.tomeeblobtest.entity.Orders;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.CustomersFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "customersController")
@ViewScoped
public class CustomersController extends AbstractController<Customers> {

    @Inject
    private EmployeesController salesRepEmployeeNumberController;

    // Flags to indicate if child collections are empty
    private boolean isPaymentsListEmpty;
    private boolean isOrdersListEmpty;

    public CustomersController() {
        // Inform the Abstract parent controller of the concrete Customers Entity
        super(Customers.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        salesRepEmployeeNumberController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPaymentsListEmpty();
        this.setIsOrdersListEmpty();
    }

    public boolean getIsPaymentsListEmpty() {
        return this.isPaymentsListEmpty;
    }

    private void setIsPaymentsListEmpty() {
        Customers selected = this.getSelected();
        if (selected != null) {
            CustomersFacade ejbFacade = (CustomersFacade) this.getFacade();
            this.isPaymentsListEmpty = ejbFacade.isPaymentsListEmpty(selected);
        } else {
            this.isPaymentsListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Payments entities that
     * are retrieved from Customers and returns the navigation outcome.
     *
     * @return navigation outcome for Payments page
     */
    public String navigatePaymentsList() {
        Customers selected = this.getSelected();
        if (selected != null) {
            CustomersFacade ejbFacade = (CustomersFacade) this.getFacade();
            List<Payments> selectedPaymentsList = ejbFacade.findPaymentsList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Payments_items", selectedPaymentsList);
        }
        return "/app/payments/index";
    }

    public boolean getIsOrdersListEmpty() {
        return this.isOrdersListEmpty;
    }

    private void setIsOrdersListEmpty() {
        Customers selected = this.getSelected();
        if (selected != null) {
            CustomersFacade ejbFacade = (CustomersFacade) this.getFacade();
            this.isOrdersListEmpty = ejbFacade.isOrdersListEmpty(selected);
        } else {
            this.isOrdersListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Orders entities that are
     * retrieved from Customers and returns the navigation outcome.
     *
     * @return navigation outcome for Orders page
     */
    public String navigateOrdersList() {
        Customers selected = this.getSelected();
        if (selected != null) {
            CustomersFacade ejbFacade = (CustomersFacade) this.getFacade();
            List<Orders> selectedOrdersList = ejbFacade.findOrdersList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Orders_items", selectedOrdersList);
        }
        return "/app/orders/index";
    }

    /**
     * Sets the "selected" attribute of the Employees controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareSalesRepEmployeeNumber(ActionEvent event) {
        Customers selected = this.getSelected();
        if (selected != null && salesRepEmployeeNumberController.getSelected() == null) {
            salesRepEmployeeNumberController.setSelected(selected.getSalesRepEmployeeNumber());
        }
    }

}
