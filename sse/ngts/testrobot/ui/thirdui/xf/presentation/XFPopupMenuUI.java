package sse.ngts.testrobot.ui.thirdui.xf.presentation;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/*******************************************************************************
 * MODULE NAME : IPOWindowController.java
 * DESCRIPTIVE NAME : EZPopupMenuUI
 * MODULE DESCRIPTION :
 * MODULE INTERFACES :
 * CALLED MODULES :
 * CALLED BY :
 * FUNCTIONAL DESCRIPTION:
 * =======================
 * ABSTRACT:
 * --------
 *************************************************************************** MODIFICATION HISTORY:
 * Date Prog.
 * DD-MMM-YYYY Init. SIR Modification Description
 * ------------- ------- ----- --------------------
 * 11-JUN-2008 YIWEI 30322 CREATE
 ******************************************************************************/
public class XFPopupMenuUI extends BasicPopupMenuUI
{

    public static ComponentUI createUI(JComponent c)
    {
        return new XFPopupMenuUI();
    }

    @Override
    public Popup getPopup(JPopupMenu popup, int x, int y)
    {
        Popup pp = super.getPopup(popup, x, y);
        JPanel panel = (JPanel) popup.getParent();
        panel.setBorder(new XFShadowBorderUI(2, 2));

        panel.setOpaque(true);
        return pp;

    }
}
