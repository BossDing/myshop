package net.shopxx.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.shopxx.entity.Store;
import net.shopxx.service.StoreService;
import org.springframework.stereotype.Component;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 店铺
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Component("storeListDirective")
public class StoreListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "stores";
	
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Store> stores;
		boolean useCache = useCache(env, params);
		if (useCache) {
			stores = storeService.findAll();
		} else {
			stores = storeService.findAll();
		}
		setLocalVariable(VARIABLE_NAME, stores, env, body);
	
	}

}