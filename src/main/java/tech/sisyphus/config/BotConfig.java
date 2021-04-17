package tech.sisyphus.config;

import lombok.Data;

@Data
public class BotConfig {

    private boolean enabled;
    /**
     * 自动搜索深度
     */
    private int deep;

    private int sleepTime;

}
