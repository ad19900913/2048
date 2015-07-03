package util;

import game_object.Block;
import game_window.Panel_2048;
/*
 * 方块移动时的策略
 * 方块B向某个方向上移动，如果该方向上下一步为空，则互相交换数值
 * 								     下一步碰到边界，不动
 *                          	     下一步碰到方块如果数值不同，不动
 *                          				      如果数值相同，融合 	 	  	
 */
public class Game_Control {
	
	public static String direction;//移动方向
	public static boolean anyblock_move=false;
	
	private static int x,y;//表示当前自动填充数字的方块坐标，每次移动后刷新
	

	/*
	 * 此方法是合法移动后才会执行的
	 * 如果在按键方向上没有任何方块被移动，则不会生成新方块
	 */
	public static void setBlock(boolean flag){
			if (flag) {
				//只找一次可能找不到合适的空格来赋值，所以循环
				while (true) {
					x = (int) (Math.random() * 4 + 1);
					y = (int) (Math.random() * 4 + 1);
					for (Block block : Panel_2048.blocks) {
						if (block.x == x && block.y == y && block.value == 0) {
							block.value = 2;
							anyblock_move = false;
							return;
						}
					}
				}
			}else{
				System.out.println("没有格子被移动");
			}
	}
	
	//交换的时候只需要交换VALUE
	public static void exchange(Block b1,Block b2){
		int temp=b1.value;
		b1.value=b2.value;
		b2.value=temp;
	}
	
	//b1向b2融合
	public static void merge(Block b1,Block b2){
		b1.value=0;
		b2.value=2*b2.value;
	}
	
	public static void stay(Block b1,Block b2){
		
	}
	
	//找到下一块特定坐标的砖块，与方向有关
	public static Block getBlock(Block block,String dir){
		for (Block target : Panel_2048.blocks){
			switch (dir) {
			case "up":
				if(target.x==block.x&&target.y==block.y-1){
					return target;
				}
				break;
			case "down":
				if(target.x==block.x&&target.y==block.y+1){
					return target;
				}		
				break;
			case "left":
				if(target.x==block.x-1&&target.y==block.y){
					return target;
				}
				break;
			case "right":
				if(target.x==block.x+1&&target.y==block.y){
					return target;
				}
				break;
			}
			
		}
		return null;
	}
	
	//处理方块运动的方法
	public static void gogogo(){
		for (int i = 0; i < 3; i++) {
			/*
			 * 下面只是对于每一个有数字的方块进行一次尝试移动，如果成功，只移动了一格
			 * 为了实现一次按键可以使得方块从最左边移动到最右边，最坏的情况下需要移动三次
			 * 因此外层加循环
			 */
			for (Block block_1 : Panel_2048.blocks) {
				if (block_1.value != 0) {//如果不为空，试图移动
					Block block_2 = getBlock(block_1, direction);

					if (block_2 != null) {
						if (block_2.value == 0) {
							exchange(block_1, block_2);
							
							anyblock_move = true;
						}
						if (block_2.value == block_1.value) {
							merge(block_1, block_2);

							anyblock_move = true;
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Block b1=new Block(2, 2);
		b1.value=100;
		Block b2=new Block(2, 3);
		
	}
	
}
