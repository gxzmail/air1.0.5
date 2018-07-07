package sse.ngts.testrobot.app.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.ToolTipUtils;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.common.IniOperator;
import sse.ngts.testrobot.engine.app.AirConfigHnd;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.ui.thirdui.xf.XFButton;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ConfScreen extends JDialog {

	private JTextField txtCycle = null;

	private JTextField txtCasePath = null;
	private JTextField txtFrmwork = null;
	private JTextField txtFrmworkId = null;
	private JTextField txtRptPath = null;
	// private JTextField txtStatResultPath = null;
	private JTextField txtVersion = null;
	private JTextField txtTimes = null;
	private JComboBox projectComboBox;

	private JButton ConformButton;
	private JButton ApplButton;
	private JButton CancleButton;

	private String cycle = "";
	private String casePath = "";
	private String frmwork = "";
	private String frmworkId = "";
	// private String statResult = "";
	private String RptPath = "";
	private String version = "";
	private String times = "";

	private static ConfScreen instance = null;

	public static ConfScreen getInstance() {
		if (instance == null) {
			instance = new ConfScreen();
		}
		return instance;
	}

	private ConfScreen() {
		initComponents();
		setCfgValue();
		setAction();
		this.setResizable(false);
		this.setLocation(100, 100);
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����3:01:45 ����˵���� void
	 */
	public void hideUI() {
		this.setVisible(false);
	}

	/**
	 * *********************************************************** ����˵���� TODO
	 * ������: neusoft ��������: 2013-9-13 ����3:01:37 ����˵���� void
	 */
	private void initComponents() {

		this.setTitle("�����ļ�����");
		JPanel panelInput = new JPanel();

		JLabel lproject = new JLabel("ϵͳ");
		String[] projects = ConstValues.projects;
		projectComboBox = new JComboBox(projects);

		JLabel lversion = new JLabel("���԰汾");
		txtVersion = new JTextField();
		BalloonTip tooltipBalloon = new BalloonTip(txtVersion,
				"ָ���汾�ţ�����������ִ������ʱ����");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		JLabel ltimes = new JLabel("����");
		txtTimes = new JTextField();
		tooltipBalloon = new BalloonTip(txtTimes, "�������κ��������������ִ�����");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		JLabel lcycle = new JLabel("���������ļ�");
		txtCycle = new JTextField();
		tooltipBalloon = new BalloonTip(txtCycle, "ָ��AIR_CYCLE.TXT�ļ������·��");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		JLabel lfrmwork = new JLabel("���Կ���ļ�");
		txtFrmwork = new JTextField();
		tooltipBalloon = new BalloonTip(txtFrmwork, "ָ������ļ������·��");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		JLabel lfrmworkId = new JLabel("���Կ��ID");
		txtFrmworkId = new JTextField();

		JLabel lcasePath = new JLabel("��������·��");
		txtCasePath = new JTextField();
		tooltipBalloon = new BalloonTip(txtCasePath, "ָ���������������·����������ϵͳ����������");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		JPanel panelOutput = new JPanel();
		JLabel execuFilepath = new JLabel("ִ�б������Ŀ¼");
		txtRptPath = new JTextField();
		tooltipBalloon = new BalloonTip(execuFilepath, "ָ��ִ�б������Ŀ¼");
		ToolTipUtils.balloonToToolTip(tooltipBalloon, 1, 3000);

		/*
		 * JLabel execuOutFilepath = new JLabel("���Խ���ļ�"); txtStatResultPath =
		 * new JTextField(); tooltipBalloon = new BalloonTip(txtStatResultPath,
		 * "ָ�����Ա��������·��"); ToolTipUtils.balloonToToolTip(tooltipBalloon, 1,
		 * 3000);
		 */

		ConformButton = new XFButton("ȷ��", 5);
		ApplButton = new XFButton("Ӧ��", 5);
		CancleButton = new XFButton("ȡ��", 5);

		panelInput.setBorder(javax.swing.BorderFactory
				.createTitledBorder("ִ�б�������"));
		panelOutput.setBorder(javax.swing.BorderFactory
				.createTitledBorder("�ű���������"));
		javax.swing.GroupLayout panelInputLayout = new javax.swing.GroupLayout(
				panelInput);
		panelInput.setLayout(panelInputLayout);

		javax.swing.GroupLayout panelOutputLayout = new javax.swing.GroupLayout(
				panelOutput);
		panelOutput.setLayout(panelOutputLayout);

		panelOutputLayout
				.setHorizontalGroup(panelOutputLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelOutputLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lproject)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				projectComboBox,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lversion)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtVersion,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				ltimes)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtTimes,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))

														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lcycle)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtCycle,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lcasePath)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtCasePath,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lfrmwork)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtFrmwork,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))
														.addGroup(
																panelOutputLayout
																		.createSequentialGroup()
																		.addComponent(
																				lfrmworkId)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtFrmworkId,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE))

										)

										.addGap(111, 111, 111)));
		panelOutputLayout
				.setVerticalGroup(panelInputLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelOutputLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lproject)
														.addComponent(
																projectComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lversion)
														.addComponent(
																txtVersion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(ltimes)
														.addComponent(
																txtTimes,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lcycle)
														.addComponent(
																txtCycle,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))

										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lcasePath)
														.addComponent(
																txtCasePath,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lfrmwork)
														.addComponent(
																txtFrmwork,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)
										.addGroup(
												panelOutputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lfrmworkId)
														.addComponent(
																txtFrmworkId,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))

										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)

										.addContainerGap()));

		panelInputLayout
				.setHorizontalGroup(panelInputLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelInputLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelInputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)

														.addGroup(
																panelInputLayout
																		.createSequentialGroup()
																		.addComponent(
																				execuFilepath)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				txtRptPath,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				320,
																				Short.MAX_VALUE)))
										.addGap(111, 111, 111)));
		panelInputLayout
				.setVerticalGroup(panelInputLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelInputLayout
										.createSequentialGroup()
										.addContainerGap()

										.addGroup(
												panelInputLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																execuFilepath)
														.addComponent(
																txtRptPath,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												23, Short.MAX_VALUE)

										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(90, 90,
																		90)
																.addComponent(
																		ConformButton)
																.addGap(70, 70,
																		70)
																.addComponent(
																		ApplButton)
																.addGap(70, 70,
																		70)
																.addComponent(
																		CancleButton))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(36, 36,
																		36)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						panelInput,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						panelOutput,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(29, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(30, 30, 30)
								.addComponent(panelOutput,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(panelInput,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(ConformButton)
												.addComponent(ApplButton)
												.addComponent(CancleButton))
								.addContainerGap(37, Short.MAX_VALUE)));
		pack();

	}

	/**
	 * *********************************************************** ����˵���� ��������
	 * ������: neusoft ��������: 2013-9-13 ����3:01:22 ����˵���� void
	 */
	private int saveCfgValue() {
		AirConfigHnd.getInstance().modify(ConstValues.TestCycle_KEY,
				txtCycle.getText());

		AirConfigHnd.getInstance().modify(ConstValues.TEST_TIMES_KEY,
				txtTimes.getText());

		IniOperator.SaveIni(ConstValues.Excute_EnvFileName, "VERSION",
				"version", txtVersion.getText());

		AirConfigHnd.getInstance().modify(ConstValues.CasePath_KEY,
				txtCasePath.getText());

		AirConfigHnd.getInstance().modify(ConstValues.FrmWorkPath_KEY,
				txtFrmwork.getText());

		AirConfigHnd.getInstance().modify(ConstValues.FrmId_KEY,
				txtFrmworkId.getText());

		AirConfigHnd.getInstance().modify(ConstValues.TestExcutePath_KEY,
				txtRptPath.getText());

		/*
		 * AirConfigHnd.getInstance().modify(ConstValues.ExecuteOutSTAT_Key,
		 * txtStatResultPath.getText());
		 */

		AirConfigHnd.getInstance().modify(ConstValues.TestProject_Key,
				projectComboBox.getSelectedItem().toString());

		AirConfigHnd.getInstance().saveCfgFile();

		return 0;
	}

	private void setAction() {
		txtCycle.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtCycle.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtCycle.getText().length() == 0) {
					txtCycle.setBackground(Color.RED);
				}

			}

		});

		txtVersion.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtVersion.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtVersion.getText().length() == 0) {
					txtVersion.setBackground(Color.RED);
				}

			}

		});

		txtTimes.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtTimes.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtTimes.getText().length() == 0) {
					txtTimes.setBackground(Color.RED);
				}

			}

		});

		txtCasePath.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtCasePath.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtCasePath.getText().length() == 0) {
					txtCasePath.setBackground(Color.RED);
				}

			}

		});
		txtFrmwork.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtFrmwork.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtFrmwork.getText().length() == 0) {
					txtFrmwork.setBackground(Color.RED);
				}

			}

		});

		txtFrmworkId.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtFrmworkId.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtFrmworkId.getText().length() == 0) {
					txtFrmworkId.setBackground(Color.RED);
				}

			}

		});

		txtRptPath.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent m_e) {
				txtRptPath.setBackground(Color.WHITE);

			}

			@Override
			public void focusLost(FocusEvent m_e) {
				if (txtRptPath.getText().length() == 0) {
					txtRptPath.setBackground(Color.RED);
				}

			}

		});

		/*********************************************************************************************/
		CancleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfScreen.this.setVisible(false);
			}
		});

		ConformButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean bFlg = true;
				if (txtCycle.getText().length() == 0) {
					txtCycle.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtVersion.getText().length() == 0) {
					txtVersion.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtCasePath.getText().length() == 0) {
					txtCasePath.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtFrmwork.getText().length() == 0) {
					txtFrmwork.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtFrmworkId.getText().length() == 0) {
					txtFrmworkId.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtRptPath.getText().length() == 0) {
					txtRptPath.setBackground(Color.RED);
					bFlg = false;
				}

				if (bFlg) {
					saveCfgValue();

					JOptionPane.showMessageDialog(ConfScreen.this, "����ɹ�!");
					ConfScreen.this.setVisible(false);
				} else {
					JOptionPane
							.showMessageDialog(ConfScreen.this, "����ʧ�ܣ�����Ϊ��!");
				}

			}
		});
		ApplButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean bFlg = true;
				if (txtCycle.getText().length() == 0) {
					txtCycle.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtVersion.getText().length() == 0) {
					txtVersion.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtCasePath.getText().length() == 0) {
					txtCasePath.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtFrmwork.getText().length() == 0) {
					txtFrmwork.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtFrmworkId.getText().length() == 0) {
					txtFrmworkId.setBackground(Color.RED);
					bFlg = false;
				}

				if (txtRptPath.getText().length() == 0) {
					txtRptPath.setBackground(Color.RED);
					bFlg = false;
				}

				if (bFlg) {
					saveCfgValue();

					JOptionPane.showMessageDialog(ConfScreen.this, "����ɹ�!");
					ConfScreen.this.setVisible(false);
				} else {
					JOptionPane
							.showMessageDialog(ConfScreen.this, "����ʧ�ܣ�����Ϊ��!");
				}

			}
		});
	}

	/**
	 * *********************************************************** ����˵����
	 * ���ý����������� ������: neusoft ��������: 2013-9-13 ����3:01:16 ����˵���� void
	 */
	private void setCfgValue() {
		String project = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestProject_Key);
		project = project == null ? ConstValues.DEFAULT_TestProject : project;
		projectComboBox.setSelectedItem(project);

		version = PropConfig.getTestVersion();
		version = version == null ? "" : version;
		txtVersion.setText(version);

		times = PropConfig.getTestTimes();
		times = times == null ? "" : times;
		txtTimes.setText(times);

		cycle = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestCycle_KEY);
		cycle = (cycle == null) ? ConstValues.DEFAULT_Cycle : cycle;
		cycle = cycle.replaceAll(ConstValues.PROJECT_REGEX, project);
		txtCycle.setText(cycle);

		casePath = AirConfigHnd.getInstance().getProperty(
				ConstValues.CasePath_KEY);
		casePath = (casePath == null) ? ConstValues.DEFAULT_CasePath : casePath;
		casePath = casePath.replaceAll(ConstValues.PROJECT_REGEX, project);
		txtCasePath.setText(casePath);

		frmwork = AirConfigHnd.getInstance().getProperty(
				ConstValues.FrmWorkPath_KEY);
		frmwork = (frmwork == null) ? ConstValues.DEFAULT_Frmwork : frmwork;
		frmwork = frmwork.replaceAll(ConstValues.PROJECT_REGEX, project);
		txtFrmwork.setText(frmwork);

		frmworkId = AirConfigHnd.getInstance().getProperty(
				ConstValues.FrmId_KEY);
		frmworkId = (frmworkId == null) ? ConstValues.DEFAULT_Frmwork
				: frmworkId;
		frmworkId = frmworkId.replaceAll(ConstValues.PROJECT_REGEX, project);
		txtFrmworkId.setText(frmworkId);

		RptPath = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestExcutePath_KEY);
		RptPath = (RptPath == null) ? ConstValues.DEFAULT_ReportPath : RptPath;
		RptPath = RptPath.replaceAll(ConstValues.PROJECT_REGEX, project);
		txtRptPath.setText(RptPath);

		/*
		 * statResult = AirConfigHnd.getInstance().getProperty(
		 * ConstValues.ExecuteOutSTAT_Key); statResult = (statResult == null) ?
		 * ConstValues.DEFAULT_StatResult : statResult;
		 */

	}

	public void showUI() {
		this.setVisible(true);
	}

}
