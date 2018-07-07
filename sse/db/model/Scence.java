package sse.db.model;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenceModel.
 */
public class Scence extends Model implements IModel {

	/* 选择 */
	public static final int CHOIECE_IDX = 0;
	/* ID */
	public static final int ID_IDX = 1;
	/* 项目 */
	public static final int PROJECT_IDX = 2;
	/* 版本 */
	public static final int VERSION_IDX = 3;
	/* 场景类别 */
	public static final int SCRIPTTYPE_IDX = 4;
	/* 场景ID */
	public static final int SCENCEID_IDX = 5;
	/* 用例编号 */
	public static final int SCRIPTID_IDX = 6;
	/* 用例描述 */
	public static final int CASEDESC_IDX = 7;
	/* 测试环境 */
	public static final int TESTENVR_IDX = 8;
	/* 需要的交易日 */
	public static final int TESTDATE_IDX = 9;
	/* 主机 */
	public static final int TESTHOST_IDX = 10;
	/* 脚本链接 */
	public static final int TESTLINK_IDX = 11;
	/* 优先级 */
	public static final int TESTPRIOR_IDX = 12;
	/* 正常/异常 */
	public static final int TESTRESULT_IDX = 13;
	/* 场景所在目录 */
	public static final int SCENCEPATH_IDX = 14;
	/* 场景名 */
	public static final int SCENCENAME_IDX = 15;
	/* 提交日期 */
	public static final int COMMITTIME_IDX = 16;

	public final static Object constValue[][] = {
			{ "选择", Boolean.class, 20, CHOIECE_IDX },
			{ "ID", Long.class, 20, ID_IDX },
			{ "项目", String.class, 60, PROJECT_IDX },
			{ "版本", Integer.class, 20, VERSION_IDX },
			{ "场景类别", String.class, 40, SCRIPTTYPE_IDX },
			{ "场景ID", String.class, 80, SCENCEID_IDX },
			{ "用例编号", String.class, 50, SCRIPTID_IDX },
			{ "用例描述", String.class, 380, CASEDESC_IDX },
			{ "测试环境", String.class, 80, TESTENVR_IDX },
			{ "需要的交易日", String.class, 80, TESTDATE_IDX },
			{ "主机", String.class, 80, TESTHOST_IDX },
			{ "脚本链接", String.class, 80, TESTLINK_IDX },
			{ "优先级", String.class, 80, TESTPRIOR_IDX },
			{ "正常/异常", String.class, 80, TESTRESULT_IDX },
			{ "场景所在目录", String.class, 80, SCENCEPATH_IDX },
			{ "场景名", String.class, 80, SCENCENAME_IDX },
			{ "提交日期", String.class, 80, COMMITTIME_IDX } };

	public final static String[] tableHeaderTitle = { "选择", "ID", "场景类别",
			"用例编号", "用例描述", "版本" };
	
	public static final String[] ExcelTitle = {"场景类别", "场景ID", "用例编号", "用例描述",
		"测试环境", "需要的交易日", "主机", "脚本链接", "优先级", "正常/异常" };;
	
	private boolean bcheck = false;

	/** The id. */
	private long id = 0;

	/** The project. */
	private String project = "";

	/** The version. */
	private int version = 0;

	/** 场景类别. */
	private String scenceType = "";

	/** 场景ID. */
	private String scenceId = "";

	/** 用例编号. */
	private String caseId = "";

	/** 用例描述. */
	private String caseDesc = "";

	/** 测试环境. */
	private String testEnvr = "";

	/** 需要的交易日. */
	private String testDate = "";

	/** 主机. */
	private String testHost = "";

	/** 脚本链接. */
	private String testLink = "";

	/** 优先级. */
	private String testPrior = "";

	/** 正常/异常. */
	private String testResult = "";

	/** 场景所在目录. */
	private String scencePath = "";

	/** 场景编号. */
	private String scenceName = "";

	private Timestamp commitTime;

	public static void main(String[] args) {
		Scence.getColumnIndex("用例编号");
	}

	public static String[] getTitles() {
		String obj[] = new String[constValue.length];

		for (int i = 0; i < constValue.length; i++) {
			obj[i] = (String) constValue[i][0];
		}

		return obj;
	}

	public static String getTitle(int index) {
		String str[] = getTitles();
		if (index > str.length) {
			return "";
		}
		return str[index];
	}

