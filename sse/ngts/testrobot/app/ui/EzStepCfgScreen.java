package sse.ngts.testrobot.app.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import sse.db.common.GridBagHelper;
import sse.ngts.testrobot.common.IniOperator;

public class EzStepCfgScreen extends JDialog {

	private JLabel lurl = new JLabel("JDBC Url:");

	private JTextField url = new JTextField();

	private JLabel lsa = new JLabel("DbUser:");

	private JTextField sa = new JTextField();

	private JLabel lOperatorCounts = new JLabel("OperatorCounts:");

	private JTextField OperatorCounts = new JTextField();

	private JLabel lGwip1 = new JLabel("Gwip1:");

	private JTextField Gwip1 = new JTextField();

	private JLabel lGwip2 = new JLabel("Gwip2:");

	private JTextField Gwip2 = new JTextField();

	private JLabel lLocalIP1 = new JLabel("LocalIP1:");

	private JTextField LocalIP1 = new JTextField();

	private JLabel lLocalIP2 = new JLabel("LocalIP2:");

	private JTextField LocalIP2 = new JTextField();

	private JLabel lOperCode = new JLabel("OperCode:");

	private JTextField OperCode = new JTextField();

	private JLabel lOperList = new JLabel("OperatorList:");

	private JTextField OperList = new JTextField();

	private JButton btnSave = new JButton("保存");

	private HashMap<String, Properties> sections = null;

	private HashMap<String, String> opercodeHash = new HashMap<String, String>();

	private String surl = "";
	private String ssa = "";
	private String sOperatorCounts = "";
	private String sGwip1 = "";
	private String sGwip2 = "";
	private String sLocalIP1 = "";
	private String sLocalIP2 = "";
	private String sOperList = "";
	private String sOperCode = "";

	private String ezstepini = "Tools\\EzSTEP\\cfg\\EzSTEPUser.ini";

	public EzStepCfgScreen() {
		init(ezstepini);
	}

