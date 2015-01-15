<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${setting.siteName}-${subject}</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
</head>
<body>
    [#if status == true]
		<p>
			尊敬的${admin.store.applyMan}，您好！欢迎来到 ${setting.siteName}。
		</p>
		<p>
			您申请的店铺已通过官方审核。
		</p>
        <p>
			您的店铺管理员账号为：${admin.username}
		</p>
        <p>
			您的登录密码为：${password}
		</p>
        <p>
			${setting.siteName}店铺管理员登录地址：<a href="${setting.siteUrl}/admin">${setting.siteUrl}/admin</a>
		</p>
        <p>
			您的店铺地址：<a href="${admin.store.indexUrl}">${admin.store.indexUrl}</a>
		</p>
    [#else]
    	<p>
			尊敬的${store.applyMan}，您好！欢迎来到 ${setting.siteName}。
		</p>
		<p>
			很抱歉，您申请的店铺未通过官方审核。
		</p>
    [/#if]
   	<p>
		<a href="${setting.siteUrl}">${setting.siteName}</a>
	</p>
</body>
</html>