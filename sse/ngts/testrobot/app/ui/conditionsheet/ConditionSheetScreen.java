package sse.ngts.testrobot.app.ui.conditionsheet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.TablecellBalloonTip;
import net.java.balloontip.positioners.LeftAbovePositioner;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.utils.ToolTipUtils;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.process.ActionController;
import sse.ngts.testrobot.app.ui.ViewScreen;

@SuppressWarnings("serial")
public class ConditionSheetScreen extends JSplitPane {
	private Logger logger = Logger.getLogger(ConditionSheetScreen.class);

	private ConditionSheetTable tableUI;
	private ConditionSheetController c;
	private TablecellBalloonTip tableBalloonTip;

	private JComboBox viewList;
	private JComboBox phaseComboBox;
	private JTextField caseTxt;
	private JComboBox stepResult;
	private JComboBox stepType;
	private JLabel costTimeLabel;
	private JLabel curruntStatus;
	private JLabel curruntStep;
	private JLabel runStatus;
	private JTextArea resutTxt = new JTextArea();

	/** 清空按钮 */
	private JButton buttonClear;
	private JButton buttonStart;
	private JButton buttonStop;
	private JButton buttonResume;
	private JButton buttonSuspend;
	private JButton buttonSingleStepRun;
	private JButton buttonStopScroll;
	private JButton buttonCurrentRunStep;
	private JButton btnCmd;

