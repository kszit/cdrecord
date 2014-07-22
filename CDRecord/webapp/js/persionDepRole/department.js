Ext.BLANK_IMAGE_URL = '../../R_framework/ext-3_0_0/resources/images/default/s.gif';

var treeloadurl = '../../department2_json/treeJson.do?id=';
var updateurl = '../../department2/Department_saveOrUpdate.action';
var deleteurl = '../../department2/Department_delete.action';
var dataurl = '../../department2_json/listJson.do';




var currentNode = null;
Ext.onReady(function() {
	// shorthand
	var Tree = Ext.tree;
	var fm = Ext.form;
	
	var tree = new Tree.TreePanel({
		title : '部门列表',
		region : 'west',
		autoScroll : true,
		height : 385,
		width : 155,
		autoHeight : false,
		animate : true,
		enableDD : true,// 不允许子节点拖动
		containerScroll : true,
		rootVisible : true,
		loader : new Tree.TreeLoader({
			dataUrl : treeloadurl + '0'
		}),
		tbar : [ {
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
		text : 'XXX集团',
		draggable : false,
		id : '0'
	});
	tree.setRootNode(root);

	//展开父节点
	root.expand();

	
	////////////////////////====================================================数据字典grid

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
			id:'type',
			header : '类型',
			dataIndex : 'type',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			id:'code',
			header : '编码',
			dataIndex : 'code',
			width : 220
		}, {
			id:'filter',
			header : '过滤值',
			dataIndex : 'filter',
			width : 100
		}, {
			id:'describe2',
			header : '描述',
			dataIndex : 'describe2',
			editor : new fm.TextField({
				allowBlank : false
			})
		},  {
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
		}, {
			header : 'parentid',
			dataIndex : 'parentid',
			width : 50
		}
	]);

	//设定默认排序字段，
	dictcm.defaultSortable = true;

	// create the Data Stor
	var dictstore = new Ext.data.JsonStore({
		url:dataurl,
		baseParams:{id:''}, 	
		root:'data',
		totalProperty:'totalCount',
		id:'id',
		fields:['id','name','type','code','filter','describe2','orderIndex','bindId','parentid'],
		sortInfo : {
			field : 'id',
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
		title : '部门管理',
		frame : true,
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
                	describe2: '',
                	orderIndex:0,
                	parentid:currentNode.id
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
			var new_params={id:currentNode.id}; 
			Ext.apply(options.params,new_params); 
		}
                
	});
	 
	
	// trigger the data store load
	dictstore.load();

	
	
	// border layout。数据字典类别树和类别子节点管理
	new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [  tree,dictgrid ],
		renderTo : 'bordergrid'
	});

});