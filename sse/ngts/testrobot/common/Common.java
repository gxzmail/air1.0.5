package sse.ngts.testrobot.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Common
{
    private static Logger logger = Logger.getLogger(Common.class);

    /**
     * 排序测试用例
     * @param in
     * @return
     */
    public static ArrayList<String> CaseListSort(HashSet<String> in)
    {

        ArrayList<String> d = new ArrayList<String>();
        Iterator<String> c = in.iterator();
        String str = null;
        while (c.hasNext())
        {
            str = c.next();
            if (str.isEmpty())
                continue;
            d.add(str);
        }

        Collections.sort(d, new Comparator<String>()
                {
            @Override
            public int compare(String obj1, String obj2)
            {

                if (obj1.compareToIgnoreCase(obj2) >= 1)
                    return 1;
                else
                    return 0;

            }
                });

        return d;
    }

    /***** 创建目录 ***********/
    public static void createDir(String path, int type)
    {
        if (path == null)
            return;
        Object[] dir = dircToStringArray(path);
        int len = 0;
        if (type == ConstValues.APPL_File)
            len = dir.length - 1;
        else if (type == ConstValues.APPL_DIRC)
            len = dir.length;
        String dirc = null;
        for (int i = 0; i < len; i++)
        {
            if (dirc != null)
            {
                dirc = dirc + File.separator + (String) dir[i];
            } else
                dirc = (String) dir[i];
            File fileDirc = new File(dirc);
            if (!fileDirc.exists() || !fileDirc.isDirectory())
            {
                logger.info("文件夹不存在:" + fileDirc.getAbsolutePath());
                fileDirc.mkdir();
                logger.info("新建文件夹:" + fileDirc.getAbsolutePath());

            }
        }
    }

    public static Object[] dircToStringArray(String path)
    {
        ArrayList<String> str = new ArrayList<String>();
        StringTokenizer t = new StringTokenizer(path, "\\");
        while (t.hasMoreTokens())
        {
            str.add(t.nextToken());

        }
        return str.toArray();

    }

    /***
     * 函数功能：把数组输出到指定的文件中 函数输入：
     * @param in
     *        －－要输出的数组 函数输出：
     * @param filePath
     *        －－输出文件路径
     * @throws IOException
     */
    public static void fileOutPut(HashSet<String> in, String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (!file.exists())
                file.createNewFile();
            PrintWriter out = new PrintWriter(new FileWriter(filePath));

            String outPutString = null;
            int i = 0;
            /* 排序 */
            ArrayList<String> d = new ArrayList<String>();
            d = CaseListSort(in);

            Iterator<String> iter = d.iterator();
            while (iter.hasNext())
            {

                outPutString = iter.next();
                out.println(String.valueOf(i++) + "|" + outPutString);
            }
            out.close();

            logger.info("写文件成功:" + filePath);

        } catch (IOException exception)
        {
            exception.printStackTrace();
            logger.info("写文件失败:" + filePath);

        }
    }

    public static String getFileName(String path)
    {
        Object[] fileArray = dircToStringArray(path);
        int len = fileArray.length;
        if (len == 0)
            return null;
        return (String) fileArray[len - 1];
    }

    /***
     * 函数说明：line字符串用token作为分隔符，取其中的第n个字段 输入参数： int n －－ 需要取的字段的位置 String
     * token－－分隔符 String line －－ 字段所在的字符串 返 回 值： 字符串
     */
    public static String getStringByToken(int n, String token, String line)
    {
        String Id = null;
        StringTokenizer t = new StringTokenizer(line, token);
        if (n > t.countTokens())
            return null;
        while (n > 1)
        {
            t.nextToken();
            n--;

        }
        Id = t.nextToken();
        return Id;
    }
}
