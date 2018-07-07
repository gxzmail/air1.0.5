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
	 * �������ܣ��ж����������Ƿ�һ�� ��������
	 * 
	 * @param caseScript
	 *            �������������ļ� ��������ֵ
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
	 * �������ܣ����������������Case_Script.txt�ļ��� �������룺 ArrayList<ApplCase> in ����Ҫ���������
	 * ��������� String filePath ���� Case_Script.txt�ļ�·�� ��������ֵ�� ��
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
			logger.error("д�ļ�����:" + file.getAbsolutePath());
			// Logger.getLogger(ApplConstValues.logName).
			// log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			// ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ�д�ļ�"+filePath+"ʧ��",
			// "ERROR");

		}
	}

	/**************************************************
	 * �������ܣ����������б�������б��ȡ�����б��������� �������룺 String pathDirc �������������ļ��� Hashtable
	 * pathCfg ���������ļ�����·�� HashSet<String>caseList ���������б�
	 * HashSet<String>sceneList ���������б� ��������ֵ�� ��
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
				logger.info("����Ϊ�գ�" + filePath);
				return false;
			}
			this.caseScript.addAll(caseDetails);

			String caseDetailPath = PropConfig.getTestResultPath() + scene
					+ ConstValues.postfix;

			this.outPutCaseDetails(caseDetails, caseDetailPath);
		}
		if (this.caseScript.isEmpty()) {
			logger.error("�������������ȡʧ��");
			logger.error("ִ���ֲ�����ʧ�� ");
			ExecuteResultDialog.viewError("ִ��ʧ�ܣ��������������ȡʧ�ܣ���", "ERROR");
			return false;
		}
		if (!deteTestEnvr(this.caseScript)) {
			logger.error("����������ͳһ��������");
			logger.error("ִ���ֲ�����ʧ�� ");
			ExecuteResultDialog.viewError("ִ��ʧ�ܣ�����������ͳһ��������", "ERROR");
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
