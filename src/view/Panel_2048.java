package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.Block;
import util.Config;
import util.Game_Control;
import util.Game_Panel;
import util.Recorder;
import util.Robot;
/*
 * 简单的2048游戏
 */
public class Panel_2048 extends Game_Panel implements KeyListener{
	boolean playing,gameover,success;//标志游戏是否开始
	public static ArrayList<Block> blocks;
	private Robot robot=new Robot(this);//模拟自动运行
	
	Date startDate;
	private Stats_Frame sf;
	
	public Panel_2048(){
		addKeyListener(this);	
	}
	
	public static void main(String[] args) {
		new Panel_2048();
		
	}
	//游戏初始化工作在这里 
	public void game_init(){
		playing = true;//标志游戏开始
		gameover=false;
		success=false;
		startDate=new Date();
		
		sf=new Stats_Frame(this);
		new Thread(sf.stats_Panel).start();
		
		blocks=new ArrayList<Block>();
		//初始化16个空白方块,初始化一个带数值的方块
		for (int i = 1; i < 5; i++){
			for (int j = 1; j <5; j++){
				Block block = new  Block(i, j);
				if(i==2&&j==2){
					block.value=2;
				}
				blocks.add(block);
			}
		}
		removeAll();
		if (Config.AUTORUN && !robot.isAlive()) {
			//自动运行
			robot.start();
		}
	}
	
	//判断游戏当前状态  是正在游戏  还是游戏失败  或者成功
	public void judge_game_state(){
		for (Block block : blocks) {
			if(block.value==2048){
				success=true;
//				playing=false;
				return;
			}
			if(block.value==0){
//				playing=true;
				return;
			}
		}
//		playing=false;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(playing){
			g.setColor(new Color(187, 173, 160));
			g.fillRect(0, 0, Config.GAME_WIDTH, Config.GAME_HEIGHT);
			judge_game_state();
			//画出每个方块
			for (Block block : blocks) {
				block.draw(g);
			}
		}
		
		if(gameover){
			g.setColor(Color.black);
			g.setFont(new Font("微软雅黑", Font.BOLD, 40));
			g.drawString("GAME OVER", 200, 200);
		}
		
		if(success){
			g.setColor(Color.black);
			g.setFont(new Font("微软雅黑", Font.BOLD, 40));
			g.drawString("YOU WIN", 200, 200);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
			game_init();
			repaint();
		}
		if(e.getSource().equals(setup)){
			
		}
		if(e.getSource().equals(about)){ 
			JTextArea jta =new JTextArea();
			jta.setFont(new Font("微软雅黑", Font.BOLD, 24));
			jta.setText(" 太真妃裙带\r\n"
					   +"                 -苏轼\r\n"
					   +"百叠漪漪水皱\r\n"
					   +"六铢縰縰云轻\r\n"
					   +"植立含风广殿\r\n"
					   +"微闻环佩摇声\r\n"
					   +"\r\n"
					   +"游戏中按Q键退出游戏\r\n"
					   +"按F键隐藏或显示计分条\r\n"
					   +"按R键开启一局新游戏\r\n"
					   +"W、A、S、D为控制键\r\n"
					   );
			jta.setSize(150, 250);
			jta.setEditable(false);
			JOptionPane.showConfirmDialog(this, jta, "关于", JOptionPane.CLOSED_OPTION,2);
		}
		if(e.getSource().equals(exit)){
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()=='w'){
			next(1);
		}
		if(e.getKeyChar()=='s'){
			next(2);
		}
		if(e.getKeyChar()=='a'){
			next(3);
		}
		if(e.getKeyChar()=='d'){
			next(4);
		}
		if(e.getKeyChar()=='f'){
			//实现弹出统计窗口
			if(sf.isVisible()){
				sf.setVisible(false);
			}else{
				sf.setVisible(true);
			}
		}
		if(e.getKeyChar()=='r'){
			game_init();
			Recorder.init();
			repaint();
		}
		if(e.getKeyChar()=='q'){
			System.exit(0);
		}
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			robot.isRunning = !robot.isRunning;
		}
	}

	public void next(int direction) {
		Game_Control.move(direction);//确定方向后处理
		Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
		Recorder.MOVE_TIMES++;
		repaint();//重画
	}
	
	public void keyReleased(KeyEvent e) {}

	
}
