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
import org.mysqltutorial.tomeeblobtest.entity.Productlines;
import org.mysqltutorial.tomeeblobtest.entity.Productlines_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mysqltutorial.tomeeblobtest.entity.Products;

/**
 *
 * @author kuw
 */
@Stateless
public class ProductlinesFacade extends AbstractFacade<Productlines> {

    @PersistenceContext(unitName = "TomeeBlobTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductlinesFacade() {
        super(Productlines.class);
    }

    public boolean isProductsListEmpty(Productlines entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Productlines> productlines = cq.from(Productlines.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(productlines, entity), cb.isNotEmpty(productlines.get(Productlines_.productsList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Products> findProductsList(Productlines entity) {
        Productlines mergedEntity = this.getMergedEntity(entity);
        List<Products> productsList = mergedEntity.getProductsList();
        productsList.size();
        return productsList;
    }
    
}
