package sse.ngts.testrobot.app.func;

public class StatusResult {
	/* ������� */
	private String id = "";
	
	/* ������ */
	private String tradeDate = "";
	
	/* ִ�н�� */
	private String result = "";
	/*  */
	private String beginTime = "";
	
	private String endTime = "";
	
	private String costTime = "";
	
	/* ִ�б��� */
	private String execLog = "";

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public String getExecLog() {
		return execLog;
	}

	public void setExecLog(String execLog) {
		this.execLog = execLog;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
	
	
}
