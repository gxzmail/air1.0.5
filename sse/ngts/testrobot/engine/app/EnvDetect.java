package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.exception.ApplFatalException;

/**
 * 初始时检查工具配置目录或文件是否存在
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
	 * 清理上次执行结果记录
	 * 
	 * @return
	 */
	public boolean ClearOutput() {
		boolean bFlg = true;

		String excuteOutputResult = PropConfig.getExecuteLogFile();
		if (excuteOutputResult == null) {
			excuteOutputResult = ConstValues.DEFAULT_ExecFile.replaceAll(
					ConstValues.PROJECT_REGEX, ConstValues.DEFAULT_TestProject);
			logger.warn("执行用例输出未配置，默认{0}" + excuteOutputResult);
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
	 * 检查当前目录是否存在
	 * 
	 * @return
	 */
	public boolean DetectEnv() {
		boolean bFlg = true;
		String currentdir = System.getProperty("user.dir");

		/* 日志目录检查 */
		File dir = new File(ConstValues.LOG_FILE_PATH);
		if (!dir.isDirectory() || !dir.exists()) {
			logger.info("{0}不存在" + dir.getAbsolutePath());

			dir.mkdir();
			logger.info("新建{0}日志目录" + ConstValues.LOG_FILE_PATH);

		}
		/*
		LogHndWrite.writeLog(currentdir + File.separator
				+ ConstValues.LOG_FILE_PATH + File.separator
				+ ConstValues.logFileName, ConstValues.logName);
		*/
		/* 检查 Cfg\\air_config.txt */
		File file = new File(currentdir + File.separator
				+ ConstValues.Air_ConfigFileName);
		try {
			if (!file.exists()) {
				logger.error("配置文件{0}不存在" + file.getAbsolutePath());
				ExecuteResultDialog.viewError("配置文件" + file.getAbsolutePath()
						+ "不存在", "ERROR");
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
	 * 读取 AIR_Config.txt文件,创建输出目录
	 * 
	 * @return
	 */

	private boolean ReadAirCfg() {
		boolean bFlg = true;

		String testResultPath = PropConfig.getTestResultPath();
		if (testResultPath == null) {
			testResultPath = ConstValues.DEFAULT_OutputPath;
			logger.warn("创建默认输出目录{0}" + testResultPath);
		} else {
			logger.info("创建输出目录{0}" + testResultPath);
		}
		
		String testEcutePath = PropConfig.getExcutePath();
		if(testEcutePath == null || testEcutePath.trim().equals(""))
		{
			testEcutePath = ConstValues.DEFAULT_OutputPath;
			logger.warn("创建默认输出目录{0}" + testEcutePath);
		}
		else
		{
			logger.info("创建输出目录{0}" + testEcutePath);
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
