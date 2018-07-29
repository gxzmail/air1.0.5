package sse.ngts.testrobot.app.func.casedetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import sse.ngts.testrobot.app.func.cycle.ApplXmlParse;
import sse.ngts.testrobot.app.func.cycle.CycleHnd;
import sse.ngts.testrobot.app.model.CaseManage;
import sse.ngts.testrobot.app.model.FrameworkCase;
import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.Common;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.AirConfigHnd;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.factory.DAOFactory;
import sse.ngts.testrobot.script.load.ScriptManage;

public class ExcelHndl {
	private static Logger logger = Logger.getLogger(ExcelHndl.class);

	private static Hashtable<String, String> scriptmanagehash = null;
	private static ExcelHndl instance = null;
	private static boolean hasCreat = false;

	/**
	 * ����˵���� ��̬ʵ�� ������: neusoft ��������: 2013-9-13 ����8:47:52 ����˵����
	 *
	 * @return ExcelHndl
	 */
	public static ExcelHndl getInstance() {
		if (instance == null) {
			instance = new ExcelHndl();
		}
		return instance;
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����8:51:16 ����˵����
	 *
	 * @param workbook
	 * @return HSSFCellStyle
	 */
	private static HSSFCellStyle setCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = setTitleStyle(workbook);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setDataFormat((short) 0);
		return cellStyle;
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����8:51:34 ����˵����
	 *
	 * @param workbook
	 * @return HSSFCellStyle
	 */
	private static HSSFCellStyle setTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom((short) 2);
		titleStyle.setBorderLeft((short) 2);
		titleStyle.setBorderRight((short) 2);
		titleStyle.setBorderTop((short) 2);
		return titleStyle;
	}

	/*****************************************************************************************/

	/**
	 * ����֯�õĲ�������д��Excel
	 *
	 * @param row
	 * @param step
	 * @param cellStyle
	 * @return
	 */

