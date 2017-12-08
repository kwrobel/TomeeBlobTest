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
import org.mysqltutorial.tomeeblobtest.entity.Customers;
import org.mysqltutorial.tomeeblobtest.entity.Customers_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Payments;
import org.mysqltutorial.tomeeblobtest.entity.Orders;
import org.mysqltutorial.tomeeblobtest.entity.Employees;

/**
 *
 * @author kuw
 */
@Stateless
public class CustomersFacade extends AbstractFacade<Customers> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersFacade() {
        super(Customers.class);
    }

    public boolean isPaymentsListEmpty(Customers entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Customers> customers = cq.from(Customers.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(customers, entity), cb.isNotEmpty(customers.get(Customers_.paymentsList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Payments> findPaymentsList(Customers entity) {
        Customers mergedEntity = this.getMergedEntity(entity);
        List<Payments> paymentsList = mergedEntity.getPaymentsList();
        paymentsList.size();
        return paymentsList;
    }

    public boolean isOrdersListEmpty(Customers entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Customers> customers = cq.from(Customers.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(customers, entity), cb.isNotEmpty(customers.get(Customers_.ordersList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Orders> findOrdersList(Customers entity) {
        Customers mergedEntity = this.getMergedEntity(entity);
        List<Orders> ordersList = mergedEntity.getOrdersList();
        ordersList.size();
        return ordersList;
    }

    public boolean isSalesRepEmployeeNumberEmpty(Customers entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Customers> customers = cq.from(Customers.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(customers, entity), cb.isNotNull(customers.get(Customers_.salesRepEmployeeNumber)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Employees findSalesRepEmployeeNumber(Customers entity) {
        return this.getMergedEntity(entity).getSalesRepEmployeeNumber();
    }
    
}
