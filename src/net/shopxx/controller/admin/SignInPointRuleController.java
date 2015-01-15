package net.shopxx.controller.admin;

import java.util.Iterator;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.SignInPointRule;
import net.shopxx.service.MemberPointsService;
import net.shopxx.service.SignInPointRuleService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller("adminSignInPointRuleController")
@RequestMapping("/admin/sign_in_point_rule")
public class SignInPointRuleController extends BaseController{

	@Resource(name = "signInPointRuleServiceImpl")
	private SignInPointRuleService signInPointRuleService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", signInPointRuleService.findPage(pageable));
		return "/admin/sign_in_point_rule/list";
	}
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/sign_in_point_rule/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SignInPointRule signInPointRule, RedirectAttributes redirectAttributes) {
		if (!isValid(signInPointRule)) {
			return ERROR_VIEW;
		}
		if (!signInPointRuleService.serialSignInTimesUnique(null, signInPointRule.getSerialSignInTimes())) {
			return ERROR_VIEW;
		}
		//校验积分是否符合范围。
		Integer[] dataArr=new Integer[2];
		dataArr[0]=-1;//积分范围最小值，等于-1为没有限制。
		dataArr[1]=-1;//积分范围最大值，等于-1为没有限制。
		String[] dataStrings= signInPointRuleService.rangeOfPointForSerialSignInTimes(null,signInPointRule.getSerialSignInTimes()).split(",");
		for (int i = 0; i < dataStrings.length; i++) {
			if (dataStrings[i].length()>0) {
				dataArr[i]=Integer.parseInt(dataStrings[i]);
			}
		}
		Integer point=signInPointRule.getPoint();
		if (!(dataArr[0]==-1&&dataArr[1]==-1||(dataArr[0]!=-1?point>dataArr[0]:true)&&(dataArr[1]!=-1?point<dataArr[1]:true))) {
			return ERROR_VIEW;
		}
		signInPointRuleService.save(signInPointRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	/**
	 * 返回连续签到次数是否唯一
	 */
	@RequestMapping(value = "/check_serialSignInTimes", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSerialSignInTimes(Integer previousSerialSignInTimes,Integer serialSignInTimes) {
		if (serialSignInTimes==null) {
			return false;
		}
		if (signInPointRuleService.serialSignInTimesUnique(previousSerialSignInTimes, serialSignInTimes)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 校验奖励的积分是否符合小于等于比该连续签到次数大的积分值，和大于等于比该连续签到次数小的积分值（开区间）。
	 */
	@RequestMapping(value = "/check_point", method = RequestMethod.GET)
	public @ResponseBody
	String checkPoint(Integer previousSerialSignInTimes,Integer serialSignInTimes,Integer point) {
		Integer[] dataArr=new Integer[2];
		dataArr[0]=-1;//积分范围最小值
		dataArr[1]=-1;//积分范围最大值
		if (serialSignInTimes!=null) {
			String[] dataStrings= signInPointRuleService.rangeOfPointForSerialSignInTimes(previousSerialSignInTimes,serialSignInTimes).split(",");
			for (int i = 0; i < dataStrings.length; i++) {
				if (dataStrings[i].length()>0) {
					dataArr[i]=Integer.parseInt(dataStrings[i]);
				}
			}
		}
		return StringUtils.join(dataArr,',');
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			signInPointRuleService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("signInPointRule", signInPointRuleService.find(id));
		return "/admin/sign_in_point_rule/edit";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SignInPointRule signInPointRule, RedirectAttributes redirectAttributes) {
		if (!isValid(signInPointRule)) {
			return ERROR_VIEW;
		}
		SignInPointRule pSignInPointRule = signInPointRuleService.find(signInPointRule.getId());
		if (pSignInPointRule == null) {
			return ERROR_VIEW;
		}
		if (!signInPointRuleService.serialSignInTimesUnique(pSignInPointRule.getSerialSignInTimes(), signInPointRule.getSerialSignInTimes())) {
			return ERROR_VIEW;
		}
		//校验积分是否符合范围。
		Integer[] dataArr=new Integer[2];
		dataArr[0]=-1;//积分范围最小值，等于-1为没有限制。
		dataArr[1]=-1;//积分范围最大值，等于-1为没有限制。
		String[] dataStrings= signInPointRuleService.rangeOfPointForSerialSignInTimes(pSignInPointRule.getSerialSignInTimes(),signInPointRule.getSerialSignInTimes()).split(",");
		for (int i = 0; i < dataStrings.length; i++) {
			if (dataStrings[i].length()>0) {
				dataArr[i]=Integer.parseInt(dataStrings[i]);
			}
		}
		Integer point=signInPointRule.getPoint();
		if (!(dataArr[0]==-1&&dataArr[1]==-1||(dataArr[0]!=-1?point>dataArr[0]:true)&&(dataArr[1]!=-1?point<dataArr[1]:true))) {
			return ERROR_VIEW;
		}
		signInPointRuleService.update(signInPointRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
}
