package sse.ngts.testrobot.factory;

import sse.ngts.testrobot.app.execute.autoexecute.AutoExecuteImpl;
import sse.ngts.testrobot.app.execute.autoexecute.IAutoExecute;
import sse.ngts.testrobot.app.execute.process.ExecuteProcessImpl;
import sse.ngts.testrobot.app.execute.process.IExecuteProcess;

public class EXECUFactory
{

    public static IAutoExecute getApplAutoExecuteInstance()
    {
        return new AutoExecuteImpl();
    }

    public static IExecuteProcess getApplExecuteProcess()
    {
        return new ExecuteProcessImpl();
    }

}
