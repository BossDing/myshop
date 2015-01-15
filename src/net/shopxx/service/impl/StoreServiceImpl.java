package net.shopxx.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AdDao;
import net.shopxx.dao.AdPositionDao;
import net.shopxx.dao.AdminDao;
import net.shopxx.dao.NavigationDao;
import net.shopxx.dao.RoleDao;
import net.shopxx.dao.StoreDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.Ad.Type;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Admin;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.PluginConfig;
import net.shopxx.entity.Role;
import net.shopxx.entity.Store;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.plugin.PaymentPlugin.FeeType;
import net.shopxx.plugin.alipayDirect.AlipayDirectPlugin;
import net.shopxx.plugin.file.FilePlugin;
import net.shopxx.service.MailService;
import net.shopxx.service.PluginConfigService;
import net.shopxx.service.StoreService;
import net.shopxx.util.SettingUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 店铺
 * 
 * @author: Guoxianlong
 * @date: Oct 11, 2014 11:25:17 AM
 */
@Service("storeServiceImpl")
public class StoreServiceImpl extends BaseServiceImpl<Store, Long> implements StoreService {

	@Resource(name = "storeDaoImpl")
	private StoreDao storeDao;
	@Resource(name = "alipayDirectPlugin")
	private AlipayDirectPlugin alipayDirectPlugin;
	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;
	@Resource(name = "roleDaoImpl")
	private RoleDao roleDao;
	@Resource(name = "adPositionDaoImpl")
	private AdPositionDao adPositionDao;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "filePlugin")
	private FilePlugin filePlugin;
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;
	@Resource(name = "navigationDaoImpl")
	private NavigationDao navigationDao;
	@Resource(name="adDaoImpl")
	private AdDao adDao;
	
	@Resource(name = "storeDaoImpl")
	public void setBaseDao(StoreDao storeDao) {
		super.setBaseDao(storeDao);
	}

	@Override
	@Transactional
	public void applyStore(Store store) {
		Assert.notNull(store);
		Assert.notNull(store.getName());
		Assert.notNull(store.getServiceTelephone());
		Assert.notNull(store.getContactTelephone());
		Assert.notNull(store.getEmail());
		Assert.notNull(store.getApplyMan());
		store.setCreateDate(new Date()); // 设置创建日期
		store.setCheckStatus(Store.CheckStatus.wait); // 设置申请状态：待审核
		store.setIsEnabled(false); // 设置启用状态：未启用
		storeDao.persist(store); // 持久化
	}

	@Override
	@Transactional
	public void check(Long id, String status) {
		Assert.notNull(id);
		Assert.notNull(status);
		Admin admin = null;
		try {
			Store store = storeDao.find(id, LockModeType.PESSIMISTIC_WRITE); // 加悲观锁
			store.setModifyDate(new Date()); // 设置修改时间
			Assert.notNull(store);
			if ("F".equals(status.trim())) {
				store.setCheckStatus(Store.CheckStatus.failure); // 审核未通过
				store.setIsEnabled(false); // 设置不启用
			} else if ("T".equals(status.trim())) {
				store.setCheckStatus(Store.CheckStatus.success); // 审核通过
				store.setIsEnabled(true); // 设置启用
				String indexUrl = SettingUtils.get().getSiteUrl() + "/" + store.getId() + ".jhtml";
				store.setIndexUrl(indexUrl);
				
				/**
				 * 设置移动端店铺首页
				 * wmd 2014/11/28
				 */
				String indexMobileUrl = SettingUtils.get().getSiteUrl() + "/mobile/" + store.getId() + ".jhtml";
				store.setIndexMobileUrl(indexMobileUrl);

				initStoragePlugin(store);
				
				admin = initStoreAdmin(store);
				adminDao.persist(admin);
				
				initStoreAdPosition(store, "头部广告1号位", "店铺头部广告1号位", 1190, 150);
				initStoreAdPosition(store, "头部广告2号位", "店铺头部广告2号位", 1190, 354);
				
				/** 移动端店铺广告位
				 * wmd 2014/12/2
				 */
				initStoreAdPosition(store, "微商城广告位", "微商城广告位", 380, 720);
				
				initStoreNavigation(store, "店铺首页", Navigation.Position.top, 1, "/" + store.getId() +".jhtml");
				initStoreNavigation(store, "所有商品", Navigation.Position.top, 2, "/dp/product/list.jhtml?storeId=" + store.getId());

				initAlipayDirect(store);
			}
			storeDao.merge(store); // 合并实体对象
			if ("F".equals(status.trim())) {
				mailService.sendApplyStoreFailureNofyMail(store); // 发邮件通知
			} else if ("T".equals(status.trim())) {
				mailService.sendApplyStoreSuccessNofyMail(admin, "888888"); // 发邮件通知
			}
		} catch (Exception e) {
			System.out.println("开店审核异常!-->" + StoreServiceImpl.class.getName());
//			e.printStackTrace();
		}
	}

	/**
	 * 初始化店铺本地存储插件数据
	 */
	private void initStoragePlugin(Store store) {
		PluginConfig pluginConfig = new PluginConfig();
		pluginConfig.setPluginId(filePlugin.getId());
		pluginConfig.setIsEnabled(true);
		pluginConfig.setStore(store);
		pluginConfigService.save(pluginConfig);
	}

	/**
	 * 初始化新的店铺管理员
	 */
	private Admin initStoreAdmin(Store store) {
		Admin admin = new Admin();
		admin.setCreateDate(new Date()); // 设置创建日期
		admin.setEntcode("macro"); // 设置企业号
		admin.setIsEnabled(true); // 设置是否启用：启用
		admin.setIsLocked(false); // 设置是否锁定：否
		admin.setStore(store); // 设置店铺属性
		Role role = roleDao.findByName("店铺管理员"); // 根据角色名称，查找角色
		admin.setLoginFailureCount(0); // 设置连续登录失败次数
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		admin.setRoles(roles); // 设置角色属性
		admin.setEmail(store.getEmail()); // 设置申请邮箱为管理员邮箱
		admin.setPassword(DigestUtils.md5Hex("888888"));// 初始密码 888888(md5加密)
		admin.setName(store.getApplyMan()); // 默认申请开店人名称为管理员名称
		String userName = admin.getName();
		String tempStr = userName;
		while (adminDao.usernameExists(userName)) {
			userName = tempStr + "_" + (int) (Math.random() * 1000); // 生成三位随机数
		}
		admin.setUsername(userName); // 用户名 为管理员名称（或 （如果用户名已存在）管理员名称 + "_" +  三位随机数）
		return admin;
	}

	/**
	 * 初始化店铺广告位
	 * 
	 * @param store
	 * @param name
	 * @param description
	 * @param height
	 * @param width
	 * @return
	 */
	private void initStoreAdPosition(Store store, String name, String description, Integer height, Integer width) {
		AdPosition ap = new AdPosition();
		ap.setCreateDate(new Date());
		ap.setName(name);
		ap.setDescription(description);
		ap.setHeight(height);
		ap.setStores(store);
		ap.setWidth(width);
		ap.setTemplate("请编辑模板");
		adPositionDao.persist(ap);
		Ad ad = new Ad();
		ad.setAdPosition(ap);
		ad.setStore(store);
		ad.setPath(null);
		ad.setTitle("头部广告");
		ad.setType(Type.image);
		adDao.persist(ad);
	}

	/**
	 * 初始化店铺导航
	 * 
	 * @param name
	 * @param position
	 * @param order
	 * @param url
	 * @return
	 */
	private void initStoreNavigation(Store store, String name, Navigation.Position position, Integer order, String url) {
		Navigation navigation = new Navigation();
		navigation.setCreateDate(new Date());
		navigation.setIsBlankTarget(false);
		navigation.setName(name);
		navigation.setOrder(order);
		navigation.setPosition(position);
		navigation.setUrl(url);
		navigation.setStore(store);
		navigationDao.persist(navigation);
	}

	@Transactional(readOnly = true)
	public Page<Store> findPage(String areaName, Pageable pageable) {
		return storeDao.findPage(areaName, pageable);
	}
	
	/**
	 * 初始化支付宝信息 - 公司的支付宝
	 * @param store
	 */
	private void initAlipayDirect(Store store) {
		PluginConfig pluginConfig = new PluginConfig();
		pluginConfig.setPluginId(alipayDirectPlugin.getId());
		pluginConfig.setAttribute(PaymentPlugin.PAYMENT_NAME_ATTRIBUTE_NAME, "支付宝");
		pluginConfig.setAttribute("partner", "2088211535838965");
		pluginConfig.setAttribute("key", "14hqtaakskwm11j8bc7rniui8wf7hpcd");
		pluginConfig.setAttribute(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, FeeType.fixed.toString());
		pluginConfig.setAttribute(PaymentPlugin.FEE_ATTRIBUTE_NAME, "0");
		pluginConfig.setAttribute(PaymentPlugin.LOGO_ATTRIBUTE_NAME, "http://storage.shopxx.net/demo-image/3.0/payment_plugin/alipay.gif");
		pluginConfig.setAttribute(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, "");
		pluginConfig.setIsEnabled(true);
		pluginConfig.setOrder(1);
		pluginConfig.setStore(store);
		pluginConfigService.save(pluginConfig);
	}
	
}