	@SuppressWarnings("deprecation")
	private static HSSFRow writeCells(HSSFRow row, FrameworkCase step,
			HSSFCellStyle cellStyle) {
		int z = 0;
		HSSFCell cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(String.valueOf(step.getStepsId()));
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(String.valueOf(step.getScriptId()));
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getDescrip());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);

		// ������
		String testcontent = step.getTestContent();
		for (Entry<String, String> entry : scriptmanagehash.entrySet()) {

			if (testcontent.toUpperCase().contains(entry.getKey())) {
				// ���Դ�Сд�滻�ַ���
				testcontent = testcontent.replaceAll("(?i)" + entry.getKey(),
						entry.getValue());
				break;
			}

		}

		cell.setCellValue(testcontent);

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestDate());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestPhase());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestAntic());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestHost());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestBatch());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestStatus());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestPrior());
		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getTestMemo());
		return row;
	}

	/**
	 * ����˵���� �Ѳ�������д�뵽ִ���ֲ��� �������� ������: neusoft ��������: 2013-9-12 ����7:21:50 ����˵����
	 *
	 * @param sheetName
	 * @param FilePath
	 *            ����ִ���ֲ�·��
	 * @param steps
	 *            ������Ҫ����Ĳ������� ��������ֵ�� ��
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean writeExcuteSheet(String sheetName, String FilePath,
			ArrayList<FrameworkCase> steps) {

		logger.info("�Ѳ���д��ִ���ֲ�:" + FilePath);

		ScriptManage manage = new ScriptManage();

		scriptmanagehash = manage.LoadScrpt();

		Iterator<FrameworkCase> c = steps.iterator();
		FrameworkCase a = new FrameworkCase();
		String title[] = a.getTitle();
		int len = title.length;
		File file = new File(FilePath);
		try {
			if (!file.exists())
				file.createNewFile();
			FileOutputStream in = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFCellStyle titleStyle = setTitleStyle(workbook);
			HSSFCellStyle cellStyle = setCellStyle(workbook);
			HSSFSheet sheet = workbook.createSheet(sheetName);

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell((short) 0);
			int j = 0;
			while (c.hasNext()) {
				if (j == 0) {
					for (int z = 0; z < len; z++) {
						cell = row.createCell((short) (z));
						cell.setCellStyle(titleStyle);
						cell.setCellValue(title[z]);
					}
					j++;
				} else {
					FrameworkCase step = c.next();
					step.setStepsId(String.valueOf(j));
					row = sheet.createRow(j);
					row = writeCells(row, step, cellStyle);
					j++;
				}

			}
			workbook.write(in);
			in.close();
			logger.info("д�ļ��ɹ�:" + file.getName());
		} catch (Exception e) {
			logger.error("д�ļ�ʧ��:" + FilePath);

			return false;
		} finally {
		}
		return true;

	}

	private Hashtable<String, Integer> htradePhase;

	/**
	 * ����˵���� TODO ������: neusoft ��������: 2013-9-13 ����8:47:09 ����˵����
	 *
	 * @param titleDesc
	 * @param tile
	 * @param fileName
	 * @return boolean
	 */
	private boolean deteTilt(String[] titleDesc, String[] tile) {
		for (int i = 0; i < titleDesc.length; i++) {
			if (titleDesc[i] == null || titleDesc[i].isEmpty())
				continue;
			int k = 0;
			for (k = 0; k < tile.length; k++) {
				if (tile[k].equalsIgnoreCase(titleDesc[i]))
					break;
			}
			if (k >= tile.length) {
				logger.error("�������:[" + titleDesc[i] + "]����");
				return false;

			}
		}
		return true;
	}

	/**
	 * ����˵���� TODO ������: neusoft ��������: 2013-9-13 ����8:49:43 ����˵����
	 *
	 * @param cell
	 * @return String
	 */
	private String getCellValue(HSSFCell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {

			case Cell.CELL_TYPE_NUMERIC:
				value = "" + cell.getNumericCellValue();
				break;

			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_BLANK:
				;
				break;
			default:
			}
		}
		return value;
	}

	/**
	 * ����˵���� ��ȡ����ִ������ ������: neusoft ��������: 2013-9-12 ����7:23:51 ����˵����
	 *
	 * @return Boolean
	 */
	public ArrayList<FrameworkCase> GetExcuteCase() {
		ArrayList<FrameworkCase> steps = new ArrayList<FrameworkCase>();
		AirConfigHnd.getInstance();

		/****************************************************
		 * ��ȡ�����б��������б����飬��������б�������б��ļ�
		 **************************************************/
		CycleHnd cycleHnd = CycleHnd.getInstance();
		String cyclefile = PropConfig.getTestCycleFile();
		logger.info("AIRCYLE�ļ�:" + cyclefile);
		if (!cycleHnd.readCycleFile(cyclefile)) {
			ExecuteResultDialog.viewError("AIRCYLE�ļ���ȡ����" + cyclefile, "����");
			return steps;
		}

		String caselistfile = PropConfig.getCaseListFile();
		logger.info("Caselist�ļ�:" + caselistfile);
		cycleHnd.outPutCaseListFile(caselistfile);

		String scencelistfile = PropConfig.getScenceListFile();
		logger.info("Scencelist�ļ�:" + scencelistfile);
		cycleHnd.outPutSceneListFile(scencelistfile);

		/******************************************************
		 * ���������б��볡���б���ȡ��������,��������������ļ���
		 ******************************************************/
		logger.info("#���������б��볡���б���ȡ��������,��������������ļ���");
		ICaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance();
		ICaseStepsScript caseSteps = DAOFactory.getApplCaseStepsInstance();

		String casepath = PropConfig.getCasePath();
		logger.info("����·����" + casepath);
		if (!caseScrpit.readCaseDetails(casepath, cycleHnd.getCaseList(),
				cycleHnd.getSceneList())) {
			ExecuteResultDialog.viewError("�����������", "����");
			return steps;
		}
		caseScrpit.outPutCaseDetails(caseScrpit.getCaseScript(),
				PropConfig.getTestCaseScript());

		/******************************************************
		 * ��ȡ�����ļ�"cfg\AIR_Config.xml"
		 ******************************************************/
		logger.info("��ȡ�����ļ�cfg\\AIR_Config.xml");
		ApplXmlParse caseConfig = ApplXmlParse.getinstance();
		if (!caseConfig.readxml()) {
			ExecuteResultDialog.viewError("��ȡ�����ļ�\"cfg\\AIR_Config.xml\"����",
					"failed");
			return steps;
		}
		/***************************************
		 * ��ȡ��������,�������еĲ���������ļ���
		 *************************************/
		if (!caseSteps.readCaseSheet(caseScrpit.getCaseScript())) {
			ExecuteResultDialog.viewError("�ֲ�����ʧ��", "error");
			return steps;
		}
		steps = caseSteps.getCaseSteps();
		htradePhase = caseSteps.getTradePhase();

		/**********************************************************
		 * �����еĲ��谴�ս����ա����׽׶Ρ����ȼ����򣬲�������ļ���
		 ********************************************************/
		Collections.sort(steps, new Comparator<FrameworkCase>() {
			@Override
			public int compare(FrameworkCase obj1, FrameworkCase obj2) {

				FrameworkCase step1 = obj1;
				FrameworkCase step2 = obj2;

				int tradeDate = step1.getTestDate().compareTo(
						step2.getTestDate());
				if (step1 == null || step2 == null || step1.getTestDate() == ""
						|| step2.getTestDate() == "") {
					logger.error("TradeDate Compare:" + step1.getScriptId()
							+ ":" + step1.getTestDate() + "==>"
							+ step2.getScriptId() + ":" + step2.getTestDate());
				}
				int tradePhase = htradePhase.get(step1.getTestPhase())
						.intValue()
						- htradePhase.get(step2.getTestPhase()).intValue();
				int casePriorty = Integer.valueOf(step1.getTestPrior())
						- Integer.valueOf(step2.getTestPrior());

/***SIR 3 begin***/
				if (tradeDate > 0)
					return 1;
				else if (tradeDate < 0)
					return -1;

				else if (tradeDate == 0) {
					if (tradePhase > 0)
						return 1;
					else if (tradePhase < 0)
						return -1;

					else if (tradePhase == 0) {
						if (casePriorty > 0)
							return 1;
						else if (casePriorty < 0)
							return -1;
						else if (casePriorty == 0)
						// &&step1.getScriptId().compareTo(step2.
						// getScriptId())>0)

						{
							String stepid1 = step1.getStepsId();
							String stepid2 = step2.getStepsId();

							String scenid1 = Common.getStringByToken(1, "_",
									step1.getScriptId())
									+ "_"
									+ Common.getStringByToken(2, "_",
											step1.getScriptId())
									+ "_"
									+ Common.getStringByToken(3, "_",
											step1.getScriptId());
							String scenid2 = Common.getStringByToken(1, "_",
									step2.getScriptId())
									+ "_"
									+ Common.getStringByToken(2, "_",
											step2.getScriptId())
									+ "_"
									+ Common.getStringByToken(3, "_",
											step2.getScriptId());
							if (scenid1.compareTo(scenid2) > 0)
								return 1;
							else if (scenid1.compareTo(scenid2) < 0)
								return -1;
							else if (scenid1.compareTo(scenid2) == 0)
							{
								//***xzguo �����ڵ���0�����
								return Integer.valueOf(stepid1) - Integer.valueOf(stepid2)
										> 0 ? 1 : -1;
							}

//							else if (scenid1.compareTo(scenid2) == 0
//									&& (Integer.valueOf(stepid1) - Integer
//											.valueOf(stepid2)) > 0)
//								return 1;

						}

					}

				}
/***SIR 3 end***/
				return 0;
			}
		});
		writeExcuteSheet(ConstValues.excuteSheetName,
				PropConfig.getExecuteFile(), steps);
		logger.info("###################################################");
		logger.info("*********     ����������ϣ�����                             *********");
		logger.info("###################################################");

		hasCreat = true;

		ExecuteResultDialog.viewSuccess("�ֲ����ɳɹ�", "success");
		return steps;
	}

	/**
	 * �������ܣ���ȡһ���е������ֶ� �������룺
	 *
	 * @param row
	 *            ����xls���е�һ�� ����ֵ�� String[]
	 */
	private String[] getTitleDesc(HSSFRow row) {
		int numberOfCells = row.getPhysicalNumberOfCells();
		String title[] = new String[numberOfCells];

		for (int k = 0; k < numberOfCells; k++) {
			HSSFCell cell = row.getCell((short) k);
			String value = cell.getRichStringCellValue().getString().trim();

			title[k] = value;
		}
		return title;

	}

	/**
	 * ����˵���� TODO ������: neusoft ��������: 2013-9-12 ����7:23:42 ����˵����
	 *
	 * @return boolean
	 */
	public boolean hasCreat() {
		return hasCreat;
	}

	/*******************************************
	 * �������ܣ��ж�û�������Ƿ��� �����б������� ���룺
	 *
	 * @param cc
	 *            �������жϵ�����
	 * @param filterStr
	 *            ���������б����� ��������ֵ�� boolean
	 *********************************************/
	private boolean isInStr(String cc, HashSet<String> filterStr) {
		if (filterStr == null)
			return true;
		Iterator<String> b = filterStr.iterator();
		while (b.hasNext()) {
			String c = b.next();
			if (cc.equalsIgnoreCase(c))
				return true;
		}
		return false;

	}

	/***************************************************************************
	 * �������ܣ���ȡ�����ļ�����ȡ��Ҫ����������ϸ��Ϣ �������룺
	 *
	 * @Param file ���������ļ�
	 * @param sheetName
	 *            ������������
	 * @param int num,
	 * @param filterStr
	 *            ���������б�
	 * @param String
	 *            scene ��������ID ��������ֵ�� ��
	 **************************************************************************/
	public ArrayList<CaseManage> readCaseSheet(File file, String sheetName,
			int num, HashSet<String> filterStr, String scene) {
		ArrayList<CaseManage> scripts = new ArrayList<CaseManage>();
		try {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {

			}

			int numberOfRows = sheet.getPhysicalNumberOfRows();
			String titleDesc[] = null;

			for (int j = num; j < numberOfRows; j++) {
				HSSFRow row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				if (j == num) {
					titleDesc = getTitleDesc(row);
					if (!deteTilt(titleDesc, ConstValues.caseTitle)) {
						logger.error("����{0}����" + file.getAbsolutePath());
						return scripts;
					}
				} else {
					Hashtable values = wrapValues(titleDesc, row);
					if (values.get(ConstValues.sceneType).toString().isEmpty()) {
						logger.debug("�������Ϊ��" + values);
						continue;
					}
					String cc = strChg(values.get(ConstValues.sceneType)
							.toString(), 3);
					if (values.get(ConstValues.sceneId).toString().isEmpty()) {
						logger.debug("����IDΪ��" + values);
						continue;
					}
					cc = cc
							+ "_"
							+ strChg(
									values.get(ConstValues.sceneId).toString(),
									3);
					cc = scene + "_" + cc;

					if (!isInStr(cc, filterStr)) {
						logger.error("����IDΪ��2:" + cc + "==>" + filterStr);
						continue;
					}
					ApplDetails script = new ApplDetails();
					script.setDetails(values, scene);
					scripts.add(script.getcaseDescript());

				}
			}

			if (scripts.size() == 0) {
				logger.info("����Ϊ�գ�" + file);
			}

			Collections.sort(scripts, new Comparator<CaseManage>() {
				@Override
				public int compare(CaseManage obj1, CaseManage obj2) {

					if (obj1 == null || obj2 == null) {
						logger.error("CaseManage�����Ƚ�Ϊ��");
						logger.error("Obj1:" + obj1.getCaseId());
						logger.error("Obj2:" + obj2.getCaseId());
						return 0;
					}

					if (obj1.getCaseId().compareToIgnoreCase(obj2.getCaseId()) >= 1)
						return 1;
					else
						return 0;

				}
			});
			logger.info("��ȡ�ļ��ɹ�:" + file.getAbsolutePath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("�Ҳ����ļ�:" + file.getAbsolutePath());
			logger.error("ִ���ֲ�����ʧ�� ");
			ExecuteResultDialog.viewError(
					"ִ��ʧ�ܣ��Ҳ����ļ�" + file.getAbsolutePath(), "ERROR");
			return scripts;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��ȡ�ļ�ʧ��:" + file.getName());
			logger.error("ִ���ֲ�����ʧ�� ");
			ExecuteResultDialog.viewError("ִ��ʧ�ܣ����ļ�" + file.getAbsolutePath()
					+ "ʧ��", "ERROR");
			return scripts;

		}
		return scripts;

	}

	/******************************************************************************/
	/******************************************************************************
	 * ��������: �����������ļ��л�ȡ���в��� �������룺
	 *
	 * @param String
	 *            sheetNm �����ű�������
	 * @param File
	 *            file �����ű�·��
	 * @param int num �����ӵڼ��п�ʼ��
	 * @pram ApplCase caseDetail ������������ ��������ֵ�� ��
	 *******************************************************************************/
	public ArrayList<FrameworkCase> readCaseStep(String sheetNm, File file,
			int num, CaseManage caseDetail) {

		ArrayList<FrameworkCase> steps = new ArrayList<FrameworkCase>();
		boolean bcaseFlg = true;

		logger.info("��ʼ����:" + file.getAbsolutePath());
		try {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheet(sheetNm);
			if (sheet == null) {
			}
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			logger.info("��ȡ�ļ�����:" + numberOfRows);
			String titleDesc[] = null;

			for (int j = num; j < numberOfRows; j++) {
				HSSFRow row = sheet.getRow(j);

				if (row == null) {
					continue;
				}
				if (j == num) {
					titleDesc = getTitleDesc(row);
					if (!deteTilt(titleDesc, ConstValues.stepTitle)) {
						logger.error("����{0}����" + file.getAbsolutePath());
						bcaseFlg = false;
					}
				} else {
					Hashtable<String, String> values = wrapValues(titleDesc,
							row);

					if (values.get("�ű����").toString().isEmpty()) {
						// logger.info( "�ű����Ϊ��");
						continue;
					}
					if (values.get("������").toString().isEmpty()) {
						logger.error("����{0}�Ľ������ֶ�Ϊ��" + caseDetail.getCaseId()
								+ "_" + values.get("�ű����").toString());
						bcaseFlg = false;
					}

					if (values.get("ִ�н׶�").toString().isEmpty()) {
						logger.error("����{0}��ִ�н׶��ֶ�Ϊ��" + caseDetail.getCaseId()
								+ "_" + values.get("�ű����").toString());
						bcaseFlg = false;
					}
					if (values.get("����״̬").toString().isEmpty()) {
						logger.error("����{0}�Ĵ���״̬Ϊ��" + caseDetail.getCaseId()
								+ "_" + values.get("�ű����").toString());

						bcaseFlg = false;
					}
					if (-1 == "ynrlYNRL".indexOf(values.get("����״̬").toString()
							.trim())) {

						logger.error("����{0}�Ĵ���״̬����" + caseDetail.getCaseId()
								+ "_" + values.get("�ű����").toString());

						bcaseFlg = false;
					}
					if (values.get("�ű�ִ������").toString().isEmpty()) {
						logger.error("����{0}�Ľű�ִ�������ֶ�Ϊ��,"
								+ caseDetail.getCaseId() + "_"
								+ values.get("�ű����").toString());
						bcaseFlg = false;
					}
					ApplStepsSet step = new ApplStepsSet();
					step.setApplSteps(values, caseDetail);
					steps.add(step.getSteps());
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("�Ҳ����ļ�:" + file.getName());

			bcaseFlg = false;
		}

		catch (Exception e) {
			logger.error("δ֪�쳣{0}" + e.getMessage());

			bcaseFlg = false;
		}
		if (bcaseFlg) {
			logger.info("********* ���������ɹ� :" + file.getName());
		} else {
			logger.error("********* ��������ʧ�� :" + file.getName());

		}

		return steps;
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����9:11:19 ����˵����
	 *
	 * @param c
	 * @param charLength
	 * @return String
	 */
	private String strChg(String c, int charLength) {

		Float cc = Float.valueOf(c);
		String a = String.valueOf(cc.intValue());
		while (a.length() < charLength)
			a = "0" + a;
		return a;
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����8:52:20 ����˵����
	 *
	 * @param titleDesc
	 * @param dataRow
	 * @return Hashtable<String,String>
	 */
	private Hashtable<String, String> wrapValues(String[] titleDesc,
			HSSFRow dataRow) {
		Hashtable<String, String> values = new Hashtable<String, String>();

		for (int k = 0; k < titleDesc.length; k++) {
			HSSFCell cell = dataRow.getCell((short) k);
			String value = getCellValue(cell);
			String desc = titleDesc[k];
			values.put(desc, value);

		}

		return values;
	}
}
