/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * 
 * JavaScript - Common
 * Version: 3.0
 */

var shopxx = {
	base: "",
	locale: "zh_CN"
};

var setting = {
	priceScale: "2",
	priceRoundType: "roundHalfUp",
	currencySign: "￥",
	currencyUnit: "元",
	uploadImageExtension: "jpg,jpeg,bmp,gif,png",
	uploadFlashExtension: "swf,flv",
	uploadMediaExtension: "swf,flv,mp3,wav,avi,rm,rmvb",
	uploadFileExtension: "zip,rar,7z,doc,docx,xls,xlsx,ppt,pptx,pdf"
};

var messages = {
	"admin.message.success": "操作成功",
	"admin.message.error": "操作错误",
	"admin.dialog.ok": "确&nbsp;&nbsp;定",
	"admin.dialog.cancel": "取&nbsp;&nbsp;消",
	"admin.dialog.deleteConfirm": "您确定要删除吗？",
	"admin.dialog.clearConfirm": "您确定要清空吗？",
	"admin.browser.title": "选择文件",
	"admin.browser.upload": "本地上传",
	"admin.browser.parent": "上级目录",
	"admin.browser.orderType": "排序方式",
	"admin.browser.name": "名称",
	"admin.browser.size": "大小",
	"admin.browser.type": "类型",
	"admin.browser.select": "选择文件",
	"admin.upload.sizeInvalid": "上传文件大小超出限制",
	"admin.upload.typeInvalid": "上传文件格式不正确",
	"admin.upload.invalid": "上传文件格式或大小不正确",
	"admin.validate.required": "必填",
	"admin.validate.email": "E-mail格式错误",
	"admin.validate.url": "网址格式错误",
	"admin.validate.date": "日期格式错误",
	"admin.validate.dateISO": "日期格式错误",
	"admin.validate.pointcard": "信用卡格式错误",
	"admin.validate.number": "只允许输入数字",
	"admin.validate.digits": "只允许输入零或正整数",
	"admin.validate.minlength": "长度不允许小于{0}",
	"admin.validate.maxlength": "长度不允许大于{0}",
	"admin.validate.rangelength": "长度必须在{0}-{1}之间",
	"admin.validate.min": "不允许小于{0}",
	"admin.validate.max": "不允许大于{0}",
	"admin.validate.range": "必须在{0}-{1}之间",
	"admin.validate.accept": "输入后缀错误",
	"admin.validate.equalTo": "两次输入不一致",
	"admin.validate.remote": "输入错误",
	"admin.validate.integer": "只允许输入整数",
	"admin.validate.positive": "只允许输入正数",
	"admin.validate.negative": "只允许输入负数",
	"admin.validate.decimal": "数值超出了允许范围",
	"admin.validate.pattern": "格式错误",
	"admin.validate.extension": "文件格式错误"
};
$.fn.extend({
	upfile: function(options) {
		var settings = {
			type: "file",
			title: message("admin.browser.title"),
			isUpload: true,
			browserUrl: shopxx.base + "/admin/file/browser.jhtml",
			uploadUrl: shopxx.base + "/admin/file/upload.jhtml",
			callback: null
		};
		$.extend(settings, options);
		
		var token = getCookie("token");
		var cache = {};
		return this.each(function() {
			var browserFrameId = "browserFrame" + (new Date()).valueOf() + Math.floor(Math.random() * 1000000);
			var $browserButton = $(this);
			$browserButton.click(function() {
				var $browser = $('<div class="xxBrowser"><\/div>');
				var $browserBar = $('<div class="browserBar"><\/div>').appendTo($browser);
				var $browserFrame;
				var $browserForm;
				var $browserUploadButton;
				var $browserUploadInput;
				var $browserParentButton;
				var $browserOrderType;
				var $browserLoadingIcon;
				var $browserList;
				if (settings.isUpload) {
					$browserFrame = $('<iframe id="' + browserFrameId + '" name="' + browserFrameId + '" style="display: none;"><\/iframe>').appendTo($browserBar);
					$browserForm = $('<form action="' + settings.uploadUrl + '" method="post" encType="multipart/form-data" target="' + browserFrameId + '"><input type="hidden" name="token" value="' + token + '" \/><input type="hidden" name="fileType" value="' + settings.type + '" \/><\/form>').appendTo($browserBar);
					$browserUploadButton = $('<a href="javascript:;" class="browserUploadButton button">' + message("admin.browser.upload") + '<\/a>').appendTo($browserForm);
					$browserUploadInput = $('<input type="file" name="file" \/>').appendTo($browserUploadButton);
				}
				$browserParentButton = $('<a href="javascript:;" class="button">' + message("admin.browser.parent") + '<\/a>').appendTo($browserBar);
				$browserBar.append(message("admin.browser.orderType") + ": ");
				$browserOrderType = $('<select name="orderType" class="browserOrderType"><option value="name">' + message("admin.browser.name") + '<\/option><option value="size">' + message("admin.browser.size") + '<\/option><option value="type">' + message("admin.browser.type") + '<\/option><\/select>').appendTo($browserBar);
				$browserLoadingIcon = $('<span class="loadingIcon" style="display: none;">&nbsp;<\/span>').appendTo($browserBar);
				$browserList = $('<div class="browserList"><\/div>').appendTo($browser);

				var $dialog = $.dialog({
					title: settings.title,
					content: $browser,
					width: 470,
					modal: true,
					ok: null,
					cancel: null
				});
				
				browserList("/");
				
				function browserList(path) {
					var key = settings.type + "_" + path + "_" + $browserOrderType.val();
					if (cache[key] == null) {
						$.ajax({
							url: settings.browserUrl,
							type: "GET",
							data: {fileType: settings.type, orderType: $browserOrderType.val(), path: path},
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$browserLoadingIcon.show();
							},
							success: function(data) {
								createBrowserList(path, data);
								cache[key] = data;
							},
							complete: function() {
								$browserLoadingIcon.hide();
							}
						});
					} else {
						createBrowserList(path, cache[key]);
					}
				}
				
				function createBrowserList(path, data) {
					var browserListHtml = "";
					$.each(data, function(i, fileInfo) {
						var iconUrl;
						var title;
						if (fileInfo.isDirectory) {
							iconUrl = shopxx.base + "/resources/admin/images/folder_icon.gif";
							title = fileInfo.name;
						} else if (new RegExp("^\\S.*\\.(doc|docx|xls|pdf|zip|rar|ppt|jpg|jpeg|bmp|gif|png|DOC|DOCX|XLS|PDF|ZIP|RAR|PPT)$", "i").test(fileInfo.name)) {
							iconUrl = fileInfo.url;
							title = fileInfo.name + " (" + Math.ceil(fileInfo.size / 1024) + "KB, " + new Date(fileInfo.lastModified).toLocaleString() + ")";
						} else {
							iconUrl = shopxx.base + "/resources/admin/images/file_icon.gif";
							title = fileInfo.name + " (" + Math.ceil(fileInfo.size / 1024) + "KB, " + new Date(fileInfo.lastModified).toLocaleString() + ")";
						}
						browserListHtml += '<div class="browserItem"><img src="' + iconUrl + '" title="' + title + '" url="' + fileInfo.url + '" isDirectory="' + fileInfo.isDirectory + '" \/><div>' + fileInfo.name + '<\/div><\/div>';
					});
					$browserList.html(browserListHtml);
					
					$browserList.find("img").bind("click", function() {
						var $this = $(this);
						var isDirectory = $this.attr("isDirectory");
						if (isDirectory == "true") {
							var name = $this.next().text();
							browserList(path + name + "/");
						} else {
							var url = $this.attr("url");
							if (settings.input != null) {
								settings.input.val(url);
							} else {
								$browserButton.prev(":text").val(url);
							}
							if (settings.callback != null && typeof settings.callback == "function") {
								settings.callback(url);
							}
							$dialog.next(".dialogOverlay").andSelf().remove();
						}
					});
					
					if (path == "/") {
						$browserParentButton.unbind("click");
					} else {
						var parentPath = path.substr(0, path.replace(/\/$/, "").lastIndexOf("/") + 1);
						$browserParentButton.unbind("click").bind("click", function() {
							browserList(parentPath);
						});
					}
					$browserOrderType.unbind("change").bind("change", function() {
						browserList(path);
					});
				}
				
				$browserUploadInput.change(function() {
					var allowedUploadExtensions;
					if (settings.type == "flash") {
						allowedUploadExtensions = setting.uploadFlashExtension;
					} else if (settings.type == "media") {
						allowedUploadExtensions = setting.uploadMediaExtension;
					} else if (settings.type == "file") {
						allowedUploadExtensions = setting.uploadFileExtension;
					} else {
						allowedUploadExtensions = setting.uploadImageExtension;
					}
					if ($.trim(allowedUploadExtensions) == "" || !new RegExp("^\\S.*\\.(" + allowedUploadExtensions.replace(/,/g, "|") + ")$", "i").test($browserUploadInput.val())) {
						$.message("warn", message("admin.upload.typeInvalid"));
						return false;
					}
					$browserLoadingIcon.show();
					$browserForm.submit();
				});
				
				$browserFrame.load(function() {
					var text;
					var io = document.getElementById(browserFrameId);
					if(io.contentWindow) {
						text = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML : null;
					} else if(io.contentDocument) {
						text = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
					}
					if ($.trim(text) != "") {
						$browserLoadingIcon.hide();
						var data = $.parseJSON(text);
						if (data.message.type == "success") {
							if (settings.input != null) {
								settings.input.val(data.url);
							} else {
								$browserButton.prev(":text").val(data.url);
							}
							if (settings.callback != null && typeof settings.callback == "function") {
								settings.callback(data.url);
							}
							cache = {};
							$dialog.next(".dialogOverlay").andSelf().remove();
						} else {
							$.message(data.message);
						}
					}
				});
				
			});
		});
	}
});
