package game_window;
import game_object.Block;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import util.Game_Control;
import util.Game_Panel;
import util.Recorder;
/*
 * �򵥵�2048��Ϸ
 * 
 * �Ѿ���ɵĹ����мƷ֣���ʱ���Ʋ�
 * ����Ҫ����㷨�Ż�����Ϸ��ʤ��ʧ�ܵ������ж���ʾ
 * 
 */
public class Panel_2048 extends Game_Panel implements KeyListener{
	boolean playing,gameover,success;//��־��Ϸ�Ƿ�ʼ
	public static ArrayList<Block> blocks;
	private Robot robot=new Robot();//ģ���Զ�����
	Date startDate;
	private Stats_Frame sf;
	
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
				Game_Control.gogogo();//ȷ���������
				Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
				repaint();//�ػ�
			}
		}
	}

	public Panel_2048(){
		addKeyListener(this);
	}
	
	public static void main(String[] args) {
		new Panel_2048();
	}
	//��Ϸ��ʼ������������ 
	public void game_init(){
		playing = true;//��־��Ϸ��ʼ
		gameover=false;
		success=false;
		startDate=new Date();
		sf=new Stats_Frame(this);
		new Thread(sf.stats_Panel).start();
		
		blocks=new ArrayList<Block>();
		//��ʼ��16���հ׷���,��ʼ��һ������ֵ�ķ���
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

		
		//robot.start();
	}
	
	//�ж���Ϸ��ǰ״̬  ��������Ϸ  ������Ϸʧ��  ���߳ɹ�
	public void judge_game_state(){
		for (Block block : blocks) {
			if(block.value==32){
				success=true;
				playing=false;
				return;
			}
			if(block.value==0){
				playing=true;
				return;
			}
		}
		playing=false;
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		if(playing){
			g.setColor(new Color(187, 173, 160));
			g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
			judge_game_state();
			//����ÿ������
			for (Block block : blocks) {
				block.draw(g);
			}
		}
		
		if(gameover){
			g.setColor(Color.black);
			g.setFont(new Font("΢���ź�", Font.BOLD, 40));
			g.drawString("GAME OVER", 200, 200);
		}
		
		if(success){
			g.setColor(Color.black);
			g.setFont(new Font("΢���ź�", Font.BOLD, 40));
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
			jta.setFont(new Font("΢���ź�", Font.BOLD, 24));
			jta.setText(" ̫����ȹ��\r\n"
					   +"                 -����\r\n"
					   +"�ٵ�����ˮ��\r\n"
					   +"�����u�u����\r\n"
					   +"ֲ��������\r\n"
					   +"΢�Ż���ҡ��\r\n"
					   +"\r\n"
					   +"��Ϸ�а�Q���˳���Ϸ\r\n"
					   +"��F�����ػ���ʾ�Ʒ���\r\n"
					   +"��R������һ������Ϸ\r\n"
					   +"W��A��S��DΪ���Ƽ�\r\n"
					   );
			jta.setSize(150, 250);
			jta.setEditable(false);
			JOptionPane.showConfirmDialog(this, jta, "����", JOptionPane.CLOSED_OPTION,2);
		}
		if(e.getSource().equals(exit)){
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()=='w'){
			Game_Control.direction="up";
			next();
		}
		if(e.getKeyChar()=='s'){
			Game_Control.direction="down";
			next();
		}
		if(e.getKeyChar()=='a'){
			Game_Control.direction="left";
			next();
		}
		if(e.getKeyChar()=='d'){
			Game_Control.direction="right";
			next();
		}
		if(e.getKeyChar()=='f'){
			//ʵ�ֵ���ͳ�ƴ���
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void next() {
		Game_Control.gogogo();//ȷ���������
		Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
		Recorder.MOVE_TIMES++;
		repaint();//�ػ�
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
