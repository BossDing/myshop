package net.shopxx.controller.gw;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.shopxx.Order;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.SearchService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("gwArticleController")
@RequestMapping("/gw/article")
public class ArticleController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public String list(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		if(articleCategory.getName().equals("家居热水解决方案")){
			long id1 = 636;
			Article article = articleService.find(id1);
			model.addAttribute("article", article);
		}else{
			model.addAttribute("article", articleService.findByName(articleCategory.getName()));
		}
		model.addAttribute("articleCategory", articleCategory);
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		pageable.setOrderDirection(Order.Direction.asc);
		pageable.setOrderProperty("order");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/list";
	}
	
	/**
	 * 新闻中心页
	 */
	@RequestMapping(value = "/xwrootlist/{id}", method = RequestMethod.GET)
	public String xwrootlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/xwrootlist";
	}
	
	/**
	 * 常见问题、安检清洗列表页
	 */
	@RequestMapping(value = "/llist/{id}", method = RequestMethod.GET)
	public String llist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
//		System.out.println("进入llist");
		ArticleCategory articleCategory = articleCategoryService.find(id);
		List<ArticleCategory> articleCategoryroot =  articleCategoryService.findParents(articleCategory);
		model.addAttribute("articleCategoryroot", articleCategoryroot.get(0));
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
//		System.out.println("111111111111111111");
		try {
			pageable.setOrderDirection(Order.Direction.asc);
			pageable.setOrderProperty("order");
			model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		pageable.setOrderDirection(Order.Direction.desc);
//		pageable.setOrderProperty("createDate");
//		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/llist";
	}
	
	
	/**
	 * 列表详情页
	 */
	@RequestMapping(value = "/lxlist/{id}", method = RequestMethod.GET)
	public String lxlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryroot =  articleCategoryService.findParents(articleCategory);
		model.addAttribute("articleCategoryroot", articleCategoryroot.get(0));
		if (article == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("article", article);
//		model.addAttribute("page", articleService.findPage(article.getArticleCategory(), null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(article.getArticleCategory(), null, pageable)); //排序由Pageable设置决定
		return "/gw/article/lxlist";
	}
	
	/**
	 * 查看文章详情
	 */
	@RequestMapping(value = "/articleDetail", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> articleDetail(Long articleId) {
		Map<String, Object> data = new HashMap<String, Object>();
		Article article = articleService.find(articleId);
		if (article == null) {
			throw new ResourceNotFoundException();
		}
		ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hotMap = new HashMap<String, Object>();
		hotMap.put("title", article.getTitle());
		hotMap.put("content", article.getContent());
		hotList.add(hotMap);
		data.put("article", hotList);
		return data;
	}
	
	/**
	 * 新闻分类列表页
	 */
	@RequestMapping(value = "/xwlist/{id}", method = RequestMethod.GET)
	public String xwlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable)); //此方法排序由id及Pageable设置决定
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/xwlist";
	}
	
	/**
	 * 新闻详情页
	 */
	@RequestMapping(value = "/xwxlist/{id}", method = RequestMethod.GET)
	public String xwxlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		if (article == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("article", article);
//		model.addAttribute("page", articleService.findPage(article.getArticleCategory(), null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(article.getArticleCategory(), null, pageable)); //排序由Pageable设置决定
		return "/gw/article/xwxlist";
	}
	
	/**
	 * 招聘分类列表页
	 */
	@RequestMapping(value = "/zplist/{id}", method = RequestMethod.GET)
	public String zplist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/zplist";
	}
	/**
	 * 招聘详情页
	 */  
	@RequestMapping(value = "/zpzlist/{id}", method = RequestMethod.GET)
	public String zpzlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		if (article == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("article", article);
//		model.addAttribute("page", articleService.findPage(article.getArticleCategory(), null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(article.getArticleCategory(), null, pageable)); //排序由Pageable设置决定
		return "/gw/article/zpzlist";
	}
	/**
	 * 招聘申请页
	 */  
	@RequestMapping(value = "/resume/{id}", method = RequestMethod.GET)
	public String resume(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		if (article == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("articleid", id);
		model.addAttribute("articlename", article.getTitle());
		return "/gw/article/resume";
	}
	/**
	 * 列表
	 */
	@RequestMapping(value = "/xlist/{id}", method = RequestMethod.GET)
	public String xlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		ArticleCategory articleCategory = article.getArticleCategory();
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("article", article);
		model.addAttribute("articleCategory", articleCategory);
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		pageable.setOrderDirection(Order.Direction.desc);
		pageable.setOrderProperty("createDate");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/xlist";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/glist/{id}", method = RequestMethod.GET)
	public String glist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryroot =  articleCategoryService.findParents(articleCategory);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleId", id);
		model.addAttribute("article", article);
		model.addAttribute("articleCategory", articleCategory);
		model.addAttribute("articleCategoryroot", articleCategoryroot.get(0));
		return "/gw/article/glist";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/plist/{id}", method = RequestMethod.GET)
	public String plist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Article article = articleService.find(id);
		ArticleCategory articleCategory = article.getArticleCategory();
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("article", article);
		model.addAttribute("articleCategory", articleCategory); 
//		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		pageable.setOrderDirection(Order.Direction.asc);
		pageable.setOrderProperty("order");
		model.addAttribute("page", articleService.findPageOrderBySetting(articleCategory, null, pageable)); //排序由Pageable设置决定
		return "/gw/article/plist";
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, Integer pageNumber, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleKeyword", keyword);
		model.addAttribute("page", searchService.search(keyword, pageable));
		return "shop/article/search";
	}

	/**
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return articleService.viewHits(id);
	}
    
	/**
	 * 文章详情
	 */
	@RequestMapping(value = "/queryArticle/{id}", method = RequestMethod.GET)
	public String queryArticle(@PathVariable Long id, ModelMap model) {
			model.addAttribute("article",articleService.find(id));
		return "/shop/article/guide" ;
	}
	
	/**
	 * 解决方案详情
	 */
	@RequestMapping(value = "/solution/{id}", method = RequestMethod.GET)
	public String solution(@PathVariable Long id, ModelMap model) {
			model.addAttribute("article",articleService.find(id));
		return "/gw/article/solution" ;
	}
	
	/**
	 * 文章详情
	 */
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String play(String name ,ModelMap model) {
//			model.addAttribute("article",articleService.find(id));
//		System.out.println("进入play");
//		System.out.println("name:"+name);
		model.addAttribute("name", name);
		return "/gw/article/play" ;
	}
	
	
	/**
	 * 原本跳转到llist方法，之后程序需要 复制了一份。
	 */
	@RequestMapping(value = "/nxwlist/{id}", method = RequestMethod.GET)
	public String nxwlist(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		List<ArticleCategory> articleCategoryroot =  articleCategoryService.findParents(articleCategory);
		model.addAttribute("articleCategoryroot", articleCategoryroot.get(0));
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
		try {
			pageable.setOrderDirection(Order.Direction.asc);
			pageable.setOrderProperty("order");
			model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/gw/article/nxwlist";
	}
	
}
