function shop_product() {
	this.type_id = null;
	this.type_code = null;
	this.type_name = null;
	this.check_num = null;
	this.page = null;
	this.pagesize = null;
	this.picture_id = null;
	this.addDataRelation("shop_product", "shop_product");		//新品上架
	this.addDataRelation("shop_hotproduct", "shop_hotproduct");	//热卖商品
	this.addDataRelation("shop_recproduct", "shop_recproduct");	//特惠商品
	this.addDataRelation("shop_product_collection", "shop_product_collection");	//banner商品
}
shop_product.prototype = new CPC.BizObject;
shop_product.prototype.getClass = function() {
	return "shop_product";
};
shop_product.prototype.getNameProp = function() {
	return '';
};
shop_product.prototype.getPKProp = function() {
	return 'item_id';
}; 
shop_product.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_product.prototype.getObjType = function() {
	return 16;
};

function shop_product_type() {
	this.superid = null;
	this.addDataRelation("shop_product_type", "shop_product_type");
}
shop_product_type.prototype = new CPC.BizObject;
shop_product_type.prototype.getClass = function() {
	return "shop_product_type";
};
shop_product_type.prototype.getNameProp = function() {
	return '';
};
shop_product_type.prototype.getPKProp = function() {
	return 'item_id';
}; 
shop_product_type.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_product_type.prototype.getObjType = function() {
	return 16;
};
/**购物车*/
function shop_cart() {
	this.item_id = null;
	this.addr_id = null;
	this.account = null;
	this.qty = null;
	this.item_code = null;
	this.item_name = null;
	this.flag = null;
	this.item_ids = null;
	this.is_invoice = null;
	this.deliver_note = null;
	this.deliver_ar = null;
	this.deliver_man = null;
	this.deliver_province = null;
	this.deliver_pcity = null;
	this.deliver_city = null;
	this.deliver_address = null;
	this.deliver_zip = null;
	this.deliver_mobile = null;
	this.addDataRelation("shop_cart", "shop_cart");	
	this.addDataRelation("shop_cart_product", "shop_cart_product");	
}
shop_cart.prototype = new CPC.BizObject;
shop_cart.prototype.getClass = function() {
	return "shop_cart";
};
shop_cart.prototype.getNameProp = function() {
	return '';
};
shop_cart.prototype.getPKProp = function() {
	return 'item_id';
}; 
shop_cart.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_cart.prototype.getObjType = function() {
	return 16;
};
/**
 * 商品评价
 */
function shop_order_evaluation() {
	this.item_id = null;
	this.create_by = null;
	this.update_time = null;
	this.isuserful = null;
	this.score = null;  //商品质量评分
	this.grading_type1 = null;  //物流速度评分
	this.grading_type2 = null;  //发货速度评分
	this.grading_type3 = null;  //服务态度评分
	this.content = null;  //评价内容
	this.shop_order_id = null;
	this.left = null;//分页条件
	this.right = null;//分页条件
	this.pagesize = null;
	this.addDataRelation("shop_order_evaluation", "shop_order_evaluation");	//
}
shop_order_evaluation.prototype = new CPC.BizObject;
shop_order_evaluation.prototype.getClass = function() {
	return "shop_order_evaluation";
};
shop_order_evaluation.prototype.getNameProp = function() {
	return '';
};
shop_order_evaluation.prototype.getPKProp = function() {
	return 'order_eval_id';
}; 
shop_order_evaluation.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_order_evaluation.prototype.getObjType = function() {
	return 16;
};
/**
 * 个人资料
 */
function shop_member() {
	this.member_id = null;
	this.member_name = null;
	this.contactperson = null;
	this.contactperson_email = null;
	this.contactperson_mobile = null;
	this.addDataRelation("shop_member", "shop_member");	//
}
shop_member.prototype = new CPC.BizObject;
shop_member.prototype.getClass = function() {
	return "shop_member";
};
shop_member.prototype.getNameProp = function() {
	return '';
};
shop_member.prototype.getPKProp = function() {
	return 'member_id';
}; 
shop_member.prototype.getPropName = function(key) {
	if ("id" == key)
		return "member_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_member.prototype.getObjType = function() {
	return 16;
};

/**
 * 订单
 */
