package sse.ngts.testrobot.app.func.casedetails;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.func.cycle.ApplXmlParse;
import sse.ngts.testrobot.app.model.CaseManage;
import sse.ngts.testrobot.app.model.FrameworkCase;
import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.Common;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.script.load.ScriptManage;

public class CaseStepsScriptImpl implements ICaseStepsScript {

	private static Logger logger = Logger.getLogger(CaseStepsScriptImpl.class);
	private ArrayList<FrameworkCase> caseSteps = new ArrayList<FrameworkCase>();
	private ArrayList<FrameworkCase> frmWorkSteps = new ArrayList<FrameworkCase>();
	private HashSet<String> tradeDate = new HashSet<String>();

	private Hashtable<String, Integer> tradePhase = new Hashtable<String, Integer>();
	private ApplXmlParse xmlParse = ApplXmlParse.getinstance();

	private Hashtable<String, String> scriptmanagehash = null;

	/*
	 * 函数功能：更改步骤中的交易日函数输入：ArrayList<ApplFrmwkCase> frmWorkSteps －－步骤数组String
	 * tradeDate －－ 交易日函数返回类型： ArrayList<ApplFrmwkCase>
	 */
	private ArrayList<FrameworkCase> chgSteps(
			ArrayList<FrameworkCase> frmWorkSteps, String tradeDate) {

		ArrayList<FrameworkCase> fSteps = new ArrayList<FrameworkCase>();
		Iterator<FrameworkCase> c = frmWorkSteps.iterator();
		while (c.hasNext()) {

			FrameworkCase d = new FrameworkCase();
			d = c.next().clone();
			d.setTestDate(tradeDate);
			fSteps.add(d);
		}
		return fSteps;
	}

	private Boolean comparePriority(ArrayList<FrameworkCase> steps1,
			ArrayList<FrameworkCase> steps2) {
		String str1 = getMaxPriority(steps1);
		String str2 = getMaxPriority(steps2);
		if (str1.compareToIgnoreCase(str2) > 0)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<FrameworkCase> getCaseSteps() {
		return caseSteps;
	}

	private String getMaxPriority(ArrayList<FrameworkCase> steps) {
		Iterator<FrameworkCase> c = steps.iterator();
		String testPrior = null;
		String maxPrior = null;
		while (c.hasNext()) {
			testPrior = c.next().getTestPrior();

			if (maxPrior == null)
				maxPrior = testPrior;
			else if (maxPrior.compareToIgnoreCase(testPrior) > 0)
				maxPrior = testPrior;
		}
		return maxPrior;
	}

	@Override
	public Hashtable<String, Integer> getTradePhase() {
		return tradePhase;
	}

	/*********************************************
	 * 函数功能：获取用例列表中所有的步骤 函数输入： String path －－文件路径 ArrayList<ApplCase>caseList
	 * －－用例列表 Hashtable pathCfg －－用例文件配置路径 函数输出： 空 函数返回值： 空
	 *********************************************/
	public Boolean readCaseListSteps(ArrayList<CaseManage> caseList) {

		boolean bReadFlg = true;

		Iterator<CaseManage> c = caseList.iterator();

		String pathDirc = null;
		String filePath = null;
		while (c.hasNext()) {
			CaseManage caseDescrip = new CaseManage();
			caseDescrip = c.next();
			String sceneId = null;

			pathDirc = PropConfig.getCasePathDirc(caseDescrip.getCaseId());
			// 场景ID
			sceneId = Common.getStringByToken(1, "_", caseDescrip.getCaseId());
			// 用例脚本的路径
			pathDirc = pathDirc + ConstValues.stepDircPathName + File.separator
					+ sceneId;
			filePath = pathDirc + File.separator + ConstValues.stepPrefix
					+ caseDescrip.getCaseId() + ConstValues.stepVersions;
			logger.info("读取测试用例:" + filePath);
			try {
				File file = new File(filePath);
				ExcelHndl step = ExcelHndl.getInstance();
				ArrayList<FrameworkCase> frameworkCase = step.readCaseStep(
						ConstValues.stepSheetName, file, 3, caseDescrip);
				if (frameworkCase == null || frameworkCase.size() == 0) {
					logger.error("!!!!!!!! 处理失败,此用例中无任何用例:"
							+ file.getAbsolutePath());
					bReadFlg = false;
				}
				// 每个用例文件的步骤文件路径
				String caseStepsTxtPath = PropConfig.getTestResultPath()
						+ caseDescrip.getCaseId() + ConstValues.stepPostfix;
				ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
						caseStepsTxtPath, frameworkCase);
				caseSteps.addAll(frameworkCase);
			} catch (Exception e) {
				logger.error("读文件失败:" + filePath);

				logger.error("执行手册生成失败 ");
				ExecuteResultDialog.viewError("执行失败,读取文件" + filePath + "失败",
						"ERROR");
				bReadFlg = false;
			}

		}
		setTradeDate(this.caseSteps);
		return bReadFlg;

	}

	@Override
	public Boolean readCaseSheet(ArrayList<CaseManage> caseList) {
		logger.info("####################  读取测试用例   ################");
		if (!readCaseListSteps(caseList))
			return false;

		logger.info("####################  写测试用例      #################");
		ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
				PropConfig.getTestCaseStepsScriptPath(), caseSteps);

