package sse.ngts.testrobot.app.func.casedetails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.model.CaseManage;
import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.PropConfig;

public class CaseScriptImpl implements ICaseScript {
	private static Logger logger = Logger.getLogger(CaseScriptImpl.class);
	private ArrayList<CaseManage> caseScript = new ArrayList<CaseManage>();

	/**
	 * 函数功能：判断用例环境是否一致 函数输入
	 * 
	 * @param caseScript
	 *            －－用例详情文件 函数返回值
	 * @return Boolean
	 */
	private Boolean deteTestEnvr(ArrayList<CaseManage> caseScript) {
		Iterator<CaseManage> c = caseScript.iterator();
		HashSet<String> a = new HashSet<String>();
		while (c.hasNext()) {
			a.add(c.next().getTestEnvr().trim());
		}
		if (a.size() == 1)
			return true;
		return false;
	}

	@Override
	public ArrayList<CaseManage> getCaseScript() {
		return caseScript;
	}

	/*************************************************
	 * 函数功能：把用例详情输出到Case_Script.txt文件中 函数输入： ArrayList<ApplCase> in －－要输出的数组
	 * 函数输出： String filePath －－ Case_Script.txt文件路径 函数返回值： 空
	 ****************************************************/
	@Override
	public void outPutCaseDetails(ArrayList<CaseManage> in, String filePath) {

		File file = new File(filePath);

		try {
			if (!file.exists())
				file.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(filePath));
			Iterator<CaseManage> iter = in.iterator();
			CaseManage outPutString = new CaseManage();
			int i = 0;
			while (iter.hasNext()) {
				outPutString = iter.next();
				out.println(String.valueOf(i++) + "|"
						+ outPutString.getCaseId() + "|"
						+ outPutString.getTestEnvr() + "|"
						+ outPutString.getTesthost() + "|"
						+ outPutString.getTestNecessDoc());
			}
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
			logger.error("写文件出错:" + file.getAbsolutePath());
			// Logger.getLogger(ApplConstValues.logName).
			// log(Level.SEVERE, "执行手册生成失败 ");
			// ApplExecuteResultDialog.viewError("执行失败，写文件"+filePath+"失败",
			// "ERROR");

		}
	}

	/**************************************************
	 * 函数功能：根据用例列表和详情列表获取用例列表详情数据 函数输入： String pathDirc －－用例所在文件夹 Hashtable
	 * pathCfg －－所有文件配置路径 HashSet<String>caseList －－用例列表
	 * HashSet<String>sceneList －－场景列表 函数返回值： 空
	 ****************************************************/
	@Override
	public Boolean readCaseDetails(String pathDirc, HashSet<String> caseList,
			HashSet<String> sceneList) {
		Iterator<String> b = sceneList.iterator();
		String scene = new String();

		while (b.hasNext()) {
			scene = b.next();
			ExcelHndl caseSheet = ExcelHndl.getInstance();
			String filePath = PropConfig.getCasePathDirc(scene)
					+ ConstValues.prefix + scene + ConstValues.versions;
			File file = new File(filePath);
			ArrayList<CaseManage> caseDetails = caseSheet.readCaseSheet(file,
					ConstValues.sheetName, 3, caseList, scene);
			if (caseDetails == null || caseDetails.size() == 0) {
				logger.info("用例为空：" + filePath);
				return false;
			}
			this.caseScript.addAll(caseDetails);

			String caseDetailPath = PropConfig.getTestResultPath() + scene
					+ ConstValues.postfix;

			this.outPutCaseDetails(caseDetails, caseDetailPath);
		}
		if (this.caseScript.isEmpty()) {
			logger.error("不存在用例或读取失败");
			logger.error("执行手册生成失败 ");
			ExecuteResultDialog.viewError("执行失败，不存在用例或读取失败！！", "ERROR");
			return false;
		}
		if (!deteTestEnvr(this.caseScript)) {
			logger.error("用例环境不统一，出错！！");
			logger.error("执行手册生成失败 ");
			ExecuteResultDialog.viewError("执行失败，用例环境不统一，出错！！", "ERROR");
			return false;
		}
		Collections.sort(caseScript, new Comparator<CaseManage>() {
			@Override
			public int compare(CaseManage obj1, CaseManage obj2) {

				if (obj1.getCaseId().compareToIgnoreCase(obj2.getCaseId()) >= 1)
					return 1;
				else
					return 0;

			}
		});
		return true;

	}
}
