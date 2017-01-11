package util;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import model.Block;

public class CommonUtil {
	
	private static Toolkit t=Toolkit.getDefaultToolkit();

	public static Block [][] list2arr(List<Block> blocks){
		Block [][] arr = new Block [4][4];
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			arr[block.y - 1][block.x - 1] = block;
		}
		return arr;
	}
	
	public static List<Block> arr2list(int [][] arr){
		List<Block> blocks = new ArrayList<Block>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				Block block = new Block(i + 1, j + 1, arr[i][j]);
				blocks.add(block);
			}
		}
		return blocks;
	}
	
	//得到clone的集合
	public static List<Block> getCloneList(List<Block> blocks){
		ArrayList<Block> temp = new ArrayList<Block>();
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			try {
				temp.add((Block) block.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	public static Image GetImage(String path){
		Image image = t.getImage(path);
		return image;
	}
	
	public static Icon GetIcon(String path){
		Icon icon = new ImageIcon(path);
		return icon;
	}
}
