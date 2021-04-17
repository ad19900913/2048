package tech.sisyphus;

import tech.sisyphus.config.YamlConfig;
import tech.sisyphus.view.MainPanel;

public class Starter {

	public static void main(String[] args) {
        new MainPanel(YamlConfig.getInstance());
    }
}
