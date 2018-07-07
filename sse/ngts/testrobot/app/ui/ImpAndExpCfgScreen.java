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
		this.setTitle("���뵼�������ļ�:[air_configs.txt��EzSTEPUser.ini��security.dat��AIRCYCLE.txt]");
		initCompents();
		this.setResizable(true);
		this.setSize(300, 200);
		this.setLocation(100, 100);
		this.setVisible(true);
	}

	private void initCompents() {
		JButton btnImport = new JButton("�������õ�����");
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
										"�������óɹ����������޸ĸ��������뻷���Ų�ͬ���ʺš����롢��������Ϣ��EzStep Rules�ѷ��������°汾Ϊ׼");
					}
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}

			}
		});

		JButton btnExport = new JButton("�������ù�����");

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
								"�����ļ�������[" + ConstValues.IMPEXP_FILE_CONFIG
										+ "]�ɹ�,���ڽ���ʱ���ʹ��ļ�");
					}
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
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
			JOptionPane.showConfirmDialog(ImpAndExpCfgScreen.this, "��������"
					+ impFile + "�����ڣ�����ʧ��");
			return false;
		}

		BufferedReader reader = new BufferedReader(new FileReader(impFile));
		String line = null;
		String cfgFile = "";
		BufferedWriter writer = null;
		/**
		 * ����>>>>>�����ļ�������д�ļ�
		 */

		while ((line = reader.readLine()) != null) {
			if (line.startsWith(">>>>>")) {

				if (writer != null) {
					writer.close();
				}

				cfgFile = line.replace(">>>>>", "").trim();
				logger.info("��������:" + cfgFile);
				// ���ڵ����б����ļ�
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
			logger.info("��������:" + key);
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
