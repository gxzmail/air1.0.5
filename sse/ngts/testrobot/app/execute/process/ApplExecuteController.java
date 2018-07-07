package sse.ngts.testrobot.app.execute.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.func.casedetails.ExcelHndl;
import sse.ngts.testrobot.app.model.FrameworkCase;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.IReceiver;
import sse.ngts.testrobot.engine.ISender;
import sse.ngts.testrobot.engine.app.PropConfig;
import sse.ngts.testrobot.exception.ApplFatalException;

public class ApplExecuteController implements ISender, IReceiver
{
    private Logger logger = Logger.getLogger(ApplExecuteController.class);
    private ConditionSheetController sheetController;
    private String exeTxt;
    protected ArrayList<ActionController> actionsController;
    private ArrayList<String> tradePhase;
    // private String fileName;
    private int currentStep = 0;
    private int stopRunStep = -1;
    private boolean isSuspend = false;
    private boolean isStop = false;
    private ArrayList<FrameworkCase> framCase;

    public ApplExecuteController(ConditionSheetController sheetController)
    {
        actionsController = new ArrayList<ActionController>();
        tradePhase = new ArrayList<String>();
        this.sheetController = sheetController;
        if (!handleLoadSheet())
            throw new ApplFatalException(null, null);
    }

    /**
     * @param sheetController
     * @param fileName
     */
    public ApplExecuteController(ConditionSheetController sheetController, String fileName)
    {
        actionsController = new ArrayList<ActionController>();
        tradePhase = new ArrayList<String>();
        this.sheetController = sheetController;
        fileName = sheetController.getSheetName();
        if (!handleLoadSheet())
            throw new ApplFatalException(null, null);
    }

    /**
     * 函数说明： 自动执行当前用例中的所有脚本,并保存脚本执行后的状态
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:07:21
     * 参数说明：
     * void
     */
    public void execActions()
    {

        if (currentStep == -1 || currentStep > actionsController.size() - 1)
            return;
        for (int i = currentStep; i < actionsController.size(); i++)
        {
            ActionController c = actionsController.get(i);

            if (isSuspend || isStop)
            {

                return;
            }

            ScriptCase script = c.getCurrentScript();

            if (script.getAttribute(ScriptCase.ATTR_FINISH_FLAG) && script.getAttribute(ScriptCase.ATTR_REFF_FLAG))
            {
                if (currentStep == actionsController.size() - 1)
                    break;
                else
                    continue;
            }

            currentStep = i;
            if (!handleStep(c))
                return;
            if (stopRunStep == currentStep)
            {
                stopRunStep = -1;
                sendNotifyEvent(ApplEvt.EVT_ID_CHANGE_STOPRUNSTEP);
                return;
            }

        }
    }

