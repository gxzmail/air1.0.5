package sse.ngts.testrobot.app.execute.autoexecute;

import sse.ngts.testrobot.app.execute.process.ExecuteProcessImpl;
import sse.ngts.testrobot.app.model.ScriptCase;

public interface IAutoExecute
{

    /**
     * 自动执行步骤
     * @param step
     *        －－步骤详情
     * @return ：boolean--执行是否成功
     */
    public boolean autoExec(ScriptCase step);

    public boolean execueteCommand(String stepExecute);

    public void setExecuteProcess(ExecuteProcessImpl executeProcess);
}
