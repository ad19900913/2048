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
 * �򵥵�2048��Ϸ
 * 
 * �������ƵĹ����мƷ֣���ʱ���Ʋ�
 * 
 */
public class Panel_2048 extends Game_Panel implements KeyListener{
	private boolean playing;//��־��Ϸ�Ƿ�ʼ
	public static ArrayList<Block> blocks;
	private Robot robot=new Robot();//ģ���Զ�����
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
	}
	
	public static void main(String[] args) {
		new Panel_2048();
	}
	//��Ϸ��ʼ������������ 
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
			//���ñ���
			g.setColor(new Color(187, 173, 160));
			g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
			
			//����ÿ������
			for (Block block : blocks) {
				block.draw(g);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)){
			playing = true;//��־��Ϸ��ʼ
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
			Game_Control.gogogo();//ȷ���������
			Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
			repaint();//�ػ�
		}
		if(e.getKeyCode()==KeyEvent.VK_S){
			Game_Control.direction="down";
			Game_Control.gogogo();//ȷ���������
			Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
			repaint();//�ػ�
		}
		if(e.getKeyCode()==KeyEvent.VK_A){
			Game_Control.direction="left";
			Game_Control.gogogo();//ȷ���������
			Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
			repaint();//�ػ�
		}
		if(e.getKeyCode()==KeyEvent.VK_D){
			Game_Control.direction="right";
			Game_Control.gogogo();//ȷ���������
			Game_Control.setBlock(Game_Control.anyblock_move);//�����µ������
			repaint();//�ػ�
		}
		if(e.getKeyCode()==KeyEvent.VK_Q){
			System.exit(0);
		}
	}
}
