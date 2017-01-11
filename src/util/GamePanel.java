package util;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.Config;
/*
 * дһ���ޱ߿򣬿����趨��С
 * ��������϶������Ҳ�������Ļ�߽�
 * ���Կ�����Ļ��Ե�Զ����ص���Ϸ���
 * ��Ϊ�����������Ϸ��
 */
public class GamePanel extends JPanel implements MouseListener,ActionListener{

	private Toolkit t=Toolkit.getDefaultToolkit();
	//���������Զ���ʾ��������ʱ������겻�ܲ�����Ӧ������������˸
	private boolean isRolling;
	//Ϊ��ʵ��ֻ���������϶�����  ������������
	private boolean canmove=false;

	//���尴ť���
	protected JButton start;
	protected JButton setup;
	protected JButton about;
	protected JButton exit;
	private JFrame f;
	private int screenX=t.getScreenSize().width;
	private int screenY=t.getScreenSize().height;
	//��¼���ʱ���λ�� �϶�Ҫ��
	private int mouseX;
	private int mouseY;
	@Override
 	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(CommonUtil.GetImage("images\\login.jpg"), 0, 0, Config.GAME_WIDTH, Config.GAME_HEIGHT, this);
		start.updateUI();
		setup.updateUI();
		about.updateUI();
		exit.updateUI();
	}
	/*���ô���λ��  Ҫ�����ػ�����ʾ�ķ��� 
	 * hiden=true ������
	 * diection=1����  2����  3����
	 * */
	public void setloca(final boolean hiden,final int direction){
		Thread t = new Thread(){
			int frameY=f.getY();
			int frameX=f.getX();
			public void run() {
				isRolling=true;//��ʾ���������Զ���Ӧλ��
				//����
				if (hiden) {
					//��������
					if (direction==1) {
						while (frameX > Config.SHOW_PIXELS-Config.GAME_WIDTH) {
							frameX -= Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					//��������
					else if(direction==3){
						while (frameX < screenX-Config.SHOW_PIXELS) {
							frameX += Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					//��������
					else if(direction==2){
						while (frameY > Config.SHOW_PIXELS-Config.GAME_HEIGHT) {
							frameY -= Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				//����
				else{
					//�������ҵ���
					if (direction==1) {
						while (frameX < -Config.SHOW_PIXELS) {
							frameX += Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					//�������󵯳� 
					else if(direction==3){
						while (frameX > screenX-Config.GAME_WIDTH+Config.SHOW_PIXELS){
							frameX -= Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					//�������µ���
					else if(direction==2){
						while (frameY < -Config.SHOW_PIXELS) {
							frameY += Config.SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(Config.SLEEP_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				isRolling=false;
			}	
		};
		t.start();
	}
	
	public void init(){
		f=new JFrame();
		f.setSize(Config.GAME_WIDTH, Config.GAME_HEIGHT);
		f.setLocationRelativeTo(null);
		//ʵ�ִ����Զ�����
		f.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mouseX = e.getX();
					mouseY = e.getY();
					canmove=true;
				}
			}
			// ֻ�е���������ſ����ƶ�����
			public void mouseReleased(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					canmove=false;
				}
			}
			//����˳� ������Ļ��Ե �Զ�����
 			public void mouseExited(MouseEvent e) {
				if (!isRolling) {
					if (f.getX() <= 0) {//��Ļ���
						setloca(true, 1);
					} else if (f.getX() >= screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS) {//��Ļ�Ҳ�
						setloca(true, 3);
					} else if (f.getY() <= 0) {//��Ļ����
						setloca(true, 2);
					}
				}
			}
			//������ �Զ�����
			public void mouseEntered(MouseEvent e) {
				if (!isRolling) {
					if (f.getX() <= 0) {//��Ļ���
						setloca(false, 1);
					}
					if (f.getX() >= screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS) {//��Ļ�Ҳ�
						setloca(false, 3);
					}
					if (f.getY() <= 0) {//��Ļ����
						setloca(false, 2);
					}
				}	
			}
		});
		//ʵ�ִ��������϶�
		f.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(final MouseEvent e){
				if (canmove) {
					//������ʾ ����Ҫ�ƶ�����Ŀ������
					int targetX = f.getX() + (e.getX() - mouseX);
					int targetY = f.getY() + (e.getY() - mouseY);
					//�߽��ж� 
					if (targetX < 0) {
						//���Ͻ�
						if (targetY < 0) {
							f.setLocation(0, 0);
						}
						//���   �����Դ�����
						else {
							f.setLocation(0, targetY);
						}
					}else if (targetX > screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS){
						if (targetY < 0){
							f.setLocation(screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS, 0);
						} else {
							f.setLocation(screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS, targetY);
						}

					}else if (targetY < 0) {
						f.setLocation(targetX, 0);
					}
					//�����߽綼���ڵ�ʱ��ſ��������϶�
					else {
						f.setLocation(targetX, targetY);
					}
				}	
			}	
		});
		//��ʼ����ť �趨��ťλ��
		start=new JButton(CommonUtil.GetIcon("images\\start.png"));start.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		setup=new JButton(CommonUtil.GetIcon("images\\setup.png"));setup.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		about=new JButton(CommonUtil.GetIcon("images\\about.png"));about.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		exit=new JButton(CommonUtil.GetIcon("images\\exit.png"));exit.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		//��Ӱ�ť
		setLayout(null);
		add(start);add(setup);add(about);add(exit);		
		//����ť�Ӽ�������ʵ������ƶ���ȥ��ɫ
		start.addMouseListener(this);start.addActionListener(this);
		setup.addMouseListener(this);setup.addActionListener(this);
		about.addMouseListener(this);about.addActionListener(this);
		exit.addMouseListener(this);exit.addActionListener(this);	
		//���ð�ťλ��
		start.setLocation(Config.GAME_WIDTH/3, Config.GAME_HEIGHT/2);
		setup.setLocation(Config.GAME_WIDTH/3, Config.GAME_HEIGHT/2+Config.MENU_GAP);
		about.setLocation(Config.GAME_WIDTH/3, Config.GAME_HEIGHT/2+2*Config.MENU_GAP);
		exit.setLocation(Config.GAME_WIDTH/3, Config.GAME_HEIGHT/2+3*Config.MENU_GAP);
		f.add(this);
		f.setUndecorated(true);
		f.setAlwaysOnTop(Config.ALWAYS_ON_TOP);
		f.setVisible(true);
	}
	public GamePanel(){
		init();
		updateUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource().equals(start)) start.setIcon(CommonUtil.GetIcon("images\\start_select.jpg"));
		if(e.getSource().equals(setup)) setup.setIcon(CommonUtil.GetIcon("images\\setup_select.jpg"));
		if(e.getSource().equals(about)) about.setIcon(CommonUtil.GetIcon("images\\about_select.jpg"));
		if(e.getSource().equals(exit)) exit.setIcon(CommonUtil.GetIcon("images\\exit_select.jpg"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource().equals(start)) {start.setIcon(CommonUtil.GetIcon("images\\start.png"));}
		if(e.getSource().equals(setup)) {setup.setIcon(CommonUtil.GetIcon("images\\setup.png"));}
		if(e.getSource().equals(about)) {about.setIcon(CommonUtil.GetIcon("images\\about.png"));}
		if(e.getSource().equals(exit)) {exit.setIcon(CommonUtil.GetIcon("images\\exit.png"));}
	}	
}