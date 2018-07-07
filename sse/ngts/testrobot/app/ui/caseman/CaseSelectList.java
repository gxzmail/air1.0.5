package sse.ngts.testrobot.app.ui.caseman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import sse.ngts.testrobot.app.excel.CaseManage;
import sse.ngts.testrobot.common.ConstValues;
import sse.ngts.testrobot.engine.app.PropConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class CaseSelectList.
 * 
 * @author showmsg
 */

public class CaseSelectList extends JFrame {

	/**
	 * The listener interface for receiving nodeSelection events. The class that
	 * is interested in processing a nodeSelection event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addNodeSelectionListener<code> method. When
	 * the nodeSelection event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see NodeSelectionEvent
	 */
	class NodeSelectionListener extends MouseAdapter {

		/** The tree. */
		JTree tree;

		/**
		 * Instantiates a new node selection listener.
		 * 
		 * @param tree
		 *            the tree
		 */
		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		/*
		 * （非 Javadoc）
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);
			TreePath path = tree.getPathForRow(row);
			// TreePath path = tree.getSelectionPath();
			/*
			 * if (row == 0) { return; }
			 */
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (path != null) {
					CheckNode node = (CheckNode) path.getLastPathComponent();
					boolean isSelected = !(node.isSelected());
					node.setSelected(isSelected);
					Enumeration en = node.depthFirstEnumeration();
					while (en.hasMoreElements()) {
						CheckNode nodechild = (CheckNode) en.nextElement();
						if (nodechild.getParent() == node) {
							nodechild.setSelected(isSelected);
							((DefaultTreeModel) tree.getModel())
									.nodeChanged(nodechild);
						}
					}

					if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
						if (isSelected) {
							tree.expandPath(path);
						} else {
							if (row != 0)
								tree.collapsePath(path);
						}
					}

