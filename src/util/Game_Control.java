package util;
import game_object.Block;
import game_window.Panel_2048;
/*
 * 方块移动时的策略   分为两大步1.跨越空白格  2.融合
 * 方块B向某个方向上移动，如果该方向上下一步为空，则互相交换数值
 * 								     下一步碰到边界，不动
 *                          	     下一步碰到方块如果数值不同，不动
 *                          				       如果数值相同，融合 
 *                         	 	  	
 */
public class Game_Control {
	public static String direction;//移动方向
	public static boolean anyblock_move=false;
	private static int x,y;//表示当前自动填充数字的方块坐标，每次移动后刷新
	/*
	 * 此方法是合法移动后才会执行的
	 * 如果在按键方向上没有任何方块被移动，则不会生成新方块
	 * flag用来标识这次移动是不是有效的移动
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
			b2.value=b1.value;
			b1.value=0;
	}
	//b1向b2融合
	public static void merge(Block b1,Block b2){
		b1.value=0;
		b2.value=2*b2.value;
	}
	//找到下一块特定坐标的砖块，与方向有关
	public static Block getBlock(Block block,String dir){
		int targetX=block.x;//用来标识要找的目标块的坐标
		int targetY=block.y;
			switch (dir) {
			case "up":
				while (getBlock(targetX, targetY-1).value==0) {
					targetY-=1;
					//进入while循环意味着方块至少移动了一次  
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case "down":
				while (getBlock(targetX, targetY+1).value==0) {
					targetY+=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case "left":
				while (getBlock(targetX-1, targetY).value==0) {
					targetX-=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case "right":
				while (getBlock(targetX+1, targetY).value==0) {
					targetX+=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			}
		return null;
	}
	
	//获取特定坐标的Block对象
	public static Block getBlock(int x,int y){
		for (Block block_1 : Panel_2048.blocks){
			if (block_1.x==x&&block_1.y==y) {
				return block_1;
			}
		}
		Block temp=new Block(5, 5);
		temp.value=100;
		return temp;
	}
	
	//处理方块运动的方法
	public static void gogogo(){
			/*
			 * 下面是对于每一个有数字的方块进行尝试移动
			 * 要实现一次按键可以使得方块从最左边移动到最右边，
			 *  
			 * 而且需要注意的是 一次有效的移动过程中  每一行或者每一列上最好只能有一次
			 * 数值相同的融合，否则游戏难度会降低。
			 * 就是 2 2 4 8        移动后应该变成
			 *     0 4 4 8        而不是直接变成
			 *     0 0 0 16
			 *           
			 */
			//如果向上移动，那么是从最上面一行开始逐行往下计算 方块的移动
			if(direction=="up"){
				for (int j = 1; j < 5; j++) {
					for (int j2 = 1; j2 < 5; j2++) {
						Block block_1=getBlock(j2, j);
						if (block_1.value!=0) {//只尝试移动有数值的方块
							/*
							 * 下面一步应该能够跨越方块  比如向右的移动中  
							 * 能从 2 0 0 4 的2直接移动成 0 0 2 4 的格局 
							 * 实现一次性移动两格
							 * 下面这一句的功能是直接定位
							 */
							Block block_2 = getBlock(block_1, direction);
							//先交换一下，在处理
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//交换完成后主块变成block_2
								anyblock_move = true;
							}
							
							Block temp=getBlock(block_2.x,block_2.y-1);
							if (block_2.value == temp.value) {
								merge(block_2, temp);
								anyblock_move = true;
							}
						}
					}
				}	
			}
			
			if(direction=="down"){
				for (int j = 4; j > 0; j--) {
					for (int j2 = 1; j2 < 5; j2++) {
						Block block_1=getBlock(j2, j);
						if (block_1.value!=0) {//只尝试移动有数值的方块
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//交换完成后主块变成block_2
								anyblock_move = true;
							}
							
							Block temp=getBlock(block_2.x,block_2.y+1);
							if (block_2.value == temp.value) {
								merge(block_2, temp);
								anyblock_move = true;
							}
						}
					}
				}
			}
			
			if(direction=="right"){
				for (int j = 4; j > 0; j--) {
					for (int j2 = 1; j2 < 5; j2++) {
						Block block_1=getBlock(j, j2);
						if (block_1.value!=0) {//只尝试移动有数值的方块
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//交换完成后主块变成block_2
								anyblock_move = true;
							}
							
							Block temp=getBlock(block_2.x+1,block_2.y);
							if (block_2.value == temp.value) {
								merge(block_2, temp);
								anyblock_move = true;
							}
						}
					}
				}
			}
			
			if(direction=="left"){
				for (int j = 1; j < 5; j++) {
					for (int j2 = 1; j2 < 5; j2++) {
						Block block_1=getBlock(j, j2);
						if (block_1.value!=0) {//只尝试移动有数值的方块
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//交换完成后主块变成block_2
								anyblock_move = true;
							}
							
							Block temp=getBlock(block_2.x-1,block_2.y);
							if (block_2.value == temp.value) {
								merge(block_2, temp);
								anyblock_move = true;
							}
						}
					}
				}
			}
		
	}
	
}
