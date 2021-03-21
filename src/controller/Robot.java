package controller;

import config.Config;
import config.Recorder;
import model.Block;
import util.CommonUtil;
import util.GameScorer;
import util.Node;
import view.Panel2048;

import java.io.IOException;
import java.util.ArrayList;

public class Robot extends Thread {
	//是否处于运行状态
	public boolean isRunning = true;
	private Panel2048 panel;
	private GameScorer scorer = new GameScorer();
	private ArrayList<Block> original = new ArrayList<Block>();
	private Node max;
	private double value;

	@Override
	public void run() {
		while (true) {
			try {
				sleep(Config.AUTORUN_SLEEP_TIME);
			} catch (InterruptedException e) {
			}
			if (isRunning) {
				Node node = new Node(0, 0, null);
				original = (ArrayList<Block>) CommonUtil.getCloneList(panel.blocks);
				generateTree(node, 0, panel.blocks);
				int direction = getDirection(node);

				if (direction == 0) {
//					FileUtil.append("移动:" + Recorder.USEFULL_MOVE_TIMES + "\t得分:" + Recorder.SCORES + "\t最大数:" + Recorder.MAX_NUM);
					panel.game_init();
					try {
						Recorder.init();
					} catch (IOException e) {
						e.printStackTrace();
					}
					panel.repaint();
				}else {
					panel.blocks = original;
					panel.control.move(direction, true);
					Recorder.S_MOVE_TIMES++;
					if (panel.control.anyblockMove) {
						Recorder.S_USEFULL_MOVE_TIMES++;
					}
					panel.control.setBlock(panel.control.anyblockMove);
					panel.repaint();
				}
				max = null;
				value = 0;
			}
		}
	}

	/**
	 * 构造格局树
	 *
	 * @param parent
	 * @param deep
	 * @param blocks
	 */
	private void generateTree(Node parent, int deep, ArrayList<Block> blocks) {
		//搜索深度+1
		deep++;
		if (deep > Config.DEEP) {
			return;
		} else {
			panel.blocks = (ArrayList<Block>) CommonUtil.getCloneList(blocks);
			for (int i = 1; i < 5; i++) {
				panel.control.move(i, false);
				double d;
				if (panel.control.anyblockMove) {
					d = scorer.getScore(panel.blocks);
					Node child = new Node(i, d, parent);
					parent.children.add(child);
					panel.control.setBlock(panel.control.anyblockMove);
					generateTree(child, deep, panel.blocks);
				} else {
					continue;
				}
				panel.blocks = (ArrayList<Block>) CommonUtil.getCloneList(blocks);
			}
		}
	}

	/**
	 * 获得最佳行动方向
	 * @param node
	 * @return
	 */
	private int getDirection(Node node) {
		//获取value最大的叶子节点
		dfs(node);
		if (max != null) {
			//由叶子节点获取行动方向
			while (max.parent.parent != null) {
				max = max.parent;
			}
			return max.key;
		} else{
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
