package net.shopxx.controller.shop.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Article;
import net.shopxx.service.NoticeService;

@Controller("shopMemberNoticeController")
@RequestMapping("/member/notice")
public class NoticeController extends BaseController {
	@Resource(name="noticeServiceImpl")
	private NoticeService noticeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)  
	public String list(Integer pageNumber, Integer pageSize,ModelMap model){
		if ((pageNumber == null) || (pageNumber.equals(new Integer(0)))) {
	        pageNumber = new Integer(1);
	      }
	      if ((pageSize == null) || (pageSize.equals(new Integer(0)))) {
	        pageSize = new Integer(6);
	      }
	      Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("page", noticeService.findPublishedPage(pageable));
		return "shop/member/notice/list";
	}  

	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
	public String content(@PathVariable Long id, Pageable pageable ,ModelMap model) {
		model.addAttribute("notice",noticeService.find(id));
		model.addAttribute("page", noticeService.findPublishedPage(pageable));
		return "/shop/member/notice/content" ;
	}
}
