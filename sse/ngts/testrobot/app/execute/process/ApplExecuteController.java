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
     * ����˵���� �Զ�ִ�е�ǰ�����е����нű�,������ű�ִ�к��״̬
     * ������: neusoft
     * ��������: 2013-9-12 ����8:07:21
     * ����˵����
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
     * ����˵����ִ��ѡ�õĲ���
     * ������: neusoft
     * ��������: 2013-9-12 ����8:08:31
     * ����˵����
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
            exeTxt = "����" + script.getFrmCase().getStepsId() + "�Ѿ�ִ�����!";
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            return;
        }

        handleStep(currentAc);
    }

    /**
     * ***********************************************************
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:26
     * ����˵����
     * @return
     *         ArrayList<ActionController>
     */
    public ArrayList<ActionController> getActionsController()
    {
        return actionsController;
    }

    /**
     * ***********************************************************
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:30
     * ����˵����
     * @return
     *         ArrayList<String>
     */
    public ArrayList<String> getTradePhase()
    {
        return tradePhase;
    }

    /**
     * ����˵���� ��ȡApplFrmwkCase�����еĶ���,������һ��ActionControllerȥ��װ node :
     * �ļ��ӵ�eedLoadActions �Ƿ���Ҫ���������Ľű�
     * ������: neusoft
     * ��������: 2013-9-12 ����8:05:55
     * ����˵����
     * @return
     *         boolean
     */
    private boolean handleLoadSheet()
    {
        actionsController.clear();
        // ��ȡ�ֲ�
        if (!readScriptSheet())
            return false;
        // ���½����ϵĽ���ʱ��
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:35
     * ����˵����
     * @param evt
     *        void
     */
    public void handlePauseScript(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        isSuspend = (Boolean) c[0];

    }

    /**
     * ����˵����ִ�е�������
     * ������: neusoft
     * ��������: 2013-9-12 ����8:06:32
     * ����˵����
     * @param c
     * @return
     *         boolean
     */
    private boolean handleStep(ActionController c)
    {
        ScriptCase script = c.getCurrentScript();
        if (!script.getAttribute(ScriptCase.ATTR_FINISH_FLAG))
        {

            exeTxt = "��ʼִ�в���" + script.getFrmCase().getStepsId() + ",�ű�IDΪ��" + script.getFrmCase().getScriptId();
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            c.handleStartScript(null);
            while (c.isCurrentControllerWorking())
            {
                Pause(2.0);
            }
            if (!script.getAttribute(ScriptCase.ATTR_FINISH_FLAG))
            {
                exeTxt = "��Ҫ�ֶ����!";
                sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
                ApplExectueStat.getInstance().execResultWrite(PropConfig.getExecuteLogFile(), c);
                return false;
            }
            if (script.getAttribute(ScriptCase.ATTR_SUCCESS_FLAG))
            {
                exeTxt = "����" + script.getFrmCase().getStepsId() + ":ִ�гɹ�";
            } else
            {
                exeTxt = "����" + script.getFrmCase().getStepsId() + ":ִ��ʧ��";
                exeTxt = "����ԭ��" + script.getTestResult().toString();
            }
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            ApplExectueStat.getInstance().execResultWrite(PropConfig.getExecuteLogFile(), c);
        }
        return true;
    }

    /**
     * ***********************************************************
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:41
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:45
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:50
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:35:55
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-12 ����8:05:32
     * ����˵����
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

            /* ����־��λ������ */
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
     * ����˵�������¿�ʼִ�У������в���Ϊδִ��״̬
     * ������: neusoft
     * ��������: 2013-9-12 ����8:08:03
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:36:02
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-12 ����8:07:46
     * ����˵����
     * @param currentStep
     *        void
     */
    public void setCurrentStep(int currentStep)
    {
        this.currentStep = currentStep;
    }

    /**
     * ***********************************************************
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-13 ����1:36:09
     * ����˵����
     * @param steps
     *        void
     */
    private void setTradePhase(ArrayList<FrameworkCase> steps)
    {
        Iterator<FrameworkCase> c = steps.iterator();
        tradePhase.add("ѡ��ʱ��");
        while (c.hasNext())
        {
            String phase = c.next().getTestPhase();
            if (tradePhase.contains(phase))
                continue;
            tradePhase.add(phase);
        }

    }

    /**
     * ����˵���� ����ִ��
     * ������: neusoft
     * ��������: 2013-9-12 ����8:08:15
     * ����˵����
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
     * ����˵���� TODO
     * ������: neusoft
     * ��������: 2013-9-12 ����8:09:04
     * ����˵����
     * @param fileName
     *        void
     */
    public void testResultFileCreate(String fileName)
    {
        ApplExectueStat executeStat = ApplExectueStat.getInstance();
        executeStat.statProcess(PropConfig.getExecuteFile(), fileName, actionsController);
    }

}
