package com.kszit.CDReport.cor.service.reportData.html;

import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HVConfigUIModelService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.FloatUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;
/**
 * 根据UIModel的类型配置html代码
 * 获得单个数据框的html代码，
 * 
 * 结合横向和纵向的配置类HeaderRowConfigPO、VerticalColumnConfigPO和单个单元格的数据来源配置类：HVConfigDataFromPO，数据UI配置类：HVConfigUIModelPO
 * 
 * 
 * @author Administrator
 *
 */
public class HtmlCodeUtil {

	HeaderRowConfigPO hconfig = null;
	VerticalColumnConfigPO vconfig = null;
	HVConfigDataFromPO cellDataFrom = null;
	HVConfigUIModelPO cellUIModel = null;
	DepartmentToReport dept = null;
	String dataValue = "";
	
	public HtmlCodeUtil(HeaderRowConfigPO hconfig,VerticalColumnConfigPO vconfig,HVConfigDataFromPO cellDataFrom,HVConfigUIModelPO cellUIModel,DepartmentToReport dept){
		this.hconfig = hconfig;
		this.vconfig = vconfig;
		this.cellDataFrom = cellDataFrom;
		this.cellUIModel = cellUIModel;
		this.dept = dept;
	}
	/**
	 * 输入单元格UI html代码
	 * @param is 是否颠倒报表（横行与纵向颠倒）
	 * @param dataValue 
	 * @return
	 */
	public String getInputHtmlCode(boolean is,String dataValue){
		String htmlCode = "";
		int dataFromBindid = getDataFromBindid();
		//判定的参数为页面中的“数据来源”
		switch(dataFromBindid){
		case StaticVaribles.DataFrom_HandInputBindId:
			htmlCode = getInputHtmlCode2(is,dataValue);
			break;
		case StaticVaribles.DataFrom_LowerLevelCell:
			htmlCode = getInputHtmlCode2(is,dataValue);
			break;
		case StaticVaribles.DataFrom_BrothersReportData:
			htmlCode = getInputHtmlCode2(is,dataValue);
			break;
		case StaticVaribles.DataFrom_ServiceTable:
			htmlCode = getInputHtmlCode2(is,dataValue);
			break;
		case 0:
			htmlCode = getInputHtmlCode2(is,dataValue);
			break;
		default:
			htmlCode = getNullCode(dataValue);
		}
		return htmlCode;
	}
        
	/**
	 * 输入单元格UI html代码
	 * @param is 是否颠倒报表（横行与纵向颠倒）
	 * @param dataValue 
	 * @return
	 */
	private String getInputHtmlCode2(boolean is,String dataValue){
		this.dataValue = dataValue;
		String htmlCode = "";

		switch(getUIModelBindid()){
		case StaticVaribles.HTMLUIModule_NullBindid:
			htmlCode = getNullCode(dataValue);
			break;
		case StaticVaribles.HTMLUIModule_InputBindid:
			htmlCode = getInputTextlCode();
			break;
		case StaticVaribles.HTMLUIModule_SelectBindid:
			htmlCode = getSelectCode();
			break;
		case StaticVaribles.HTMLUIModule_DictionaryBindid:
			htmlCode = getInputTextlCode();
			break;
        case StaticVaribles.HTMLUIModule_PopWin:
			htmlCode = getPopWinCode();
			break;
        case StaticVaribles.HTMLUIModule_Hiden:
			htmlCode = getHiddenCode();
			break;   
        case StaticVaribles.HTMLUIModule_Picture:
			htmlCode = getPictureFJCode();
			break;    
		default:
			htmlCode = getNullCode(dataValue);
		}
		return htmlCode;
	}
        
	/**
	 * null 
	 * @return
	 */
	public String getNullCode(String dataValue){
        String alignStyle = "";
        switch (hconfig.getDataType()) {
        case StaticVaribles.DataType_TextBindId:
        	alignStyle = "style='float:left;'";
            break;
        case StaticVaribles.DataType_NumberBindId:
        	alignStyle = "style='float:right;'";
            break;
        case StaticVaribles.DataType_DateBindId:
            
            break;
        case StaticVaribles.DataType_TimeBindId:
        	
            break;
        case StaticVaribles.DataType_DateTimeBindId:
            
            break;
        default:

    }
		
		this.dataValue = dataValue;
		StringBuffer sb = new StringBuffer();
		//sb.append("<input type='hidden' name='"+getInputName()+"' id='"+getInputName()+"' value=''/>"+getDataValue());
		sb.append("<div "+alignStyle+">"+getDataValue()+"</div>");
		return sb.toString();
	}
        
