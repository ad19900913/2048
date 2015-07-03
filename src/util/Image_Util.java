package util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Image_Util {
	
	private static Toolkit t=Toolkit.getDefaultToolkit();
	
	public static Image GetImage(String path){
		Image image = t.getImage(path);
		return image;
	}
	
	public static Icon GetIcon(String path){
		Icon icon = new ImageIcon(path);
		return icon;
	}
}