	private void init(String file) {

		IniOperator reader;
		try {
			reader = new IniOperator();
			if(!reader.ReadIni(file))
			{
				return;
			}
			surl = reader.getValue("PubTable", "jdbc.url");
			ssa = reader.getValue("PubTable", "DbUser");
			sOperatorCounts = reader.getValue("System", "OperatorCounts");
			sGwip1 = reader.getValue("oper1", "Gwip1");
			sGwip2 = reader.getValue("oper1", "Gwip2");
			sLocalIP1 = reader.getValue("oper1", "LocalIP1");
			sLocalIP2 = reader.getValue("oper1", "LocalIP2");
			sections = reader.getMap();
			int iOperatorCounts = Integer.parseInt(sOperatorCounts);
			for (int i = 1; i <= iOperatorCounts; i++) {
				String opercode = reader.getValue("oper" + i, "OperCode");
				opercode = opercode.substring(0, 5);
				opercodeHash.put(opercode, opercode);
				sOperList += opercode + ",";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		initComponents();
		this.setResizable(true);
		this.setSize(800, 480);
		this.setLocation(100, 100);
		this.setVisible(true);
	}

	private void initComponents() {
		JPanel container = new JPanel(new GridBagLayout());
		GridBagLayout g = new GridBagLayout();
		container.setLayout(g);

		GridBagHelper.addComponent(container, lurl, 0, 0, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		url.setText(surl);
		GridBagHelper.addComponent(container, url, 1, 0, 3, 1, 80, 2, 0.0, 0.0,
				GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lsa, 0, 1, 1, 1, 80, 2, 0.0, 0.0,
				GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		sa.setText(ssa);
		GridBagHelper.addComponent(container, sa, 1, 1, 1, 1, 80, 2, 0.0, 0.0,
				GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lOperatorCounts, 2, 1, 1, 1, 80,
				2, 0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);
		OperatorCounts.setText(sOperatorCounts);
		OperatorCounts.setEditable(false);
		GridBagHelper.addComponent(container, OperatorCounts, 3, 1, 1, 1, 80,
				2, 0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lGwip1, 0, 2, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		Gwip1.setText(sGwip1);
		GridBagHelper.addComponent(container, Gwip1, 1, 2, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lGwip2, 2, 2, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		Gwip2.setText(sGwip2);
		GridBagHelper.addComponent(container, Gwip2, 3, 2, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lLocalIP1, 0, 3, 1, 1, 80, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);
		LocalIP1.setText(sLocalIP1);
		GridBagHelper.addComponent(container, LocalIP1, 1, 3, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lLocalIP2, 2, 3, 1, 1, 80, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);
		LocalIP2.setText(sLocalIP2);
		GridBagHelper.addComponent(container, LocalIP2, 3, 3, 1, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lOperList, 0, 4, 1, 1, 80, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);
		OperList.setToolTipText("EzSTEP已添加的PBU列表");
		OperList.setEditable(false);
		OperList.setText(sOperList);
		GridBagHelper.addComponent(container, OperList, 1, 4, 3, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		GridBagHelper.addComponent(container, lOperCode, 0, 5, 1, 1, 80, 2,
				0.0, 0.0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.CENTER);
		OperCode.setToolTipText("以,分隔添加新的PBU");
		GridBagHelper.addComponent(container, OperCode, 1, 5, 3, 1, 80, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				surl = url.getText();
				ssa = sa.getText();
				sOperatorCounts = OperatorCounts.getText();
				sGwip1 = Gwip1.getText();
				sGwip2 = Gwip2.getText();
				sLocalIP1 = LocalIP1.getText();
				sLocalIP2 = LocalIP2.getText();
				sOperCode = OperCode.getText();

				String[] addlist = sOperCode.split(",");

				int iOperatorCounts = Integer.parseInt(sOperatorCounts);

				int icnt = 1;
				for (String opercode : addlist) {
					if (opercodeHash.containsKey(opercode)) {
						continue;
					}
					int idex = icnt + iOperatorCounts;
					Properties current = new Properties();
					current.setProperty("OperCode", opercode + "000001");
					current.setProperty("GwipCounts", "2");
					current.setProperty("Gwip1", sGwip1);
					current.setProperty("Gwip2", sGwip2);
					current.setProperty("WanTimeOut1", "10");
					current.setProperty("LocalIP1", sLocalIP1);
					current.setProperty("LocalIP2", sLocalIP2);
					current.setProperty("WanTimeOut2", "10");
					current.setProperty("WanRetry", "3");
					current.setProperty("DbUser", ssa);
					current.setProperty("ReqRespTable", "ReqResp_" + opercode);
					current.setProperty("ExecReportTable", "ExecReport_"
							+ opercode);
					current.setProperty("jdbc.driver",
							"com.microsoft.sqlserver.jdbc.SQLServerDriver");
					current.setProperty("jdbc.url", surl);
					sections.put("oper" + idex, current);
					icnt++;
				}

				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							ezstepini + ".bak"));

					Set<Entry<String, Properties>> sets = sections.entrySet();
					Iterator<Entry<String, Properties>> iterator = sets
							.iterator();
					while (iterator.hasNext()) {
						Entry<String, Properties> section = iterator.next();
						String key = section.getKey();
						writer.write("[" + key + "]\n");
						Properties props = section.getValue();
						Enumeration<Object> keyEnum = props.keys();
						while (keyEnum.hasMoreElements()) {
							String id = (String) keyEnum.nextElement();
							if (id.equals("jdbc.url")) {
								props.setProperty("jdbc.url", surl);
							} else if (id.equals("DbUser")) {
								props.setProperty("DbUser", ssa);
							} else if (id.equals("OperatorCounts")) {
								props.setProperty(
										"OperatorCounts",
										Integer.toString(iOperatorCounts
												+ addlist.length));
							} else if (id.equals("LocalIP1")) {
								props.setProperty("LocalIP1", sLocalIP1);
							} else if (id.equals("LocalIP2")) {
								props.setProperty("LocalIP2", sLocalIP2);
							} else if (id.equals("Gwip1")) {
								props.setProperty("Gwip1", sGwip1);
							} else if (id.equals("Gwip2")) {
								props.setProperty("Gwip2", sGwip2);
							}
							String v = props.getProperty(id);
							writer.write(id + " = " + v + "\n");
						}
					}

					writer.close();
					FileUtils.deleteQuietly(new File(ezstepini));
					FileUtils.moveFile(new File(ezstepini + ".bak"), new File(
							ezstepini));

				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		});
		GridBagHelper.addComponent(container, btnSave, 1, 6, 1, 1, 2, 2, 0.0,
				0.0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		add(container);

	}

	/**
	 * 函数说明：TODO 2013-11-18 下午4:15:03
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
