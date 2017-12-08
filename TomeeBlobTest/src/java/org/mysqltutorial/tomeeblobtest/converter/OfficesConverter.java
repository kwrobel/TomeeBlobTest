package org.mysqltutorial.tomeeblobtest.converter;

import org.mysqltutorial.tomeeblobtest.entity.Offices;
import org.mysqltutorial.tomeeblobtest.facade.OfficesFacade;
import org.mysqltutorial.tomeeblobtest.controller.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "officesConverter")
public class OfficesConverter implements Converter {

    private OfficesFacade ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.getEjbFacade().find(getKey(value));
    }

    java.lang.String getKey(String value) {
        java.lang.String key;
        key = value;
        return key;
    }

    String getStringKey(java.lang.String value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Offices) {
            Offices o = (Offices) object;
            return getStringKey(o.getOfficeCode());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Offices.class.getName()});
            return null;
        }
    }

    private OfficesFacade getEjbFacade() {
        this.ejbFacade = CDI.current().select(OfficesFacade.class).get();
        return this.ejbFacade;
    }
}
