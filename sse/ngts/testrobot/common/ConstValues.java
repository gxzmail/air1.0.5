package sse.ngts.testrobot.common;

import java.io.File;

public class ConstValues {

	/**** �����ֲᲿ�ֳ��� **/
	public final static String logFileName = "TestRobot.log";

	public final static String logName = "sse.ngts.testrobot.TestRobot";

	public final static String CRURRENT_PATH_DIR = System
			.getProperty("user.dir");

	/* AIR �������� */
	public final static String Air_ConfigFileName = "Cfg" + File.separator
			+ "air_config.txt";

	public final static String LOG_FILE_PATH = "logs";

	/* ����ִ������ */
	public final static String Excute_EnvFileName = "Cfg" + File.separator
			+ "air_configs.txt";

	public final static String Default_AIRCYLE_LIST = "Aircyle.txt";

	/* ApplCaseDetails ���еĳ��� */
	public final static String sceneType = "�������";

	public final static String sceneId = "����ID";

	/* ApplCaseScriptImpl�еĳ��� */
	public final static String sheetName = "��������";

	public final static String versions = "_CV01.xls";

	public final static String prefix = "NGTS_AM_AIR_D_";

	public final static String resultDircName = "���Խ��";/* �����ļ�������������ļ�·���� */

	public final static String postfix = "_detais.txt";/* ���������ļ��ĺ�׺ */

	/* ApplCaseFramworkScript�еĳ��� */
	public final static String caseSheetName = "���Խű�";

	/* ApplCaseStepsScriptImpl�еĳ��� */
	public final static String stepSheetName = "���Խű�";

	public final static String stepVersions = "_CV01.xls";

	public final static String stepPrefix = "NGTS_AM_AIR_";

	public final static String stepDircPathName = "�ű�";

	public final static String scriptPathName = "����·��"; /* config�ļ������������ļ������ļ����� */

	public final static String stepPostfix = "_Steps.xls";

	public final static String OutPutStepsSheetName = "����";

	public final static String caseTitle[] = { "�������", "����ID", "�������", "��������",
			"���Ի���", "��Ҫ�Ľ�����", "����", "���ȼ�", "�ű�����", "����/�쳣", "��Ӧ�����ĵ�" };

	public final static String stepTitle[] = { "�ű����", "�ű�����", "�ű�ִ������", "������",
			"ִ�н׶�", "Ԥ�ڽ��", "����", "������", "����״̬", "���ȼ�", "��ע" };

	/* ApplExcecuteScript�еĳ��� */
	public final static String excuteSheetName = "ִ���ֲ�";

	/*** end **/

	/********************* Excute *******************************************/
	/*** ִ�в��ֳ��� **/
/*	public final static String outStatusFileDir = "Output" + File.separator
			+ "AIR_AutoExecute_result" + File.separator;*/

	public final static String outStatusTmpFileName = "Output" + File.separator
			+ "AIR_AutoExecute_result" + File.separator + "result_tmp.txt";

	/* ���ɵ�ִ�н�������� */
	public final static String statsheetName = "ִ�н��";
	public final static String statReuslt = "ͳ�ƽ��";

	public final static String logExecuteFileName = "TestRobotExecute.log";
	public final static String logExecutName = "sse.ngts.testrobot.Execute";

	/* ִ�н���ɹ�ʧ�ܵ�ֵ */
	public final static String executeSuccess = "--AIR--SUCCESS--";
	public final static String executeFailed = "--AIR--FAILURE--";
	public final static String executeManual = "�ֶ�ִ��";
	public final static String executeOmit = "��ִ��";

	public final static String stepsId = "������";
	public final static String scriptId = "�ű����";
	public final static String tradeData = "������";
	public final static String testPhase = "ִ�н׶�";
	public final static String testStatus = "����״̬";

	public final static String manual = "Manual";
	public final static String skip = "N/A";
	public final static int testMaxNum = 4;

