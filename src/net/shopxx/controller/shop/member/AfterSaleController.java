package net.shopxx.controller.shop.member;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.shopxx.Message;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Refunds;
import net.shopxx.entity.Returns;
import net.shopxx.service.GroupService;
import net.shopxx.service.RefundsService;
import net.shopxx.service.ReturnsService;

@Controller("shopMemberAfterSaleController")
@RequestMapping("/member/afterSale")
public class AfterSaleController extends BaseController {
	
	@Resource(name = "returnsServiceImpl")
	private ReturnsService returnsService;
	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String List(String orderNo, String returnOrExchangeNo, Integer afterSaleStatus, Integer afterSaleType, String logisticsNo,
			String begindate, String enddate, Integer pageNumber, Integer pageSize,Model model){
		System.out.println("afterSale-list");
		System.out.println("orderNo="+orderNo);
		System.out.println("returnOrExchangeNo="+returnOrExchangeNo);
		System.out.println("afterSaleStatus="+afterSaleStatus);
		System.out.println("afterSaleType="+afterSaleType);
		System.out.println("logisticsNo="+logisticsNo);
		System.out.println("begindate="+begindate);
		System.out.println("enddate="+enddate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date endDate = null;
		try {
			if(begindate!=null){
				beginDate = sdf.parse(begindate);
				System.out.println("beginDate="+beginDate);
			}
			if(enddate!=null){
				endDate = sdf.parse(enddate);
				System.out.println("endDate="+endDate);
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(pageNumber == null ||pageNumber.equals(new Integer(0))){
			   pageNumber = new Integer(1);
		}
		if(pageSize == null ||pageSize.equals(new Integer(0))){
			pageSize = new Integer(6);
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("pageNumber", pageNumber);   			      
		model.addAttribute("pageSize", pageSize);
		try {
			if(afterSaleType!=null && afterSaleType == 1){
				//查询退款单（全部）
				System.out.println("退款单");
				Page<Refunds> page = refundsService.findPage(orderNo,returnOrExchangeNo,afterSaleStatus,logisticsNo,beginDate,endDate,pageable);
				model.addAttribute("page", page);
			}else if(afterSaleType == null || afterSaleType == 2){
				//查询退货单（全部）
				System.out.println("退货单");
				Page<Returns> page = returnsService.findPage(orderNo,returnOrExchangeNo,afterSaleStatus,logisticsNo,beginDate,endDate,pageable);
				model.addAttribute("page", page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("returnOrExchangeNo", returnOrExchangeNo);
		model.addAttribute("logisticsNo", logisticsNo);
		model.addAttribute("afterSaleStatus", afterSaleStatus);
		model.addAttribute("afterSaleType", afterSaleType);
		model.addAttribute("begindate", begindate);
		model.addAttribute("enddate", enddate);
		System.out.println(returnsService.findPage(pageable).getContent().size());
		return "shop/member/afterSale/list";
	}
	
//	@RequestMapping(value = "/query",method = RequestMethod.GET)
//	public void query(String orderNo,String returnOrExchangeNo,String afterSaleStatus,String afterSaleType,String logisticsNo,
//			Date begindate,Date enddate){
//		System.out.println("orderNo="+orderNo);
//		System.out.println("returnOrExchangeNo="+returnOrExchangeNo);
//		System.out.println("afterSaleStatus="+afterSaleStatus);
//		System.out.println("afterSaleType="+afterSaleType);
//		System.out.println("logisticsNo="+logisticsNo);
//		System.out.println("begindate="+begindate);
//		System.out.println("enddate="+enddate);
//	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(String ids,Integer afterSaleType) {
		System.out.println("delete");
		System.out.println("ids==="+ids);
		System.out.println("afterSaleType="+afterSaleType);
		if(afterSaleType == 1 || afterSaleType == null){
			System.out.println("退款单");
			if(ids != null && ids.length() >0){
				String ss[] = ids.split(",");
				for (int i = 0; i < ss.length; i++) {
					refundsService.delete(Long.parseLong(ss[i]));
				}
			}else{
				return Message.warn("请勾选您想删除的退款单");
			}
		}else{
			System.out.println("退货单");
			if(ids != null && ids.length() >0){
				String ss[] = ids.split(",");
				for (int i = 0; i < ss.length; i++) {
					returnsService.delete(Long.parseLong(ss[i]));
				}
			}else{
				return Message.warn("请勾选您想删除的退货单");
			}
		}
		return SUCCESS_MESSAGE;
	}
	
}
