package sse.ngts.testrobot.app.func.cycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import sse.ngts.testrobot.app.ui.ExecuteResultDialog;
import sse.ngts.testrobot.common.Common;
import sse.ngts.testrobot.common.ConstValues;

/**
 * 读取AIR_CONFIGS.CFG文件
 * 
 * @author neusoft
 */
public class ApplXmlParse {

	private Logger logger = Logger.getLogger(ApplXmlParse.class);
	private Hashtable<String, String> xmlParse = new Hashtable<String, String>();

	private static ApplXmlParse instance;

	public static ApplXmlParse getinstance() {
		if (instance == null) {
			instance = new ApplXmlParse();
		}
		return instance;
	}

	public Hashtable<String, String> getXmlParse() {
		return xmlParse;
	}

	/* 2012-04-18 modified by wuli,end */
	public void handlenodelist(NodeList nodelist) {
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node child = nodelist.item(i);
			if (child instanceof Element) {
				Element childelment = (Element) child;
				NodeList nodelist1 = childelment.getChildNodes();
				if (nodelist1.getLength() == 1) {
					Text textNode = (Text) childelment.getFirstChild();
					String text = textNode.getData().trim();
					NamedNodeMap t = child.getAttributes();

					if (t.getLength() >= 1) {
						for (int k = 0; k < t.getLength(); k++) {
							Node tnode = t.item(k);
							xmlParse.put(tnode.getNodeValue().trim(), text);

						}
					} else {
						xmlParse.put(childelment.getTagName().trim(), text);

					}

				} else if (nodelist1.getLength() > 1) {
					handlenodelist(nodelist1);

				}
			}
		}
	}

	public boolean readxml() {
		File airconfig = new File(ConstValues.Excute_EnvFileName);

		if (airconfig.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(airconfig));
				String tempString = null;
				String keys = null;
				String values = null;
				while ((tempString = reader.readLine()) != null) {
					if (tempString.isEmpty())
						continue;
					if (tempString.trim().contains("#"))
						continue;
					if (tempString.trim().contains("=")) {
						keys = Common.getStringByToken(1, "=", tempString);
						values = Common.getStringByToken(2, "=", tempString);
						xmlParse.put(keys.trim(), values);
					}

				}

				logger.info("读文件成功:" + airconfig.getName());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("读文件失败，IO读写错误:" + airconfig.getAbsolutePath());
				ExecuteResultDialog.viewError(
						"执行失败,读文件" + airconfig.getAbsolutePath() + "失败",
						"ERROR");
				return false;
			} catch (Exception e) {
				logger.error("读文件}失败:" + airconfig.getName());
				ExecuteResultDialog.viewError(
						"执行失败,读文件" + airconfig.getAbsolutePath() + "失败",
						"ERROR");
				return false;

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}

		} else {
			logger.info("文件不存在:" + airconfig.getName());

			logger.info("执行手册生成失败 ");
			ExecuteResultDialog.viewError(
					"执行失败,文件" + airconfig.getAbsolutePath() + "不存在", "ERROR");
			return false;

		}

	}

}
