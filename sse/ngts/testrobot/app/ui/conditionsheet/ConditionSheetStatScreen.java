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
        setTitle("����ͳ�Ʊ���");
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
     * StringBuffer text = new StringBuffer("������ǰ��ͼ�� PBU,�ʻ�,��Ʒ ͳ�����\n\n");
     * 
     * text.append("|PBU/�ʻ�|\n");
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
     * text.append("��ʹ��PBU:" + pbuSize + "��,�ʻ�" + accSize + "��");
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
     * text.append("|��Ʒ/�ʻ�|\n");
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
     * text.append("��ʹ�ò�Ʒ:" + instSize + "��");
     * pane.append(text.toString());
     */
    }

}