	/**
	 * input 数据框
	 * @return
	 */
	public String getInputTextlCode() {

        StringBuffer javascriptFunction = new StringBuffer();

        if (!hconfig.isIsEmpty()) {
          
            javascriptFunction.append("function " + getValidateFunctionName() + "(o){");
            javascriptFunction.append("var value = o.value;");
            javascriptFunction.append("if(value==''){alert('【" + vconfig.getContent() + "】的【" + hconfig.getContent() + "】不能为空！');//o.focus();\n");
            javascriptFunction.append("o.style.borderColor='red';");
            javascriptFunction.append("return;}");
            javascriptFunction.append("o.style.borderColor='black';return;}");
           
        }
        switch (hconfig.getDataType()) {
            case StaticVaribles.DataType_TextBindId:
                if (hconfig.getDataLength() != 0) {
                    javascriptFunction.append("function " + getValidateFunctionName() + "(o){");
                    javascriptFunction.append("var value = o.value;");
                    javascriptFunction.append("var datalength=" + (int)hconfig.getDataLength() + ";");
                    javascriptFunction.append("if(value.length>datalength){alert('【" + vconfig.getContent() + "】的【" + hconfig.getContent() + "】格的字符个数不能超过'+datalength+'个。');//o.focus();\n");
                    javascriptFunction.append("o.style.borderColor='red';");
                    javascriptFunction.append("return;}");
                    javascriptFunction.append("o.style.borderColor='black';return;}");
                }
                break;
            case StaticVaribles.DataType_NumberBindId:
                if (hconfig.getDataLength() != 0) {
                	float dataLength = hconfig.getDataLength();
                	
                	int decimalsPart = FloatUtil.getDecimalsPart(dataLength);
                	int integerPart = FloatUtil.getIntegerPart(dataLength);
                	
                    javascriptFunction.append("function " + getValidateFunctionName() + "(o){");
                    javascriptFunction.append("var value = o.value;");
                    if(decimalsPart==0){//无小数部分
                    	javascriptFunction.append("var datalength=" + integerPart + ";");
                        javascriptFunction.append("if(!(/^\\d{0," + integerPart + "}$/.test(o.value))){alert('【" + vconfig.getContent() + "】的【" + hconfig.getContent() + "】格的数字长度不能超过'+datalength+'个。');//o.focus();\n");
                    }else if(decimalsPart>0){//包括小数部分
                    	javascriptFunction.append("var datalength=" + (int)hconfig.getDataLength() + ";");
                    	//   /(^(0|[1-9]+)\.\d{0,2}$)|(^(0|[1-9]+)$)/ 
                        javascriptFunction.append("if(!(/(^(0|[1-9]+)\\.\\d{0,"+decimalsPart+"}$)|(^(0|[1-9]+)$)/.test(o.value))){alert('【" + vconfig.getContent() + "】的【" + hconfig.getContent() + "】格的数字长度不能超过'+datalength+'个。');//o.focus();\n");
                    }
                    
                    javascriptFunction.append("o.style.borderColor='red';");
                    javascriptFunction.append("return;}");
                    javascriptFunction.append("o.style.borderColor='black';return;}");
                }
                break;
            case StaticVaribles.DataType_DateBindId:
                javascriptFunction.append("function " + getClickFunctionName() + "(o){");
                javascriptFunction.append("WdatePicker({dateFmt:'yyyy-MM-dd'});");
                javascriptFunction.append("return;}");
                break;
            case StaticVaribles.DataType_TimeBindId:
                javascriptFunction.append("function " + getClickFunctionName() + "(o){");
                javascriptFunction.append("WdatePicker({dateFmt:'HH:mm:ss'});");
                javascriptFunction.append("return;}");
                break;
            case StaticVaribles.DataType_DateTimeBindId:
                javascriptFunction.append("function " + getClickFunctionName() + "(){");
                javascriptFunction.append("WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});");
                javascriptFunction.append("return;}");
                break;
                
                
            default:

        }
		
		
		
                
                StringBuffer sb = new StringBuffer();
                sb.append(inputHtml());
                sb.append("<script type=\"text/javascript\">");
                sb.append(javascriptFunction.toString());
		sb.append("</script>");
		
		return sb.toString();
	}
	