		logger.info("####################  读取框架   ################");
		if (!readFrmSheet(PropConfig.getFrmWorkPath(), PropConfig.getFrmId()))
			return false;
		logger.info("####################  写框架   ################");
		ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
				PropConfig.getFrmStepPath(), frmWorkSteps);

		ScriptManage manage = new ScriptManage();

		scriptmanagehash = manage.LoadScrpt();

		/****************************************************
		 * 根据配置文件更新步骤
		 ****************************************************/
		if (!xmlParse.readxml()) {
			logger.info("打开配置文件\"cfg\\AIR_Config.xml\"出错");
			return false;
		}

		logger.info("###############  替换测试用例中系统变量   ####################");
		replaceCaseSteps(caseSteps);

		logger.info("###############  替换框架中系统变量   ####################");
		replaceFrameSteps(frmWorkSteps);
		/*****************************************************
		 * 查看框架步骤中的所有步骤的优先级是否高于脚本中的所有步骤的 优先级
		 ******************************************************/
		if (!comparePriority(caseSteps, frmWorkSteps)) {
			logger.error("程序执行失败，框架中步骤的优先级低于用例脚本中步骤的优先级！！");

			return false;

		}
		caseSteps.addAll(frmWorkSteps);
		return true;
	}

	public Boolean readFrmSheet(String frmWorkPath, String frameWorkId) {
		try {

			File file = new File(frmWorkPath);
			CaseManage a = new CaseManage();
			a.setCaseId(frameWorkId);

			ExcelHndl frmSteps = ExcelHndl.getInstance();
			ArrayList<FrameworkCase> frameworkCase = frmSteps.readCaseStep(
					ConstValues.caseSheetName, file, 3, a);
			if (frameworkCase == null || frameworkCase.size() == 0) {
				logger.error("!!!!!!! 框架用例为空:" + frmWorkPath);
				return false;
			}
			setTradePhase(frameworkCase);
			Iterator<String> c = tradeDate.iterator();
			String date;
			Iterator<FrameworkCase> b = frameworkCase.iterator();
			while (b.hasNext()) {
				b.next();
			}
			while (c.hasNext()) {
				date = c.next().toString();
				this.frmWorkSteps.addAll(chgSteps(frameworkCase, date));
			}
		} catch (Exception e) {
			logger.error("读文件失败:" + frmWorkPath);
			ExecuteResultDialog.viewError("执行失败，读文件" + frmWorkPath + "失败",
					"ERROR");
			return false;
		}
		return true;
	}
	

	
	/**
	 * 
	 * 函数说明：变量替换
	 * 2014-8-6 上午8:39:12
	 * @param steps
	 */
	private void replaceCaseSteps(ArrayList<FrameworkCase> steps) {
		Iterator<FrameworkCase> iter = steps.iterator();
		Hashtable<String, String> xmlP = new Hashtable<String, String>();
		xmlP = xmlParse.getXmlParse();
		String regex = "%T[\\+|-][0-9]%";
		Pattern pattern = Pattern.compile(regex);
		
		while (iter.hasNext()) {
			FrameworkCase elem = iter.next();
			String s = null;

			for (String key : xmlP.keySet()) {
				System.out.println("Key:"+key + ", " + xmlP.get(key).trim());
				if (elem.getTestContent().indexOf(key) != -1) {
					System.out.println("Content:" + elem.getTestContent());
					String casekey = key.replace("+", "\\+");
					s = elem.getTestContent().replaceAll("%" + casekey + "%",
							xmlP.get(key).trim());
					System.out.println("Replace:" + s);
					elem.setTestContent(s);
				}
			}

			for (Entry<String, String> entry : scriptmanagehash.entrySet()) {

				if (elem.getTestContent().toUpperCase()
						.contains(entry.getKey())) {
					// 忽略大小写替换字符串
					s = elem.getTestContent().replaceAll(
							"(?i)" + entry.getKey(), entry.getValue());
					elem.setTestContent(s);
					break;
				}

			}

		}
	}

	private void replaceFrameSteps(ArrayList<FrameworkCase> steps) {
		Iterator<FrameworkCase> iter = steps.iterator();
		while (iter.hasNext()) {
			FrameworkCase elem = iter.next();
			String s = null;
			/*
			String regex = "%T[+|-][0-9]%";
			String date = elem.getTestDate().trim();
			if(Pattern.matches(regex, date))
			{
				s = elem.getTestContent().replaceAll(date,
						date);
				elem.setTestContent(s);
			}
			*/
			for (int i = 1; i < 6; i++) {
				if (elem.getTestDate().trim().equals("T" + i)) {
					for (int j = 6; j >= 0; j--) {
						int k = j + i;
						s = elem.getTestContent().replaceAll("%T" + j + "%",
								"%T" + k + "%");
						elem.setTestContent(s);
					}
				}
			}
			
		}
		replaceCaseSteps(steps);
	}

	/***********************************************
	 * 函数功能：根据用例步骤数组获取交易日 函数输入：
	 * 
	 * @param Steps
	 *            －－交易日数组 函数返回值： HashSet<String> －－交易日数组
	 *************************************************/
	private void setTradeDate(ArrayList<FrameworkCase> Steps) {
		Iterator<FrameworkCase> c = Steps.iterator();

		while (c.hasNext()) {
			this.tradeDate.add(c.next().getTestDate());
		}
	}

	public void setTradePhase(ArrayList<FrameworkCase> e) {
		Iterator<FrameworkCase> c = e.iterator();
		String str = new String();
		ArrayList<String> str3 = new ArrayList<String>();

		int i = 0;
		while (c.hasNext()) {
			str = c.next().getTestPhase().trim();
			if (str3.contains(str))
				continue;
			else {
				str3.add(str);
				tradePhase.put(str, i);
				i++;
			}
		}
	}

}
