package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.dao.ExperienceApplyDao;
import net.shopxx.dao.ExperienceDao;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;

import org.springframework.stereotype.Repository;

/**
 * Dao - 体验中心
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Repository("experienceApplyDaoImpl")
public class ExperienceApplyDaoImpl extends BaseDaoImpl<ExperienceApply, Long> implements ExperienceApplyDao {
  
	public List<ExperienceApply> findList(Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExperienceApply> criteriaQuery = criteriaBuilder.createQuery(ExperienceApply.class);
		Root<ExperienceApply> root = criteriaQuery.from(ExperienceApply.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}