package util;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * дһ���ޱ߿򣬿����趨��С
 * ��������϶������Ҳ�������Ļ�߽�
 * ���Կ�����Ļ��Ե�Զ����ص���Ϸ���
 * ��Ϊ�����������Ϸ��
 */
public class Game_Panel extends JPanel implements MouseListener,ActionListener{
	//���ڴ�С
	public static final int GAME_WIDTH=600;
	public static final int GAME_HEIGHT=600;
	//����Ļ��Ե����ʱ¶��������
	private final int SHOW_PIXELS=5;
	//����ʱ��
	private final int SLEEP_TIME=5;
	private Toolkit t=Toolkit.getDefaultToolkit();
	//���������Զ���ʾ��������ʱ������겻�ܲ�����Ӧ������������˸
	private boolean isRolling;
	//Ϊ��ʵ��ֻ���������϶�����  ������������
	private boolean canmove=false;
	//������ҳ�水ť��С�ĳ���
	private static final int MENU_GAP=75,MENU_WIDTH=200,MENU_HEIGHT=65;
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
		g.drawImage(Image_Util.GetImage("images\\login.jpg"), 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
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
						while (frameX > SHOW_PIXELS-GAME_WIDTH) {
							frameX -= SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					//��������
					else if(direction==3){
						while (frameX < screenX-SHOW_PIXELS) {
							frameX += SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					//��������
					else if(direction==2){
						while (frameY > SHOW_PIXELS-GAME_HEIGHT) {
							frameY -= SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				//����
				else{
					//�������ҵ���
					if (direction==1) {
						while (frameX < -SHOW_PIXELS) {
							frameX += SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					//�������󵯳� 
					else if(direction==3){
						while (frameX > screenX-GAME_WIDTH+SHOW_PIXELS){
							frameX -= SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					//�������µ���
					else if(direction==2){
						while (frameY < -SHOW_PIXELS) {
							frameY += SHOW_PIXELS;
							f.setLocation(frameX, frameY);
							try {
								sleep(SLEEP_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
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
		f.setSize(GAME_WIDTH, GAME_HEIGHT);
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
				// TODO Auto-generated method stub
				if (e.getButton()==MouseEvent.BUTTON1) {
					canmove=false;
				}
			}
			//����˳� ������Ļ��Ե �Զ�����
 			public void mouseExited(MouseEvent e) {
				if (!isRolling) {
					if (f.getX() <= 0) {//��Ļ���
						setloca(true, 1);
					} else if (f.getX() >= screenX - GAME_WIDTH + SHOW_PIXELS) {//��Ļ�Ҳ�
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
					if (f.getX() >= screenX - GAME_WIDTH + SHOW_PIXELS) {//��Ļ�Ҳ�
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
					}else if (targetX > screenX - GAME_WIDTH + SHOW_PIXELS){
						if (targetY < 0){
							f.setLocation(screenX - GAME_WIDTH + SHOW_PIXELS, 0);
						} else {
							f.setLocation(screenX - GAME_WIDTH + SHOW_PIXELS, targetY);
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
		start=new JButton(Image_Util.GetIcon("images\\start.png"));start.setSize(MENU_WIDTH, MENU_HEIGHT);
		setup=new JButton(Image_Util.GetIcon("images\\setup.png"));setup.setSize(MENU_WIDTH, MENU_HEIGHT);
		about=new JButton(Image_Util.GetIcon("images\\about.png"));about.setSize(MENU_WIDTH, MENU_HEIGHT);
		exit=new JButton(Image_Util.GetIcon("images\\exit.png"));exit.setSize(MENU_WIDTH, MENU_HEIGHT);
		//��Ӱ�ť
		setLayout(null);
		add(start);add(setup);add(about);add(exit);		
		//����ť�Ӽ�������ʵ������ƶ���ȥ��ɫ
		start.addMouseListener(this);start.addActionListener(this);
		setup.addMouseListener(this);setup.addActionListener(this);
		about.addMouseListener(this);about.addActionListener(this);
		exit.addMouseListener(this);exit.addActionListener(this);	
		//���ð�ťλ��
		start.setLocation(GAME_WIDTH/3, GAME_HEIGHT/2);
		setup.setLocation(GAME_WIDTH/3, GAME_HEIGHT/2+MENU_GAP);
		about.setLocation(GAME_WIDTH/3, GAME_HEIGHT/2+2*MENU_GAP);
		exit.setLocation(GAME_WIDTH/3, GAME_HEIGHT/2+3*MENU_GAP);
		f.add(this);
		f.setUndecorated(true);
		f.setAlwaysOnTop(true);
		f.setVisible(true);
	}
	public Game_Panel(){
		init();
		updateUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
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
					   +"΢�Ż���ҡ��\r\n");
			jta.setSize(150, 150);
			jta.setEditable(false);
			JOptionPane.showConfirmDialog(this, jta, "����", JOptionPane.CLOSED_OPTION,2);
		}
		if(e.getSource().equals(exit)){
			System.exit(0);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(start)) start.setIcon(Image_Util.GetIcon("images\\start_select.jpg"));
		if(e.getSource().equals(setup)) setup.setIcon(Image_Util.GetIcon("images\\setup_select.jpg"));
		if(e.getSource().equals(about)) about.setIcon(Image_Util.GetIcon("images\\about_select.jpg"));
		if(e.getSource().equals(exit)) exit.setIcon(Image_Util.GetIcon("images\\exit_select.jpg"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(start)) {start.setIcon(Image_Util.GetIcon("images\\start.png"));}
		if(e.getSource().equals(setup)) {setup.setIcon(Image_Util.GetIcon("images\\setup.png"));}
		if(e.getSource().equals(about)) {about.setIcon(Image_Util.GetIcon("images\\about.png"));}
		if(e.getSource().equals(exit)) {exit.setIcon(Image_Util.GetIcon("images\\exit.png"));}
	}	
}