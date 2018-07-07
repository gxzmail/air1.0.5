/*
 * 
 */
package sse.ngts.testrobot.app.excel;

import java.util.Hashtable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class CaseSheetCommon.
 */
public class CaseSheetCommon {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CaseSheetCommon.class);

	/**
	 * Check sheet title.
	 * 
	 * @param data
	 *            -- ������
	 * @param titleDesc
	 *            -- �б���
	 * @return the hashtable
	 */
	public static Hashtable<String, Integer> CheckSheetTitle(String[] data,
			String[] titleDesc)

	{
		boolean bAll = true;
		boolean bFlg = false;
		Hashtable<String, Integer> index = new Hashtable<String, Integer>();

		for (int i = 0; i < titleDesc.length; i++) {
			bFlg = false;
			if (titleDesc[i] == null || titleDesc[i].isEmpty())
				continue;
			for (int k = 0; k < data.length; k++) {
				if (data[k].equalsIgnoreCase(titleDesc[i])) {
					bFlg = true;
					index.put(titleDesc[i], k);
					break;
				}
			}
			if (!bFlg) {
				logger.error("�С�{0}��δ�ҵ�"+ titleDesc[i]);
				bAll = false;
			}
		}

		return index;
	}
}
