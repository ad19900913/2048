package config;

import java.io.IOException;

public class Config {
	/**
	 * 窗口宽度
	 */
	public static final int GAME_WIDTH= 400;
	/**
	 * 窗口高度
	 */
	public static final int GAME_HEIGHT= 400;
	/**
	 * 在屏幕边缘隐藏时露出的像素
	 */
	public static final int SHOW_PIXELS = 5;
	/**
	 * 休眠时间
	 */
	public static final int SLEEP_TIME = 5;
	/**
	 * 自动运行间隔时间
	 */
	public static final int AUTORUN_SLEEP_TIME = 1;
	//定义主页面按钮大小的常量
	public static final int MENU_GAP = GAME_HEIGHT / 9, MENU_WIDTH = GAME_WIDTH / 3, MENU_HEIGHT = GAME_HEIGHT / 10;
	/**
	 * 自动运行
	 */
	public static final boolean AUTORUN = true;
	/**
	 * 初始化格局类型，true为全新格局，false为自定义
	 */
	public static final boolean CASE_NEW = true;
	/**
	 * 自动搜索深度
	 */
	public static final int DEEP = 8;
	/**
	 * 窗口可见性
	 */
	public static final boolean ALWAYS_ON_TOP = false;
	/**
	 * 日志文件
	 */
	public static final String LOG_FILE = "C:/Users/Captain/Desktop/2048.log";
	/**
	 * 单调性
	 */
	public static int MONOTONICITY = 60;
	/**
	 * 平滑性
	 */
	public static int SMOOTHNESS = 0;
	/**
	 * 空闲性
	 */
	public static int FREETILES = 40;
	/**
	 * 总分
	 */
	public static int TOTAL = 100;
	/**
	 * 间隔
	 */
	public static int GAP = 5;
	
	/**
	 * 调整参数
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
