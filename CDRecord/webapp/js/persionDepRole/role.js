Ext.BLANK_IMAGE_URL = content_path
		+ '/R_framework/ext-3_0_0/resources/images/default/s.gif';


/**
 * js实现的map功能
 * @returns
 */


function Map() {
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();
	
	/**
	 * 放入一个键值对
	 * @param {String} key
	 * @param {Object} value
	 */
	this.put = function(key, value) {
		if(this.data[key] == null){
			this.keys.push(key);
		}
		this.data[key] = value;
	};
	
	/**
	 * 获取某键对应的值
	 * @param {String} key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};
	
	/**
	 * 删除一个键值对
	 * @param {String} key
	 */
	//this.remove = function(key) {
	//	this.keys.remove(key);
	//	this.data[key] = null;
	//};
	
	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function} 回调函数 function(key,value,index){..}
	 */
	this.each = function(fn){
		if(typeof fn != 'function'){
			return;
		}
		var len = this.keys.length;
		for(var i=0;i<len;i++){
			var k = this.keys[i];
			fn(k,this.data[k],i);
		}
	};
	
	/**
	 * 获取键值数组(类似Java的entrySet())
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};
	
	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};
	
	/**
	 * 获取键值对数量
	 */
	this.size = function(){
		return this.keys.length;
	};
	
	/**
	 * 重写toString 
	 */
	this.toString = function(){
		var s = "{";
		for(var i=0;i<this.keys.length;i++,s+=','){
			var k = this.keys[i];
			s += k+"="+this.data[k];
		}
		s+="}";
		return s;
	};
	this.initMap = function(map){
		 Ext.Ajax.request({
				url : '/menu/menu_getAllMenu.do',
				callback : function(options, success,
						response) {//回调函数
					var msg = [ "请求是否成功：", success,
							"\n", "服务器返回值：",
							response.responseText,
							"本地自定义属性：",
							options.customer ];
					var keyAndValues = response.responseText.split('|');
					var menulength = keyAndValues.length;
					for(var i=0;i<menulength;i++){
						var keyAndValue = keyAndValues[i].split('=');
						map.put(keyAndValue[0],keyAndValue[1]);
					}
				}
			});//发送请求
	 }
}




//list保存
var updateurl = content_path + '/role2/Role_saveOrUpdate.action';
var deleteurl = content_path + '/role2/Role_delete.action';
//list数据url
var dataurl = content_path + '/role_json/listJson.do';

var setUserDepartmentWin = null;//角色菜单设置窗口

