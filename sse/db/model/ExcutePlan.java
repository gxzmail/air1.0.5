package sse.db.model;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcutePlan.
 */
public class ExcutePlan extends Model {
	
	private boolean bcheck = false;
	
	/** The id. */
	private long id = 0;
	
	/** The plan id. */
	private String planId = "";
	
	/** The script id. */
	private String scriptId = "";
	
	/** The version. */
	private int version = 0;
	
	public final static Object constValue[][] = { {"选择", Boolean.class, 80 },
		{ "ID", Long.class, 80 } ,{"测试计划编号", String.class, 80}, 
		{"用例编号", String.class, 80}, {"用例版本", String.class,80}};
	
	public static String[] getTitles() {
		String obj[] = new String[constValue.length];

		for (int i = 0; i < constValue.length; i++) {
			obj[i] = (String)constValue[i][0];
		}

		return obj;
	}
	public static String getTitle(int index)
	{
		String str[] = getTitles();
		if(index > str.length)
		{
			return "";
		}
		return str[index];
	}
	public static int[] getColumnWidths() {
		int obj[] = new int[constValue.length];

		for (int i = 0; i < constValue.length; i++) {
			obj[i] = (Integer)constValue[i][2];
		}

		return obj;
	}
	
	public static Object[] getColumnTypes() {
		Object obj[] = new Object[constValue.length];

		for (int i = 0; i < constValue.length; i++) {
			obj[i] = constValue[i][1];
		}

		return obj;
	}
	
	public static int getColumnIndex(String colname) {
		int index = -1;
		if (colname == null || colname.trim().length() == 0) {
			return index;
		}
		Object obj[] = getTitles();
		for (int i = 0; i < obj.length; i++) {
			if (colname.trim().equalsIgnoreCase((String) obj[i])) {
				index = i;
				break;
			}
		}
		return index;
	}


	/**
	 * Gets the value.
	 * 
	 * @param index
	 *            the index
	 * @return the value
	 */
	public Object getValue(int index) {
		Object str = null;

		switch (index) {
		case 0:
			str = bcheck;
			break;
		case 1:
			str = id;
			break;
		case 2:
			str = planId;
			break;
		case 3:
			str = scriptId;
			break;
		case 4:
			str = version;
			break;		
		default:
			str = "";
			break;
		}

		return str;
	}

	/**
	 * Sets the value.
	 * 
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean setValue(int index, String value) {
		boolean bFlg = true;

		switch (index) {
		case 0:
			bcheck = Boolean.parseBoolean(value);
			break;
		case 1:
			id = Long.parseLong(value);
			break;
		case 2:
			planId = value;
			break;
		case 3:
			scriptId = value;
			break;
		case 4:
			version = Integer.parseInt(value);
			break;
	
		default:
			bFlg = false;
			break;
		}

		return bFlg;
	}

	public static Object getColumnType(String title) {
		int index = getColumnIndex(title);
		Object obj[] = getColumnTypes();
		if(index > obj.length)
		{
			return Object.class;
		}
		return obj[index];
	}

	public static int getColumnWidth(String title) {
		int index = getColumnIndex(title);
		int obj[] = getColumnWidths();
		if(index > obj.length)
		{
			return -1;
		}
		return obj[index];
	}
	
	/*---------------------------------------------------------*/
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Gets the plan id.
	 *
	 * @return the plan id
	 */
	public String getPlanId() {
		return planId;
	}
	
	/**
	 * Gets the script id.
	 *
	 * @return the script id
	 */
	public String getScriptId() {
		return scriptId;
	}
	
	/**
	 * 获得执行用例对应的SCRIPT版本
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Sets the plan id.
	 *
	 * @param planId the new plan id
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	/**
	 * 设置执行用例对应的script版本
	 *
	 * @param scriptId the new script id
	 */
	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}
	
	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
}
