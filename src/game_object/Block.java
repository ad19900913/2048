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
	
	@Override
	public boolean equals(Object obj) {
		Block temp=(Block) obj;
		if(x==temp.x&&y==temp.y){
			return true;
		}
		return false;
	}

	public Color getColor(int value){
		switch (value) {
		case 0:
			return new Color(204, 192, 179);
		case 2:
			return new Color(238, 228, 218);
		case 4:
			return new Color(237, 224, 200);
		case 8:
			return new Color(242, 177, 121);
		case 16:
			return new Color(245, 149, 99);
		case 32:
			return new Color(246, 124, 95);
		case 64:
			return new Color(246, 94, 59);
		case 128:
			return new Color(237, 207, 114);
		case 256:
			return new Color(237, 204, 97);
		case 512:
			return new Color(204, 192, 179);
		case 1024:
			return new Color(238, 228, 218);
		case 2048:
			return new Color(237, 224, 200);
		case 4096:
			return new Color(242, 177, 121);
		}
		return new Color(204, 192, 179);
	}

	public int getValueX(){
		if(value<10){
			return (gap*x+width*(x-1))+(width/2)-20;
		}else if (value>10&&value<100){
			return (gap*x+width*(x-1))+(width/2)-35;
		}else if(value>1000){
			return (gap*x+width*(x-1))+(width/2)-65;
		}else{
			return (gap*x+width*(x-1))+(width/2)-50;
		}
	}
	
	public void draw(Graphics g){
		g.setColor(getColor(value));
		if(value==0){
			g.fill3DRect((gap*x+width*(x-1)), (gap*y+width*(y-1)), width, height, true);
		}else{
			g.fill3DRect((gap*x+width*(x-1)), (gap*y+width*(y-1)), width, height, true);
			g.setColor(new Color(119, 110, 101));
			g.setFont(new Font("微软雅黑", Font.BOLD, 50));
			int valueX=getValueX();
			int valueY=(gap*y+width*(y-1))+height/2+20;
			g.drawString(Integer.toString(value), valueX, valueY);
		}
	}
}
