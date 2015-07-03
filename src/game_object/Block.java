package game_object;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import util.Game_Panel;

public class Block {
	public int x;//纵横坐标采用(1,1)(2,2)(3.3)(4.4)的形式
	public int y;
	public int value;//方块的数值
	
	private int gap=10;//方块之间的间隔
	private int width=(Game_Panel.GAME_WIDTH-5*gap)/4;//方块的大小
	private int height=(Game_Panel.GAME_HEIGHT-5*gap)/4;
	
	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g){
		if(value==0){
			g.setColor(new Color(204, 192, 179));
			g.fill3DRect((gap*x+width*(x-1)), (gap*y+width*(y-1)), width, height, true);
		}else{
			g.setColor(new Color(238, 228, 218));
			g.fill3DRect((gap*x+width*(x-1)), (gap*y+width*(y-1)), width, height, true);
			g.setColor(new Color(119, 110, 101));
			g.setFont(new Font("微软雅黑", Font.BOLD, 50));
			g.drawString(Integer.toString(value), (gap*x+width*(x-1))+width/3,  (int) ((gap*y+width*(y-1))+height/1.5));
		}
	}
}
