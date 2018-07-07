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
	private String cfgFileName;/* AIR_CYCLE.TXT�ļ�·�� */
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
	 * �������ܣ���������б��ļ� ��������� filePath ���������б��ļ�·��
	 */
	public void outPutCaseListFile(String filePath) {
		Common.fileOutPut(this.caseList, filePath);
	}

	/**
	 * �������ܣ���������б��ļ� ��������� filePath ���������б��ļ�·��
	 */
	public void outPutSceneListFile(String filePath) {
		Common.fileOutPut(this.sceneList, filePath);
	}

	/**
	 * �������ܣ���ȡ�����б�������б����� �������룺 filePath ���� AIR_CYCLE.TXT�ļ�·�� ��������ֵ�� ��
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
					logger.info("��������ID:" + caseId);
					logger.info("���س���ID:" + sceneId);
					caseList.add(caseId);
					sceneList.add(sceneId);
				}
				if (caseList.isEmpty()) {
					logger.info("caseListΪ�գ���鿴�����ļ�");
					System.exit(0);
				}
				logger.info("���ļ��ɹ�" + file.getName());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("���ļ�{0}ʧ�ܣ�IO��д����" + file.getAbsolutePath());
				ExecuteResultDialog.viewError(
						"ִ��ʧ��,���ļ�" + file.getAbsolutePath() + "ʧ��", "ERROR");
				return false;
			} catch (Exception e) {
				logger.error("���ļ�{0}ʧ��" + file.getName());
				ExecuteResultDialog.viewError(
						"ִ��ʧ��,���ļ�" + file.getAbsolutePath() + "ʧ��", "ERROR");
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
			logger.error("�ļ�������" + file.getName());

			logger.error("ִ���ֲ�����ʧ�� ");
			ExecuteResultDialog.viewError("ִ��ʧ��,�ļ�" + file.getAbsolutePath()
					+ "������", "ERROR");
			return false;

		}

	}

}
