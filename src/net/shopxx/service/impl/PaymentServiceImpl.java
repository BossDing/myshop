/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PaymentDao;
import net.shopxx.dao.PointsWaterDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.Order;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PointsWater;
import net.shopxx.entity.Store;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.Payment.Status;
import net.shopxx.entity.Payment.Type;
import net.shopxx.service.MemberPointsService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.PointsWaterService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 收款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl extends BaseServiceImpl<Payment, Long> implements PaymentService {

	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberPointsServiceImpl")
	private MemberPointsService memberPointsService;
	@Resource(name = "pointsWaterDaoImpl")
	private PointsWaterDao pointsWaterDao;
	@Resource(name = "pointsWaterServiceImpl")
	private PointsWaterService pointsWaterService;

	@Resource(name = "paymentDaoImpl")
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}

	@Transactional(readOnly = true)
	public Payment findBySn(String sn) {
		return paymentDao.findBySn(sn);
	}

	public void handle(Payment payment) {
		paymentDao.refresh(payment, LockModeType.PESSIMISTIC_WRITE);
		if (payment != null && payment.getStatus() == Status.wait) {
			if (payment.getType() == Type.payment) {
				Order order = payment.getOrder();
				if (order != null&&null!=order.getType()&&order.getType().equals("0")) {
					
					orderService.payment(order, payment, null);
				}else if(null!=order&&null!=order.getType()&&order.getType().equals("1")){
					
					orderService.payment(order, payment, null);
					/**修改支付人的积分*/
					Member member=order.getMember();
					member.setPoint(member.getPoint()-order.getLowPoints());
					memberService.update(member);
					/**更新订单*/
					order.setLowPointsPaid(order.getLowPoints());
					order.setOrderStatus(OrderStatus.confirmed);
					order.setPaymentStatus(PaymentStatus.paid);
					orderService.update(order);
					/**插入积分流水**/
					PointsWater pointWater = new PointsWater();
					pointWater.setCreateDate(order.getModifyDate());
					pointWater.setRulename("积分兑换");
					pointWater.setMember_id(member.getId());
					pointWater.setPoints_stat(2);
					pointWater.setPoints(Integer.valueOf(order.getLowPointsPaid().toString()));
					pointWater.setOrder(order);
					pointsWaterService.save(pointWater);
				}
			} else if (payment.getType() == Type.recharge) {
				Member member = payment.getMember();
				if (member != null) {
					memberService.update(member, null, payment.getEffectiveAmount(), null, null);
				}
			}
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			paymentDao.merge(payment);
		}
	}
	
	@Transactional(readOnly = true)
	public Page<Payment> findPage(Pageable pageable) {
		return paymentDao.findPage(pageable);
	}
	
	@Transactional
	public List<Payment> findListForDeleteStore(Store store){
		return paymentDao.findListForDeleteStore(store);
	}
}