package tech.sisyphus;

import org.yaml.snakeyaml.Yaml;
import tech.sisyphus.config.YamlConfig;
import tech.sisyphus.view.MainPanel;

public class Starter {

	public static void main(String[] args) {
        YamlConfig config = new Yaml().load(Starter.class.getClassLoader().getResourceAsStream("application.yaml"));
        new MainPanel(config);
    }
}
