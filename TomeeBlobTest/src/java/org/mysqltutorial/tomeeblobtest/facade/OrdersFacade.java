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
import org.mysqltutorial.tomeeblobtest.entity.Orders;
import org.mysqltutorial.tomeeblobtest.entity.Orders_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Orderdetails;
import org.mysqltutorial.tomeeblobtest.entity.Customers;

/**
 *
 * @author kuw
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    public boolean isOrderdetailsListEmpty(Orders entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Orders> orders = cq.from(Orders.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(orders, entity), cb.isNotEmpty(orders.get(Orders_.orderdetailsList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Orderdetails> findOrderdetailsList(Orders entity) {
        Orders mergedEntity = this.getMergedEntity(entity);
        List<Orderdetails> orderdetailsList = mergedEntity.getOrderdetailsList();
        orderdetailsList.size();
        return orderdetailsList;
    }

    public boolean isCustomerNumberEmpty(Orders entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Orders> orders = cq.from(Orders.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(orders, entity), cb.isNotNull(orders.get(Orders_.customerNumber)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Customers findCustomerNumber(Orders entity) {
        return this.getMergedEntity(entity).getCustomerNumber();
    }
    
}
