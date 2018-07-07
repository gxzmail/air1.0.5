package sse.ngts.testrobot.app.model;

public class FrameworkCase implements Cloneable
{

    private String scriptId = "";
    private String descrip = "";
    private String testContent = "";
    private String testDate = "";
    private String testPhase ="";
    private String testAntic ="";
    private String testHost ="";
    private String testBatch="";
    private String testStatus="";
    private String testPrior="";
    private String testMemo="";
    private String stepsId="";
    private String caseDetials="";
    
    /* 运行时间 */
    private String runTime =  "";
    
    /* 开始结束时间 */
    private String gapTime = "";
    
    /* 标记 */
    private String mark = "";
    
    /* 日志 */
    private String log = "";
   
    
    private final String title[] = { "步骤编号", "脚本编号", "脚本描述", "脚本执行内容", "交易日", "执行阶段", "预期结果", "主机", "批处理", "错误状态",
            "优先级", "备注" };

    @Override
    public FrameworkCase clone()
    {
        try
        {
            FrameworkCase cloned = (FrameworkCase) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e)
        {
            return null;
        }

    }

    public String getCaseDetials()
    {
        return caseDetials;
    }

    public String getDescrip()
    {
        return descrip;
    }

    public String getScriptId()
    {
        return scriptId;
    }

    public String getStepsId()
    {
        return stepsId;
    }

    public String getTestAntic()
    {
        return testAntic;
    }

    public String getTestBatch()
    {
        return testBatch;
    }

    public String getTestContent()
    {
        return testContent;
    }

    public String getTestDate()
    {
        return testDate;
    }

    public String getTestHost()
    {
        return testHost;
    }

    public String getTestMemo()
    {
        return testMemo;
    }

    public String getTestPhase()
    {
        return testPhase;
    }

    public String getTestPrior()
    {
        return testPrior;
    }

    public String getTestStatus()
    {
        return testStatus;
    }

    public String[] getTitle()
    {
        return title;
    }

    public void setCaseDetials(String caseDetials)
    {
        this.caseDetials = caseDetials;
    }

    public void setDescrip(String descrip)
    {
        this.descrip = descrip;
    }

    public void setScriptId(String scriptId)
    {
        this.scriptId = scriptId;
    }

    public void setStepsId(String stepsId)
    {
        this.stepsId = stepsId;
    }

    public void setTestAntic(String testAntic)
    {
        this.testAntic = testAntic;
    }

    public void setTestBatch(String testBatch)
    {
        this.testBatch = testBatch;
    }

    public void setTestContent(String testContent)
    {
        this.testContent = testContent;
    }

    public void setTestDate(String testDate)
    {
        this.testDate = testDate;
    }

    public void setTestHost(String testHost)
    {
        this.testHost = testHost;
    }

    public void setTestMemo(String testMemo)
    {
        this.testMemo = testMemo;
    }

    public void setTestPhase(String testPhase)
    {
        this.testPhase = testPhase;
    }

    public void setTestPrior(String testPrior)
    {
        this.testPrior = testPrior;
    }

    public void setTestStatus(String testStatus)
    {
        this.testStatus = testStatus;
    }

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getGapTime() {
		return gapTime;
	}

	public void setGapTime(String gapTime) {
		this.gapTime = gapTime;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
    
    
    
}
