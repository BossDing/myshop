/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Pageable;
import net.shopxx.controller.app.BaseController;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.service.DepositService;
import net.shopxx.service.MemberService;
import net.shopxx.util.TwUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 预存款
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPDepositController")
@RequestMapping("/m/member/deposit")
public class DepositController extends BaseController {

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	/**
	 * 余额支付
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> depositList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Deposit deposit: depositService.findPage(member, pageable).getContent()) {
				HashMap<String, Object> depositMap = new HashMap<String, Object>();
				depositMap.put("id", deposit.getId());
				depositMap.put("type", TwUtil.getIntDepositType(deposit.getType().toString()));
				depositMap.put("credit", deposit.getCredit().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				depositMap.put("debit", deposit.getDebit().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				depositMap.put("balance", deposit.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				depositMap.put("sn", deposit.getOrder() == null ? "" : deposit.getOrder().getSn());
				depositMap.put("createdate", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(deposit.getCreateDate()));
				depositList.add(depositMap);
			}
			
			model.put("datas", depositList);
			model.put("length", depositList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
}