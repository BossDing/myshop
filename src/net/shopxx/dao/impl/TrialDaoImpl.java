package net.shopxx.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.dao.TrialDao;
import net.shopxx.entity.Trial;

import org.springframework.stereotype.Repository;

/**
 * Dao - 试用
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Repository("trialDaoImpl")
public class TrialDaoImpl extends BaseDaoImpl<Trial, Long> implements TrialDao {

	public List<Trial> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trial> criteriaQuery = criteriaBuilder.createQuery(Trial.class);
		Root<Trial> root = criteriaQuery.from(Trial.class);
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

	@Override
	public List<Trial> findOrderByEndDateAndDate(Date date) {
		String jpql = "select trial from Trial trial where lower(to_char(trial.endDate,'yyyy-MM-dd HH:mm:ss')) > lower(to_char(:date,'yyyy-MM-dd HH:mm:ss')) order by trial.endDate asc";
		return entityManager.createQuery(jpql, Trial.class).setFlushMode(FlushModeType.COMMIT).setParameter("date", date).getResultList();
	}

}