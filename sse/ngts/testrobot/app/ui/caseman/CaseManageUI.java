/**
 * 
 */
package sse.ngts.testrobot.app.ui.caseman;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author showmsg
 */

public class CaseManageUI extends JFrame
{

    class ButtonActionListener implements ActionListener
    {
        CheckNode root;
        JTextArea textArea;
        ArrayList<String> selectCase = new ArrayList<String>();
        
        ButtonActionListener(final CheckNode root, final JTextArea textArea)
        {
            this.root = root;
            this.textArea = textArea;
        }

        @Override
		public void actionPerformed(ActionEvent e)
        {
            Enumeration en = root.breadthFirstEnumeration();
            textArea.setText("");
            String excutecase = "";
            while (en.hasMoreElements())
            {
                CheckNode node = (CheckNode) en.nextElement();
               
                if (node.isSelected())
                {
                	excutecase = "";
                    TreeNode[] nodes = node.getPath();
                    textArea.append(nodes[0].toString());
                    for (int i = 1; i < nodes.length; i++)
                    {
                        textArea.append("/" + nodes[i].toString());
                        if(node.isLeaf())
                        {
                        	excutecase = nodes[i].toString();
                        }
                     
                    }
                    textArea.append("\n");
                    if(excutecase.length() != 0)
                    {
                    	selectCase.add(excutecase);
                    }
                }
            }
            SaveCaseMan("text.txt");
        }
        
        private void SaveCaseMan(String filename)
        {
        	FileWriter writer = null;
        	File f = new File(filename);
        	try {
				 writer = new FileWriter(f);
				for(String cases: selectCase)
	        	{
	        		writer.append(cases + "\n");
	        	}
				
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        	finally
        	{
        		try {
					writer.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
        	}
        	
        }
    }
    class NodeSelectionListener extends MouseAdapter
    {
        JTree tree;

        NodeSelectionListener(JTree tree)
        {
            this.tree = tree;
        }

        @Override
		public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            int row = tree.getRowForLocation(x, y);
            TreePath path = tree.getPathForRow(row);
            // TreePath path = tree.getSelectionPath();
            if(row == 0)
            {
                return ;
            }
            if (path != null)
            {
                CheckNode node = (CheckNode) path.getLastPathComponent();
                boolean isSelected = !(node.isSelected());
                node.setSelected(isSelected);
                if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION)
                {
                    if (isSelected)
                    {
                        tree.expandPath(path);
                    } else
                    {
                        tree.collapsePath(path);
                    }
                }
                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                // I need revalidate if node is root. but why?
                if (row == 0)
                {
                    tree.revalidate();
                    tree.repaint();
                }
            }
        }
    }
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (ClassNotFoundException e1)
        {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        } catch (InstantiationException e1)
        {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        } catch (IllegalAccessException e1)
        {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1)
        {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        CaseManageUI frame = new CaseManageUI();
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
			public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

    }
    
    private CheckNode[] nodes = null;

    private JTextArea printarea = null;

    private List<PathNode> treelist = null;

    public CaseManageUI()
    {
        super("用例管理");
      
        printarea = new JTextArea();

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
		
        panel.add(createTree(), BorderLayout.CENTER);
        panel.add(createBtn(), BorderLayout.EAST);

        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // splitpane.setOrientation(0);
        splitpane.setDividerLocation(450);

        splitpane.setLeftComponent(panel);

        splitpane.setRightComponent(createPrint());

        getContentPane().add(splitpane, BorderLayout.CENTER);
        
        this.setLocation(100, 100);
        this.setSize(800, 600);
        this.setVisible(true);

    }

    private JPanel createBtn()
    {
        JButton button = new JButton("print");
        button.addActionListener(new ButtonActionListener(nodes[0], printarea));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane createPrint()
    {
        JScrollPane textPanel = new JScrollPane(printarea);
        return textPanel;
    }

    private JScrollPane createTree()
    {
        PathNode root = new PathNode();
        root.setId(0);
        root.setParent(0);
        root.setPathname("TestCases");
        root.setFilename("TestCases");
        root.setRoot(true);
        
        treelist = TreeHndl.getPathTree(root);

        nodes = new CheckNode[treelist.size()];

        for (int i = 0; i < treelist.size(); i++)
        {
            nodes[i] = new CheckNode(treelist.get(i).getFilename());

        }

        for (int i = 0; i < treelist.size(); i++)
        {
            int parent = treelist.get(i).getParent();
            int id = treelist.get(i).getId();
            // System.out.println("parent:" + parent + "|" + treelist.get(parent).getPathname() + ", child:" + id + "|"
            // + treelist.get(id).getPathname());
            if (parent != id)
            {
                nodes[parent].add(nodes[id]);
            } else
            {
                // System.out.println(i);
            }
        }

        JTree tree = new JTree(nodes[0]);
        tree.expandRow(0);
        tree.setScrollsOnExpand(true);
        tree.setToolTipText("执行用例筛选");
       
      
        tree.setCellRenderer(new CheckRenderer());
        tree.setExpandsSelectedPaths(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.addMouseListener(new NodeSelectionListener(tree));
        JScrollPane sp = new JScrollPane();    
        sp.setViewportView(tree);
        return sp;
    }
}
