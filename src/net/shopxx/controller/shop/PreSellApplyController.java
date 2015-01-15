package net.shopxx.controller.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopxx.Message;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellApply;
import net.shopxx.entity.PreSellRole;
import net.shopxx.service.AreaService;
import net.shopxx.service.BaseService;
import net.shopxx.service.MemberService;
import net.shopxx.service.PreSellApplyService;
import net.shopxx.service.PreSellRoleService;
import net.shopxx.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 预约申请
 * 
 * @author hfh
 * @version 1.0   
 */
@Controller("PreSellApplyController")
@RequestMapping("/preSellApply")
public class PreSellApplyController extends BaseController {
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "preSellRoleServiceImpl")
	private PreSellRoleService preSellRoleService;
	@Resource(name = "preSellApplyServiceImpl")
	private PreSellApplyService preSellApplyService;

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(PreSellApply preSellApply,Long areaId,Long productId,
			Long preSellRoleId,RedirectAttributes redirectAttributes,String applierName, HttpServletRequest request, HttpServletResponse response){
		System.out.println("123123");
		System.out.println("preSellApply"+preSellApply);
		System.out.println("areaId"+areaId);
		System.out.println("productId"+productId);
		System.out.println("preSellRoleId"+preSellRoleId);
		
		Member member = memberService.getCurrent();
		if(member == null){
			return Message.error("用户还没有登录");
		}
		
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String no = sdf.format(date);
		preSellApply.setMember(member);
		preSellApply.setProduct(productService.find(productId));
		preSellApply.setArea(areaService.find(areaId));
		preSellApply.setPreSellRole(preSellRoleService.find(preSellRoleId));
		preSellApply.setCreateDate(date);
		preSellApply.setApplyDate(date);
		preSellApply.setEntCode(null);
		preSellApply.setPreSellApplyNo(no);
		preSellApply.setCreatedBy(member.getUsername());
		System.out.println("qweqwe");
		preSellApplyService.save(preSellApply);
		System.out.println("asdasd");
		return Message.success("预约登记申请成功");
	}
	
	
	@RequestMapping(value = "/succeed",method = RequestMethod.GET)
	public String applySucceed(ModelMap model) { 
		return "/shop/presell/applySucceed";   
	}
}
