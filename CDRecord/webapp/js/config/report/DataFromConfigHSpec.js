//  =======================================================================================

	var grid, record;
	var saveUrl =  content_path+"/reportConfig/dataFromConfig_save.action";
	var dataUrl =  content_path+"/reportConfig_json/dataFromJson.action?headerRowModel.reportBindid="+reportid+"&headerRowModel.bindId="+hbindid;

	var comBoxDataSource =  content_path+"/dictionary_json/listJson.do?type=";

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
        grid.getStore().reload({params:{reportid:reportid}}); 
	}
	function canAddItem(){
		if(reportid==0){
			alert("请先保存表单");
			return false;
		}else{
			return true;
		}
	}
	
	function createGrid() {
	    
		// create the data store
		var record = Ext.data.Record.create([{
			name : 'bindId',
			type : 'int'
		}, {
			name : 'orderIndex',
			type : 'int'
		}, {
			name : 'reportBindid'
		}, {
			name : 'hbindid',
			type : 'int'
		}, {
			name : 'vbindid',
			type : 'int'
		}, {
			name : 'vName'
		}, {
			name : 'dataFromType'
		}, {
			name : 'formula'
		}, {
			name : 'dataTerm'
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
		}]);
		
		store = new Ext.ux.maximgb.tg.AdjacencyListStore({
			
			autoLoad : false,
			reader : new Ext.data.JsonReader({
				id : '_id'
			}, record),
			proxy : new Ext.data.HttpProxy({url:dataUrl}),
			listeners:{
				update : function(dictstore, record, option) {
					if (option == Ext.data.Record.COMMIT) {
						record.data.reportBindid = reportid;
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
		store.load(); 
		
		//数据来源下拉列表数据源
		var headerDataFrom =  new Ext.data.JsonStore({
		      fields: ['name', 'bindId'],
		      root:'data',
		      url:comBoxDataSource+dataFrombindid
		});
		headerDataFrom.load();
		
		var checkColumn = new Ext.grid.CheckColumn({
			header : '允许空',
			dataIndex : 'isEmpty',
			width : 55
		});
		
		// create the Grid
		grid = new Ext.ux.maximgb.tg.EditorGridPanel({
			store : store,
			master_column_id : 'vName',
			renderTo:'VerticalColumnGrid',
			height:350,
			columnLines: true,
			stripeRows : true,
			autoExpandColumn : 'vName',
			title : '数据关系配置',
			clicksToEdit:1,
			plugins : checkColumn,
			columns : [ 
			 {
				id : 'vName',
				header : "列名称",
				width : 160,
				sortable : true,
				dataIndex : 'vName',
				selectOnFocus:true
			}, {
				header : "计算公式",
				width : 75,
				sortable : true,
				dataIndex : 'formula',
				selectOnFocus:true,
				editor : new Ext.form.TextField({
					allowBlank : false
				})
			},{
				header : "数据来源",
				width : 75,
				sortable : true,
				dataIndex : 'dataFromType',
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
				header : "数据项",
				width : 75,
				sortable : true,
				dataIndex : 'dataTerm',
				selectOnFocus:true,
				editor : new Ext.form.TextField({
					allowBlank : false
				})
			}],
			//plugins : expander,
			viewConfig : {
				enableRowBody : true
			},
	        // inline toolbars
	        tbar:[{
	            text:'保存',
	            tooltip:'保存',
	            iconCls:'save',
	            handler:function(button,evn){
	            	var modifRecords = store.getModifiedRecords();
	            	while(modifRecords.length != 0){
	            		modifRecords[0].commit();
	            	}
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
					url = '/reportData/reportConfig/verticalConfigC_uiModelConfigPage.action?verticalColumnModel.bindId='+rowBindid+'&verticalColumnModel.reportid='+reportid;
					break;  
				case 8: //数据来源（计算方式）
					url = '/reportData/reportConfig/verticalConfigC_dataFromConfigPage.action?verticalColumnModel.bindId='+rowBindid+'&verticalColumnModel.reportid='+reportid;
					break;  
				default: 
					alert("other");
				}

				openUIConfig(rowName,reportid,rowBindid,url,1);
			}
		});
	}
