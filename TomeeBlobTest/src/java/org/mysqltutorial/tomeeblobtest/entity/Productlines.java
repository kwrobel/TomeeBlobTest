/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mysqltutorial.tomeeblobtest.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kuw
 */
@Entity
@Table(name = "productlines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productlines.findAll", query = "SELECT p FROM Productlines p")
    , @NamedQuery(name = "Productlines.findByProductLine", query = "SELECT p FROM Productlines p WHERE p.productLine = :productLine")
    , @NamedQuery(name = "Productlines.findByTextDescription", query = "SELECT p FROM Productlines p WHERE p.textDescription = :textDescription")})
public class Productlines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "productLine")
    private String productLine;
    @Size(max = 4000)
    @Column(name = "textDescription")
    private String textDescription;
    @Lob
    @Size(max = 16777215)
    @Column(name = "htmlDescription")
    private String htmlDescription;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productLine")
    private List<Products> productsList;

    public Productlines() {
    }

    public Productlines(String productLine) {
        this.productLine = productLine;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @XmlTransient
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productLine != null ? productLine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productlines)) {
            return false;
        }
        Productlines other = (Productlines) object;
        if ((this.productLine == null && other.productLine != null) || (this.productLine != null && !this.productLine.equals(other.productLine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mysqltutorial.tomeeblobtest.entity.Productlines[ productLine=" + productLine + " ]";
    }
    
}
