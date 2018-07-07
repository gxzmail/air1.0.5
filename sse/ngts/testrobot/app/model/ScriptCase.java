package sse.ngts.testrobot.app.model;

import java.util.ArrayList;
import java.util.BitSet;

import sse.ngts.testrobot.common.ConstValues;

public class ScriptCase {

	/**
	 * 步骤详情类
	 */
	public FrameworkCase frmCase;
	// 当前步骤运行的状态
	public static final int PROCESS_STATUS_RUN = 1;
	public static final int PROCESS_STATUS_STOP = 2;

	// 当前脚本的特性属性
	/* 自动执行的脚本　 */
	public static final int ATTR_AUTO_FLAG = 1;

	public static final int ATTR_ENABLE_FLAG = 2;

	public static final int ATTR_SHOW_FLAG = 3;

	public static final int ATTR_FINISH_FLAG = 4;

	public static final int ATTR_SUCCESS_FLAG = 5;
	/* 是否访问过 */
	public static final int ATTR_REFF_FLAG = 6;

	public static final int ATTR_SKIP_FLAG = 7;

	public static final int ATTR_AUTOFIN_FLAG = 8;
	
	private int stepType = 0;
	
	private boolean faileflag = false;
	
	private BitSet attributeSets = new BitSet();

	// 当前脚本的运行状态
	private int processStatus = PROCESS_STATUS_STOP;

	private String stepTypeDescr = null;
	
	private String testResultDescr = null;
	
	private Boolean faileMannul = false;
	
	private ArrayList<String> testResult = new ArrayList<String>();
	
	
	
	public ScriptCase() {

		setAttribute(ATTR_ENABLE_FLAG, true);
		setAttribute(ATTR_SHOW_FLAG, true);
		setAttribute(ATTR_AUTO_FLAG, true);
		setAttribute(ATTR_SKIP_FLAG, false);
		setAttribute(ATTR_REFF_FLAG, false);
		setAttribute(ATTR_AUTOFIN_FLAG, false);
		testResultDescr = ConstValues.result1;
		setStepType();
		setMannul();

	}

	public ScriptCase(FrameworkCase step) {
		super();
		frmCase = step;

		setAttribute(ATTR_ENABLE_FLAG, true);
		setAttribute(ATTR_SHOW_FLAG, true);
		setAttribute(ATTR_AUTO_FLAG, true);
		setAttribute(ATTR_SKIP_FLAG, false);
		setAttribute(ATTR_AUTOFIN_FLAG, false);
		if (this.frmCase.getTestContent().indexOf(ConstValues.manual) == 0)
			setAttribute(ATTR_AUTO_FLAG, false);
		if (this.frmCase.getTestContent()
				.substring(0, ConstValues.skip.length())
				.equalsIgnoreCase(ConstValues.skip))
			setAttribute(ATTR_SKIP_FLAG, true);
		setAttribute(ATTR_REFF_FLAG, false);
		setStepType();
		testResultDescr = ConstValues.result1;
		setMannul();
	}

	/**
	 * 获得当前脚本的性质
	 * 
	 * @return 当前脚本的性质
	 */
	public boolean getAttribute(int flag) {
		return attributeSets.get(flag);
	}

	public FrameworkCase getFrmCase() {
		return frmCase;
	}

	public boolean getMannul() {
		return faileMannul;
	}

	public int getProcessStatus() {
		return processStatus;
	}

	public int getStepType() {
		return stepType;
	}
	
	public String getStepTypeDescr() {
		return stepTypeDescr;
	}

	public ArrayList<String> getTestResult() {
		return testResult;
	}

	public String getTestResultDescr() {
		setReuslt();
		return testResultDescr;
	}

	public boolean isFaileflag() {
		return faileflag;
	}

	/**
	 * 设置当前脚本的性质
	 * 
	 * @param attribute
	 *            设置当前脚本的性质
	 */
	public void setAttribute(int flag, boolean f) {
		attributeSets.set(flag, f);
	}

