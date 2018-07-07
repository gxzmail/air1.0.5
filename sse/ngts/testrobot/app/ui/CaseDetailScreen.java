package sse.ngts.testrobot.app.ui;

import java.awt.Color;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import sse.ngts.testrobot.app.model.ScriptCase;

/**
 * 显示用例执行详情窗口
 * @author neusoft
 */
public class CaseDetailScreen extends JDialog implements Runnable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7867218834935545880L;

    // private ActionController controller;

    private ScriptCase scriptCase;

    private JLabel caseDetial = new JLabel();
    private JLabel caseContent = new JLabel();
    private JLabel testResult = new JLabel();
    private JLabel failedCause = new JLabel();
    private JLabel caseDescrip = new JLabel();
    private JTextArea txtCaseDetial = new JTextArea();
    private JTextArea txtCaseContent = new JTextArea();
    private JTextArea txtTestResult = new JTextArea();
    private JTextArea txtFailedCause = new JTextArea();
    private JTextArea txtCaseDescrip = new JTextArea();
    private JScrollPane sCaseDetial = new JScrollPane();
    private JScrollPane sCaseContent = new JScrollPane();
    private JScrollPane sFailedCause = new JScrollPane();
    private JScrollPane sCaseDescrip = new JScrollPane();

    private String scriptid = "";
    private String executeStatus = "";

    public CaseDetailScreen(ScriptCase scriptcase)
    {
        this.scriptCase = scriptcase;
        this.scriptid = this.scriptCase.getFrmCase().getScriptId();
        this.executeStatus = this.scriptCase.getTestResultDescr();
    }

    public void createUI()
    {
        this.setTitle(scriptid + "-步骤详情窗口:" + executeStatus + "【ESC退出】,【SPACE显示】");

        initComponents();

        setComponentValues();

        setSize(600, 450);
        setLocation(120, 100);

        this.toFront();
        this.setResizable(false);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
      
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventPostProcessor(new KeyEventPostProcessor()
        {

			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				if(KeyEvent.VK_ESCAPE == e.getKeyCode())
				{
					CaseDetailScreen.this.dispose();
				}
				return false;
			}
        	
        });

    }

    /**
     * 布局窗口
     */
    private void initComponents()
    {
        JPanel jPanel1 = new JPanel();

        caseDetial.setText("用例详情");
        caseDescrip.setText("脚本描述");
        caseContent.setText("脚本内容");
        testResult.setText("测试结果");
        failedCause.setText("测试日志");

        txtCaseContent.setLineWrap(true);
        txtCaseDetial.setLineWrap(true);
        txtTestResult.setLineWrap(true);
        txtTestResult.setEditable(false);
        txtFailedCause.setLineWrap(true);
        txtCaseDescrip.setLineWrap(true);

        sCaseDetial.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sCaseDescrip.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sCaseContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sFailedCause.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        sCaseDetial.getViewport().add(txtCaseDetial);
        sCaseDescrip.getViewport().add(txtCaseDescrip);
        sCaseContent.getViewport().add(txtCaseContent);
        sFailedCause.getViewport().add(txtFailedCause);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout
        .setHorizontalGroup(jPanel1Layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel1Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                jPanel1Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                                .addGroup(
                                        jPanel1Layout
                                        .createSequentialGroup()
                                        .addComponent(caseDetial)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(sCaseDetial, 500, 500, 500))
                                                .addGroup(
                                                        jPanel1Layout
                                                        .createSequentialGroup()
                                                        .addComponent(caseDescrip)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(sCaseDescrip, 500, 500, 500))
                                                                .addGroup(
                                                                        jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(caseContent)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(sCaseContent, 500, 500, 500))
                                                                                .addGroup(
                                                                                        jPanel1Layout
                                                                                        .createSequentialGroup()
                                                                                        .addComponent(testResult)
                                                                                        .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(txtTestResult, 500, 500, 500))
                                                                                                .addGroup(
                                                                                                        jPanel1Layout
                                                                                                        .createSequentialGroup()
                                                                                                        .addComponent(failedCause)
                                                                                                        .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                .addComponent(sFailedCause, 500, 500, 500)))
                                                                                                                .addGap(111, 111, 111)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel1Layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(caseDetial).addComponent(sCaseDetial, 40, 40, 40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                        Short.MAX_VALUE)
                                        .addGroup(
                                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(caseDescrip).addComponent(sCaseDescrip, 40, 40, 40))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                                        Short.MAX_VALUE)
                                                        .addGroup(
                                                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(caseContent).addComponent(sCaseContent, 60, 60, 60))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                                                        Short.MAX_VALUE)
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(testResult)
                                                                                .addComponent(txtTestResult, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(
                                                                                                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                        .addComponent(failedCause).addComponent(sFailedCause, 120, 120, 120))
                                                                                                        .addContainerGap()));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel1,
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(26, Short.MAX_VALUE)))));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18).addContainerGap(37, Short.MAX_VALUE)));
        pack();

    }

    @Override
    public void run()
    {
        createUI();
        this.setVisible(true);
        this.toFront();
    }

    /**
     * 初始化数据
     */
    private void setComponentValues()
    {
        if (scriptCase != null)
        {
            String strCaseDetails = scriptCase.getFrmCase().getCaseDetials();
            txtCaseDetial.setText((strCaseDetails == null) ? "" : strCaseDetails);

            String strCaseDescrip = scriptCase.getFrmCase().getDescrip();
            txtCaseDescrip.setText((strCaseDescrip == null) ? "" : strCaseDescrip);
            txtCaseContent.setText(scriptCase.getFrmCase().getTestContent());
            txtTestResult.setText(scriptCase.getTestResultDescr());

            txtFailedCause.setWrapStyleWord(true);
            if (scriptCase.getTestResult() == null)
                txtFailedCause.setText("");
            else
            {
                int i = scriptCase.getTestResult().size();
                if (i > 0)
                {
                    if (!scriptCase.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG))
                    {
                        txtFailedCause.setForeground(Color.red);
                    }
                    txtFailedCause.append(scriptCase.getTestResult().get(i - 1));
                }
            }
        }
    }

}
