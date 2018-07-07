package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

import sse.ngts.testrobot.common.ConstValues;

public class AirConfigHnd
{

    private Logger logger = Logger.getLogger(AirConfigHnd.class);

    private  Hashtable<String, String> caseType = new Hashtable<String, String>();

    public static final String cfgFileName = ConstValues.Air_ConfigFileName;

    private static Properties props;

    private static AirConfigHnd instance;

    public static AirConfigHnd getInstance()
    {
        if (instance == null)
        {
            instance = new AirConfigHnd();
        }
        return instance;
    }

    public AirConfigHnd()
    {
        ReadAirCfg();
    }








    /**
     * 将 /替换为//
     * @param str
     * @return
     */
    private String chgStr(String str)
    {
        StringBuilder str2 = new StringBuilder();
        if (str == null)
            return str;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (c == File.separatorChar)
            {
                str2.append(File.separator + File.separator);

            } else
                str2.append(c);
        }
        return str2.toString();

    }

    public Hashtable<String, String> getCaseType()
    {
        return caseType;
    }


    /**
     * 提供转码功能
     * @param key
     * @return
     */
    public String getProperty(String key)
    {
        String str = null;
        try
        {
            str = new String(props.getProperty(key).getBytes("ISO_8859_1"), "GBK");

        } catch (UnsupportedEncodingException e)
        {
            logger.error("不支持的编码转换" + key + ", Error:" + e.getMessage());
            e.printStackTrace();

        } catch (Exception e)
        {
            logger.error("未找到Key:" + key + ", Error:" + e.getMessage());
        }

        return str;

    }



    /**
     * 读取配置
     */
    public void loadCfgFile()
    {
        File file = new File(cfgFileName);
        if (file.exists())
        {
            FileInputStream in = null;
            try
            {
                in = new FileInputStream(file);
                props.load(in);
            } catch (FileNotFoundException ex)
            {
                logger.error("Not found " + cfgFileName + ", Error:" + ex.getMessage());
                ex.printStackTrace();
            } catch (IOException ex)
            {
                logger.error("IO Exception" + cfgFileName + ", Error:" + ex.getMessage());
                ex.printStackTrace();
            }

        }

    }

    /**
     * 修改配置
     * @param key
     * @param value
     */
    public void modify(String key, String value)
    {
        try
        {
            props.setProperty(key, new String(value.getBytes("GBK"), "ISO_8859_1"));

        } catch (UnsupportedEncodingException e)
        {
            logger.error("不支持的编码转换" + key + "," + value + ", Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean ReadAirCfg()
    {
        boolean bFlg = true;
        props = new Properties();

        logger.info("读取配置文件" + cfgFileName);

        loadCfgFile();

        Enumeration<Object> keyEnum = props.keys();
        while (keyEnum.hasMoreElements())
        {
            String str = (String) keyEnum.nextElement();
            if (str.indexOf(ConstValues.CASETYPE_KEY) != -1)
            {
                caseType.put(str.substring(10), getProperty(str));
            }
        }

        return bFlg;
    }

    /**
     * 保存配置
     */
    public void saveCfgFile()
    {

        try
        {
            File file = new File(cfgFileName);
            if (file.exists())
            {
                file.delete();
            }
            file.createNewFile();
            PrintWriter outP = new PrintWriter(new FileWriter(cfgFileName));
            Enumeration<Object> keyEnum = props.keys();
            while (keyEnum.hasMoreElements())
            {

                String str = (String) keyEnum.nextElement();
                String str1 = new String(getProperty(str));
                outP.println(str + "=" + chgStr(str1));

            }
            outP.close();
        } catch (FileNotFoundException ex)
        {
            logger.error("Not found " + cfgFileName + ", Error:" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex)
        {
            logger.error("IO Exception" + cfgFileName + ", Error:" + ex.getMessage());
            ex.printStackTrace();
        }

    }
}
