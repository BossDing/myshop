package net.shopxx.controller.admin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.GiftItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.service.PromotionService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 同步促销
 * @author Administrator
 *
 */
@Controller("adminSynsPromotionController")
@RequestMapping("/admin/sync_promotion")
public class SyncPromotionController extends BaseController {

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Page<Promotion> page = null;
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("isUseCuXiao", Filter.Operator.eq, true));
		page = promotionService.findPage(filters, null, pageable);
		model.addAttribute("page", page);
		return "/admin/sync_promotion/list";
	}

	/**
	 * 同步
	 */
	@RequestMapping(value = "/tongbu", method = RequestMethod.POST)
	public @ResponseBody
	Message tongbu(Long[] ids) {
		
			System.out.println("ids=="+ids);
			if (ids.length == 0) {
				return Message.error("数据异常，请重新操作");
			}
			
			Store store = WebUtils.getStore();
			if (store == null)
				return Message.error("操作失败，只有店铺管理员才能同步");
			
			//获取要同步的促销
			List<Promotion> promotionList = promotionService.findList(ids);
			System.out.println("psize="+promotionList.size());
			BigDecimal zero = new BigDecimal(0);
			Class<Promotion> classPromotion = Promotion.class;
			//获取promotion类字段
			Field[] fields = classPromotion.getDeclaredFields();
			for (Promotion oldPromotion : promotionList) {
				System.out.println("p名称=="+oldPromotion.getName());
				Promotion newPromotion = new Promotion();
				copyProperties(oldPromotion,newPromotion,fields,store);
				System.out.println("名称=="+newPromotion.getName());
				promotionService.save(newPromotion);
				System.out.println("保存成功");
			}
		return SUCCESS_MESSAGE;
	}
	/**
	 * 复制属性
	 * @param srcPromotion
	 * @param targetPromotion
	 * @param fields
	 * @param store
	 */
	private void copyProperties(Promotion srcPromotion, Promotion targetPromotion, Field[] fields, Store store) {
		
			try {
//				for (Field field : fields) {
//					String fieldTypeName = field.getType().getName();
//					/**
//					 * 设置过滤属性
//					 */
//					int modify = field.getModifiers();
//					if (Modifier.isFinal(modify) || Modifier.isStatic(modify)) {
//						continue;
//					}
//					if (fieldTypeName.contains("List") || fieldTypeName.contains("Set")
//							|| fieldTypeName.contains("Map")) {
//						continue;
//					}
//					// 设置成员变量访问权
//					field.setAccessible(true);
//					try {
//						field.set(targetPromotion, field.get(srcPromotion));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
				targetPromotion.setName(srcPromotion.getName());//名称
				targetPromotion.setTitle(srcPromotion.getTitle());//标题
				targetPromotion.setBeginDate(srcPromotion.getBeginDate());//开始时间
				targetPromotion.setEndDate(srcPromotion.getEndDate());//结束时间
				targetPromotion.setMinimumQuantity(srcPromotion.getMaximumQuantity());//最小商品数量
				targetPromotion.setMaximumQuantity(srcPromotion.getMaximumQuantity());//最大商品数量
				targetPromotion.setMinimumPrice(srcPromotion.getMinimumPrice());//最小商品价格
				targetPromotion.setMaximumPrice(srcPromotion.getMaximumPrice());//最大商品价格
				targetPromotion.setPriceExpression(srcPromotion.getPriceExpression());//价格运算表达式
				targetPromotion.setPointExpression(srcPromotion.getPointExpression());//积分运算表达式
				targetPromotion.setPromotionPrice(srcPromotion.getPromotionPrice());//促销价
				targetPromotion.setIsFreeShipping(false);//是否免运费
				targetPromotion.setIsCouponAllowed(false);//是否允许使用优惠券
				targetPromotion.setIntroduction(srcPromotion.getIntroduction());//介绍
//				targetPromotion.setMemberRanks(null);//允许参加会员等级 
				
				Set<ProductCategory> productCategorys = srcPromotion.getProductCategories();//允许参与商品分类
				targetPromotion.getProductCategories().addAll(productCategorys);
				Set<Product> products = srcPromotion.getProducts();//允许参与商品
				targetPromotion.getProducts().addAll(products);
//				targetPromotion.setBrands(null);//允许参与品牌
//				targetPromotion.setCoupons(null);//赠送优惠券
				List<GiftItem> giftItems = srcPromotion.getGiftItems();//赠品
				List<GiftItem> newGifts = new ArrayList<GiftItem>();
				newGifts.addAll(newGifts);
				targetPromotion.setGiftItems(newGifts);
				targetPromotion.setActionType(srcPromotion.getActionType());//活动分类
				targetPromotion.setImgpath(srcPromotion.getImgpath());//特惠展现图
				targetPromotion.setShareSummary(srcPromotion.getShareSummary());//分享到社区网站内容
				targetPromotion.setType(srcPromotion.getType());//促销类型 0  1  2  3
				targetPromotion.setEntCode(srcPromotion.getEntCode());//企业号
				targetPromotion.setStore(store);//店铺
				targetPromotion.setIsUseCuXiao(false);//是否同步促销  商城管理员控制
				targetPromotion.setPromotion(srcPromotion);//来源促销 店铺账户使用
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