	public final static String result1 = "�ȴ�ִ��";
	public final static String result2 = "����ִ��";
	public final static String result3 = "ִ�гɹ�";
	public final static String result4 = "ִ��ʧ��";
	public final static String result5 = "ʧ�����ֶ�";
	public final static String result6 = "��Ҫ�ֶ�ִ��";
	public final static String result7 = "�ֶ�ִ�����";
	public final static String[] title = { "������", "�ű����", "�ű�����", "�ű�ִ������",
			"Ԥ�ڽ��", "������", "����״̬", "ִ�н��", "��ע" };
	public final static int APPL_File = 1;
	public final static int APPL_DIRC = 2;

	/** ���ô��� */

	public static String[] projects = {"ATP", "MTP", "BTP", "DTP", "ITP"};
	
	/**
	 * Ĭ��������Ŀ
	 */
	public static final String DEFAULT_TestProject = "ATP";

	/**
	 * ��Ŀƥ���ַ���
	 */
	public static final String PROJECT_REGEX = "%PROJECT%";

	/**
	 * AIR_CYCLE.TXTĬ������·��
	 */
	public static final String DEFAULT_Cycle = "cfg" + File.separator
			+ "%PROJECT%_AIR_CYCLE.txt";

	/**
	 * ����Դ�����ļ�����
	 */
	public static final String ODBC_CONFIG_FILE = "CFG" + File.separator
			+ "ODBC_config.txt";

	/**
	 * Ezstep����PBU�����ݿ�����
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
	 * testcasesĬ������·��������ϵͳ����������������Ŀ���õ�Ŀ¼��
	 */
	public static final String DEFAULT_CasePath = "testcases" + File.separator
			+ "%PROJECT%";

	/**
	 * ���Կ�ܽű�Ĭ�����ã����Կ����ά����Ա���ƣ������޸�
	 */
	public static final String DEFAULT_Frmwork = "testcases" + File.separator
			+ "AIR��ܽű�" + File.separator
			+ "NGTS_AM_AIR_%PROJECT%_AIR���Կ��_CV01.xls";

	/**
	 * Ĭ���������Ŀ¼
	 */
	public static final String DEFAULT_OutputPath = "output";

	public static final String VERSION_TEST_PLAN_PATH = "plan";

	/**
	 * ִ����������ļ�
	 */
	public static final String DEFAULT_ExecFile = "output" + File.separator
			+ "%PROJECT%_AIR_ִ���ֲ�.xls";
	
	
	public static final String DEFAULT_ReportPath = "output" + File.separator + "AIR_AutoExecute_result";
	
	/**
	 * ���Ա�������ļ�
	 */
/*	public static final String DEFAULT_StatResult = "output" + File.separator
			+ "%PROJECT%_�ű�����ͳ��.xls";*/

	/**
	 * Ĭ�ϲ��Կ��ID
	 */
	public static final String DEFAULT_FrmworkId = "FW01_001_001";

	/*************** Config Key *****************************************/

	public static final String TestCycle_KEY = "appl.TestFile";

	public static final String TestCaseStepsScriptPath_KEY = "appl.TestCaseStepsScriptPath"; /* ���������ļ������·�� */

	public static final String ExecuteFile_KEY = "appl.ExecuteFile";
	public static final String CasePath_KEY = "appl.CasePath"; /* �������ڵ��ļ��е�����·�� */

	public static final String FrmWorkPath_KEY = "appl.FRMWORKFile"; /* ����ļ�������·�� */

	public static final String TestProject_Key = "appl.TestProject";

	public static final String FrmId_KEY = "appl.FrmId";

	public static final String CASETYPE_KEY = "appl.path.";
	
	public static final String TEST_TIMES_KEY = "appl.TestTimes";

	/* δ�������õ� */
	public static final String TestResultPath_KEY = "appl.TestResultPath";
	
	public static final String TestExcutePath_KEY = "appl.ExecuteoutPutPath";
	
	public static final String CaseListFile_KEY = "appl.CaseListFile"; /* �����б��ļ������·�� */

	public static final String sceneListFile_KEY = "appl.sceneListFile";/* �����б��ļ������·�� */

	public static final String CaseScript_KEY = "appl.CaseScript";

}
