package sse.ngts.testrobot.app.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.PropConfig;

public class ImpAndExpCfgScreen extends JDialog {

	private static Logger logger = Logger.getLogger(ImpAndExpCfgScreen.class);

	public ImpAndExpCfgScreen() {
		init();

	}

	private void init() {
		this.setTitle("导入导出配置文件:[air_configs.txt、EzSTEPUser.ini、security.dat、AIRCYCLE.txt]");
		initCompents();
		this.setResizable(true);
		this.setSize(300, 200);
		this.setLocation(100, 100);
		this.setVisible(true);
	}

	private void initCompents() {
		JButton btnImport = new JButton("导入配置到本地");
		btnImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HashMap<String, String> implist = new HashMap<String, String>();
				implist.put(ConstValues.Excute_EnvFileName,
						ConstValues.Excute_EnvFileName);
				implist.put(ConstValues.Default_AIRCYLE_LIST,
						PropConfig.getTestCycleFile());
				implist.put(ConstValues.EZSTEP_PBU_CONFIG,
						ConstValues.EZSTEP_PBU_CONFIG);
				implist.put(ConstValues.EZSTEP_SECURITY_CONFIG,
						ConstValues.EZSTEP_SECURITY_CONFIG);

				try {
					boolean bimp = ImportFile(ConstValues.IMPEXP_FILE_CONFIG,
							implist);
					if (bimp) {
						JOptionPane
								.showMessageDialog(ImpAndExpCfgScreen.this,
										"导入配置成功，请依次修改各配置中与环境号不同的帐号、密码、环境号信息，EzStep Rules已服务器最新版本为准");
					}
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		});

		JButton btnExport = new JButton("导出配置供发送");

		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> explist = new HashMap<String, String>();
				explist.put(ConstValues.Excute_EnvFileName,
						ConstValues.Excute_EnvFileName);
				explist.put(PropConfig.getTestCycleFile(),
						ConstValues.Default_AIRCYLE_LIST);
				explist.put(ConstValues.EZSTEP_PBU_CONFIG,
						ConstValues.EZSTEP_PBU_CONFIG);
				explist.put(ConstValues.EZSTEP_SECURITY_CONFIG,
						ConstValues.EZSTEP_SECURITY_CONFIG);

				try {
					boolean bexp = ExportFile(explist,
							ConstValues.IMPEXP_FILE_CONFIG);
					if (bexp) {
						JOptionPane.showMessageDialog(ImpAndExpCfgScreen.this,
								"配置文件导出到[" + ConstValues.IMPEXP_FILE_CONFIG
										+ "]成功,请在交付时发送此文件");
					}
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}

		});

		this.add(btnImport, BorderLayout.NORTH);
		this.add(btnExport, BorderLayout.SOUTH);
	}

	private boolean ImportFile(String impFile, HashMap<String, String> implist)
			throws IOException {
		File impHnd = new File(impFile);
		if (!impHnd.exists()) {
			JOptionPane.showConfirmDialog(ImpAndExpCfgScreen.this, "导入配置"
					+ impFile + "不存在，导入失败");
			return false;
		}

		BufferedReader reader = new BufferedReader(new FileReader(impFile));
		String line = null;
		String cfgFile = "";
		BufferedWriter writer = null;
		/**
		 * 根据>>>>>区分文件，依次写文件
		 */

		while ((line = reader.readLine()) != null) {
			if (line.startsWith(">>>>>")) {

				if (writer != null) {
					writer.close();
				}

				cfgFile = line.replace(">>>>>", "").trim();
				logger.info("导入配置:" + cfgFile);
				// 存在导出列表中文件
				if (implist.containsKey(cfgFile)) {
					writer = new BufferedWriter(new FileWriter(
							implist.get(cfgFile)));
				} else {
					writer = null;
				}

				continue;
			}
			if (writer == null) {
				continue;
			}
			writer.write(line + "\n");

		}
		if (writer != null) {
			writer.close();
		}

		reader.close();

		return true;
	}

	private boolean ExportFile(HashMap<String, String> explist, String expFile)
			throws IOException {
		File expHnd = new File(expFile);
		if (!expHnd.exists()) {
			expHnd.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(expFile));

		Set<String> sets = explist.keySet();
		Iterator<String> iterator = sets.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			logger.info("导出配置:" + key);
			writer.write(">>>>>" + explist.get(key) + "\n");

			File f = new File(key);
			if (!f.exists()) {
				continue;
			}
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(key));

			while ((line = reader.readLine()) != null) {
				writer.write(line + "\n");
			}
			reader.close();
		}

		writer.close();

		return true;
	}

}
