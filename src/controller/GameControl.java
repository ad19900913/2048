package controller;
import java.util.ArrayList;
import java.util.List;

import config.Recorder;
import model.Block;
import view.Panel2048;

/*
 * �����ƶ�ʱ�Ĳ���   ��Ϊ����1.��Խ�հ׸�  2.�ں�
 * ����B��ĳ���������ƶ�������÷�������һ��Ϊ�գ����ཻ����ֵ
 * 								     ��һ�������߽磬����
 *                          	     ��һ���������������ֵ��ͬ������
 *                          				       �����ֵ��ͬ���ں� 
 *                         	 	  	
 */
public class GameControl {
	//�ƶ�����
	public int direction;
	//�Ƿ��з����ƶ�
	public boolean anyblock_move=false;
	//��ʾ��ǰ�Զ�������ֵķ������꣬ÿ���ƶ���ˢ��
	private int x,y;
	private Panel2048 panel_2048;
	
	public GameControl(Panel2048 panel_2048) {
		super();
		this.panel_2048 = panel_2048;
	}

	/*
	 * �˷����ǺϷ��ƶ���Ż�ִ�е�
	 * ����ڰ���������û���κη��鱻�ƶ����򲻻������·���
	 * flag������ʶ����ƶ��ǲ�����Ч���ƶ�
	 */
	public boolean setBlock(boolean flag){
		if (flag) {
			List<Block> list = new ArrayList<Block>();
			x = (int) (Math.random() * 4 + 1);
			y = (int) (Math.random() * 4 + 1);
			for (Block block : panel_2048.blocks) {
				if (block.value == 0) {
					list.add(block);
				}
			}
			int index = (int) (Math.random() * list.size());
			list.get(index).value = 2;
			clearMergeFlag();
			anyblock_move = false;
			return true;
		}
		return false;	
	}
	
	private void clearMergeFlag() {
		for (Block block : panel_2048.blocks) {
			block.canMerge = true;
		}
	}

	//������ʱ��ֻ��Ҫ����VALUE
	public void exchange(Block b1,Block b2){
			b2.value=b1.value;
			b1.value=0;
	}
	
	/**
	 * b1��b2�ں�
	 * ������һ���ƶ���
	 * ÿһ�л���ÿһ��ֻ����һ���ں�
	 * @param b1
	 * @param b2
	 * @param flag	��Ǳ����ƶ��Ƿ�Ʒ�
	 */
	public void merge(Block b1, Block b2, boolean flag){
		
		if(b1.canMerge && b2.canMerge ){
			b1.canMerge = false;
			b2.canMerge = false;
			b1.value=0;
			b2.value=2*b2.value;
			if (flag) {
				Recorder.S_MAX_NUM = Math.max(Recorder.S_MAX_NUM, b2.value);
				Recorder.S_SCORES = Recorder.S_SCORES + b2.value;
			}
		}
		
	}
	//�ҵ���һ���ض������ש�飬�뷽���й�
	public Block getBlock(Block block,int dir){
		int targetX=block.x;//������ʶҪ�ҵ�Ŀ��������
		int targetY=block.y;
			switch (dir) {
			case 1:
				while (getBlock(targetX, targetY-1).value==0) {
					targetY-=1;
					//����whileѭ����ζ�ŷ��������ƶ���һ��  
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case 2:
				while (getBlock(targetX, targetY+1).value==0) {
					targetY+=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case 3:
				while (getBlock(targetX-1, targetY).value==0) {
					targetX-=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			case 4:
				while (getBlock(targetX+1, targetY).value==0) {
					targetX+=1;
					anyblock_move=true;
				}
				return getBlock(targetX, targetY);
			}
		return null;
	}
	
	//��ȡ�ض������Block����
	public Block getBlock(int x,int y){
		for (Block block_1 : panel_2048.blocks){
			if (block_1.x==x&&block_1.y==y) {
				return block_1;
			}
		}
		Block temp=new Block(5, 5);
		temp.value=100;
		return temp;
	}
	
	//�������˶��ķ���
	public void move(int direction, boolean flag){
		anyblock_move = false;
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
		if(direction==1){
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
							merge(block_2, temp, flag);
							anyblock_move = true;
						}
					}
				}
			}	
		}
		
		if(direction==2){
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
							merge(block_2, temp, flag);
							anyblock_move = true;
						}
					}
				}
			}
		}
		
		if(direction==4){
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
							merge(block_2, temp, flag);
							anyblock_move = true;
						}
					}
				}
			}
		}
		
		if(direction==3){
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
							merge(block_2, temp, flag);
							anyblock_move = true;
						}
					}
				}
			}
		}
	}
	
}
