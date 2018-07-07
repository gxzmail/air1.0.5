/**
 * 项目名称：Demo_AutoExecute2
 * 类名称：PropConfig
 * 类描述：
 * 创建人：neusoft
 * 修改人：neusoft
 * 修改备注：
 * @version
 */
package sse.ngts.testrobot.engine.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.common.Common;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.common.IniOperator;

// TODO: Auto-generated Javadoc
/**
 * The Class PropConfig.
 * 
 * @author neusoft
 */
public class PropConfig {

	/** The logger. */
	private Logger logger = Logger.getLogger(PropConfig.class);

	/**
	 * AIR_CYCLE.TXT文件的输入路径
	 */
	private static String testCycleFile = null;

	/** 场景列表文件的输出路径. */
	private static String testScenceListFile = null;

	/** 框架文件的输入路径. */
	private static String testFrmworkPath = null;

	/** 框架步骤文件的输出路径. */
	private static String frmStepPath = null;

	/** The frm id. */
	private static String frmId = null;

	/** The Test case steps script path. */
	private static String TestCaseStepsScriptPath = null; // 用例步骤文件的输出路径

	/** The Test result path. */
	private static String TestResultPath = null;

	private static String TestExcutePath = null;
	/** The execute file. */
	private static String executeFile = null; // 执行执行手册的输入路径

	/** The exectue out put stat. */
	private static String exectueOutPutStat = null;

	/** The exectue out put result. */
	private static String exectueOutPutResult = null;

	private static String testVersion = null;

	private static String testProject = null;

	private static String genTime = "";

	/**
	 * Gets the case list file.
	 * 
	 * @return the case list file
	 */
	public static String getCaseListFile() {
		return AirConfigHnd.getInstance().getProperty(
				ConstValues.CaseListFile_KEY);
	}

	public static String getGenTime() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

		if (genTime.equals("")) {
			genTime = dateFormatter.format(new Date());
		}

		return genTime;
	}

	/**
	 * Gets the case path.
	 * 
	 * @return the case path
	 */
	public static String getCasePath() {
		return AirConfigHnd.getInstance().getProperty(ConstValues.CasePath_KEY);
	}

	/**
	 * Gets the case path dirc.
	 * 
	 * @param caseId
	 *            the case id
	 * @return the case path dirc
	 */
	public static String getCasePathDirc(String caseId) {
		String scene = null;
		String pathDirc = null;
		scene = Common.getStringByToken(1, "_", caseId).trim().substring(0, 2);
		pathDirc = AirConfigHnd.getInstance().getProperty(
				ConstValues.CasePath_KEY)
				+ AirConfigHnd.getInstance().getCaseType().get(scene);

		return pathDirc;
	}

	/**
	 * Gets the execute file.
	 * 
	 * @return the execute file
	 */
	public static String getExecuteFile() {

		executeFile = AirConfigHnd.getInstance().getProperty(
				ConstValues.ExecuteFile_KEY);

		return executeFile;
	}

	public static String getExecuteLogFile() {
		String logPath = "";
		String project = getProject();
		String outpath = getExcutePath();
		String version = getTestVersion();
		String times = AirConfigHnd.getInstance().getProperty(
				ConstValues.TEST_TIMES_KEY);

		logPath = outpath + project + "_" + version + "_" + times + ".txt";
		return logPath;
	}

	public static String getTestTimes() {
		String times = AirConfigHnd.getInstance().getProperty(
				ConstValues.TEST_TIMES_KEY);
		return times;
	}

	public static String getExecuteAnalyseFile() {
		String anlysePath = "";
		String project = getProject();
		String outpath = getExcutePath();
		String version = getTestVersion();
		String times = AirConfigHnd.getInstance().getProperty(
				ConstValues.TEST_TIMES_KEY);

		anlysePath = outpath + project + "_" + version + "_" + times + ".xls";
		return anlysePath;
	}

	public static String getExcutePath() {
		TestExcutePath = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestExcutePath_KEY);
		return TestExcutePath;
	}

	/**
	 * Gets the frm id.
	 * 
	 * @return the frm id
	 */
	public static String getFrmId() {
		frmId = AirConfigHnd.getInstance().getProperty(ConstValues.FrmId_KEY);
		return frmId;
	}

	/**
	 * Gets the frm step path.
	 * 
	 * @return the frm step path
	 */
	public static String getFrmStepPath() {
		frmStepPath = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestResultPath_KEY)
				+ AirConfigHnd.getInstance().getProperty(ConstValues.FrmId_KEY)
				+ ConstValues.stepPostfix;
		return frmStepPath;
	}

	/**
	 * Gets the frm work path.
	 * 
	 * @return the frm work path
	 */
	public static String getFrmWorkPath() {
		testFrmworkPath = AirConfigHnd.getInstance().getProperty(
				ConstValues.FrmWorkPath_KEY);
		return testFrmworkPath;
	}

	/**
	 * Gets the scence list file.
	 * 
	 * @return the scence list file
	 */
	public static String getScenceListFile() {
		testScenceListFile = AirConfigHnd.getInstance().getProperty(
				ConstValues.sceneListFile_KEY);
		return testScenceListFile;
	}

	/**
	 * Gets the test case script.
	 * 
	 * @return the test case script
	 */
	public static String getTestCaseScript() {

		return AirConfigHnd.getInstance().getProperty(
				ConstValues.CaseScript_KEY);
	}

	/**
	 * Gets the test case steps script path.
	 * 
	 * @return the test case steps script path
	 */
	public static String getTestCaseStepsScriptPath() {
		TestCaseStepsScriptPath = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestCaseStepsScriptPath_KEY);
		return TestCaseStepsScriptPath;
	}

	/**
	 * ***********************************************************
	 * 函数说明：获取Cycle文件地址 创建人: neusoft 创建日期: 2013-9-13 下午5:45:43 参数说明：.
	 * 
	 * @return the test cycle file String
	 *         ************************************************************
	 */
	public static String getTestCycleFile() {
		testCycleFile = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestCycle_KEY);
		return testCycleFile;
	}

	/**
	 * Gets the test result path.
	 * 
	 * @return the test result path
	 */
	public static String getTestResultPath() {
		TestResultPath = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestResultPath_KEY);
		return TestResultPath;
	}

	public static String getTestVersion() {
		IniOperator ini = new IniOperator(ConstValues.Excute_EnvFileName);

		testVersion = ini.getValue("VERSION", "version");
		return testVersion;
	}

	public static String getStatusFile() {
		String project = getProject();
		String outpath = getExcutePath();
		String version = getTestVersion();
		String times = AirConfigHnd.getInstance().getProperty(
				ConstValues.TEST_TIMES_KEY);

		String status = outpath + project + "_" + version + "_" + times
				+ ".dat";
		return status;
	}

	public static String getProject() {
		testProject = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestProject_Key);
		return testProject;
	}

}
