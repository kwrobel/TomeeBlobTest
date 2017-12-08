package org.mysqltutorial.tomeeblobtest.converter;

import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import org.mysqltutorial.tomeeblobtest.facade.OrderdetailsFacade;
import org.mysqltutorial.tomeeblobtest.controller.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "orderdetailsConverter")
public class OrderdetailsConverter implements Converter {

    private OrderdetailsFacade ejbFacade;

    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.getEjbFacade().find(getKey(value));
    }

    org.mysqltutorial.tomeeblobtest.entity.OrderdetailsPK getKey(String value) {
        org.mysqltutorial.tomeeblobtest.entity.OrderdetailsPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new org.mysqltutorial.tomeeblobtest.entity.OrderdetailsPK();
        key.setOrderNumber(Integer.parseInt(values[0]));
        key.setProductCode(values[1]);
        return key;
    }

    String getStringKey(org.mysqltutorial.tomeeblobtest.entity.OrderdetailsPK value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value.getOrderNumber());
        sb.append(SEPARATOR);
        sb.append(value.getProductCode());
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Orderdetails) {
            Orderdetails o = (Orderdetails) object;
            return getStringKey(o.getOrderdetailsPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Orderdetails.class.getName()});
            return null;
        }
    }

    private OrderdetailsFacade getEjbFacade() {
        this.ejbFacade = CDI.current().select(OrderdetailsFacade.class).get();
        return this.ejbFacade;
    }
}
