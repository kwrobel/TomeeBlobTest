package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Products;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.ProductsFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productsController")
@ViewScoped
public class ProductsController extends AbstractController<Products> {

    @Inject
    private ProductlinesController productLineController;

    // Flags to indicate if child collections are empty
    private boolean isOrderdetailsListEmpty;

    public ProductsController() {
        // Inform the Abstract parent controller of the concrete Products Entity
        super(Products.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productLineController.setSelected(null);
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
        Products selected = this.getSelected();
        if (selected != null) {
            ProductsFacade ejbFacade = (ProductsFacade) this.getFacade();
            this.isOrderdetailsListEmpty = ejbFacade.isOrderdetailsListEmpty(selected);
        } else {
            this.isOrderdetailsListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Orderdetails entities
     * that are retrieved from Products and returns the navigation outcome.
     *
     * @return navigation outcome for Orderdetails page
     */
    public String navigateOrderdetailsList() {
        Products selected = this.getSelected();
        if (selected != null) {
            ProductsFacade ejbFacade = (ProductsFacade) this.getFacade();
            List<Orderdetails> selectedOrderdetailsList = ejbFacade.findOrderdetailsList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Orderdetails_items", selectedOrderdetailsList);
        }
        return "/app/orderdetails/index";
    }

    /**
     * Sets the "selected" attribute of the Productlines controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductLine(ActionEvent event) {
        Products selected = this.getSelected();
        if (selected != null && productLineController.getSelected() == null) {
            productLineController.setSelected(selected.getProductLine());
        }
    }

}
