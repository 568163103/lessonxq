$.fn.extend({
	JSONToForm : function(object,prefix) {
		for ( var property in object) {
			var value = object[property];
			if (value !== undefined){
				var self = null;
				if (prefix !== undefined){
					self = this.find('[name="'+prefix+'.'+property+'"]');
				} else {
					self = this.find('[name="'+property+'"]');
				}
				if (self == undefined || null == self || self.size() == 0){
					continue;
				}
				var t = self.attr("type");
				if("radio" == t || "checkbox" == t){
					self.attr("checked",false);
					self.filter("[value='" + value + "']").attr("checked",true);
				}else{
					self.val(value);
				}
			}
		}
	},
	enter : function(callback){
		this.keypress(function(event){
			if(event.keyCode==13)
				callback();
		});
	},
	getOffset : function(location,px){
		var element = this;
		var offset = element.offset();
		var top,left;
		if((!px)||px==0){
			px = 25;
		}
		//location 第一种情况 location等于left right这样的方位时候
		top = offset.top;
		left = offset.left;	
		if(!location){
			left = element.width() + left + px;	
		}else if(location =="right"){
			left = element.width() + left + px;	
		}else if(location == "left" ){
			left = left - px - element.width();
		}else if(location == "top"){
			top = top - px;	
		}else if(location =="bottom"){
			top = element.height()+top+px;
		}else if(location =="center"){
			  var bodyWidth = element.width();
			  var bodyHeight = element.height();
			  var offset = element.offset();
			  var top  = offset.top + bodyHeight / 2 - px;
			  var left = offset.left + bodyWidth / 2 - px * 2;
		}else {
			return ;
		}
		return {top:top,left:left};
	},
	loadhide : function(){
		if("undefined" != typeof(div_loading)){
			div_loading.dialog( "close" );
		}
		return this;
	},
	/**
	 * @param {String} msg loading图片旁边显示的信息
	 * @location {Object} location 显示的位置 参数可以是left right top bottom
	 * @px {int} 设置位置的偏移量
	 */
	loading : function(msg,location,url){
		if(!msg || msg==null || "" == msg){
			msg="loading";
		}
		if(!location){
			location = "center";
		}
		if(!url){
			url = "/images/loading_middle.gif";
		}
		if("undefined" == typeof(div_loading)){
			div_loading = $("<div id='loading_div' ><img src='" + context_path + url + "' style='vertical-align:text-bottom' />"+msg+"</div>");
			$(document.body).append(div_loading);
			div_loading.dialog({
				dialogClass: "ui-overlay",
				position: { my: location, at: location, of: this },
				modal: true
			});
		} else {
			div_loading.dialog( "option", { position: { my: location, at: location, of: this } } );
			div_loading.dialog( "open" );
		}
		return this;
	},
	/*----- 拉伸背景图片 -----*/
	resizeBg : function(){
		var imgLoad = function (url, callback) {
			var img = new Image();
			img.src = url;
			if (img.complete) {
				callback(img.width, img.height);
			} else {
				img.onload = function () {
					callback(img.width, img.height);
					img.onload = null;
				};
			}
		};
		var original = {
			width:$(window).width()+14,
			height:$(window).height()
		};
		return this.each(function(i,imageEl){
			var image = $(imageEl);
			imgLoad(image.attr('src'),function(){
				var img = {
					width:image.width(),
					height:image.height()
				};		
				percentage=original.width/img.width;
				if(img.height*percentage<original.height)
					percentage=original.height/img.height;
				percentage=percentage>1?percentage:1;				
				image.width(img.w=img.width*percentage).height(img.h=img.height*percentage);
				$(window).resize(function(){
					image.parent().css({width:"100%",height:"100%",position:"absolute",overflow:"hidden"});
					var w = $(this).width()+14;
					var h = $(this).height()-230;

					percentage=w/img.width;
					if(img.height*percentage<h)
					percentage=h/img.height;
					percentage=percentage>1?percentage:1;	
					var newWidth = img.width*percentage;
					var newHeight = img.height*percentage;
					if(percentage!=1){
						image.width(newWidth).height(newHeight);
					}
					image.css({"top":(h-newHeight)/2+230,"left":(w-newWidth)/2,"position":"absolute"});
				}).trigger("resize");
			});
		});
	},
	bindAllCheckBox : function(){
		this.each(function(i){
			var checkBoxAll = $(this);
			var name = checkBoxAll.attr("name").split("All")[0];
			var checkBoxs = $("[name='" + name + "']");
			var checkBoxValues = "";
			if($("[name='" + name + "Values']").val()){
				checkBoxValues = $("[name='" + name + "Values']").val().split(",");
			}
			var checkBoxsAllSelected = true;
			//绑定全选 事件
	    	checkBoxAll.click(function(){
	    		if(this.checked){				 //如果当前点击的多选框被选中
	    			checkBoxs.attr("checked", true );
	    		}else{								
	    			checkBoxs.attr("checked", false );
	    		}
	    	});
	    	checkBoxs.click(function(){
	    		var flag=true;
	    		checkBoxs.each(function(){
	    			if(!this.checked){
	    				flag = false;
	    			}
	    		});
	    		if( flag ){
	    			checkBoxAll.attr("checked", true );
	    		}else{
	    			checkBoxAll.attr("checked", false );
	    		}
	    	}).each(function(i){
	    		if($.inArray(this.value,checkBoxValues)>=0){
	    			$(this).attr("checked", true );
	    		} else {
	    			checkBoxsAllSelected = false;
	    		}
	    	});
	    	if(checkBoxsAllSelected){
	    		checkBoxAll.attr("checked", true );
	    	}
		});
	},
	tableCheckBox : function(){
		this.each(function(i){
			var parentEl = $(this);
			parentEl.find("[class=CheckedAll]").click(function(){
        		if(this.checked){				 //如果当前点击的多选框被选中
        			parentEl.find("[class=items]").attr("checked", true );
        			parentEl.find("[class=CheckedAll]").attr("checked", true );
        		}else{								
        			parentEl.find("[class=items]").attr("checked", false );
        			parentEl.find("[class=CheckedAll]").attr("checked", false );
        		}
        	});
        	parentEl.find("[class=items]").click(function(){
        		var flag=true;
        		parentEl.find("[class=items]").each(function(){
        			if(!this.checked){
        				flag = false;return false;
        			}
        		});
        		if( flag ){
        			parentEl.find("[class=CheckedAll]").attr("checked", true );
        		}else{
        			parentEl.find("[class=CheckedAll]").attr("checked", false );
        		}
        	});
		});
	},
	autoPos : function(){
		var elm = this;
		var startPos = $(elm).offset().top;
		$.event.add(window, "scroll", function () {
			var p = $(window).scrollTop();
			$(elm).css('position', ((p) > startPos) ? 'fixed' : 'static');
			$(elm).css('top', ((p) > startPos) ? '0px' : '');
		});
	}
});
$(function(){
	$(".win-header-div").click(function(){
		if ($(this).attr('name') == "open") {
			$(".win-advsearch").slideUp();
			$(this).attr('name', 'close');
			$(this).text("展开窗口");
			$(this).addClass("win-header-div-down");
		} else {
			$(".win-advsearch").slideDown();
			$(this).attr('name', 'open');
			$(this).text("收起窗口");
			$(this).removeClass("win-header-div-down");
		}
	});
	$(".more-win-header-div").click(function(){
		if ($(this).attr('name') == "open") {
			$("#" + $(this).attr('id') + "-div").slideUp();
			$(this).attr('name', 'close');
			$(this).text("展开窗口");
			$(this).addClass("more-win-header-div-down");
		} else {
			$("#" + $(this).attr('id') + "-div").slideDown();
			$(this).attr('name', 'open');
			$(this).text("收起窗口");
			$(this).removeClass("more-win-header-div-down");
		}
	});
	Date.prototype.format = function(format) {  
		var o = {
			"M+" : this.getMonth() + 1, //month  
			"d+" : this.getDate(), //day  
			"h+" : this.getHours(), //hour  
			"m+" : this.getMinutes(), //minute  
			"s+" : this.getSeconds(), //second  
			"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter  
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		return format;
	};
	$(".a_button").button();
});