	/**
	 * select 下拉列表
	 * @return
	 */
	public String getSelectCode(){
		String defalValue = getDataValue();
		StringBuffer sb = new StringBuffer();
		sb.append("<select class='"+StaticVaribles.HTMLSelectCSS+"' name='"+getInputName()+"' id='"+getInputName()+"'>");
		HVConfigUIModelPO uiModelPo = new HVConfigUIModelPO(hconfig, null, cellUIModel);
		Map<String,String> keyandValue = uiModelPo.getUIModuleDSMap();
		sb.append("<option value='请选择'>请选择..</option>");
		for(String key:keyandValue.keySet()){
			String value = keyandValue.get(key);
			String selected = "";
			if(!StringUtil.isEmpty(defalValue) && defalValue.equals(key)){
				selected = "selected";
			}
			sb.append("<option value='"+key+"' "+selected+" >"+value+"</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
        
	/**
	 * Dictionary  数据字典
	 * @return
	 */
	public String getDictionaryCode(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+getInputName()+"'>");
		sb.append("<option value='"+"dddd"+"'>ddd</option>");
		sb.append("<option value='"+"dddd2"+"'>ddd2</option>");
		sb.append("</select>");
		return sb.toString();
	}
	/**
	 * 隐藏域
	 * @return
	 */
    public String getHiddenCode(){
        StringBuffer sb  = new StringBuffer();
        String value = getDataValue();
        String hiddenName = getInputName();;
        
        
        
        sb.append("<input type='hidden' name='"+hiddenName+"' id='"+hiddenName+"' value='"+value+"'></intput>");
        sb.append(getNullCode(value));
        return sb.toString();
    }
    
	/**
	 * 附件
	 * @return
	 */
    public String getPictureFJCode(){
        StringBuffer sb  = new StringBuffer();
        String value = getDataValue();
        String hiddenName = getInputName();
        sb.append("<a href='"+value+"' target='blank'><img id='"+hiddenName+"Img' src='"+value+"' width='40' height='40' ></a>");
        sb.append("<input type='hidden' name='"+hiddenName+"' id='"+hiddenName+"' value='"+value+"'></intput>");
        sb.append("<a href='#' onclick='picFileUpload(\""+hiddenName+"\");return false;'>上传</a>");
//        sb.append("<input type='file' name='"+hiddenName+"' id='"+hiddenName+"' value='"+value+"'></intput>");
        return sb.toString();
    }
    
       /**
	 * pop wind  弹出输入框
	 * @return
	 */
	public String getPopWinCode(){
            StringBuffer javascriptFunction = new StringBuffer();
            
                            javascriptFunction.append("function " + getClickFunctionName() + "(o){");
                javascriptFunction.append("var ga_box=new outBox ('box');ga_box.openBox('box boxshaw_r radius_s','mask',true,o);activeElement=o;$('#quit').click(function(){ga_box.quitBox()});");
                javascriptFunction.append("return;}");
                            
		
		StringBuffer sb = new StringBuffer();
                sb.append(inputHtml());
                sb.append("<script type=\"text/javascript\">");
		sb.append(javascriptFunction.toString());
                sb.append("</script>");
		return sb.toString();
	}
	
	/**
	 * 单元格验证函数名称  
	 * @return
	 */
	public String getValidateFunctionName(){
		return "validation"+hconfig.getBindId()+"_"+vconfig.getBindId();
	}
        
	/**
	 * 单元格单击函数名称  
	 * @return
	 */
	public String getClickFunctionName(){
		return "click"+hconfig.getBindId()+"_"+vconfig.getBindId();
	}
        
	/**
	 * 表单的name属性值，提交表单时用到,格式:vid#hid
	 * @return
	 */
	public String getInputName(){
		String name = null;
		  if(dept!=null){
			  name = hconfig.getBindId()+"#"+dept.getBindid();
	        }else{
	        	name = hconfig.getBindId()+"#"+vconfig.getBindId();
	        }
		return name;
	}
        
	/**
	 * 获得值
	 * @return
	 */
	public String getDataValue(){
		if(dataValue!=null && !"".equals(dataValue)){
            switch (hconfig.getDataType()) {
            case StaticVaribles.DataType_NumberBindId:
            	//float dataValueFloat = Float.parseFloat(dataValue);
            	dataValue = FloatUtil.getDecimalFormat(hconfig.getDataLength(),dataValue);
            	break;
            case StaticVaribles.DataType_DateBindId:
                
                break;
            case StaticVaribles.DataType_TimeBindId:
            	
                break;
            case StaticVaribles.DataType_DateTimeBindId:
                
                break;
            default:

            }
			
			return dataValue;
		}else{
			//获得默认值
			return hconfig.getDefaultValue()==null?"":hconfig.getDefaultValue();
		}
	}
        
	/**
	 * 获取单元格的UI的bindid
	 * @return
	 */
	public int getUIModelBindid(){
		HVConfigUIModelPO uiModel = new HVConfigUIModelPO(hconfig, null, cellUIModel);
		return uiModel.getUIModule();
	}
	
	/**
	 * 数据来源（手工录入或者通过计算获得）bindid
	 * @return
	 */
	public int getDataFromBindid(){
		HVConfigDataFromPO dataFromPo = DataFrom.getDataFromPO(hconfig, vconfig, cellDataFrom);
		if(dataFromPo == null){
			return 0;
		}
		return Integer.parseInt(dataFromPo.getDataFromType());
	}
	
	
	private String inputHtml(){
            String InputCss = StaticVaribles.HTMLInputCSS;
            switch (hconfig.getDataType()) {
            case StaticVaribles.DataType_TextBindId:
                InputCss = StaticVaribles.HTMLInputCSSStr;
                break;
            case StaticVaribles.DataType_NumberBindId:
            	InputCss = StaticVaribles.HTMLInputCSSNum;
                break;
            case StaticVaribles.DataType_DateBindId:
                
                break;
            case StaticVaribles.DataType_TimeBindId:
            	
                break;
            case StaticVaribles.DataType_DateTimeBindId:
                
                break;
            default:

        }
            StringBuffer sb  = new StringBuffer();
            sb.append("<input type='text' class='"+InputCss+"' name='"+getInputName()+"' id='"+getInputName()+"' onclick='"+getClickFunctionName()+"(this);' onBlur='"+getValidateFunctionName()+"(this);' value='"+getDataValue()+"'></intput>");
            //div 显示输入框
//            sb.append("<div id='AnswerDiv_215' questionid='215' class='EditableArea' contenteditable='true'></div>");
            return sb.toString();
        }
}