    /**
     * 函数说明：执行选好的步骤
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:08:31
     * 参数说明：
     * @param stepId
     *        void
     */
    public void executSelectStep(int stepId)
    {
        if (stepId == -1 || stepId > actionsController.size() - 1)
            return;
        ActionController currentAc = actionsController.get(stepId);
        ScriptCase script = currentAc.getCurrentScript();

        if (script.getAttribute(ScriptCase.ATTR_FINISH_FLAG) && script.getAttribute(ScriptCase.ATTR_REFF_FLAG))
        {
            exeTxt = "步骤" + script.getFrmCase().getStepsId() + "已经执行完成!";
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            return;
        }

        handleStep(currentAc);
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:26
     * 参数说明：
     * @return
     *         ArrayList<ActionController>
     */
    public ArrayList<ActionController> getActionsController()
    {
        return actionsController;
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:30
     * 参数说明：
     * @return
     *         ArrayList<String>
     */
    public ArrayList<String> getTradePhase()
    {
        return tradePhase;
    }

    /**
     * 函数说明： 读取ApplFrmwkCase数组中的对象,并对用一个ActionController去封装 node :
     * 文件接点eedLoadActions 是否需要加载用例的脚本
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:05:55
     * 参数说明：
     * @return
     *         boolean
     */
    private boolean handleLoadSheet()
    {
        actionsController.clear();
        // 读取手册
        if (!readScriptSheet())
            return false;
        // 更新界面上的交易时段
        setTradePhase(framCase);
        for (int i = 0; i < framCase.size(); i++)
        {
            ScriptCase script = new ScriptCase(framCase.get(i));

            ActionController actionController = new ActionController(sheetController);

            actionController.initScript(script);
            actionController.setViewRow(i + 1);
            actionsController.add(actionController);
        }
 
        return true;
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:35
     * 参数说明：
     * @param evt
     *        void
     */
    public void handlePauseScript(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        isSuspend = (Boolean) c[0];

    }

    /**
     * 函数说明：执行单个步骤
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:06:32
     * 参数说明：
     * @param c
     * @return
     *         boolean
     */
    private boolean handleStep(ActionController c)
    {
        ScriptCase script = c.getCurrentScript();
        if (!script.getAttribute(ScriptCase.ATTR_FINISH_FLAG))
        {

            exeTxt = "开始执行步骤" + script.getFrmCase().getStepsId() + ",脚本ID为：" + script.getFrmCase().getScriptId();
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            c.handleStartScript(null);
            while (c.isCurrentControllerWorking())
            {
                Pause(2.0);
            }
            if (!script.getAttribute(ScriptCase.ATTR_FINISH_FLAG))
            {
                exeTxt = "需要手动完成!";
                sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
                ApplExectueStat.getInstance().execResultWrite(PropConfig.getExecuteLogFile(), c);
                return false;
            }
            if (script.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG))
            {
                exeTxt = "步骤" + script.getFrmCase().getStepsId() + ":执行成功";
            } else
            {
                exeTxt = "步骤" + script.getFrmCase().getStepsId() + ":执行失败";
                exeTxt = "错误原因：" + script.getTestResult().toString();
            }
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            ApplExectueStat.getInstance().execResultWrite(PropConfig.getExecuteLogFile(), c);
        }
        return true;
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:41
     * 参数说明：
     * @param evt
     *        void
     */
    public void handleStopAction(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        isStop = (Boolean) c[1];

    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:45
     * 参数说明：
     * @param evt
     *        void
     */
    public void handleStopId(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        stopRunStep = (Integer) c[2];

    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:50
     * 参数说明：
     * @param time
     *        void
     */
    private void Pause(double time)
    {
        try
        {
            Thread.sleep((int) (1000 * time));
        } catch (InterruptedException ex)
        {
        }
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:35:55
     * 参数说明：
     * @param evt
     *        void
     */
    public void postEvt(ApplEvt evt)
    {
        evt.setSender(this);
        if (evt.getReceiver() == null)
        {
            evt.setReceiver(this);
        }
        ApplEvtQueue.getInstance().post(evt);
    }

    /**
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:05:32
     * 参数说明：
     * @return
     *         boolean
     */
    private boolean readScriptSheet()
    {

        ExcelHndl genscript = ExcelHndl.getInstance();

        if (!genscript.hasCreat())
        {
            //WindowHandler windowHandler = new WindowHandler();
           // windowHandler.setLevel(Level.ALL);

            /* 把日志定位到窗体 */
           // logger.addHandler(windowHandler);
            framCase = genscript.GetExcuteCase();
            if (framCase == null || framCase.size() == 0 || !genscript.hasCreat())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 函数说明：重新开始执行，置所有步骤为未执行状态
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:08:03
     * 参数说明：
     * void
     */
    public void reStartExec()
    {
        for (int i = 0; i < actionsController.size(); i++)
        {
            ScriptCase script = actionsController.get(i).getCurrentScript();
            script.setAttribute(ScriptCase.ATTR_REFF_FLAG, false);
            script.setAttribute(ScriptCase.ATTR_FINISH_FLAG, false);
            script.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, false);
            script.getTestResult().clear();
            script.setReuslt();
            script.setFaileflag(false);

        }
        currentStep = 0;
        stopRunStep = -1;
        isSuspend = false;
        isStop = false;

    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:36:02
     * 参数说明：
     * @param messgeID
     *        void
     */
    private void sendNotifyEvent(String messgeID)
    {
        Object params[] = { currentStep, exeTxt, stopRunStep };
        ApplEvt evt1 = new ApplEvt(messgeID, params);
        evt1.setReceiver(sheetController);
        postEvt(evt1);

    }

    /**
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:07:46
     * 参数说明：
     * @param currentStep
     *        void
     */
    public void setCurrentStep(int currentStep)
    {
        this.currentStep = currentStep;
    }

    /**
     * ***********************************************************
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-13 下午1:36:09
     * 参数说明：
     * @param steps
     *        void
     */
    private void setTradePhase(ArrayList<FrameworkCase> steps)
    {
        Iterator<FrameworkCase> c = steps.iterator();
        tradePhase.add("选择时段");
        while (c.hasNext())
        {
            String phase = c.next().getTestPhase();
            if (tradePhase.contains(phase))
                continue;
            tradePhase.add(phase);
        }

    }

    /**
     * 函数说明： 单步执行
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:08:15
     * 参数说明：
     * void
     */
    public void sigleStepProcess()
    {
        if (currentStep < 0 || currentStep > actionsController.size() - 1)
            return;
        ActionController currentAc = actionsController.get(currentStep);
        ScriptCase script = currentAc.getCurrentScript();

        while (script.getAttribute(ScriptCase.ATTR_FINISH_FLAG) && script.getAttribute(ScriptCase.ATTR_REFF_FLAG))
        {
            if (currentStep == actionsController.size() - 1)
            {
                return;
            }
            if (currentStep + 1 < actionsController.size())
            {
                currentStep++;
                currentAc = actionsController.get(currentStep);
                script = currentAc.getCurrentScript();
            }

        }
        handleStep(currentAc);
    }

    /**
     * 函数说明： TODO
     * 创建人: neusoft
     * 创建日期: 2013-9-12 下午8:09:04
     * 参数说明：
     * @param fileName
     *        void
     */
    public void testResultFileCreate(String fileName)
    {
        ApplExectueStat executeStat = ApplExectueStat.getInstance();
        executeStat.statProcess(PropConfig.getExecuteFile(), fileName, actionsController);
    }

}
