/*SIR��Mark		author 		time 			fix problem
 *   SIR 1			xzguo						fix concurrency problem
 *   SIR 2			xzguo						add fresh button
 *   SIR 3          xzguo						fix load test case sheet out of order
 *
 *
 *
 *
 * *
 */

package testrobotexcute;


import org.apache.log4j.Logger;

import sse.ngts.air.ctrl.client.Client;
import sse.ngts.air.ctrl.common.LoadLog4j;
import sse.ngts.testrobot.app.ui.ViewController;
import sse.ngts.testrobot.engine.app.EnvDetect;

public class TestRobotExecute {

	public static void main(String[] args) {

		LoadLog4j log = new LoadLog4j();
		log.init();

		/* ���ӷ������������ļ� */
		//Client client = new Client(Client.SERVER_CONF_FILE);
		//client.start();

		/* ����AIR������ */
		// ApplEngine.getinstance();
		// ��鵱ǰ���������Ƿ���ȷ
		EnvDetect detect = new EnvDetect();

		detect.DetectEnv();

		detect.ClearOutput();

		// ��ȡ�����ļ�
		//ViewScreen screen = new ViewScreen();
		// ����������
		ViewController.getInstance();
	}



}
