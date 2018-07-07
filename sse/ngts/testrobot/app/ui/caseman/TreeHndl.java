/**
 * 项目名称：Demo_AutoExecute2
 * 类名称：TreeHndl
 * 类描述：
 * 创建人：neusoft
 * 修改人：neusoft
 * 修改备注：
 * @version
 */
package sse.ngts.testrobot.app.ui.caseman;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author neusoft
 */
public class TreeHndl
{
    private static List<PathNode> node = new ArrayList<PathNode>();
    private static int sero = 0;

    private static void getChild(PathNode root)
    {
        ArrayList<PathNode> childnode = new ArrayList<PathNode>();
        
        if(root.getFilename().compareToIgnoreCase(".svn") == 0 || root.getFilename().compareToIgnoreCase(".cvs") == 0)
        {
            return;
        }
        File f = new File(root.getPathname());

        for (File file : f.listFiles())
        {
            if(file.getName().compareToIgnoreCase(".svn") == 0 || file.getName().compareToIgnoreCase(".cvs") == 0)
            {
                continue;
            }
            
            sero++;
            PathNode child = new PathNode();

            child.setId(sero);
            child.setParent(root.getId());
            child.setPathname(f.getPath() + File.separator + file.getName());
            child.setFilename(file.getName());
            if (file.isDirectory())
            {
                child.setRoot(true);
                childnode.add(child);
                node.add(child);
                GetPath(child);
            } else
            {
                child.setRoot(false);
                childnode.add(child);
                node.add(child);
            }
            
        }

    }

    private static void GetPath(PathNode root)
    {

        getChild(root);
    }
    
    public static List<PathNode> getPathTree(PathNode root)
    {
        node.add(root);
        GetPath(root);
        return node;
    }

}
