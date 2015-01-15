package net.shopxx.controller.shop.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Order;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.Filter.Operator;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;
import net.shopxx.entity.TrialImage;
import net.shopxx.entity.TrialReport;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.service.TrialApplyService;
import net.shopxx.service.TrialReportService;
import net.shopxx.service.TrialService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import freemarker.template.utility.StringUtil;

/**
 * Controller - 个人中心试用申请
 * 
 * @author lzy
 * @version 1.0   
 */
@Controller("MemberApplyController")
@RequestMapping("/member/trial")
public class TrialController extends BaseController {
	
	@Resource(name = "trialApplyServiceImpl")
	private TrialApplyService trialApplyService;
	@Resource(name = "trialReportServiceImpl")
	private TrialReportService trialReportService;
	@Resource(name = "trialServiceImpl")
	private TrialService trialService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String findTiralList(ModelMap model) {
		Member member = memberService.getCurrent();
		List<TrialApply> applys = trialApplyService.findListByMember(member);
		List<TrialReport> reports = trialReportService.findListByMember(member);
		model.addAttribute("applys", applys);
		model.addAttribute("reports", reports);
		model.addAttribute("member", member);
		List<Trial> trials = trialService.findOrderByEndDateAndDate(new Date());
		model.addAttribute("trials", trials);
		return "/shop/member/trialApply/list";   
	}
	
	@RequestMapping(value = "/applyList",method = RequestMethod.GET)
	public String applyList(Integer pageNumber, Integer pageSize ,ModelMap model) {
		if ((pageNumber == null) || (pageNumber.equals(new Integer(0)))) {
	        pageNumber = new Integer(1);
	      }
	      if ((pageSize == null) || (pageSize.equals(new Integer(0)))) {
	        pageSize = new Integer(4);
	      }
	      Pageable pageable = new Pageable(pageNumber, pageSize);
		Member member = memberService.getCurrent();
		List<Order> orders = new ArrayList<Order>();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setOperator(Operator.eq);
		filter.setProperty("member");
		filter.setValue(member);
		filters.add(filter);
		model.addAttribute("pageNumber", pageNumber);
	    model.addAttribute("pageSize", pageSize);
		//List<TrialApply> applys = trialApplyService.findListByMember(member);
		List<Trial> trialNews = trialService.findList(4, null, orders);
		model.addAttribute("page",  trialApplyService.findPage(filters, orders, pageable));
		model.addAttribute("trialNews", trialNews);
		model.addAttribute("member", member);
//		List<Trial> trials = trialService.findOrderByEndDateAndDate(new Date());
//		model.addAttribute("trials", trials);
		return "/shop/member/trialApply/applyList";   
	}
	
	@RequestMapping(value = "/reportList",method = RequestMethod.GET)
	public String reportList(Long applyId ,Integer pageNumber, Integer pageSize ,ModelMap model) {
		if ((pageNumber == null) || (pageNumber.equals(new Integer(0)))) {
	        pageNumber = new Integer(1);
	      }
	      if ((pageSize == null) || (pageSize.equals(new Integer(0)))) {
	        pageSize = new Integer(4);
	      }
	      Pageable pageable = new Pageable(pageNumber, pageSize);
		Member member = memberService.getCurrent();
		List<Order> orders = new ArrayList<Order>();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setOperator(Operator.eq);
		filter.setProperty("member");
		filter.setValue(member);
		filters.add(filter);
		model.addAttribute("pageNumber", pageNumber);
	    model.addAttribute("pageSize", pageSize);
		model.addAttribute("applyId", applyId);
		//model.addAttribute("applys", applys);
		model.addAttribute("page", trialReportService.findPage(filters, orders,pageable));
		model.addAttribute("member", member);
	  //List<Trial> trials = trialService.findOrderByEndDateAndDate(new Date());
	  //model.addAttribute("trials", trials);
		return "/shop/member/trialApply/reportList";   
	}
	
	@RequestMapping(value = "/reportContent/{reportId}",method = RequestMethod.GET)
	public String reportContent(@PathVariable Long reportId ,ModelMap model) {
		Setting setting = SettingUtils.get();
		String siteUrl = setting.getSiteUrl();
		Member member = memberService.getCurrent();
		TrialReport report =trialReportService.find(reportId);
		List<Order> orders = new ArrayList<Order>();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setOperator(Operator.eq);
		filter.setProperty("product");
		filter.setValue(report.getProduct());
		List<TrialReport> reports = trialReportService.findList(2, filters, orders);
		model.addAttribute("siteUrl", siteUrl);
		model.addAttribute("report", report);
		model.addAttribute("reports", reports);
		model.addAttribute("member", member);
		List<Trial> trials = trialService.findOrderByEndDateAndDate(new Date());
		model.addAttribute("trials", trials);
		return "/shop/member/trialApply/reportContent";   
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public @ResponseBody
	Message saveReport(TrialReport report,Long areaId ,Long trialApplyId,RedirectAttributes redirectAttributes) {
		System.out.println("进入--");
		Member member = memberService.getCurrent();
		TrialApply apply = trialApplyService.find(trialApplyId);
		if(member == null){
			return Message.error("用户还没有登陆");
		}
		if(apply == null){
			return Message.warn("试用申请不存在");
		}
		Trial trial = trialService.find(apply.getTrial().getId());
		Long productId = apply.getProduct().getId();
		Product product = productService.find(productId);
		
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-HHmmss-sss");
		String no = sf.format(date);
		report.setProduct(product);
		report.setCreateDate(date);
		report.setModifyDate(date);
		report.setTrialReportNo("report-"+no);
		report.setCreatedBy(member.getUsername());
		report.setArea(apply.getArea()); //
		report.setMember(member); //
		report.setTrial(trial); //
		System.out.println("xxxxxxxxxxx--->"+report.getArea().getId());
		System.out.println("xxxxxxxxxxx--->"+report.getTrial().getId());
		report.setTrialApply(apply); //
	   System.out.println("xxxxxxxxxxx--->"+report.getTrialApply().getId());
		System.out.println("xxxxxxxxxxx--->"+member.getUsername());
		report.setReportStatus("approving");
		System.out.println("--loi--"+1);
		try {
			trialReportService.save(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("--loi--"+2);
		return Message.success("试用报告提交成功");
	}
}
