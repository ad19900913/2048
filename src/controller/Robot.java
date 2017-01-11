package controller;

import java.io.IOException;
import java.util.ArrayList;

import config.Config;
import config.Recorder;
import model.Block;
import util.CommonUtil;
import util.FileUtil;
import util.GameScorer;
import util.Node;
import view.Panel2048;

public class Robot extends Thread{
	//�Ƿ�������״̬
	public boolean isRunning = true;
	private Panel2048 panel;
	private GameScorer scorer = new GameScorer();
	private ArrayList<Block> original = new ArrayList<Block>();
	private Node max;
	private double value;
	@Override
	public void run(){
		while (true) {
			try {sleep(Config.AUTORUN_SLEEP_TIME);} catch (InterruptedException e) {}
			if (isRunning) {
				Node node = new Node(0, 0, null);
				original = (ArrayList<Block>) CommonUtil.getCloneList(panel.blocks);
				generateTree(node, 0, panel.blocks);
				int direction = getDirection(node);

				if (direction == 0) {
//					FileUtil.append("�ƶ�:" + Recorder.USEFULL_MOVE_TIMES + "\t�÷�:" + Recorder.SCORES + "\t�����:" + Recorder.MAX_NUM);
					panel.game_init();
					try {
						Recorder.init();
					} catch (IOException e) {
						e.printStackTrace();
					}
					panel.repaint();
				}else{
					panel.blocks = original;
					panel.control.move(direction, true);
					Recorder.S_MOVE_TIMES++;
					if (panel.control.anyblock_move) {
						Recorder.S_USEFULL_MOVE_TIMES++;
					}
					panel.control.setBlock(panel.control.anyblock_move);
					panel.repaint();
				}
				max = null;
				value = 0;
			}
		}
	}
	/**
	 * ��������
	 * @param parent
	 * @param deep
	 * @param blocks
	 */
	private void generateTree(Node parent, int deep, ArrayList<Block> blocks) {
		//�������+1
		deep++;
		if (deep > Config.DEEP) {
			return;
		} else {
			panel.blocks = (ArrayList<Block>) CommonUtil.getCloneList(blocks);
			for (int i = 1; i < 5; i++) {
				panel.control.move(i, false);
				double d;
				if (panel.control.anyblock_move) {
					d = scorer.getScore(panel.blocks);
					Node child = new Node(i, d, parent);
					parent.children.add(child);
					panel.control.setBlock(panel.control.anyblock_move);
					generateTree(child, deep, panel.blocks);
				}else{
					continue;
				}
				panel.blocks = (ArrayList<Block>) CommonUtil.getCloneList(blocks);
			}
		}
	}
	/**
	 * �������ж�����
	 * @param node
	 * @return
	 */
	private int getDirection(Node node){
		//��ȡvalue����Ҷ�ӽڵ�
		dfs(node);
		if (max != null) {
			//��Ҷ�ӽڵ��ȡ�ж�����
			while (max.parent.parent != null) {
				max = max.parent;
			}
			return max.key;
		}else{ 
			return 0;
		}
	}
	
	private Node dfs(Node parent){
		for (Node node : parent.children) {
			if (node.children != null && node.children.size() > 0) {
				dfs(node);
			} else {
				if (node.value > value) {
					max = node;
					value = node.value;
				}
			}
		}
		return max;
	}
	
	public Robot(Panel2048 panel) {
		super();
		this.panel = panel;
	}
	
}
