package net.shopxx.controller.shop.member;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.shopxx.controller.admin.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.PointsWater;
import net.shopxx.service.MemberService;
import net.shopxx.service.PointsWaterService;
import net.shopxx.service.SignInPointRuleService;

@Controller("shopSignInController")
@RequestMapping("/member/sign_in")
public class SignInController extends BaseController {
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "signInPointRuleServiceImpl")
	private SignInPointRuleService signInPointRuleService;
	@Resource(name = "pointsWaterServiceImpl")
	private PointsWaterService pointsWaterService;

	/**
	 * 签到送积分。
	 */
	@RequestMapping(value = "/check_and_sign_in", method = RequestMethod.GET)
	public @ResponseBody
	String signIn() {
		// 返回数据，第一个元素表示是否领取成功，第二个为领取后的会员总积分。
		String[] dataArr = new String[2];
		Member member = memberService.getCurrent();

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateformat.format(new Date());
		String signInDate = member.getSignInDate() == null ? null : dateformat
				.format(member.getSignInDate());
		if (!today.equals(signInDate)) {// 今天还没签到。
			// 本次领取的积分。
			Integer point = signInPointRuleService
					.getPointOfSerialSignInTimes(member
							.getSerialSignInTimes());
			// 修改会员积分
			member.setPoint(member.getPoint() == null ? point : member
					.getPoint() + point);
			// 修改会员连续签到次数（增加1）
			member.setSerialSignInTimes(member.getSerialSignInTimes() == null ? 1
					: member.getSerialSignInTimes() + 1);
			// 修改会员最后签到日期
			member.setSignInDate(new Date());
			memberService.update(member);
			// 记录到积分流水表中。
			PointsWater pw = new PointsWater();
			pw.setMember_id(member.getId());
			pw.setPoints(point);
			pw.setPoints_stat(1);// 1表示收入，2表示支出
			pw.setRulename("签到送积分");
			pointsWaterService.save(pw);
			dataArr[0] = "true";
			dataArr[1] = member.getPoint().toString();
		} else {
			dataArr[0] = "false";
		}
		return StringUtils.join(dataArr, ',');
	}

	/**
	 * 查询当天是否未签到和签到可获得的积分。
	 */
	@RequestMapping(value = "/get_sign_in_point", method = RequestMethod.GET)
	public @ResponseBody
	String getSignInPoint() {
		// 返回数据，第一个元素表示是否未签到，第二个为当天签到可获得的积分。
		String[] dataArr = new String[2];
		Member member = memberService.getCurrent();

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateformat.format(new Date());
		String signInDate = member.getSignInDate() == null ? null : dateformat
				.format(member.getSignInDate());
		Integer point =0;
		if (!today.equals(signInDate)) {// 今天还没签到。
			// 本次领取的积分。
			point = signInPointRuleService
					.getPointOfSerialSignInTimes(member
							.getSerialSignInTimes());
			dataArr[0] = "true";
		} else {// 如果已经签到了。
			/*
			 * 调用getMaxPointOfSerialSignInTimes方法查询的是根据连续签到次数应该获得的积分，如果用户当天签到后，连续签
			 * 到次数增加，查询出来的积分可能是下一次签到获取的积分，前台显示为当天可获取的积分领取与否，并非下次可领取积分 。
			 */
			point = signInPointRuleService
					.getPointOfSerialSignInTimes(member
							.getSerialSignInTimes() - 1);
			dataArr[0] = "false";
		}
		dataArr[1] = point.toString();
		return StringUtils.join(dataArr, ',');
	}

}
