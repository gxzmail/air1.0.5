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
	/** 测试计划编号，自动增长. */
	private long id = 0;

	/** 被测试系统. */
	private String project = "";

	/** 被测试系统版本. */
	private String version = "";

	/** 测试计划ID 20位字符. */
	private String planid = "";

	/** 测试状态， 见 PlanStatus. */
	private String status = "";

	/** 测试计划创建人. */
	private String author = "";

	/** 测试计划创建时间. */
	private Timestamp creatTime = null;

	/** 测试计划执行开始时间,Default YYYY-MM-DD. */
	private String planStartDate = "";

	/** 测试计划预计结束时间. */
	private String planEndDate = "";

	/** 里程碑设定时间，不能为空 Default YYYY-MM-DD. */
	private String milestoneDate = "";
	/** 最新一次更新时间 */
	private String latestUpdTime = "";

	/* 选择 */
	public static final int CHOIECE_IDX = 0;
	/* ID */
	public static final int ID_IDX = 1;
	/* 项目 */
	public static final int PROJECT_IDX = 2;
	/* 版本 */
	public static final int VERSION_IDX = 3;
	/* 测试计划编号 */
	public static final int PLANID_IDX = 4;
	/* 状态 */
	public static final int STATUS_IDX = 5;
	/* 作者 */
	public static final int AUTHOR_IDX = 6;
	/* 创建时间 */
	public static final int CREATTIME_IDX = 7;
	/* 计划开始时间 */
	public static final int PLANSTARTDATE_IDX = 8;
	/* 计划结束时间 */
	public static final int PLANENDDATE_IDX = 9;
	/* 里程碑时间 */
	public static final int MILESTONEDATE_IDX = 10;
	/* 最后更新时间 */
	public static final int LATESTUPDTIME_IDX = 11;

	public final static Object constValue[][] = {
			{ "选择", Boolean.class, 40, CHOIECE_IDX },
			{ "ID", Long.class, 40, ID_IDX },
			{ "项目", String.class, 40, PROJECT_IDX },
			{ "版本", String.class, 80, VERSION_IDX },
			{ "测试计划编号", String.class, 160, PLANID_IDX },
			{ "状态", String.class, 60, STATUS_IDX },
			{ "作者", String.class, 60, AUTHOR_IDX },
			{ "创建时间", String.class, 120, CREATTIME_IDX },
			{ "计划开始时间", String.class, 80, PLANSTARTDATE_IDX },
			{ "计划结束时间", String.class, 80, PLANENDDATE_IDX },
			{ "里程碑时间", String.class, 80, MILESTONEDATE_IDX },
			{ "最后更新时间", String.class, 120, LATESTUPDTIME_IDX } };

	/**
	 * <p>
	 * INIT -- 初始状态，未选择执行用例，只创建测试计划
	 * </p>
	 * <p>
	 * READY -- 选择的测试用例，但还未执行，此时可能用例还未选择完毕
	 * </p>
	 * <p>
	 * ASSIGN -- 用例已经指派给对应的人员进行执行
	 * </p>
	 * <p>
	 * RUN -- 测试计划正在执行中
	 * </p>
	 * <p>
	 * OVER -- 测试计划已完成
	 * </p>
	 * <p>
	 * CLOSE -- 测试计划已关闭
	 * </p>
	 * <p>
	 * PAUSE -- 测试计划被暂停
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
