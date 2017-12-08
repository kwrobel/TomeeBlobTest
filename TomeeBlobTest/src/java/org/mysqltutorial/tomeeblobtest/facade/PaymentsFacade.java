/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mysqltutorial.tomeeblobtest.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mysqltutorial.tomeeblobtest.entity.Payments;
import org.mysqltutorial.tomeeblobtest.entity.Payments_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Customers;

/**
 *
 * @author kuw
 */
@Stateless
public class PaymentsFacade extends AbstractFacade<Payments> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaymentsFacade() {
        super(Payments.class);
    }

    public boolean isCustomersEmpty(Payments entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Payments> payments = cq.from(Payments.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(payments, entity), cb.isNotNull(payments.get(Payments_.customers)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Customers findCustomers(Payments entity) {
        return this.getMergedEntity(entity).getCustomers();
    }
    
}
