package net.shopxx.controller.shop;


import javax.annotation.Resource;

import net.shopxx.Pageable;
import net.shopxx.entity.GroupPurchase;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.GroupService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("GroupController")
@RequestMapping("/group")
public class GroupController extends BaseController {
	
	@Resource(name = "groupServiceImpl")
	private GroupService groupService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Integer pageNumber, Integer pageSize,Model model){
		System.out.println("group-shop-list");
		try {
			if(pageNumber == null ||pageNumber.equals(new Integer(0))){
				   pageNumber = new Integer(1);
			}  
			if(pageSize == null ||pageSize.equals(new Integer(0))){
				pageSize = new Integer(6);
			}
			Pageable pageable = new Pageable(pageNumber, pageSize);
			model.addAttribute("pageNumber", pageNumber);   			      
			model.addAttribute("pageSize", pageSize);
			
			//Page<GroupPurchase> groups = groupService.findPage(pageable);
			//System.out.println(groups.getContent().size());
//			List<GroupPurchase> groups = groupService.findAll();
//			List<Product> productss = new ArrayList<Product>();
//			Set<Product> products = new HashSet<Product>();
//			System.out.println("groupsize="+groups.size());
//			for(GroupPurchase groupPurchase : groups){
//				products = groupPurchase.getProducts();
//				System.out.println("products="+products.size());
//				for(Product product : products){
//					productss.add(product);
//				}
//			}
//			System.out.println("productss后="+productss.size());
//			long total = productss.size();
//			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
//			if (totalPages < pageable.getPageNumber()) {
//				pageable.setPageNumber(totalPages);
//			}
//			Page<Product> groupss = new Page<Product>(productss,productss.size(), pageable);
//			model.addAttribute("page", groupss);
//			model.addAttribute("groups", groups);
//			System.out.println("groupss=="+groupss.getContent().size());
			
			model.addAttribute("page", groupService.findPage(pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "shop/group/list";
	}
	
	@RequestMapping(value = "/content/{groupId}/{productId}",method = RequestMethod.GET)
	public String content(@PathVariable Long groupId,@PathVariable Long productId, Model model){
//		System.out.println("group====content");
//		System.out.println("groupId="+groupId);
//		System.out.println("productId="+productId);
		GroupPurchase groupPurchase = groupService.find(groupId);
		System.out.println("content-purchasePrice="+groupPurchase.getPurchasePrice());
		Product product = productService.find(productId);
		//判断是否已收藏该商品
		Member member = memberService.getCurrent();
		model.addAttribute("member",member);
		try {
			if(member != null){
				if (member.getFavoriteProducts().contains(product)) {
					model.addAttribute("hasFavoriteProduct", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		System.out.println("groupName="+groupPurchase.getName());
//		System.out.println("productName="+product.getName());
		model.addAttribute("groupPurchase", groupPurchase);
		model.addAttribute("product", product);
		return "shop/group/content";
	}
}
