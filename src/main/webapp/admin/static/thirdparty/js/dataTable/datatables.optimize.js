/**
 * datatables优化
 */
$.extend($.fn.dataTable.defaults, {
	dom: 't<"dataTables_info"il>p',
	language: {
		"url": "/admin/static/thirdparty/js/dataTable/datatables_language.json"
	},
	processing: true, //当datatable获取数据时候是否显示正在处理提示信息。
	serverSide: true, //服务器处理分页
	responsive: {
		details: false
	},
	initComplete: function(settings) {
		var _$this = this;

		/**
		 * 重写搜索事件
		 */
		$('#doSearch').bind('click', function(e) {
			_$this.api().ajax.reload();
		});
		$('#search').bind('keyup', function(e) {
			if(e.keyCode == 13 || (e.keyCode == 8 && (this.value.length == 0))) {
				_$this.api().ajax.reload();
			}
		});
	},
	drawCallback: drawCallbackDefault
});
/**
 * 本页刷新
 */
function reloadTable() {
	datatable.ajax.reload(null, false);
}
/**
 * 首页刷新
 */
function query(){
	datatable.ajax.reload(null, true);
}
/**
 * DT绘制完成默认回调函数
 * 单独写出来是方便二次定制
 * 
 * 默认回调函数功能：
 * 1.DT第一列checkbox初始化成icheck
 * 2.iCheck全选、取消多选、多选与单选双向关联
 * 3.选中的tr加上selected class
 * 
 * @param {Object} settings
 */
function drawCallbackDefault(settings, _$this) {
	//console.log("drawCallbackDefault");
	_$this = (isExitsVariable('_$this') && _$this) ? _$this : this;
	selector = _$this.selector;
	$(selector + ' input').iCheck({
		checkboxClass: 'icheckbox_minimal',
		increaseArea: '20%'
	});

	/**
	 * DT thead iCheck 点击事件
	 */
	$(selector + ' input[name=all]').on('ifChecked ifUnchecked', function(e) {
		$(this).closest('table').find('input[name=single]').each(function() {
			if(e.type == 'ifChecked') {
				$(this).iCheck('check');
				$(this).closest('tr').addClass('selected');
			} else {
				$(this).iCheck('uncheck');
				$(this).closest('tr').removeClass('selected');
			}
		});
	});

	/**
	 * DT tbody iCheck点击事件
	 */
	$(selector + ' input[name=single]').on('ifChecked ifUnchecked', function(e) {
		if(e.type == 'ifChecked') {
			$(this).iCheck('check');
			$(this).closest('tr').addClass('selected');
			//全选单选框的状态处理
			var selected = _$this.api().rows('.selected').data().length; //被选中的行数
			var recordsDisplay = _$this.api().page.info().recordsDisplay; //搜索条件过滤后的总行数
			var iDisplayStart = _$this.api().page.info().start; // 起始行数
			if(selected === _$this.api().page.len() || selected === recordsDisplay || selected === (recordsDisplay - iDisplayStart)) {
				$(selector + ' input[name=all]').iCheck('check');
			}
		} else {
			$(this).iCheck('uncheck');
			$(this).closest('tr').removeClass('selected');
			$(selector + ' input[name=all]').attr('checked', false);
			$(selector + ' input[name=all]').iCheck('update');
		}
	});

	/**
	 * 检测参数是否定义
	 * @param {Object} variableName
	 */
	function isExitsVariable(variableName) {
		try {
			if(typeof(variableName) == "undefined") {
				return false;
			} else {
				return true;
			}
		} catch(e) {}
		return false;
	}
}

/** 
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)可以用 1-2 个占位符 
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 *eg: 
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */
Date.prototype.pattern = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份         
		"d+": this.getDate(), //日         
		"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时         
		"H+": this.getHours(), //小时         
		"m+": this.getMinutes(), //分         
		"s+": this.getSeconds(), //秒         
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度         
		"S": this.getMilliseconds() //毫秒         
	};
	var week = {
		"0": "/u65e5",
		"1": "/u4e00",
		"2": "/u4e8c",
		"3": "/u4e09",
		"4": "/u56db",
		"5": "/u4e94",
		"6": "/u516d"
	};
	if(/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if(/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

// js数组拓展方法
Array.prototype.indexOf = function(val) {
	for(var i = 0; i < this.length; i++) {
		if(this[i] == val)
			return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if(index > -1) {
		this.splice(index, 1);
	}
};

/**
 * JS Map插件
 */
function HashMap() {
	// 私有变量
	var arr = {};
	// 增加
	this.put = function(key, value) {
			arr[key] = value;
		}
		// 查询
	this.get = function(key) {
			if(arr[key]) {
				return arr[key]
			} else {
				return null;
			}
		}
		// 删除
	this.remove = function(key) {
			// delete 是javascript中关键字 作用是删除类中的一些属性
			delete arr[key]
		}
		// 遍历
	this.eachMap = function(fn) {
			for(var key in arr) {
				fn(key, arr[key])
			}
		}
		//长度
	this.size = function() {
			var len = 0;
			for(var key in arr) {
				len++;
			}
			return len;
		}
		//key数组
	this.getKeyArray = function() {
			var keys = new Array();
			for(var key in arr) {
				keys.push(key);
			}
			return keys;
		}
		//Value数组
	this.getValueArray = function() {
			var values = new Array();
			for(var key in arr) {
				values.push(this.get(key));
			}
			return values;
		}
		//是否存在某个元素(值)
	this.isExitValue = function(value) {
			for(var key in arr) {
				if(this.get(key) == value) {
					return true;
				}
			}
			return false;
		}
		//是否存在某个元素(键)
	this.isExitKey = function(param) {
		for(var key in arr) {
			if(key == param) {
				return true;
			}
		}
		return false;
	}
};

/**
 * 
 * @param {Object} btns 操作按钮数组
 */
function getDTOperateBtn(btns) {
	var html = '';
	var i = 0;
	while(i < btns.length) {
		var btn = btns[i];
		html += '&nbsp;<a title="'+btn.title+'" href="javascript:'+btn.click+';" style="text-decoration:none"><i class="Hui-iconfont">'+btn.icon+'</i></a>&nbsp;';
		i ++;
	}
	return html;
}
/**
 * 创建按钮对象
 * @param name
 * @param text
 * @param value
 * @returns {___anonymous7800_7802}
 */
function butInfo(icon,title,click){
	var obj = new Object();
	obj.icon = icon;
	obj.title = title;
	obj.click =click;
	//可以继续扩展别的属性
	return obj;
}