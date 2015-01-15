package net.shopxx.util;

public class SendMail {
	public static void main(String[] args) {
		/*String smtp = "smtp.qq.com";// smtp服务器
		String from = "1377572879@qq.com";// 邮件显示名称
		String to = "634635833@qq.com";// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "测试邮件";// 邮件标题
		String content = "你好！";// 邮件内容
		String username = "1377572879";// 发件人真实的账户名
		String password = "hao329075#";// 发件人密码
*/		
		
		String smtp = "smtp.163.com";// smtp服务器
		String from = "macroTest@163.com";// 邮件显示名称
		String to = "1377572879@qq.com";// 收件人的邮件地址，必须是真实地址
//		String to = "634635833@qq.com";// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "万家乐官方网站留言回复";// 邮件标题
		String content = "你好你好！";// 邮件内容
//		String username = "万家乐官方网站留言回复";// 发件人真实的账户名
		String username = "macroTest";// 发件人真实的账户名
		String password = "gztw10086";// 发件人密码
		
		
	/*	String smtp = "smtp.126.com";// smtp服务器
		String from = "macrogw@126.com";// 邮件显示名称
		String to = "634635833@qq.com";// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "";// 邮件标题
		String content = "你好你好！";// 邮件内容
		String username = "macrogw";// 发件人真实的账户名
		String password = "macrogw123#";// 发件人密码
		*/
		
		
		/*String smtp = "smtp.163.com";// smtp服务器
		String from = "gwmacro@163.com";// 邮件显示名称
//		String to = "1377572879@qq.com";// 收件人的邮件地址，必须是真实地址
		String to = "634635833@qq.com";// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "";// 邮件标题
		String content = "你好你好！";// 邮件内容
//		String username = "万家乐官方网站留言回复";// 发件人真实的账户名
		String username = "gwmacro";// 发件人真实的账户名
		String password = "gwmacro123#";// 发件人密码
*/		
		Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password);
	}
}
