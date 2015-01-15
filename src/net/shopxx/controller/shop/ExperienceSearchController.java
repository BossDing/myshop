package net.shopxx.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.entity.Experience;
import net.shopxx.service.ExperienceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 体验店服务
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Controller("ExperienceSearchController")
@RequestMapping("/experienceSearch")
public class ExperienceSearchController {

	@Resource(name = "experienceServiceImpl")
	private ExperienceService experienceService;

	/**
	 * 查找体验店
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String local, String searchWord, String province,
			String city, HttpServletRequest request, ModelMap model) {
		String area = null;
		if ((province == "" || province == null)
				&& (city == "" || city == null)) {
			area = local;
		}
		List<Experience> experiences = experienceService.findAll(searchWord,
				area, province, city);
		model.addAttribute("experiences", experiences);
		return "/shop/experiencesearch/list";
	}

	/**
	 * ajax动态获取地区体验店网店信息
	 */
	@RequestMapping(value = "/getPoint", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getPoint(String local, String searchWord,
			String province, String city, HttpServletRequest request,
			ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		String area = null;
		if (city != null) {
			if ((province == "" || province == null)
					&& (city == "" || city == null)) {
				area = local;
			}
			List<Experience> experiences = experienceService.findAll(
					searchWord, area, province, city);
			data.put("experiences", experiences);

		}
		return data;
	}

}
