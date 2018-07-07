package sse.ngts.testrobot.app.model;

public class CaseManage
{
    private String sheetName = "";
    private String sceneType = "";
    private String sceneId = "";
    private String caseDetails = "";

    private String caseId = "";
    private String testEnvr = "";
    private String testDate = "";
    private String testhost = "";
    private String testPrior = "";
    private String testResult = "";
    private String testNecessDoc = "";

    public String getCaseDetails()
    {
        return caseDetails;
    }

    public String getCaseId()
    {
        return caseId;
    }

    public String getSceneId()
    {
        return sceneId;
    }

    public String getSceneType()
    {
        return sceneType;
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public String getTestDate()
    {
        return testDate;
    }

    public String getTestEnvr()
    {
        return testEnvr;
    }

    public String getTesthost()
    {
        return testhost;
    }

    public String getTestNecessDoc()
    {
        return testNecessDoc;
    }

    public String getTestPrior()
    {
        return testPrior;
    }

    public String getTestResult()
    {
        return testResult;
    }

    public void setCaseDetails(String caseDetails)
    {
        this.caseDetails = caseDetails;
    }

    public void setCaseId(String caseId)
    {
        this.caseId = caseId;
    }

    public void setSceneId(String sceneId)
    {
        this.sceneId = sceneId;
    }

    public void setSceneType(String sceneType)
    {
        this.sceneType = sceneType;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public void setTestDate(String testDate)
    {
        this.testDate = testDate;
    }

    public void setTestEnvr(String testEnvr)
    {
        this.testEnvr = testEnvr;
    }

    public void setTesthost(String testhost)
    {
        this.testhost = testhost;
    }

    public void setTestNecessDoc(String testNecessDoc)
    {
        this.testNecessDoc = testNecessDoc;
    }

    public void setTestPrior(String testPrior)
    {
        this.testPrior = testPrior;
    }

    public void setTestResult(String testResult)
    {
        this.testResult = testResult;
    }

}
