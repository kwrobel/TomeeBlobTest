package org.mysqltutorial.tomeeblobtest.controller;

import org.mysqltutorial.tomeeblobtest.entity.Productlines;
import org.mysqltutorial.tomeeblobtest.entity.Products;
import java.util.List;
import org.mysqltutorial.tomeeblobtest.facade.ProductlinesFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "productlinesController")
@ViewScoped
public class ProductlinesController extends AbstractController<Productlines> {

    // Flags to indicate if child collections are empty
    private boolean isProductsListEmpty;

    public ProductlinesController() {
        // Inform the Abstract parent controller of the concrete Productlines Entity
        super(Productlines.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsProductsListEmpty();
    }

    public boolean getIsProductsListEmpty() {
        return this.isProductsListEmpty;
    }

    private void setIsProductsListEmpty() {
        Productlines selected = this.getSelected();
        if (selected != null) {
            ProductlinesFacade ejbFacade = (ProductlinesFacade) this.getFacade();
            this.isProductsListEmpty = ejbFacade.isProductsListEmpty(selected);
        } else {
            this.isProductsListEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Products entities that
     * are retrieved from Productlines and returns the navigation outcome.
     *
     * @return navigation outcome for Products page
     */
    public String navigateProductsList() {
        Productlines selected = this.getSelected();
        if (selected != null) {
            ProductlinesFacade ejbFacade = (ProductlinesFacade) this.getFacade();
            List<Products> selectedProductsList = ejbFacade.findProductsList(selected);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Products_items", selectedProductsList);
        }
        return "/app/products/index";
    }

}
