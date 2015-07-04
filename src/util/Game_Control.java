package util;
import game_object.Block;
import game_window.Panel_2048;
/*
 * �����ƶ�ʱ�Ĳ���   ��Ϊ����1.��Խ�հ׸�  2.�ں�
 * ����B��ĳ���������ƶ�������÷�������һ��Ϊ�գ����ཻ����ֵ
 * 								     ��һ�������߽磬����
 *                          	     ��һ���������������ֵ��ͬ������
 *                          				       �����ֵ��ͬ���ں� 
 *                         	 	  	
 */
public class Game_Control {
	public static String direction;//�ƶ�����
	public static boolean anyblock_move=false;
	private static int x,y;//��ʾ��ǰ�Զ�������ֵķ������꣬ÿ���ƶ���ˢ��
	/*
	 * �˷����ǺϷ��ƶ���Ż�ִ�е�
	 * ����ڰ���������û���κη��鱻�ƶ����򲻻������·���
	 * flag������ʶ����ƶ��ǲ�����Ч���ƶ�
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
			b2.value=b1.value;
			b1.value=0;
	}
	//b1��b2�ں�
	public static void merge(Block b1,Block b2){
		b1.value=0;
		b2.value=2*b2.value;
	}
	//�ҵ���һ���ض������ש�飬�뷽���й�
	public static Block getBlock(Block block,String dir){
		int targetX=block.x;//������ʶҪ�ҵ�Ŀ��������
		int targetY=block.y;
			switch (dir) {
			case "up":
				while (getBlock(targetX, targetY-1).value==0) {
					targetY-=1;
					//����whileѭ����ζ�ŷ��������ƶ���һ��  
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
	
	//��ȡ�ض������Block����
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
	
	//�������˶��ķ���
	public static void gogogo(){
			/*
			 * �����Ƕ���ÿһ�������ֵķ�����г����ƶ�
			 * Ҫʵ��һ�ΰ�������ʹ�÷����������ƶ������ұߣ�
			 *  
			 * ������Ҫע����� һ����Ч���ƶ�������  ÿһ�л���ÿһ�������ֻ����һ��
			 * ��ֵ��ͬ���ںϣ�������Ϸ�ѶȻή�͡�
			 * ���� 2 2 4 8        �ƶ���Ӧ�ñ��
			 *     0 4 4 8        ������ֱ�ӱ��
			 *     0 0 0 16
			 *           
			 */
			//��������ƶ�����ô�Ǵ�������һ�п�ʼ�������¼��� ������ƶ�
			if(direction=="up"){
				for (int j = 1; j < 5; j++) {
					for (int j2 = 1; j2 < 5; j2++) {
						Block block_1=getBlock(j2, j);
						if (block_1.value!=0) {//ֻ�����ƶ�����ֵ�ķ���
							/*
							 * ����һ��Ӧ���ܹ���Խ����  �������ҵ��ƶ���  
							 * �ܴ� 2 0 0 4 ��2ֱ���ƶ��� 0 0 2 4 �ĸ�� 
							 * ʵ��һ�����ƶ�����
							 * ������һ��Ĺ�����ֱ�Ӷ�λ
							 */
							Block block_2 = getBlock(block_1, direction);
							//�Ƚ���һ�£��ڴ���
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//������ɺ�������block_2
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
						if (block_1.value!=0) {//ֻ�����ƶ�����ֵ�ķ���
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//������ɺ�������block_2
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
						if (block_1.value!=0) {//ֻ�����ƶ�����ֵ�ķ���
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//������ɺ�������block_2
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
						if (block_1.value!=0) {//ֻ�����ƶ�����ֵ�ķ���
							Block block_2 = getBlock(block_1, direction);
							if (!block_1.equals(block_2)) {
								exchange(block_1, block_2);//������ɺ�������block_2
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
