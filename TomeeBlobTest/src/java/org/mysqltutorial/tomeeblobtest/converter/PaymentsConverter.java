package org.mysqltutorial.tomeeblobtest.converter;

import org.mysqltutorial.tomeeblobtest.entity.Payments;
import org.mysqltutorial.tomeeblobtest.facade.PaymentsFacade;
import org.mysqltutorial.tomeeblobtest.controller.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "paymentsConverter")
public class PaymentsConverter implements Converter {

    private PaymentsFacade ejbFacade;

    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.getEjbFacade().find(getKey(value));
    }

    org.mysqltutorial.tomeeblobtest.entity.PaymentsPK getKey(String value) {
        org.mysqltutorial.tomeeblobtest.entity.PaymentsPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new org.mysqltutorial.tomeeblobtest.entity.PaymentsPK();
        key.setCustomerNumber(Integer.parseInt(values[0]));
        key.setCheckNumber(values[1]);
        return key;
    }

    String getStringKey(org.mysqltutorial.tomeeblobtest.entity.PaymentsPK value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value.getCustomerNumber());
        sb.append(SEPARATOR);
        sb.append(value.getCheckNumber());
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Payments) {
            Payments o = (Payments) object;
            return getStringKey(o.getPaymentsPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Payments.class.getName()});
            return null;
        }
    }

    private PaymentsFacade getEjbFacade() {
        this.ejbFacade = CDI.current().select(PaymentsFacade.class).get();
        return this.ejbFacade;
    }
}
