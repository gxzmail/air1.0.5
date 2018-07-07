package sse.ngts.testrobot.app.execute.process;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.autoexecute.IAutoExecute;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.exception.ApplPauseException;
import sse.ngts.testrobot.factory.EXECUFactory;

public class ExecuteProcessImpl implements IExecuteProcess {

	private Logger logger = Logger.getLogger(ExecuteProcessImpl.class);

	/**
	 * 判断步骤的执行类别，调用相应的接口实现执行过程，并根据返回值设置步骤详情数组
	 * 
	 * @param step
	 *            －－步骤详情数组
	 * @return boolean －－步骤执行是否成功
	 */
	private ActionController actioncontroller;

	/**
	 * 当步骤已经执行一次，但因为需要手动执行而停止执行，则设置步骤状态为执行成功
	 * 
	 * @param step
	 * @return
	 */
	private boolean handlePreSteps(ScriptCase step) {
		if (!step.getAttribute(ScriptCase.ATTR_REFF_FLAG))
			return false;
		if (step.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& step.getStepType() != 2 || step.getStepType() == 4) {
			step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
			step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
			logger.info("步骤{0}手动执行成功" + step.getFrmCase().getStepsId());
			step.getTestResult().add(
					"步骤" + step.getFrmCase().getStepsId() + "手动执行成功");
			return true;
		} else if (step.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
				&& step.getStepType() == 2) {
			step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
			return false;
		}
		return false;
	}

	public boolean isStoprun() {
		return actioncontroller.isStopRun();
	}

	@Override
	public void setActioncontroller(ActionController actioncontroller) {
		this.actioncontroller = actioncontroller;
	}

	@Override
	public void stepProcess(ScriptCase step) {

		String[] str = { step.getFrmCase().getStepsId(),
				step.getFrmCase().getScriptId() };
		logger.info("步骤{0}开始执行步骤， 脚本号码：{1}" + str);
		if (handlePreSteps(step)) {
			step.setReuslt();
			return;
		}
		/* 设置步骤被访问过 */
		step.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);

		try {
			/* 手动执行抛出异常，需要手动执行 */
			if (step.getStepType() == 4) {
				logger.info("步骤{0}为手动执行类型" + str);
				step.getTestResult().add("手动类型，需要手动执行");
				throw new ApplPauseException();
			} else if (step.getStepType() == 5) {
				// 写入日志，步骤不执行
				step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
				step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
				step.getTestResult().add("N/A类型，跳过");
				step.setAttribute(ScriptCase.ATTR_AUTOFIN_FLAG, true);
				logger.info("步骤{0}执行完成，脚本号码：{1}" + str);

			} else {
				// 写入日志，步骤自动执行
				IAutoExecute autoExecute = EXECUFactory
						.getApplAutoExecuteInstance();
				autoExecute.setExecuteProcess(this);
				if (!autoExecute.autoExec(step)) {
					throw new ApplPauseException();
				}

			}
			step.setReuslt();
		} catch (ApplPauseException ex) {
			step.setReuslt();
			ex.printStackTrace();
			throw new ApplPauseException();

		}

	}

}
