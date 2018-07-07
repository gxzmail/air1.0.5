package sse.db.model;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenceModel.
 */
public class Scence extends Model implements IModel {

	/* ѡ�� */
	public static final int CHOIECE_IDX = 0;
	/* ID */
	public static final int ID_IDX = 1;
	/* ��Ŀ */
	public static final int PROJECT_IDX = 2;
	/* �汾 */
	public static final int VERSION_IDX = 3;
	/* ������� */
	public static final int SCRIPTTYPE_IDX = 4;
	/* ����ID */
	public static final int SCENCEID_IDX = 5;
	/* ������� */
	public static final int SCRIPTID_IDX = 6;
	/* �������� */
	public static final int CASEDESC_IDX = 7;
	/* ���Ի��� */
	public static final int TESTENVR_IDX = 8;
	/* ��Ҫ�Ľ����� */
	public static final int TESTDATE_IDX = 9;
	/* ���� */
	public static final int TESTHOST_IDX = 10;
	/* �ű����� */
	public static final int TESTLINK_IDX = 11;
	/* ���ȼ� */
	public static final int TESTPRIOR_IDX = 12;
	/* ����/�쳣 */
	public static final int TESTRESULT_IDX = 13;
	/* ��������Ŀ¼ */
	public static final int SCENCEPATH_IDX = 14;
	/* ������ */
	public static final int SCENCENAME_IDX = 15;
	/* �ύ���� */
	public static final int COMMITTIME_IDX = 16;

	public final static Object constValue[][] = {
			{ "ѡ��", Boolean.class, 20, CHOIECE_IDX },
			{ "ID", Long.class, 20, ID_IDX },
			{ "��Ŀ", String.class, 60, PROJECT_IDX },
			{ "�汾", Integer.class, 20, VERSION_IDX },
			{ "�������", String.class, 40, SCRIPTTYPE_IDX },
			{ "����ID", String.class, 80, SCENCEID_IDX },
			{ "�������", String.class, 50, SCRIPTID_IDX },
			{ "��������", String.class, 380, CASEDESC_IDX },
			{ "���Ի���", String.class, 80, TESTENVR_IDX },
			{ "��Ҫ�Ľ�����", String.class, 80, TESTDATE_IDX },
			{ "����", String.class, 80, TESTHOST_IDX },
			{ "�ű�����", String.class, 80, TESTLINK_IDX },
			{ "���ȼ�", String.class, 80, TESTPRIOR_IDX },
			{ "����/�쳣", String.class, 80, TESTRESULT_IDX },
			{ "��������Ŀ¼", String.class, 80, SCENCEPATH_IDX },
			{ "������", String.class, 80, SCENCENAME_IDX },
			{ "�ύ����", String.class, 80, COMMITTIME_IDX } };

	public final static String[] tableHeaderTitle = { "ѡ��", "ID", "�������",
			"�������", "��������", "�汾" };
	
	public static final String[] ExcelTitle = {"�������", "����ID", "�������", "��������",
		"���Ի���", "��Ҫ�Ľ�����", "����", "�ű�����", "���ȼ�", "����/�쳣" };;
	
	private boolean bcheck = false;

	/** The id. */
	private long id = 0;

	/** The project. */
	private String project = "";

	/** The version. */
	private int version = 0;

	/** �������. */
	private String scenceType = "";

	/** ����ID. */
	private String scenceId = "";

	/** �������. */
	private String caseId = "";

	/** ��������. */
	private String caseDesc = "";

	/** ���Ի���. */
	private String testEnvr = "";

	/** ��Ҫ�Ľ�����. */
	private String testDate = "";

	/** ����. */
	private String testHost = "";

	/** �ű�����. */
	private String testLink = "";

	/** ���ȼ�. */
	private String testPrior = "";

	/** ����/�쳣. */
	private String testResult = "";

	/** ��������Ŀ¼. */
	private String scencePath = "";

	/** �������. */
	private String scenceName = "";

	private Timestamp commitTime;

	public static void main(String[] args) {
		Scence.getColumnIndex("�������");
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
	 * ͨ����תʵ������
	 * @param path
	 */
	public void setPath(String path)
	{
		this.scencePath = path;
	}
	
	/**
	 * ͨ����תʵ������
	 * @param pathname
	 */
	public void setFileName(String pathname)
	{
		this.scenceName = pathname;
	}
	
}
