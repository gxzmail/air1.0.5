package sse.ngts.testrobot.engine;

import java.lang.reflect.Method;

public class ApplEvt
{

    public static final String EVT_ID_OPEN_SCRIPT = "OpenScript";

    public static final String EVT_ID_START_SCRIPT = "StartScript";

    public static final String EVT_ID_CONTINUE_SCRIPT = "ContinueScript";

    public static final String EVT_ID_PAUSE_SCRIPT = "PauseScript";

    public static final String EVT_ID_OPEN_ACTION = "OpenAction";
    public static final String EVT_ID_STOP_ACTION = "StopAction";
    public static final String EVT_ID_LOAD_ACTION = "LoadScript";

    public static final String EVT_ID_ACTIVE_NOTIFY = "ActiveNotify";

    public static final String EVT_ID_STOP_NOTIFY = "StopNotify";
    public static final String EVT_ID_WARN_NOTIFY = "WarnNotify";

    public static final String EVT_ID_PAUSE_ACTION = "PauseAction";

    public static final String EVT_ID_SCROLL_ACTION = "TableScroll";

    public static final String EVT_ID_APPENDTXT_ACTION = "AppendTxt";

    public static final String EVT_ID_CHANGEPHASE_ACTION = "ChangPhase";
    public static final String EVT_ID_SETSTOPID_ACTION = "StopId";
    public static final String EVT_ID_CHANGE_STOPRUNSTEP = "ChangeStopRunStep";
    private String handleMethod;
    private Object args;
    private ISender sender;
    private IReceiver receiver;

    public ApplEvt(String handleMethod)
    {
        this.handleMethod = handleMethod;
    }

    public ApplEvt(String handleMethod, Object args)
    {
        this.handleMethod = handleMethod;
        this.args = args;
    }

    public void dispatch()
    {
        String methodName = "handle" + handleMethod;

        try
        {
            Method t = receiver.getClass().getMethod(methodName, ApplEvt.class);
            t.invoke(receiver, this);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public Object getArgs()
    {
        return args;
    }

    public String getHandleMethod()
    {
        return handleMethod;
    }

    public IReceiver getReceiver()
    {
        return receiver;
    }

    public ISender getSender()
    {
        return sender;
    }

    public void setReceiver(IReceiver receiver)
    {
        this.receiver = receiver;
    }

    public void setSender(ISender sender)
    {
        this.sender = sender;
    }

}
