package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.LeaveWordsDao;
import net.shopxx.entity.Consultation;
import net.shopxx.entity.LeaveWords;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.Product;
import net.shopxx.util.WebUtils;
@Repository("leaveWordsDaoImpl")
public class LeaveWordsDaoImpl extends BaseDaoImpl<LeaveWords,Long> implements LeaveWordsDao {

	public Page<LeaveWords> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LeaveWords> criteriaQuery = criteriaBuilder.createQuery(LeaveWords.class);
		Root<LeaveWords> root = criteriaQuery.from(LeaveWords.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forLeaveWords")));
		Predicate p1 = criteriaBuilder.equal(root.get("consultationType"), "1");
		Predicate p2 = criteriaBuilder.equal(root.get("consultationType"), "2");
		Predicate p3 = criteriaBuilder.equal(root.get("consultationType"), "3");
		Predicate p4 = criteriaBuilder.equal(root.get("consultationType"), "4");
		Predicate p5 = criteriaBuilder.equal(root.get("consultationType"), "5");
		Predicate p6 = criteriaBuilder.equal(root.get("consultationType"), "6");
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(p1, p2,p3,p4,p5,p6));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<LeaveWords> findPage(Long label ,Pageable pageable){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LeaveWords> criteriaQuery = criteriaBuilder.createQuery(LeaveWords.class);
		Root<LeaveWords> root = criteriaQuery.from(LeaveWords.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forLeaveWords")));
		if(label == 1){
			Predicate p1 = criteriaBuilder.equal(root.get("consultationType"), "1");
			Predicate p2 = criteriaBuilder.equal(root.get("consultationType"), "2");
			Predicate p3 = criteriaBuilder.equal(root.get("consultationType"), "3");
			Predicate p4 = criteriaBuilder.equal(root.get("consultationType"), "4");
			Predicate p5 = criteriaBuilder.equal(root.get("consultationType"), "5");
			Predicate p6 = criteriaBuilder.equal(root.get("consultationType"), "6");
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(p1, p2,p3,p4,p5,p6));
		}else if(label == 2){
			Predicate p7 = criteriaBuilder.equal(root.get("consultationType"), "7");
			Predicate p8 = criteriaBuilder.equal(root.get("consultationType"), "8");
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(p7, p8));
		}
		String entcode = WebUtils.getXentcode();
		if (StringUtils.isNotEmpty(entcode)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}
