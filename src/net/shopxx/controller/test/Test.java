package net.shopxx.controller.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @Description
	 * @author Guoxianlong
	 * @create_date Sep 9, 2014 10:15:15 AM
	 */
	public static void main(String[] args) {
		String str = "<div class=\"title\">\r\n\t<br />\r\n</div>\r\n<div>\r\n\t<p sizset=\"51\" sizcache=\"23\">\r\n\t\t<img align=\"absMiddle\" src=\"http://img04.taobaocdn.com/imgextra/i4/810471546/T2m3K.XUNXXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img04.taobaocdn.com/imgextra/i4/810471546/T2m3K.XUNXXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img01.taobaocdn.com/imgextra/i1/810471546/T2OyW.XHVXXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img01.taobaocdn.com/imgextra/i1/810471546/T2OyW.XHVXXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img04.taobaocdn.com/imgextra/i4/810471546/T2gluKXpRbXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img04.taobaocdn.com/imgextra/i4/810471546/T2gluKXpRbXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img02.taobaocdn.com/imgextra/i2/810471546/T2Jne8XN0XXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img02.taobaocdn.com/imgextra/i2/810471546/T2Jne8XN0XXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img03.taobaocdn.com/imgextra/i3/810471546/T2CfS.XQlXXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img03.taobaocdn.com/imgextra/i3/810471546/T2CfS.XQlXXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img03.taobaocdn.com/imgextra/i3/810471546/T2owPUXvdaXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img03.taobaocdn.com/imgextra/i3/810471546/T2owPUXvdaXXXXXXXX-810471546.jpg\" /><img align=\"absMiddle\" src=\"http://img02.taobaocdn.com/imgextra/i2/810471546/T2ZYi7XIRaXXXXXXXX-810471546.jpg\" width=\"710\" _ke_saved_src=\"http://img02.taobaocdn.com/imgextra/i2/810471546/T2ZYi7XIRaXXXXXXXX-810471546.jpg\" /> \r\n\t</p>\r\n</div>";
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher m = p.matcher(str);
        while(m.find()){
            System.out.println(m.group(1));
        }
	}

}
