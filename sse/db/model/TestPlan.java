package sse.db.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// TODO: Auto-generated Javadoc
/**
 * The Class TestPlan.
 */
public class TestPlan extends Model implements IModel {

	/**
	 * Gets the plan status.
	 * 
	 * @return the plan status
	 */
	public static String[] getPlanStatus() {
		return PlanStatus;
	}

	/**
	 * Sets the plan status.
	 * 
	 * @param planStatus
	 *            the new plan status
	 */
	public static void setPlanStatus(String[] planStatus) {
		PlanStatus = planStatus;
	}

	private boolean bcheck = false;
	/** ���Լƻ���ţ��Զ�����. */
	private long id = 0;

	/** ������ϵͳ. */
	private String project = "";

	/** ������ϵͳ�汾. */
	private String version = "";

	/** ���Լƻ�ID 20λ�ַ�. */
	private String planid = "";

	/** ����״̬�� �� PlanStatus. */
	private String status = "";

	/** ���Լƻ�������. */
	private String author = "";

	/** ���Լƻ�����ʱ��. */
	private Timestamp creatTime = null;

	/** ���Լƻ�ִ�п�ʼʱ��,Default YYYY-MM-DD. */
	private String planStartDate = "";

	/** ���Լƻ�Ԥ�ƽ���ʱ��. */
	private String planEndDate = "";

	/** ��̱��趨ʱ�䣬����Ϊ�� Default YYYY-MM-DD. */
	private String milestoneDate = "";
	/** ����һ�θ���ʱ�� */
	private String latestUpdTime = "";

	/* ѡ�� */
	public static final int CHOIECE_IDX = 0;
	/* ID */
	public static final int ID_IDX = 1;
	/* ��Ŀ */
	public static final int PROJECT_IDX = 2;
	/* �汾 */
	public static final int VERSION_IDX = 3;
	/* ���Լƻ���� */
	public static final int PLANID_IDX = 4;
	/* ״̬ */
	public static final int STATUS_IDX = 5;
	/* ���� */
	public static final int AUTHOR_IDX = 6;
	/* ����ʱ�� */
	public static final int CREATTIME_IDX = 7;
	/* �ƻ���ʼʱ�� */
	public static final int PLANSTARTDATE_IDX = 8;
	/* �ƻ�����ʱ�� */
	public static final int PLANENDDATE_IDX = 9;
	/* ��̱�ʱ�� */
	public static final int MILESTONEDATE_IDX = 10;
	/* ������ʱ�� */
	public static final int LATESTUPDTIME_IDX = 11;

	public final static Object constValue[][] = {
			{ "ѡ��", Boolean.class, 40, CHOIECE_IDX },
			{ "ID", Long.class, 40, ID_IDX },
			{ "��Ŀ", String.class, 40, PROJECT_IDX },
			{ "�汾", String.class, 80, VERSION_IDX },
			{ "���Լƻ����", String.class, 160, PLANID_IDX },
			{ "״̬", String.class, 60, STATUS_IDX },
			{ "����", String.class, 60, AUTHOR_IDX },
			{ "����ʱ��", String.class, 120, CREATTIME_IDX },
			{ "�ƻ���ʼʱ��", String.class, 80, PLANSTARTDATE_IDX },
			{ "�ƻ�����ʱ��", String.class, 80, PLANENDDATE_IDX },
			{ "��̱�ʱ��", String.class, 80, MILESTONEDATE_IDX },
			{ "������ʱ��", String.class, 120, LATESTUPDTIME_IDX } };

	/**
	 * <p>
	 * INIT -- ��ʼ״̬��δѡ��ִ��������ֻ�������Լƻ�
	 * </p>
	 * <p>
	 * READY -- ѡ��Ĳ�������������δִ�У���ʱ����������δѡ�����
	 * </p>
	 * <p>
	 * ASSIGN -- �����Ѿ�ָ�ɸ���Ӧ����Ա����ִ��
	 * </p>
	 * <p>
	 * RUN -- ���Լƻ�����ִ����
	 * </p>
	 * <p>
	 * OVER -- ���Լƻ������
	 * </p>
	 * <p>
	 * CLOSE -- ���Լƻ��ѹر�
	 * </p>
	 * <p>
	 * PAUSE -- ���Լƻ�����ͣ
	 * </p>
	 * .
	 */
	public static String[] PlanStatus = { "INIT", "READY", "ASSIGN", "RUN",
			"OVER", "CLOSE" };

	public Object getValue(int index) {
		Object obj = null;
		switch (index) {
		case CHOIECE_IDX:
			obj = bcheck;
			break;
		case ID_IDX:
			obj = id;
			break;
		case PROJECT_IDX:
			obj = project;
			break;
		case VERSION_IDX:
			obj = version;
			break;
		case PLANID_IDX:
			obj = planid;
			break;
		case STATUS_IDX:
			obj = status;
			break;
		case AUTHOR_IDX:
			obj = author;
			break;
		case CREATTIME_IDX:
			obj = creatTime;
			break;
		case PLANSTARTDATE_IDX:
			obj = planStartDate;
			break;
		case PLANENDDATE_IDX:
			obj = planEndDate;
			break;
		case MILESTONEDATE_IDX:
			obj = milestoneDate;
			break;
		case LATESTUPDTIME_IDX:
			obj = latestUpdTime;
			break;
		default:
			break;

		}

		return obj;
	}

	public boolean setValue(int index, String value) {
		boolean bFlg = true;
		switch (index) {
		case CHOIECE_IDX:
			bcheck = Boolean.parseBoolean(value);
			break;
		case ID_IDX:
			id = Long.parseLong(value);
			break;
		case PROJECT_IDX:
			project = value;
			break;
		case VERSION_IDX:
			version = value;
			break;
		case PLANID_IDX:
			planid = value;
			break;
		case STATUS_IDX:
			status = value;
			break;
		case AUTHOR_IDX:
			author = value;
			break;
		case CREATTIME_IDX:
			creatTime = null;
			break;
		case PLANSTARTDATE_IDX:
			planStartDate = value;
			break;
		case PLANENDDATE_IDX:
			planEndDate = value;
			break;
		case MILESTONEDATE_IDX:
			milestoneDate = value;
			break;
		case LATESTUPDTIME_IDX:
			latestUpdTime = value;
			break;
		default:
			break;

		}
		return bFlg;
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

	/*--------------------------------------------------------------*/
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getMilestoneDate() {
		return milestoneDate;
	}

	public void setMilestoneDate(String milestoneDate) {
		this.milestoneDate = milestoneDate;
	}

	public String getLatestUpdTime() {
		return latestUpdTime;
	}

	public void setLatestUpdTime(String latestUpdTime) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		latestUpdTime = dateFormatter.format(new Date());
		this.latestUpdTime = latestUpdTime;
	}

}
