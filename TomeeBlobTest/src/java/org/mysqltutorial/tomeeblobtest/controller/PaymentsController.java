package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Payments;
import org.mysqltutorial.tomeeblobtest.facade.PaymentsFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "paymentsController")
@ViewScoped
public class PaymentsController extends AbstractController<Payments> {

    @Inject
    private CustomersController customersController;

    public PaymentsController() {
        // Inform the Abstract parent controller of the concrete Payments Entity
        super(Payments.class);
    }

    @Override
    protected void setEmbeddableKeys() {
        this.getSelected().getPaymentsPK().setCustomerNumber(this.getSelected().getCustomers().getCustomerNumber());
    }

    @Override
    protected void initializeEmbeddableKey() {
        this.getSelected().setPaymentsPK(new org.mysqltutorial.tomeeblobtest.entity.PaymentsPK());
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        customersController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Customers controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCustomers(ActionEvent event) {
        Payments selected = this.getSelected();
        if (selected != null && customersController.getSelected() == null) {
            customersController.setSelected(selected.getCustomers());
        }
    }

}