function shop_order() {
	this.superid = null;
	this.page = null;
	this.pagesize = null;
	this.addDataRelation("shop_order", "shop_order");
	this.addDataRelation("shop_order_line", "shop_order_line");
}
shop_order.prototype = new CPC.BizObject;
shop_order.prototype.getClass = function() {
	return "shop_order";
};
shop_order.prototype.getNameProp = function() {
	return '';
};
shop_order.prototype.getPKProp = function() {
	return 'order_id';
}; 
shop_order.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_order.getObjType = function() {
	return 16;
};


function shop_remit_money() {
	this.addDataRelation("shop_remit_money", "shop_remit_money");
}
shop_remit_money.prototype = new CPC.BizObject;
shop_remit_money.prototype.getClass = function() {
	return "shop_remit_money";
};
shop_remit_money.prototype.getNameProp = function() {
	return '';
};
shop_remit_money.prototype.getPKProp = function() {
	return 'remit_id';
}; 
shop_remit_money.prototype.getPropName = function(key) {
	if ("id" == key)
		return "remit_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_remit_money.prototype.getObjType = function() {
	return 1;
};


function shop_area() {
	this.area_type = null;
	this.area_id = null;
	this.superarea = null;
	this.addDataRelation("shop_area", "shop_area");
}
shop_area.prototype = new CPC.BizObject;
shop_area.prototype.getClass = function() {
	return "shop_area";
};
shop_area.prototype.getNameProp = function() {
	return '';
};
shop_area.prototype.getPKProp = function() {
	return 'area_id';
}; 
shop_area.prototype.getPropName = function(key) {
	if ("id" == key)
		return "area_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_area.prototype.getObjType = function() {
	return 1;
};

/**
 * 订单
 */
function shop_service() {
	this.page = null;
	this.pagesize = null;
	this.search_condition = null;
	this.addDataRelation("shop_service", "shop_service");
}
shop_service.prototype = new CPC.BizObject;
shop_service.prototype.getClass = function() {
	return "shop_service";
};
shop_service.prototype.getNameProp = function() {
	return '';
};
shop_service.prototype.getPKProp = function() {
	return 'service_id';
}; 
shop_service.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_service.getObjType = function() {
	return 1;
};

/**
 * 收藏夹
 */
function shop_collection() {
	//产品ID
	this.count;// count % 2 == 0 ：收藏 ？取消收藏； 
	this.item_id;
	this.member_name;
	this.addDataRelation("shop_collection", "shop_collection");
}
shop_collection.prototype = new CPC.BizObject;
shop_collection.prototype.getClass = function() {
	return "shop_collection";
};
shop_collection.prototype.getNameProp = function() {
	return '';
};
shop_collection.prototype.getPKProp = function() {
	return 'collection_id';
}; 
shop_collection.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_collection.getObjType = function() {
	return 1;
}; 


function shop_member_addr() {
	this.addr_id = null;
	this.addDataRelation("shop_member_addr", "shop_member_addr");
}
shop_member_addr.prototype = new CPC.BizObject;
shop_member_addr.prototype.getClass = function() {
	return "shop_member_addr";
};
shop_member_addr.prototype.getNameProp = function() {
	return '';
};
shop_member_addr.prototype.getPKProp = function() {
	return 'addr_id';
}; 
shop_member_addr.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_member_addr.getObjType = function() {
	return 1;
};


function shop_pay_type() {
	this.paymenttype = null;
	this.orderno = null;
	this.orderdesc = null;
	this.orderamount = null;
	this.orderurl = null;
	this.addDataRelation("shop_pay_type", "shop_pay_type");
}
shop_pay_type.prototype = new CPC.BizObject;
shop_pay_type.prototype.getClass = function() {
	return "shop_pay_type";
};
shop_pay_type.prototype.getNameProp = function() {
	return '';
};
shop_pay_type.prototype.getPKProp = function() {
	return 'pay_type_id';
}; 
shop_pay_type.prototype.getPropName = function(key) {
	if ("id" == key)
		return "item_id";
	else if ("code" == key)
		return "";
	else if ("name" == key)
		return "";
	else if ("type" == key)
		return "";
	else if ("subject" == key)
		return "";
	else
		return key;
};
shop_pay_type.getObjType = function() {
	return 1;
};
