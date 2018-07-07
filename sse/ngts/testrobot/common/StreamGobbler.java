package sse.ngts.testrobot.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    StringBuilder str = new StringBuilder();
    Boolean runflag = false;

    public StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public Boolean getRunflag()
    {
        return runflag;
    }

    public StringBuilder getStr()
    {
        return str;
    }

    @Override
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                if (-1 != line.indexOf(ConstValues.executeSuccess))
                {
                    runflag = true;

                }

                str.append(line + "\n");

                System.out.println(type + ">" + line);
            }

        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

}
