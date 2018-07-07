package sse.db.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

public class Utiles {

	public static Vector convertToVector(Object[] anArray) {
		if (anArray == null) {
			return null;
		}
		Vector v = new Vector(anArray.length);
		for (int i = 0; i < anArray.length; i++) {
			v.addElement(anArray[i]);
		}
		return v;
	}

	/**
	 * 返回当天日期
	 * 
	 * @return
	 */
	public static String today() {
		String today = "";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		today = dateFormatter.format(new Date());
		return today;
	}

}
