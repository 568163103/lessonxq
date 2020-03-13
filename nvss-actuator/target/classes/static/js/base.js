(function(window, $) {
    $.extend(jQuery.easing, {
        easeInOutBack: function(x, t, b, c, d, s) {
            if (s == undefined) s = 1.70158;
            if ((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
            return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
        },
        easeOutElastic: function(x, t, b, c, d) {
            var s = 1.70158;
            var p = 0;
            var a = c;
            if (t == 0) return b;
            if ((t /= d) == 1) return b + c;
            if (!p) p = d * .3;
            if (a < Math.abs(c)) {
                a = c;
                var s = p / 4;
            } else var s = p / (2 * Math.PI) * Math.asin(c / a);
            return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
        },
        easeOutBack: function(x, t, b, c, d, s) {
            if (s == undefined) s = 1.70158;
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
    });
    var ns = window.ns || {};
    var isIE7 = ns.isIE7 = !!navigator.userAgent.match(/MSIE 7.0/);
    var isIE8 = ns.isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
    var isIE9 = ns.isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
    var isIELow = isIE7 || isIE8 || isIE9 ? true : false;
    var isIE10 = ns.isIE10 = !!navigator.userAgent.match(/MSIE 10.0/);
    var $window = $(window);
    var initSlimScroll = ns.initSlimScroll = function(el) {
        var el = el || ".slimScrollDiv";
        $(el).each(function() {
            var $t = $(this);
            if ($t.attr("data-initialized")) {
                return; // exit
            }

            var height;

            if ($t.attr("data-height")) {
                height = $t.attr("data-height");
            } else {
                height = $t.css('height');
            }

            $t.slimScroll({
                allowPageScroll: true,
                size: '7px',
                color: ($t.attr("data-handle-color") ? $t.attr("data-handle-color") : '#bbb'),
                wrapperClass: ($t.attr("data-wrapper-class") ? $t.attr("data-wrapper-class") : 'slimScrollDiv'),
                railColor: ($t.attr("data-rail-color") ? $t.attr("data-rail-color") : '#eaeaea'),
                position: 'right',
                height: height,
                alwaysVisible: ($t.attr("data-always-visible") == "1" ? true : false),
                railVisible: ($t.attr("data-rail-visible") == "1" ? true : false),
                disableFadeOut: true
            });
            $t.attr("data-initialized", "1");
        });
    };
    var initSideBar = ns.initSideBar = function(opts) {
        var defaults = {
                el: ".page-sidebar",
                top: 66,
                bottom: 0,
                time: 250,
                ctrl: ".page-sidebar-ctrl",
                container: ".page-container",
                closed: "page-sidebar-closed",
                sub: ".sub-menu",
                iframe: "#page-iframe"
            },
            opts = $.extend({}, defaults, opts);
        var $el = $(opts.el);
            hasInit = $el.attr("data-hasInit");
        if ( hasInit ){
            return false;
        }
        $el.attr("data-hasInit",1);
        var title = ">ul>li",
            $container = $el.parents(opts.container),
            $ctrl = $container.find(opts.ctrl),
            $iframe = $(opts.iframe),
            isClosed = +ns.cookie.get("__sideIsClosed__") || 0;

        $el.on("click", title, function() {
            if (!$container.hasClass(opts.closed)) {
                var $t = $(this),
                    $sub = $t.find(opts.sub);
                $sub.stop(false, true).slideToggle(opts.time, function() {
                    $t.toggleClass("open");
                    if ( $t.hasClass("open") ){
                        //$el.slimScroll({ scrollTo: $t.offset().top });
                    }
                }).toggleClass("open");
            }
        }).on("mouseenter", title, function() {
            if ($container.hasClass(opts.closed)) {
                $(this).addClass("hover");
            }
        }).on("mouseleave", title, function() {
            if ($container.hasClass(opts.closed)) {
                $(this).removeClass("hover");
            }
        }).on("click", "ul a", function(e) {
            var $t = $(this),
                href = $t.attr("href");
            if (!/^javascript:/.test(href)) {
                $el.find(opts.sub).find("li").removeClass("current");
                $t.parent().addClass("current").siblings().removeClass("current");
                $iframe.attr("src", href);
                e.preventDefault();
                e.stopPropagation();
            }
        });
        $ctrl.on("click", function() {
            ns.cookie.set("__sideIsClosed__", +!isClosed, 7);
            $container.toggleClass(opts.closed);
        });
        if (isIE7 || isClosed) {
            $container.addClass(opts.closed);
        }
        var lastH = $window.height(),
            before = 0,
            inited = false;

        function resize() {
            var now = +new Date;
            if (now - before < 50) {
                before = now;
                return false;
            }
            var H = $window.height();
            if (lastH == H && inited) {
                return false;
            }
            inited = true;
            lastH = H;
            var h = H - opts.top - opts.bottom;
            if (isIE7) {
                $el.height(h);
            } else {
                $el.slimScroll({
                    height: h
                });
            }
        }
        resize();
        $window.on("resize", resize);
    };
    var initLrPanel = ns.initLrPanel = function(el, ctrl, cls) {
        var el = el || ".page-lr-panel";
        var ctrl = ctrl || ".lr-panel-ctrl";
        var cls = cls || "page-lr-panel-closed";
        var $el = $(el);
        $el.each(function() {
            var $t = $(this);
            if ($t.attr("data-inited") === "true") {
                return false;
            }
            $t.find(ctrl).on("click", function() {
                $t.toggleClass(cls);
            });
        });
    };
    var initTable = ns.initTable = function(el, box, checkbox) {
        var el = el || 'input.table-input-selectAll';
        var box = box || '.table';
        var checkbox = checkbox || '.table-input-checkbox';
        var $box = $(box);
        $box.each(function(){
            var $t = $(this),
                hasInit = $t.attr("data-hasInit"),
                $el = $t.find(el);
            if ( hasInit ){
                return false;
            }
            $t.attr("data-hasInit",1);
            $t.on("click", el, function() {
                var selected = $el.attr("data-selected");
                var toSelect = $el.prop("checked");
                $t.find(checkbox).each(function() {
                    var $t = $(this);
                    $t.prop("checked", toSelect);
                });
            });
        });
    };
    var initIframe = ns.initIframe = function(opts) {
        var defaults = {
                el: "#page-iframe",
                top: 66,
                bottom: 0
            },
            opts = $.extend({}, defaults, opts);
        var $el = $(opts.el);

        function resize() {
            $el.height($window.height() - opts.top - opts.bottom);
        }
        resize();
        $window.on("resize", resize);
    };
    var initSelect = ns.initSelect = function(opts) {
        if (!$.fn.select2) {
            return false;
        }
        var defaults = {
                minimumResultsForSearch: -1,
                ex : ".noSelect2"
            },
            opts = $.extend({}, defaults, opts);
        var $el = $("select").not(opts.ex);
        return $el.select2(opts);
    };
    var initDialog = ns.initDialog = function(){
        function getTop(_win){
            if ( _win.top === _win ){
                return _win;
            }else{
                return getTop(_win.top);
            }
        }
        var top = getTop(window),
            iframe = top.jQuery("#page-iframe")[0];
        $.dialog = top.jQuery.dialog;
        if ( !$.dialog || !top.jQuery.dialog ){
            return false;
        }
        top.jQuery.dialog._win = top;
        top.jQuery.dialog._iframe = iframe ? iframe.contentWindow : "";
        top.jQuery.dialog.close = function(){
            $.each(top.jQuery.dialog.list,function(i,v){
                v.close();
            })
        };
    };
    var initGoback = ns.initGoback = function(classname,ex){
        var classname = classname || ".ns-goback";
        var ex = ex || ".ns-disabled";
        $(classname).not(ex).on("click",function(){
            window.history.go(-1);
        });
    };
    ns.open = function(el,opts,width,height,confirm,cancel,okVal,cancelVal){
        if ( typeof el === "object" ){
            var $el = $(el),
                href = $el.attr("href"),
                title = $el.attr("title");
        }else{
            var href = el || opts.href,
                title = opts || opts.title;
        }
        var data = {};
        if ( typeof opts === "object" ){
            data = opts;
            data.title = data.title || title;
        }else{
            data = {
                title : title,
                width : width || 1000,
                height : height || 600,
                ok : confirm,
                cancel : cancel,
                okVal: okVal,
                cancelVal: cancelVal
            }
        }
        $.dialog.open(data.href || href,data);
        return false;
    }
    ns.formatDate = function(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd HH:mm:ss";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/g, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
            return dict[arguments[0]];
        });
    };
    ns.cookie = {
        set: function(name, value, days) {
            var expires = "";
            if (days) {
                var d = new Date();
                d.setTime(d.getTime() + days * 24 * 60 * 60 * 1000);
                expires = "; expires=" + d.toGMTString();
            }
            document.cookie = name + "=" + value + expires + "; path=/";
        },
        get: function(name) {
            var re = new RegExp("(\;|^)[^;]*(" + name + ")\=([^;]*)(;|$)");
            var res = re.exec(document.cookie);
            return res != null ? res[3] : null;
        },
        del: function(name) {
            nostar.cookie.set(name, '', -1);
        }
    };
    ns.tip = function(con,ok,cancel,time,title){
        var title = title || "温馨提示",
            time = time || 100,
            con = con || "没有内容！",
            ok = ok === undefined ? false : ok,
            cancel = cancel === undefined ? true : cancel;
        return $.dialog({
            title : title,
            content : con,
            ok : ok,
            cancel : cancel,
            time : time
        });
    }
    ns.ajaxLock = false;
    var init = ns.init = function() {
        if (!isIE7) {
            ns.initSlimScroll();
        }
        window.console = window.console ? window.console : {
            log : window.alert,
            error : window.alert,
            info : window.alert
        }
        ns.initSideBar();
        ns.initIframe();
    };
    if ( window.template ){
        ns.tpl = ns.template = window.template;
    }
    $(function() {
        ns.initTable();
        //ns.initSelect();
        ns.initDialog();
        //ns.initTextarea();
        ns.initGoback();
    });
    window.NS = ns;
})(window, jQuery)