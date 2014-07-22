package com.kszit.CDReport.util;

/**
 * <p>
 * Title: Constants
 * </p>
 * <p>
 * Description: 常量
 * </p>
 * 
 */
public class Constants {
	/**
	 * prevent instanciation
	 */
	private Constants() {
		// default
	}

	/**
	 * session中保存的登陆用户名
	 */
	public static String LOGIN_USERNAME = "LoginUserName";
	/**
	 * 应用部署的根目录下
	 */
	public static String APP_ROOT_PATH = "";
	/**
	 * 是否為開發模式
	 */
	public static boolean IS_DEVELEP = Boolean
			.parseBoolean(ApplicationConfigReader.getString("config.isdevelep"));
	/**
	 * 是否启用组织机构模块
	 */
	public static boolean USE_DEPARTMENT = Boolean
			.parseBoolean(ApplicationConfigReader
					.getString("config.useSelfDepartment"));
	/**
	 * 组织机构接口实现类
	 */
	public static String DEPARTMENT_INTERFACE = ApplicationConfigReader
			.getString("config.departmentInterface");
	/**
	 * 用户接口实现类
	 */
	public static String USER_INTERFACE = ApplicationConfigReader
			.getString("config.userInterface");
	/**
	 * 角色接口实现类
	 */
	public static String ROLE_INTERFACE = ApplicationConfigReader
			.getString("config.roleInterface");
	/**
	 * 是否启用流程模块
	 */
	public static boolean USE_FLOW = Boolean
			.parseBoolean(ApplicationConfigReader
					.getString("config.useSelfFlow"));
	/**
	 * 应用的名称
	 */
	public static String APP_NAME = ApplicationConfigReader
			.getString("config.appName");
	/**
	 * 应用程序部署的根路径
	 */
	public static String APP_ROOT_DIR = ApplicationConfigReader
			.getString("config.appRoot.dir");

	/**
	 * 系统生成文件的存放路径
	 */
	public static String APP_FOLDER_DIR = ApplicationConfigReader
			.getString("config.appFolder.dir");
	/**
	 * excel模版存放文件夹
	 */
	public static String EXCEL_FOLDER = ApplicationConfigReader
			.getString("config.excel.folder.name");
	/**
	 * 数据文件缓存文件夹
	 */
	public static String DATA_FOLDER = ApplicationConfigReader
			.getString("config.data.folder.name");
	/**
	 * 填报文件缓存文件夹
	 */
	public static String INPUT_FOLDER = ApplicationConfigReader
			.getString("config.input.folder.name");
	/**
	 * 临时文件缓存文件夹
	 */
	public static String TEMP_FOLDER = ApplicationConfigReader
			.getString("config.temp.folder.name");
	/**
	 * 用户上传图片保持路径
	 */
	public static String USER_PICFILE_FOLDER = ApplicationConfigReader
			.getString("config.userpic.folder.name");
	
	/**
	 * 用户上传图片保持路径
	 */
	public static String PAGE_LIST_SIEZ = ApplicationConfigReader
			.getString("config.page.list.size");
	
	/**
	 * 界面
	 */
	public static String PAGE_UI = ApplicationConfigReader
			.getString("config.page.ui");

}
