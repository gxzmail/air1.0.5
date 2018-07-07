package sse.ngts.testrobot.app.func.casedetails;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import sse.ngts.testrobot.app.model.CaseManage;
import sse.ngts.testrobot.app.model.FrameworkCase;

public class ApplStepsSet {
	private FrameworkCase steps;
	private CaseManage caseDetail;

	private static boolean cycle_err_on = false;

	public FrameworkCase getSteps() {
		return steps;
	}

	private String NumChg(String a) {
		int b = Float.valueOf(a).intValue();
		return String.valueOf(b);
	}

	/**
	 * 函数功能:设置步骤中所有字段的值 函数输入：
	 * 
	 * @param values
	 * @param cDetail
	 *            函数返回值： 空
	 */
	public void setApplSteps(Hashtable values, CaseManage cDetail) {
		Set a = values.entrySet();
		Iterator b = a.iterator();
		caseDetail = new CaseManage();

		this.caseDetail = cDetail;
		steps = new FrameworkCase();

		// 设置脚本当前字段的值
		while (b.hasNext()) {
			Entry c = (Entry) b.next();
			String key = (String) c.getKey();
			String value = (String) c.getValue();
			setValue(key, value.trim());
		}
		steps.setCaseDetials(cDetail.getCaseDetails());
	}

	private void setSteps(FrameworkCase steps) {
		this.steps = steps;
	}

	private void setValue(String key, String value) {
		if (key.equals("脚本编号")) {
			this.steps.setScriptId(this.caseDetail.getCaseId() + "_"
					+ strChg(NumChg(value), 3));
		} else if (key.equals("脚本描述")) {
			this.steps.setDescrip(value);
		} else if (key.equals("脚本执行内容")) {
			this.steps.setTestContent(value);
		} else if (key.equals("交易日")) {
			this.steps.setTestDate(value);
		} else if (key.equals("执行阶段")) {
			this.steps.setTestPhase(value);
		} else if (key.equals("预期结果")) {
			this.steps.setTestAntic(value);
		} else if (key.equals("主机")) {
			this.steps.setTestHost(value);
		} else if (key.equals("批步骤")) {
			if (value.isEmpty())
				this.steps.setTestBatch("");
			else
				this.steps.setTestBatch(NumChg(value));
		} else if (key.equals("优先级")) {
			if (value.isEmpty()) {
				if (!caseDetail.getTestPrior().isEmpty())
					this.steps.setTestPrior(NumChg(caseDetail.getTestPrior()
							.trim()));
				else
					this.steps.setTestPrior(NumChg("5"));
			} else
				this.steps.setTestPrior(NumChg(value));
		} else if (key.equals("错误状态")) {
			if (cycle_err_on == false) {
				this.steps.setTestStatus(value);
			} else {
				this.steps.setTestStatus("Y");

			}
		} else if (key.equals("备注")) {
			this.steps.setTestMemo(value);
		}
	}

	public static void setCycleAlwaysContinue() {
		cycle_err_on = true;	
	}

	private String strChg(String c, int charLength) {

		Float caseDescript = Float.valueOf(c);
		String a = String.valueOf(caseDescript.intValue());
		while (a.length() < charLength)
			a = "0" + a;
		return a;
	}
}
