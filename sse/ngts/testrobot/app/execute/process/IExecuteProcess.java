package sse.ngts.testrobot.app.execute.process;

import sse.ngts.testrobot.app.model.ScriptCase;

public interface IExecuteProcess
{

    public void setActioncontroller(ActionController actioncontroller);

    /**
     * �жϲ����ִ����𣬵�����Ӧ�Ľӿ�ʵ��ִ�й��̣������ݷ���ֵ���ò�����������
     * @param step
     *        ����������������
     * @return boolean ��������ִ���Ƿ�ɹ�
     */
    public void stepProcess(ScriptCase step);

}
