/*SIR　Mark		author 		time 			fix problem
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

		/* 连接服务器，更新文件 */
		//Client client = new Client(Client.SERVER_CONF_FILE);
		//client.start();

		/* 启动AIR主窗体 */
		// ApplEngine.getinstance();
		// 检查当前环境配置是否正确
		EnvDetect detect = new EnvDetect();

		detect.DetectEnv();

		detect.ClearOutput();

		// 读取配置文件
		//ViewScreen screen = new ViewScreen();
		// 启动主程序
		ViewController.getInstance();
	}



}
