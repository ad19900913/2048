package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Config;
import util.Recorder;

public class Stats_Frame extends JFrame{
	
	Panel_2048 p2;
	Stats_Panel stats_Panel;

	class Stats_Panel extends JPanel implements Runnable{

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
			g.setColor(new Color(187, 173, 160));
			g.fillRect(0, 0, Config.GAME_WIDTH, Config.GAME_HEIGHT/10);
			
			g.setColor(Color.black);
			g.setFont(new Font("微软雅黑",Font.BOLD,20));
			
			g.drawString("尝试移动次数:"+Recorder.MOVE_TIMES+"     有效移动次数:"+Recorder.USEFULL_MOVE_TIMES+"\t     分数:"+Recorder.SCORES+"\t     用时:"+Recorder.PLAY_TIMES+"秒", 0, 30);
		}

		@Override
		public void run() {
			while (true) {
				// TODO Auto-generated method stub
				try {
					if (p2.playing) {
						Date date =new Date();
						int play_time = (int) ((date.getTime() - p2.startDate
								.getTime()) / 1000);
						Recorder.PLAY_TIMES = play_time;
					}
					if (Stats_Frame.this.isVisible()) {
						Stats_Frame.this.setLocation(p2.getLocationOnScreen().x,
								p2.getLocationOnScreen().y - Config.GAME_HEIGHT/10);
						repaint();
						
						p2.requestFocusInWindow();
					}
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public Stats_Frame(Panel_2048 panel_2048){
		p2=panel_2048;
		setSize(Config.GAME_WIDTH, Config.GAME_HEIGHT/10);
		setLocationRelativeTo(p2);
		setUndecorated(true);
		stats_Panel=new Stats_Panel();
		add(stats_Panel);
		setVisible(true);
	}
	
	
	
}
