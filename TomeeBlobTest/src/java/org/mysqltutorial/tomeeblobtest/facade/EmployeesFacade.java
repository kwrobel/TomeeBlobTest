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
import org.mysqltutorial.tomeeblobtest.entity.Employees;
import org.mysqltutorial.tomeeblobtest.entity.Employees_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Customers;
import org.mysqltutorial.tomeeblobtest.entity.Employees;
import org.mysqltutorial.tomeeblobtest.entity.Employees;
import org.mysqltutorial.tomeeblobtest.entity.Offices;

/**
 *
 * @author kuw
 */
@Stateless
public class EmployeesFacade extends AbstractFacade<Employees> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeesFacade() {
        super(Employees.class);
    }

    public boolean isCustomersListEmpty(Employees entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employees> employees = cq.from(Employees.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(employees, entity), cb.isNotEmpty(employees.get(Employees_.customersList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Customers> findCustomersList(Employees entity) {
        Employees mergedEntity = this.getMergedEntity(entity);
        List<Customers> customersList = mergedEntity.getCustomersList();
        customersList.size();
        return customersList;
    }

    public boolean isEmployeesListEmpty(Employees entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employees> employees = cq.from(Employees.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(employees, entity), cb.isNotEmpty(employees.get(Employees_.employeesList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Employees> findEmployeesList(Employees entity) {
        Employees mergedEntity = this.getMergedEntity(entity);
        List<Employees> employeesList = mergedEntity.getEmployeesList();
        employeesList.size();
        return employeesList;
    }

    public boolean isReportsToEmpty(Employees entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employees> employees = cq.from(Employees.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(employees, entity), cb.isNotNull(employees.get(Employees_.reportsTo)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Employees findReportsTo(Employees entity) {
        return this.getMergedEntity(entity).getReportsTo();
    }

    public boolean isOfficeCodeEmpty(Employees entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employees> employees = cq.from(Employees.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(employees, entity), cb.isNotNull(employees.get(Employees_.officeCode)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Offices findOfficeCode(Employees entity) {
        return this.getMergedEntity(entity).getOfficeCode();
    }
    
}
