$(function() {
	function pageCallback (loadObject){
		var currDiv = loadObject.obj;
		var beforeNum = currDiv.find("[class=pageObject\\.currentPageNum]").val();
		if(!beforeNum){
			return;
		}
		var pageclickednumber = 1 + 1*beforeNum;
		if(pageclickednumber > currDiv.find("[class=pageObject\\.pageNum]").val()){
			return;
		}
		currDiv.find("[class=pageObject\\.currentPageNum]").val(pageclickednumber);
		currDiv.find("[class=pageObject\\.pageClick]").val(1);
		currDiv.find("[class=randomNum]").val(Math.random());
		currDiv.loading("加载中，请稍后","bottom");
		$.ajax({
			type : "POST",
			url : currDiv.parents("form").attr("action"),
			data : currDiv.parents("form").serializeArray(),
			dataType : "html",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(dataResult){
				var data = $(dataResult);
				var currDivReturn = data.filter("#"+currDiv.attr("id"));
				if(currDivReturn.html() == null){
					currDivReturn = data.find("#"+currDiv.attr("id"));
				}
	     		if(currDivReturn.html()!=null){
					currDiv.find("tbody").append(currDivReturn.find("tbody tr"));
					currDiv.divInitSet();
				} else {
					if(data.find("[id=j_username]").html()!=null){
						window.location = (context_path == ""?"/":context_path);
					} else {
						currDiv.find("[class=pageObject\\.currentPageNum]").val(beforeNum);
						alert("加载数据失败，请稍后重试。");
					}
				}
	     		currDiv.loadhide();
				currDiv.find(".a_button").button();
	     		if(typeof(_successTableCallBack) == "function"){
					_successTableCallBack(currDiv.attr("id"));
				}
	    		currDiv.find("[class=pageObject\\.pageClick]").val(0);
	     		loadObject.status = true;
	        },
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				currDiv.loadhide();
				currDiv.find("[class=pageObject\\.currentPageNum]").val(beforeNum);
				alert("加载数据失败，请稍后重试！");
	    		currDiv.find("[class=pageObject\\.pageClick]").val(0);
	     		loadObject.status = true;
			}
		});
	}
	$.fn.divInitSet = function (){
    	this.each(function (){
    		var currDiv = $(this);var currTable = currDiv.find("table").addClass("tableCss");
    		var sortName = currDiv.find("[class=pageObject\\.sortName]");
    		var sortOrder = currDiv.find("[class=pageObject\\.sortOrder]");
    		// 隔行改变背景色、表头事件、选中事件绑定
    		var cols = -1;
    		currTable.find("thead th").each(function(index){
    			if(this.abbr !=""){
	        		var orderCss = "asc";
        			if(this.abbr == sortName.val()){
        				orderCss = (sortOrder.val()=="asc"?"desc":"asc");
	        			$(this).addClass('sorted').find("div").addClass(sortOrder.val());
	        			cols = index;
        			}
	        		$(this).mouseover(function() {
	        			if(this.abbr == sortName.val()){
	        				$(this).addClass('hover').find("div").removeClass(sortOrder.val());
	        			}
	        			$(this).addClass('hover').find("div").addClass(orderCss);
	        		});
	        		$(this).mouseout(function() {
	        			$(this).removeClass('hover').find("div").removeClass(orderCss);
	        			if(this.abbr == sortName.val()){
	        				$(this).removeClass('hover').find("div").addClass(sortOrder.val());
	        			}
	        		});
    			}
        	});
    		currTable.find("tbody tr").each(function(index){
        		$(this).addClass((index % 2 == 1 ? "alt" :""));
        		$(this).children("td").each(function (col){
        			if(col==cols && index % 2 == 1)
        				$(this).addClass('sorted');
        		});
        		$(this).mouseover(function() {$(this).addClass("over");});
        		$(this).mouseout(function() {$(this).removeClass("over");});
        	});
    		
    		//绑定全选 事件
        	currDiv.tableCheckBox();
    	});
    	return this;
	};
	$.fn.scrollLoading = function(options) {
		var defaults = {
			attr: "data-url",
			container: $(window),
			callback: pageCallback
		};
		var params = $.extend({}, defaults, options || {});
		params.cache = [];
		$(this).each(function() {
			var node = this.nodeName.toLowerCase(), url = $(this).attr(params["attr"]);
			//重组
			var data = {
				obj: $(this),
				tag: node,
				url: url,
				status:true
			};
			params.cache.push(data);
		});
		
		var callback = function(loadObject) {
			if ($.isFunction(params.callback)) {
				params.callback(loadObject);
			}
		};
		//动态显示数据
		$(window).scrollTop(0);
		var loading = function() {
			var scrt = $(window).scrollTop(), scrb = scrt + $(window).height();
            $.each(params.cache, function(i, data) {
                var div = data.obj, tag = data.tag, url = data.url,status = data.status;
                if (div && status) {
                    var divt = div.position().top; var divb = divt + div.height() - 100;
                    if (divb > scrt && divb <= scrb) {
                    	data.status = false;
						if (url) {
							//在浏览器窗口内
							if (tag === "img") {
								//图片，改变src
								callback(div.attr("src", url));		
							}
						} else {
							// 无地址，直接触发回调
							callback(data);
						}
					}
				}
			});	
		};
		//滚动执行
		params.container.bind("scroll", loading);
	};
    $("div[name=pageDiv]").divInitSet().scrollLoading();
});