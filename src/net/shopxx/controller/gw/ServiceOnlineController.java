package net.shopxx.controller.gw;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Order;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.entity.AfterBooking;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.entity.Extension;
import net.shopxx.entity.Instruction;
import net.shopxx.entity.LeaveWords;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.AfterBooking.Type;
import net.shopxx.service.AfterBookingService;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.ExtensionService;
import net.shopxx.service.InstructionService;
import net.shopxx.service.LeaveWordsService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 在线服务
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("gwServiceOnlineController")
@RequestMapping("/gw/serviceOnline")
public class ServiceOnlineController extends BaseController {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "afterBookingServiceImpl")
	private AfterBookingService afterBookingService;
	@Resource(name = "leaveWordsServiceImpl")
	private LeaveWordsService leaveWordsService;
	@Resource(name = "extensionServiceImpl")
	private ExtensionService extensionService;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "instructionServiceImpl")
	private InstructionService instructionService;

	/**
	 * 延保服务
	 */
	@RequestMapping(value = "/extension", method = RequestMethod.GET)
	public String extension(HttpServletRequest request, ModelMap model) {
		return "/gw/serviceonline/extension";
	}

	/**
	 * 产品说明书
	 */
	@RequestMapping(value = "/chanpinshuomingshu", method = RequestMethod.GET)
	public String chanpinshuomingshu(HttpServletRequest request, ModelMap model) {
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("order"));
//		orders.add(Order.asc("grade"));
		List<ProductCategory> categorys = productCategoryService.findList(null, null, orders);
		// System.out.println("categorys="+categorys.size());
		model.addAttribute("categorys", categorys);
		return "/gw/serviceonline/chanpinshuomingshu";
	}

	/**
	 * 常见问题
	 * 
	 * @param request
	 * @param model
	 * @returnStringhfh
	 */
	@RequestMapping(value = "/commonProblemList", method = RequestMethod.GET)
	public String commonProblemList(HttpServletRequest request, ModelMap model) {
		return "/gw/serviceonline/commonProblemList";
	}

	/**
	 * 维修预约
	 */
	@RequestMapping(value = "/repair", method = RequestMethod.GET)
	public String repair(HttpServletRequest request, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		List<ProductCategory> categorys = productCategoryService.findAll();
		// System.out.println("categorys="+categorys.size());
		model.addAttribute("categorys", categorys);
		return "/gw/serviceonline/repair";
	}

	/**
	 * 安装预约
	 */
	@RequestMapping(value = "/install", method = RequestMethod.GET)
	public String install(HttpServletRequest request, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		List<ProductCategory> categorys = productCategoryService.findAll();
		// System.out.println("categorys="+categorys.size());
		model.addAttribute("categorys", categorys);
		return "/gw/serviceonline/install";
	}

	/**
	 * 在线留言
	 */
	@RequestMapping(value = "/leaveMsg", method = RequestMethod.GET)
	public String leaveMsg(HttpServletRequest request, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/gw/serviceonline/leaveMsg";
	}

	/**
	 * 校验验证码是否有效
	 * 
	 * @param account
	 * @returnbooleanhfh
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkCode(String captchaId, String captcha) {
		// System.out.println("captchaId="+captchaId);
		// System.out.println("captcha="+captcha);
		if (!captchaService.isValid(CaptchaType.other, captchaId, captcha)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 维修、安装预约保存
	 * 
	 * @param afterBooking
	 * @param productCategoryId
	 * @param type
	 * @param request
	 * @param redirectAttributes
	 * @returnMessagehfh
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(AfterBooking afterBooking, Long productCategoryId,  Type type,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		 System.out.println("进入serviceonline-----save");
		 
		// System.out.println(type==Type.repair);
		// System.out.println(afterBooking.getProductCategory());
		// System.out.println(productCategoryId);
		// System.out.println(afterBooking.getAddress());
		// System.out.println(afterBooking.getConsignee());
		// System.out.println(afterBooking.getPhone());
		// Long[] ps = null;
		
		// if(null!=productIds&&!"".equalsIgnoreCase(productIds.trim())){
		// String[] ss = productIds.split("-");
		// ps= new Long[ss.length];
		// for(int i=0;i<ss.length;i++){
		// ps[i] = Long.parseLong(ss[i]);
		// }
		// }
		// afterBooking.setArea(areaService.find(areaId));
		// List<Product> products = productService.findList(ps);
		// if(null!=products){
		// afterBooking.setProducts(new HashSet<Product>(products));
		// }
		try {
			/*Member member = memberService.getCurrent();
			if (member == null) {
				return Message.error("用户还没有登录");
				// return ERROR_VIEW;
			}
			if (null != member.getAfterBookings()
					&& member.getAfterBookings().size() > 0) {
				afterBooking.setSn(sdf.format(new Date())
						+ member.getAfterBookings().size());
			} else {
				afterBooking.setSn(sdf.format(new Date()) + 0);
			}*/
			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hhmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String no = sdf.format(date);
			afterBooking.setSn(no);
