/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
package core.guilog;  
  
import java.io.IOException;  
import java.util.Scanner;  
import javax.swing.JLabel;  
	  
	/** 
	 *  
	 * �������� 
	 * ����ϵ�ɨ�������� 
	 * ��ɨ�赽���ַ�����ʾ��JLabel�� 
	 * @author ��ʤ�� 
	 * @date 2011-12-20 ���� 
	 * @version 1.0 
	 */  
	public class LabelLogAppender extends LogAppender {  
	  
	    private JLabel label;  
	  
	    /** 
	     * Ĭ�ϵĹ��� 
	     * @param label ��¼�����ƣ��ü�¼���������־��Ϣ������ȡ�������ָ����JLabel��� 
	     * @throws IOException  
	     */  
	    public LabelLogAppender(JLabel label) throws IOException {  
	        super("label");  
	        this.label = label;  
	    }  
	  
	    @Override  
	    public void run() {  
	        // ����ϵ�ɨ��������  
	        Scanner scanner = new Scanner(reader);  
	        // ��ɨ�赽���ַ�����ʾ��ָ����JLabel��  
	        while (scanner.hasNextLine()) {  
	            try {  
	                //˯��  
	                Thread.sleep(100);  
	                String line = scanner.nextLine();  
	                label.setText(line);  
	                line = null;  
	                 } catch (Exception ex) {  
	                //�쳣��Ϣ��������  
	            }  
	        }  
	    }  
	} 
