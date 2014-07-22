Ext.BLANK_IMAGE_URL = '../R_framework/ext-3_0_0/resources/images/default/s.gif';

var treeloadurl = '../menu_json/menuTreeNodes.do?parentid=';

var menudataurl = '../menu_json/menuGridDatas.do?parentid=0';
var menuupdateurl = '../menu/menu_save.action';
var menudeleteurl = '../menu/menu_delete.action';

var currentNode = null;
Ext.onReady(function() {
	
	var fm = Ext.form;
	
	var tree = new Ext.tree.TreePanel({
	    renderTo: 'tree-div',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    rootVisible : false,
	    // auto create TreeLoader
	    dataUrl: treeloadurl+0,
	    root: {
	        nodeType: 'async',
	        text: 'Ext JS',
	        draggable: false,
	        id: '0'
	    },
		tbar : [{
			text : '类别管理',
			handler : function() {
				var dictCategoryWin = new Ext.Window({
					contentEl : 'AWS_ORG_Operate_Div',
					layout : 'fit',
					title : '2',
					width : '600',
					height : '700',
					shadow : false,
					maximizable : true,
					plain : true,
					closeAction : 'hide',
					modal: true,
					items:[menugrid],
					listeners:{
						hide:function() {
							tree.getRootNode().reload();
						}
					}
				});
				dictCategoryWin.show();
			}
		},{
			text : '刷新',
			handler : function() {
				tree.getRootNode().reload();
			}
		}]
	});
	
	tree.on('click', function(node) {
		currentNode = node;
		//dictstore.reload();
	});
	tree.on('beforeload', function(node) {
		tree.loader.dataUrl = treeloadurl + node.id; // 定义子节点的Loader
	});
	tree.on('load', function(node) {//加载子节点后，选中子节点的第一个节点
		if(node.childNodes.length!=0){
			currentNode = node.childNodes[0];
			tree.selectPath(node.childNodes[0].getPath());
			//dictstore.reload();
		}
	});
	tree.getRootNode().expand();
	
	
	var menujssm = new Ext.grid.CheckboxSelectionModel({singleSelect:false}); 
	var menucm = new Ext.grid.ColumnModel([    	
	    new Ext.grid.RowNumberer({
	    	header : 'NO'
	    }), 
	    menujssm,
		{
			id : 'id',
			header : 'Id',
			dataIndex : 'id',
			sortable:true,
			width : 30
		}, {
			header : '名称',
			dataIndex : 'text',
			width : 100,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : '链接',
			dataIndex : 'href',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : '目标窗口',
			dataIndex : 'hrefTarget',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : '排序号',
			dataIndex : 'orderIndex',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}
	]);

	// create the Data Stor
	var menustore = new Ext.data.JsonStore({
		url:menudataurl,
		baseParams:{type:''},
		root:'data',
		totalProperty:'totalCount',
		id:'id',
		fields:['id','text','href','hrefTarget','bindId','orderIndex'],
		sortInfo : {
			field : 'orderIndex',
			direction : 'ASC'
		},
		listeners:{
			update : function(dictstore, record, option) {
				if (option == Ext.data.Record.COMMIT) {
					var jsonData = Ext.util.JSON.encode(record.data);
					Ext.Ajax.request({
						url : menuupdateurl,
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
							if (content_re == "true") {
								Ext.MessageBox.alert('提示', '更新成功！');
							} else {
								Ext.MessageBox.alert('提示', '添加成功！');
								//record.data.id = content_re;
								// alert(content_re.trim());
								return true;
							}
							// 重新加载数据
							//dictstore.reload();
						}// end success block
					});
				}
			}
		}
	});

	// create the editor grid
	var menugrid = new Ext.grid.EditorGridPanel({
		store : menustore,
		cm : menucm,
		sm:menujssm,
		width : 600,
		height : 300,
		
		title : '数据字典类别',
		frame : true,
		clicksToEdit : 1,
		tbar : [ {
			text : '添加',
			handler : function() {
				
				if(currentNode==null){
					alert('请选择类别');
					//return ;
				}
                var Plant = menugrid.getStore().recordType;
                var p = new Plant({
                	id:'',
                	text: ''
                });
                menugrid.stopEditing();
                menustore.insert(0, p);
                menugrid.startEditing(0, 0);
			}
		}, {
			text : '删除',
			//cls: 'x-btn-text-icon details',
			handler : function(){
				/*
					var record = jsgrid.getSelectionModel().getSelected();
					if(record==null){
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					}else{
						jsstore.remove(record);
					}
				*/
				
				Ext.MessageBox.confirm('提示框', '您确定要进行该操作？',function(btn){
					if(btn=='yes'){
						var records = menugrid.getSelectionModel().getSelections();
						var delete_success = true;
						var ids = "";
						for(i=0;i<records.length;i++){
							var record = records[i];
							ids += record.id+",";
						}
						ids += "0";
						Ext.Ajax.request({
						   url : menudeleteurl,
						   method : 'POST',
						   params : {
							   ids:ids
						   },// end params
						   failure : function(response, options) {
								delete_success = false;
								//store.reload();
						   },// end failure block
						   success : function(response, options) {
								//Ext.MessageBox.alert('提示','删除成功！');
								//store.reload();
								//jsstore.remove(record);
						   }//end success block
						});
						if(delete_success){
							Ext.MessageBox.alert('提示','删除成功!',function(id){menustore.reload();});
						}else{
							Ext.MessageBox.alert('提示','有部分数据没有删除成功，请重试。',function(id){menustore.reload();});
						}
					}
				});	
				
	            }
		}, {
			text : '保存',
			handler : function() {
            	var modifRecords = menustore.getModifiedRecords();
            	//alert(modifRecords.length);
            	while(modifRecords.length != 0){
            		modifRecords[0].commit();
            		alert(modifRecords[0].data.spmc);
            	}
            	menustore.reload();
            	//tree.getRootNode().reload();
			}
		} ]
	});
	
	menugrid.store.on('load',function(){
		
	});
	menugrid.store.on('beforeload',function(dictstore,options){
		//alert(currentNode.id);
		if(currentNode == null){
			var new_params={type: '1'}; 
			Ext.apply(options.params,new_params); 
		}else{
			var new_params={type: currentNode.id}; 
			Ext.apply(options.params,new_params); 
		}

	});
	if(!isadmin){
		tree.getTopToolbar().get(0).hide();
	}
	
	menustore.load();
	


});