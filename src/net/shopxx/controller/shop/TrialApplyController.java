package net.shopxx.controller.shop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Product;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.service.TrialApplyService;
import net.shopxx.service.TrialService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller - 试用申请
 * 
 * @author lzy
 * @version 1.0   
 */
@Controller("TrialApplyController")
@RequestMapping("/trialApply")
public class TrialApplyController extends BaseController {

	@Resource(name = "trialApplyServiceImpl")
	private TrialApplyService trialApplyService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "trialServiceImpl")
	private TrialService trialService;
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(TrialApply apply,Long areaId,Long productId,Long trialId,RedirectAttributes redirectAttributes) {
		Member member = memberService.getCurrent();
		if(member == null){
			return Message.error("用户还没有登陆");
		}
		
		Trial trial = trialService.find(trialId);
		
		if(trial == null){
			return Message.warn("该试用不存在");
		}
		
		boolean isHave = trialApplyService.findCountByCreateBy(trial, member.getUsername());
		if(isHave){
			return Message.warn("您已经填写了该试用");
		}
		
		Long countNum = trial.getAppliernum();
		
		if(countNum == null){
			countNum = 1L;
		}else{
			countNum++;
		}
		trial.setAppliernum(countNum);
		
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-HHmmss-sss");
		String no = sf.format(date);
		apply.setMember(member);
		apply.setProduct(productService.find(productId));
		apply.setTrial(trialService.find(trialId));
		apply.setArea(areaService.find(areaId));
		apply.setCreateDate(date);
		apply.setModifyDate(date);
		apply.setApplyDate(date);
		apply.setApplyStatus(null);
		apply.setEntCode(null);
		apply.setModifyBy(null);
		apply.setTrialApplyNo(no);
		apply.setCreatedBy(member.getUsername());
		apply.setIsReport(null);
		apply.setApplyStatus("approving");
		apply.setIsReport("No");
		trialApplyService.save(apply);
		trialService.update(trial);
		return Message.success("申请沁园试用成功");
	}     
	
	@RequestMapping(value = "/succeed",method = RequestMethod.GET)
	public String applySucceed(ModelMap model) {
		return "/shop/trial/applySucceed";   
	}
}
