package sse.ngts.testrobot.app.execute.process;

import sse.ngts.testrobot.app.model.ScriptCase;

public interface IExecuteProcess
{

    public void setActioncontroller(ActionController actioncontroller);

    /**
     * 判断步骤的执行类别，调用相应的接口实现执行过程，并根据返回值设置步骤详情数组
     * @param step
     *        －－步骤详情数组
     * @return boolean －－步骤执行是否成功
     */
    public void stepProcess(ScriptCase step);

}
