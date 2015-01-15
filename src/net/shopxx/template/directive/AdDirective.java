package net.shopxx.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.Ad;
import net.shopxx.entity.Store;
import net.shopxx.service.AdService;
import net.shopxx.service.StoreService;
import net.shopxx.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模版指令 - 广告
 * @author: Guoxianlong
 * @date: Oct 20, 2014  9:10:25 PM
 */
@Component("adDirective")
public class AdDirective extends BaseDirective {

	/** 变量名称 */
	private static final String AD_POSITION_NAME = "ad_position_name";
	private static final String VARIABLE_NAME = "ads";
	
	@Resource(name = "adServiceImpl")
	private AdService adService;
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Store store = null;
		List<Ad> ads;
		String adPositionName = FreemarkerUtils.getParameter(AD_POSITION_NAME, String.class, params);
		String storeid = getStoreId(params);
		if(storeid != null && !"".equals(storeid)) {
			Long id = Long.parseLong(storeid);
			store = storeService.find(id);
		}
		ads = adService.findAdByAdPositionName(adPositionName, store);
		setLocalVariable(VARIABLE_NAME, ads, env, body);
	}

}
