package net.shopxx.controller.shop;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Pageable;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellRole;
import net.shopxx.entity.Product;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Trial;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
import net.shopxx.service.MemberService;
import net.shopxx.service.PreSellRoleService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.TagService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller("PreSellRoleController")
@RequestMapping("/preSellRole")
public class PreSellRoleController extends BaseController {
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;
	@Resource(name="preSellRoleServiceImpl")
	private PreSellRoleService preSellRoleService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	Product product;
	
	/**预约申请*/
	@RequestMapping(value = "/toApply/{preSellRoleId}",method = RequestMethod.GET)
	public String toApply(@PathVariable Long preSellRoleId, HttpServletRequest request,ModelMap model){
		Member member = memberService.getCurrent();
		PreSellRole preSellRole = preSellRoleService.find(preSellRoleId);
		model.addAttribute("member",member);
		model.addAttribute("preSellRole",preSellRole);
		return "/shop/presell/apply";
	}
	
	/**
	 * 抢购
	 */
	@RequestMapping(value = "/presellqg/{preSellRoleId}",method = RequestMethod.GET)
	public String presellqg(@PathVariable Long preSellRoleId, HttpServletRequest request,ModelMap model){
		Member member = memberService.getCurrent();
		PreSellRole preSellRole = preSellRoleService.find(preSellRoleId);
		Set<Product> products = preSellRole.getProducts();	
		for (Product object : products) {  
			this.product = object ;
		}  
		model.addAttribute("product",product);
		model.addAttribute("member",member);
		model.addAttribute("preSellRole",preSellRole);
		return "/shop/presell/presellqg";
	}
	
	/**
	 * 预约
	 */
	@RequestMapping(value = "/reserve/{preSellRoleId}",method = RequestMethod.GET)
	public String reserve(@PathVariable Long preSellRoleId, HttpServletRequest request,ModelMap model){
		Member member = memberService.getCurrent();
		PreSellRole preSellRole = preSellRoleService.find(preSellRoleId);
		Set<Product> products = preSellRole.getProducts();	
		for (Product object : products) {  
			this.product = object ;
		}
		model.addAttribute("product",product);
		model.addAttribute("member",member);
		model.addAttribute("preSellRole",preSellRole);
		return "/shop/presell/reserve";
	}
	
	/**
	 * 新品上市 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, 
			Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Pageable pageable = new Pageable(pageNumber, pageSize);
//		Pageable pageable1 = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
//		System.out.println("orderType:"+orderType);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", productService.findPage(null, brand, promotion, tags, null, startPrice, endPrice, 
																						true, true, null, false, null, null, orderType, pageable));
		List<PreSellRole> preSellRoles = preSellRoleService.findList(true,false,1,null,null);
		List<PreSellRole> nextPreSellRoles = preSellRoleService.findList(false,false,1,null,null);
		model.addAttribute("preSellRoles", preSellRoles);
		model.addAttribute("nextPreSellRoles", nextPreSellRoles);
//		System.out.println(preSellRoleService.findPage1(pageable).getContent().get(0).getId());
//		System.out.println("--6--");
		return "/shop/presell/list";
	}
	
	
	/**
	 * 
	 * @param preSellRoleId
	 * @param request
	 * @param model
	 * @returnStringhfh
	 */
	@RequestMapping(value = "/toRemind/{preSellRoleId}",method = RequestMethod.GET)
	public String toRemind(@PathVariable Long preSellRoleId, HttpServletRequest request, ModelMap model) {
		PreSellRole preSellRole = preSellRoleService.find(preSellRoleId);
		model.addAttribute("preSellRole", preSellRole);
		return "/shop/presell/remind";   
	}
}
