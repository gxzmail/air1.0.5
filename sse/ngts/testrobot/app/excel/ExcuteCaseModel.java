/*
 * 
 */
package sse.ngts.testrobot.app.excel;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcuteCaseModel.
 */
public class ExcuteCaseModel {

	/** The title. */
	public final String title[] = { "步骤编号", "脚本编号", "脚本描述", "脚本执行内容", "交易日",
			"执行阶段", "预期结果", "主机", "批处理", "错误状态", "优先级", "备注" };

	/** 步骤编号. */
	private String stepsId;

	/** 脚本编号. */
	private String scriptId;

	/** 脚本描述. */
	private String descrip;
	
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
	
	/** 批处理. */
	private String testBatch;
	
	/** 错误状态. */
	private String testStatus;
	
	/** 优先级. */
	private String testPrior;
	
	/** 备注. */
	private String testMemo;

	// private String caseDetials;

	/**
	 * Gets the descrip.
	 *
	 * @return the descrip
	 */
	public String getDescrip() {
		return descrip;
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
	 * Gets the steps id.
	 *
	 * @return the steps id
	 */
	public String getStepsId() {
		return stepsId;
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
	 * Gets the test memo.
	 *
	 * @return the test memo
	 */
	public String getTestMemo() {
		return testMemo;
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String[] getTitle() {
		return title;
	}

	/**
	 * Sets the descrip.
	 *
	 * @param descrip the new descrip
	 */
	public void setDescrip(String descrip) {
		this.descrip = descrip;
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
	 * Sets the steps id.
	 *
	 * @param stepsId the new steps id
	 */
	public void setStepsId(String stepsId) {
		this.stepsId = stepsId;
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
	 * Sets the test memo.
	 *
	 * @param testMemo the new test memo
	 */
	public void setTestMemo(String testMemo) {
		this.testMemo = testMemo;
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

}
