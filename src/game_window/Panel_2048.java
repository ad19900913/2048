package game_window;
import game_object.Block;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import util.Game_Control;
import util.Game_Panel;
/*
 * 简单的2048游戏
 * 
 * 还需完善的功能有计分，计时，计步
 * 
 */
public class Panel_2048 extends Game_Panel implements KeyListener{
	private boolean playing;//标志游戏是否开始
	public static ArrayList<Block> blocks;
	private Robot robot=new Robot();//模拟自动运行
	class Robot extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Math.random()<0.33){
					Game_Control.direction="down";
				}else if(Math.random()>0.66){
					Game_Control.direction="left";
				}else{
					Game_Control.direction="right";
				}
				Game_Control.gogogo();//确定方向后处理
				Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
				repaint();//重画
			}
		}
	}
	public Panel_2048(){
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
	}
	
	public static void main(String[] args) {
		new Panel_2048();
	}
	//游戏初始化工作在这里 
	public void game_init(){
		removeAll();
		requestFocusInWindow();
		addKeyListener(this);
		//robot.start();
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if(playing){
			//设置背景
			g.setColor(new Color(187, 173, 160));
			g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
			
			//画出每个方块
			for (Block block : blocks) {
				block.draw(g);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
			playing = true;//标志游戏开始
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W){
			Game_Control.direction="up";
			Game_Control.gogogo();//确定方向后处理
			Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
			repaint();//重画
		}
		if(e.getKeyCode()==KeyEvent.VK_S){
			Game_Control.direction="down";
			Game_Control.gogogo();//确定方向后处理
			Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
			repaint();//重画
		}
		if(e.getKeyCode()==KeyEvent.VK_A){
			Game_Control.direction="left";
			Game_Control.gogogo();//确定方向后处理
			Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
			repaint();//重画
		}
		if(e.getKeyCode()==KeyEvent.VK_D){
			Game_Control.direction="right";
			Game_Control.gogogo();//确定方向后处理
			Game_Control.setBlock(Game_Control.anyblock_move);//生成新的随机块
			repaint();//重画
		}
		if(e.getKeyCode()==KeyEvent.VK_Q){
			System.exit(0);
		}
	}
}
