package sse.ngts.testrobot.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;

import sse.db.common.GridBagHelper;
import sse.ngts.testrobot.app.func.casedetails.ApplStepsSet;
import sse.ngts.testrobot.app.interf.ISheetController;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetScreen;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.AirConfigHnd;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.ui.background.BackGroundPanel;
import sse.ngts.testrobot.ui.thirdui.xf.XFButton;

public class ViewScreen extends JFrame {
	private static Logger logger = Logger.getLogger(ViewScreen.class);

	private ViewController controller;
	private JButton btnExecute;
	private JButton btnConfig;
	private JButton btnLog;
	private JButton btnReport;
	private JButton btnCaseMan;
	private JButton btnEnvCfg;
	private JButton btnDocument;
	private JButton btnEzStep;
	private JButton btnODBC;
	private JButton btnImpExp;
	private JButton btnContinue;

// SIR 2 begin
	private JButton btnFresh;
// SIR 2 end
	private JPanel configPanel;

	private JTabbedPane xlsSheetPane;

	public static void installSkin() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

			/* �趨��ʾ���� */
			FontUIResource font = new FontUIResource("����", Font.PLAIN, 12);

			Enumeration<Object> enu = UIManager.getDefaults().keys();

			Object key, value;
			while (enu.hasMoreElements()) {
				key = enu.nextElement();
				value = UIManager.get(key);
				if (value instanceof FontUIResource) {
					UIManager.put(key, font);
				}
			}
		} catch (Exception e) {
			logger.error("δ֪�쳣{0}" + e.getMessage());
		}
	}

	public ViewScreen() {
		initComponents();
	}

	public ViewScreen(final ViewController controller) {
		this.controller = controller;
		initComponents();
	}

	private void initComponents() {
		installSkin();

		JPanel titlePanel = new JPanel(new BorderLayout());
		/************************* �趨���� **********************/
		JPanel btnPanel = new JPanel();

		btnConfig = new XFButton("����", 5);
		btnImpExp = new XFButton("���뵼������", 5);

		btnImpExp.setBackground(Color.green);

		btnEnvCfg = new XFButton("��������", 5);
		btnODBC = new XFButton("ODBC����", 5);
		btnEzStep = new XFButton("EzSTEP����", 5);

		btnCaseMan = new XFButton("��������", 5);
		btnCaseMan.setEnabled(false);
		btnContinue = new XFButton("һ�����ü���", 5);

		btnContinue.setToolTipText("�ڴ�ִ���ֲ�ǰ������������Ϊ����ɼ���ִ��");

		btnExecute = new XFButton("��ִ���ֲ�", 5);
		btnExecute.setBackground(Color.green);

		btnLog = new XFButton("��־", 5);
		btnReport = new XFButton("���Ա���", 5);
		btnReport.setEnabled(false);
		btnDocument = new XFButton("����˵��", 5);
		btnDocument.setToolTipText("��ϸ������AIR ���ߵ�ʹ�÷������ڱ�д����ʱ��Ϊ�ο��ֲ�");
		
//SIR 2 begin
		btnFresh = new XFButton("ˢ��", 5);
		btnFresh.setEnabled(true);
//SIR 2 end

		btnPanel.add(btnConfig);
		btnPanel.add(btnImpExp);
		btnPanel.add(btnEnvCfg);
		btnPanel.add(btnODBC);
		btnPanel.add(btnEzStep);
		btnPanel.add(btnCaseMan);
		btnPanel.add(btnContinue);
		btnPanel.add(btnExecute);
		btnPanel.add(btnReport);
		btnPanel.add(btnLog);
		btnPanel.add(btnDocument);
//SIR 2 begin
		btnPanel.add(btnFresh);
//SIR 2 end

		titlePanel.add(btnPanel, BorderLayout.WEST);

		this.add(titlePanel, BorderLayout.NORTH);

		/************************* �ű��� ***********************/
		JPanel instrucPanel = new JPanel(new BorderLayout());
		ImageIcon instrucIcon = new ImageIcon(
				ViewScreen.class.getResource("/pic/air����ͼ.gif"));
		JLabel instruclabel = new JLabel();
		instruclabel.setIcon(instrucIcon);

		JPanel descScrollPane = new JPanel();
		descScrollPane.setLayout(new BorderLayout());
		descScrollPane.add(instruclabel, BorderLayout.NORTH);
		instrucPanel.add(descScrollPane, BorderLayout.CENTER);
		instrucPanel.setBorder(BorderFactory.createTitledBorder("ʹ��˵�� "));

		xlsSheetPane = new JTabbedPane();
		xlsSheetPane.setBackground(Color.WHITE);
		JSplitPane funcSplitPane = new JSplitPane();
		funcSplitPane.setOrientation(0);
		funcSplitPane.setDividerLocation(400);
		funcSplitPane.setTopComponent(instrucPanel);

		/************* ���ô��� **********************/
		JLabel label = new JLabel("��ǰ��������");
		JLabel lexport = new JLabel("�������ã��򿪽����->���뵼������->����");
		JLabel limport = new JLabel(
				"��������ֲᲽ�裺�򿪽����->���뵼������->��������->ODBC����->EzStep����");
		configPanel = new BackGroundPanel(PropConfig.getTestVersion());
		configPanel.setLayout(new GridBagLayout());

		GridBagHelper.addComponent(configPanel, label, 0, 0, 1, 1, 50, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		GridBagHelper.addComponent(configPanel, lexport, 0, 1, 1, 1, 50, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);

		GridBagHelper.addComponent(configPanel, limport, 0, 2, 1, 1, 50, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);

		funcSplitPane.setBottomComponent(configPanel);

		xlsSheetPane.add("ָ��", funcSplitPane);

		xlsSheetPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					/* �жϵ�ǰҪ�رմ����Ƿ�Ϊ�������� */
					if (xlsSheetPane.getSelectedComponent() instanceof ConditionSheetScreen) {
						/* �жϴ����Ƿ�����������ִ�� */
						if (controller.isApplEngineWorking()) {
							JOptionPane.showMessageDialog(null, "�ű�����ִ�У����ܹرգ�",
									"INFO", JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						int selection = JOptionPane.showConfirmDialog(null,
								"ȷ����Ҫ�ر�ѡ�����", "����",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (selection == JOptionPane.CANCEL_OPTION) {
							return;
						}
						controller.handleTestResult();
						controller
								.handleRemoveSheetView((ConditionSheetScreen) xlsSheetPane
										.getSelectedComponent());
						xlsSheetPane.remove(xlsSheetPane.getSelectedComponent());

					}
				}
			}
		});

		handleAction();

		this.add(xlsSheetPane, BorderLayout.CENTER);

		this.setTitle("AIRִ���ֲ������");

		Dimension de = this.getToolkit().getScreenSize();

		int sheight = new Double(de.getHeight() * 0.9).intValue();

		int swidth = new Double(de.getWidth() * 0.9).intValue();
		this.setMinimumSize(new Dimension(800, 600));
		setSize(swidth, sheight);
		setLocation(10, 10);

		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showApp();
				/*
				 * if (splashScreen != null) { splashScreen.setVisible(false); }
				 */
			}
		});

	}

	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if(btnReport.isEnabled())
			{
				controller.handleTestResult();
			}
			super.processWindowEvent(e);
			return;
		
		}
	}

	public void addNewTabSheetView(ISheetController c, String sheetName) {
		String project = AirConfigHnd.getInstance().getProperty(
				ConstValues.TestProject_Key);
		xlsSheetPane.add(sheetName, c.getUI());

		this.setTitle("�Զ�������ִ�н���--������" + project);
		xlsSheetPane.setSelectedComponent(c.getUI());
	}

	/**
	 * 
	 * ����˵����������� 2013-11-15 ����4:33:04
	 */
	private void handleAction() {

		btnContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplStepsSet.setCycleAlwaysContinue();
				btnContinue.setVisible(false);
				JOptionPane.showMessageDialog(null, "��������������������Լ���ִ��");
			}
		});
		btnExecute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!controller.conditionSheetViewDete()) {
					int selection = JOptionPane.showConfirmDialog(null,
							"�Ƿ�����ִ�У�����ִ�л�ɾ������ִ�м�¼��", "����",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);

					if (selection == JOptionPane.OK_OPTION) {
						controller.restartConditionSheet(false);
					}

				}

				// ��ȡ�ű�����

				// ��ȡ�Ѵ���ִ�н��
				// ��ִ�н���
