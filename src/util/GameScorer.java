package util;

import config.Config;
import model.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * 评价函数
 *
 * @author user
 */
public class GameScorer {

	//单调性
	private double getmonotonicity(List<Block> blocks) {
		Block[][] arr = CommonUtil.list2arr(blocks);
		double count = 0;
		double temp = 1;
		double all = 64;
		//横向计算
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i][j + 1];
				double d = 0;
				if (block1.value == 0 || block2.value == 0) {
					d = 1;
				} else {
					d = (double) block1.value / (double) block2.value;
				}
				temp *= d;
			}
			count += temp;
			temp = 1;
		}
		//纵向计算
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i + 1][j];
				double d = 0;
				if (block1.value == 0 || block2.value == 0) {
					d = 1;
				} else {
					d = (double) block1.value / (double) block2.value;
				}
				temp *= d;
			}
			count += temp;
			temp = 1;
		}
		return (count / all) * Config.MONOTONICITY;
	}

	//平滑性
	private double getsmoothness(List<Block> blocks) {
		Block[][] arr = CommonUtil.list2arr(blocks);
		double count = 0;
		double all = 24;
		//横向计算
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i][j + 1];
				double min = (double) Math.min(block1.value, block2.value);
				double max = (double) Math.max(block1.value, block2.value) == 0 ? 1 : (double) Math.max(block1.value, block2.value);
				double d = min / max;
				count += d;
			}
		}
		//纵向计算
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i + 1][j];
				double min = (double) Math.min(block1.value, block2.value);
				double max = (double) Math.max(block1.value, block2.value) == 0 ? 1 : (double) Math.max(block1.value, block2.value);
				double d = min / max;
				count += d;
			}
		}
		return (count / all) * Config.SMOOTHNESS;
	}

	//空闲性
	private double getfreetiles(List<Block> blocks) {
		double count = 0;
		double all = 16;
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).value == 0) {
				count++;
			}
		}
		double d = (count / all) * Config.FREETILES;
		return d;
	}
	
	public double getScore(List<Block> blocks) {
		return getmonotonicity(blocks) + getsmoothness(blocks) + getfreetiles(blocks);
	}
	
	public static void main(String[] args) {
		int [][] arr = new int [][]{{2,0,0,0},
			{2,2,4,8},
			{4,0,0,0},
			{8,0,0,0}};
		ArrayList<Block> blocks = (ArrayList<Block>) CommonUtil.arr2list(arr);
		new GameScorer().getmonotonicity(blocks);
	}

}
