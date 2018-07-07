package sse.ngts.testrobot.script.load;

import java.io.File;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.AirConfigHnd;

public class ScriptManage
{
    // private Logger logger = Logger.getLogger(getClass());

    private Hashtable<String, String> scripthash = new Hashtable<String, String>();

    private static final String COMMON_SCRIPT_PATH = "common_tools";

    private static final String PRIVATE_SCRIPT_PATH = "private_tools";

    private static String project = "";

    private void LoadCommonScript()
    {
        File f = new File(COMMON_SCRIPT_PATH);
        if (!f.exists())
        {
            // logger.error("����AIR����Ŀ¼ʧ��");
            System.exit(-1);
        }

        String[] filenames = f.list();
        String path = f.getPath() + File.separator + File.separator;
        for (String str : filenames)
        {
            scripthash.put(str.toUpperCase(), path + str.toUpperCase());
            // System.out.println("key:" + str + ", Value:" + path + str);
        }

    }

    private void LoadCurScript()
    {
        File f = new File(System.getProperty("user.dir"));

        String[] filenames = f.list();

        for (String str : filenames)
        {
            if (str.toUpperCase().endsWith(".PL") || str.toUpperCase().endsWith(".bat"))
            {
                scripthash.put(str.toUpperCase(), str.toUpperCase());
                // System.out.println("key:" + str + ", Value:" + str);
            }
        }

    }

    private void LoadPrjScript(String project)
    {
        File f = new File(PRIVATE_SCRIPT_PATH + File.separator + project);

        if (f.exists())
        {
            String path = PRIVATE_SCRIPT_PATH + File.separator + File.separator + project + File.separator
                    + File.separator;
            String[] filenames = f.list();
            for (String str : filenames)
            {

                if (scripthash.containsKey(str))
                {
                    // logger.info("ʹ��˽�й���" + str);
                }
                scripthash.put(str.toUpperCase(), path + str.toUpperCase());
                // System.out.println("key:" + str + ", Value:" + path + str);

            }

        }

    }

    public Hashtable<String, String> LoadScrpt()
    {
        project = AirConfigHnd.getInstance().getProperty(ConstValues.TestProject_Key);

        if (project == null)
        {
            JOptionPane.showMessageDialog(null,
                    "�������Ѹ��£���������[air_config.txt]�������appl.TestProject=����ϵͳ����[ATP,BTP,MTP,DTP,ITP],�����ִ��", "����",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        LoadCurScript();

        LoadCommonScript();

        LoadPrjScript(project);

        return scripthash;

    }

}
