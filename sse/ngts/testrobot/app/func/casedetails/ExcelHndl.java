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
	 * 函数说明： 静态实例 创建人: neusoft 创建日期: 2013-9-13 上午8:47:52 参数说明：
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
	 * *********************************************************** 函数说明： TODO
	 * 创建人: neusoft 创建日期: 2013-9-13 上午8:51:16 参数说明：
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
	 * *********************************************************** 函数说明： TODO
	 * 创建人: neusoft 创建日期: 2013-9-13 上午8:51:34 参数说明：
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
	 * 把组织好的测试用例写入Excel
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

		// 处理工具
		String testcontent = step.getTestContent();
		for (Entry<String, String> entry : scriptmanagehash.entrySet()) {

			if (testcontent.toUpperCase().contains(entry.getKey())) {
				// 忽略大小写替换字符串
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
	 * 函数说明： 把步骤数组写入到执行手册中 函数输入 创建人: neusoft 创建日期: 2013-9-12 下午7:21:50 参数说明：
	 *
	 * @param sheetName
	 * @param FilePath
	 *            －－执行手册路径
	 * @param steps
	 *            －－需要输出的步骤数组 函数返回值： 空
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean writeExcuteSheet(String sheetName, String FilePath,
			ArrayList<FrameworkCase> steps) {

		logger.info("把步骤写入执行手册:" + FilePath);

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
			logger.info("写文件成功:" + file.getName());
		} catch (Exception e) {
			logger.error("写文件失败:" + FilePath);

			return false;
		} finally {
		}
		return true;

	}

	private Hashtable<String, Integer> htradePhase;

	/**
	 * 函数说明： TODO 创建人: neusoft 创建日期: 2013-9-13 上午8:47:09 参数说明：
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
				logger.error("表格列名:[" + titleDesc[i] + "]错误");
				return false;

			}
		}
		return true;
	}

	/**
	 * 函数说明： TODO 创建人: neusoft 创建日期: 2013-9-13 上午8:49:43 参数说明：
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
	 * 函数说明： 获取所有执行用例 创建人: neusoft 创建日期: 2013-9-12 下午7:23:51 参数说明：
	 *
	 * @return Boolean
	 */
	public ArrayList<FrameworkCase> GetExcuteCase() {
		ArrayList<FrameworkCase> steps = new ArrayList<FrameworkCase>();
		AirConfigHnd.getInstance();

		/****************************************************
		 * 获取场景列表与用例列表数组，输出场景列表和用例列表文件
		 **************************************************/
		CycleHnd cycleHnd = CycleHnd.getInstance();
		String cyclefile = PropConfig.getTestCycleFile();
		logger.info("AIRCYLE文件:" + cyclefile);
		if (!cycleHnd.readCycleFile(cyclefile)) {
			ExecuteResultDialog.viewError("AIRCYLE文件读取错误" + cyclefile, "错误");
			return steps;
		}

		String caselistfile = PropConfig.getCaseListFile();
		logger.info("Caselist文件:" + caselistfile);
		cycleHnd.outPutCaseListFile(caselistfile);

		String scencelistfile = PropConfig.getScenceListFile();
		logger.info("Scencelist文件:" + scencelistfile);
		cycleHnd.outPutSceneListFile(scencelistfile);

		/******************************************************
		 * 根据用例列表与场景列表，获取用例详情,并把详情输出到文件中
		 ******************************************************/
		logger.info("#根据用例列表与场景列表，获取用例详情,并把详情输出到文件中");
		ICaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance();
		ICaseStepsScript caseSteps = DAOFactory.getApplCaseStepsInstance();

		String casepath = PropConfig.getCasePath();
		logger.info("用例路径：" + casepath);
		if (!caseScrpit.readCaseDetails(casepath, cycleHnd.getCaseList(),
				cycleHnd.getSceneList())) {
			ExecuteResultDialog.viewError("用例处理错误", "错误");
			return steps;
		}
		caseScrpit.outPutCaseDetails(caseScrpit.getCaseScript(),
				PropConfig.getTestCaseScript());

		/******************************************************
		 * 读取配置文件"cfg\AIR_Config.xml"
		 ******************************************************/
		logger.info("读取配置文件cfg\\AIR_Config.xml");
		ApplXmlParse caseConfig = ApplXmlParse.getinstance();
		if (!caseConfig.readxml()) {
			ExecuteResultDialog.viewError("读取配置文件\"cfg\\AIR_Config.xml\"出错！",
					"failed");
			return steps;
		}
		/***************************************
		 * 获取用例步骤,并把所有的步骤输出到文件中
		 *************************************/
		if (!caseSteps.readCaseSheet(caseScrpit.getCaseScript())) {
			ExecuteResultDialog.viewError("手册生成失败", "error");
			return steps;
		}
		steps = caseSteps.getCaseSteps();
		htradePhase = caseSteps.getTradePhase();

		/**********************************************************
		 * 把所有的步骤按照交易日、交易阶段、优先级排序，并输出到文件中
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
								//***xzguo 不存在等于0的情况
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
		logger.info("*********     用例生成完毕！！！                             *********");
		logger.info("###################################################");

		hasCreat = true;

		ExecuteResultDialog.viewSuccess("手册生成成功", "success");
		return steps;
	}

	/**
	 * 函数功能：获取一列中的所有字段 函数输入：
	 *
	 * @param row
	 *            －－xls表单中的一列 返回值： String[]
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
	 * 函数说明： TODO 创建人: neusoft 创建日期: 2013-9-12 下午7:23:42 参数说明：
	 *
	 * @return boolean
	 */
	public boolean hasCreat() {
		return hasCreat;
	}

	/*******************************************
	 * 函数功能：判断没个用例是否在 用例列表数组中 输入：
	 *
	 * @param cc
	 *            －－用判断的用例
	 * @param filterStr
	 *            －－用例列表数组 函数返回值： boolean
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
	 * 函数功能：读取场景文件，获取需要的用例的详细信息 函数输入：
	 *
	 * @Param file －－场景文件
	 * @param sheetName
	 *            －－场景表单名
	 * @param int num,
	 * @param filterStr
	 *            －－用例列表
	 * @param String
	 *            scene －－场景ID 函数返回值： 空
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
						logger.error("用例{0}错误" + file.getAbsolutePath());
						return scripts;
					}
				} else {
					Hashtable values = wrapValues(titleDesc, row);
					if (values.get(ConstValues.sceneType).toString().isEmpty()) {
						logger.debug("场景类别为空" + values);
						continue;
					}
					String cc = strChg(values.get(ConstValues.sceneType)
							.toString(), 3);
					if (values.get(ConstValues.sceneId).toString().isEmpty()) {
						logger.debug("用例ID为空" + values);
						continue;
					}
					cc = cc
							+ "_"
							+ strChg(
									values.get(ConstValues.sceneId).toString(),
									3);
					cc = scene + "_" + cc;

					if (!isInStr(cc, filterStr)) {
						logger.error("用例ID为空2:" + cc + "==>" + filterStr);
						continue;
					}
					ApplDetails script = new ApplDetails();
					script.setDetails(values, scene);
					scripts.add(script.getcaseDescript());

				}
			}

			if (scripts.size() == 0) {
				logger.info("用例为空：" + file);
			}

			Collections.sort(scripts, new Comparator<CaseManage>() {
				@Override
				public int compare(CaseManage obj1, CaseManage obj2) {

					if (obj1 == null || obj2 == null) {
						logger.error("CaseManage用例比较为空");
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
			logger.info("读取文件成功:" + file.getAbsolutePath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("找不到文件:" + file.getAbsolutePath());
			logger.error("执行手册生成失败 ");
			ExecuteResultDialog.viewError(
					"执行失败，找不到文件" + file.getAbsolutePath(), "ERROR");
			return scripts;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取文件失败:" + file.getName());
			logger.error("执行手册生成失败 ");
			ExecuteResultDialog.viewError("执行失败，读文件" + file.getAbsolutePath()
					+ "失败", "ERROR");
			return scripts;

		}
		return scripts;

	}

	/******************************************************************************/
	/******************************************************************************
	 * 函数功能: 从用例或框架文件中获取所有步骤 函数输入：
	 *
	 * @param String
	 *            sheetNm －－脚本表单名称
	 * @param File
	 *            file －－脚本路径
	 * @param int num －－从第几行开始读
	 * @pram ApplCase caseDetail －－用例详情 函数返回值： 空
	 *******************************************************************************/
	public ArrayList<FrameworkCase> readCaseStep(String sheetNm, File file,
			int num, CaseManage caseDetail) {

		ArrayList<FrameworkCase> steps = new ArrayList<FrameworkCase>();
		boolean bcaseFlg = true;

		logger.info("开始处理:" + file.getAbsolutePath());
		try {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheet(sheetNm);
			if (sheet == null) {
			}
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			logger.info("获取文件行数:" + numberOfRows);
			String titleDesc[] = null;

			for (int j = num; j < numberOfRows; j++) {
				HSSFRow row = sheet.getRow(j);

				if (row == null) {
					continue;
				}
				if (j == num) {
					titleDesc = getTitleDesc(row);
					if (!deteTilt(titleDesc, ConstValues.stepTitle)) {
						logger.error("用例{0}错误" + file.getAbsolutePath());
						bcaseFlg = false;
					}
				} else {
					Hashtable<String, String> values = wrapValues(titleDesc,
							row);

					if (values.get("脚本编号").toString().isEmpty()) {
						// logger.info( "脚本编号为空");
						continue;
					}
					if (values.get("交易日").toString().isEmpty()) {
						logger.error("步骤{0}的交易日字段为空" + caseDetail.getCaseId()
								+ "_" + values.get("脚本编号").toString());
						bcaseFlg = false;
					}

					if (values.get("执行阶段").toString().isEmpty()) {
						logger.error("步骤{0}的执行阶段字段为空" + caseDetail.getCaseId()
								+ "_" + values.get("脚本编号").toString());
						bcaseFlg = false;
					}
					if (values.get("错误状态").toString().isEmpty()) {
						logger.error("步骤{0}的错误状态为空" + caseDetail.getCaseId()
								+ "_" + values.get("脚本编号").toString());

						bcaseFlg = false;
					}
					if (-1 == "ynrlYNRL".indexOf(values.get("错误状态").toString()
							.trim())) {

						logger.error("步骤{0}的错误状态不对" + caseDetail.getCaseId()
								+ "_" + values.get("脚本编号").toString());

						bcaseFlg = false;
					}
					if (values.get("脚本执行内容").toString().isEmpty()) {
						logger.error("步骤{0}的脚本执行内容字段为空,"
								+ caseDetail.getCaseId() + "_"
								+ values.get("脚本编号").toString());
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
			logger.error("找不到文件:" + file.getName());

			bcaseFlg = false;
		}

		catch (Exception e) {
			logger.error("未知异常{0}" + e.getMessage());

			bcaseFlg = false;
		}
		if (bcaseFlg) {
			logger.info("********* 处理用例成功 :" + file.getName());
		} else {
			logger.error("********* 处理用例失败 :" + file.getName());

		}

		return steps;
	}

	/**
	 * *********************************************************** 函数说明： TODO
	 * 创建人: neusoft 创建日期: 2013-9-13 上午9:11:19 参数说明：
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
	 * *********************************************************** 函数说明： TODO
	 * 创建人: neusoft 创建日期: 2013-9-13 上午8:52:20 参数说明：
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