					((DefaultTreeModel) tree.getModel()).nodeChanged(node);
					// I need revalidate if node is root. but why?
					if (row == 0) {
						tree.revalidate();
						tree.repaint();
					}
				}
			}
		}
	}

	/**
	 * The listener interface for receiving buttonAction events. The class that
	 * is interested in processing a buttonAction event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addButtonActionListener<code> method. When
	 * the buttonAction event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see ButtonActionEvent
	 */
	class SaveCaseListener implements ActionListener {

		/** The root. */
		CheckNode root;

		/** The text area. */
		JTextArea textArea;

		/** The select case. */
		ArrayList<String> selectCase;

		String version;

		/**
		 * Instantiates a new button action listener.
		 * 
		 * @param root
		 *            the root
		 * @param textArea
		 *            the text area
		 * @param selectCase
		 *            the select case
		 */
		SaveCaseListener(final CheckNode root, final JTextArea textArea,
				final ArrayList<String> selectCase, final String version) {
			this.root = root;
			this.textArea = textArea;
			this.selectCase = selectCase;
			this.version = version;
		}

		/*
		 * （非 Javadoc）
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			if (version.length() == 0) {
				JOptionPane.showMessageDialog(null, "版本不能为空", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Enumeration en = root.breadthFirstEnumeration();
			textArea.setText("");
			String excutecase = "";
			while (en.hasMoreElements()) {
				CheckNode node = (CheckNode) en.nextElement();

				if (node.isSelected()) {
					excutecase = "";
					TreeNode[] nodes = node.getPath();
					// textArea.append(nodes[0].toString());
					for (int i = 1; i < nodes.length; i++) {
						if (node.isLeaf()) {
							// textArea.append(nodes[i].toString());

							excutecase = nodes[i].toString();
						}

					}
					// textArea.append("\n");
					if (excutecase.length() != 0) {

						if (!selectCase.contains(excutecase)) {
							selectCase.add(excutecase);
						}

					}
				} else {
					TreeNode[] nodes = node.getPath();
					for (int i = 1; i < nodes.length; i++) {
						if (node.isLeaf()) {
							// textArea.append(nodes[i].toString());

							excutecase = nodes[i].toString();
						}

					}
					if (excutecase.length() != 0) {
						if (selectCase.contains(excutecase)) {
							selectCase.remove(excutecase);
						}
					}
				}
			}
			for (String str : selectCase) {
				textArea.append(str + "\n");
			}

			SaveCaseMan(ConstValues.VERSION_TEST_PLAN_PATH + File.separator
					+ version + "_cycle.txt");
		}

		/**
		 * Save case man.
		 * 
		 * @param filename
		 *            the filename
		 */
		private void SaveCaseMan(String filename) {
			FileWriter writer = null;
			File f = new File(filename);
			try {
				writer = new FileWriter(f);
				for (String cases : selectCase) {
					writer.append(cases + "\n");
				}

			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}

		}
	}

	class TreePopMenu implements MouseListener {
		private JTree tree;
		private JPopupMenu popmenu;

		public TreePopMenu(JTree tree) {
			this.tree = tree;
			popMenuInit();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mousePressed(MouseEvent e) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path == null) {
				return;
			}

			tree.setSelectionPath(path);
			if (e.getButton() == MouseEvent.BUTTON3) {
				popmenu.show(tree, e.getX(), e.getY());
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		private void popMenuInit() {
			popmenu = new JPopupMenu();
			JMenuItem expandItem = new JMenuItem("全部展开");
			expandItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					tree.expandPath(new TreePath(treenode.getPath()));
					tree.updateUI();
				}

			});
			popmenu.add(expandItem);
		}

	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		CaseSelectList frame = new CaseSelectList();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	/** The nodes. */
	private CheckNode[] nodes = null;

	/** The printarea. */
	private JTextArea printarea = null;

	/** The treelist. */
	private List<PathNode> treelist = null;

	/** The select cases. */
	private ArrayList<String> selectCases = new ArrayList<String>();

	private JTree tree = null;

	private JLabel lversion = null;

	/**
	 * Instantiates a new case select list.
	 */
	public CaseSelectList() {
		super("用例管理");

		printarea = new JTextArea();
		printarea.setEditable(false);

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

		this.setResizable(false);
		this.setLocation(100, 100);
		this.setSize(800, 600);
		this.setVisible(true);

	}

	/**
	 * Creates the btn.
	 * 
	 * @return the j panel
	 */
	private JPanel createBtn() {
		lversion = new JLabel(PropConfig.getTestVersion());
		Font font = new Font("Arial Bold", Font.BOLD, 18);
		lversion.setForeground(Color.RED);
		lversion.setFont(font);

		JButton btnadd = new JButton("添加执行用例");
		btnadd.setToolTipText("支持用例按顺序添加，方法为先添加执行在前用例，再添加执行在后用例");
		btnadd.addActionListener(new SaveCaseListener(nodes[0], printarea,
				selectCases, lversion.getText()));

		JPanel panel = new JPanel(new BorderLayout());

		panel.add(lversion, BorderLayout.NORTH);
		panel.add(btnadd, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Creates the print.
	 * 
	 * @return the j scroll pane
	 */
	private JSplitPane createPrint() {

		JSplitPane scrollpanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		scrollpanel.setDividerLocation(30);

		JPanel panel = new JPanel();
		JLabel lhistory = new JLabel("历史执行用例");
		final JComboBox hisComb = new JComboBox();
		hisComb.addItem("请选择执行用例");
		/* 历史执行用例保存目录 */
		File fhis = new File(ConstValues.VERSION_TEST_PLAN_PATH);
		if (fhis.exists() && fhis.isDirectory()) {
			for (String his : fhis.list()) {
				hisComb.addItem(his);
			}

		}

		JButton btnhistory = new JButton("导入历史");

		btnhistory.addActionListener(new ActionListener() {
			private ArrayList<String> list = null;

			/** The root. */
			CheckNode root = nodes[0];

			@Override
			public void actionPerformed(ActionEvent e) {
				String dir = ConstValues.VERSION_TEST_PLAN_PATH
						+ File.separator + (String) hisComb.getSelectedItem();
				list = LoadSelectCase(dir);
				Enumeration en = root.breadthFirstEnumeration();

				printarea.setText("");
				String excutecase = "";
				while (en.hasMoreElements()) {
					CheckNode node = (CheckNode) en.nextElement();
					
					TreeNode[] nodes = node.getPath();
					for (int i = 1; i < nodes.length; i++) {
						if (list.contains(nodes[i].toString())) {
							printarea.append(nodes[i].toString() + "\n");
							node.setSelected(true);
							
							((DefaultTreeModel) tree.getModel())
									.nodeChanged(node);

							 
						}
					}
				}
				((DefaultTreeModel) tree.getModel()).nodeChanged(root);
				tree.revalidate();
				tree.repaint();
				tree.updateUI();

				JOptionPane.showMessageDialog(null, "导入成功", "信息",
						JOptionPane.INFORMATION_MESSAGE);
			}

			private ArrayList<String> LoadSelectCase(String path) {
				BufferedReader reader = null;
				list = new ArrayList<String>();
				String line = "";
				File f = new File(path);
				if (!f.exists() || !f.isFile()) {
					return list;
				}

				try {
					reader = new BufferedReader(new FileReader(f));

					while ((line = reader.readLine()) != null) {
						if (line.length() > 0) {
							list.add(line);
						}
					}

				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

				return list;

			}

		});

		panel.add(lhistory);
		panel.add(hisComb);
		panel.add(btnhistory);

		JScrollPane textPanel = new JScrollPane(printarea);

		scrollpanel.setTopComponent(panel);
		scrollpanel.setBottomComponent(textPanel);

		return scrollpanel;
	}

	/**
	 * Creates the tree.
	 * 
	 * @return the j scroll pane
	 */
	private JScrollPane createTree() {

		ArrayList<PathNode> pathnode = CaseManage.getAllScenceID(PropConfig
				.getCasePath());

		nodes = new CheckNode[pathnode.size()];

		for (int i = 0; i < pathnode.size(); i++) {
			nodes[i] = new CheckNode(pathnode.get(i).getFilename());

		}

		for (int i = 0; i < pathnode.size(); i++) {
			int parent = pathnode.get(i).getParent();
			int id = pathnode.get(i).getId();
			// System.out.println("parent:" + parent + "|" +
			// treelist.get(parent).getPathname() + ", child:" + id + "|"
			// + treelist.get(id).getPathname());
			if (parent != id) {
				nodes[parent].add(nodes[id]);
			} else {
				// System.out.println(i);
			}
		}

		tree = new JTree(nodes[0]);
		tree.expandRow(0);
		tree.setScrollsOnExpand(true);
		tree.setToolTipText("执行用例筛选");

		tree.setCellRenderer(new CheckRenderer());
		tree.setExpandsSelectedPaths(true);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		tree.addMouseListener(new NodeSelectionListener(tree));
		tree.addMouseListener(new TreePopMenu(tree));
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(tree);
		return sp;
	}
}
