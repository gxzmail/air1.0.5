package sse.ngts.testrobot.app.func.casedetails;

import java.util.ArrayList;
import java.util.Hashtable;

import sse.ngts.testrobot.app.model.CaseManage;
import sse.ngts.testrobot.app.model.FrameworkCase;

public interface ICaseStepsScript
{

    public ArrayList<FrameworkCase> getCaseSteps();

    public Hashtable<String, Integer> getTradePhase();

    /**
     * �������ܣ���������ID����ȡ�����ű����ڵ��ļ���
     * @param caseId
     * @param pathCfg
     * @return
     */
    public Boolean readCaseSheet(ArrayList<CaseManage> caseList);

}
