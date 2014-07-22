Ext.BLANK_IMAGE_URL = '../../R_framework/ext-3_0_0/resources/images/default/s.gif';

var treeloadurl = '../../department2_json/treeJson.do?id=';

var deptselectdataurl = '../../department2_json/listJson.do?id=';
var roleselectdataurl = '../../role_json/listJson.do';

var updateurl = '../../persion2/Persion_saveOrUpdate.action';
var deleteurl = '../../persion2/Persion_delete.action';
var dataurl = '../../persion2_json/listJson.do';




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


		//数据来源下拉列表数据源
		var deptDataFrom =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
		      root:'data',
		      url:deptselectdataurl+"0"
		});
		deptDataFrom.load();
                
                		//数据来源下拉列表数据源
		var roleDataFrom =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
		      root:'data',
		      url:roleselectdataurl
		});
		roleDataFrom.load();
                

                
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
			header : '姓名',
			dataIndex : 'name',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : '登陆账号',
			dataIndex : 'loginName',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			header : '密码',
			dataIndex : 'password',
			width : 220,
			editor : new fm.TextField({
				allowBlank : false
			})
		}, {
			id : 'roleid',
                        header : '角色',
			dataIndex : 'roleid',
			width : 220,
                        selectOnFocus:true,
                        editor: new Ext.form.ComboBox({
			 		store :roleDataFrom,
			 		typeAhead: true,
			 		id:'roleid',
			 		mode: 'remote',
			 		ref : '../area',
			 		editable :false,
			 		forceSelection: true,
			 		displayField : 'name',
			 		valueField : "bindId",
			 		triggerAction: 'all',
			 		hiddenName: 'bindId'
	            }),
                        renderer: function(value,metadata,record){
	            	var index = roleDataFrom.find('bindId',value);
	            	if(index!=-1){   
	            		return roleDataFrom.getAt(index).data.name;   
	            	}   
	            	return value==0?"":value;   
	            }
		}, {
			id : 'depId',
                        header : '部门',
			dataIndex : 'depId',
			width : 220,
                        selectOnFocus:true,
			
                        renderer: function(value,metadata,record){
	            	var index = deptDataFrom.find('bindId',value);
	            	if(index!=-1){   
	            		return deptDataFrom.getAt(index).data.name;   
	            	}   
	            	return value==0?"":value;   
	            }
		},{
			header : '排序号',
			dataIndex : 'orderIndex',
			width : 70,
			editor : new fm.NumberField({
				allowBlank : false,
				maxValue : 100000
			})
		}, {
                     id : 'bindId',
			header : 'bindId',
			dataIndex : 'bindId'
		}
	]);

	//设定默认排序字段，
	dictcm.defaultSortable = true;

	// create the Data Stor
	var dictstore = new Ext.data.JsonStore({
		url:dataurl,
		baseParams:{depid:''}, 	
		root:'data',
		totalProperty:'totalCount',
		id:'id',
		fields:['id','name','loginName','password','depId','roleid','orderIndex','bindId'],
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
		autoExpandColumn : 'bindId',
		title : '员工管理',
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
                	orderIndex:0,
                	depId:currentNode.id
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
			var new_params={depid:currentNode.id}; 
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