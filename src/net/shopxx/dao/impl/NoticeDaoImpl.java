package net.shopxx.dao.impl;

import java.util.Collections;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.NoticeDao;
import net.shopxx.entity.Notice;
import net.shopxx.entity.Product;

@Repository("noticeDaoImpl")
public class NoticeDaoImpl extends BaseDaoImpl<Notice,Long> implements NoticeDao {

	public Page<Notice> findPublishedPage(Pageable pageable){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Notice> criteriaQuery = criteriaBuilder.createQuery(Notice.class);
		Root<Notice> root = criteriaQuery.from(Notice.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("isPublication"), true));
		return super.findPage(criteriaQuery, pageable);
	}
}