	private boolean buttonStartFlag = false;
	private boolean popStarEn = true;
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"HH:mm:ss");
	{
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
	}

	public ConditionSheetScreen(ConditionSheetController c) {
		this.c = c;
		createComponent();
		createAction();

	}

	/**
	 * 
	 * 函数说明：设置执行情况 2013-11-15 下午2:59:18
	 */
	public void changeCurrentStatus(final String status) {
		new Thread() {
			@Override
			public void run() {
				runStatus.setText("当前运行结果：   错误数/已执行数/手动执行数/总步骤数:" + status);
			}
		}.start();
	}

	/**
	 * 
	 * 函数说明：TODO 2013-11-15 下午3:00:34
	 * 
	 * @param label2
	 */
	public void changePhase(Object[] label2) {
		for (int i = 0; i < label2.length; i++)
			phaseComboBox.addItem(label2[i]);
	}

	public void changeScriptStep(int step, String str) {
		curruntStep.setText("      当前运行步骤：" + (step + 1));
	}

	public void changeTotalCostTime(long costTime) {
		costTimeLabel.setText("      运行时间:"
				+ dateFormatter.format(new Date(costTime)));
	}

	private void createAction() {

		btnCmd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Runtime.getRuntime().exec("cmd /c start start.bat");

				} catch (IOException e1) {
					logger.error("未知错误{0}"+ e1.getMessage());
				}
			}

		});

		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (buttonStartFlag) {

					/* 2012-04-19 modified by wuli ,start */
					Object options[] = { "取消", "确认" };
					int selection = JOptionPane.showOptionDialog(null,
							"重新开始会清除执行记录，要重新开始执行脚本吗？", "警告",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);

					if (selection == 0)
						return;
					else if (selection == 1) {
						c.reStartExec(false);
						c.clearSheetInfo();
						buttonStartFlag = true;
					}
					/* 2012-04-19 modified by wuli ,end */

				} else {
					buttonStartFlag = true;
				}
				c.setStop(false);
				c.setSuspend(false);
				c.execAutoActions();
				setFuncStatus(false);
				buttonStop.setEnabled(true);

			}

		});

		buttonSuspend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setSuspend(true);
				setFuncStatus(true);
			}

		});
		buttonResume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (c.isApplSheetWorking()) {
					JOptionPane.showMessageDialog(null, "当前步骤仍在执行中，不能执行选定步骤！",
							"warnning", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				c.setSuspend(false);
				c.execAutoActions();
				setFuncStatus(false);
			}

		});
		buttonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object options[] = { "取消", "确认" };

				int selection = JOptionPane.showOptionDialog(null,
						"需要停止当前的执行吗?", "警告", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				if (selection == 0)
					return;
				else if (selection == 1) {
					c.setSuspend(true);
					setFuncStatusStop();
				}
			}
		});

		buttonSingleStepRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (c.isApplSheetWorking()) {
					JOptionPane.showMessageDialog(null, "当前步骤仍在执行中，不能执行选定步骤！",
							"warnning", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				c.sigleStepProcess();
				setFuncStatus(false);
			}

		});
		buttonCurrentRunStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableUI.scrollRectToVisible(tableUI.getCellRect(
						c.getCurrentRunRow() - 1, 0, true));

			}

		});
		viewList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object item = ((JComboBox) e.getSource()).getSelectedItem();
				c.filterViewByExecDateAndPhaseAndCase(item.toString(),
						phaseComboBox.getSelectedItem().toString(), caseTxt
								.getText().toString(), stepResult
								.getSelectedItem().toString(), stepType
								.getSelectedItem().toString());
			}

		});

		phaseComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object item = ((JComboBox) e.getSource()).getSelectedItem();
				c.filterViewByExecDateAndPhaseAndCase(viewList
						.getSelectedItem().toString(), item.toString(), caseTxt
						.getText().toString(), stepResult.getSelectedItem()
						.toString(), stepType.getSelectedItem().toString());
			}

		});
		caseTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				c.filterViewByExecDateAndPhaseAndCase(viewList
						.getSelectedItem().toString(), phaseComboBox
						.getSelectedItem().toString(), caseTxt.getText()
						.toString(), stepResult.getSelectedItem().toString(),
						stepType.getSelectedItem().toString());

			}

		});
		stepResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				c.filterViewByExecDateAndPhaseAndCase(viewList
						.getSelectedItem().toString(), phaseComboBox
						.getSelectedItem().toString(), caseTxt.getText()
						.toString(), stepResult.getSelectedItem().toString(),
						stepType.getSelectedItem().toString());

			}

		});
		stepType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				c.filterViewByExecDateAndPhaseAndCase(viewList
						.getSelectedItem().toString(), phaseComboBox
						.getSelectedItem().toString(), caseTxt.getText()
						.toString(), stepResult.getSelectedItem().toString(),
						stepType.getSelectedItem().toString());

			}

		});
		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				viewList.setSelectedItem("选择日期");
				phaseComboBox.setSelectedItem("选择时段");
				caseTxt.setText("");
				stepResult.setSelectedItem("选择结果");
				stepType.setSelectedItem("选择类型");
				c.filterViewByExecDateAndPhaseAndCase(viewList
						.getSelectedItem().toString(), phaseComboBox
						.getSelectedItem().toString(), caseTxt.getText()
						.toString(), stepResult.getSelectedItem().toString(),
						stepType.getSelectedItem().toString());

			}

		});

		tableUI.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				c.changeSelectedRows(tableUI.getSelectedRows());
				tableUI.repaint();
				if (e.getClickCount() == 2) {
					c.handleOpenAction(null);
				} else if (e.getButton() == 3) {
					ConditionSheetPopup popui = new ConditionSheetPopup(
							popStarEn, c);
					popui.init(tableUI, c.findControllersBySelectedRows());
					popui.show(e.getX(), e.getY());
				}

			}

		});

		tableUI.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				c.changeSelectedRows(tableUI.getSelectedRows());

				// TODO
				/* F1 查看详细说明 有Bug功能，会造成界面略卡和多行显示刷新问题 */
				if (e.getKeyCode() == KeyEvent.VK_F1) {

					if (tableBalloonTip == null || !tableBalloonTip.isVisible()) {
						ArrayList<ActionController> selectedControllers = c
								.findControllersBySelectedRows();
						if (selectedControllers.isEmpty()) {
							return;
						}
						ActionController selectedController = selectedControllers
								.get(0);

						int row = tableUI.getSelectedRow();
						int col = tableUI.getSelectedColumn();

						String labeltext = selectedController
								.getCurrentScript().getFrmCase()
								.getTestContent();
						JTextArea tips = new JTextArea(labeltext);
						tips.setAutoscrolls(true);
						tips.setRows(labeltext.length() > 200 ? labeltext
								.length() / 200 : 1);
						tips.setColumns(labeltext.length() > 200 ? 200
								: labeltext.length());
						tips.setLineWrap(true);

						tableBalloonTip = new TablecellBalloonTip(tableUI,
								tips, row, col, new EdgedBalloonStyle(
										new Color(105, 253, 245), new Color(64,
												64, 64)),
								new LeftAbovePositioner(1, 20), null);

						tableUI.repaint();
					} else {
						tableBalloonTip.setVisible(false);
						tableBalloonTip.closeBalloon();
						tableBalloonTip = null;
						tableUI.repaint();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					c.changeSelectedRows(tableUI.getSelectedRows());
					tableUI.repaint();
					c.handleOpenAction(null);
				}
				/* F5单步执行 */
				else if (e.getKeyCode() == KeyEvent.VK_F5) {

					if (c.isApplSheetWorking()) {
						JOptionPane.showMessageDialog(null,
								"当前步骤仍在执行中，不能执行选定步骤！", "warnning",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					c.sigleStepProcess();
					setFuncStatus(false);
					repaint();

				}
				/* F6执行当前步骤 */
				else if (e.getKeyCode() == KeyEvent.VK_F6) {

					ArrayList<ActionController> selectedControllers = c
							.findControllersBySelectedRows();
					if (selectedControllers.isEmpty()) {
						return;
					}
					ActionController selectedController = selectedControllers
							.get(0);

					selectedController.getApplSheetUIController()
							.executSelectStep(
									Integer.parseInt(selectedController
											.getCurrentScript().getFrmCase()
											.getStepsId()) - 1);
					setFuncStatus(false);
					c.setSuspend(false);
					repaint();
				}
				/* 设置断点 */
				else if (e.getKeyCode() == KeyEvent.VK_F7) {
					ArrayList<ActionController> selectedControllers = c
							.findControllersBySelectedRows();
					if (selectedControllers.isEmpty()) {
						return;
					}
					ActionController selectedController = selectedControllers
							.get(0);
					int isbreakpointid = selectedController
							.getApplSheetUIController().getStopRunStep();
					int curbreakpointid = Integer.parseInt(selectedController
							.getCurrentScript().getFrmCase().getStepsId()) - 1;
					if (curbreakpointid != isbreakpointid) {
						selectedController.getApplSheetUIController()
								.setStopRunStep(curbreakpointid);
					} else {
						selectedController.getApplSheetUIController()
								.setStopRunStep(-1);
					}
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_F8) {
					if (c.isApplSheetWorking()) {
						JOptionPane.showMessageDialog(null,
								"当前步骤仍在执行中，不能执行选定步骤！", "warnning",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					c.setSuspend(false);
					ArrayList<ActionController> selectedControllers = c
							.findControllersBySelectedRows();
					if (selectedControllers.size() == 0)
						return;
					ActionController a = selectedControllers.get(0);
					a.getApplSheetUIController().execAutoActions(
							Integer.parseInt(a.getCurrentScript().getFrmCase()
									.getStepsId()) - 1);
					c.getUI().setFuncStatus(false);
					c.setSuspend(false);
					repaint();
				}
				
				/* 设置标记成功 */
				if (e.getKeyCode() == KeyEvent.VK_1) {

					new Thread() {
						@Override
						public void run() {
							if (!c.findControllersBySelectedRows().isEmpty()) {

								int selection = JOptionPane.showConfirmDialog(
										null, "需要设置所有选定的步骤为\"执行成功\"标记吗？", "警告",
										JOptionPane.OK_CANCEL_OPTION,
										JOptionPane.WARNING_MESSAGE);
								if (selection == JOptionPane.CANCEL_OPTION)
									return;
								else if (selection == JOptionPane.OK_OPTION) {
									c.changeStepStatus(1);
								}
							}
						}
					}.start();
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_0) {
					new Thread() {
						@Override
						public void run() {
							if (!c.findControllersBySelectedRows().isEmpty()) {
								int selection = JOptionPane.showConfirmDialog(
										null, "需要设置所有选定的步骤为\"未执行\"标记吗？", "警告",
										JOptionPane.OK_CANCEL_OPTION,
										JOptionPane.WARNING_MESSAGE);
								if (selection == JOptionPane.CANCEL_OPTION)
									return;
								else if (selection == JOptionPane.OK_OPTION) {
									c.changeStepStatus(0);

								}
							}
						}
					}.start();
					repaint();

				} else if (e.getKeyCode() == KeyEvent.VK_2) {
					new Thread() {
						@Override
						public void run() {
							if (!c.findControllersBySelectedRows().isEmpty()) {
								int selection = JOptionPane.showConfirmDialog(
										null, "需要设置所有选定的步骤为\"执行失败\"标记吗？", "警告",
										JOptionPane.OK_CANCEL_OPTION,
										JOptionPane.WARNING_MESSAGE);
								if (selection == JOptionPane.CANCEL_OPTION)
									return;
								else if (selection == JOptionPane.OK_OPTION) {
									c.changeStepStatus(2);
								}
							}
						}
					}.start();
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_9)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "⑨");
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_8)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "⑧");
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_7)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "⑦");
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_6)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "⑥");
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_5)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "⑤");
					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_4)
				{
					int row = tableUI.getSelectedRow();
					tableUI.ReMark(row, "④");
					repaint();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
		});
		buttonStopScroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (c.getScrollAllowed()) {
					c.setScrollAllowed(false);
					buttonStopScroll.setText("恢复滚屏");
					curruntStatus.setText("当前处于停止滚屏状态          ");
				} else {
					c.setScrollAllowed(true);
					buttonStopScroll.setText("停止滚屏");
					curruntStatus.setText("当前处于滚屏状态              ");

				}

			}
		});
	}

	private void createComponent() {
		ImageIcon startIcon = new ImageIcon(
				ViewScreen.class.getResource("/pic/run_exc1.gif"));

		ImageIcon stopIcon = new ImageIcon(
				ViewScreen.class.getResource("/pic/stop.gif"));

		ImageIcon resumeIcon = new ImageIcon(
				ViewScreen.class.getResource("/pic/resume_co.gif"));

		ImageIcon suspendIcon = new ImageIcon(
				ViewScreen.class.getResource("/pic/suspend_co.gif"));
		tableUI = new ConditionSheetTable(c);

		JPanel mainFuncPanel = new JPanel(new BorderLayout());
		JPanel ViewPanel = new JPanel(new BorderLayout());
		costTimeLabel = new JLabel("总运行时间:00:00:00");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableUI);

		JLabel txtLabel = new JLabel("选择日期:");
		String label[] = { "选择日期", "T0", "T1", "T2" };

		JLabel phaseLabel = new JLabel("选择时段:");
		Object[] tradePhase = c.getTradePhase().toArray();
		JLabel caseLabel = new JLabel("筛选:");

		JLabel statusLabel = new JLabel("选择结果:");
		String label3[] = { "选择结果", "等待执行", "执行失败", "执行成功", "需要手动执行", "手动执行完成",
				"失败需手动" };
		JLabel stepTypeLabel = new JLabel("选择类型:");
		String label4[] = { "选择类型", "自动执行", "手动执行" };

		JLabel lblank = new JLabel("                    ");
		btnCmd = new JButton("命令行");
		buttonClear = new JButton("清空");
		buttonStart = new JButton("");
		buttonStop = new JButton("");
		buttonResume = new JButton("");
		buttonCurrentRunStep = new JButton("");
		curruntStatus = new JLabel("当前处于滚屏状态              ");
		curruntStep = new JLabel("当前运行步骤：0  ");
		buttonSuspend = new JButton("");
		buttonSingleStepRun = new JButton("");
		buttonStopScroll = new JButton("");
		buttonStart.setIcon(startIcon);
		buttonStart.setToolTipText("开始执行脚本");
		buttonStop.setIcon(stopIcon);
		buttonStop.setToolTipText("停止脚本执行");
		buttonResume.setIcon(resumeIcon);
		buttonResume.setToolTipText("继续执行");
		buttonSuspend.setIcon(suspendIcon);
		buttonSuspend.setToolTipText("暂停脚本执行");
		buttonSingleStepRun.setText("单步执行");
		buttonSingleStepRun.setToolTipText("单步执行");
		buttonCurrentRunStep.setText("跳转至当前");
		buttonCurrentRunStep.setToolTipText("当前正在运行步骤");
		buttonStopScroll.setText("停止滚屏");
		buttonStopScroll.setToolTipText("停止滚屏");
		JPanel viewFuncPanel = new JPanel(new BorderLayout());
		viewList = new JComboBox(label);
		phaseComboBox = new JComboBox(tradePhase);
		caseTxt = new JTextField("");
		stepResult = new JComboBox(label3);
		caseTxt.setColumns(20);
		// caseTxt.setToolTipText("可根据脚本编号、用例描述、脚本描述、脚本执行内容列进行筛选");
		BalloonTip tooltipBalloon = new BalloonTip(caseTxt,
				"可根据脚本编号、用例描述、脚本描述、脚本执行内容列进行筛选");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);
		stepType = new JComboBox(label4);
		JPanel viewChoosePanel = new JPanel();
		JPanel viewChoosePane2 = new JPanel();

		/* 控件栏 */
		JLabel labhelp = new JLabel(
				"F5:单步执行,F6执行当前,F7设置取消断点,F8从当前位置继续执行,键盘0:标记未执行,键盘1:标记执行成功,键盘2:标记执行失败      ");
		viewChoosePane2.add(labhelp);
		viewChoosePane2.add(curruntStatus);
		viewChoosePane2.add(curruntStep);
		viewChoosePane2.add(costTimeLabel);
		
		//viewChoosePanel.add(runStatus);
		viewChoosePanel.add(buttonStart);
		viewChoosePanel.add(buttonSuspend);
		viewChoosePanel.add(buttonResume);
		viewChoosePanel.add(buttonStop);
		viewChoosePanel.add(buttonSingleStepRun);
		viewChoosePanel.add(buttonCurrentRunStep);

		viewChoosePanel.add(buttonStopScroll);

		JPanel viewPanel1 = new JPanel();
		/* 选择栏 */
		viewPanel1.add(txtLabel);
		viewPanel1.add(viewList);

		viewPanel1.add(phaseLabel);
		viewPanel1.add(phaseComboBox);

		viewPanel1.add(stepTypeLabel);
		viewPanel1.add(stepType);

		viewPanel1.add(statusLabel);
		viewPanel1.add(stepResult);

		viewPanel1.add(caseLabel);
		viewPanel1.add(caseTxt);

		viewPanel1.add(buttonClear);
		viewPanel1.add(lblank);
		viewPanel1.add(btnCmd);
		ViewPanel.add(viewPanel1, BorderLayout.WEST);
		viewFuncPanel.add(viewChoosePanel, BorderLayout.EAST);

		mainFuncPanel.add(ViewPanel, BorderLayout.NORTH);
		mainFuncPanel.add(scrollPane, BorderLayout.CENTER);
		mainFuncPanel.add(viewFuncPanel, BorderLayout.SOUTH);

		/************* 结果窗口 **********************/
		JPanel ResultPanel = new JPanel(new BorderLayout());
		runStatus = new JLabel();
		runStatus.setText("当前运行结果：错误数/已执行数/手动执行数/总步骤数:0/0/0/0  ");
		ResultPanel.add(runStatus, BorderLayout.NORTH);
		ResultPanel.add(viewChoosePane2, BorderLayout.SOUTH);
		JScrollPane resultPane = new JScrollPane();

		resultPane.setViewportView(resutTxt);
		ResultPanel.add(resultPane, BorderLayout.CENTER);

		Dimension de = this.getToolkit().getScreenSize();
		int sheight = new Double(de.getHeight() * 0.65).intValue();
		
		/* 设置窗口分隔 */
		this.setOrientation(0);
		this.setDividerLocation(sheight);
		this.setTopComponent(mainFuncPanel);
		this.setBottomComponent(ResultPanel);	
		/* 设置点击分隔 */
		this.setOneTouchExpandable(true);
		/* 设置分隔线宽度 */
		this.setDividerSize(10);		
		
		/* 初始化按钮状态 */
		setFuncStatusBegin();
	}

	public JTextArea getResutTxt() {
		return resutTxt;
	}

	/**
	 * 
	 * 函数说明：返回TableUI实例 2013-11-15 下午3:04:13
	 * 
	 * @return
	 */
	public ConditionSheetTable getTableUI() {
		return tableUI;
	}

	public void setButtonStartFlag(boolean buttonStartFlag) {
		this.buttonStartFlag = buttonStartFlag;
	}

	public synchronized void setFuncStatus(final boolean f) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				buttonStart.setEnabled(f);
				buttonResume.setEnabled(f);
				buttonSingleStepRun.setEnabled(f);
				viewList.setEnabled(f);
				phaseComboBox.setEnabled(f);
				caseTxt.setEnabled(f);
				stepResult.setEnabled(f);
				stepType.setEnabled(f);

				buttonSuspend.setEnabled(!f);
				popStarEn = f;
				if (c.isStop()) {
					buttonSingleStepRun.setEnabled(false);
					buttonResume.setEnabled(false);
					buttonSuspend.setEnabled(false);
					popStarEn = false;
				}

			}
		});
	}

	/**
	 * 初始情况下按键设置
	 */
	private synchronized void setFuncStatusBegin() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				buttonStart.setEnabled(true);
				buttonResume.setEnabled(false);
				buttonSingleStepRun.setEnabled(true);
				viewList.setEnabled(true);
				phaseComboBox.setEnabled(true);
				caseTxt.setEnabled(true);
				stepResult.setEnabled(true);
				stepType.setEnabled(true);
				buttonSuspend.setEnabled(false);
				buttonStop.setEnabled(false);
				popStarEn = true;
			}
		});
	}

	/**
	 * 单击停止键后界面上控件设置
	 */
	public synchronized void setFuncStatusStop() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				c.setStop(true);
				buttonStart.setEnabled(true);
				buttonResume.setEnabled(false);
				buttonSingleStepRun.setEnabled(false);
				viewList.setEnabled(true);
				phaseComboBox.setEnabled(true);
				caseTxt.setEnabled(true);
				stepResult.setEnabled(true);
				stepType.setEnabled(true);
				buttonSuspend.setEnabled(false);
				buttonStop.setEnabled(false);
				popStarEn = false;
			}
		});
	}

	public void tableScroll(int row, Boolean isAllowScroll) {
		int height = (int) (tableUI.getCellRect(2, 0, false).getY() - tableUI
				.getCellRect(1, 0, false).getY());
		int rows = (int) (tableUI.getVisibleRect().getHeight() / height);

		/* 2012-09-11,修改刷新次数过多问题 */
		if (isAllowScroll && ((row % (rows / 2) == 0 && row >= rows - 1))) {
			tableUI.scrollRectToVisible(tableUI.getCellRect(row + rows / 2 + 1,
					0, true));
		}
	}
}
