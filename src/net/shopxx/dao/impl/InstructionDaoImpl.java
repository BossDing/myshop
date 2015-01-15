package net.shopxx.dao.impl;



import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.shopxx.dao.InstructionDao;
import net.shopxx.entity.Instruction;
import net.shopxx.entity.Product;
import net.shopxx.entity.Store;
import net.shopxx.util.WebUtils;

@Repository("instructionDaoImpl")
public class InstructionDaoImpl extends BaseDaoImpl<Instruction, Long> implements
		InstructionDao {

	@Override
	public List<Instruction> search(String keyword, Integer count) {
//		System.out.println("到dao---search");
		if (StringUtils.isEmpty(keyword)) {
			return Collections.<Instruction> emptyList();
		}
		keyword = keyword.toLowerCase();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instruction> criteriaQuery = criteriaBuilder
				.createQuery(Instruction.class);
		Root<Instruction> root = criteriaQuery.from(Instruction.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("name")), "%" + keyword + "%")));
		criteriaQuery.where(restrictions);
//		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	@Override
	public Instruction findByInstructionName(String name) {
//		System.out.println("进入dao---findByInstructionName");
		if (name == null) {
//			System.out.println("null");
			return null;
		}
		String jpql = "select instruction from Instruction instruction where lower(Instruction.name) = lower(:name)";
//		Store store = WebUtils.getStore();
//		if (store != null) {
//			jpql += " and product.store=:store";
//		}
		try {
			TypedQuery<Instruction> query = entityManager.createQuery(jpql,
					Instruction.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("name", name);
//			if (store != null) {
//				query.setParameter("store", store);
//			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
