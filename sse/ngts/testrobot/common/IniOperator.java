package sse.ngts.testrobot.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class IniOperator {

	protected HashMap<String, Properties> sections = new HashMap<String, Properties>();
	private String currentSection;
	private Properties current;
	private IniOperator reader = null;

	private static Logger logger = Logger.getLogger(IniOperator.class);

	public HashMap<String, Properties> getMap() {
		return sections;
	}

	public IniOperator() {

	}

	public IniOperator(String filename) {
		try {
			ReadIni(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean ReadIni(String filename) throws IOException {
		File f = new File(filename);
		if (!f.exists()) {
			logger.error("配置文件[" + filename + "]不存在");
			return false;
		}
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		read(reader);
		reader.close();
		return true;
	}

	protected void read(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			parseLine(line);

		}
	}

	protected void parseLine(String line) {
		line = line.trim();
		if (line.matches("\\[.*\\]")) {
			currentSection = line.replaceFirst("\\[(.*)\\]", "$1").trim();
			current = new Properties();
			sections.put(currentSection, current);

		} else if (line.matches(".*=.*")) {
			if (current != null) {
				int i = line.indexOf('=');
				String name = line.substring(0, i).trim();
				String value = line.substring(i + 1).trim();
				current.setProperty(name, value);
			}
		}
	}

	public String getValue(String section, String name) {
		Properties p = (Properties) sections.get(section);
		if (p == null) {
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}

	public static boolean SaveIni(String iniFil, String section, String name,
			String value) {
		IniOperator ini = new IniOperator(iniFil);
		HashMap<String, Properties> prop = ini.getMap();
		Properties p = prop.get(section);
		if(p == null)
		{
			p = new Properties();
			
		}
		p.setProperty(name, value);
		prop.put(section, p);
		IniOperator.SaveIni(iniFil, prop);
		return true;
	}

	public static boolean SaveIni(String iniFil,
			HashMap<String, Properties> sections) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(iniFil
					+ ".bak"));

			Set<Entry<String, Properties>> sets = sections.entrySet();
			Iterator<Entry<String, Properties>> iterator = sets.iterator();
			while (iterator.hasNext()) {
				Entry<String, Properties> section = iterator.next();
				String key = section.getKey();
				writer.write("[" + key + "]\n");
				Properties props = section.getValue();
				Enumeration<Object> keyEnum = props.keys();
				while (keyEnum.hasMoreElements()) {
					String id = (String) keyEnum.nextElement();
					String v = props.getProperty(id);
					writer.write(id + " = " + v + "\n");
				}
			}

			writer.close();
			FileUtils.deleteQuietly(new File(iniFil));
			FileUtils.moveFile(new File(iniFil + ".bak"), new File(iniFil));

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

}
