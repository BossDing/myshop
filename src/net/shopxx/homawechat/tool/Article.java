package net.shopxx.homawechat.tool;
/**
 *
 * @author xiaolai
 * @version 创建时间：Feb 28, 2014 11:22:44 AM
 * 类说明 
 */
public class Article {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;

	public Article()
	{
	}

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getDescription()
	{
		return Description != null ? Description : "";
	}

	public void setDescription(String description)
	{
		Description = description;
	}

	public String getPicUrl()
	{
		return PicUrl != null ? PicUrl : "";
	}

	public void setPicUrl(String picUrl)
	{
		PicUrl = picUrl;
	}

	public String getUrl()
	{
		return Url != null ? Url : "";
	}

	public void setUrl(String url)
	{
		Url = url;
	}
}
