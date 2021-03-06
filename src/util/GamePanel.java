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
 * 写一个无边框，可以设定大小
 * 可以鼠标拖动，并且不超出屏幕边界
 * 可以靠近屏幕边缘自动隐藏的游戏面板
 * 作为父类给其他游戏用
 */
public class GamePanel extends JPanel implements MouseListener,ActionListener{

	private Toolkit t=Toolkit.getDefaultToolkit();
	//窗口正在自动显示或者隐藏时对于鼠标不能产生响应，否则会出现闪烁
	private boolean isRolling;
	//为了实现只能鼠标左键拖动窗口  加入下面属性
	private boolean canmove=false;

	//定义按钮组件
	protected JButton start;
	protected JButton setup;
	protected JButton about;
	protected JButton exit;
	private JFrame f;
	private int screenX=t.getScreenSize().width;
	private int screenY=t.getScreenSize().height;
	//记录点击时鼠标位置 拖动要用
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
	/*设置窗体位置  要有隐藏或者显示的方向 
	 * hiden=true 是隐藏
	 * diection=1：左  2：上  3：右
	 * */
	public void setloca(final boolean hiden,final int direction){
		Thread t = new Thread(){
			int frameY=f.getY();
			int frameX=f.getX();
			public void run() {
				isRolling=true;//表示窗口正在自动适应位置
				//隐藏
				if (hiden) {
					//向左隐藏
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
					//向右隐藏
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
					//向上隐藏
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
				//弹出
				else{
					//从左向右弹出
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
					//从右向左弹出 
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
					//从上向下弹出
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
		//实现窗口自动隐藏
		f.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mouseX = e.getX();
					mouseY = e.getY();
					canmove=true;
				}
			}
			// 只有点击鼠标左键才可以移动窗口
			public void mouseReleased(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					canmove=false;
				}
			}
			//鼠标退出 且在屏幕边缘 自动隐藏
 			public void mouseExited(MouseEvent e) {
				if (!isRolling) {
					if (f.getX() <= 0) {//屏幕左侧
						setloca(true, 1);
					} else if (f.getX() >= screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS) {//屏幕右侧
						setloca(true, 3);
					} else if (f.getY() <= 0) {//屏幕上面
						setloca(true, 2);
					}
				}
			}
			//鼠标进入 自动出现
			public void mouseEntered(MouseEvent e) {
				if (!isRolling) {
					if (f.getX() <= 0) {//屏幕左侧
						setloca(false, 1);
					}
					if (f.getX() >= screenX - Config.GAME_WIDTH + Config.SHOW_PIXELS) {//屏幕右侧
						setloca(false, 3);
					}
					if (f.getY() <= 0) {//屏幕上面
						setloca(false, 2);
					}
				}	
			}
		});
		//实现窗口自由拖动
		f.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(final MouseEvent e){
				if (canmove) {
					//用来表示 窗口要移动到的目标坐标
					int targetX = f.getX() + (e.getX() - mouseX);
					int targetY = f.getY() + (e.getY() - mouseY);
					//边界判断 
					if (targetX < 0) {
						//左上角
						if (targetY < 0) {
							f.setLocation(0, 0);
						}
						//左侧   下面以此类推
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
					//三个边界都不在的时候才可以自由拖动
					else {
						f.setLocation(targetX, targetY);
					}
				}	
			}	
		});
		//初始化按钮 设定按钮位置
		start=new JButton(CommonUtil.GetIcon("images\\start.png"));start.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		setup=new JButton(CommonUtil.GetIcon("images\\setup.png"));setup.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		about=new JButton(CommonUtil.GetIcon("images\\about.png"));about.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		exit=new JButton(CommonUtil.GetIcon("images\\exit.png"));exit.setSize(Config.MENU_WIDTH, Config.MENU_HEIGHT);
		//添加按钮
		setLayout(null);
		add(start);add(setup);add(about);add(exit);		
		//给按钮加监听器，实现鼠标移动上去变色
		start.addMouseListener(this);start.addActionListener(this);
		setup.addMouseListener(this);setup.addActionListener(this);
		about.addMouseListener(this);about.addActionListener(this);
		exit.addMouseListener(this);exit.addActionListener(this);	
		//设置按钮位置
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