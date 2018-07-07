package sse.db.model;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class ScriptModel.
 */
public class Script extends Model {

	/** The Constant stepTitle. */
	public final static String stepTitle[] = { "脚本编号", "脚本描述", "脚本执行内容", "交易日",
			"执行阶段", "预期结果", "主机", "批步骤", "错误状态", "优先级", "备注" };
	public static final String ExcelTitle[] = { "脚本编号", "脚本描述", "脚本执行内容", "交易日",
		"执行阶段", "预期结果", "主机", "批步骤", "错误状态", "优先级", "备注" };
	
	/**
	 * Gets the steptitle.
	 *
	 * @return the steptitle
	 */
	public static String[] getSteptitle() {
		return stepTitle;
	}
	
	/** The id. */
	private long id = 0;
	
	/** The project. */
	private String project = "";
	
	/** The version. */
	private int version = 0;
	
	/** 脚本编号. */
	private String scriptId;
	
	/** 脚本描述. */
	private String scriptDesc;
	
	/** 脚本执行内容. */
	private String testContent;
	
	/** 交易日. */
	private String testDate;
	
	/** 执行阶段. */
	private String testPhase;
	
	/** 预期结果. */
	private String testAntic;
	
	/** 主机. */
	private String testHost;
	
	/** 批步骤. */
	private String testBatch;
	
	/** 错误状态. */
	private String testStatus;
	
	/** 优先级. */
	private String testPrior;

	/** 备注. */
	private String remark;
	
	/** Script所在目录. */
	private String scriptPath = "";
	
	/** Script文件名. */
	private String scriptName = "";
	/** The commit time. */
	private Timestamp commitTime;

