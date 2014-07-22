HeaderRowConfig = function() {
	var reportId = '0';
	var dataTypeId = 0;
	var dataFromId = 0
	var UIModuleId = 0;
	var grid, record;
	var deleteUrl = content_path+"/reportConfig/headerRowC_delete.action";
	var saveUrl = content_path+"/reportConfig/headerRowC_save.action";
	var dataUrl = content_path+"/reportConfig_json/headerRowsJson.action";
	var newidUrl = content_path+"/reportConfig/headerRowC_newHeaderRow.action";
	
	var comBoxDataSource = content_path+"/dictionary_json/listJson.do?type=";
	
	var store;
	
	var activeConfigDialog;
	if (!activeConfigDialog) {
	    activeConfigDialog = new Ext.Window({
	      el : 'configDlg',
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
	        	activeConfigDialog.hide();
	        }
	      }]
	    });
	  }
          
	//弹出窗口
	function openUIConfig(pagename, reportBindid,rowBindid, url,fieldName,height,width) {
		activeConfigDialog.setTitle(pagename);
		activeConfigDialog.purgeListeners();
		activeConfigDialog.addListener('show', function() {
			activeConfigDlgPage.location = url;
			activeConfigDialog.setHeight(height);
			activeConfigDialog.setWidth(width);
			this.center();
			return;
		});
		activeConfigDialog.show();
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
	function createGrid(reportid,dataTypeid,dataFromid,UIModuleid) {
		reportId = reportid;
		dataTypeId = dataTypeid;
		dataFromId = dataFromid;
		UIModuleId = UIModuleid;
		
		
	    var sm = new Ext.grid.CheckboxSelectionModel({
	    	singleSelect:true,
	        listeners:{
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
			type : 'float'
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
			name : 'dataRuleItems'
		}, {
			name : 'dataRelation'
		}, {
			name : 'dataRelationItems'
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
		}, {
			name : 'uiModelChildItemSet',
			type : 'int'
		}, {
			name : 'dataFromChildItemSet',
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
						Ext.getBody().mask("数据重新加载中，请稍等"); 
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
									//return true;
								}
								// 重新加载数据
								store.reload();
								Ext.getBody().unmask();//去除MASK   
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
		
		//是否配置数据来源或uiModel属性
		var isSettingType =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
 		      root:'data',
 		      url:comBoxDataSource+isSettingbindid
 		});
		isSettingType.load();
		
		var checkColumn = new Ext.grid.CheckColumn({
			header : '允许空',
			dataIndex : 'isEmpty',
			width : 55
		});
		// create the Grid
		grid = new Ext.ux.maximgb.tg.EditorGridPanel({
            id:'rconfigh',
			store : store,
			master_column_id : 'content',
			renderTo:'headerRowGrid',
			height:350,
			sm:sm,
			columnLines: true,
			stripeRows : true,
			autoExpandColumn : 'content',
			title : '表头行配置',
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
	            	return value==0?"":value;   
	            }
			}, {
				header : "数据长度",
				width : 75,
				sortable : true,
				dataIndex : 'dataLength',
				selectOnFocus:true,
				editor : new Ext.form.NumberField({
					allowBlank : false
				}),
                renderer: function(value,metadata,record){
                    return value==0?"":value;   
                }
			},checkColumn,{
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
	            	return value==0?"":value;   
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
			}, */
			{
				header : "特性UI组件",
				width : 75,
				sortable : true,
				dataIndex : 'uiModelChildItemSet',
				selectOnFocus:true,
	            renderer: function(value,metadata,record){
	            	var index = isSettingType.find('bindId',value);
	            	if(index!=-1){   
	            		return isSettingType.getAt(index).data.name;   
	            	}   
	            	return value==0?"":value;   
	            }
			}, {
				header : "数据来源",
				width : 75,
				sortable : true,
				dataIndex : 'dataFrom',
				selectOnFocus:true,
				/*
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
	            */
	            renderer: function(value,metadata,record){
	            	var index = headerDataFrom.find('bindId',value);
	            	if(index!=-1){   
	            		return headerDataFrom.getAt(index).data.name;   
	            	}   
	            	return value==0?"":value;   
	            }
			},{
				header : "特性数据来源",
				width : 75,
				sortable : true,
				dataIndex : 'dataFromChildItemSet',
				selectOnFocus:true,
	            renderer: function(value,metadata,record){
	            	var index = isSettingType.find('bindId',value);
	            	if(index!=-1){   
	            		return isSettingType.getAt(index).data.name;   
	            	}
	            	return value==0?"":value;   
	            }
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
				}),
                                 renderer: function(value,metadata,record){
                                    return value==0?"":value;   
                                }
			}, {
				header : "高度",
				width : 75,
				sortable : true,
				dataIndex : 'height',
				editor : new Ext.form.NumberField({
					allowBlank : false
				}),
                renderer: function(value,metadata,record){
                    return value==0?"":value;   
                }
			},{
				header : "id",
				width : 75,
				sortable : true,
				dataIndex : 'id'
			}],
			//plugins : expander,
			viewConfig: { 
				columnsText: '显示/隐藏列', 
				sortAscText: '正序排列', 
				sortDescText: '倒序排列', 
				forceFit: true, 
				enableRowBody: true 
			}, 
	        tbar:[{
	            text:'汇总表从子表复制',
	            tooltip:'Add a new row',
	            iconCls:'add',
	            handler:function(button,evn){
	            	if(!canAddItem()){
	            		return;
	            	}
	            	copyFromTableReport(1);
	            }
	        }, '-',{
	            text:'从其他表选择',
	            tooltip:'Add a new row',
	            iconCls:'add',
	            handler:function(button,evn){
	            	if(!canAddItem()){
	            		return;
	            	}
	            	copyFromTableReport(2);
	            }
	        }, '-',{
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
					if(records.length==1){//
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
			        			height:100,
			        			width:100
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
									ids += record.data._id;
								}else{
									ids += record.data._id+",";
								}
							}
							Ext.Ajax.request({
							   url : deleteUrl,
							   method : 'POST',
							   params : {
								   deleteIds:ids,
								   reportid:reportId
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
	        }]
		});
		grid.on("cellclick", function(grid, rowIndex, columnIndex, e) {
			var record = store.getAt(rowIndex);
			var rowBindid = record.get('bindId');
			var rowName = record.get('content');
                        var isLeaf = record.get("_is_leaf");
                        if(!isLeaf){
                            return false;
                        }
			if(columnIndex==7 || columnIndex==8 || columnIndex==9 || columnIndex==10) {
				if (rowBindid == 0) {
					Ext.Msg.alert('提示', '请保存表格中的数据!');
					return false;
				}
				var url;
				var width = 800;
				var height = 410;
				switch (columnIndex){  
				case 7: //UI model 配置信息---通用
					url = content_path+'/reportConfig/headerRowC_uiModelConfigPage.action?headerRowModel.bindId='+rowBindid+'&headerRowModel.reportBindid='+reportId;
					break; 
				case 8: //UI model 配置信息---个性
					width = 1000;
					height = 500;
					url = content_path+'/reportConfig/uiModelConfig_uiModelConfigPage.action?headerRowModel.bindId='+rowBindid+'&headerRowModel.reportBindid='+reportId;
					break;  
				case 9: //数据来源（数据关系）---通用
					width = 1000;
					height = 500;
					url = content_path+'/reportConfig/headerRowC_dataFromConfigPage.action?headerRowModel.bindId='+rowBindid+'&headerRowModel.reportBindid='+reportId;
					break; 
				case 10: //数据来源（数据关系）---个性
					width = 1000;
					height = 500;
					url = content_path+'/reportConfig/dataFromConfig_dataFromConfigPage.action?headerRowModel.bindId='+rowBindid+'&headerRowModel.reportBindid='+reportId;
					break; 
				default: 
					alert("other");
				}

				openUIConfig(rowName,reportId,rowBindid,url,1,height,width);
			}
		});
		
		grid.on("beforeedit", function(arg) {//cancel,column,field,grid,record,row,value
                        var isLeaf = arg.record.data._is_leaf;
                        return isLeaf;
		});
	}
	
	
	
	

	var copyFromChildReport = content_path+"/reportConfig/reportConfig_selectHVPartPage.action?reportConfig.reportLink=";
	var copyFromOthorReport = content_path+"/reportConfig/reportConfig_selectReportPartPage.action?reportConfig.reportLink=";
	
	var copyFromChildReportSave = content_path+'/reportConfig/reportConfig_saveHVPart.do';
	var copyFromOthorReportSave = content_path+'/reportConfig/reportConfig_saveHPart.do';
	
	var reportSaveUrl = "";
	var reportPageUrl = "";
	function copyFromTableReport (type) {  //移动文章到指定栏目的调用方法
		if(type==1){
			reportSaveUrl = copyFromChildReportSave;
			reportPageUrl = copyFromChildReport;
		}else if(type==2){
			reportSaveUrl = copyFromOthorReportSave;
			reportPageUrl = copyFromOthorReport;
		}
		
        var viewWinPanel = createViewWinPanel();
        viewWinPanel.show();
    }
	
	
	function createViewWinPanel() {// 移动文章到指定栏目的窗口
		var currentTreeNode = '';
		var currentPartType = 'h';
		function reloadConfigReport(){
			if(currentTreeNode==''){
				return;
			}
			var selectTableCellPage2 = document.getElementById('selectTableCellPage');
			selectTableCellPage2.src = reportPageUrl+currentTreeNode.id+"&param1="+currentPartType;
		}
		
		function reportTreeWithYear(){
			
			var yearConfigListUtl = content_path+"/reportConfig_json/configYearList.do";
			var reportConfigListUtl = content_path+"/reportConfig_json/configReportListJson.do?reportConfig.rtyear=";
			var Tree = Ext.tree;
			var isfirst = true;
			var currentNode;
			
			var reportTree = new Tree.TreePanel({
				//region : 'west',
				autoScroll : true,
				autoHeight : false,
				animate : true,
				enableDD : true,// 不允许子节点拖动
				containerScroll : true,
				rootVisible : false,
				loader : new Tree.TreeLoader({
					dataUrl : yearConfigListUtl
				}),
				root:new Tree.AsyncTreeNode({
					text : '123',
					draggable : false,
					id : '0'
				}),
				listeners:{
					click:function(node) {
						this.currentNode = node;
						var nodeId = node.id;
						if(nodeId.indexOf('-')>=0){
							currentTreeNode = node;
							reloadConfigReport();
						}else{
							alert('年份');
						}
						//reloadIFRAME();
					},
					beforeload:function(node) {
						if(isfirst){
							reportTree.loader.dataUrl = yearConfigListUtl;
							isfirst = false;
						}else{
							reportTree.loader.dataUrl = reportConfigListUtl+node.id;
						}
					},
					load:function(node) {//加载子节点后，选中子节点的第一个节点
						if(node.childNodes.length!=0){
							currentNode = node.childNodes[0];
							reportTree.selectPath(node.childNodes[0].getPath());
							//reloadIFRAME();
						}
					}
				}
			});
			return reportTree;
		}
		var radiogroup = new Ext.form.RadioGroup({
			fieldLabel : "radioGroup",
			name : 'HorV',
			height : 15,
			items : [ new Ext.form.Radio({
				boxLabel : '纵向表头',
				name : 'HorV',
				inputValue : 'v',
				checked : true
			}), new Ext.form.Radio({
				boxLabel : '横向表头',
				name : 'HorV',
				inputValue : 'h'
			}), new Ext.form.Radio({
				boxLabel : '全表格',
				name : 'HorV',
				inputValue : 'hv'
			}) ],
			listeners : {
				'change' : function(rg, r) {
					currentPartType = r.inputValue;
					reloadConfigReport();//加载数据
				}
			}
		});
		//var reportConfigTree = createReportConfigTree(radiogroup);

		var reporttree2 = reportTreeWithYear();
		var pnWest = new Ext.Panel({
			id : 'pnWest',
			title : '报表列表',
			width : 250,
			heigth : 'auto',
			split : true,// 显示分隔条
			region : 'west',
			collapsible : true,
			items : [ reporttree2 ]
		});
		var pnCenter = new Ext.Panel(
				{
					region : 'center',
					layout : 'anchor',
					items : [
							radiogroup,

							{
								id : 'iframeHtml',
								html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=385 src=></iframe>'
							} ]
				});

		var win = new Ext.Window(
				{
					layout : "border",
					width : 1000,
					height : 500,
					modal : true,
					closeAction : 'hide',
					buttonAlign : "center",
					title : "从下级表选择指标",
					items : [ pnWest, pnCenter ],
					buttons : [
							{
								text : '确定',
								handler : function(button, evn) {
									
									var selectedCell = window.frames["selectTableCellPage"].window.getSelectedCellHVIds();
									var oldReportBindid = reporttree2.currentNode.id;
									
									var records = grid.getSelectionModel().getSelections();
									var parentid = '0';
									if (records.length == 1) {//
										var record = records[0];
										parentid = record.data.bindId;
									}
									var newReportBindid = reportId;
									
									
									var requestConfig = {
										url : content_path+'/reportConfig/reportConfig_saveHPart.do',// 请求的服务器地址
										params : {
											param1 : newReportBindid,
											param2 : oldReportBindid,
											param3 : selectedCell,
											param4 : parentid
										},
										callback : function(options, success,
												response) {// 回调函数
											alert(response.responseText);
											grid.getStore().reload();
										}
									}
									Ext.Ajax.request(requestConfig);// 发送请求
								}
							}, {
								text : '取消',
								handler : function(button, evn) {
									win.hide();
								}
							} ]
				});
		return win;
	}
	
	
	
	

	/*
	function createReportConfigTree(radiogroup){
		var reportConfigTree = new Ext.tree.TreePanel({
			title : 'My Task List',
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
			},
			myReloadDate:function(nodeId){
				alert(radiogroup.dd());
				this.on('beforeload', function(node) {
					//alert("232:"+nodeId);
					this.loader.dataUrl = content_path+'/reportConfig_json/headerConfigFromOtherHVPageTreeDate.do?reportid='+nodeId+'&parentid='+node.id; // 定义子节点的Loader
					
				});//展开树 
				var TreeLoader = this.getLoader(); //得到Ext.tree.TreeLoader
				TreeLoader.dataUrl = content_path+'/reportConfig_json/headerConfigFromOtherHVPageTreeDate.do?reportid='+nodeId+'&parentid='; //更新数据源
				var treeroot = this.getRootNode(); //得到根节点                
				treeroot.reload(); //重新加载根节点
				treeroot.expand(true, false);
			}
		});
		return reportConfigTree;
	}
	*/
	
	
	
	
	
	
	return {
		init : function(reportid,dataTypeid,dataFromid,UIModeleid) {
			createGrid(reportid,dataTypeid,dataFromid,UIModeleid);
		},
		setReportId:function(reportid){
			setReportId(reportid);
		}
	}
}();

