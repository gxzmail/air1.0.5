package sse.ngts.testrobot.common;

import java.io.File;

public class ConstValues {

	/**** 生成手册部分常量 **/
	public final static String logFileName = "TestRobot.log";

	public final static String logName = "sse.ngts.testrobot.TestRobot";

	public final static String CRURRENT_PATH_DIR = System
			.getProperty("user.dir");

	/* AIR 启动配置 */
	public final static String Air_ConfigFileName = "Cfg" + File.separator
			+ "air_config.txt";

	public final static String LOG_FILE_PATH = "logs";

	/* 测试执行配置 */
	public final static String Excute_EnvFileName = "Cfg" + File.separator
			+ "air_configs.txt";

	public final static String Default_AIRCYLE_LIST = "Aircyle.txt";

	/* ApplCaseDetails 类中的常量 */
	public final static String sceneType = "场景类别";

	public final static String sceneId = "场景ID";

	/* ApplCaseScriptImpl中的常量 */
	public final static String sheetName = "测试用例";

	public final static String versions = "_CV01.xls";

	public final static String prefix = "NGTS_AM_AIR_D_";

	public final static String resultDircName = "测试结果";/* 配置文件中用例详情的文件路径名 */

	public final static String postfix = "_detais.txt";/* 用例详情文件的后缀 */

	/* ApplCaseFramworkScript中的常量 */
	public final static String caseSheetName = "测试脚本";

	/* ApplCaseStepsScriptImpl中的常量 */
	public final static String stepSheetName = "测试脚本";

	public final static String stepVersions = "_CV01.xls";

	public final static String stepPrefix = "NGTS_AM_AIR_";

	public final static String stepDircPathName = "脚本";

	public final static String scriptPathName = "步骤路径"; /* config文件中用例步骤文件所在文件夹名 */

	public final static String stepPostfix = "_Steps.xls";

	public final static String OutPutStepsSheetName = "步骤";

	public final static String caseTitle[] = { "场景类别", "场景ID", "用例编号", "用例描述",
			"测试环境", "需要的交易日", "主机", "优先级", "脚本链接", "正常/异常", "对应需求文档" };

	public final static String stepTitle[] = { "脚本编号", "脚本描述", "脚本执行内容", "交易日",
			"执行阶段", "预期结果", "主机", "批步骤", "错误状态", "优先级", "备注" };

	/* ApplExcecuteScript中的常量 */
	public final static String excuteSheetName = "执行手册";

	/*** end **/

	/********************* Excute *******************************************/
	/*** 执行部分常量 **/
/*	public final static String outStatusFileDir = "Output" + File.separator
			+ "AIR_AutoExecute_result" + File.separator;*/

	public final static String outStatusTmpFileName = "Output" + File.separator
			+ "AIR_AutoExecute_result" + File.separator + "result_tmp.txt";

	/* 生成的执行结果表单名称 */
	public final static String statsheetName = "执行结果";
	public final static String statReuslt = "统计结果";

	public final static String logExecuteFileName = "TestRobotExecute.log";
	public final static String logExecutName = "sse.ngts.testrobot.Execute";

	/* 执行结果成功失败的值 */
	public final static String executeSuccess = "--AIR--SUCCESS--";
	public final static String executeFailed = "--AIR--FAILURE--";
	public final static String executeManual = "手动执行";
	public final static String executeOmit = "不执行";

	public final static String stepsId = "步骤编号";
	public final static String scriptId = "脚本编号";
	public final static String tradeData = "交易日";
	public final static String testPhase = "执行阶段";
	public final static String testStatus = "错误状态";

	public final static String manual = "Manual";
	public final static String skip = "N/A";
	public final static int testMaxNum = 4;

	public final static String result1 = "等待执行";
	public final static String result2 = "正在执行";
	public final static String result3 = "执行成功";
	public final static String result4 = "执行失败";
	public final static String result5 = "失败需手动";
	public final static String result6 = "需要手动执行";
	public final static String result7 = "手动执行完成";
	public final static String[] title = { "步骤编号", "脚本编号", "脚本描述", "脚本执行内容",
			"预期结果", "批处理", "错误状态", "执行结果", "备注" };
	public final static int APPL_File = 1;
	public final static int APPL_DIRC = 2;

