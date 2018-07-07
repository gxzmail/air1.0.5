package sse.ngts.testrobot.app.ui.conditionsheet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.process.ActionController;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.ui.thirdui.xf.XFTable;
import sse.ngts.testrobot.ui.thirdui.xf.XFTableBO;

public class ConditionSheetTable extends XFTable {
	private Logger logger = Logger.getLogger(ConditionSheetTable.class);

	private static String columns[] = { "序列", "脚本编号", "用例描述", "脚本描述", "脚本执行内容",
			"交易日", "执行时段", "错误状态", "结果", "运行时间", "开始/结束时间", "标记" };

	private static int width[] = { 50, 100, 200, 180, 350, 50, 60, 80, 80, 80,
			110, 80 };

	private int currID = 1;

	private ConditionSheetRender render;

	public ConditionSheetTable(ConditionSheetController controller) {
		super(columns);

		this.setWidth(width);
		render = new ConditionSheetRender(controller);
		this.getTableHeader().setReorderingAllowed(false);
		this.setDefaultRenderer(Object.class, render);

	}

	/**
	 * 
	 * 函数说明：添加行 2013-11-15 下午2:40:50
	 * 
	 * @param c
	 */
	public void addRow(ActionController c) {
		ApplTableBO bo = new ApplTableBO(currID, c.getCurrentScript());
		super.addRow(bo);
		currID++;
	}

	/**
	 * 设置运行时间
	 * 
	 * @param rowID
	 * @param costTime
	 */
	public void changeCostTime(final int rowID, final long costTime) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));

		final String scostTime = dateFormatter.format(new Date(costTime));

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ConditionSheetTable.this.getModel().setValueAt(scostTime,
						rowID, 9);

			}
		});
	}

	public void changeBeginEndTime(final int rowID, long beginTime, long endTime) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		String defaultGapTime = "";
		if (beginTime != 0) {
			final String sbeginTime = dateFormatter.format(new Date(beginTime));
			final String sendTime = dateFormatter.format(new Date(endTime));
			defaultGapTime = sbeginTime + "/" + sendTime;
		}
		
		final String sTime = defaultGapTime;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ConditionSheetTable.this.getModel()
						.setValueAt(sTime, rowID, 10);

			}
		});
	}

	/**
	 * 
	 * 函数说明：标记 2013-11-15 下午5:34:41
	 * 
	 * @param rowID
	 * @param remark
	 */
	public void ReMark(final int rowID, final String remark) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				ConditionSheetTable.this.getModel().setValueAt(remark, rowID,
						11);
			}
		});
	}

	/**
	 * 设置执行起始时间
	 * 
	 * @param rowID
	 * @param starttime
	 * @param endtime
	 */
	public void changeRunPhase(final int rowID, final long starttime,
			final long endtime) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		final String timestart = dateFormatter.format(new Date(starttime));
		final String timeend = dateFormatter.format(new Date(endtime));

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				String str = timestart + "/" + timeend;
				ConditionSheetTable.this.getModel().setValueAt(str, rowID, 10);
			}
		});
	}

	/**
	 * 设置执行结果
	 * 
	 * @param rowID
	 * @param result
	 */
	public void changeStatus(final int rowID, final String result) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				logger.info("更改状态 row:" + rowID);
				ConditionSheetTable.this.getModel()
						.setValueAt(result, rowID, 8);
			}
		});
	}

	public String changstr(String str, int len) {
		if (str == null || str.equals(""))
			return "";
		if (str.length() > len) {
			str = str.substring(0, len - 3);
			str = str.concat("...");
		}
		return str;
	}

	@Override
	public void clearAll() {
		super.clearAll();
		currID = 1;
	}

	public ScriptCase getApplExecutCase(int row) {
		return ((ApplTableBO) super.getBO(row)).getApplExecutCase();
	}

	public int getCurrID() {
		return currID;
	}

	public ConditionSheetRender getRender() {
		return render;
	}

	class ApplTableBO extends XFTableBO {
		private ScriptCase script;

		public ApplTableBO(int id, ScriptCase script) {
			this.script = script;
		}

		public ScriptCase getApplExecutCase() {
			return script;
		}

		/* 修改Table显示的字符数 */
		@Override
		public Object[] getItems() {
			// ConditionSheetTable.this.columnModel.getColumn(0).getWidth();

			return new Object[] { script.getFrmCase().getStepsId(),
					script.getFrmCase().getScriptId(),
					script.getFrmCase().getCaseDetials(),
					script.getFrmCase().getDescrip(),
					script.getFrmCase().getTestContent(),
					script.getFrmCase().getTestDate(),
					script.getFrmCase().getTestPhase(),
					script.getStepTypeDescr(), script.getTestResultDescr(),
					script.getFrmCase().getRunTime(),
					script.getFrmCase().getGapTime(),
					script.getFrmCase().getMark() };
		}

		@Override
		public String getTableField(int col) {
			return "";
		}

	}
}
