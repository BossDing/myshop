package net.shopxx.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PreSellRoleDao;
import net.shopxx.entity.PreSellRole;

import org.springframework.stereotype.Repository;

@Repository("preSellRoleDaoImpl")
public class PreSellRoleDaoImpl extends BaseDaoImpl<PreSellRole, Long> implements PreSellRoleDao {

	@Override
	public List<PreSellRole> findList(Boolean hasBegun, Boolean hasEnded,
			Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PreSellRole> criteriaQuery = criteriaBuilder.createQuery(PreSellRole.class);
		Root<PreSellRole> root = criteriaQuery.from(PreSellRole.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(root.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasEnded != null) {
			if (hasEnded) {
				restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
			}
		}  
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	
//	public Page<PreSellRole> findPage(Pageable pageable1) {
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<PreSellRole> criteriaQuery = criteriaBuilder.createQuery(PreSellRole.class);
//		Root<PreSellRole> root = criteriaQuery.from(PreSellRole.class);
//		criteriaQuery.select(root);
//		return super.findPage(criteriaQuery, pageable1);
//	}
	
}
