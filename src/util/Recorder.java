package util;

public class Recorder {
	//有效移动次数
	public static int USEFULL_MOVE_TIMES=0;
	//总移动次数
	public static int MOVE_TIMES=0;
	//得分
	public static int SCORES=0;
	//本次游戏时间
	public static int PLAY_TIMES=0;
	
	
	public static void init(){
		USEFULL_MOVE_TIMES=0;
		MOVE_TIMES=0;
		SCORES=0;
		PLAY_TIMES=0;
	}
}