var map = new Map();
map.initMap(map);
Ext.onReady(function() {
	function getMenuValueByKey(key){
		var return_value = map.get(key);
		return return_value;
	}
	
	function getMenuValueByKeys(keys){
		var return_value = "";
		var keySplits = keys.split('|');
		var keyLength = keySplits.length;
		for(var i=1;i<keyLength-1;i++){
			return_value += getMenuValueByKey(keySplits[i])+"；";
		}
		return return_value;
	}
	
	var fm = Ext.form;
	////////////////////////====================================================角色grid
	var rolejssm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false
	});

	var rolecm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer({
		header : 'NO'
	}), rolejssm, {
		id : 'id',
		header : 'Id',
		dataIndex : 'id',
		width : 30
	}, {
		header : '名称',
		dataIndex : 'name',
		width : 120,
		editor : new fm.TextField({
			allowBlank : false
		})
	}, {
		header : "拥有菜单",
		width : 450,
		dataIndex : 'menus',
		selectOnFocus : true,
        renderer: function(value,metadata,record){
			return getMenuValueByKeys(value);
        }
	}, {
		id : 'remark',
		header : '描述',
		dataIndex : 'remark',
		width : 220,
		editor : new fm.TextField({
			allowBlank : false
		})
	}, {
		header : '排序号',
		dataIndex : 'orderIndex',
		width : 70,
		editor : new fm.NumberField({
			allowBlank : false,
			maxValue : 100000
		})
	}, {
		header : 'bindId',
		dataIndex : 'bindId',
		width : 70
	} ]);

	//设定默认排序字段，
	rolecm.defaultSortable = true;

	// create the Data Stor
	var rolestore = new Ext.data.JsonStore({
		url : dataurl,
		baseParams : {
			type : ''
		},
		root : 'data',
		totalProperty : 'totalCount',
		id : 'id',
		fields : [ 'id', 'name', 'menus', 'remark', 'orderIndex', 'bindId' ],
		sortInfo : {
			field : 'orderIndex',
			direction : 'ASC'
		},
		listeners : {
			update : function(rolestore, record, option) {
				if (option == Ext.data.Record.COMMIT) {
					var jsonData = Ext.util.JSON.encode(record.data);
					Ext.Ajax.request({
						url : updateurl,
						method : 'POST',
						params : {
							data : jsonData
						},// end params
						failure : function(response, options) {
							return false;
						},// end failure block
						success : function(response, options) {
							// 添加操作返回id，更新操作返回true
							var content_re = response.responseText.trim();
							Ext.MessageBox.alert('提示', '添加成功！');
							record.data.id = content_re;
							return true;
							// 重新加载数据
							//dictstore.reload();
						}// end success block
					});
				}
			}
		}
	});

	// create the editor grid
	var rolegrid = new Ext.grid.EditorGridPanel({
		store : rolestore,
		cm : rolecm,
		sm : rolejssm,
		region : 'center',
		width : 600,
		height : 300,
		autoExpandColumn : 'remark',
		title : '系统角色',
		frame : true,
		clicksToEdit : 1,
		tbar : [
				{
					text : '添加',
					handler : function() {
						var Plant = rolegrid.getStore().recordType;
						var p = new Plant({
							id : '',
							name : '',
							menus : '',
							remark : '',
							orderIndex : 0
						});
						rolegrid.stopEditing();
						rolestore.insert(0, p);
						rolegrid.startEditing(0, 0);
					}
				},
				{
					text : '删除',
					//cls: 'x-btn-text-icon details',
					handler : function() {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(
								btn) {
							if (btn == 'yes') {
								var records = rolegrid.getSelectionModel()
										.getSelections();
								var delete_success = true;
								var ids = "";
								for (i = 0; i < records.length; i++) {
									var record = records[i];
									ids += record.id + ",";
								}
								ids += "0";
								Ext.Ajax.request({
									url : deleteurl,
									method : 'POST',
									params : {
										ids : ids
									},// end params
									failure : function(response, options) {
										delete_success = false;

									},// end failure block
									success : function(response, options) {

									}//end success block
								});

								if (delete_success) {
									Ext.MessageBox.alert('提示', '删除成功!',
											function(id) {
												rolestore.reload();
											});
								} else {
									Ext.MessageBox.alert('提示',
											'有部分数据没有删除成功，请重试。', function(id) {
												rolestore.reload();
											});
								}
							}
						});
					}
				}, {
					text : '保存',
					handler : function() {
						var modifRecords = rolestore.getModifiedRecords();
						while (modifRecords.length != 0) {
							modifRecords[0].commit();
						}
					}
				} ]
	});

	var menuTree = new Ext.tree.TreePanel({
		title : 'My Task List',
		height : 300,
		width : 400,
		useArrows : true,
		autoScroll : true,
		animate : true,
		enableDD : true,
		containerScroll : true,
		rootVisible : true,
		root : new Ext.tree.AsyncTreeNode({
			text : 'XXX集团',
			draggable : false,
			id : '0'
		}),
		listeners : {
			'checkchange' : function(node, checked) {
				if (checked) {
					node.getUI().addClass('complete');
				} else {
					node.getUI().removeClass('complete');
				}
			}
		}
	});

	//选中的角色的bindid
	var rolebindid = "";
	/**
	 * 重新加载角色相关的菜单树
	 */
	function menuTreeReLoaded(tree, url) {
		tree.on('beforeload', function(node) {
			menuTree.loader.dataUrl = url + node.id; // 定义子节点的Loader
		});//展开树 
		var TreeLoader = tree.getLoader(); //得到Ext.tree.TreeLoader
		TreeLoader.dataUrl = url; //更新数据源
		var treeroot = tree.getRootNode(); //得到根节点                
		treeroot.reload(); //重新加载根节点
		treeroot.expand(true, false);
	}
	/**
	 * 保存角色拥有的菜单的url
	 */
	function getRoleMenuUrl(menuIds) {
		return content_path
				+ "/role2/Role_saveMenuOfRole.do?role2Model.bindId="
				+ rolebindid + "&role2Model.menus=" + menuIds;
	}

	rolegrid.on("cellclick", function(grid, rowIndex, columnIndex, e) {
		var dataIndex = grid.getColumnModel().getDataIndex(columnIndex);
		if ('menus' == dataIndex) {
			rolebindid = rolestore.getAt(rowIndex).get('bindId');
			var treeloadurl = content_path
					+ '/role_json/setRoleMenuTreeData.do?role2Model.bindId='
					+ rolebindid + '&menuParentId=';
			menuTreeReLoaded(menuTree, treeloadurl);
			if (!setUserDepartmentWin) {
				setUserDepartmentWin = new Ext.Window({
					applyTo : 'setRoleMenu',
					layout : 'fit',
					width : 500,
					height : 300,
					closeAction : 'hide',
					plain : true,
					modal : true,
					items : menuTree,
					buttons : [
							{
								text : '确定',
								disabled : false,
								handler : function() {
									var menuIds = '', selNodes = menuTree
											.getChecked();
									Ext.each(selNodes, function(node) {
										menuIds += '|' + node.id;
									});
									menuIds += '|';
									var setMenuOfRole = {
										url : getRoleMenuUrl(menuIds),
										callback : function(options, success,
												response) {//回调函数
											var msg = [ "请求是否成功：", success,
													"\n", "服务器返回值：",
													response.responseText,
													"本地自定义属性：",
													options.customer ];
											rolestore.reload();
										}
									};
									Ext.Ajax.request(setMenuOfRole);//发送请求
									setUserDepartmentWin.hide();
								}
							}, {
								text : '取消',
								handler : function() {
									setUserDepartmentWin.hide();

								}
							} ]
				});
			}
			setUserDepartmentWin.show();
		}
	});
	rolegrid.store.on('load', function() {

	});
	rolestore.load();

	// border layout。数据字典类别树和类别子节点管理
	new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [ rolegrid ],
		renderTo : 'bordergrid'
	});

});