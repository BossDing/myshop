/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.template.directive;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import net.shopxx.entity.AdPosition;
import net.shopxx.service.AdPositionService;
import net.shopxx.util.FreemarkerUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 广告位
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Component("adPositionDirective")
public class AdPositionDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "adPosition";
	
	/** 变量名称 */
	private static final String AD_NAME = "adname";

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;
  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		AdPosition adPosition = null;
		Long id = getId(params);
		String name = getName(params);		
		boolean useCache = useCache(env, params);  
		String cacheRegion = getCacheRegion(env, params);
		String adName = FreemarkerUtils.getParameter(AD_NAME, String.class, params);  
		if(adName !=null && !adName.equals("")){
			adPosition = adPositionService.findByName(adName);
			if(adPosition == null){
				adPosition = adPositionService.find(id);
			}
		}else{															
			if (useCache) {						
				if(id>0){
					adPosition = adPositionService.find(id, cacheRegion);
				}else if(name!=null && name.length()>0){
					adPosition = adPositionService.find(id, cacheRegion);
				}        
				
			} else {
				if(id>0){
					adPosition = adPositionService.find(id);			
				}else if(name!=null && name.length()>0){
					adPosition = adPositionService.find(id);
				}				
			}								
		}
		if (body != null) {
			setLocalVariable(VARIABLE_NAME, adPosition, env, body);
		} else {
			if (adPosition != null && adPosition.getTemplate() != null) {
				try {
					Map<String, Object> model = new HashMap<String, Object>();
					model.put(VARIABLE_NAME, adPosition);
					Writer out = env.getOut();
					new Template("adTemplate", new StringReader(adPosition.getTemplate()), freeMarkerConfigurer.getConfiguration()).process(model, out);
				} catch (Exception e) {				
					e.printStackTrace();
				}							
			}
		}
	}

}