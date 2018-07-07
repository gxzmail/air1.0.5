package sse.ngts.testrobot.app.func.cycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.Common;

public class CycleHnd {
	private String cfgFileName;/* AIR_CYCLE.TXT文件路径 */
	private Logger logger = Logger.getLogger(CycleHnd.class);
	private HashSet<String> caseList = new HashSet<String>();;
	private HashSet<String> sceneList = new HashSet<String>();

	public static CycleHnd instance = null;

	public static CycleHnd getInstance() {
		if (instance == null) {
			return new CycleHnd();
		}
		return instance;
	}

	public HashSet<String> getCaseList() {
		return caseList;
	}

	public HashSet<String> getSceneList() {
		return sceneList;
	}

	/**
	 * 函数功能：输出用例列表文件 函数输出： filePath －－用例列表文件路径
	 */
	public void outPutCaseListFile(String filePath) {
		Common.fileOutPut(this.caseList, filePath);
	}

	/**
	 * 函数功能：输出场景列表文件 函数输出： filePath －－场景列表文件路径
	 */
	public void outPutSceneListFile(String filePath) {
		Common.fileOutPut(this.sceneList, filePath);
	}

	/**
	 * 函数功能：获取场景列表和用例列表数组 函数输入： filePath －－ AIR_CYCLE.TXT文件路径 函数返回值： 空
	 */
	public boolean readCycleFile(String filePath) {
		this.cfgFileName = filePath;

		File file = new File(cfgFileName);

		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				String caseId = null;
				String sceneId = null;
				while ((tempString = reader.readLine()) != null) {
					/* 2012-06-27 modified by wuli start */
					if (tempString.isEmpty() || !tempString.contains("|"))
						/* 2012-06-27 modified by wuli end */
						continue;
					/* 2012-4-18 add by wuli start */
					if (tempString.trim().startsWith("#"))
						continue;
					/* 2012-4-18 add by wuli end */
					caseId = Common.getStringByToken(1, "|", tempString);
					sceneId = Common.getStringByToken(1, "_", caseId);
					if (caseId == null || sceneId == null)
						continue;
					logger.info("加载用例ID:" + caseId);
					logger.info("加载场景ID:" + sceneId);
					caseList.add(caseId);
					sceneList.add(sceneId);
				}
				if (caseList.isEmpty()) {
					logger.info("caseList为空，请查看测试文件");
					System.exit(0);
				}
				logger.info("读文件成功" + file.getName());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("读文件{0}失败，IO读写错误" + file.getAbsolutePath());
				ExecuteResultDialog.viewError(
						"执行失败,读文件" + file.getAbsolutePath() + "失败", "ERROR");
				return false;
			} catch (Exception e) {
				logger.error("读文件{0}失败" + file.getName());
				ExecuteResultDialog.viewError(
						"执行失败,读文件" + file.getAbsolutePath() + "失败", "ERROR");
				return false;

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}

		} else {
			logger.error("文件不存在" + file.getName());

			logger.error("执行手册生成失败 ");
			ExecuteResultDialog.viewError("执行失败,文件" + file.getAbsolutePath()
					+ "不存在", "ERROR");
			return false;

		}

	}

}
