/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mysqltutorial.tomeeblobtest.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Orders;
import org.mysqltutorial.tomeeblobtest.entity.Products;

/**
 *
 * @author kuw
 */
@Stateless
public class OrderdetailsFacade extends AbstractFacade<Orderdetails> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderdetailsFacade() {
        super(Orderdetails.class);
    }

    public boolean isOrdersEmpty(Orderdetails entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Orderdetails> orderdetails = cq.from(Orderdetails.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(orderdetails, entity), cb.isNotNull(orderdetails.get(Orderdetails_.orders)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Orders findOrders(Orderdetails entity) {
        return this.getMergedEntity(entity).getOrders();
    }

    public boolean isProductsEmpty(Orderdetails entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Orderdetails> orderdetails = cq.from(Orderdetails.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(orderdetails, entity), cb.isNotNull(orderdetails.get(Orderdetails_.products)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Products findProducts(Orderdetails entity) {
        return this.getMergedEntity(entity).getProducts();
    }
    
}