	public void setFaileflag(boolean faileflag) {
		this.faileflag = faileflag;
	}

	private void setMannul() {
		if ((this.stepType == 1) || (this.stepType == 3)
				|| (this.stepType == 6)) {
			faileMannul = true;
			return;
		}
		faileMannul = false;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	public void setReuslt() {
		if (processStatus == PROCESS_STATUS_RUN)
			this.setTestResultDescr(ConstValues.result2);
		else if (!this.getAttribute(ScriptCase.ATTR_REFF_FLAG))
			this.setTestResultDescr(ConstValues.result1);
		else if (this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& this.faileMannul
				&& this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& !faileflag)
			this.setTestResultDescr(ConstValues.result3);
		else if (this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& (!this.faileMannul))
			this.setTestResultDescr(ConstValues.result3);
		else if (this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& (!this.getAttribute(ScriptCase.ATTR_AUTO_FLAG) || this.faileMannul
						&& this.faileflag))
			this.setTestResultDescr(ConstValues.result7);
		else if (!this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.faileMannul)
			this.setTestResultDescr(ConstValues.result4);
		else if (!this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SKIP_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG))
			this.setTestResultDescr(ConstValues.result6);
		else if (this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.faileMannul) {
			this.setTestResultDescr(ConstValues.result5);
			this.faileflag = true;
		}

	}
	
	/**
	 * 获取用例的执行结果
	 * @return
	 */
	public String getResult()
	{
		String result = "";
		if (processStatus == PROCESS_STATUS_RUN)
		{
			result = ConstValues.result2;
		}
		else if (!this.getAttribute(ScriptCase.ATTR_REFF_FLAG))
		{
			result = ConstValues.result1;
		}
		else if (this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& this.faileMannul
				&& this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& !faileflag)
		{
			result = ConstValues.result3;
		}
		else if (this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& (!this.faileMannul))
		{
			result = ConstValues.result3;
		}
		else if (this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& (!this.getAttribute(ScriptCase.ATTR_AUTO_FLAG) || this.faileMannul
						&& this.faileflag))
		{
			result = ConstValues.result7;
		}
		else if (!this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.faileMannul)
		{
			result = ConstValues.result4;
		}
		else if (!this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SKIP_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG))
		{
			result = ConstValues.result6;
		}
		else if (this.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& !this.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
				&& this.getAttribute(ScriptCase.ATTR_REFF_FLAG)
				&& this.faileMannul) {
			
			result = ConstValues.result5;			
		}
		
		return result;
	}
	
	public void setStepType() {
		if (this.getAttribute(ATTR_SKIP_FLAG)) {
			this.stepType = 5;
			stepTypeDescr = "跳过执行";
		} else if (this.getAttribute(ATTR_AUTO_FLAG)
				&& this.frmCase.getTestStatus().equalsIgnoreCase("N")) {
			this.stepType = 1;
			stepTypeDescr = "出错后手动";
		} else if (this.getAttribute(ATTR_AUTO_FLAG)
				&& this.frmCase.getTestStatus().equalsIgnoreCase("Y")) {
			this.stepType = 2;
			stepTypeDescr = "出错继续";
		} else if (this.getAttribute(ATTR_AUTO_FLAG)
				&& this.frmCase.getTestStatus().equalsIgnoreCase("L")) {
			this.stepType = 3;
			stepTypeDescr = "出错四次后停止";
		} else if (this.getAttribute(ATTR_AUTO_FLAG)
				&& this.frmCase.getTestStatus().equalsIgnoreCase("R")) {
			this.stepType = 6;
			stepTypeDescr = "出错重复至暂停";
		} else if (!this.getAttribute(ATTR_AUTO_FLAG)) {
			this.stepType = 4;
			stepTypeDescr = "手动执行";
		}

	}

	public void setTestResultDescr(String testResultDescr) {
		this.testResultDescr = testResultDescr;
	}
}
