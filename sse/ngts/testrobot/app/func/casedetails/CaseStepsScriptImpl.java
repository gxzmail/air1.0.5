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
	 * �������ܣ����Ĳ����еĽ����պ������룺ArrayList<ApplFrmwkCase> frmWorkSteps ������������String
	 * tradeDate ���� �����պ����������ͣ� ArrayList<ApplFrmwkCase>
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
	 * �������ܣ���ȡ�����б������еĲ��� �������룺 String path �����ļ�·�� ArrayList<ApplCase>caseList
	 * ���������б� Hashtable pathCfg ���������ļ�����·�� ��������� �� ��������ֵ�� ��
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
			// ����ID
			sceneId = Common.getStringByToken(1, "_", caseDescrip.getCaseId());
			// �����ű���·��
			pathDirc = pathDirc + ConstValues.stepDircPathName + File.separator
					+ sceneId;
			filePath = pathDirc + File.separator + ConstValues.stepPrefix
					+ caseDescrip.getCaseId() + ConstValues.stepVersions;
			logger.info("��ȡ��������:" + filePath);
			try {
				File file = new File(filePath);
				ExcelHndl step = ExcelHndl.getInstance();
				ArrayList<FrameworkCase> frameworkCase = step.readCaseStep(
						ConstValues.stepSheetName, file, 3, caseDescrip);
				if (frameworkCase == null || frameworkCase.size() == 0) {
					logger.error("!!!!!!!! ����ʧ��,�����������κ�����:"
							+ file.getAbsolutePath());
					bReadFlg = false;
				}
				// ÿ�������ļ��Ĳ����ļ�·��
				String caseStepsTxtPath = PropConfig.getTestResultPath()
						+ caseDescrip.getCaseId() + ConstValues.stepPostfix;
				ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
						caseStepsTxtPath, frameworkCase);
				caseSteps.addAll(frameworkCase);
			} catch (Exception e) {
				logger.error("���ļ�ʧ��:" + filePath);

				logger.error("ִ���ֲ�����ʧ�� ");
				ExecuteResultDialog.viewError("ִ��ʧ��,��ȡ�ļ�" + filePath + "ʧ��",
						"ERROR");
				bReadFlg = false;
			}

		}
		setTradeDate(this.caseSteps);
		return bReadFlg;

	}

	@Override
	public Boolean readCaseSheet(ArrayList<CaseManage> caseList) {
		logger.info("####################  ��ȡ��������   ################");
		if (!readCaseListSteps(caseList))
			return false;

		logger.info("####################  д��������      #################");
		ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
				PropConfig.getTestCaseStepsScriptPath(), caseSteps);

		logger.info("####################  ��ȡ���   ################");
		if (!readFrmSheet(PropConfig.getFrmWorkPath(), PropConfig.getFrmId()))
			return false;
		logger.info("####################  д���   ################");
		ExcelHndl.writeExcuteSheet(ConstValues.OutPutStepsSheetName,
				PropConfig.getFrmStepPath(), frmWorkSteps);

		ScriptManage manage = new ScriptManage();

		scriptmanagehash = manage.LoadScrpt();

		/****************************************************
		 * ���������ļ����²���
		 ****************************************************/
		if (!xmlParse.readxml()) {
			logger.info("�������ļ�\"cfg\\AIR_Config.xml\"����");
			return false;
		}

		logger.info("###############  �滻����������ϵͳ����   ####################");
		replaceCaseSteps(caseSteps);

		logger.info("###############  �滻�����ϵͳ����   ####################");
		replaceFrameSteps(frmWorkSteps);
		/*****************************************************
		 * �鿴��ܲ����е����в�������ȼ��Ƿ���ڽű��е����в���� ���ȼ�
		 ******************************************************/
		if (!comparePriority(caseSteps, frmWorkSteps)) {
			logger.error("����ִ��ʧ�ܣ�����в�������ȼ����������ű��в�������ȼ�����");

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
				logger.error("!!!!!!! �������Ϊ��:" + frmWorkPath);
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
			logger.error("���ļ�ʧ��:" + frmWorkPath);
			ExecuteResultDialog.viewError("ִ��ʧ�ܣ����ļ�" + frmWorkPath + "ʧ��",
					"ERROR");
			return false;
		}
		return true;
	}
	

	
	/**
	 * 
	 * ����˵���������滻
	 * 2014-8-6 ����8:39:12
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
					// ���Դ�Сд�滻�ַ���
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
	 * �������ܣ������������������ȡ������ �������룺
	 * 
	 * @param Steps
	 *            �������������� ��������ֵ�� HashSet<String> ��������������
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
