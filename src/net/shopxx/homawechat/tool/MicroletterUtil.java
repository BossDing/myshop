package net.shopxx.homawechat.tool;

import javax.annotation.Resource;

import net.shopxx.entity.Pushevent;
import net.shopxx.service.PusheventService;

/**
 *
 * @author shenlong
 * @version 3.0
 * 类说明 
 */
public class MicroletterUtil {
//	
//	/**　查询用户事件
//	 * @param fromUserName
//	 * @param cpcDao
//	 * @return String 
//	 * @throws SinocpcException 
//	 * @throws
//	 */
//	public static String findEventKey(String fromUserName) {
//		StringBuffer tempSql = new StringBuffer();
////		tempSql.append("select pe.eventkey from PushEvent pe where pe.fromusername = '"+fromUserName+"'");
////		String eventkey = DaoUtil.CreateWEIXINDao().strSelect(tempSql.toString());
//		pushevent = pusheventService.findByFromusername(fromUserName);
//		if(null != pushevent.getEventkey() && pushevent.getEventkey().trim().length() > 0){
//			return pushevent.getEventkey();
//		} else {
//			return "";
//		}
//	}
//
//	/**查询用户事件主键Id
//	 * @param fromUserName
//	 * @param cpcDao
//	 * @return String 
//	 * @throws SinocpcException 
//	 * @throws
//	 */
//	public static String findEventObjId(String fromUserName) throws Exception{
//		StringBuffer tempSql = new StringBuffer();
////		tempSql.append("select pe.objid from PushEvent pe where pe.fromusername = '"+fromUserName+"'");
////		String eventkey = DaoUtil.CreateWEIXINDao().strSelect(tempSql.toString());
//		pushevent = pusheventService.findByFromusername(fromUserName);
//		if(null != pushevent.getEventkey() && pushevent.getEventkey().trim().length() > 0){
//			return pushevent.getEventkey();
//		} else {
//			return "";
//		}
//	}
//	/**
//	 * 增加事件
//	 * @param FromUserName
//	 * @param ToUserName
//	 * @param CreateTime
//	 * @throws SinocpcException
//	 */
//	public static void addEventKey(String FromUserName,String ToUserName,String CreateTime) throws Exception{
////		StringBuffer tempSql = new StringBuffer();
////		tempSql.append("insert into PushEvent\n" +
////				"  (tousername, fromusername, createtime, msgtype, event, eventkey)\n" + 
////				"values\n" + 
////				"  ('"+ToUserName+"', '"+FromUserName+"', '"+CreateTime+"', 'event', 'CLICK', 'BranBinDing')");
////		 DaoUtil.CreateWEIXINDao().execute(tempSql.toString());
//		pushevent.setTousername(ToUserName);
//		pushevent.setFromusername(FromUserName);
//		pushevent.setCreatetime(CreateTime);
//		pushevent.setMsgtype("event");
//		pushevent.setEvent("CLICK");
//		pushevent.setEventkey("BranBinDing");
//		pusheventService.save(pushevent);
//		
//	}
//	
	/****
	 * clearStringBuffer方法慨述:清除StringBuffer
	 * 创 建  人：huanghy
	 * 创建时间：2013-12-11 上午11:07:17
	 * 修 改  人：(修改了该文件，请填上修改人的名字)
	 * 修改日期：(请填上修改该文件时的日期)
	 * @param tempSbf void 
	 * @throws
	 */
	public static void clearStringBuffer(StringBuffer tempSbf){
		if(null != tempSbf && tempSbf.length() > 0){
			tempSbf.delete(0,tempSbf.length());
		}
	}
//	/**
//	 * delPushEvent方法慨述:删除用户记录事件
//	 * 创 建  人：huanghy
//	 * 创建时间：2013-12-11 上午11:16:01
//	 * 修 改  人：(修改了该文件，请填上修改人的名字)
//	 * 修改日期：(请填上修改该文件时的日期)
//	 * @param fromusername用户微信账号
//	 * @param tempSql
//	 * @param cpcDao数据连接
//	 * @return void(暂无)
//	 * @throws SinocpcException 
//	 */
//	public static void delPushEvent(String fromusername) throws Exception{
////		StringBuffer tempSql =new StringBuffer("delete from PushEvent pe   where pe.fromusername = '"+fromusername+"'");
////		 DaoUtil.CreateWEIXINDao().execute(tempSql.toString());
//	}
//	/**
//	 * isEventKey方法慨述:是否记录事件
//	 * 创 建  人：huanghy
//	 * 创建时间：2013-12-11 下午5:16:17
//	 * 修 改  人：(修改了该文件，请填上修改人的名字)
//	 * 修改日期：(请填上修改该文件时的日期)
//	 * @param fromUserName
//	 * @param cpcDao
//	 * @param tempSql
//	 * @return boolean 
//	 * @throws SinocpcException 
//	 */
//	public static boolean isEventKey(String fromUserName) throws Exception{
////		return false;
////		StringBuffer tempSql =new StringBuffer();
////		tempSql.append("select pe.eventkey from PushEvent pe where pe.fromusername = '"+fromUserName+"'");
////		String tempEventKey = DaoUtil.CreateWEIXINDao().strSelect(tempSql.toString());
////		if(null != tempEventKey 
////				&& tempEventKey.trim().length() > 0){
////			return false;
////		} else {
////			return true;
////		}
//		pushevent = pusheventService.findByFromusername(fromUserName);
//		if(null != pushevent.getEventkey() && pushevent.getEventkey().trim().length() > 0){
//			return false;
//		} else {
//			return true;
//		}
//	}
//	
//	/**
//	 * isCheckUser方法慨述:检查用户是否已绑定服务平台
//	 * 创 建  人：huanghy
//	 * 创建时间：2013-12-11 下午5:16:17
//	 * 修 改  人：(修改了该文件，请填上修改人的名字)
//	 * 修改日期：(请填上修改该文件时的日期)
//	 * @param fromUserName
//	 * @param cpcDao
//	 * @param tempSql
//	 * @return boolean 
//	 * @throws DatabaseException
//	 */
//	public static int isCheckUser(String fromUserName)  throws Exception{
//		return 0;
////		CPCDao dao = new CPCDao(DaoUtil.EntCode);
////		dao.setTransation(false);
////		StringBuffer tempSql = new StringBuffer();
////		tempSql.append("select count(1)   from bas_service_site bss where bss.microno = '"+fromUserName+"'");
////		int nCount=dao.intSelect(tempSql.toString());
////		dao.freeConnection();
////		if(nCount>0)return 1;
////		return 0;
//	}
//	
//	/**
//	 * getUserId方法慨述:获取当前微信号的用户名
//	 * 创 建  人：huanghy
//	 * 创建时间：2013-12-11 下午5:16:17
//	 * 修 改  人：(修改了该文件，请填上修改人的名字)
//	 * 修改日期：(请填上修改该文件时的日期)
//	 * @param fromUserName
//	 * @return String 
//	 * @throws DatabaseException
//	 */
//	public static String getUserId(String fromUserName)  throws Exception{
//		return fromUserName;
////		CPCDao dao = new CPCDao(DaoUtil.EntCode);
////		dao.setTransation(false);
////		StringBuffer tempSql = new StringBuffer();
////		tempSql.append(
////				"select account\n" + 
////				"  from shop_member\n" + 
////				" where service_site_id in\n" + 
////				"       (select service_site_id\n" + 
////				"          from bas_service_site\n" + 
////				"         where microno =  '"+fromUserName+"'");
////		String userid=dao.strSelect(tempSql.toString());
////		dao.freeConnection();
////		return userid;
//	}
//	/**
//	 * Main方法慨述:主方法
//	 * 创 建  人：huanghy
//	 * 创建时间：2013-12-11 下午5:16:17
//	 * 修 改  人：(修改了该文件，请填上修改人的名字)
//	 * 修改日期：(请填上修改该文件时的日期)
//	 * @param fromUserName
//	 * @return String 
//	 * @throws DatabaseException
//	 */
////	public static String InitDb(Element resEle) throws SinocpcException {
////		String FromUserName = resEle.elementText("FromUserName");
////		String ToUserName = resEle.elementText("ToUserName");
////		String CreateTime = resEle.elementText("CreateTime");
////		int nCount=MicroletterUtil.isCheckUser(FromUserName);
////		if(nCount==1){
////			String UserId = MicroletterUtil.getUserId(FromUserName);
////			DaoUtil.CreateSysUseObject(UserId);
////			return "";
////		}else{
////			DaoUtil.CreateSysUseObject("guest");
////			StringBuffer tempSql=new StringBuffer();
////			tempSql.append("insert into PushEvent\n" +
////					"  (tousername, fromusername, createtime, msgtype, event, eventkey)\n" + 
////					"values\n" + 
////					"  ('"+ToUserName+"', '"+FromUserName+"', '"+CreateTime+"', 'event', 'CLICK', 'BranBinDing')");
////			
////			return "亲，请输入您所在网点联系人登记的手机号码即可进行区域绑定，我们会将本区域所有服务订单信息推送到您的微信上，多谢合作[抱拳]";
////		}
////		
////	}
}
