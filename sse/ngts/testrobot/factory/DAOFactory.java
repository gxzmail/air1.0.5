package sse.ngts.testrobot.factory;

import sse.ngts.testrobot.app.func.casedetails.CaseScriptImpl;
import sse.ngts.testrobot.app.func.casedetails.CaseStepsScriptImpl;
import sse.ngts.testrobot.app.func.casedetails.ICaseScript;
import sse.ngts.testrobot.app.func.casedetails.ICaseStepsScript;

public class DAOFactory
{

    public static ICaseScript getApplCaseScriptInstance()
    {
        return new CaseScriptImpl();
    }

    public static ICaseStepsScript getApplCaseStepsInstance()
    {
        return new CaseStepsScriptImpl();
    }

}