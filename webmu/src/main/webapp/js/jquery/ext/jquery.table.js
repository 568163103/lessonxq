/*
* jQuery pager plugin
* Version 1.0 (12/22/2008)
* @requires jQuery v1.2.6 or later
*
* Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
*
* Copyright (c) 2008-2009 Jon Paul Davies
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
* 
* Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
*
* This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
*
* Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickDiv: PagerClickTest });
*
* Where pagenumber is the visible page number
*       pagecount is the total number of pages to display
*       buttonClickDiv is the method to fire when a pager button is clicked.
*
* buttonClickDiv signiture is PagerClickTest = function(pageclickednumber) 
* Where pageclickednumber is the number of the page clicked in the control.
*
* The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
* Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
*/
$(function() {
    $.fn.pager = function(options) {
        var opts = $.extend({}, $.fn.pager.defaults, options);
        return this.each(function() {
        	// empty out the destination element and then render out the pager with the supplied options
            $(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount),parseInt(options.totalnumber), options.buttonClickDiv));
            // specify correct cursor activity
            $('.pages li').mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
        });
    };

    // render and return the pager with the supplied options
    function renderpager(pagenumber, pagecount,totalnumber, buttonClickDiv) {
    	var pagenum=$("<li class='pgEmpty'><span style='vertical-align:middle'>共"+totalnumber+"条 / "+pagecount+"页记录</span></li>");
    	// setup $pager to hold render
        var $pager = $('<ul class="pages" id="' + buttonClickDiv.attr("id") + '_pages"></ul>');
        var pagesize=$("<li class='pgNum'>每页条数：<select class='pageObject.pageSize' name='"+buttonClickDiv.find("[class=objectName]").val()+".pageSize' /></li>");
    	pagesize.appendTo($pager).find("select").append("<option value='18'>18</option>").append("<option value='20'>20</option>")
    		.append("<option value='50'>50</option>").append("<option value='80'>80</option>").append("<option value='100'>100</option>")
    		.append("<option value='200'>200</option>").val(buttonClickDiv.find("[class=pageObject\\.pageSizeTemp]").val())
    		.change(function (){
	    		buttonClickDiv.pageClickSubmit();
	    	});
    	$pager.append(pagenum).append(renderButton('首页', pagenumber, pagecount, buttonClickDiv))
        	.append(renderButton('上一页', pagenumber, pagecount, buttonClickDiv));
        // pager currently only handles 10 viewable pages ( could be easily parameterized, maybe in next version ) so handle edge cases
        var startPoint = 1;
        var endPoint = 9;
        if (pagenumber > 4) {
            startPoint = pagenumber - 4;
            endPoint = pagenumber + 4;
        }
        if (endPoint > pagecount) {
            startPoint = pagecount - 8;
            endPoint = pagecount;
        }
        if (startPoint < 1) {
            startPoint = 1;
        }
        // loop thru visible pages and render buttons
        for (var page = startPoint; page <= endPoint; page++) {
            var currentButton = $('<li class="page-number">' + (page) + '</li>');
            page == pagenumber ? currentButton.addClass('pgCurrent') : currentButton.click(function() { buttonClickDiv.pageClickSubmit(this.firstChild.data); });
            currentButton.appendTo($pager);
        }
        // render in the next and last buttons before returning the whole rendered control back.
        $pager.append(renderButton('下一页', pagenumber, pagecount, buttonClickDiv)).append(renderButton('尾页', pagenumber, pagecount, buttonClickDiv));
        return $pager;
    }

    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(buttonLabel, pagenumber, pagecount, buttonClickDiv) {
        var $Button = $("<li class='pgNext'>" + buttonLabel + "</li>");
        var destPage = 1;
        // work out destination page for required button type
        switch (buttonLabel) {
            case "首页":
                destPage = 1;
                break;
            case "上一页":
                destPage = pagenumber - 1;
                break;
            case "下一页":
                destPage = pagenumber + 1;
                break;
            case "尾页":
                destPage = pagecount;
                break;
        }
        // disable and 'grey' out buttons if not needed.
        if (buttonLabel == "首页" || buttonLabel == "上一页") {
            pagenumber <= 1 ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickDiv.pageClickSubmit(destPage); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickDiv.pageClickSubmit(destPage); });
        }
        return $Button;
    }
    
    // pager defaults. hardly worth bothering with in this case but used as placeholder for expansion in the next version
    $.fn.pager.defaults = {
        pagenumber: 1,
        pagecount: 1
    };
    
    $.fn.pageClickSubmit = function (pageclickednumber){
    	var currDiv = this;
    	currDiv.find("table").loading("加载中，请稍后");
    	this.pageClickSubmitSeltIcon(pageclickednumber,false);
    };
    
    $.fn.refreshDivAJAX = function (){
    	var currDiv = this;
    	currDiv.find("table").loading("加载中，请稍后");
    	this.pageClickSubmitSeltIcon(0,true);
    };
    
    $.fn.pageClickSubmitSeltIcon = function (pageclickednumber,refreshCount){
    	var currDiv = this;
    	var beforeNum = currDiv.find("[class=pageObject\\.currentPageNum]").val();
    	if(pageclickednumber){
    		currDiv.find("[class=pageObject\\.currentPageNum]").val(pageclickednumber);
    	}
    	if(!refreshCount)
    		currDiv.find("[class=pageObject\\.pageClick]").val(1);
    	currDiv.find("[class=randomNum]").val(Math.random());
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
    				currDiv.replaceWith(currDivReturn);
    				currDiv = currDivReturn;
    				currDiv.divInitSet();
    			} else {
					if(data.find("[id=j_username]").size() > 0){
						window.location = (context_path == ""?"/":context_path);
					} else {
						currDiv.find("[class=pageObject\\.currentPageNum]").val(beforeNum);
						currDiv.find("[class=pageObject\\.pageClick]").val(0);
						alert("加载数据失败，请稍后重试。");
					}
    			}
         		currDiv.find("table").loadhide();
				currDiv.find(".a_button").button();
         		if(typeof(_successTableCallBack) == "function"){
    				_successTableCallBack(currDiv.attr("id"));
    			}
            },
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			currDiv.find("[class=pageObject\\.currentPageNum]").val(beforeNum);
    			currDiv.find("[class=pageObject\\.pageClick]").val(0);
    			currDiv.find("table").loadhide();
    			alert("加载数据失败，请稍后重试！");
    		}
    	});
    };

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
	        		$(this).click(function (){
	        			sortName.val(this.abbr);
	        			sortOrder.val(orderCss);
	        			currDiv.pageClickSubmit();
	        		});
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
        	
        	//初始化分页
        	currDiv.find("[class=pager_c]").pager({
    			pagenumber: currDiv.find("[class=pageObject\\.currentPageNum]").val(), 
    			pagecount: currDiv.find("[class=pageObject\\.pageNum]").val(),
    			totalnumber:currDiv.find("[class=pageObject\\.totalNum]").val(), 
    			buttonClickDiv: currDiv
    		});
    	});
	};
    $("div[name=pageDiv]").divInitSet();
});