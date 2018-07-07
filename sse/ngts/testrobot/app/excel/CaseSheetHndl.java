package sse.ngts.testrobot.app.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import sse.db.model.Scence;

// TODO: Auto-generated Javadoc
/**
 * The Class CaseSheetHndl.
 */
public class CaseSheetHndl {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CaseSheetHndl.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		/*
		 * ArrayList<Object> frmk = CaseSheetHndl.ReadCaseSheet(
		 * "TestCases\\AIR��ܽű�\\AIR_�Զ������߲��Կ��_CV01.xls", "���Խű�",
		 * ConstValues.stepTitle, 3, ScriptModel.class);
		 */

		ArrayList<Object> scence = CaseSheetHndl.ReadCaseSheet(
				"TestCases\\��ҵƽ̨BATCH(BT)\\NGTS_AM_AIR_D_BT01_CV01.xls",
				"��������", Scence.getTitles(), 3, Scence.class);
		for (Object s : scence) {
			Scence model = (Scence) s;
			System.out.println(model.getCaseId());
		}

		WriteCaseSheet("a.xls", "test", Scence.getTitles(), scence);

	}

	/**
	 * ����˵���� ͨ����ȡExcelָ����sheetname�������б���title�������ݴ�ŵ�model��
	 * �˷��������˷�ת�����ڵ���ʱ����׼ȷ�Ĳ���.
	 * 
	 * @param exlsname
	 *            -- Excel�ļ�·��
	 * @param sheetname
	 *            -- ������sheet��
	 * @param title
	 *            -- �����б���
	 * @param skipnum
	 *            -- �б�����ʼ��
	 * @param model
	 *            -- ģ��
	 * @return the array list
	 */
	public static ArrayList<Object> ReadCaseSheet(String exlsname,
			String sheetname, String[] title, int skipnum, Class model) {

		ArrayList<Object> frmList = new ArrayList<Object>();

		/* ͨ�������ȡ��Ӧ���е�index */
		Hashtable<String, Integer> dataIndex = new Hashtable<String, Integer>();

		File f = new File(exlsname);
		try {
			FileInputStream in = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			if (workbook.getSheetIndex(sheetname) < 0) {
				logger.info("Excel " + exlsname
						+ " must not exit sheet " + sheetname);
				return frmList;
			}
			HSSFSheet sheet = workbook.getSheet(sheetname);
			if (sheet == null) {
			}
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			String titleDesc[] = null;

			for (int j = skipnum; j < numberOfRows; j++) {
				HSSFRow row = sheet.getRow(j);

				if (row == null) {
					continue;
				}

				int numberOfCells = row.getPhysicalNumberOfCells();
				String rowline[] = new String[numberOfCells];
				for (int k = 0; k < numberOfCells; k++) {

					rowline[k] = "";
					HSSFCell cell = row.getCell(k);
					if (cell == null) {
						logger.info(
								"Cell is null, rowline: " + row.getRowNum());
						continue;
					}
					String value = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						value = cell.getRichStringCellValue().toString();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						value = Double.toString(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						value = cell.getRichStringCellValue().toString();
						break;
					case Cell.CELL_TYPE_BLANK:
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						value = Boolean.toString(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_ERROR:
						value = Byte.toString(cell.getErrorCellValue());
						break;

					default:
						break;
					}

					rowline[k] = value;
				}

				/* ����б����Ƿ���ȷ */
				if (j == skipnum) {
					dataIndex = CaseSheetCommon.CheckSheetTitle(rowline, title);
					if (dataIndex.size() != title.length) {
						logger.error(exlsname + " " + sheetname
								+ "�б�����ʧ��");
						return frmList;
					}
				} else {
					/* ���˿��� */
					boolean bBlank = false;
					for (Entry<String, Integer> entry : dataIndex.entrySet()) {
						if (rowline.length <= entry.getValue()) {
							bBlank = true;
							break;
						}

						if (rowline[entry.getValue()] != null
								&& rowline[entry.getValue()].length() > 0) {
							bBlank = true;
						}
					}

					if (!bBlank) {
						break;
					}

					Object instance = model.newInstance();
					Class[] param = new Class[] { int.class, String.class };
					Method method = instance.getClass().getMethod("setValue",
							param);
					method.setAccessible(true);

					Method IndexMethod = instance.getClass().getMethod(
							"getColumnIndex", new Class[] { String.class });
					IndexMethod.setAccessible(true);

					for (Entry<String, Integer> entry : dataIndex.entrySet()) {
						if (rowline.length <= entry.getValue()) {
							method.invoke(instance,
									new Object[] { entry.getValue(), "" });
						} else {

							/* ��ȡ�������� */
							int index = (Integer) IndexMethod.invoke(instance,
									new Object[] { entry.getKey() });

							method.invoke(instance, new Object[] { index,
									rowline[entry.getValue()] });
						}
					}

					Method path = instance.getClass().getMethod("setPath",
							new Class[] { String.class });
					path.invoke(instance, new Object[] { f.getParentFile()
							.getPath() });
					Method name = instance.getClass().getMethod("setFileName",
							new Class[] { String.class });
					name.invoke(instance, new Object[] { f.getName() });
					frmList.add(instance);
				}

			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("�Ҳ����ļ�:" + f.getName());
		}

		catch (NoSuchMethodException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		return frmList;
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����8:51:16 ����˵����.
	 * 
	 * @param workbook
	 *            the workbook
	 * @return HSSFCellStyle
	 */
	private static HSSFCellStyle setCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setDataFormat((short) 0);
		return cellStyle;
	}

	/**
	 * Sets the title style.
	 * 
	 * @param workbook
	 *            the workbook
	 * @return the hSSF cell style
	 */
	private static HSSFCellStyle setTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom((short) 2);
		titleStyle.setBorderLeft((short) 2);
		titleStyle.setBorderRight((short) 2);
		titleStyle.setBorderTop((short) 2);
		return titleStyle;
	}

	/**
	 * Write case sheet.
	 * 
	 * @param exlsname
	 *            the exlsname
	 * @param sheetname
	 *            the sheetname
	 * @param titles
	 *            the titles
	 * @param objlist
	 *            the objlist
	 * @return true, if successful
	 */
	public static boolean WriteCaseSheet(String exlsname, String sheetname,
			String[] titles, ArrayList<Object> objlist) {
		boolean bFlg = true;
		FileOutputStream outstream = null;

		File file = new File(exlsname);
		try {
			outstream = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();

			HSSFCellStyle titleStyle = setTitleStyle(workbook);
			HSSFCellStyle cellStyle = setCellStyle(workbook);
			HSSFSheet sheet = workbook.createSheet(sheetname);

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			/* Title */
			for (int i = 0; i < titles.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(titles[i]);
			}

			int rowLine = 1;
			/* ���� */
			for (int i = 0; i < objlist.size(); i++) {
				row = sheet.createRow(rowLine);
				Object obj = objlist.get(i);
				Class[] param = new Class[] { int.class };
				Method GetValue = obj.getClass().getMethod("getValue", param);

				Method Index = obj.getClass().getMethod("getColumnIndex",
						new Class[] { String.class });
				GetValue.setAccessible(true);

				for (int j = 0; j < titles.length; j++) {

					int index = (Integer) Index.invoke(obj,
							new Object[] { titles[j] });
					cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = (String) GetValue.invoke(obj,
							new Object[] { index });
					cell.setCellValue(value);
				}
				rowLine++;
			}
			workbook.write(outstream);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {

		}

		finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}
		return bFlg;
	}

}
