package sse.ngts.testrobot.app.execute.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.app.model.FrameworkCase;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.common.Common;
import sse.ngts.testrobot.common.ConstValues;

/**
 * @author neusoft
 *
 */
public class ApplExectueStat {

	private String errorNum;
	private String sumSteps;
	private String sucessNum;
	private String sheetName;
	private static ApplExectueStat instance;
	private Logger logger = Logger.getLogger(ApplExectueStat.class);
	private TreeMap<String, AnalyseData> analyse = new TreeMap<String, AnalyseData>();

	public static ApplExectueStat getInstance() {
		if (instance == null) {
			instance = new ApplExectueStat();
		}
		return instance;
	}

	public void execResultWrite(String fileName, ActionController c) {

		try {
			File fi = new File(fileName);
			if (!fi.exists() || !fi.isFile()) {
				Common.createDir(fileName, ConstValues.APPL_File);
				fi.createNewFile();
			}
			FileWriter file = new FileWriter(fileName, true);
			PrintWriter pwrite = new PrintWriter(file);
			pwrite.write("------------------------------------------------------");
			pwrite.println();
			if (!c.getCurrentScript()
					.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
			}
			pwrite.write("步骤号  ："
					+ c.getCurrentScript().getFrmCase().getStepsId());
			pwrite.println();
			pwrite.write("脚本ID  ："
					+ c.getCurrentScript().getFrmCase().getScriptId());
			pwrite.println();
			pwrite.write("执行状态：" + c.getCurrentScript().getTestResultDescr());

			pwrite.println();
			pwrite.write("执行结果：");
			pwrite.println();
			/* 2012-09-11,修改只显示最新日志,by wuli */

			int j = c.getCurrentScript().getTestResult().size();
			if (j > 0) {
				pwrite.write(c.getCurrentScript().getTestResult().get(j - 1));
				pwrite.println();
			}
			/* 2012-09-11,修改只显示最新日志,by wuli */
			pwrite.write("------------------------------------------------------");
			pwrite.println();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HSSFCellStyle setCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = setTitleStyle(workbook);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		return cellStyle;
	}

	public HSSFCellStyle setTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom((short) 2);
		titleStyle.setBorderLeft((short) 2);
		titleStyle.setBorderRight((short) 2);
		titleStyle.setBorderTop((short) 2);
		return titleStyle;
	}

	public void statProcess(String ExecFile, String OutFile,
			ArrayList<ActionController> actionscontroller) {
		this.sheetName = ExecFile;
		writeResultStat(OutFile, actionscontroller);

	}
	
	public void statResult(ArrayList<ActionController> steps) {

		if (steps == null)
			return;
		Iterator<ActionController> c = steps.iterator();
		ScriptCase a;

		int errorNum1 = 0;
		int sumSteps1 = 0;
		while (c.hasNext()) {
			ActionController action = c.next();
			if (!action.getApplSheetUIController().getSheetName()
					.equalsIgnoreCase(sheetName))
				continue;
			a = action.getCurrentScript();
			if (!a.getAttribute(ScriptCase.ATTR_REFF_FLAG))
				continue;
			if (!a.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
				errorNum1++;
			}
			sumSteps1++;
		}
		sumSteps = String.valueOf(sumSteps1);
		errorNum = String.valueOf(errorNum1);
		sucessNum = String.valueOf(sumSteps1 - errorNum1);
	}

