/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
package core.guilog;  
  
import java.io.IOException;  
import java.util.Scanner;  
import javax.swing.JScrollPane;  
import javax.swing.JTextArea;  
  
/** 
 *  
 * �������� 
 * ����ϵ�ɨ�������� 
 * ��ɨ�赽���ַ�����ʾ��JTextArea�� 
 * @author ��ʤ�� 
 * @date 2011-12-20 ���� 
 * @version 1.0 
 */  
public class TextAreaLogAppender extends LogAppender {  
  
    private JTextArea textArea;  
    private JScrollPane scroll;  
  
    /** 
     * Ĭ�ϵĹ��� 
     * @param textArea ��¼�����ƣ��ü�¼���������־��Ϣ������ȡ�������ָ����JTextArea��� 
     * @param scroll JTextArea���ʹ�õĹ�����壬��Ϊ��JTextArea�������־ʱ��Ĭ�ϻ�ʹ��ֱ�������Զ����¹�����������Ҫ�˹��ܣ��˲�����ʡ�� 
     * @throws IOException  
     */  
    public TextAreaLogAppender(JTextArea textArea, JScrollPane scroll) throws IOException {  
        super("textArea");  
        this.textArea = textArea;  
        this.scroll = scroll;  
    }  
  
    @Override  
    public void run() {  
        // ����ϵ�ɨ��������  
        Scanner scanner = new Scanner(reader);  
        // ��ɨ�赽���ַ��������ָ����JTextArea���  
        while (scanner.hasNextLine()) {  
            try {  
                //˯��  
                Thread.sleep(100);  
                String line = scanner.nextLine();  
                textArea.append(line);  
                textArea.append("\n");  
                line = null;  
                //ʹ��ֱ�������Զ����¹���  
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());  
                            } catch (Exception ex) {  
                //�쳣��������  
            }  
        }  
    }  
}  
