package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.exception.ApplFatalException;

/**
 * ��ʼʱ��鹤������Ŀ¼���ļ��Ƿ����
 * 
 * @author neusoft
 */
public class EnvDetect {
	private static EnvDetect instance;

	public static EnvDetect getinstance() {
		if (instance == null) {
			instance = new EnvDetect();
		}
		return instance;
	}

	private Logger logger = Logger.getLogger(EnvDetect.class);

	public EnvDetect() {

	}

	/**
	 * �����ϴ�ִ�н����¼
	 * 
	 * @return
	 */
	public boolean ClearOutput() {
		boolean bFlg = true;

		String excuteOutputResult = PropConfig.getExecuteLogFile();
		if (excuteOutputResult == null) {
			excuteOutputResult = ConstValues.DEFAULT_ExecFile.replaceAll(
					ConstValues.PROJECT_REGEX, ConstValues.DEFAULT_TestProject);
			logger.warn("ִ���������δ���ã�Ĭ��{0}" + excuteOutputResult);
		}
		File exeResult = new File(excuteOutputResult);
		if (!exeResult.exists())
		{
			try {
				exeResult.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bFlg;
	}

	/**
	 * ��鵱ǰĿ¼�Ƿ����
	 * 
	 * @return
	 */
	public boolean DetectEnv() {
		boolean bFlg = true;
		String currentdir = System.getProperty("user.dir");

		/* ��־Ŀ¼��� */
		File dir = new File(ConstValues.LOG_FILE_PATH);
		if (!dir.isDirectory() || !dir.exists()) {
			logger.info("{0}������" + dir.getAbsolutePath());

			dir.mkdir();
			logger.info("�½�{0}��־Ŀ¼" + ConstValues.LOG_FILE_PATH);

		}
		/*
		LogHndWrite.writeLog(currentdir + File.separator
				+ ConstValues.LOG_FILE_PATH + File.separator
				+ ConstValues.logFileName, ConstValues.logName);
		*/
		/* ��� Cfg\\air_config.txt */
		File file = new File(currentdir + File.separator
				+ ConstValues.Air_ConfigFileName);
		try {
			if (!file.exists()) {
				logger.error("�����ļ�{0}������" + file.getAbsolutePath());
				ExecuteResultDialog.viewError("�����ļ�" + file.getAbsolutePath()
						+ "������", "ERROR");
				throw new ApplFatalException(null, null);
			}
		} catch (ApplFatalException ex) {
			logger.error(ex.getMessage());
			throw new ApplFatalException(null, null);
		}

		ReadAirCfg();

		return bFlg;
	}

	/**
	 * ��ȡ AIR_Config.txt�ļ�,�������Ŀ¼
	 * 
	 * @return
	 */

	private boolean ReadAirCfg() {
		boolean bFlg = true;

		String testResultPath = PropConfig.getTestResultPath();
		if (testResultPath == null) {
			testResultPath = ConstValues.DEFAULT_OutputPath;
			logger.warn("����Ĭ�����Ŀ¼{0}" + testResultPath);
		} else {
			logger.info("�������Ŀ¼{0}" + testResultPath);
		}
		
		String testEcutePath = PropConfig.getExcutePath();
		if(testEcutePath == null || testEcutePath.trim().equals(""))
		{
			testEcutePath = ConstValues.DEFAULT_OutputPath;
			logger.warn("����Ĭ�����Ŀ¼{0}" + testEcutePath);
		}
		else
		{
			logger.info("�������Ŀ¼{0}" + testEcutePath);
		}
		
		File f = new File(testResultPath);
		if (!f.exists())
			f.mkdirs();
		
		File fexcute = new File(testEcutePath);
		if(!fexcute.exists())
		{
			fexcute.mkdirs();
		}
		
		return bFlg;
	}
}
