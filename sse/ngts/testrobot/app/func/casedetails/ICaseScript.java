package sse.ngts.testrobot.app.func.casedetails;

import java.util.ArrayList;
import java.util.HashSet;

import sse.ngts.testrobot.app.model.CaseManage;

public interface ICaseScript
{

    /*
     * �������ܣ�
     * ���ݳ����б�����ȡ�����ļ����ѳ����ļ��������������б��д��ڵ���������ϸ��Ϣȡ�������ŵ�������
     * �������룺
     * String pathDirc ���������ļ����·��
     * Hashtable pathCfg ����������Ӧ��Ŀ¼
     * HashSet<String>caseList ���������б�
     * HashSet<String>sceneList ���������б�
     * ��������ֵ��
     * ��
     */

    public ArrayList<CaseManage> getCaseScript();

    /*
     * �������ܣ��������е�����������ĵ���
     * �������룺
     * ArrayList<ApplCase> in ����Ҫ���������
     * �����
     * String filePath ��������ļ�·��
     * ��������ֵ��
     * ��
     */
    public void outPutCaseDetails(ArrayList<CaseManage> in, String filePath);

    public Boolean readCaseDetails(String pathDirc, HashSet<String> caseList,
            HashSet<String> sceneList);
}
