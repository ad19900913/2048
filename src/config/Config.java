package config;

import java.io.IOException;

import util.FileUtil;

public class Config {
	/**
	 * ���ڿ��
	 */
	public static final int GAME_WIDTH=400;
	/**
	 * ���ڸ߶�
	 */
	public static final int GAME_HEIGHT=400;
	/**
	 * ����Ļ��Ե����ʱ¶��������
	 */
	public static final int SHOW_PIXELS=5;
	/**
	 * ����ʱ��
	 */
	public static final int SLEEP_TIME=5;
	/**
	 * �Զ����м��ʱ��
	 */
	public static final int AUTORUN_SLEEP_TIME=1;
	//������ҳ�水ť��С�ĳ���
	public static final int MENU_GAP=GAME_HEIGHT/9,MENU_WIDTH=GAME_WIDTH/3,MENU_HEIGHT=GAME_HEIGHT/10;
	/**
	 * �Զ�����
	 */
	public static final boolean AUTORUN = true;
	/**
	 * ��ʼ��������ͣ�trueΪȫ�¸�֣�falseΪ�Զ���
	 */
	public static final boolean CASE_NEW = true;
	/**
	 * �Զ��������
	 */
	public static final int DEEP = 8;
	/**
	 *  ���ڿɼ���
	 */
	public static final boolean ALWAYS_ON_TOP = false;
	/**
	 * ������
	 */
	public static int MONOTONICITY = 60;
	/**
	 * ƽ����
	 */
	public static int SMOOTHNESS = 0;
	/**
	 * ������
	 */
	public static int FREETILES = 40;
	/**
	 * �ܷ�
	 */
	public static int TOTAL = 100;
	/**
	 * ���
	 */
	public static int GAP = 5;
	/**
	 * ��־�ļ�
	 */
	public static final String LOG_FILE = "C:/Users/Captain/Desktop/2048.log";
	
	/**
	 * ��������
	 * @throws IOException 
	 */
	public static void improveArgs() throws IOException {
		outer: while (MONOTONICITY <= TOTAL) {
			while (SMOOTHNESS <= TOTAL - MONOTONICITY) {
//				FileUtil.append("MONOTONICITY:" + MONOTONICITY + "\tSMOOTHNESS:" + SMOOTHNESS + "\tFREETILES:" + FREETILES);
				SMOOTHNESS += GAP;
				FREETILES = TOTAL - MONOTONICITY - SMOOTHNESS;
				break outer;
			}
			MONOTONICITY += GAP;
			SMOOTHNESS = 0;
			FREETILES = TOTAL - MONOTONICITY - SMOOTHNESS;
		}
	}
	
}
