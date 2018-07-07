package sse.ngts.testrobot.app.execute.process;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.autoexecute.IAutoExecute;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.exception.ApplPauseException;
import sse.ngts.testrobot.factory.EXECUFactory;

public class ExecuteProcessImpl implements IExecuteProcess {

	private Logger logger = Logger.getLogger(ExecuteProcessImpl.class);

	/**
	 * �жϲ����ִ����𣬵�����Ӧ�Ľӿ�ʵ��ִ�й��̣������ݷ���ֵ���ò�����������
	 * 
	 * @param step
	 *            ����������������
	 * @return boolean ��������ִ���Ƿ�ɹ�
	 */
	private ActionController actioncontroller;

	/**
	 * �������Ѿ�ִ��һ�Σ�����Ϊ��Ҫ�ֶ�ִ�ж�ִֹͣ�У������ò���״̬Ϊִ�гɹ�
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
			logger.info("����{0}�ֶ�ִ�гɹ�" + step.getFrmCase().getStepsId());
			step.getTestResult().add(
					"����" + step.getFrmCase().getStepsId() + "�ֶ�ִ�гɹ�");
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
		logger.info("����{0}��ʼִ�в��裬 �ű����룺{1}" + str);
		if (handlePreSteps(step)) {
			step.setReuslt();
			return;
		}
		/* ���ò��豻���ʹ� */
		step.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);

		try {
			/* �ֶ�ִ���׳��쳣����Ҫ�ֶ�ִ�� */
			if (step.getStepType() == 4) {
				logger.info("����{0}Ϊ�ֶ�ִ������" + str);
				step.getTestResult().add("�ֶ����ͣ���Ҫ�ֶ�ִ��");
				throw new ApplPauseException();
			} else if (step.getStepType() == 5) {
				// д����־�����費ִ��
				step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
				step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
				step.getTestResult().add("N/A���ͣ�����");
				step.setAttribute(ScriptCase.ATTR_AUTOFIN_FLAG, true);
				logger.info("����{0}ִ����ɣ��ű����룺{1}" + str);

			} else {
				// д����־�������Զ�ִ��
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
