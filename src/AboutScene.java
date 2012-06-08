import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class AboutScene{
	
	private int width;
	private int height;
	private int posX;             //场景左上角相对于屏幕左上角的X坐标
	private int posY;             //场景左上角相对于屏幕左上角的Y坐标
	private Engine engine;
	private Image aboutImage;
	private Image menusImage;
	
	
	public AboutScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = 0;
		try{
			aboutImage = Image.createImage("/image/gameAbout.png");
			menusImage = Image.createImage("/image/menus.png");
		}catch(Exception e){}
	}
	
	/**
	 * 设置菜单界面的左上角相对于屏幕的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * 按键消息处理
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.UP:
			break;
		case GameCanvas.DOWN:
			break;
		case GameCanvas.FIRE:
			break;
		}
	}
	
	/**
	 * 场景逻辑处理
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		
//		gra.drawImage(selectionImage, 64, 40, Graphics.LEFT|Graphics.TOP);
	}
}