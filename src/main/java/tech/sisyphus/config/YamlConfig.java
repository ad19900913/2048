package tech.sisyphus.config;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;
import tech.sisyphus.Starter;

@Data
public class YamlConfig {

    private static YamlConfig Instance;
    /**
     * 窗口宽度
     */
    private int gameWidth;
    /**
     * 窗口高度
     */
    private int gameHeight;
    /**
     * 在屏幕边缘隐藏时露出的像素
     */
    private int showPixels;
    /**
     * 休眠时间
     */
    private int sleepTime;
    /**
     * 自动运行间隔时间
     */
    private int autorunSleepTime;
    //定义主页面按钮大小的常量
    private int menuGap = gameHeight / 9;
    private int menuWidth = gameWidth / 3;
    private int menuHeight = gameHeight / 10;
    /**
     * 自动运行
     */
    private BotConfig bot;
    /**
     * 初始化格局类型，true为全新格局，false为自定义
     */
    private boolean caseNew;
    /**
     * 窗口可见性
     */
    private boolean alwaysOnTop;
    /**
     * 单调性
     */
    private int monotonicity;
    /**
     * 平滑性
     */
    private int smoothness;
    /**
     * 空闲性
     */
    private int freetiles;
    /**
     * 总分
     */
    private int total;
    /**
     * 间隔
     */
    private int gap;

    private YamlConfig() {
    }

    public static YamlConfig getInstance() {
        if (Instance == null) {
            Instance = new Yaml().loadAs(Starter.class.getClassLoader().getResourceAsStream("application.yaml"), YamlConfig.class);
        }
        return Instance;
    }

    /**
     * 调整参数
     */
    public void improveArgs() {
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
