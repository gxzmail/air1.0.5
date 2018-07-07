package sse.ngts.testrobot.exception;


import org.apache.log4j.Logger;

public class ApplRunFailException extends Error
{
    private Logger logger = Logger.getLogger(ApplRunFailException.class);

    private String act;
    private String msg;

    /**
     * NGTSTestRunFailException
     * @param nGTSTestRunFailException
     *        NGTSTestRunFailException
     */
    public ApplRunFailException(ApplRunFailException nGTSTestRunFailException)
    {
        this.act = nGTSTestRunFailException.getAct();
        this.msg = nGTSTestRunFailException.getMsg();
    }

    public ApplRunFailException(String act, String msg)
    {
        super(msg);
        this.act = act;
        this.msg = msg;
    }

    public String getAct()
    {
        return act;
    }

    public String getMsg()
    {
        return msg;
    }

}
