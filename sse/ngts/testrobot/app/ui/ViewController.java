package sse.ngts.testrobot.app.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.interf.ISheetController;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetScreen;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.IReceiver;
import sse.ngts.testrobot.engine.ISender;
import sse.ngts.testrobot.engine.app.PropConfig;

public class ViewController implements ISender, IReceiver {
	private static ViewController instance = null;

	public static ViewController getInstance() {
		if (instance == null) {
			instance = new ViewController();
		}
		return instance;
	}

	private ArrayList<ISheetController> sheetControllers;

	private Logger logger = Logger.getLogger(ViewController.class);

	private ViewScreen ui;

	private ViewController() {
		ui = new ViewScreen(this);
		sheetControllers = new ArrayList<ISheetController>();
	}

	/**
	 * ���룺String fileName�D�DҪ�򿪵ı��� ArrayList<ApplFrmwkCase> steps �D�D ���еĲ���������Ϣ
	 * ������� ���ܣ��½�һ������һ��ConditionSheet���ļ���ͼ
	 */

	public boolean conditionSheetViewDete() {
		for (ISheetController c : sheetControllers) {
			if (c instanceof ConditionSheetController
					&& !((ConditionSheetController) c).isViewflag()) {
				return false;
			}
		}
		return true;

	}

	public ViewScreen getUI() {
		return ui;
	}

	public void handleAddConditionSheet(String fileName) {
		/**
		 * �жϴ����Ƿ�򿪹�
		 */
		for (ISheetController c : sheetControllers) {
			if (c instanceof ConditionSheetController
					&& ((ConditionSheetController) c).getSheetName()
							.equalsIgnoreCase(fileName)
					&& ((ConditionSheetController) c).isViewflag()) {
				ui.selectedTabSheeView((ConditionSheetController) c);
				return;
			} else if (c instanceof ConditionSheetController
					&& !((ConditionSheetController) c).isViewflag()) {
//				ui.addNewTabSheetView(c, fileName);
//				return;
			}
		}

		ConditionSheetController c = new ConditionSheetController();

		ui.addNewTabSheetView(c, fileName);

		// ���ؽű�
		boolean loadFlag = c.handLoadCasesGatherToUI(fileName);

		// c.LoadLastResult();

		c.getUI().changeCurrentStatus(c.getStatus());
		sheetControllers.add(c);
		c.setViewflag(true);
		if (!loadFlag) {
			ViewController.getInstance().showWarnMessage();
		}

	}

	public void handleOpenTestResultSheet(ApplEvt evt) {

		// String fileDirc = System.getProperty("user.dir");
		String filePath = PropConfig.getExecuteAnalyseFile();

		try {

			Runtime.getRuntime().exec("cmd /c start " + filePath);
		} catch (IOException ex) {
			logger.error("δ֪�쳣{0}" + ex.getMessage());
		}

	}

	public void handleRemoveSheetView(ConditionSheetScreen removeUI) {
		for (ISheetController c : sheetControllers) {
			if (c.getUI() == removeUI) {
				// sheetControllers.remove(c);
				((ConditionSheetController) c).deleSheetController();
				return;
			}
		}

	}

	public void handleTestResult() {

		for (ISheetController c : sheetControllers) {
			if (c instanceof ConditionSheetController
					&& ((ConditionSheetController) c).getSheetName()
							.equalsIgnoreCase(PropConfig.getExecuteFile())) {
				((ConditionSheetController) c).testResultFileCreate(PropConfig
						.getExecuteAnalyseFile());
			}
		}

	}

	/**
	 * �жϵ�ǰ�����Ƿ�����ִ��
	 * 
	 * @return
	 */
	public boolean isApplEngineWorking() {
		for (ISheetController c : sheetControllers) {
			if (c.isApplSheetWorking()) {
				return true;
			}
		}
		return false;
	}

	public void restartConditionSheet(Boolean closeFlag) {
		for (ISheetController c : sheetControllers) {
			if (c instanceof ConditionSheetController
					&& !((ConditionSheetController) c).isViewflag()) {
				((ConditionSheetController) c).reStartExec(closeFlag);
			}
		}

	}

	public void showWarnMessage() {
		JOptionPane.showMessageDialog(ui, "��ִ���ֲ�ʧ��!");
	}

}
