package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import config.Config;
import config.Recorder;
import controller.GameControl;
import controller.Robot;
import model.Block;
import util.CommonUtil;
import util.GamePanel;
/*
 * �򵥵�2048��Ϸ
 */
public class Panel2048 extends GamePanel implements KeyListener{
	boolean gameover,success;//��־��Ϸ�Ƿ�ʼ
	public ArrayList<Block> blocks = new ArrayList<Block>();
	private Robot robot=new Robot(this);//ģ���Զ�����
	public GameControl control = new GameControl(this);
	
	Date startDate;
	private Stats_Frame sf;
	
	public Panel2048(){
		addKeyListener(this);	
	}
	
	//��Ϸ��ʼ������������ 
	public void game_init(){
		gameover=false;
		success=false;
		startDate=new Date();
		
		if (sf == null) {
			sf = new Stats_Frame(this);
			new Thread(sf.stats_Panel).start();
		}
		init_block();
		removeAll();
		if (Config.AUTORUN && !robot.isAlive()) {
			//�Զ�����
			robot.start();
		}
	}

	/**
	 * ��ʼ����֣�������ȫ�¸�֣�Ҳ�����Զ����֣����ڵ���
	 */
	private void init_block() {
		if (Config.CASE_NEW) {
			blocks = new ArrayList<Block>();
			//��ʼ��16���հ׷���,��ʼ��һ������ֵ�ķ���
			for (int i = 1; i < 5; i++) {
				for (int j = 1; j < 5; j++) {
					Block block = new Block(i, j);
					if (i == 2 && j == 2) {
						block.value = 2;
					}
					blocks.add(block);
				}
			} 
		}else{
			int [][] arr = new int [][]{{2,0,0,0},
										{2,2,4,8},
										{4,0,0,0},
										{8,0,0,0}};
				blocks = (ArrayList<Block>) CommonUtil.arr2list(arr);
		}
	}
	
	//�ж���Ϸ��ǰ״̬  ��������Ϸ  ������Ϸʧ��  ���߳ɹ�
	public void judge_game_state(){
		for (Block block : blocks) {
			if(block.value==2048){
				success=true;
				return;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, Config.GAME_WIDTH, Config.GAME_HEIGHT);
		//judge_game_state();
		//����ÿ������
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).draw(g);
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
			jta.setText("��Ϸ�а�Q���˳���Ϸ\r\n"
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
			//ʵ�ֵ���ͳ�ƴ���
			if(sf.isVisible()){
				sf.setVisible(false);
			}else{
				sf.setVisible(true);
			}
		}
		if(e.getKeyChar()=='r'){
			game_init();
			try {
				Recorder.init();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
		control.move(direction, true);//ȷ���������
		control.setBlock(control.anyblock_move);//�����µ������
		Recorder.S_MOVE_TIMES++;
		repaint();//�ػ�
	}
	
	public void keyReleased(KeyEvent e) {}

	
}
