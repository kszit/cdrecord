//  =======================================================================================
VerticalColumnConfig = function() {
	var reportId = '0';
	var dataTypeId = 0;
	var dataFromId = 0
	var UIModuleId = 0;
	
	var grid, record;
	var deleteUrl = content_path+"/reportConfig/verticalConfigC_delete.action";
	var saveUrl = content_path+"/reportConfig/verticalConfigC_save.action";
	//var dataUrl = "/reportData/reportConfig_json/vertiCalcolumnJson.action?verticalColumnModel._id=";
	var dataUrl = content_path+"/reportConfig_json/vertiCalcolumnJson.action";
	var newidUrl = content_path+"/reportConfig/verticalConfigC_newVertical.action";

	var comBoxDataSource = content_path+"/dictionary_json/listJson.do?type=";

	var store;
	
	var activeConfigDialog_v;
	if (!activeConfigDialog_v) {
		activeConfigDialog_v = new Ext.Window({
	      el : 'configDlgv',
	      layout : 'fit',
	      title : 'UI组件配置',
	      width : 800,
	      height : 410,
	      plain : true,
	      closable : false,
	      closeAction : 'hide',
	      resizable : false,
	      modal : true,
	      items : [{
	        html : '<iframe name=activeConfigDlgPage id=activeConfigDlgPage frameborder=0 width=100% height=100% src=../aws_html/load.htm></iframe>'
	      }],
	      buttons : [{
	        text : '应用',
	        handler : function() {
	        	//提交子页面的表单，ajax方式
	        	document.getElementById('activeConfigDlgPage').contentWindow.submitForm();
	        }
	      }, {
	        text : '关闭',
	        handler : function() {
	        	store.reload();
	        	activeConfigDlgPage.location = "../aws_html/wait.htm";
	        	activeConfigDialog_v.hide();
	        }
	      }]
	    });
	  }
	
	function openUIConfig(pagename, reportBindid,rowBindid, url,fieldName) {
		activeConfigDialog_v.setTitle(pagename);
		activeConfigDialog_v.purgeListeners();
		activeConfigDialog_v.addListener('show', function() {
			activeConfigDlgPage.location = url;
			this.center();
			return;
		});
		activeConfigDialog_v.show();
	}
	
	function setReportId(reportid){
		reportId = reportid;
        grid.getStore().reload({params:{reportid:reportId}}); 
	}
	function canAddItem(){
		if(reportId=='0-0'){
			alert("请先保存表单");
			return false;
		}else{
			return true;
		}
	}
	function createGrid(reportid,dataTypeid,dataFromid,UIModeleid) {
		reportId = reportid;
		dataTypeId = dataTypeid;
		dataFromId = dataFromid
		UIModuleId = UIModeleid;
		
	    var sm = new Ext.grid.CheckboxSelectionModel({
	    	singleSelect:true,
	        listeners:{
	            // On selection change, set enabled state of the removeButton
	            // which was placed into the GridPanel using the ref config
	            selectionchange: function(sm) {
	                if (sm.getCount()) {
	                    grid.removeButton.enable();
	                } else {
	                    grid.removeButton.disable();
	                }
	            }
	        }
	    });
		
		// create the data store
		var record = Ext.data.Record.create([ {
			name : 'content'
		}, {
			name : 'dataCode'
		}, {
			name : 'dataType',
			type : 'int'
		}, {
			name : 'dataLength',
			type : 'int'
		}, {
			name : 'isEmpty',
			type : 'bool'
		}, {
			name : 'defaultValue'
		}, {
			name : 'UIModule',
			type : 'int'
		}, {
			name : 'UIModuleDS'
		}, {
			name : 'dataFrom',
			type : 'int'
		}, {
			name : 'dataRule'
		}, {
			name : 'dataRelation'
		}, {
			name : 'width',
			type : 'int'
		}, {
			name : 'height',
			type : 'int'
		}, {
			name : 'id',
			type : 'int'
		}, {
			name : '_id',
			type : 'int'
		}, {
			name : '_parent',
			type : 'int'
		}, {
			name : '_is_leaf',
			type : 'bool'
		}, {
			name : 'orderIndexStr'
		}, {
			name : 'reportBindid'
		}, {
			name : 'bindId',
			type : 'int'
		} ]);
		
		store = new Ext.ux.maximgb.tg.AdjacencyListStore({
			
			autoLoad : false,
			reader : new Ext.data.JsonReader({
				id : '_id'
			}, record),
			proxy : new Ext.data.HttpProxy({url:dataUrl}),
			listeners:{
				update : function(dictstore, record, option) {
					if (option == Ext.data.Record.COMMIT) {
						record.data.reportBindid = reportId;
						var jsonData = Ext.util.JSON.encode(record.data);
						Ext.Ajax.request({
							url : saveUrl,
							method : 'POST',
							params : {
								saveDataJson : jsonData
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
									record.data.id = content_re;
									// alert(content_re.trim());
									return true;
								}
								// 重新加载数据
								store.reload();
							}// end success block
						});
						
					}
				}
			}
		});
		store.load({params:{reportid:reportId}}); 
		
		//UI组件下拉列表数据源
		var headerUIModule =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
		      root:'data',
		      url:comBoxDataSource+UIModuleId
		});
		headerUIModule.load();
		
		//数据来源下拉列表数据源
		var headerDataFrom =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
		      root:'data',
		      url:comBoxDataSource+dataFromId
		});
		headerDataFrom.load();
		
		//数据类型下拉列表数据源
		var headerDataType =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
 		      root:'data',
 		      url:comBoxDataSource+dataTypeId
 		});
		headerDataType.load();
		headerDataType.on('load',function(){
			//alert(Ext.getCmp('headerDataType').valueField);
			//alert('commo data load');
		});
		
		var checkColumn = new Ext.grid.CheckColumn({
			header : '允许空',
			dataIndex : 'isEmpty',
			width : 55
		});
		// create the Grid
		grid = new Ext.ux.maximgb.tg.EditorGridPanel({
			store : store,
			master_column_id : 'content',
			renderTo:'VerticalColumnGrid',
			height:350,
			sm:sm,
			columnLines: true,
			stripeRows : true,
			autoExpandColumn : 'content',
			title : '左侧列配置',
			clicksToEdit:1,
			plugins : checkColumn,
			columns : [ 
			    sm,
			 {
				id : 'content',
				header : "名称",
				width : 160,
				sortable : true,
				dataIndex : 'content',
				selectOnFocus:true,
				editor : new Ext.form.TextField({
					allowBlank : false
				})
			}, {
				header : "代码",
				width : 75,
				sortable : true,
				dataIndex : 'dataCode',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "数据类型",
				width : 75,
				sortable : true,
				dataIndex : 'dataType',
				selectOnFocus:true,
			 	editor: new Ext.form.ComboBox({
			 		store :headerDataType,
			 		typeAhead: true,
			 		id:'headerDataType',
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
	            	//alert("value:"+value);
	            	var index = headerDataType.find('bindId',value);
	            	//alert("index:"+index);
	            	if(index!=-1){   
	            		return headerDataType.getAt(index).data.name;   
	            	}   
	            	return value;   
	            }
			}, {
				header : "数据长度",
				width : 75,
				sortable : true,
				dataIndex : 'dataLength',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			},checkColumn, {
				header : "默认值",
				width : 75,
				sortable : true,
				dataIndex : 'defaultValue',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "UI组件",
				width : 75,
				sortable : true,
				dataIndex : 'UIModule',
				selectOnFocus:true,
				/*
				editor: new Ext.form.ComboBox({
			 		store :headerUIModule,
			 		typeAhead: true,
			 		id:'headerUIModule',
			 		mode: 'remote',
			 		ref : '../area',
			 		editable :false,
			 		forceSelection: true,
			 		displayField : 'name',
			 		valueField : "bindId",
			 		triggerAction: 'all',
			 		hiddenName: 'bindId'
	            }),
	            */
	            renderer: function(value,metadata,record){
	            	var index = headerUIModule.find('bindId',value);
	            	if(index!=-1){   
	            		return headerUIModule.getAt(index).data.name;   
	            	}   
	            	return value;   
	            }
			}, 
			/*{
				header : "UI组件数据源",
				width : 75,
				sortable : true,
				dataIndex : 'UIModuleDS',
				selectOnFocus:true,
				editor : new Ext.form.TextField({
					allowBlank : false
				})
			}, 
			*/{
				header : "数据来源",
				width : 75,
				sortable : true,
				dataIndex : 'dataFrom',
				selectOnFocus:true,
			 	editor: new Ext.form.ComboBox({
			 		store :headerDataFrom,
			 		typeAhead: true,
			 		id:'headerDataFrom',
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
	            	//alert("value:"+value);
	            	var index = headerDataFrom.find('bindId',value);
	            	//alert("index:"+index);
	            	if(index!=-1){   
	            		return headerDataFrom.getAt(index).data.name;   
	            	}   
	            	return value;   
	            }
			}, {
				header : "计算规则",
				width : 75,
				sortable : true,
				dataIndex : 'dataRule',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "平衡关系",
				width : 75,
				sortable : true,
				dataIndex : 'dataRelation',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "宽度",
				width : 75,
				sortable : true,
				dataIndex : 'width',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "高度",
				width : 75,
				sortable : true,
				dataIndex : 'height',
				editor : new Ext.form.NumberField({
					allowBlank : false
				})
			}, {
				header : "id",
				width : 75,
				sortable : true,
				dataIndex : 'id'
			}],
			//plugins : expander,
			viewConfig : {
				enableRowBody : true
			},

	        // inline toolbars
	        tbar:[{
	            text:'添加',
	            tooltip:'Add a new row',
	            iconCls:'add',
	            handler:function(button,evn){
	            	if(!canAddItem()){
	            		return;
	            	}
					var records = grid.getSelectionModel().getSelections();
					var store = grid.getStore();
					var insertPosition;
					var parentid;
					if(records.length==1){
						var record = records[0];
						var record_index = store.indexOf(record);
						store.getAt(record_index).set('_is_leaf','false');
						parentid = record.data.bindId;
						insertPosition = record_index+1;
					}else{
						insertPosition = 0;
						parentid = 0;
					}
					var childsize = 0;
					var childids = '';
					store.each(function(temp_record){
						if(parentid!=0){
							if(temp_record.data._parent==record.data._id || childids.indexOf('#'+temp_record.data._parent+',')!=-1){
								childids += '#'+temp_record.data._id+',';
								childsize++;
							}
						}else{
							childsize++;
						}
					});
					insertPosition += childsize;
					Ext.Ajax.request({
					   url:newidUrl,
					   params : {
						   parentid:parentid,
						   reportid:reportId
					   },
					   success: function(response, options){
						   var newidAndOrder = response.responseText.trim();
						   var temp = newidAndOrder.split('@@');
						   var bindid = temp[2];
						   var neworderIndex = temp[1];
						   	var newid = temp[0];
			                var Plant = grid.getStore().recordType;
			                var p = new Plant({
			                	content : "",
			                	width : 0,
			        			height : 0,
			        			id:newid,
			        			_id : bindid,
			        			_parent : parentid,
			        			_is_leaf : true,
			        			bindId : bindid,
			        			orderIndexStr:neworderIndex,
			        			dataType:103,//文本
			        			dataLength:50,
			        			UIModule:141,//文本框
			        			dataFrom:112,//手工输入
			        			uiModelChildItemSet:182,
			        			dataFromChildItemSet:182,
			        			height:20,
			        			width:20
			                });
			                grid.stopEditing();
			                store.insert(insertPosition, p);
			                grid.startEditing(0, 0);
                                        //store.reload();
					   },
					   failure: function(response, options){}
					});
	            }
	        }, '-', {
	            text:'保存',
	            tooltip:'保存',
	            iconCls:'save',
	            handler:function(button,evn){
	            	var modifRecords = store.getModifiedRecords();
	            	while(modifRecords.length != 0){
	            		modifRecords[0].commit();
	            	}
	            }
	        },'-',{
	            text:'删除',
	            tooltip:'Remove the selected item',
	            iconCls:'remove',
	            ref: '../removeButton',
	            disabled: true,
	            handler : function(){
					Ext.MessageBox.confirm('提示框', '您确定要进行该操作？',function(btn){
						if(btn=='yes'){
							
							var records = grid.getSelectionModel().getSelections();
							var delete_success = true;
							var ids = "";
							for(i=0,records_length=records.length;i<records_length;i++){
								var record = records[i];
								if(!record.data._is_leaf){
									alert("此记录下有子记录，请先删除子记录！");
									return ;
								}
								if(records_length==(i+1)){
									ids += record.data.id;
								}else{
									ids += record.data.id+",";
								}
							}
							Ext.Ajax.request({
							   url : deleteUrl,
							   method : 'POST',
							   params : {
								   deleteIds:ids
							   },
							   failure : function(response, options) {delete_success = false;},
							   success : function(response, options) {}
							});
							
							if(delete_success){
								Ext.MessageBox.alert('提示','删除成功!',function(id){store.reload();});
							}else{
								Ext.MessageBox.alert('提示','有部分数据没有删除成功，请重试。',function(id){store.reload();});
							}
							
						}
					});	
					
	            }
	        }, '-', {
	            text:'刷新',
	            tooltip:'刷新',
	            iconCls:'refresh',
	            handler:function(button,evn){
	            	store.reload();
	            }
	        }, '-', {
	            text:'字典数据',
	            tooltip:'字典数据',
	            iconCls:'refresh',
	            handler:function(button,evn){
	            	var form1 = new Ext.form.FormPanel({
	            		height : 150,
	            		width : 250,
	            		region : 'center',		
	            		items:[
	    			new Ext.form.ComboBox({
	    				fieldLabel : '数据项',
				 		store :headerUIModule,
				 		typeAhead: true,
				 		id:'selectZiDianOfUiModel',
				 		mode: 'remote',
				 		ref : '../area',
				 		editable :false,
				 		forceSelection: true,
				 		displayField : 'name',
				 		valueField : "bindId",
				 		triggerAction: 'all',
				 		hiddenName: 'bindId'
		            })
	            	]
	            	});
	        		var win = new Ext.Window(
	        				{
	        					layout : "border",
	        					width : 300,
	        					height : 200,
	        					modal : true,
	        					closeAction : 'hide',
	        					buttonAlign : "center",
	        					title : "从下级表选择指标",
	        					items : [form1 ],
	        					buttons : [
	        							{
	        								text : '确定',
	        								handler : function(button, evn) {
	        									
	        										var dictId = Ext.getCmp('selectZiDianOfUiModel').value;
	        										
	        										Ext.Ajax.request({
	        											   url:content_path+'/reportConfig/verticalConfigC_leftItemFromDictOrBuess.do',
	        											   params : {
	        												   param1:dictId,
	        												   param2:reportId
	        											   },
	        											   success: function(response, options){
	        												   alert(response.text);
	        												   store.reload();
	        											   },
	        											   failure: function(response, options){}
	        											});
	        										
	        									
	        								}
	        							}, {
	        								text : '取消',
	        								handler : function(button, evn) {
	        									win.hide();
	        									win.destroy();
	        								}
	        							} ]
	        				});
	        		win.show();
	            	
	            	//store.reload();
	            }
	        }]
		});
		grid.on("cellclick", function(grid, rowIndex, columnIndex, e) {
			var record = store.getAt(rowIndex);
			var rowBindid = record.get('bindId');
			var rowName = record.get('content');
			if(columnIndex==7 || columnIndex==8) {
				if (rowBindid == 0) {
					Ext.Msg.alert('提示', '请保存表格中的数据!');
					return false;
				}
				var url;
				switch (columnIndex){  
				case 7: //UI model 配置信息
					url = content_path+'/reportConfig/verticalConfigC_uiModelConfigPage.action?verticalColumnModel.bindId='+rowBindid+'&verticalColumnModel.reportBindid='+reportId;
					break;  
				case 8: //数据来源（计算方式）
					url = content_path+'/reportConfig/verticalConfigC_dataFromConfigPage.action?verticalColumnModel.bindId='+rowBindid+'&verticalColumnModel.reportBindid='+reportId;
					break;  
				default: 
					alert("other");
				}

				openUIConfig(rowName,reportId,rowBindid,url,1);
			}
		});
	}

	return {
		init : function(reportid,dataTypeid,dataFromid,UIModeleid) {
			createGrid(reportid,dataTypeid,dataFromid,UIModeleid);
		},
		setReportId:function(reportid){
			setReportId(reportid);
		}
	}
}();
