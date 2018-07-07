/*
 * 
 */
package sse.ngts.testrobot.app.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.db.model.Scence;
import sse.db.model.Script;
import sse.ngts.testrobot.app.ui.caseman.PathNode;
import sse.ngts.testrobot.common.ConstValues;

// TODO: Auto-generated Javadoc
/**
 * The Class CaseManage.
 */
public class CaseManage {

	/** The logger. */
	private static Logger logger = Logger.getLogger(ConstValues.logName);

	/**
	 * Gets the all case scences.
	 *
	 * @param scencespath the scencespath
	 * @return the all case scences
	 */
	public static ArrayList<Scence> getAllCaseScences(
			ArrayList<String> scencespath) {
		ArrayList<Scence> obj = new ArrayList<Scence>();
		File f = null;
		for (String path : scencespath) {
			f = new File(path);
			if (!f.exists() || !f.isDirectory()) {
				continue;
			}
			File[] scencefile = f.listFiles();

			for (File scence : scencefile) {
				if (!scence.exists() || scence.isDirectory()) {
					continue;
				}
				// System.out.println("################   " + scence.getName() +
				// "   ##############");
				ArrayList<Object> scencelist = CaseSheetHndl.ReadCaseSheet(
						scence.getPath(), "测试用例", Scence.ExcelTitle, 3,
						Scence.class);
				for (Object o : scencelist) {
					obj.add((Scence) o);
				}
			}

		}

		return obj;

	}

	/**
	 * Gets the all excute cases.
	 *
	 * @param scences the scences
	 * @return the all excute cases
	 */
	public static ArrayList<Object> getAllExcuteCases(
			ArrayList<Scence> scences) {
		ArrayList<Object> obj = new ArrayList<Object>();

		for (Scence model : scences) {
			if (model.getCaseId().length() > 4) {
				String scriptpath = model.getScencePath() + File.separator
						+ "脚本" + File.separator
						+ model.getCaseId().substring(0, 4) + File.separator
						+ "NGTS_AM_AIR_" + model.getCaseId() + "_CV01.xls";
				File f = new File(scriptpath);
				
				logger.log(Level.INFO, "执行用例:" + f.getPath());
				
				if(!f.exists()) continue;
//				System.out.println("############################");
				ArrayList<Object> o = CaseSheetHndl.ReadCaseSheet(scriptpath,
						"测试脚本", Script.ExcelTitle, 3, Script.class);
				for(Object os:o)
				{
					obj.add(os);
				}
			}
		}

		return obj;
	}
	
	public static ArrayList<PathNode> getAllScenceID(String casepath)
	{
		ArrayList<PathNode> pathnodes = new ArrayList<PathNode>();
		
		File fheadnode = new File(casepath);
		if(!fheadnode.exists() || !fheadnode.isDirectory())
		{
			logger.log(Level.SEVERE, "所给用例目录不正确");
			return pathnodes;
		}
        
        PathNode node = new PathNode();
        node.setId(0);
        node.setRoot(true);
        node.setParent(0);
        node.setFilename(casepath);
        node.setPathname(casepath);
        pathnodes.add(node);
        int icount = 1;
        
        for(File file:fheadnode.listFiles())
        {
        	if(!file.isDirectory())
        	{
        		continue;
        	}
        	node = new PathNode();
        	node.setId(icount);
            node.setRoot(true);
            node.setParent(0);
            node.setFilename(file.getName());
            node.setPathname(file.getName());
            pathnodes.add(node);       
            
            int classifyid = icount;
            icount++;
            
            
            
            for(File sf:file.listFiles())
            {
            	if(sf.isDirectory())
            	{
            		continue;
            	}
            	node = new PathNode();
            	node.setId(icount);
                node.setRoot(true);
                node.setParent(classifyid);
                node.setFilename(sf.getName());
                node.setPathname(sf.getName());
                pathnodes.add(node);   
                System.out.println(sf.getPath());
                ArrayList<Object> scencelist =  CaseSheetHndl.ReadCaseSheet(
						sf.getPath(), "测试用例", Scence.ExcelTitle, 3,
						Scence.class);
                
                
                int scenceparentid = icount;
                icount++;
                
                for(Object o:scencelist)
                {
                	Scence model = (Scence)o;
                	if(model.getCaseId().length() == 0)
                	{
                		continue;
                	}
                	node = new PathNode();
                	node.setId(icount);
                    node.setRoot(false);
                    node.setParent(scenceparentid);
                    node.setFilename(model.getCaseId());
                    node.setPathname(model.getCaseId());
                    pathnodes.add(node); 
                    icount++;
                }
            }
            
            
        	
        }
        return pathnodes;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		ArrayList<String> a = new ArrayList<String>();
		a.add("TestCases\\综业平台BATCH(BT)");
		a.add("TestCases\\接口文件功能测试(IF)");
		a.add("TestCases\\撮合交易和非交易(RT)");
		ArrayList<Scence> aa = CaseManage.getAllCaseScences(a);

		CaseManage.getAllExcuteCases(aa);
		/*
		 * for(ScenceModel mode:aa) { System.out.println(mode.getCaseId()); }
		 */
	}

}