	/** 配置窗口 */

	public static String[] projects = {"ATP", "MTP", "BTP", "DTP", "ITP"};
	
	/**
	 * 默认配置项目
	 */
	public static final String DEFAULT_TestProject = "ATP";

	/**
	 * 项目匹配字符串
	 */
	public static final String PROJECT_REGEX = "%PROJECT%";

	/**
	 * AIR_CYCLE.TXT默认配置路径
	 */
	public static final String DEFAULT_Cycle = "cfg" + File.separator
			+ "%PROJECT%_AIR_CYCLE.txt";

	/**
	 * 数据源配置文件操作
	 */
	public static final String ODBC_CONFIG_FILE = "CFG" + File.separator
			+ "ODBC_config.txt";

	/**
	 * Ezstep关于PBU和数据库配置
	 */
	public static final String EZSTEP_PBU_CONFIG = "Tools" + File.separator
			+ "EzSTEP" + File.separator + "cfg" + File.separator
			+ "EzSTEPUser.ini";

	/**
	 * security.dat
	 */
	public static final String EZSTEP_SECURITY_CONFIG = "Tools"
			+ File.separator + "EzSTEP" + File.separator + "cfg"
			+ File.separator + "security.dat";

	public static final String IMPEXP_FILE_CONFIG = "imp_exp_cfg.dat";

	/**
	 * testcases默认配置路径，测试系统用例放在其下以项目配置的目录下
	 */
	public static final String DEFAULT_CasePath = "testcases" + File.separator
			+ "%PROJECT%";

	/**
	 * 测试框架脚本默认配置，测试框架由维护人员控制，不可修改
	 */
	public static final String DEFAULT_Frmwork = "testcases" + File.separator
			+ "AIR框架脚本" + File.separator
			+ "NGTS_AM_AIR_%PROJECT%_AIR测试框架_CV01.xls";

	/**
	 * 默认配置输出目录
	 */
	public static final String DEFAULT_OutputPath = "output";

	public static final String VERSION_TEST_PLAN_PATH = "plan";

	/**
	 * 执行用例输出文件
	 */
	public static final String DEFAULT_ExecFile = "output" + File.separator
			+ "%PROJECT%_AIR_执行手册.xls";
	
	
	public static final String DEFAULT_ReportPath = "output" + File.separator + "AIR_AutoExecute_result";
	
	/**
	 * 测试报告输出文件
	 */
/*	public static final String DEFAULT_StatResult = "output" + File.separator
			+ "%PROJECT%_脚本测试统计.xls";*/

	/**
	 * 默认测试框架ID
	 */
	public static final String DEFAULT_FrmworkId = "FW01_001_001";

	/*************** Config Key *****************************************/

	public static final String TestCycle_KEY = "appl.TestFile";

	public static final String TestCaseStepsScriptPath_KEY = "appl.TestCaseStepsScriptPath"; /* 用例步骤文件的输出路径 */

	public static final String ExecuteFile_KEY = "appl.ExecuteFile";
	public static final String CasePath_KEY = "appl.CasePath"; /* 用例所在的文件夹的输入路径 */

	public static final String FrmWorkPath_KEY = "appl.FRMWORKFile"; /* 框架文件的输入路径 */

	public static final String TestProject_Key = "appl.TestProject";

	public static final String FrmId_KEY = "appl.FrmId";

	public static final String CASETYPE_KEY = "appl.path.";
	
	public static final String TEST_TIMES_KEY = "appl.TestTimes";

	/* 未进行设置的 */
	public static final String TestResultPath_KEY = "appl.TestResultPath";
	
	public static final String TestExcutePath_KEY = "appl.ExecuteoutPutPath";
	
	public static final String CaseListFile_KEY = "appl.CaseListFile"; /* 用例列表文件的输出路径 */

	public static final String sceneListFile_KEY = "appl.sceneListFile";/* 场景列表文件的输出路径 */

	public static final String CaseScript_KEY = "appl.CaseScript";

}
