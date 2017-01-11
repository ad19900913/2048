package config;

import java.io.IOException;

import util.FileUtil;

public class Recorder {
	/**
	 * ������Ϸ-��Ч�ƶ�����
	 */
	public static int S_USEFULL_MOVE_TIMES = 0;
	/**
	 * ������Ϸ-���ƶ�����
	 */
	public static int S_MOVE_TIMES = 0;
	/**
	 * ������Ϸ-�÷� 
	 */
	public static int S_SCORES = 0;
	/**
	 * ������Ϸ-��Ϸʱ��
	 */
	public static int S_PLAY_TIMES = 0;
	/**
	 * ������Ϸ-�����
	 */
	public static int S_MAX_NUM = 0;
	/**
	 * ͳ��-�����
	 */
	public static int T_MAX_NUM = 0;
	/**
	 * ͳ��-�ܵ÷�
	 */
	public static int T_SCORES = 0;
	/** 
	 * ͳ��-ƽ���÷�
	 */
	public static int T_AVERAGE_SCORES = 0;
	/**
	 * ͳ��-��С�÷�
	 */
	public static int T_MIN_SCORES = 0;
	/**
	 * ͳ��-���÷�
	 */
	public static int T_MAX_SCORES = 0;
	/**
	 * ��ǰ���д���
	 */
	public static int T_RUN_NUM = 0;
	/**
	 * ������д���
	 */
	public static int T_MAX_RUN_NUM = 50;
	/**
	 * ��Ϸ�ɹ�����
	 */
	public static int T_SUCCESS_NUM = 0;
	
	
	public static void init() throws IOException{
		if (S_MAX_NUM >= 2048) {
			T_SUCCESS_NUM++;
		}
		T_MAX_NUM = Math.max(T_MAX_NUM, S_MAX_NUM);
		T_RUN_NUM++;
		T_SCORES += S_SCORES;
		T_MIN_SCORES = T_MIN_SCORES == 0 ? S_SCORES : Math.min(T_MIN_SCORES, S_SCORES);
		T_MAX_SCORES = Math.max(T_MAX_SCORES, S_SCORES);
		T_AVERAGE_SCORES = T_SCORES / T_RUN_NUM;
		S_USEFULL_MOVE_TIMES=0;
		S_MOVE_TIMES=0;
		S_SCORES=0;
		S_PLAY_TIMES=0;
		S_MAX_NUM=0;
		if (T_RUN_NUM >= T_MAX_RUN_NUM) {
			double percent = (double)((double)T_SUCCESS_NUM/(double)T_MAX_RUN_NUM);
			FileUtil.append("MONOTONICITY:" + Config.MONOTONICITY + "\tSMOOTHNESS:" + Config.SMOOTHNESS + "\tFREETILES:" + Config.FREETILES + "\tMAX_NUM:" + T_MAX_NUM + "\tMIN_SCORES:" + T_MIN_SCORES + "\tMAX_SCORES:" + T_MAX_SCORES + "\tAVERAGE_SCORES:" + T_AVERAGE_SCORES + "\tSUCCESS:" + percent);
			T_MAX_NUM = 0;
			T_RUN_NUM = 0;
			T_MIN_SCORES = 0;
			T_MAX_SCORES = 0;
			T_SCORES = 0;
			T_AVERAGE_SCORES = 0;
			T_SUCCESS_NUM = 0;
			Config.improveArgs();
		}
	}
}
