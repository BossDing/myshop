package net.shopxx.controller.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;
import net.shopxx.entity.TrialReport;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.service.TrialApplyService;
import net.shopxx.service.TrialReportService;
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
@Controller("TrialReportController")
@RequestMapping("/trialReport")
public class TrialReportController extends BaseController {
	
	@Resource(name = "trialReportServiceImpl")
	private TrialReportService trialReportService;
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
	 * 根据id查询报表详情
	 */
	@RequestMapping(value = "/detail/{reportId}",method = RequestMethod.GET)
	public String toReportDetail(@PathVariable Long reportId, HttpServletRequest request, ModelMap model) {
		TrialReport report = trialReportService.find(reportId);
		model.addAttribute("report", report);
		return "/shop/trial/reportDetail";   
	}
	
	/**
	 * 根据id查询报表详情
	 */
	@RequestMapping(value = "/toReport/{trialApplyId}",method = RequestMethod.GET)
	public String toReport(@PathVariable Long trialApplyId, HttpServletRequest request, ModelMap model) { 
		Member member = memberService.getCurrent();
		TrialApply apply = trialApplyService.find(trialApplyId);
		model.addAttribute("apply", apply);
		model.addAttribute("member", member);
		return "/shop/trial/report";   
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(TrialReport report,Long areaId,Long trialApplyId,RedirectAttributes redirectAttributes) {
		Member member = memberService.getCurrent();
		TrialApply apply = trialApplyService.find(trialApplyId);
		Area area = areaService.find(areaId);
		if(member == null){
			return Message.error("用户还没有登陆");
		}
		if(apply == null){
			return Message.warn("试用申请不存在");
		}
		Trial trial = trialService.find(apply.getTrial().getId());
		Product product = productService.find(apply.getProduct().getId());
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-HHmmss-sss");
		String no = sf.format(date);
		report.setCreateDate(date);
		report.setModifyDate(date);
		report.setTrialReportNo("report-"+no);
		report.setCreatedBy(member.getUsername());
		report.setArea(area);
		report.setMember(member);
		report.setProduct(product);
		report.setTrial(trial);
		report.setTrialApply(apply);
		report.setReportStatus("approving");
		trialReportService.save(report);
		return Message.success("试用报告提交成功");
	}
	
	@RequestMapping(value = "/succeed",method = RequestMethod.GET)
	public String reportSucceed() { 
		return "/shop/trial/reportSucceed";   
	}
	
	@RequestMapping(value = "/reportList/{trialId}",method = RequestMethod.GET)
	public String toReportList(@PathVariable Long trialId, HttpServletRequest request, ModelMap model) {
		Trial trial = trialService.find(trialId);
		model.addAttribute("trial", trial);
		return "/shop/trial/reportList";   
	}
}
