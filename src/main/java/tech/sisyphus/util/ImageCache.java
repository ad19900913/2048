package tech.sisyphus.util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static final Toolkit DEFAULT_TOOLKIT = Toolkit.getDefaultToolkit();
    private static final Map<String, Icon> iconMap = new HashMap<>();
    private static final Map<String, Image> imageMap = new HashMap<>();

    public static Image getImage(String path) {
        return imageMap.computeIfAbsent(path, key -> DEFAULT_TOOLKIT.getImage(CommonUtil.class.getClassLoader().getResource(key)));
    }

    public static Icon getIcon(String path) {
        return iconMap.computeIfAbsent(path, key -> new ImageIcon(CommonUtil.class.getClassLoader().getResource(key)));
    }

}
