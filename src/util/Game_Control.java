package util;

import game_object.Block;
import game_window.Panel_2048;
/*
 * �����ƶ�ʱ�Ĳ���
 * ����B��ĳ���������ƶ�������÷�������һ��Ϊ�գ����ཻ����ֵ
 * 								     ��һ�������߽磬����
 *                          	     ��һ���������������ֵ��ͬ������
 *                          				      �����ֵ��ͬ���ں� 	 	  	
 */
public class Game_Control {
	
	public static String direction;//�ƶ�����
	public static boolean anyblock_move=false;
	
	private static int x,y;//��ʾ��ǰ�Զ�������ֵķ������꣬ÿ���ƶ���ˢ��
	

	/*
	 * �˷����ǺϷ��ƶ���Ż�ִ�е�
	 * ����ڰ���������û���κη��鱻�ƶ����򲻻������·���
	 */
	public static void setBlock(boolean flag){
			if (flag) {
				//ֻ��һ�ο����Ҳ������ʵĿո�����ֵ������ѭ��
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
				System.out.println("û�и��ӱ��ƶ�");
			}
	}
	
	//������ʱ��ֻ��Ҫ����VALUE
	public static void exchange(Block b1,Block b2){
		int temp=b1.value;
		b1.value=b2.value;
		b2.value=temp;
	}
	
	//b1��b2�ں�
	public static void merge(Block b1,Block b2){
		b1.value=0;
		b2.value=2*b2.value;
	}
	
	public static void stay(Block b1,Block b2){
		
	}
	
	//�ҵ���һ���ض������ש�飬�뷽���й�
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
	
	//�������˶��ķ���
	public static void gogogo(){
		for (int i = 0; i < 3; i++) {
			/*
			 * ����ֻ�Ƕ���ÿһ�������ֵķ������һ�γ����ƶ�������ɹ���ֻ�ƶ���һ��
			 * Ϊ��ʵ��һ�ΰ�������ʹ�÷����������ƶ������ұߣ�����������Ҫ�ƶ�����
			 * �������ѭ��
			 */
			for (Block block_1 : Panel_2048.blocks) {
				if (block_1.value != 0) {//�����Ϊ�գ���ͼ�ƶ�
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
