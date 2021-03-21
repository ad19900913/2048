package tech.sisyphus.config;

import tech.sisyphus.util.FileUtil;

import java.io.IOException;

public class Recorder {
    /**
     * 单局游戏-有效移动次数
     */
    public static int S_USEFULL_MOVE_TIMES = 0;
    /**
     * 单局游戏-总移动次数
     */
    public static int S_MOVE_TIMES = 0;
    /**
     * 单局游戏-得分
     */
    public static int S_SCORES = 0;
    /**
     * 单局游戏-游戏时间
     */
    public static int S_PLAY_TIMES = 0;
    /**
     * 单局游戏-最大数
     */
    public static int S_MAX_NUM = 0;
    /**
     * 统计-最大数
     */
    public static int T_MAX_NUM = 0;
    /**
     * 统计-总得分
     */
    public static int T_SCORES = 0;
    /**
     * 统计-平均得分
     */
    public static int T_AVERAGE_SCORES = 0;
    /**
     * 统计-最小得分
     */
    public static int T_MIN_SCORES = 0;
    /**
     * 统计-最大得分
     */
    public static int T_MAX_SCORES = 0;
    /**
     * 当前运行次数
     */
    public static int T_RUN_NUM = 0;
    /**
     * 最大运行次数
     */
    public static int T_MAX_RUN_NUM = 50;
    /**
     * 游戏成功次数
     */
    public static int T_SUCCESS_NUM = 0;


    public static void init() throws IOException {
        if (S_MAX_NUM >= 2048) {
            T_SUCCESS_NUM++;
        }
        T_MAX_NUM = Math.max(T_MAX_NUM, S_MAX_NUM);
        T_RUN_NUM++;
        T_SCORES += S_SCORES;
        T_MIN_SCORES = T_MIN_SCORES == 0 ? S_SCORES : Math.min(T_MIN_SCORES, S_SCORES);
        T_MAX_SCORES = Math.max(T_MAX_SCORES, S_SCORES);
        T_AVERAGE_SCORES = T_SCORES / T_RUN_NUM;
        S_USEFULL_MOVE_TIMES = 0;
        S_MOVE_TIMES = 0;
        S_SCORES = 0;
        S_PLAY_TIMES = 0;
        S_MAX_NUM = 0;
        if (T_RUN_NUM >= T_MAX_RUN_NUM) {
            double percent = (double) ((double) T_SUCCESS_NUM / (double) T_MAX_RUN_NUM);
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
