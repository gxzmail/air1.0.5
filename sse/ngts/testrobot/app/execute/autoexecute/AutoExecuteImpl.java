/*
 *  Created on: 2017-5-26
 *      Author: xzguo
 *  Time		SIR MAKR    		DESCRIPTION
 * 2017-05-26	sir 1		modify the air turns to be red although the  expect is the same with response
 *
 */



package sse.ngts.testrobot.app.execute.autoexecute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sse.ngts.testrobot.app.execute.process.ExecuteProcessImpl;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.common.StreamGobbler;

public class AutoExecuteImpl implements IAutoExecute {
	private static ArrayList<String> changstr(String str) {
		ArrayList<String> strarray = new ArrayList<String>();
		StringTokenizer t = new StringTokenizer(str);
		int count = 0;
		String[] arr = new String[t.countTokens()];
		while (t.hasMoreTokens()) {
			arr[count++] = t.nextToken();

		}
		int i = 0, j = 0;
		boolean iflag = false, jflag = false;
		for (count = 0; count < arr.length; count++) {
			if (arr[count].indexOf("\"") == 0 && !iflag) {
				i = str.indexOf("\"", j);
				if (i + 1 < str.length())
					j = i + 1;
				iflag = true;
				if (arr[count].length() > 1) {
					int k = str.indexOf("\"", j);
					if (k - i + 1 == arr[count].length()) {
						jflag = true;
						j = k;
					}
				}
			} else if ((arr[count].indexOf("\"") == arr[count].length() - 1)
					&& iflag) {
				j = str.indexOf("\"", j);
				jflag = true;
			}

			if (!iflag) {
				strarray.add(arr[count]);
				System.out.println(arr[count]);
			}
			if (iflag && jflag) {
				strarray.add(str.substring(i, j + 1));
				System.out.println(str.substring(i, j + 1));
				iflag = jflag = false;
				if (j + 1 < str.length())
					j = j + 1;
			}
		}
		return strarray;
	}

	private Logger logger = Logger.getLogger(AutoExecuteImpl.class);
	private ExecuteProcessImpl executeProcess;
	private String failedCause = new String();

	/**
	 * 自动执行步骤
	 * 
	 * @param step
	 *            －－步骤详情
	 * @return ：boolean--执行是否成功
	 */
	@Override
	public boolean autoExec(ScriptCase step) {
		String stepContent = step.getFrmCase().getTestContent();
		boolean flag = execueteCommand(stepContent);
		int RNum = 1;
		int LNum = 1;
		if (flag) {
			step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
			step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
			step.setAttribute(ScriptCase.ATTR_AUTOFIN_FLAG, true);

			// 结果写入日志
			step.getTestResult().add(getFailedCause());
			logger.info("步骤执行成功，步骤ID:{0} " + step.getFrmCase().getStepsId());

		} else if (!flag) {

			if (step.getStepType() == 2) {
				// 结果写入日志
				logger.warn("步骤{0}执行失败,错误状态为”Y“,继续执行!"
						+ step.getFrmCase().getStepsId());
				step.getTestResult().add(getFailedCause());
				step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
				step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, false);
			} else {

				if (step.getStepType() == 1) {
					logger.error("步骤{0}执行失败,错误状态为”N“,进入手动执行!"
							+ step.getFrmCase().getStepsId());
					step.getTestResult().add(getFailedCause());

					return false;
				} else if (step.getStepType() == 6) {
					while (!flag) {
						logger.error("步骤{0}执行失败,错误状态为”R“"
								+ step.getFrmCase().getStepsId());
						step.getTestResult().add(
								"第" + RNum + "次" + getFailedCause());
						if (isRunStop()) {
							return false;
						}
						Pause(1.0);
						flag = execueteCommand(stepContent);
						RNum++;
					}
					logger.info("步骤执行成功，步骤ID:{0} "
							+ step.getFrmCase().getStepsId());
					step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
					step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
					step.getTestResult().add(
							"第" + RNum + "次" + getFailedCause());

				} else if (step.getStepType() == 3) {
					while (!flag) {
						logger.error("步骤{0}执行失败,错误状态为”L“"
								+ step.getFrmCase().getStepsId());
						step.getTestResult().add(
								"第" + LNum + "次" + getFailedCause());
						Pause(1.0);
						flag = execueteCommand(stepContent);
						if (++LNum == 4) {
							step.getTestResult().add(
									"第" + LNum + "次" + getFailedCause());
							return false;
						}
					}
					logger.info("步骤执行成功，步骤ID:{0} "
							+ step.getFrmCase().getStepsId());
					step.setAttribute(ScriptCase.ATTR_FINISH_FLAG, true);
					step.setAttribute(ScriptCase.ATTR_SUCCESS_FLAG, true);
					step.getTestResult().add(
							"第" + LNum + "次" + getFailedCause());

				} else {
					// 写入日志
					logger.info("执行失败,无法判断原因 ," + "步骤ID:{0} "
							+ step.getFrmCase().getStepsId());

				}

			}
		}
		return true;
	}

	/**
	 * 调用command执行步骤
	 * 
	 * @param stepExecute
	 *            －－要执行的内容
	 */
	@Override
	public boolean execueteCommand(String stepExecute) {
		failedCause = "";
		Object[] cmdarray = changstr(stepExecute).toArray();
		String[] strarr = new String[cmdarray.length];
		boolean stepSuccess = false;
		for (int i = 0; i < strarr.length; i++) {
			strarr[i] = (String) cmdarray[i];

		}
		try {
			Process pro = Runtime.getRuntime().exec(strarr);
			StreamGobbler outputGobbler = new StreamGobbler(
					pro.getInputStream(), "OUTPUT");
			outputGobbler.start();
			int exitVal = pro.waitFor();
/*sir 1 begin*/
			outputGobbler.join();
/*sir 1 end*/
			int count = 3;
			while (exitVal == 13 && count > 0) {
				pro = Runtime.getRuntime().exec(strarr);
				outputGobbler = new StreamGobbler(pro.getInputStream(),
						"OUTPUT");
				outputGobbler.start();
				exitVal = pro.waitFor();
				count--;
			}
			stepSuccess = outputGobbler.getRunflag();
			failedCause = outputGobbler.getStr().toString();
			pro.destroy();
		} catch (IOException ex) {
			logger.warn("command执行失败，原因{0}" + ex.getMessage());
			return stepSuccess;
		} catch (InterruptedException e) {
			logger.warn("command执行失败，原因{0}" + e.getMessage());
			return stepSuccess;
		}
		return stepSuccess;
	}

	private String getFailedCause() {
		return failedCause;
	}

	private boolean isRunStop() {
		return executeProcess.isStoprun();
	}

	private void Pause(double time) {
		try {
			Thread.sleep((int) (1000 * time));
		} catch (InterruptedException ex) {
			logger.error("线程执行失败:" + ex.getMessage());
		}
	}

	@Override
	public void setExecuteProcess(ExecuteProcessImpl executeProcess) {
		this.executeProcess = executeProcess;
	}

}