	/**
	 * Gets the commit time.
	 *
	 * @return the commit time
	 */
	public Timestamp getCommitTime() {
		return commitTime;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the index.
	 *
	 * @param colname the colname
	 * @return the index
	 */
	public int getColumnIndex(String colname) {
		int index = 1;
		if (colname == null || colname.trim().length() == 0) {
			return index;
		}
		for (int i = 0; i < stepTitle.length; i++) {
			if (colname.trim().equalsIgnoreCase(stepTitle[i])) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Gets the remark.
	 *
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Gets the script desc.
	 *
	 * @return the script desc
	 */
	public String getScriptDesc() {
		return scriptDesc;
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
	 * Gets the script name.
	 *
	 * @return the script name
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * Gets the script path.
	 *
	 * @return the script path
	 */
	public String getScriptPath() {
		return scriptPath;
	}

	/**
	 * Gets the test antic.
	 *
	 * @return the test antic
	 */
	public String getTestAntic() {
		return testAntic;
	}

	/**
	 * Gets the test batch.
	 *
	 * @return the test batch
	 */
	public String getTestBatch() {
		return testBatch;
	}

	/**
	 * Gets the test content.
	 *
	 * @return the test content
	 */
	public String getTestContent() {
		return testContent;
	}

	/**
	 * Gets the test date.
	 *
	 * @return the test date
	 */
	public String getTestDate() {
		return testDate;
	}

	/**
	 * Gets the test host.
	 *
	 * @return the test host
	 */
	public String getTestHost() {
		return testHost;
	}

	/**
	 * Gets the test phase.
	 *
	 * @return the test phase
	 */
	public String getTestPhase() {
		return testPhase;
	}

	/**
	 * Gets the test prior.
	 *
	 * @return the test prior
	 */
	public String getTestPrior() {
		return testPrior;
	}

	/**
	 * Gets the test status.
	 *
	 * @return the test status
	 */
	public String getTestStatus() {
		return testStatus;
	}

	/**
	 * Gets the value.
	 *
	 * @param index the index
	 * @return the value
	 */
	public String getValue(int index) {
		String str = "";

		switch (index) {
		case 0:
			str = scriptId;
			break;
		case 1:
			str = scriptDesc;
			break;
		case 2:
			str = testContent;
			break;
		case 3:
			str = testDate;
			break;
		case 4:
			str = testPhase;
			break;
		case 5:
			str = testAntic;
			break;
		case 6:
			str = testHost;
			break;
		case 7:
			str = testBatch;
			break;
		case 8:
			str = testStatus;
			break;
		case 9:
			str = testPrior;
			break;
		case 10:
			str = remark;
			break;
		case 11:
			str = scriptPath;
			break;
		case 12:
			str = scriptName;
			break;
		default:
			str = "";
			break;
		}
		return str;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 根据已有的数据进行过滤，如果为真，表示此条数据可以过滤.
	 *
	 * @return true, if is filter
	 */
	public boolean isFilter() {
		boolean bFlg = false;

		return bFlg;
	}

	/**
	 * 根据设置的内容replace字段值.
	 */
	public void replaceValue() {

	}

	/**
	 * Sets the commit time.
	 *
	 * @param commitTime the new commit time
	 */
	public void setCommitTime(Timestamp commitTime) {
		this.commitTime = commitTime;
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
	 * Sets the project.
	 *
	 * @param project the new project
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * Sets the remark.
	 *
	 * @param remark the new remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Sets the script desc.
	 *
	 * @param scriptDesc the new script desc
	 */
	public void setScriptDesc(String scriptDesc) {
		this.scriptDesc = scriptDesc;
	}

	/**
	 * Sets the script id.
	 *
	 * @param scriptId the new script id
	 */
	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	/**
	 * Sets the script name.
	 *
	 * @param scriptName the new script name
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * Sets the script path.
	 *
	 * @param scriptPath the new script path
	 */
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	/**
	 * Sets the test antic.
	 *
	 * @param testAntic the new test antic
	 */
	public void setTestAntic(String testAntic) {
		this.testAntic = testAntic;
	}

	/**
	 * Sets the test batch.
	 *
	 * @param testBatch the new test batch
	 */
	public void setTestBatch(String testBatch) {
		this.testBatch = testBatch;
	}

	/**
	 * Sets the test content.
	 *
	 * @param testContent the new test content
	 */
	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	/**
	 * Sets the test date.
	 *
	 * @param testDate the new test date
	 */
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	/**
	 * Sets the test host.
	 *
	 * @param testHost the new test host
	 */
	public void setTestHost(String testHost) {
		this.testHost = testHost;
	}

	/**
	 * Sets the test phase.
	 *
	 * @param testPhase the new test phase
	 */
	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}

	/**
	 * Sets the test prior.
	 *
	 * @param testPrior the new test prior
	 */
	public void setTestPrior(String testPrior) {
		this.testPrior = testPrior;
	}

	/**
	 * Sets the test status.
	 *
	 * @param testStatus the new test status
	 */
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	/**
	 * Sets the value.
	 *
	 * @param index the index
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean setValue(int index, String value) {
		boolean bFlg = true;

		switch (index) {
		case 0:
			scriptId = value;
			break;
		case 1:
			scriptDesc = value;
			break;
		case 2:
			testContent = value;
			break;
		case 3:
			testDate = value;
			break;
		case 4:
			testPhase = value;
			break;
		case 5:
			testAntic = value;
			break;
		case 6:
			testHost = value;
			break;
		case 7:
			testBatch = value;
			break;
		case 8:
			testStatus = value;
			break;
		case 9:
			testPrior = value;
			break;
		case 10:
			remark = value;
		case 11:
			scriptPath = value;
			break;
		case 12:
			scriptName = value;
			break;
		default:
			bFlg = false;
			break;
		}

		return bFlg;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * 通过反转实现设置
	 * @param path
	 */
	public void setPath(String path)
	{
		this.scriptPath = path;
	}
	
	/**
	 * 通过反转实现设置
	 * @param pathname
	 */
	public void setFileName(String pathname)
	{
		this.scriptName = pathname;
	}
	
}
