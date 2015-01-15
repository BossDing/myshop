package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ExtensionDao;
import net.shopxx.entity.Extension;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;

@Repository("extensionDaoImpl")
public class ExtensionDaoImpl extends BaseDaoImpl<Extension, Long> implements ExtensionDao {

	public Extension findByMember(String username) {
		// TODO Auto-generated method stub
//		System.out.println("dao");
		String jpql = "select extension from Extension extension where lower(extension.cardNo) = lower(:cardNo)";
//		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("userName", username).getSingleResult();
//		return count > 0;
		try {
			return entityManager.createQuery(jpql, Extension.class).setFlushMode(FlushModeType.COMMIT).setParameter("cardNo", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
