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
import org.mysqltutorial.tomeeblobtest.entity.Offices;
import org.mysqltutorial.tomeeblobtest.entity.Offices_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Employees;

/**
 *
 * @author kuw
 */
@Stateless
public class OfficesFacade extends AbstractFacade<Offices> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OfficesFacade() {
        super(Offices.class);
    }

    public boolean isEmployeesListEmpty(Offices entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Offices> offices = cq.from(Offices.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(offices, entity), cb.isNotEmpty(offices.get(Offices_.employeesList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Employees> findEmployeesList(Offices entity) {
        Offices mergedEntity = this.getMergedEntity(entity);
        List<Employees> employeesList = mergedEntity.getEmployeesList();
        employeesList.size();
        return employeesList;
    }
    
}
