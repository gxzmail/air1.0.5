package sse.ngts.testrobot.app.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.apache.log4j.Logger;

import sse.db.common.GridBagHelper;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.common.IniOperator;

public class EnvCfgScreen extends JDialog {

	private static EnvCfgScreen instance = null;
	private HashMap<String, Properties> sections = null;
	private JButton btnSave = new JButton("保存"); 
	
	private JLabel [] label = new JLabel[200];
	private JTextField [] field = new JTextField[200];
	private int icnt = 0;
	
	private Logger logger = Logger.getLogger(EnvCfgScreen.class);
	
	private String cfgFile = ConstValues.Excute_EnvFileName;
	
	public static EnvCfgScreen getInstance() {
		if (instance == null) {
			instance = new EnvCfgScreen();
		}
		return instance;
	}
	public EnvCfgScreen() {
		init(this.cfgFile);
	}
	public EnvCfgScreen(String file) {
		this.cfgFile = file;
		init(this.cfgFile);
	}
	
	private void init(String file)
	{
		IniOperator reader;
		try {
			reader = new IniOperator();
			if(!reader.ReadIni(file))
			{
				return;
			}			
			sections = reader.getMap();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		initComponents();
		this.setResizable(true);
		this.setSize(800, 450);
		this.setLocation(100, 100);
		this.setVisible(true);
	}
	
	/**
	 * *********************************************************** 函数说明： TODO
	 * 创建人: neusoft 创建日期: 2013-9-13 下午3:01:37 参数说明： void
	 */
	private void initComponents() {
		JPanel container = new JPanel(new GridBagLayout());
		GridBagLayout g = new GridBagLayout();
		container.setLayout(g);
		
		Set<Entry<String, Properties>> sets = sections.entrySet();
		Iterator<Entry<String, Properties>> iterator = sets.iterator();
		
		int ilab = 0;
		
		while (iterator.hasNext()) {
			
			Entry<String, Properties> section = iterator.next();
			String key = section.getKey();
			Properties props = section.getValue();
			//System.out.println("key:" + key);
			Enumeration<Object> keyEnum = props.keys();
			while (keyEnum.hasMoreElements()) {
				if(icnt >= 200)
				{
					logger.error("字段已超出配置，退出");
					return;
				}
				String id = (String) keyEnum.nextElement();
				//System.out.println("id:" + id);
				label[icnt] = new JLabel(key + "~" + id);
				String v = props.getProperty(id);
				//System.out.println("value:" + v);
				field[icnt] = new JTextField(v);
				GridBagHelper.addComponent(container, label[icnt], ilab%4, icnt/2, 1, 1, 50, 2, 0.0, 0.0,
						GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
				ilab++;
				GridBagHelper.addComponent(container, field[icnt], ilab%4, icnt/2, 1, 1, 50, 2, 0.0, 0.0,
						GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
				ilab++;
				icnt++;
			}
			
		}
		GridBagHelper.addComponent(container, btnSave, 1, (icnt+1)/2, 1, 1, 2, 2, 0.0, 0.0,
				GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < icnt; i++)
				{
					String labtxt = label[i].getText();
					String[] keyvalue = labtxt.split("~");
					Properties props = sections.get(keyvalue[0]);
					props.setProperty(keyvalue[1], field[i].getText());
					sections.put(keyvalue[0], props);					
				}
				
				IniOperator.SaveIni(cfgFile, sections);
				
			}
		});
		add(container);
	}

}