	@SuppressWarnings("deprecation")
	public HSSFRow writeCells(int stepId, HSSFRow row, ScriptCase step,
			HSSFCellStyle cellStyle) {

		int z = 0;
		HSSFCell cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(String.valueOf(stepId));

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getScriptId());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getDescrip());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getTestContent());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getTestAntic());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getTestBatch());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getTestStatus());

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG))
			cell.setCellValue("未执行");
		else if (step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG))
			cell.setCellValue("执行成功");
		else
			cell.setCellValue("执行失败");

		cell = row.createCell((short) z++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(step.getFrmCase().getTestMemo());
		return row;
	}

	public void writeResultStat(String FilePath,
			ArrayList<ActionController> resultStat) {

		File file = new File(FilePath);
		try {

			logger.debug("开始写统计文件:" + FilePath);

			Common.createDir(FilePath, ConstValues.APPL_File);
			if (!file.exists() || !file.isFile())
				file.createNewFile();
			FileOutputStream in = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFCellStyle titleStyle = setTitleStyle(workbook);
			HSSFCellStyle cellStyle = setCellStyle(workbook);
			HSSFSheet sheet = workbook.createSheet(ConstValues.statsheetName);

			HSSFRow row = null;
			HSSFCell cell = null;

			int j = 0;
			row = sheet.createRow(j);

			cell = row.createCell((short) (0));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("步骤ID");

			cell = row.createCell((short) (1));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("用例描述");

			cell = row.createCell((short) (2));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("脚本编号");

			cell = row.createCell((short) (3));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("错误状态");

			cell = row.createCell((short) (4));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("结果");

			cell = row.createCell((short) (5));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("运行时间");

			cell = row.createCell((short) (6));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("开始/结束时间");

			cell = row.createCell((short) (7));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("脚本执行内容");

			cell = row.createCell((short) (8));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("脚本描述");

			cell = row.createCell((short) (9));
			cell.setCellStyle(titleStyle);
			cell.setCellValue("错误原因");
			j++;

			Iterator<ActionController> c = resultStat.iterator();

			int errorNum = 0;
			int sumSteps = 0;
			int sucSteps = 0;
			analyse = new TreeMap<String, AnalyseData>();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
			dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
			SimpleDateFormat costFormatter = new SimpleDateFormat("HH:mm:ss");
			costFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
			
			while (c.hasNext()) {
				ActionController action = c.next();
				/*
				 * if (!action.getApplSheetUIController().getSheetName()
				 * .equalsIgnoreCase(sheetName)) continue;
				 */
				ScriptCase step = action.getCurrentScript();
				sumSteps++;

				if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
						&& !step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
					errorNum++;
				} else if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
						&& step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
					sucSteps++;
				}
				FrameworkCase cases = step.getFrmCase();
				String scriptid = cases.getScriptId();
				String key = scriptid.substring(0, scriptid.length() - 4);
				if (analyse.containsKey(key)) {
					AnalyseData data = (AnalyseData) analyse.get(key);

					data.setSumSteps(data.getSumSteps() + 1);
					if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
							&& !step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
						data.setErrSteps(data.getErrSteps() + 1);
					} else if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
							&& step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
						data.setSucSteps(data.getSucSteps() + 1);
					}
					if(action.getBeginTime() > 0 && data.getBeginTime() == 0)
					{
						data.setBeginTime(action.getBeginTime());						
					}					
					else if(data.getBeginTime() > 0 && action.getEndTime() > 0)
					{
						data.setEndTime(action.getEndTime());
					}
					
					
					
					if (cases.getTestContent().trim().length() == 0
							|| cases.getTestContent().trim().toUpperCase()
									.startsWith("NA")
							|| cases.getTestContent().trim().toUpperCase()
									.startsWith("N/A")) {
						data.setManulSteps(data.getManulSteps() + 1);
					}

					data.setCostTime(data.getCostTime() + action.getCostTime());
					analyse.put(key, data);
				} else {
					AnalyseData data = new AnalyseData();
					data.setSumSteps(1);
					if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
							&& !step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
						data.setErrSteps(1);
					} else if (step.getAttribute(ScriptCase.ATTR_REFF_FLAG)
							&& step.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)) {
						data.setSucSteps(1);
					}
					data.setBeginTime(action.getBeginTime());
					if (cases.getTestContent().trim().length() == 0
							|| cases.getTestContent().trim().toUpperCase()
									.startsWith("NA")
							|| cases.getTestContent().trim().toUpperCase()
									.startsWith("N/A")) {
						data.setManulSteps(1);
					}
					data.setCostTime(action.getCostTime());
					analyse.put(key, data);

				}

				row = sheet.createRow(j);

				cell = row.createCell((short) (0));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getStepsId());

				cell = row.createCell((short) (1));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getCaseDetials());

				cell = row.createCell((short) (2));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getScriptId());

				cell = row.createCell((short) (3));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getTestStatus());

				cell = row.createCell((short) (4));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getResult());

				cell = row.createCell((short) (5));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(costFormatter.format(new Date(action
						.getCostTime())));

				cell = row.createCell((short) (6));
				cell.setCellStyle(cellStyle);
				String gapTime = dateFormatter.format(new Date(action
						.getBeginTime()))
						+ "/"
						+ dateFormatter.format(new Date(action.getEndTime()));
				cell.setCellValue(gapTime);

				cell = row.createCell((short) (7));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getTestContent());

				cell = row.createCell((short) (8));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(step.getFrmCase().getDescrip());

				cell = row.createCell((short) (9));
				cell.setCellStyle(cellStyle);
				int i = step.getTestResult().size();
				if (i > 0) {
					cell.setCellValue(step.getTestResult().get(i - 1));
				}
				j++;

			}

			row = sheet.createRow(j);
			cell = row.createCell((short) (0));
			cell.setCellValue("执行统计结果，总共执行步骤数为:" + sumSteps + ";执行失败步骤为："
					+ errorNum + ";成功步骤为：" + sucSteps);

			HSSFSheet sheetAnalyse = workbook
					.createSheet(ConstValues.statReuslt);
			row = sheetAnalyse.createRow(0);

			cell = row.createCell((short) (0));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("场景编号");

			cell = row.createCell((short) (1));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总用例数");

			cell = row.createCell((short) (2));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("成功用例数");

			cell = row.createCell((short) (3));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("失败用例数");
			
			cell = row.createCell((short) (4));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("手工用例数");
			
			cell = row.createCell((short) (5));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("运行时间");
			
			cell = row.createCell((short) (6));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("开始/结束时间");
			
			Set<Entry<String, AnalyseData>> sets = analyse.entrySet();
			Iterator<Entry<String, AnalyseData>> iterator = sets.iterator();
			int k = 1;
			int sumSteps2 = 0;
			int sucSteps2 = 0;
			int errSteps2 = 0;
			long costTimes2 = 0;
			int manulSteps2 = 0;			
			
			while (iterator.hasNext()) {
				Entry<String, AnalyseData> section = iterator.next();
				String key = section.getKey();
				AnalyseData data = section.getValue();
				row = sheetAnalyse.createRow(k);

				cell = row.createCell((short) (0));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(key);

				sumSteps2 += data.getSumSteps();
				cell = row.createCell((short) (1));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(data.getSumSteps());

				sucSteps2 += data.getSucSteps();
				cell = row.createCell((short) (2));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(data.getSucSteps());

				errSteps2 += data.getErrSteps();
				cell = row.createCell((short) (3));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(data.getErrSteps());
				
				manulSteps2 += data.getManulSteps();
				cell = row.createCell((short) (4));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(data.getManulSteps());
				
				costTimes2 += data.getCostTime();
				cell = row.createCell((short) (5));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(costFormatter.format(new Date(data
						.getCostTime())));
				
				
				cell = row.createCell((short) (6));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(dateFormatter.format(new Date(data
						.getBeginTime())) + "/" + dateFormatter.format(new Date(data
								.getEndTime())));
				
				k++;
			}
			row = sheetAnalyse.createRow(k);

			cell = row.createCell((short) (0));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总计");

			cell = row.createCell((short) (1));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(sumSteps2);

			cell = row.createCell((short) (2));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(sucSteps2);

			cell = row.createCell((short) (3));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(errSteps2);
			
			cell = row.createCell((short) (4));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(manulSteps2);
			
			cell = row.createCell((short) (5));
			cell.setCellStyle(cellStyle);
			cell.setCellValue(costFormatter.format(new Date(costTimes2)));
			
			cell = row.createCell((short) (6));
			cell.setCellStyle(cellStyle);
			cell.setCellValue("");
			
			workbook.write(in);
			in.close();
			logger.debug("统计文件处理结束!!!");
		} catch (Exception e) {
			Logger.getLogger(ApplExectueStat.class).info("写文件{0}失败" + FilePath);
			Logger.getLogger(ApplExectueStat.class).info("执行结果统计生成失败 ");

		}
	}

}
