import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;



public class OperationScene{
	
	private int width;
	private int height;
	private int posX;             //游戏简介场景左上角相对于屏幕左上角的X坐标
	private int posY;             //游戏简介场景左上角相对于屏幕左上角的Y坐标
	private Engine engine;
	
	private Image operationImage;
	private Image menusImage;
	
	public OperationScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = 0;
		initialize();
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
	
	protected void initialize(){
		try{
			operationImage = Image.createImage("/image/gameOperation.png");
			menusImage = Image.createImage("/image/menus.png");
		}catch(Exception e){}
	}
	
	/**
	 * 按键消息处理
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.RIGHT:
			// 返回游戏主菜单
			engine.setGameState(Engine.GAME_STATE_MENU);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 场景逻辑处理
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		gra.drawImage(operationImage, posX, posY, Graphics.LEFT|Graphics.TOP);
          //drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) 
		gra.drawRegion(menusImage, 0, 0, 67, 15, Sprite.TRANS_NONE, 0, 0, Graphics.LEFT|Graphics.TOP);
	}
}