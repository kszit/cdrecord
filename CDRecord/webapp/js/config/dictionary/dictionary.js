Ext.BLANK_IMAGE_URL = '../../R_framework/ext-3_0_0/resources/images/default/s.gif';

var treeloadurl = '../../dictionary_json/dictCateChild.do?id=';
var updateurl = '../../dictionary/Dictionary_saveOrUpdate.action';
var deleteurl = '../../dictionary/Dictionary_delete.action';
var dataurl = '../../dictionary_json/listJson.do';

//var Catigordataurl = '../../dictionary_json/CategoryListJson.do';
var Catigordataurl = '../../dictionary_json/dictCateChild.do?id=0';
var Catigorupdateurl = '../../dictionary/DicCategory_saveOrUpdate.action';
var Catigordeleteeurl = '../../dictionary/DicCategory_delete.action';
var currentNode = null;
Ext.onReady(function() {
	// shorthand
	var Tree = Ext.tree;
	var fm = Ext.form;
	
	var tree = new Tree.TreePanel({
		title : '数据字典类别',
		region : 'west',
		autoScroll : true,
		height : 385,
		width : 155,
		autoHeight : false,
		animate : true,
		enableDD : true,// 不允许子节点拖动
		containerScroll : true,
		rootVisible : false,
		loader : new Tree.TreeLoader({
			dataUrl : treeloadurl + '0'
		}),
		tbar : [ {
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
					items:[dictCatigorygrid],
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
		dictstore.reload();
	});
	tree.on('beforeload', function(node) {
		tree.loader.dataUrl = treeloadurl + node.id; // 定义子节点的Loader
	});
	tree.on('load', function(node) {//加载子节点后，选中子节点的第一个节点
		if(node.childNodes.length!=0){
			currentNode = node.childNodes[0];
			tree.selectPath(node.childNodes[0].getPath());
			dictstore.reload();
		}
	});

	//设置父节点
	var root = new Tree.AsyncTreeNode({
		text : '123',
		draggable : false,
		id : '0'
	});
	tree.setRootNode(root);

	//展开父节点
	root.expand();

	
	////////////////////////====================================================数据字典grid
	// custom column plugin example
	var checkColumn = new Ext.grid.CheckColumn({
		header : '是否启用',
		dataIndex : 'used',
		width : 55
	});
	var dictjssm = new Ext.grid.CheckboxSelectionModel({singleSelect:false}); 
	// the column model has information about grid columns
	// dataIndex maps the column to the specific data field in
	// the data store (created below)
	var dictcm = new Ext.grid.ColumnModel([    	
	    new Ext.grid.RowNumberer({
	    	header : 'NO'
	    }), 
		dictjssm,
		{
			id : 'id',
			header : 'Id',
			dataIndex : 'id',
			width : 30
		}, {
			header : '名称',
			dataIndex : 'name',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			id:'describe2',
			header : '描述',
			dataIndex : 'describe2',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, checkColumn , {
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
		}
	]);

	//设定默认排序字段，
	dictcm.defaultSortable = true;

	// create the Data Stor
	var dictstore = new Ext.data.JsonStore({
		url:dataurl,
		baseParams:{type:''}, 	
		root:'data',
		totalProperty:'totalCount',
		id:'id',
		fields:['id','name','describe2','used','orderIndex','type','bindId'],
		sortInfo : {
			field : 'orderIndex',
			direction : 'ASC'
		},
		listeners:{
			update : function(dictstore, record, option) {
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
								//alert(content_re);
								Ext.MessageBox.alert('提示', '更新成功！');
								Ext.MessageBox.alert('提示', '添加成功！');
								record.data.id = content_re;
								// alert(content_re.trim());
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
	var dictgrid = new Ext.grid.EditorGridPanel({
		store : dictstore,
		cm : dictcm,
		sm:dictjssm,
		region : 'center',
		width : 600,
		height : 300,
		autoExpandColumn : 'describe2',
		title : '数据字典',
		frame : true,
		plugins : checkColumn,
		clicksToEdit : 1,
		tbar : [ {
			text : '添加',
			handler : function() {
				if(currentNode==null){
					alert('请选择类别');
					return ;
				}
                var Plant = dictgrid.getStore().recordType;
                var p = new Plant({
                	id:'',
                	name: '',
                	describe: '',
                	used: true,
                	orderIndex:0,
                	type:currentNode.id
                });
                dictgrid.stopEditing();
                dictstore.insert(0, p);
                dictgrid.startEditing(0, 0);
			}
		}, {
			text : '删除',
			//cls: 'x-btn-text-icon details',
			handler : function(){
				/*
					var record = dictgrid.getSelectionModel().getSelected();
					alert(record);
					alert(Ext.type(record));
					if(Ext.type(record)=='false'){
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					}else{
						//jsstore.remove(record);
					}
				*/
				
				Ext.MessageBox.confirm('提示框', '您确定要进行该操作？',function(btn){
					if(btn=='yes'){
						var records = dictgrid.getSelectionModel().getSelections();
						var delete_success = true;
						var ids = "";
						for(i=0;i<records.length;i++){
							var record = records[i];
							ids += record.id+",";
						}
						ids += "0";
						Ext.Ajax.request({
						   url : deleteurl,
						   method : 'POST',
						   params : {
							   deleteids:ids
						   },// end params
						   failure : function(response, options) {
								delete_success = false;
								
						   },// end failure block
						   success : function(response, options) {
							   //dictstore.reload();
								//Ext.MessageBox.alert('提示','删除成功！');
								//store.reload();
								//jsstore.remove(record);
						   }//end success block
						});
						
						if(delete_success){
							Ext.MessageBox.alert('提示','删除成功!',function(id){dictstore.reload();});
						}else{
							Ext.MessageBox.alert('提示','有部分数据没有删除成功，请重试。',function(id){dictstore.reload();});
						}
					}
				});	
				
	            }
		}, {
			text : '保存',
			handler : function() {
            	var modifRecords = dictstore.getModifiedRecords();
            	while(modifRecords.length != 0){
            		modifRecords[0].commit();
            	}
            	//dictstore.reload();
			}
		} ]
	});
	
	dictgrid.store.on('load',function(){
		
	});
	dictgrid.store.on('beforeload',function(dictstore,options){
		if(currentNode != null){
			var new_params={type: currentNode.id}; 
			Ext.apply(options.params,new_params); 
		}
	});
	 
	
	// trigger the data store load
	dictstore.load();

	

	////////////////////////////////////////////=======================================================数据字典类别grid
	var dictCatigoryjssm = new Ext.grid.CheckboxSelectionModel({singleSelect:false}); 
	// the column model has information about grid columns
	// dataIndex maps the column to the specific data field in
	// the data store (created below)
	var dictCatigorycm = new Ext.grid.ColumnModel([    	
	    new Ext.grid.RowNumberer({
	    	header : 'NO'
	    }), 
		dictCatigoryjssm,
		{
			id : 'id',
			header : 'Id',
			dataIndex : 'id',
			sortable:true,
			width : 30
		}, {
			header : '名称',
			dataIndex : 'text',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : 'bindId',
			dataIndex : 'bindId',
			width : 220
		}
	]);

	// create the Data Stor
	var dictCatigorystore = new Ext.data.JsonStore({
		url:Catigordataurl,
		baseParams:{type:''},
		//root:'data',
		//totalProperty:'totalCount',
		id:'id',
		fields:['id','text','bindId'],
		sortInfo : {
			field : 'id',
			direction : 'ASC'
		},
		listeners:{
			update : function(dictstore, record, option) {
				if (option == Ext.data.Record.COMMIT) {
					var jsonData = Ext.util.JSON.encode(record.data);
					Ext.Ajax.request({
						url : Catigorupdateurl,
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
	var dictCatigorygrid = new Ext.grid.EditorGridPanel({
		store : dictCatigorystore,
		cm : dictCatigorycm,
		sm:dictCatigoryjssm,
		width : 600,
		height : 300,
		
		title : '数据字典类别',
		frame : true,
		clicksToEdit : 1,
		tbar : [ {
			text : '添加',
			handler : function() {
                var Plant = dictCatigorygrid.getStore().recordType;
                var p = new Plant({
                	id:'',
                	text: ''
                });
                dictCatigorygrid.stopEditing();
                dictCatigorystore.insert(0, p);
                dictCatigorygrid.startEditing(0, 0);
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
						var records = dictCatigorygrid.getSelectionModel().getSelections();
						var delete_success = true;
						var ids = "";
						for(i=0;i<records.length;i++){
							var record = records[i];
							ids += record.id+",";
						}
						ids += "0";
						Ext.Ajax.request({
						   url : Catigordeleteeurl,
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
							Ext.MessageBox.alert('提示','删除成功!',function(id){dictCatigorystore.reload();});
						}else{
							Ext.MessageBox.alert('提示','有部分数据没有删除成功，请重试。',function(id){dictCatigorystore.reload();});
						}
					}
				});	
				
	            }
		}, {
			text : '保存',
			handler : function() {
            	var modifRecords = dictCatigorystore.getModifiedRecords();
            	//alert(modifRecords.length);
            	while(modifRecords.length != 0){
            		modifRecords[0].commit();
            		//alert(modifRecords[0].data.spmc);
            	}
            	dictCatigorystore.reload();
            	//tree.getRootNode().reload();
			}
		} ]
	});
	
	dictCatigorygrid.store.on('load',function(){
		
	});
	dictCatigorygrid.store.on('beforeload',function(dictstore,options){
		//alert(currentNode.id);
		if(currentNode == null){
			var new_params={type: '1'}; 
			Ext.apply(options.params,new_params); 
		}else{
			var new_params={type: currentNode.id}; 
			Ext.apply(options.params,new_params); 
		}

	});
	 
	
	// trigger the data store load
	dictCatigorystore.load();


	
	
	
	// border layout。数据字典类别树和类别子节点管理
	new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [ tree, dictgrid ],
		renderTo : 'bordergrid'
	});

});