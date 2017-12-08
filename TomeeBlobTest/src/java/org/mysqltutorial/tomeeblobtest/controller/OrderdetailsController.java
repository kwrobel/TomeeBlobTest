package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import org.mysqltutorial.tomeeblobtest.facade.OrderdetailsFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "orderdetailsController")
@ViewScoped
public class OrderdetailsController extends AbstractController<Orderdetails> {

    @Inject
    private OrdersController ordersController;
    @Inject
    private ProductsController productsController;

    public OrderdetailsController() {
        // Inform the Abstract parent controller of the concrete Orderdetails Entity
        super(Orderdetails.class);
    }

    @Override
    protected void setEmbeddableKeys() {
        this.getSelected().getOrderdetailsPK().setOrderNumber(this.getSelected().getOrders().getOrderNumber());
        this.getSelected().getOrderdetailsPK().setProductCode(this.getSelected().getProducts().getProductCode());
    }

    @Override
    protected void initializeEmbeddableKey() {
        this.getSelected().setOrderdetailsPK(new org.mysqltutorial.tomeeblobtest.entity.OrderdetailsPK());
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        ordersController.setSelected(null);
        productsController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Orders controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareOrders(ActionEvent event) {
        Orderdetails selected = this.getSelected();
        if (selected != null && ordersController.getSelected() == null) {
            ordersController.setSelected(selected.getOrders());
        }
    }

    /**
     * Sets the "selected" attribute of the Products controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProducts(ActionEvent event) {
        Orderdetails selected = this.getSelected();
        if (selected != null && productsController.getSelected() == null) {
            productsController.setSelected(selected.getProducts());
        }
    }

}
