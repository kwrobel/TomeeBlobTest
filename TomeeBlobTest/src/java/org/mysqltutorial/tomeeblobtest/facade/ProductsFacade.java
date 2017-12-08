/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mysqltutorial.tomeeblobtest.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mysqltutorial.tomeeblobtest.entity.Products;
import org.mysqltutorial.tomeeblobtest.entity.Products_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import org.mysqltutorial.tomeeblobtest.entity.Productlines;

/**
 *
 * @author kuw
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Products> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }

    public boolean isOrderdetailsListEmpty(Products entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Products> products = cq.from(Products.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(products, entity), cb.isNotEmpty(products.get(Products_.orderdetailsList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Orderdetails> findOrderdetailsList(Products entity) {
        Products mergedEntity = this.getMergedEntity(entity);
        List<Orderdetails> orderdetailsList = mergedEntity.getOrderdetailsList();
        orderdetailsList.size();
        return orderdetailsList;
    }

    public boolean isProductLineEmpty(Products entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Products> products = cq.from(Products.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(products, entity), cb.isNotNull(products.get(Products_.productLine)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Productlines findProductLine(Products entity) {
        return this.getMergedEntity(entity).getProductLine();
    }
    
}
