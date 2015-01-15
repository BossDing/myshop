<html>
    <head>
        <meta http-equiv="content-type" content="charset=utf-8" />
        <link rel="stylesheet" href="${base}/resources/mobile/css/footer.css" />
        <script type="text/javascript">
            $().ready(function() {
                queryInfo();
            });
            function queryInfo() {
                $.ajax({
                    url: "${base}/mobile/cart/queryCartCount.jhtml",
                    type: "POST",
                    dataType: "json",
                    cache: false,
                    success: function(data) {
                        if (data.message.type == "success") {
                            $("#cartcount").text("");
                            $("#cartcount").append(data.quantity);
                        }
                    },
                    complete: function() {}
                });
            }
        </script>
    </head>
    
    <body>
        <div class="nav-index1 card">
            <ul>
                <li>
                    <a href="${base}/mobile/index.jhtml">
                        <span class="ico ico-qb">
                        </span>
                        <span class="t">
                            <span>
                                万家乐首页
                            </span>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="${base}/mobile/product_category/index.jhtml">
                        <span class="ico ico-ss">
                        </span>
                        <span class="t">
                            <span>
                                分类
                            </span>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="${base}/mobile/cart/list.jhtml">
                        <span class="ico ico-th">
                        </span>
                        <span class="t">
                            <span>
                                购物车
                            </span>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="${base}/mobile/member/index.jhtml">
                        <span class="ico ico-pc">
                        </span>
                        <span class="t">
                            <span>
                                我
                            </span>
                        </span>
                    </a>
                </li>
            </ul>
            <div class="nav-index2 card">
                <a href="${base}/mobile/cart/list.jhtml">
                    <span class="ico ico-qb">
                    </span>
                    <span class="t" id="cartcount">
                        0
                    </span>
                </a>
            </div>
        </div>
    </body>

</html>