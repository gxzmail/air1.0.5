/**
 * ��Ŀ���ƣ�Demo_AutoExecute2
 * �����ƣ�PathTree
 * ��������
 * �����ˣ�neusoft
 * �޸��ˣ�neusoft
 * �޸ı�ע��
 * @version
 */
package sse.ngts.testrobot.app.ui.caseman;

/**
 * @author neusoft
 */
public class PathNode
{
    /**
     * �Ƿ�ΪĿ¼
     */
    private boolean isRoot = false;

    private int parent = 0;
    private int id = 0;
    private String pathname = "";
    private String filename = "";
    
    public String getFilename()
    {
        return filename;
    }
    public int getId()
    {
        return id;
    }
    public int getParent()
    {
        return parent;
    }
    public String getPathname()
    {
        return pathname;
    }
    public boolean isRoot()
    {
        return isRoot;
    }
    public void setFilename(String m_filename)
    {
        filename = m_filename;
    }
    public void setId(int m_id)
    {
        id = m_id;
    }
    public void setParent(int m_parent)
    {
        parent = m_parent;
    }
    public void setPathname(String m_pathname)
    {
        pathname = m_pathname;
    }
    public void setRoot(boolean m_isRoot)
    {
        isRoot = m_isRoot;
    }

}