//				btnContinue.setVisible(false)������ע�͵�			
				btnContinue.setVisible(false);
				controller.handleAddConditionSheet(PropConfig.getExecuteFile()); // ˢ�½���
				btnReport.setEnabled(true);
			}

		});

		btnEnvCfg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EnvCfgScreen env = new EnvCfgScreen();
			}

		});

		btnImpExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImpAndExpCfgScreen env = new ImpAndExpCfgScreen();
			}

		});

		btnODBC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EnvCfgScreen env = new EnvCfgScreen(
						ConstValues.ODBC_CONFIG_FILE);
			}

		});
		btnEzStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EzStepCfgScreen step = new EzStepCfgScreen();
			}

		});
		/**
		 * ��������
		 */
		btnCaseMan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// CaseSelectList caseman = new CaseSelectList();
			}

		});

		/**
		 * ������
		 */
		btnConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfScreen.getInstance().showUI();
			}
		});

		/**
		 * ����־
		 */
		btnLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileDirc = System.getProperty("user.dir");
				String filePath = fileDirc + File.separator
						+ PropConfig.getExecuteLogFile();
				File file = new File(filePath);
				if (!file.exists())
					return;
				try {
					Runtime.getRuntime().exec("cmd /c start " + filePath);
				} catch (IOException ex) {
					logger.error("δ֪�쳣{0}" + ex.getMessage());
				}
			}
		});

		/**
		 * ��ִ�б���
		 */
		btnReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.handleTestResult();
				controller.handleOpenTestResultSheet(null);
			}
		});

		/**
		 * �򿪰����ֲ�
		 */
		btnDocument.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String scriptdesc = System.getProperty("user.dir")
						+ File.separator + "Manual" + File.separator
						+ "NGTS_AM_AIR_D_04_02_�����ֲ�_CV01.xls";
				try {
					Runtime.getRuntime().exec("cmd /c start " + scriptdesc);
				} catch (IOException e1) {
					logger.error("δ֪�쳣{0}" + e1.getMessage());
				}
			}

		});
		
		/**
		 * 	��ˢ��
		 */		
		btnFresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ȡ�ű�����

				// ��ȡ�Ѵ���ִ�н��
				// ��ִ�н���
				btnContinue.setVisible(false);
				controller.handleAddConditionSheet(PropConfig.getExecuteFile()); // ˢ�½���
				btnReport.setEnabled(true);
				
			}
		});

	}

	public void selectedTabSheeView(ConditionSheetController c) {
		xlsSheetPane.setSelectedComponent(c.getUI());
	}

	private void showApp() {
		setIconImage(ImageHelper.loadImage("logo.gif").getImage());
		setVisible(true);
	}

}