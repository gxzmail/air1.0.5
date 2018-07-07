package sse.ngts.testrobot.app.execute.process;

public class AnalyseData {

	/* 总用例数 */
	private int sumSteps = 0;

	/* 错误用例数 */
	private int errSteps = 0;
	
	/* 成功用例数 */
	private int sucSteps = 0;	

	/* 未执行用例 */
	private int nonSteps = 0;

	/* 花费时间 */
	private long costTime = 0;

	/* 手工用例数 */
	private int manulSteps = 0;
	
	private long beginTime = 0;
	
	private long endTime = 0;
	

	public int getSumSteps() {
		return sumSteps;
	}
	
	public void setSumSteps(int sumSteps) {
		this.sumSteps = sumSteps;
	}

	public int getErrSteps() {
		return errSteps;
	}

	public void setErrSteps(int errSteps) {
		this.errSteps = errSteps;
	}

	public int getNonSteps() {
		return nonSteps;
	}

	public void setNonSteps(int nonSteps) {
		this.nonSteps = nonSteps;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public int getManulSteps() {
		return manulSteps;
	}

	public void setManulSteps(int manulSteps) {
		this.manulSteps = manulSteps;
	}
	public int getSucSteps() {
		return sucSteps;
	}

	public void setSucSteps(int sucSteps) {
		this.sucSteps = sucSteps;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
