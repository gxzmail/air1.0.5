package sse.ngts.testrobot.app.func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.process.ActionController;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.engine.app.PropConfig;

public class ApplStatusSave {
	private String statusFile = PropConfig.getStatusFile();
	// private String statusTmpFile = ConstValues.outStatusTmpFileName;
	private ArrayList<String> status;
	private static ApplStatusSave instance;

	public static ApplStatusSave getinstance() {
		if (instance == null) {
			instance = new ApplStatusSave();
		}
		return instance;
	}

	private Logger logger = Logger.getLogger(ApplStatusSave.class);

	/**
     * 
     */
	public void clear() {
		status = null;
		File file = new File(statusFile);
		if (file.exists()) {
			file.delete();
		}

	}

	/**
	 * 读取版本执行结果信息
	 * 
	 * @return
	 */
	public Hashtable<String, StatusResult> readStatus() {
		Hashtable<String, StatusResult> status = new Hashtable<String, StatusResult>();
		String line = null;
		try {
			File file = new File(statusFile);

			logger.error("上次执行结果文件:" + statusFile);

			if (!file.exists()) {
				return status;
			}
			BufferedReader in = new BufferedReader(new FileReader(statusFile));
			while ((line = in.readLine()) != null) {
				String[] data = line.split("\\|");

				if (data.length != 7) {
					continue;
				}
				StatusResult result = new StatusResult();
				result.setId(data[0]);
				result.setTradeDate(data[1]);
				result.setResult(data[2]);
				result.setBeginTime(data[3]);
				result.setEndTime(data[4]);
				result.setCostTime(data[5]);
				result.setExecLog(data[6]);

				status.put(result.getId() + result.getTradeDate(), result);
			}

			in.close();

		} catch (FileNotFoundException e) {
			logger.error("文件未找到{0}" + e.getMessage());
		} catch (IOException e) {
			logger.error("IO操作异常{0}" + e.getMessage());
		}

		return status;
	}

	/**
	 * 
	 * @param actionsController
	 */
	public void writStatus(ArrayList<ActionController> actionsController) {
		try {

			File file = new File(statusFile);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(statusFile, false));
			String str = null;
			int i = 0;
			for (i = 0; i < actionsController.size(); i++) {
				ActionController c = actionsController.get(i);
				Boolean refed = c.getCurrentScript().getAttribute(
						ScriptCase.ATTR_REFF_FLAG);
				Boolean result = c.getCurrentScript().getAttribute(
						ScriptCase.ATTR_SUCCESS_FLAG);
				if (c.isCurrentControllerWorking())
					continue;
				/* 保存用例的编号 */
				String id = c.getCurrentScript().getFrmCase().getScriptId();
				String caseDate = c.getCurrentScript().getFrmCase()
						.getTestDate();
				long begintime = c.getBeginTime();
				long costtime = c.getCostTime();
				String log = "null";
				long endtime = c.getEndTime();
				if (refed) {
					if (result) {
						str = id + "|" + caseDate + "|" + 1 + "|" + begintime
								+ "|" + endtime + "|" + costtime + "|" + log;
						out.println(str);
					} else {
						str = id + "|" + caseDate + "|" + 0 + "|" + begintime
								+ "|" + endtime + "|" + costtime + "|" + log;
						out.println(str);
					}
				}
			}
			out.close();

		} catch (FileNotFoundException e) {
			logger.error("文件未找到{0}" + e.getMessage());

		} catch (IOException e) {
			logger.error("IO操作异常{0}" + e.getMessage());
		}
	}

}
