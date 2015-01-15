/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.Setting;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.Setting.ReviewAuthority;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.Review;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReviewService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 评论
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopReviewController")
@RequestMapping("/review")
public class ReviewController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	/**
	 * 发表
	 */
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public String add(@PathVariable Long id, Long orderItemId ,Integer pageNumber, Integer pageSize , String scrollSide,ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			throw new ResourceNotFoundException();
		}
		if ((pageNumber == null) || (pageNumber.equals(new Integer(0)))) {
	        pageNumber = new Integer(1);
	      }
	      if ((pageSize == null) || (pageSize.equals(new Integer(0)))) {
	        pageSize = new Integer(4);
	      }
	      Pageable pageable = new Pageable(pageNumber, pageSize);
	      Product product = null;
	      OrderItem orderItem = null;
	      if(orderItemId != null){
	    	  orderItem = orderItemService.find(orderItemId);
	    	  if(orderItem != null){
	    		  product = orderItem.getProduct();
	    	  }
	      }else{
	    	  product = productService.find(id);
	      }
	      if (product == null) {
				throw new ResourceNotFoundException();
			}
		Member member = memberService.getCurrent();
		Long scoreCount = product.getScoreCount();
		Long positiveCount = reviewService.count(null, product, Review.Type.positive, true);
		Long moderateCount = reviewService.count(null, product, Review.Type.moderate, true);
		Long negativeCount = reviewService.count(null, product, Review.Type.negative, true);
		if(positiveCount+moderateCount+negativeCount>scoreCount){
			scoreCount = reviewService.count(null, product, null, true);
		}
		model.addAttribute("pageNumber", pageNumber);
	    model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", reviewService.findPage(null, product, null, true, pageable));
		model.addAttribute("positiveRate", WebUtils.getPercent(positiveCount, scoreCount));
		model.addAttribute("moderateRate", WebUtils.getPercent(moderateCount, scoreCount));
		model.addAttribute("negativeRate", WebUtils.getPercent(negativeCount, scoreCount));
		model.addAttribute("product", product);
		model.addAttribute("orderItem", orderItem);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		if(scrollSide != null){
			model.addAttribute("scrollSide", scrollSide);
		}
		return "/shop/review/add";
	}

	/**
	 * 内容
	 */
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
	public String content(@PathVariable Long id, Long orderItemId,Integer pageNumber, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = null;
		OrderItem orderItem = null;
		if(orderItemId != null){
		orderItem = orderItemService.find(id);
		}
		product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("product", product);
		model.addAttribute("page", reviewService.findPage(null, product, null, true, pageable));
		return "/shop/review/content";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String captchaId, String captcha, Long id, Long orderItemId ,Integer score, String content, HttpServletRequest request) {
		if (!captchaService.isValid(CaptchaType.review, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			return Message.error("shop.review.disabled");
		}
		if (!isValid(Review.class, "score", score) || !isValid(Review.class, "content", content)) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (setting.getReviewAuthority() != ReviewAuthority.anyone && member == null) {
			return Message.error("shop.review.accessDenied");
		}
		Product product = null;
		OrderItem orderItem = null;
		Order order = null;
		List<OrderItem> orderItems = null;
		if(orderItemId != null){
	    	  orderItem = orderItemService.find(orderItemId);
	    	  if(orderItem != null){
	    		  product = orderItem.getProduct();
	    	  }
	    	  order = orderItem.getOrder();
	      }else{
	    	  product = productService.find(id);
	    	  orderItems = orderItemService.toOrderItem(product, member);
	  		if(orderItems.size()>0){
	  			orderItem = orderItems.get(0);
	  			order = orderItem.getOrder();
	  		}
	      }
			if (product == null) {
				return ERROR_MESSAGE;
			}
		if (setting.getReviewAuthority() == ReviewAuthority.purchased) {
			if (!productService.isPurchased(member, product)) {
				return Message.error("shop.review.noPurchased");
			}
			if (orderItem == null) {
				return Message.error("shop.review.reviewed");
			}
		}
		Review review = new Review();
		review.setScore(score);
		review.setContent(content);
		review.setIp(request.getRemoteAddr());
		review.setMember(member);
		review.setProduct(product);
		if (setting.getIsReviewCheck()) {
			review.setIsShow(false);
			reviewService.save(review);
			orderItem.setReviewStatus(true);
			orderItemService.update(orderItem);
			if(orderItemService.countUnreview(order) ==0 && Order.ReviewStatus.unreview.equals(order.getReviewStatus())){
				order.setReviewStatus(Order.ReviewStatus.reviewed);
				orderService.update(order);
			}
			return Message.success("shop.review.check");
		} else {
			review.setIsShow(true);
			reviewService.save(review);
			orderItem.setReviewStatus(true);
			orderItemService.update(orderItem);
			if(orderItemService.countUnreview(order) ==0  && Order.ReviewStatus.unreview.equals(order.getReviewStatus())){
				order.setReviewStatus(Order.ReviewStatus.reviewed);
				orderService.update(order);
			}
			return Message.success("shop.review.success");
		}
	}

	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public @ResponseBody
	Message reply( Long id, Long forReviewId ,String content, HttpServletRequest request) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			return Message.error("shop.review.disabled");
		}
		Product product = productService.find(id);
		if (product == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (setting.getReviewAuthority() != ReviewAuthority.anyone && member == null) {
			return Message.error("shop.review.accessDenied");
		}
		Review forReview = reviewService.find(forReviewId);
		Review review = new Review();
		review.setContent(content);
		review.setIp(request.getRemoteAddr());
		review.setMember(member);
		review.setProduct(product);
		review.setForReview(forReview);
		review.setIsShow(true);
		reviewService.save(review);
		return Message.success("shop.review.success");
		
	}
}