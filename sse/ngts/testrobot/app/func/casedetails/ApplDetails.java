package sse.ngts.testrobot.app.func.casedetails;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import sse.ngts.testrobot.app.model.CaseManage;

public class ApplDetails
{

    private CaseManage caseDescript;
    private String sceneType;
    private String sceneId;
    public CaseManage getcaseDescript()
    {
        return caseDescript;
    }

    private void setCaseId(String scene)
    {
        caseDescript.setCaseId(scene + "_" + this.sceneType + "_" + this.sceneId);
    }

    /************************************
     * �������ܣ�����ApplCase�������ֶε�ֵ
     * �������룺
     * @param values
     *        ����ֵ
     * @param scene
     *        �����������ڵĳ������
     *        ��������ֵ��
     *        ��
     *************************************/
    public void setDetails(Hashtable values, String scene)
    {
        Set a = values.entrySet();
        Iterator b = a.iterator();
        caseDescript = new CaseManage();
        // ���ýű���ǰ�ֶε�ֵ
        while (b.hasNext())
        {
            Entry c = (Entry) b.next();
            String key = (String) c.getKey();
            String value = (String) c.getValue();
            setValue(key, value.trim());
        }
        setCaseId(scene);

    }

    public void setValue(String key, String value)
    {
        if (key.equals("�������"))
        {
            this.sceneType = strChg(value, 3);
            this.caseDescript.setSceneType(sceneType);
        } else if (key.equals("����ID"))
        {
            this.sceneId = strChg(value, 3);
            this.caseDescript.setSceneId(sceneId);
        } else if (key.equals("��������"))
        {
            this.caseDescript.setCaseDetails(value);
        } else if (key.equals("���Ի���"))
        {
            this.caseDescript.setTestEnvr(value);
        } else if (key.equals("����"))
        {
            this.caseDescript.setTesthost(value);
        } else if (key.equals("��Ӧ�����ĵ�"))
        {
            this.caseDescript.setTestNecessDoc(value);
        } else if (key.equals("���ȼ�"))
        {
            this.caseDescript.setTestPrior(value);
        } else if (key.equals("����/�쳣"))
        {
            this.caseDescript.setTestResult(value);
        }
    }

    private String strChg(String c, int charLength)
    {

        Float caseDescript = Float.valueOf(c);
        String a = String.valueOf(caseDescript.intValue());
        while (a.length() < charLength)
            a = "0" + a;
        return a;
    }

}
