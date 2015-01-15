package net.shopxx.controller.dp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopxx.FileInfo;
import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.admin.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Store;
import net.shopxx.service.AreaService;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.FileService;
import net.shopxx.service.StoreService;
import net.shopxx.util.AddressUtil;
import net.shopxx.util.JsonUtils;
import net.shopxx.util.SendMessageUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 店铺
 * 
 * @author Cgd
 * @version 1.0
 */
@Controller("dpStoreController")
@RequestMapping("/store")
public class StoreController extends BaseController {

	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	
	
	/**
	 * 申请开店
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String applyStore() {
		return "/dp/store/apply";
	}

	/**
	 * 申请开店 提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(Store store, Long areaId,
			RedirectAttributes redirectAttributes) {
		try {
			store.setArea(areaService.find(areaId));
			store.setAreaName(areaService.find(areaId).getFullName());
			storeService.applyStore(store);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} catch (Exception e) {
			addFlashMessage(redirectAttributes, ERROR_MESSAGE);
			e.printStackTrace();
		}
		return "redirect:apply.jhtml";
	}

	/**
	 * 列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String keyword, Long cityId, Long provinceId,
			Integer pageNumber, Integer pageSize, HttpServletRequest request,
			ModelMap model) {

		Area city = null;
		if (null == keyword && null == cityId && null == provinceId
				&& null == pageNumber && null == pageSize) {
			try {
				String[] address = AddressUtil.getAddresses(request, "UTF-8");
				if (StringUtils.isNotEmpty(address[0])
						&& StringUtils.isNotEmpty(address[1])) {
					provinceId = areaService.findByAreaName(address[0].trim())
							.getId();
					city = areaService.findByAreaName(address[1].trim());
					cityId = city.getId();
					// System.out.println("根据ip查找--省份：" + address[0] + ", "
					// + address[1]);
					// System.out.println("我司地区数据库--省份"
					// + areaService.findByAreaName(address[0]) + ", "
					// + city.getName());
				}
			} catch (Exception e) {
				System.out.println("根据IP没有找到对应省份城市");
//				e.printStackTrace();
			}
		}

		if (pageNumber == null || pageNumber.equals(new Integer(0))) {
			pageNumber = new Integer(1);
		}

		if (pageSize == null || pageSize.equals(new Integer(0))) {
			pageSize = new Integer(9);
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("cityId", cityId);
		model.addAttribute("provinceId", provinceId);
		pageable.setSearchValue(keyword);
		pageable.setSearchProperty("name");

		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("checkStatus", Filter.Operator.eq,
				Store.CheckStatus.success)); // 通过审核
		filters.add(new Filter("isEnabled", Filter.Operator.eq, new Boolean(
				true))); // 启用中

		model.addAttribute("provinceList", areaService.findRoots());
		if (city != null) {
			filters.add(new Filter("areaName", Filter.Operator.like, "%"
					+ city.getName().trim() + "%"));
		} else if (cityId != null) {
			filters.add(new Filter("areaName", Filter.Operator.like, "%"
					+ areaService.find(cityId).getName().trim() + "%"));
		}
		model.addAttribute("page", storeService.findPage(filters, null,
				pageable));
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/dp/store/list";
	}

	/**
	 * 地图
	 */
	@RequestMapping(value = "/map/{id}", method = RequestMethod.GET)
	public String map(@PathVariable Long id, ModelMap model) {
		Store store = storeService.find(id);
		if (store == null) {
			return ERROR_VIEW;
		}
		model.addAttribute("store", store);
		return "/dp/store/map";
	}


	/**
	 * 上传
	 */
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileInfo.FileType fileType, MultipartFile file,
			HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
		} else {
			String url = fileService.upload(fileType, file, false);
			if (url == null) {
				data.put("message", Message.warn("admin.upload.error"));
			} else {
				data.put("message", SUCCESS_MESSAGE);
				data.put("url", url);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtils.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/file/browser", method = RequestMethod.GET)
	public @ResponseBody
	List<FileInfo> browser(String path, FileInfo.FileType fileType,
			FileInfo.OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}

	/**
	 * 把对应店铺的地址发送至指定手机
	 * 
	 * @param telephone
	 * @param captchaId
	 * @param captcha
	 * @param storeId
	 * @return
	 */
	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
	public @ResponseBody
	Message sendAddressBySMS(String telephone, String captchaId,
			String captcha, Long storeId) {

		if (StringUtils.isEmpty(captcha)) {
			return Message.error("请输入验证码");
		}
		if (StringUtils.isEmpty(telephone)) {
			return Message.error("请输入手机号码");
		}
		if (!captchaService.isValid(null, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		if (storeId == null) {
			return Message.error("数据异常，请刷新页面再试");
		}
		Store store = storeService.find(storeId);
		if (store == null) {
			return Message.error("数据异常，请刷新页面再试");
		}
		// Setting setting = SettingUtils.get();
		StringBuilder sb = new StringBuilder("欢迎来到" + store.getName()
				+ ",本店地址：" + store.getAreaName() + " " + store.getAddress());
		String busline = store.getBusline();
		if (busline != null)
			sb.append(".公交路线：" + store.getBusline());
		BigDecimal mapy = store.getMapy();
		BigDecimal mapx = store.getMapx();
		if (mapy != null && mapx != null)
			sb.append(". 地图经度：" + mapy + ",维度：" + mapx + ".");
		try {
			SendMessageUtils.executeSendV(sb.toString(), telephone, null,
					"macro");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("异常:" + e.getMessage());
			return Message.error("服务繁忙，请稍后重试");
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 异步验证验证码
	 * 
	 * @param captchaId
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value = "/checkCaptcha", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkCaptcha(String captchaId, String captcha) {
		// System.out.println("captchaId--" + captchaId);
		// System.out.println("captcha--" + captcha);
		if (StringUtils.isEmpty(captcha)) {
			return false;
		}
		if (!captchaService.isValid(null, captchaId, captcha)) {
			return false;
		}
		return true;
	}


}