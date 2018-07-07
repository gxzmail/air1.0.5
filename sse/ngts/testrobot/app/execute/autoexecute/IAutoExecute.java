package sse.ngts.testrobot.app.execute.autoexecute;

import sse.ngts.testrobot.app.execute.process.ExecuteProcessImpl;
import sse.ngts.testrobot.app.model.ScriptCase;

public interface IAutoExecute
{

    /**
     * �Զ�ִ�в���
     * @param step
     *        ������������
     * @return ��boolean--ִ���Ƿ�ɹ�
     */
    public boolean autoExec(ScriptCase step);

    public boolean execueteCommand(String stepExecute);

    public void setExecuteProcess(ExecuteProcessImpl executeProcess);
}
