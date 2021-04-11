package tech.sisyphus.config;

import lombok.Data;

@Data
public class YamlConfig {
    /**
     * 窗口宽度
     */
    private int gameWidth = 512;
    /**
     * 窗口高度
     */
    private int gameHeight = 512;
    /**
     * 在屏幕边缘隐藏时露出的像素
     */
    private int showPixels = 5;
    /**
     * 休眠时间
     */
    private int sleepTime = 5;
    /**
     * 自动运行间隔时间
     */
    private int autorunSleepTime = 1;
    //定义主页面按钮大小的常量
    private int menuGap = gameHeight / 9, menuWidth = gameWidth / 3, menuHeight = gameHeight / 10;
    /**
     * 自动运行
     */
    private boolean autorun = true;
    /**
     * 初始化格局类型，true为全新格局，false为自定义
     */
    private boolean caseNew = true;
    /**
     * 自动搜索深度
     */
    private int deep = 8;
    /**
     * 窗口可见性
     */
    private boolean alwaysOnTop = false;
    /**
     * 单调性
     */
    private int monotonicity = 60;
    /**
     * 平滑性
     */
    private int smoothness = 0;
    /**
     * 空闲性
     */
    private int freetiles = 40;
    /**
     * 总分
     */
    private int total = 100;
    /**
     * 间隔
     */
    private int gap = 5;

    /**
     * 调整参数
     */
    private void improveArgs() {
        outer:
        while (monotonicity <= total) {
            while (smoothness <= total - monotonicity) {
                smoothness += gap;
                freetiles = total - monotonicity - smoothness;
                break outer;
            }
            monotonicity += gap;
            smoothness = 0;
            freetiles = total - monotonicity - smoothness;
        }
    }

}
