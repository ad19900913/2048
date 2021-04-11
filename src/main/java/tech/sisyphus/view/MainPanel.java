package tech.sisyphus.view;

import tech.sisyphus.config.Recorder;
import tech.sisyphus.config.YamlConfig;
import tech.sisyphus.controller.GameControl;
import tech.sisyphus.controller.Robot;
import tech.sisyphus.model.Block;
import tech.sisyphus.util.CommonUtil;
import tech.sisyphus.util.DraggablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/*
 * 简单的2048游戏
 */
public class MainPanel extends DraggablePanel implements KeyListener {
    boolean gameover, success;//标志游戏是否开始
    private final Robot robot = new Robot(this);//模拟自动运行
    public ArrayList<Block> blocks = new ArrayList<>();
    public GameControl control = new GameControl(this);

    Date startDate;
    private StatusBar sf;

    public MainPanel(YamlConfig config) {
        super(config);
        addKeyListener(this);
    }

    //游戏初始化工作在这里
    public void gameInit() {
        gameover = false;
        success = false;
        startDate = new Date();

        if (sf == null) {
            sf = new StatusBar(this);
            new Thread(sf.statusPanel).start();
        }
        initBlock();
        removeAll();
        if (config.isAutorun() && !robot.isAlive()) {
            //自动运行
            robot.start();
        }
    }

	/**
	 * 初始化格局，可以是全新格局，也可以自定义格局，便于调试
	 */
	private void initBlock() {
        if (config.isCaseNew()) {
            blocks = new ArrayList<>();
            //初始化16个空白方块,初始化一个带数值的方块
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 5; j++) {
                    Block block = new Block(i, j);
                    if (i == 2 && j == 2) {
                        block.value = 2;
                    }
                    blocks.add(block);
                }
			}
		} else {
			int[][] arr = new int[][]{{2, 0, 0, 0},
					{2, 2, 4, 8},
					{4, 0, 0, 0},
					{8, 0, 0, 0}};
			blocks = (ArrayList<Block>) CommonUtil.arr2list(arr);
		}
	}

	//判断游戏当前状态  是正在游戏  还是游戏失败  或者成功
	public void judgeGameState() {
		for (Block block : blocks) {
			if (block.value == 2048) {
				success = true;
				return;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, config.getGameWidth(), config.getGameHeight());
        //judge_game_state();
        //画出每个方块
        for (Block block : blocks) {
            block.draw(g);
        }

        if (gameover) {
            g.setColor(Color.black);
            g.setFont(new Font("宋体", Font.BOLD, 40));
            g.drawString("GAME OVER", 200, 200);
		}

		if (success) {
			g.setColor(Color.black);
			g.setFont(new Font("宋体", Font.BOLD, 40));
			g.drawString("YOU WIN", 200, 200);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)) {
			gameInit();
			repaint();
		}
		if(e.getSource().equals(setup)){
			
		}
		if(e.getSource().equals(about)) {
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("宋体", Font.BOLD, 24));
			jta.setText("游戏中按Q键退出游戏\r\n"
					+ "按F键隐藏或显示计分条\r\n"
					+ "按R键开启一局新游戏\r\n"
					+ "W、A、S、D为控制键\r\n"
			);
			jta.setSize(150, 250);
			jta.setEditable(false);
			JOptionPane.showConfirmDialog(this, jta, "关于", JOptionPane.DEFAULT_OPTION, 2);
		}
		if(e.getSource().equals(exit)){
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			next(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			next(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			next(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			next(4);
		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			//实现弹出统计窗口
			sf.setVisible(!sf.isVisible());
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			gameInit();
			try {
				Recorder.init();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			System.exit(0);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			robot.isRunning = !robot.isRunning;
		}
	}

	public void next(int direction) {
		control.move(direction, true);//确定方向后处理
		control.setBlock(control.anyblockMove);//生成新的随机块
		Recorder.S_MOVE_TIMES++;
		repaint();//重画
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
}
