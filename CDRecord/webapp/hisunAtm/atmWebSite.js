Ext.BLANK_IMAGE_URL = content_path
		+ '/R_framework/ext-3_0_0/resources/images/default/s.gif';



//list保存
var updateurl = content_path + '/atm/websit_saveOrUpdate.action';
var deleteurl = content_path + '/atm/websit_delete.action';
//list数据url
var dataurl = content_path + '/websit_json/listJson.do';

Ext.onReady(function() {

	
	
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
		header : '网点名称',
		dataIndex : 'wsName',
		width : 250,
		editor : new fm.TextField({
			allowBlank : false
		})
	}, {
		header : "编号",
		width : 150,
		dataIndex : 'wsNumber',
		editor : new fm.TextField({
			allowBlank : false
		})
	}, {
		id : 'belongBank',
		header : "归属银行",
		width : 50,
		dataIndex : 'belongBank',
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
		fields : [ 'id', 'wsName', 'wsNumber', 'belongBank', 'orderIndex', 'bindId' ],
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
		autoExpandColumn : 'belongBank',
		title : 'atm网点',
		frame : true,
		clicksToEdit : 1,
		tbar : [
				{
					text : '添加',
					handler : function() {
						var Plant = rolegrid.getStore().recordType;
						var p = new Plant({
							id : '',
							wsname : '',
							wsnumber : '',
							belongBank : '',
							orderIndex : 0,
							bindId:''
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
	rolestore.reload();
	// border layout。数据字典类别树和类别子节点管理
	new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [ rolegrid ],
		renderTo : 'bordergrid'
	});

});