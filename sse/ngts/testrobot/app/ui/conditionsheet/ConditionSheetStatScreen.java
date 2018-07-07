package sse.ngts.testrobot.app.ui.conditionsheet;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConditionSheetStatScreen extends JFrame
{
    private JTextArea pane;
    public ConditionSheetStatScreen(ConditionSheetController c)
    {
        setTitle("技术统计报表");
        JPanel centerPanel = new JPanel(new BorderLayout());
        pane = new JTextArea();
        JScrollPane scroll = new JScrollPane(pane);
        centerPanel.add(scroll, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel);
        setVisible(true);
        setSize(800, 800);
        fmtTxt1();
        fmtTxt2();

    }

    private void fmtTxt1()
    {/*
     * int pbuSize = 0;
     * int accSize = 0;
     * 
     * StringBuffer text = new StringBuffer("本表单当前视图下 PBU,帐户,产品 统计情况\n\n");
     * 
     * text.append("|PBU/帐户|\n");
     * HashMap pbuacc = controller.getPBUAccRela();
     * 
     * Set a = pbuacc.entrySet();
     * Iterator b = a.iterator();
     * pbuSize = a.size();
     * 
     * while (b.hasNext())
     * {
     * Map.Entry c = (Entry) b.next();
     * String pbu = (String) c.getKey();
     * HashMap list = (HashMap) c.getValue();
     * 
     * text.append("|"+pbu+"   ");
     * Set e = list.entrySet();
     * Iterator f = e.iterator();
     * accSize+=e.size();
     * ArrayList<String> accs = new ArrayList<String>();
     * while (f.hasNext())
     * {
     * Map.Entry g = (Entry) f.next();
     * String acc = (String) g.getKey();
     * accs.add(acc);
     * }
     * 
     * Collections.sort(accs);
     * 
     * StringBuffer tempLine = new StringBuffer(
     * "----------------------------------------------------------------------------------------------------------------------------------------------"
     * );
     * for( String acc : accs )
     * {
     * text.append("| " +acc);
     * }
     * 
     * 
     * text.append("\n");
     * text.append(tempLine+"\n");
     * }
     * text.append("共使用PBU:" + pbuSize + "个,帐户" + accSize + "个");
     * pane.setText(text.toString());
     */
    }

    private void fmtTxt2()
    {/*
     * int instSize = 0;
     * int accSize = 0;
     * 
     * StringBuffer text = new StringBuffer("\n\n");
     * 
     * text.append("|产品/帐户|\n");
     * HashMap instacc = controller.getInstAccRela();
     * 
     * Set a = instacc.entrySet();
     * Iterator b = a.iterator();
     * instSize = a.size();
     * 
     * while (b.hasNext())
     * {
     * Map.Entry c = (Entry) b.next();
     * String pbu = (String) c.getKey();
     * HashMap list = (HashMap) c.getValue();
     * 
     * text.append("|"+pbu+"   ");
     * Set e = list.entrySet();
     * Iterator f = e.iterator();
     * accSize+=e.size();
     * ArrayList<String> accs = new ArrayList<String>();
     * while (f.hasNext())
     * {
     * Map.Entry g = (Entry) f.next();
     * String acc = (String) g.getKey();
     * accs.add(acc);
     * }
     * 
     * Collections.sort(accs);
     * 
     * StringBuffer tempLine = new StringBuffer(
     * "----------------------------------------------------------------------------------------------------------------------------------------------"
     * );
     * for( String acc : accs )
     * {
     * text.append("| " +acc);
     * }
     * 
     * 
     * text.append("\n");
     * text.append(tempLine+"\n");
     * }
     * text.append("共使用产品:" + instSize + "个");
     * pane.append(text.toString());
     */
    }

}