//			afterBooking.setMember(member);
			if (productCategoryId == -1) {
				return Message.warn("请选择您购买的产品类别");
			}
			afterBooking.setProductCategory(productCategoryService
					.find(productCategoryId));
			// System.out.println(afterBooking.getProductCategory().getName());
			
			
//			System.out.println("productName="+productName);
//			System.out.println("afterBooking.getProductName()="+afterBooking.getProductName());
			/*Product product = null;
			if(productName != null){
//				System.out.println("111111111111");
				product = productService.findByProductName(productName);
//				System.out.println("product="+product);
				if (product == null) {
//						data.put("message", Message.warn("该商品不存在"));
					return Message.warn("该商品不存在");
				}else{
					afterBooking.setProduct(product);
				}
			}*/
//			 if(afterBooking.getProduct()!=null){
//				 System.out.println("产品 ："+afterBooking.getProduct().getName());
//			 }
			
			
			// System.out.println("entcode="+afterBooking.getEntCode());
			String entCode = afterBooking.getEntCode();
			if (null == entCode || "".equals(entCode.trim())
					|| entCode.length() < 1) {
//				afterBooking.setEntCode("gwmacro");
				afterBooking.setEntCode(WebUtils.getXentcode());
			}
			afterBooking.setSource(1);
			afterBooking.setState(0);
			// if(afterBooking.getType()==Type.install){
			// afterBooking.setFaultType(0);
			// afterBooking.setFaultDate(new Date());
			// }else if(afterBooking.getType()==Type.repair){
			//			
			// }
			afterBooking.setType(type);
			// Date date = new Date();
			// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// String no = sf.format(date);
			// afterBooking.setSn(no);
			// System.out.println(afterBooking.getSn());
			try {
				afterBookingService.save(afterBooking);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("保存成功，返回success");
		return Message.success("提交成功");
	}

	/**
	 * 在线留言保存
	 */
	@RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
	public @ResponseBody
	Message saveMsg(LeaveWords leaveWords, HttpServletRequest request) {
//		System.out.println("进入在线留言保存");
//		Member member = memberService.getCurrent();
//		if (member == null) {
//			return Message.error("用户还没有登录");
//			// return ERROR_VIEW;
//		}
//		leaveWords.setMember(member);
		leaveWords.setConsultationType("6");
		leaveWords.setSource(1);
		System.out.println("entcode:"+WebUtils.getXentcode());
		leaveWords.setEntcode(WebUtils.getXentcode());
		leaveWordsService.save(leaveWords);
		return Message.success("shop.consultation.success");
	}

	/**
	 * 延保服务-查询
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> query(Extension extension2, HttpServletRequest request) {
		// System.out.println(extension2.getCardNo());
		// System.out.println(extension2.getPassword());
		Map<String, Object> data = new HashMap<String, Object>();
		// Member member = memberService.getCurrent();
		// if(member == null){
		// return Message.error("用户还没有登录");
		// data.put("message", Message.error("用户还没有登录"));
		// return data;
		// return ERROR_VIEW;
		// }
		// System.out.println("member="+member.getUsername());
		try {
			// Extension extension =
			// extensionService.findByMember(member.getUsername());
			Extension extension = extensionService.findByMember(extension2
					.getCardNo());
			if (extension == null) {
				// return Message.warn("对不起，您目前暂无延保服务卡号");
				// data.put("message", Message.warn("对不起，您目前暂无延保服务卡号"));
				data.put("message", Message.error("服务卡号不存在"));
				return data;
			} else {
				// System.out.println("extension="+extension.getCardNo());
				// System.out.println("extension="+extension.getBarCode());
				// System.out.println("extension="+extension.getEndDate());
				// if(!extension.getCardNo().equals(extension2.getCardNo())){
				// data.put("message", Message.error("服务卡号有误"));
				// return data;
				// }
				System.out.println(00000);
				System.out.println(extension.getPassword());
				System.out.println(extension2.getPassword());
				if (!extension.getPassword().equals(extension2.getPassword())) {
					data.put("message", Message.error("密码有误"));
					return data;
				}
				// data.put("member", member.getUsername());
				data.put("member", extension.getUserName());
				data.put("barCode", extension.getBarCode());
				data.put("endDate", (extension.getEndDate() + "").trim().split(
						"\\.")[0]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// extensionService.setConsultationType("6");
		// leaveWordsService.save(extension);
		// return Message.success("shop.consultation.success");
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 常见问题-查询
	 */
	@RequestMapping(value = "/problemQuery", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> problemQuery(String productSn, String problemType) {
		// System.out.println("problemQuery");
		// System.out.println("product="+productSn);
		// System.out.println("problemType="+problemType);
		Map<String, Object> data = new HashMap<String, Object>();
		Long categoryId = (long) 1109;
		
		
		if (productSn != null && problemType != null) {
			/** 根据产品型号和故障类型查询 */
			System.out.println("根据产品型号和故障类型查询");
			ArticleCategory articleCategory = articleCategoryService.find(categoryId);
			
			
			List<Article> articleList = null;
			Product product = productService.findBySn(productSn);
			ProductCategory productCategory = null;
			if (product != null) {
				productCategory = product.getProductCategory();
			}
			try {
				if (productCategory !=null) {
					System.out.println("productCategoryName="+ productCategory.getName());
					articleList = articleService.findByTitle(productCategory.getName(), articleCategory);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			List<Article> articleList1 = null;
			try {
				articleList1 = articleService.findByContent(problemType,articleCategory);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			if (articleList.size() == 0 || articleList1.size() == 0) {
				data.put("message", Message.warn("查询不到符合条件的文章"));
				return data;
			}
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
			for(Article article1: articleList1){
				HashMap<String, Object> hotMap = new HashMap<String, Object>();
				hotMap.put("title", article1.getTitle());
				hotMap.put("content", article1.getContent());
				hotList.add(hotMap);
			}
			data.put("articleList", hotList);
			data.put("message", Message.success("查询成功"));
			return data;

			
			
			
			
		} else if (productSn != null && problemType == null) {
			/** 根据产品型号查询 */
			System.out.println("根据产品型号查询");
			// Product product = null;
			// ProductCategory productCategory = null;
			List<Article> articleList = null;
			Product product = productService.findBySn(productSn);
			System.out.println(product);
			ProductCategory productCategory = null;
			if (product != null) {
				productCategory = product.getProductCategory();
			}
//			List<ProductCategory> productCategoryroot = null;
//			if (productCategory != null) {
//				productCategoryroot = productCategoryService
//						.findParents(productCategory);
//				System.out.println(productCategoryroot.size());
//			}
			try {
				if(productCategory!=null) {
					System.out.println("productCategoryName="+ productCategory.getName());
					ArticleCategory articleCategory = articleCategoryService.find(categoryId);
					articleList = articleService.findByTitle(productCategory.getName(),articleCategory);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			if (articleList.size() == 0) {
				data.put("message", Message.warn("查询不到符合条件的文章"));
				return data;
			}
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
			for(Article article : articleList){
				HashMap<String, Object> hotMap = new HashMap<String, Object>();
				hotMap.put("title", article.getTitle());
				hotMap.put("content", article.getContent());
				hotList.add(hotMap);
			}
			data.put("articleList", hotList);
//			data.put("title", article.getTitle());
//			data.put("content", article.getContent());
			data.put("message", Message.success("查询成功"));
			return data;

			
			
			
			
		} else if (productSn == null && problemType != null) {
			/** 根据故障类型查询 */
			System.out.println("根据故障类型查询");
			List<Article> articleList = null;
			ArticleCategory articleCategory = articleCategoryService
					.find(categoryId);
			try {
				articleList = articleService.findByContent(problemType,
						articleCategory);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			
			System.out.println("articleList=" + articleList.size());
			if (articleList.size() == 0) {
				data.put("message", Message.warn("查询不到符合条件的文章"));
				return data;
			}
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String, Object>>();
			for (Article article : articleList) {
				HashMap<String, Object> hotMap = new HashMap<String, Object>();
				hotMap.put("title", article.getTitle());
				hotMap.put("content", article.getContent());
				hotList.add(hotMap);
			}
			data.put("articleList", hotList);
			// data.put("title", article1.getTitle());
			// data.put("content",article1.getContent());
			data.put("message", Message.success("查询成功"));
			return data;
		}

		data.put("message", Message.warn("请输入查询条件"));
		return data;
	}

	
	/***
	 * 查询说明书
	 * @param productCategoryId
	 * @param productSn
	 * @returnMap<String,Object>hfh
	 */
	@RequestMapping(value = "/instructionQuery", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> instructionQuery(String productName) {
//		System.out.println("instructionQuery");
//		System.out.println("productName="+productName);
		Map<String, Object> data = new HashMap<String, Object>();
//		String itemNo = "";
		Product product = null;
		if(productName != null){
//			System.out.println("111111111111");
			product = productService.findByProductName(productName);
//			System.out.println("product="+product);
			if (product == null) {
				data.put("message", Message.warn("该商品不存在"));
				return data;
			}
//			if(product!=null){
//				System.out.println("itemNo==="+product.getSn());
//			}
//			itemNo = product.getSn();
//			System.out.println("itemNo==="+product.getSn());
			String instruction = null;
			if (product != null) {
				instruction = product.getInstruction();
			}
			if (instruction == null) {
				data.put("message", Message.warn("该产品暂时没有维护说明书"));
				return data;
			}
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> hotMap = new HashMap<String, Object>();
			hotMap.put("instruction", instruction);
			hotMap.put("productName", product.getName());
			hotList.add(hotMap);
			data.put("instruction", hotList);
			data.put("message", Message.success("查询成功"));
			return data;
		}else if(productName == null){
//			System.out.println("33333333333333");
			data.put("message", Message.warn("请输入搜索条件"));
			return data;
		}
//		System.out.println("4444444444444444");
		data.put("message", Message.warn("请输入搜索条件"));
		return data;
	}
	
	
	/**
	 * 根据分类查询对应商品
	 * @param productCategoryId
	 * @returnMap<String,Object>hfh
	 */
	@RequestMapping(value = "/productsQuery", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> productsQuery(Long productCategoryId) {
//		System.out.println("productsQuery");
//		System.out.println("productCategoryId="+productCategoryId);
		Map<String, Object> data = new HashMap<String, Object>();
//		ProductCategory productCategoryRoot = productCategoryService.find(productCategoryId);
////		List<ProductCategory> productCategoryList = productCategoryService.findChildrenByParent(productCategoryId);
//		List<ProductCategory> productCategoryList = productCategoryService.findChildren(productCategoryRoot);
//		if(productCategoryList.size()!=0){
//			System.out.println("productCategoryListsize="+productCategoryList.size());
//			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
//			for(ProductCategory productCategory: productCategoryList){
//				List<Product> productList = productService.findList(productCategory, null, null, null, null);
//				if(productList.size()!=0){
//					System.out.println("productListsize="+productList.size());
//					for(Product product: productList){
//						HashMap<String, Object> hotMap = new HashMap<String, Object>();
//						hotMap.put("productName", product.getFullName());
//						hotMap.put("productSn", product.getSn());
//						hotList.add(hotMap);
//					}
//					data.put("productList", hotList);
//				}
//			}
//		}
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		List<Product> productList = productService.findList(productCategory, null, null, null, null);
		if(productList.size()!=0){
//			System.out.println("size="+productList.size());
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
			for(Product product: productList){
				HashMap<String, Object> hotMap = new HashMap<String, Object>();
				hotMap.put("productName", product.getFullName());
				hotMap.put("productSn", product.getSn());
				hotList.add(hotMap);
			}
			data.put("productList", hotList);
		}
		
//		List<Product> products = new ArrayList<Product>();
//		ProductCategory productCategory = productCategoryService.find(productCategoryId);
//		if(null!=productCategory){
//			products = productService.findList(productCategory, null, null,null, null);
//		}else{
//			products = productService.findAll();
//		 }
//		ArrayList<HashMap<String , Object>> hotList = new ArrayList<HashMap<String,Object>>();
//		for(Product product: products){
//			HashMap<String, Object> hotMap = new HashMap<String, Object>();
//			hotMap.put("productName", product.getFullName());
//			hotMap.put("productSn", product.getSn());
//			hotList.add(hotMap);
//		}
//		data.put("productList", hotList);
		return data;
	}
	
	
	/**
	 * 在线留言
	 */
	@RequestMapping(value = "/servicePolicy", method = RequestMethod.GET)
	public String servicePolicy(HttpServletRequest request, ModelMap model) {
		// model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/gw/serviceonline/servicePolicy";
	}
	
	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/instruction_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> instructionSelect(String q) {
//		System.out.println("进入instruction_select");
//		System.out.println("q:"+q);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Instruction> instructions = instructionService.search(q, 20);
			for (Instruction instruction : instructions) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", instruction.getId());
				map.put("name", instruction.getName());
//				map.put("productName", instruction.getProductName());
				map.put("instruction", instruction.getUrl());
				data.add(map);
			}
		}  
		return data;
	}
	
	
	/***
	 * 查询说明书(不与产品表关联)
	 * @param productCategoryId
	 * @param productSn
	 * @returnMap<String,Object>hfh
	 */
	@RequestMapping(value = "/instructionQuery2", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> instructionQuery2(String name) {
//		System.out.println("进入instructionQuery2");
//		System.out.println("name="+name);
//		System.out.println("productName="+productName);
		Map<String, Object> data = new HashMap<String, Object>();
//		String itemNo = "";
//		Product product = null;
		Instruction instruction = null;
		if(name != null){
//			System.out.println("111111111111");
//			product = productService.findByProductName(productName);
			instruction = instructionService.findByInstructionName(name);
//			System.out.println("product="+product);
			if (instruction == null) {
				data.put("message", Message.warn("未找到相应的说明书"));
				return data;
			}
//			if(product!=null){
//				System.out.println("itemNo==="+product.getSn());
//			}
//			itemNo = product.getSn();
//			System.out.println("itemNo==="+product.getSn());
//			String instruction = null;
			/*String url = null;
			if (instruction != null) {
				url = instruction.getUrl();
			}
			if (instruction == null) {
				data.put("message", Message.warn("该产品暂时没有维护说明书"));
				return data;
			}*/
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> hotMap = new HashMap<String, Object>();
			hotMap.put("url", instruction.getUrl());
			hotMap.put("name", instruction.getName());
			hotList.add(hotMap);
			data.put("instruction", hotList);
			data.put("message", Message.success("查询成功"));
			return data;
		}else if(name == null){
//			System.out.println("33333333333333");
			data.put("message", Message.warn("请输入搜索条件"));
			return data;
		}
//		System.out.println("4444444444444444");
		data.put("message", Message.warn("请输入搜索条件"));
		return data;
	}
}