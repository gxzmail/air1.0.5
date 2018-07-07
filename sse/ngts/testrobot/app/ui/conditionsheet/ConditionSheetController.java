package sse.ngts.testrobot.app.ui.conditionsheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.process.ActionController;
import sse.ngts.testrobot.app.execute.process.ApplExecuteController;
import sse.ngts.testrobot.app.func.ApplStatusSave;
import sse.ngts.testrobot.app.func.StatusResult;
import sse.ngts.testrobot.app.interf.ISheetController;
import sse.ngts.testrobot.app.model.FrameworkCase;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.app.ui.CaseDetailScreen;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.IReceiver;
import sse.ngts.testrobot.engine.ISender;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.exception.ApplFatalException;

public class ConditionSheetController implements ISheetController, IReceiver,
		ISender {
	private static Logger logger = Logger
			.getLogger(ConditionSheetController.class);

	private ActionController currentChooseActionController = null;
	private ActionController currentWorkingController = null;

	private int currentSelectedRow;
	private int[] currentSelectedRows;
	protected ConditionSheetScreen ui;
	private CaseDetailScreen actionScreen;
	protected ArrayList<ActionController> actionsController;

	private String SheetName;
	private HashMap preferredRowHeight;
	private long costTime;

	private boolean isSuspend = false;
	private boolean isStop = false;
	private int stopRunStep = -1;
	private Boolean scrollAllowed = true;/* �Ƿ�������� */
	private ApplExecuteController applExec;
	private ArrayList<String> tradePhase;
	private int currentRunStepId;
	private int currentRunRow;
	private int stopRunRow = -1;
	private Thread aThread;
	private boolean viewflag = false;

	public ConditionSheetController() {
		tradePhase = new ArrayList<String>();
		ui = new ConditionSheetScreen(this);
		preferredRowHeight = new HashMap();
	}

	public void addCostTime(long time) {
		this.costTime += time;
	}

	public synchronized void changeSelectedRow(int row) {
		currentSelectedRow = row;
	}

	public synchronized void changeSelectedRows(int rows[]) {
		this.currentSelectedRows = rows;
	}

	/**
	 * 
	 * ����˵�������ò���ִ��״̬ 2013-11-15 ����3:18:37
	 * 
	 * @param type
	 */
	public void changeStepStatus(int type) {

		if (type == 2) {
			for (ActionController a : findControllersBySelectedRows()) {
				if (a.isCurrentControllerWorking()) {
					JOptionPane.showMessageDialog(null, "����" + a.getViewRow()
							+ "����ִ���У��������øò����״̬��", "warnning",
							JOptionPane.INFORMATION_MESSAGE);
					continue;
				}
				ScriptCase script = a.getCurrentScript();
				script.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);
				script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, false);
				if (!script.getMannul()
						&& script.getAttribute(ScriptCase.ATTR_AUTO_FLAG)) {
					script.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
				} else {
					script.setAttribute(ScriptCase.ATTR_FINISH_FLAG, false);
				}
				script.setReuslt();
				ui.getTableUI().changeStatus(a.getViewRow() - 1, "");

			}
		} else if (type == 1) {
			for (ActionController a : findControllersBySelectedRows()) {
				if (a.isCurrentControllerWorking()) {
					JOptionPane.showMessageDialog(null, "����" + a.getViewRow()
							+ "����ִ���У��������øò����״̬��", "warnning",
							JOptionPane.INFORMATION_MESSAGE);
					continue;
				}
				ScriptCase script = a.getCurrentScript();
				script.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);
				script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
				script.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
				script.setReuslt();
				ui.getTableUI().changeStatus(a.getViewRow() - 1, "");
				
			}

		} else if (type == 0) {
			for (ActionController a : findControllersBySelectedRows()) {
				if (a.isCurrentControllerWorking()) {
					JOptionPane.showMessageDialog(null, "����" + a.getViewRow()
							+ "����ִ���У��������øò����״̬��", "warnning",
							JOptionPane.INFORMATION_MESSAGE);
					continue;
				}
				ScriptCase script = a.getCurrentScript();
				script.setAttribute(ScriptCase.ATTR_REFF_FLAG, false);
				script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, false);
				script.setAttribute(ScriptCase.ATTR_FINISH_FLAG, false);
				script.setReuslt();
				ui.getTableUI().changeStatus(a.getViewRow() - 1, "δִ��");
				ui.getTableUI().changeBeginEndTime(a.getViewRow() - 1, 0, 0);
				ui.getTableUI().changeCostTime(a.getViewRow() - 1, 0);

			}

		} else
			return;
		saveSheetInfo();
		ui.changeCurrentStatus(getStatus());

	}

	public void clearSheetInfo() {
		ApplStatusSave.getinstance().clear();
	}

	public void deleSheetController() {
		this.getUI().setFuncStatus(true);
		this.setViewflag(false);
	}

	/**************************************************************************
	 * �Զ�ִ�е�ǰ�����е����нű�,������ű�ִ�к��״̬
	 *************************************************************************/
	public void execAutoActions() {
		new Thread() {
			@Override
			public void run() {
				applExec.execActions();
				ui.setFuncStatus(true);
			}
		}.start();
	}

	/**************************************************************************
	 * ��ָ���ĵط���ʼִ�нű�,������ű�ִ�к��״̬
	 *************************************************************************/
	public void execAutoActions(int stepId) {

		applExec.setCurrentStep(stepId);
		execAutoActions();
	}

	/**************************************************************************
	 * ִ��ѡ�õĲ���
	 *************************************************************************/
	public void executSelectStep(int stepId) {
		currentRunStepId = stepId;
		new Thread() {
			@Override
			public void run() {
				applExec.executSelectStep(currentRunStepId);
				ui.setFuncStatus(true);
			}
		}.start();
	}

	/**************************************************************************
	 * ������ͼɸѡ������������ͼ
	 ***********************************************************************/
	public void filterViewByExecDateAndPhaseAndCase(String date, String phase,
			String filter, String result, String stepType) {
		ui.getTableUI().clearAll();
		preferredRowHeight.clear();

		logger.info("ɸѡ����:������ " + date + ", ����ʱ�� " + phase + ", ɸѡ�ֶ�: "
				+ filter + ", ִ�н�� " + result + ", ����(�Զ�/�ֶ�) " + stepType);
		int j = 1;

		for (ActionController c : actionsController) {
			c.setFilterViewShow(false);
			c.setViewRow(-1);
		}
		// ��ȡ�ϴ�ִ�н���ļ�
		Hashtable<String, StatusResult> status = ApplStatusSave.getinstance()
				.readStatus();

		for (int i = 0; i < actionsController.size(); i++) {
			ActionController c = actionsController.get(i);
			ScriptCase script = c.getCurrentScript();
			FrameworkCase frmcase = script.getFrmCase();
			if ((frmcase.getTestDate().trim().equals(date) || date
					.equals("ѡ������"))
					&& (frmcase.getTestPhase().trim().equals(phase) || phase
							.equals("ѡ��ʱ��"))
					&& (frmcase.getDescrip().indexOf(filter) != -1
							|| frmcase.getTestContent().indexOf(filter) != -1
							|| frmcase.getTestAntic().indexOf(filter) != -1
							|| frmcase.getTestBatch().indexOf(filter) != -1
							|| frmcase.getScriptId().indexOf(filter) != -1 || filter
								.equals("��������ID"))
					&& (script.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
							&& stepType.equals("�Զ�ִ��")
							|| !script.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
							&& stepType.equals("�ֶ�ִ��") || stepType
								.equals("ѡ������"))) {

				if ((result.equals(ConstValues.result1) && !script
						.getAttribute(ScriptCase.ATTR_REFF_FLAG))
						|| (result.equals(ConstValues.result2) && script
								.getProcessStatus() == ScriptCase.PROCESS_STATUS_RUN)
						|| (result.equals(ConstValues.result3)
								&& script
										.getAttribute(ScriptCase.ATTR_FINISH_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_REFF_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_AUTO_FLAG) && (!script
								.getMannul() || script.getMannul()
								&& !script.isFaileflag()))
						|| (result.equals(ConstValues.result7)
								&& script
										.getAttribute(ScriptCase.ATTR_FINISH_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_REFF_FLAG) && (!script
								.getAttribute(ScriptCase.ATTR_AUTO_FLAG) || script
								.getMannul() && script.isFaileflag()))
						|| (result.equals(ConstValues.result4)
								&& script
										.getAttribute(ScriptCase.ATTR_FINISH_FLAG)
								&& !script
										.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_REFF_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_AUTO_FLAG) && !script
									.getMannul())
						|| (result.equals(ConstValues.result5)
								&& !script
										.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_REFF_FLAG)
								&& script
										.getAttribute(ScriptCase.ATTR_AUTO_FLAG) && script
									.getMannul())
						|| (result.equals(ConstValues.result6)
								&& !script
										.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
								&& !script
										.getAttribute(ScriptCase.ATTR_SKIP_FLAG)
								&& !script
										.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG) && script
									.getAttribute(ScriptCase.ATTR_REFF_FLAG))
						|| result.equals("ѡ����")) {

					String id = frmcase.getScriptId();
					String caseDate = frmcase.getTestDate();
					String idkey = id + caseDate;
					if (status.containsKey(idkey)) {

						String costtime = ((StatusResult) status.get(idkey))
								.getCostTime();
						long costtim = costtime.trim().equals("") ? 0 : Long
								.parseLong(costtime);
						String begintime = ((StatusResult)status.get(idkey)).getBeginTime();
						String endtime = ((StatusResult)status.get(idkey)).getEndTime();
						
						long begintim = begintime.trim().equals("")?0:Long.parseLong(begintime);
						long endtim = endtime.trim().equals("")?0:Long.parseLong(endtime);
						if (status.containsKey(idkey)
								&& ((StatusResult) status.get(idkey))
										.getResult().equals("1")) {
							script.setAttribute(ScriptCase.ATTR_FINISH_FLAG,
									true);
							script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG,
									true);
							script.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);							

						} else if (status.containsKey(idkey)
								&& ((StatusResult) status.get(idkey))
										.getResult().equals("0")) {
							script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG,
									false);
							script.setAttribute(ScriptCase.ATTR_REFF_FLAG, true);
							if (script.getAttribute(ScriptCase.ATTR_AUTO_FLAG)
									&& !script.getMannul()) {
								script.setAttribute(
										ScriptCase.ATTR_FINISH_FLAG, true);
							}							
						}
						c.setCostTime(costtim);
						c.setBeginTime(begintim);
						c.setEndTime(endtim);
					}

					c.setFilterViewShow(true);
					c.setViewRow(j);
					ui.getTableUI().addRow(c);
					ui.getTableUI().changeCostTime(c.getViewRow() - 1,
							c.getCostTime());
					ui.getTableUI().changeBeginEndTime(c.getViewRow() - 1,
							c.getBeginTime(), c.getEndTime());
					j++;

				}
			}
		}
		logger.info("ɸѡ�����������:" + j);
	}

	/*
	 * ͨ��ѡ���������õ���Ӧ�Ŀ�����
	 */
	public ActionController findControllerBySSelectedRow() {
		// return actionsController.get(getCurrentSelectedRow());

		for (ActionController c : actionsController) {
			if (c.getViewRow() == getCurrentSelectedRow()) {
				return c;
			}
		}
		return null;

	}

	/**
	 * ��ѡ����м��������
	 * 
	 * @return
	 */
	public ArrayList<ActionController> findControllersBySelectedRows() {
		ArrayList<ActionController> selectedControllers = new ArrayList<ActionController>();

		if (currentSelectedRows == null || currentSelectedRows.length == 0)
			return selectedControllers;

		for (int j : currentSelectedRows) {
			for (ActionController c : actionsController) {
				if (c.getViewRow() - 1 == j)
					selectedControllers.add(c);
			}
		}

		return selectedControllers;
	}

	public ArrayList<ActionController> getActionsController() {
		return actionsController;
	}

	public int getCurrentRunRow() {
		return currentRunRow;
	}

	public int getCurrentRunStepId() {
		return currentRunStepId;
	}

	public int getCurrentSelectedRow() {
		return currentSelectedRow;
	}

	public ActionController getCurrentWorkingController() {
		return currentWorkingController;
	}

	public int getPreferredRowHeight(int row) {
		Integer a = (Integer) preferredRowHeight.get(row);
		if (a != null) {
			return a;
		}
		return -1;
	}

	public Boolean getScrollAllowed() {
		return scrollAllowed;
	}

	@Override
	public String getSheetName() {
		return this.SheetName;
	}

	/**
	 * 
	 * ����˵����ͳ��ִ����� 2013-11-15 ����3:38:58
	 * 
	 * @return
	 */
	public String getStatus() {
		int wrongNum = 0;
		int hasRunNum = 0;
		int MannulNum = 0;
		for (int i = 0; i < actionsController.size(); i++) {
			ScriptCase c = actionsController.get(i).getCurrentScript();
			if (c.getAttribute(ScriptCase.ATTR_REFF_FLAG)
					&& !(c.getProcessStatus() == ScriptCase.PROCESS_STATUS_RUN))
				hasRunNum++;
			if (c.getAttribute(ScriptCase.ATTR_REFF_FLAG)
					&& c.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
					&& (!c.getAttribute(ScriptCase.ATTR_AUTO_FLAG) || c
							.getMannul()))
				MannulNum++;
			if (c.getAttribute(ScriptCase.ATTR_REFF_FLAG)
					&& !c.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG)
					&& !(c.getProcessStatus() == ScriptCase.PROCESS_STATUS_RUN))
				wrongNum++;
		}
		if (hasRunNum == actionsController.size()) {
			testResultFileCreate(PropConfig.getExecuteAnalyseFile());
		}
		return wrongNum + "/" + hasRunNum + "/" + MannulNum + "/"
				+ actionsController.size();
	}

	public int getStopRunRow() {
		return stopRunRow;
	}

	public int getStopRunStep() {
		return stopRunStep;
	}

	public ArrayList<String> getTradePhase() {
		return tradePhase;
	}

	@Override
	public ConditionSheetScreen getUI() {
		return ui;
	}

	public void handleActiveNotify(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();

		ui.getTableUI().changeStatus((Integer) c[0] - 1, (String) c[3]);
		ui.changeTotalCostTime(costTime);
		ui.changeCurrentStatus(getStatus());

	}

	public void handleAppendTxt(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();
		currentRunStepId = (Integer) c[0];
		currentRunRow = actionsController.get(currentRunStepId).getViewRow();

		ui.changeScriptStep((Integer) c[0], null);
		ui.getResutTxt().append((String) c[1]);
		ui.getResutTxt().append("\n");
	}

	public void handleChangeStopRunStep(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();

		stopRunRow = (Integer) c[2];

	}

	public void handleChangPhase(ApplEvt evt) {
		ui.changePhase(tradePhase.toArray());
	}

	/**
	 * �Ѳ�����������ʾ��UI��
	 * 
	 * @param testcasesFile
	 * @return
	 */
	public boolean handLoadCasesGatherToUI(String testcasesFile) {
		this.SheetName = testcasesFile;
		logger.debug("��ȡ�����ɺõĲ�����������" + testcasesFile);
		try {
			applExec = new ApplExecuteController(this, testcasesFile);
			actionsController = applExec.getActionsController();
			tradePhase = applExec.getTradePhase();

		} catch (ApplFatalException ex) {
			return false;
		}

		handleChangPhase(null);

		ui.getTableUI().clearAll();

		for (int i = 0; i < actionsController.size(); i++) {

			if (ui != null) {
				// ��ʾ�Զ��ű��Ĳ���
				ui.getTableUI().addRow(actionsController.get(i));
			}

		}
		return true;
	}

	/* �򿪲�������Ի��� */
	public void handleOpenAction(ApplEvt evt) {

		if (aThread != null && aThread.isAlive())
			aThread.stop();
		if (currentChooseActionController != null) {
			if (actionScreen != null)
				actionScreen.dispose();

		}

		ArrayList<ActionController> c = findControllersBySelectedRows();

		if (c.size() != 0) {
			currentChooseActionController = c.get(0);
			ScriptCase scriptcase = currentChooseActionController
					.getCurrentScript();
			actionScreen = new CaseDetailScreen(scriptcase);
			aThread = new Thread(actionScreen);
			aThread.start();
		}
	}

	public void handleStopNotify(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();
		addCostTime((Long) c[1]);
		ui.getTableUI().changeStatus((Integer) c[0] - 1, (String) c[3]);
		ui.getTableUI().changeCostTime((Integer) c[0] - 1, (Long) c[1]);
		ui.changeTotalCostTime(costTime);

		ui.changeCurrentStatus(getStatus());
		ui.getTableUI().changeRunPhase((Integer) c[0] - 1, (Long) c[5],
				(Long) c[6]);
		saveSheetInfo();
	}

	public void handleTableScroll(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();
		currentRunRow = (Integer) c[0];

		ui.tableScroll(currentRunRow, scrollAllowed);
	}

	public void handleWarnNotify(ApplEvt evt) {
		Object[] c = (Object[]) evt.getArgs();
		addCostTime((Long) c[1]);
		ui.getTableUI().changeCostTime((Integer) c[0] - 1, (Long) c[1]);
		ui.getTableUI().changeStatus((Integer) c[0] - 1, (String) c[3]);
		ui.changeTotalCostTime(costTime);
		ui.changeCurrentStatus(getStatus());
		ui.getTableUI().changeRunPhase((Integer) c[0] - 1, (Long) c[5],
				(Long) c[6]);
		saveSheetInfo();
	}

	public boolean is() {
		return viewflag;
	}

	@Override
	public boolean isApplSheetWorking() {
		for (ActionController c : actionsController) {
			if (c.isCurrentControllerWorking()) {
				return true;
			}
		}
		return false;
	}

	public boolean isStop() {
		return isStop;
	}

	public boolean isSuspend() {

		return isSuspend;
	}

	public boolean isViewflag() {
		return false;
	}


	public void postEvt(ApplEvt evt) {
		evt.setSender(this);
		if (evt.getReceiver() == null) {
			evt.setReceiver(this);
		}
		ApplEvtQueue.getInstance().post(evt);
	}

	/**************************************************************************
	 * ���¿�ʼִ�У������в���Ϊδִ��״̬
	 *************************************************************************/
	public void reStartExec(Boolean stopFlag) {
		if (applExec == null)
			return;

		ui.setButtonStartFlag(stopFlag);
		isSuspend = false;
		isStop = false;
		stopRunStep = -1;
		scrollAllowed = true;/* �Ƿ�������� */
		currentRunStepId = 0;
		applExec.reStartExec();
		ui.getResutTxt().setText("");
		ui.repaint();

	}

	@Override
	public synchronized void saveSheetInfo() {

		new Thread() {
			@Override
			public void run() {
				ApplStatusSave.getinstance().writStatus(actionsController);
			}
		}.start();
	}

	private void sendNotifyEvent(String messgeID) {
		Object params[] = { isSuspend, isStop, stopRunStep };
		ApplEvt evt1 = new ApplEvt(messgeID, params);
		evt1.setReceiver(applExec);
		postEvt(evt1);

	}

	public void setActionsController(
			ArrayList<ActionController> actionsController) {
		this.actionsController = actionsController;
	}

	public void setCurrentWorkingController(ActionController c) {
		currentWorkingController = c;
	}

	public void setPreferredRowHeight(int row, int height) {
		preferredRowHeight.put(row, height);
	}

	public void setScrollAllowed(Boolean scrollAllowed) {
		this.scrollAllowed = scrollAllowed;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
		sendNotifyEvent(ApplEvt.EVT_ID_STOP_ACTION);
	}

	public void setStopRunStep(int stopRunStep) {
		this.stopRunStep = stopRunStep;
		if (stopRunStep < 0) {
			this.stopRunRow = -1;
		} else {
			this.stopRunRow = actionsController.get(stopRunStep).getViewRow();
		}
		sendNotifyEvent(ApplEvt.EVT_ID_SETSTOPID_ACTION);
	}

	public void setSuspend(boolean isSuspend) {

		this.isSuspend = isSuspend;
		sendNotifyEvent(ApplEvt.EVT_ID_PAUSE_SCRIPT);
	}

	public void setViewflag(boolean viewflag) {
		this.viewflag = viewflag;
	}

	/**************************************************************************
	 * ����ִ��
	 *************************************************************************/
	public void sigleStepProcess() {
		new Thread() {
			@Override
			public void run() {
				applExec.sigleStepProcess();
				ui.setFuncStatus(true);
			}
		}.start();

	}

	public void testResultFileCreate(String fileName) {
		applExec.testResultFileCreate(fileName);
	}

	private void updateCostTime() {
		for (ActionController c : actionsController) {
			this.costTime += c.getCostTime();
		}
	}

}
