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
     * 函数功能：设置ApplCase中所有字段的值
     * 函数输入：
     * @param values
     *        －－值
     * @param scene
     *        －－用例所在的场景编号
     *        函数返回值：
     *        空
     *************************************/
    public void setDetails(Hashtable values, String scene)
    {
        Set a = values.entrySet();
        Iterator b = a.iterator();
        caseDescript = new CaseManage();
        // 设置脚本当前字段的值
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
        if (key.equals("场景类别"))
        {
            this.sceneType = strChg(value, 3);
            this.caseDescript.setSceneType(sceneType);
        } else if (key.equals("场景ID"))
        {
            this.sceneId = strChg(value, 3);
            this.caseDescript.setSceneId(sceneId);
        } else if (key.equals("用例描述"))
        {
            this.caseDescript.setCaseDetails(value);
        } else if (key.equals("测试环境"))
        {
            this.caseDescript.setTestEnvr(value);
        } else if (key.equals("主机"))
        {
            this.caseDescript.setTesthost(value);
        } else if (key.equals("对应需求文档"))
        {
            this.caseDescript.setTestNecessDoc(value);
        } else if (key.equals("优先级"))
        {
            this.caseDescript.setTestPrior(value);
        } else if (key.equals("正常/异常"))
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
