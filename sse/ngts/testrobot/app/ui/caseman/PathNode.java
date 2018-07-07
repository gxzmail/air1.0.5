/**
 * 项目名称：Demo_AutoExecute2
 * 类名称：PathTree
 * 类描述：
 * 创建人：neusoft
 * 修改人：neusoft
 * 修改备注：
 * @version
 */
package sse.ngts.testrobot.app.ui.caseman;

/**
 * @author neusoft
 */
public class PathNode
{
    /**
     * 是否为目录
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
