package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Orders;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.OrdersFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "ordersController")
@ViewScoped
public class OrdersController extends AbstractController<Orders> {

    @Inject
    private CustomersController customerNumberController;

    // Flags to indicate if child collections are empty
    private boolean isOrderdetailsListEmpty;

    public OrdersController() {
        // Inform the Abstract parent controller of the concrete Orders Entity
        super(Orders.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        customerNumberController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsOrderdetailsListEmpty();
    }

    public boolean getIsOrderdetailsListEmpty() {
        return this.isOrderdetailsListEmpty;
    }

    private void setIsOrderdetailsListEmpty() {
        Orders selected = this.getSelected();
        if (selected != null) {
            OrdersFacade ejbFacade = (OrdersFacade) this.getFacade();
            this.isOrderdetailsListEmpty = ejbFacade.isOrderdetailsListEmpty(selected);
        } else {
            this.isOrderdetailsListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Orderdetails entities
     * that are retrieved from Orders and returns the navigation outcome.
     *
     * @return navigation outcome for Orderdetails page
     */
    public String navigateOrderdetailsList() {
        Orders selected = this.getSelected();
        if (selected != null) {
            OrdersFacade ejbFacade = (OrdersFacade) this.getFacade();
            List<Orderdetails> selectedOrderdetailsList = ejbFacade.findOrderdetailsList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Orderdetails_items", selectedOrderdetailsList);
        }
        return "/app/orderdetails/index";
    }

    /**
     * Sets the "selected" attribute of the Customers controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCustomerNumber(ActionEvent event) {
        Orders selected = this.getSelected();
        if (selected != null && customerNumberController.getSelected() == null) {
            customerNumberController.setSelected(selected.getCustomerNumber());
        }
    }

}
