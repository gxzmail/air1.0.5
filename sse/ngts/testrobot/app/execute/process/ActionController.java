package sse.ngts.testrobot.app.execute.process;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.IReceiver;
import sse.ngts.testrobot.engine.ISender;
import sse.ngts.testrobot.exception.ApplPauseException;
import sse.ngts.testrobot.factory.EXECUFactory;

public class ActionController implements ISender, IReceiver {
	private ScriptCase currentScript;

	private ConditionSheetController sheetController;

	private boolean filterViewShow = true;
	private boolean isCurrentControllerWorking = false;

	private long beginTime;
	private long endTime;
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	private long startTime = 0;
	private int viewRow;
	private long cumulatedExecTime = 0;

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"HHmmss");
	{
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	}

	public ActionController(ConditionSheetController c) {
		this.sheetController = c;

	}

/*	public void changRowTime() {
		endTime = System.currentTimeMillis();
		this.cumulatedExecTime += (endTime - beginTime);
		getApplSheetUIController().addCostTime(endTime - beginTime);
		sendNotifyEvent(ApplEvt.EVT_ID_STOP_NOTIFY);
	}*/

	/* 获得脚本所属于的用例表格 */
	public ConditionSheetController getApplSheetUIController() {
		return sheetController;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public long getCostTime() {
		return cumulatedExecTime;
	}

	public ScriptCase getCurrentScript() {
		return currentScript;
	}

	public boolean getFilterViewShow() {
		return filterViewShow;
	}

	public int getViewRow() {
		return viewRow;
	}
	
	/**
	 * 
	 * 函数说明：执行步骤
	 * 2013-11-15 下午4:03:40
	 * @param evt
	 */
	public void handleStartScript(ApplEvt evt) {

		if (isCurrentControllerWorking) {
			return;
		}
		isCurrentControllerWorking = true;
		sendNotifyEvent(ApplEvt.EVT_ID_ACTIVE_NOTIFY);
		sendNotifyEvent(ApplEvt.EVT_ID_SCROLL_ACTION);
		currentScript.setProcessStatus(ScriptCase.PROCESS_STATUS_RUN);
		playScriptCore();
	}

	public void initScript(ScriptCase script) {
		currentScript = script;

	}

	public synchronized boolean isCurrentControllerWorking() {
		return isCurrentControllerWorking;
	}

	public boolean isStopRun() {
		return sheetController.isSuspend();
	}

	private void playScriptCore() {
		currentScript.setProcessStatus(ScriptCase.PROCESS_STATUS_RUN);
		sheetController.setCurrentWorkingController(this);
		beginTime = System.currentTimeMillis();
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
		try {
			IExecuteProcess executProcess = EXECUFactory
					.getApplExecuteProcess();
			executProcess.setActioncontroller(this);
			executProcess.stepProcess(currentScript);
			currentScript.setProcessStatus(ScriptCase.PROCESS_STATUS_STOP);
			endTime = System.currentTimeMillis();
			this.cumulatedExecTime += (endTime - beginTime);
			getApplSheetUIController().addCostTime(endTime - beginTime);
			sendNotifyEvent(ApplEvt.EVT_ID_STOP_NOTIFY);

		} catch (ApplPauseException ex) {
			endTime = System.currentTimeMillis();
			this.cumulatedExecTime += (endTime - beginTime);
			getApplSheetUIController().addCostTime(endTime - beginTime);
			ex.printStackTrace();
			currentScript.setProcessStatus(ScriptCase.PROCESS_STATUS_STOP);
			sendNotifyEvent(ApplEvt.EVT_ID_WARN_NOTIFY);
		}
		sheetController.setCurrentWorkingController(null);
		isCurrentControllerWorking = false;
	}
	
	/**
	 * 
	 * 函数说明：TODO
	 * 2013-11-15 下午4:00:17
	 * @param evt
	 */
	public void postEvt(ApplEvt evt) {
		evt.setSender(this);
		if (evt.getReceiver() == null) {
			evt.setReceiver(this);
		}
		ApplEvtQueue.getInstance().post(evt);
	}
	
	/**
	 * 
	 * 函数说明：发送更新窗体消息
	 * 2013-11-15 下午3:59:48
	 * @param messgeID
	 */
	private void sendNotifyEvent(String messgeID) {
		if (viewRow <= 0)
			return;
		String result = currentScript.getTestResultDescr();
		String id = this.currentScript.getFrmCase().getStepsId();

		Object params[] = { viewRow, cumulatedExecTime,
				isCurrentControllerWorking, result, id, startTime, endTime };

		ApplEvt evt1 = new ApplEvt(messgeID, params);
		evt1.setReceiver(sheetController);
		postEvt(evt1);

	}

	/* 设置脚本所属于的用例表格 */
	public void setApplSheetUIController(ConditionSheetController c) {
		this.sheetController = c;
	}

	public void setCostTime(long time) {
		cumulatedExecTime = time;
	}

	public void setCurrentControllerWorking(boolean isCurrentControllerWorking) {
		this.isCurrentControllerWorking = isCurrentControllerWorking;
	}

	public void setFilterViewShow(boolean t) {
		filterViewShow = t;
	}

	/**/
	public void setViewRow(int viewRow) {
		this.viewRow = viewRow;
	}

}
