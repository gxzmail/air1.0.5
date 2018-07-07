/**
 * 
 */
package sse.ngts.testrobot.app.ui.caseman;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author showmsg
 * 
 */
public class CheckNode extends DefaultMutableTreeNode {
	public final static int SINGLE_SELECTION = 0;
	public final static int DIG_IN_SELECTION = 4;
	protected int selectionMode;
	protected boolean isSelected;

	public CheckNode() {
		this(null);
	}

	public CheckNode(Object userObject) {
		this(userObject, true, false);
	}

	public CheckNode(Object userObject, boolean allowsChildren,
			boolean isSelected) {
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		setSelectionMode(DIG_IN_SELECTION);
	}

	public int getSelectionMode() {
		return selectionMode;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
	    this.isSelected = isSelected;
	    
	    if ((selectionMode == DIG_IN_SELECTION)
	        && (children != null)) {
	      Enumeration e = children.elements();      
	      while (e.hasMoreElements()) {
	        CheckNode node = (CheckNode)e.nextElement();
	        node.setSelected(isSelected);
	      }
	    }
	  }

	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	// If you want to change "isSelected" by CellEditor,
	/*
	 * public void setUserObject(Object obj) { if (obj instanceof Boolean) {
	 * setSelected(((Boolean)obj).booleanValue()); } else {
	 * super.setUserObject(obj); } }
	 */
}
