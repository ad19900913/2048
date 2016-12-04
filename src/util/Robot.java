package util;

import java.util.ArrayList;
import java.util.List;

import model.Block;
import view.Panel_2048;

public class Robot extends Thread{
	//是否处于运行状态
	public boolean isRunning = true;
	//单调性
	private double monotonicity = (double)3 / (double)8;
	//平滑性
	private double smoothness = (double)3 / (double)8;
	//空闲性
	private double freetiles = (double)1 / (double)4;
	
	Panel_2048 panel_2048 ;
	@Override
	public void run(){
		while (true) {
			try {sleep(Config.AUTORUN_SLEEP_TIME);} catch (InterruptedException e) {}
			if (isRunning) {
				if (getnum(panel_2048.blocks) == 0) {
					System.out.print("");
				}
				//把移动前的分布复制一份备用
				ArrayList<Block> temp = (ArrayList<Block>) getCloneList(panel_2048.blocks);
				//从4种情况中选出格局评分最高的执行
				Game_Control.move(1);
				double d1;
				if (Game_Control.anyblock_move) {
					d1 = getmonotonicity(panel_2048.blocks) + getsmoothness(panel_2048.blocks) + getfreetiles(panel_2048.blocks);
				} else {
					d1 = -1;
				}
				panel_2048.blocks = (ArrayList<Block>) getCloneList(temp);
				
				Game_Control.move(2);
				double d2;
				if (Game_Control.anyblock_move) {
					d2 = getmonotonicity(panel_2048.blocks) + getsmoothness(panel_2048.blocks) + getfreetiles(panel_2048.blocks);
				} else {
					d2 = -1;
				}
				panel_2048.blocks = (ArrayList<Block>) getCloneList(temp);
				
				Game_Control.move(3);
				double d3;
				if (Game_Control.anyblock_move) {
					d3 = getmonotonicity(panel_2048.blocks) + getsmoothness(panel_2048.blocks) + getfreetiles(panel_2048.blocks);
				} else {
					d3 = -1;
				}
				panel_2048.blocks = (ArrayList<Block>) getCloneList(temp);
				
				Game_Control.move(4);
				double d4;
				if (Game_Control.anyblock_move) {
					d4 = getmonotonicity(panel_2048.blocks) + getsmoothness(panel_2048.blocks) + getfreetiles(panel_2048.blocks);
				} else {
					d4 = -1;
				}
				panel_2048.blocks = (ArrayList<Block>) getCloneList(temp);
				
				double d = Math.max(Math.max(d1, d2), Math.max(d3, d4));
				if (d == -1) {
					System.out.println("移动:" + Recorder.USEFULL_MOVE_TIMES + "\t得分:" + Recorder.SCORES);
					panel_2048.game_init();
					Recorder.init();
					panel_2048.repaint();
				}else{
					if (d == d1) {
						Game_Control.move(1);
					}else if (d == d2) {
						Game_Control.move(2);
					}else if (d == d3) {
						Game_Control.move(3);
					}else if (d == d4) {
						Game_Control.move(4);
					}
					Recorder.MOVE_TIMES++;
					Game_Control.setBlock(Game_Control.anyblock_move);
					panel_2048.repaint();
				}
			}
		}
	}
	
	private List<Block> getCloneList(List<Block> blocks){
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
	
	private double getmonotonicity(List<Block> blocks){
		Block [][] arr = list2arr(blocks);
		double count = 1;
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
					d = (double)block1.value / (double)block2.value;
				}
				count *= d;
			}
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
					d = (double)block1.value / (double)block2.value;
				}
				count *= d;
			}
		}
		return (count/all)*monotonicity;
	}
	
	private double getsmoothness(List<Block> blocks){
		Block [][] arr = list2arr(blocks);
		double count = 0;
		double all = 24;
		//横向计算
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i][j + 1];
				double min = (double)Math.min(block1.value, block2.value);
				double max = (double)Math.max(block1.value, block2.value) == 0 ? 1 : (double)Math.max(block1.value, block2.value);
				double d = min / max;
				count += d;
			}
		}
		//纵向计算
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length; j++) {
				Block block1 = arr[i][j];
				Block block2 = arr[i + 1][j];
				double min = (double)Math.min(block1.value, block2.value);
				double max = (double)Math.max(block1.value, block2.value) == 0 ? 1 : (double)Math.max(block1.value, block2.value);
				double d = min / max;
				count += d;
			}
		}
		return (count/all)*smoothness;
	}
	
	private double getfreetiles(List<Block> blocks){
		double count = 0;
		double all = 16;
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).value == 0) {
				count++;
			}
		}
		double d = (count/all)*freetiles;
		return d;
	}
	
	private int getnum(List<Block> blocks){
		int count = 0;
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).value == 0) {
				count++;
			}
		}
		return count;
	}
	
	private Block [][] list2arr(List<Block> blocks){
		Block [][] arr = new Block [4][4];
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			arr[block.y - 1][block.x - 1] = block;
		}
		return arr;
	}

	public Robot(Panel_2048 panel_2048) {
		super();
		this.panel_2048 = panel_2048;
	}
}
