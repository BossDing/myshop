package net.shopxx.controller.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Filter.Operator;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;
import net.shopxx.entity.TrialRemind;
import net.shopxx.entity.TrialReport;
import net.shopxx.service.MemberService;
import net.shopxx.service.TrialRemindService;
import net.shopxx.service.TrialReportService;
import net.shopxx.service.TrialService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 试用首页
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Controller("TrialController")
@RequestMapping("/trial")
public class TrialController extends BaseController {

	@Resource(name = "trialRemindServiceImpl")
	private TrialRemindService trialRemindService;
	@Resource(name = "trialServiceImpl")
	private TrialService trialService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "trialReportServiceImpl")
	private TrialReportService trialReportService;
	
	@RequestMapping(value = "/trial_index", method = RequestMethod.GET)
	public String trial_index(HttpServletRequest request, ModelMap model) {
		List<Trial> trials = trialService.findList(true, false, 1, null, null);
		List<Trial> nextTrials = trialService.findList(false, null, 1, null,
				null);
		List<Order> orders = new ArrayList<Order>();
		Order o = new Order();
		o.setDirection(Order.Direction.desc);
		o.setProperty("endDate");
		orders.add(o);
		List<Trial> trialReports = trialService.findList(null,true,2, null, orders);
		List<String> areas = new ArrayList<String>();
		if (trials.size() > 0) {
			for (TrialApply t : trials.get(0).getTrialApplys()) {
				if (t.getArea() != null) {
					if (t.getArea().getParent() != null) {
						if (t.getArea().getParent().getParent() != null) {
							areas.add(t.getArea().getParent().getParent()
									.getFullName());
						} else {
							areas.add(t.getArea().getParent().getFullName());
						}
					} else {
						areas.add(t.getArea().getFullName());
					}
				}
			}
		}else{
			if (trialReports.size() > 0) {
				for (TrialApply t : trialReports.get(0).getTrialApplys()) {
					if (t.getArea() != null) {
						if (t.getArea().getParent() != null) {
							if (t.getArea().getParent().getParent() != null) {
								areas.add(t.getArea().getParent().getParent()
										.getFullName());
							} else {
								areas.add(t.getArea().getParent().getFullName());
							}
						} else {
							areas.add(t.getArea().getFullName());
						}
					}
				}
			}
		}
		List<Trial> endTrials = trialService.findList(null, true, 1, null,orders);
		model.addAttribute("endTrials", endTrials);
		model.addAttribute("nextTrials", nextTrials);
		model.addAttribute("trialReports", trialReports);
		model.addAttribute("trials", trials);
		model.addAttribute("areas", areas);
		return "/shop/trial/index";
	}

	@RequestMapping(value = "/content/{trialId}", method = RequestMethod.GET)
	public String content(@PathVariable
	Long trialId, HttpServletRequest request, ModelMap model) {
		Trial trial = trialService.find(trialId);
		List<String> areas = new ArrayList<String>();
		if (trial != null) {
			for (TrialApply t : trial.getTrialApplys()) {
				if (t.getArea() != null) {
					if (t.getArea().getParent() != null) {
						if (t.getArea().getParent().getParent() != null) {
							areas.add(t.getArea().getParent().getParent()
									.getFullName());
						} else {
							areas.add(t.getArea().getParent().getFullName());
						}
					} else {
						areas.add(t.getArea().getFullName());
					}
				}
			}
		}
		model.addAttribute("trial", trial);
		model.addAttribute("areas", areas);
		return "/shop/trial/content";
	}

	@RequestMapping(value = "/toApply/{trialId}", method = RequestMethod.GET)
	public String toApply(@PathVariable
	Long trialId, HttpServletRequest request, ModelMap model) {
		Member member = memberService.getCurrent();
		List<Trial> trials = trialService.findList(true, false, 1, null, null);
		Trial trial = trialService.find(trialId);
		Product product = trial.getProducts().iterator().next();
		List<Order> orders = new ArrayList<Order>();
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setOperator(Operator.eq);
		filter.setProperty("product");
		filter.setValue(product);
		List<TrialReport> reports = trialReportService.findList(3, filters, orders);
		model.addAttribute("product", product);
		model.addAttribute("reports", reports);
		model.addAttribute("member", member);
		model.addAttribute("trial", trial);
		model.addAttribute("trials", trials);
		
		return "/shop/trial/apply";
	}

	@RequestMapping(value = "/toRemind/{trialId}", method = RequestMethod.GET)
	public String toRemind(@PathVariable
	Long trialId, HttpServletRequest request, ModelMap model) {
		Trial trial = trialService.find(trialId);
		model.addAttribute("trial", trial);
		return "/shop/trial/remind";
	}

	@RequestMapping(value = "/saveRemind", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveRemind(TrialRemind trialRemind,
			HttpServletRequest request, ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		trialRemindService.save(trialRemind);
		data.put("msg", "success");
		return data;
	}

	@RequestMapping(value = "/haveRemind", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> haveRemind(TrialRemind trialRemind,
			HttpServletRequest request, ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<TrialRemind> list = trialRemindService.findAll();
		for (TrialRemind tr : list) {
			if (tr.getTel().equals(trialRemind.getTel())
					&& (tr.getTrialRoleId() == trialRemind.getTrialRoleId())) {
				data.put("msg", "fail");
				return data;
			}
		}
		data.put("msg", "success");
		return data;
	}
}