	public static int[] getColumnWidths() {
		int obj[] = new int[constValue.length];

		for (int i = 0; i < constValue.length; i++) {
			obj[i] = (Integer) constValue[i][2];
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
				index = (Integer) constValue[i][3];
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
		case CHOIECE_IDX:
			str = bcheck;
			break;
		case ID_IDX:
			str = id;
			break;
		case SCRIPTTYPE_IDX:
			str = scenceType;
			break;
		case SCENCEID_IDX:
			str = scenceId;
			break;
		case SCRIPTID_IDX:
			str = caseId;
			break;
		case CASEDESC_IDX:
			str = caseDesc;
			break;
		case TESTENVR_IDX:
			str = testEnvr;
			break;
		case TESTDATE_IDX:
			str = testDate;
			break;
		case TESTHOST_IDX:
			str = testHost;
			break;
		case TESTLINK_IDX:
			str = testLink;
			break;
		case TESTPRIOR_IDX:
			str = testPrior;
			break;
		case TESTRESULT_IDX:
			str = testResult;
			break;
		case SCENCEPATH_IDX:
			str = scencePath;
			break;

		case SCENCENAME_IDX:
			str = scenceName;
			break;
		case VERSION_IDX:
			str = version;
			break;
		case PROJECT_IDX:
			str = project;
			break;
		case COMMITTIME_IDX:
			str = commitTime;
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
		case CHOIECE_IDX:
			bcheck = Boolean.parseBoolean(value);
			break;
		case ID_IDX:
			id = Long.parseLong(value);
			break;
		case SCRIPTTYPE_IDX:
			scenceType = value;
			break;
		case SCENCEID_IDX:
			scenceId = value;
			break;
		case SCRIPTID_IDX:
			caseId = value;
			break;
		case CASEDESC_IDX:
			caseDesc = value;
			break;
		case TESTENVR_IDX:
			testEnvr = value;
			break;
		case TESTDATE_IDX:
			testDate = value;
			break;
		case TESTHOST_IDX:
			testHost = value;
			break;
		case TESTLINK_IDX:
			testLink = value;
			break;
		case TESTPRIOR_IDX:
			testPrior = value;
			break;
		case TESTRESULT_IDX:
			testResult = value;
			break;
		case SCENCEPATH_IDX:
			scencePath = value;
			break;

		case SCENCENAME_IDX:
			scenceName = value;
			break;
		case VERSION_IDX:
			version = Integer.parseInt(value);
			break;
		case PROJECT_IDX:
			project = value;
			break;
		case COMMITTIME_IDX:
			commitTime = null;
		default:
			bFlg = false;
			break;
		}

		return bFlg;
	}

	public static Object getColumnType(String title) {
		int index = getColumnIndex(title);
		Object obj[] = getColumnTypes();
		if (index > obj.length) {
			return Object.class;
		}
		return obj[index];
	}

	public static int getColumnWidth(String title) {
		int index = getColumnIndex(title);
		int obj[] = getColumnWidths();
		if (index > obj.length) {
			return -1;
		}
		return obj[index];
	}

	/*---------------------------------------------------------------------------*/
	public boolean isBcheck() {
		return bcheck;
	}

	public void setBcheck(boolean bcheck) {
		this.bcheck = bcheck;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getScenceType() {
		return scenceType;
	}

	public void setScenceType(String scenceType) {
		this.scenceType = scenceType;
	}

	public String getScenceId() {
		return scenceId;
	}

	public void setScenceId(String scenceId) {
		this.scenceId = scenceId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getTestEnvr() {
		return testEnvr;
	}

	public void setTestEnvr(String testEnvr) {
		this.testEnvr = testEnvr;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestHost() {
		return testHost;
	}

	public void setTestHost(String testHost) {
		this.testHost = testHost;
	}

	public String getTestLink() {
		return testLink;
	}

	public void setTestLink(String testLink) {
		this.testLink = testLink;
	}

	public String getTestPrior() {
		return testPrior;
	}

	public void setTestPrior(String testPrior) {
		this.testPrior = testPrior;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getScencePath() {
		return scencePath;
	}

	public void setScencePath(String scencePath) {
		this.scencePath = scencePath;
	}

	public String getScenceName() {
		return scenceName;
	}

	public void setScenceName(String scenceName) {
		this.scenceName = scenceName;
	}

	public Timestamp getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Timestamp commitTime) {
		this.commitTime = commitTime;
	}
	/**
	 * 通过反转实现设置
	 * @param path
	 */
	public void setPath(String path)
	{
		this.scencePath = path;
	}
	
	/**
	 * 通过反转实现设置
	 * @param pathname
	 */
	public void setFileName(String pathname)
	{
		this.scenceName = pathname;
	}
	
}
