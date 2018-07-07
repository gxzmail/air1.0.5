package sse.ngts.testrobot.engine;

import java.util.LinkedList;

public class ApplEvtQueue extends Thread
{
    public static ApplEvtQueue getInstance()
    {
        if (instance == null)
        {
            instance = new ApplEvtQueue();
        }
        return instance;
    }

    private LinkedList mQueue;

    private boolean mGoOn = true;

    private static ApplEvtQueue instance = null;

    private ApplEvtQueue()
    {
        mQueue = new LinkedList();
        this.setDaemon(false);
        this.setName("AppEventQueue");
        this.start();
    }

    public void post(ApplEvt evt)
    {
        synchronized (mQueue)
        {
            mQueue.addLast(evt);
            mQueue.notifyAll();
        }
    }

    /**
     * ???
     */
    @Override
    public void run()
    {
        while (mGoOn)
        {
            while (mQueue.size() > 0)
            {
                ApplEvt evt;

                synchronized (mQueue)
                {
                    evt = (ApplEvt) mQueue.removeFirst();
                }

                // System.out.println(evt.getHandleMethod());
                evt.dispatch();

            }
            try
            {
                synchronized (mQueue)
                {
                    if (mQueue.size() == 0)
                    {
                        mQueue.wait();
                    }
                }
            } catch (InterruptedException e)
            {

            }
        }
    }

    /**
     * ???
     */
    public void terminate()
    {
        synchronized (mQueue)
        {
            mGoOn = false;
            mQueue.notifyAll();
        }
    }

}
