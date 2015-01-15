package net.shopxx.controller.shop;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.shopxx.service.MemberService;
import net.shopxx.service.PointsWaterService;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.bcel.internal.generic.NEW;
/**
 * Controller - 设置邀请者id
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopInviterController")
@RequestMapping("/inviter")
public class InviterController {
	@Resource(name = "pointsWaterServiceImpl")
	PointsWaterService pointsWaterService;
	@Resource(name="memberServiceImpl")
	MemberService memberService;
	/**
	 * 设置邀请者ID
	 */
	@RequestMapping(value = "/set_inviter_id", method = RequestMethod.GET)
	public void setInviterId(String inviter_id,HttpSession session) {
		session.setAttribute("inviter_id", inviter_id);
	}

//	/**
//	 * 获取人人营销数据
//	 */
//	@RequestMapping(value = "/get_points_data", method = RequestMethod.GET)
//	public @ResponseBody 
//	String getPointsData() {
//		Long[] data=new Long[3];
//		// 获取参与推荐人数
//		data[0]=pointsWaterService.getCountMembers();
//		// 获取累计送出积分
//		data[1]=pointsWaterService.getSumPoints();
//		// 推荐人最高积分
//		data[2]=memberService.getMaxPoints();
//		String dataString=StringUtils.join(data,",");
//		return dataString;
//	}
}
