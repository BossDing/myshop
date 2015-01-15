Ext.ns('CPC.Transaction_Order');
/*******************************************************************************
 * 商品搜索
 * author: Chengandou
 * data:2014-04-03
 * modifyBy:
 * modificationtime:
*******************************************************************************
 */
CPC.Transaction_Order = Ext.extend(CPC.BaseForm, {
	init : function() {
		Ext.regModel('transaction_order', {
			fields : ['order_id','order_no', 'entcode', 'sum_qty', 'docid','isempty', 'sum_amount', 'create_time','pay_stat','order_stat']
		});

		var _this = this;
		var groupingBase = {
			xtype : 'dataview',
			itemSelector : 'div.search_result',
			tpl : 	
			'<div class="Big">'
			+'<div class="dingdan-top">'
			+'	  <table>'
			+'		  <tr>'
			+'			  <td><a href="/eshop/povosdgmb/index.jsp"><span class="dingdan-top-left"></span></a></td>'
			+'			  <td class="dingdan-top-center">订单列表</td>'
			+'			  <td><a href="/eshop/povosdgmb/jsp/shop_cart.jsp"><span class="dingdan-top-right"></span></a></td>'
			+'		  </tr>'
			+'	  </table>'
			+'</div>'
			+'</div>'
			+'<div class="dingdan-big">'
			+'   <div class="shouhuo">'
			+'      <tpl for=".">'
			+'  	    <tpl if="isempty == 2 ">'
			+'				<div class="shouhuo-b">'
			+'					<div class="photo">'
			+'						<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">'
			+'						<img src="/cpcimageview?entcode={entcode}&docid={docid}" width="62" height="62"></a></div>'
			+'					<ul class="dfjsdh">'
			+'						<li class="shouhuo-b-a">{sum_qty}件<br />共计:{sum_amount}元<br />{create_time}</li>'
			+'						<li>订单编号：[<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">{order_no}</a>]</li>'
			+'						<li><span>待收货确认</span></li>'
			+'					</ul>'
			+'					<div class="right"><a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}"><img src="../images/jie_50.png" width="17" height="35"></a></div>'
			+'				</div>'
			+'     		</tpl>'
			+'  	    <tpl if="isempty == 3 ">'
			+'				<div class="shouhuo-b">'
			+'					<div class="photo">'
			+'						<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">'
			+'						<img src="/cpcimageview?entcode={entcode}&docid={docid}" width="62" height="62"></a></div>'
			+'					<ul class="dfjsdh">'
			+'						<li class="shouhuo-b-a">{sum_qty}件<br />共计:{sum_amount}元<br />{create_time}</li>'
			+'						<li>订单编号：[<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">{order_no}</a>]</li>'
			+'						<li><span>支付失败</span></li>'
			+'					</ul>'
			+'					<div class="right"><a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}"><img src="../images/jie_50.png" width="17" height="35"></a></div>'
			+'				</div>'
			+'     		</tpl>'
			+'  	    <tpl if="isempty == 4 ">'
			+'				<div class="shouhuo-b">'
			+'					<div class="photo">'
			+'						<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">'
			+'						<img src="/cpcimageview?entcode={entcode}&docid={docid}" width="62" height="62"></a></div>'
			+'					<ul class="dfjsdh">'
			+'						<li class="shouhuo-b-a">{sum_qty}件<br />共计:{sum_amount}元<br />{create_time}</li>'
			+'						<li>订单编号：[<a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}">{order_no}</a>]</li>'
			+'						<li><span>已确定收货</span></li>'
			+'					</ul>'
			+'					<div class="right"><a href="/eshop/povosdgmb/jsp/dingdanxinxi.jsp?order_id={order_id}"><img src="../images/jie_50.png" width="17" height="35"></a></div>'
			+'				</div>'
			+'     		</tpl>'
			+'    	    <tpl if=\"isempty == 1\">'
			+'				<div class="meiyou">'
			+'					<div class="hoi">您还没有订单</div>'
			+'					<div class="fot"><a href="/eshop/povosdgmb/index.jsp">去逛逛</a></div>'
			+'				</div>'
			+'          </tpl>'
			+'      </tpl>'
			+'		</div>'
			+'		<div class="bottom">'
			+'			<ul>'
			+'				<li><a id="bottom_link1"></a></li>'
        	+'				<li class="bottom-a"><a id="bottom_link2"></a></li>'
        	+'				<li><a href="#">返回顶部</a></li>'
			+'			</ul>'
			+'		</div>'
			+'</div>',

			selModel : {
				mode : 'single',
				allowDeselect : false
			},

			indexBar : false,
			listeners : {
				selectionchange : {
					fn : function(sel, records) {},
					scope : this
				}
			},
			onItemDisclosure : false,

			store : new Ext.data.Store({
				model : 'transaction_order',
				sorters : 'order_no',
				data : [ ]
			})
		};

		this.panel = new Ext.Panel({
			fullscreen : true,
			autoHeight : true,
			collapsible : true,
			layout : 'fit',
			scroll : 'vertical',
			items : [groupingBase]
		});
		/***************************/
		var obj = new shop_order();
		obj.page = 1;
	    obj.pagesize = 10;
		this.requestEx({
			bizobj : obj,
			action : 'gtransactionshoporder',
			success : function() {
				var datas = obj.getDataRelation('shop_order').data;

				if(datas ==null || datas.length<=0){
					obj.isempty = 1;
					groupingBase.store.add(obj);
				}
				for (var i = 0; i < datas.length; i++) {
					if(datas[i].pay_stat==3){
						datas[i].isempty = 3;
					}else if(datas[i].pay_stat == 2 && datas[i].order_stat >=7){
						datas[i].isempty = 4;
					}else if(datas[i].pay_stat == 2 && datas[i].order_stat <7){
						datas[i].isempty = 2;
					}
					groupingBase.store.add(datas[i]);
				}
				if (userId == "guest") {//没登录
					$('#bottom_link1').attr('href', '/eshop/povosdgmb/jsp/login.jsp?redirectUrl='+encodeURIComponent(encodeURIComponent(location.href)));
					$('#bottom_link1').html('登录');
					$('#bottom_link2').attr('href', '/eshop/povosdgmb/jsp/register.jsp');
					$('#bottom_link2').html('注册');
				} else {
					$('#bottom_link1').attr('href', '/eshop/povosdgmb/jsp/user.jsp');
					$('#bottom_link1').html('个人中心');
					$('#bottom_link2').attr('href', '/eshop/povosdg/Mobilelogout.do');
					$('#bottom_link2').html('退出');
				}
			}
		});
		
	},
	
	toIndex : function(){
		document.location.href = "/eshop/povosdgmb/index.jsp";//返回首页
	}

});
var mainForm;

// 程序入口
Ext.setup({
	tabletStartupScreen : 'tablet_startup.png',
	phoneStartupScreen : 'phone_startup.png',
	icon : 'icon.png',
	glossOnIcon : false,
	onReady : function() {
		mainForm = new CPC.Transaction_Order();
	}
});

