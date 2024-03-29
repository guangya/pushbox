import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;



public class IntroScene extends Scene {
	
	private int posX;             // 游戏简介场景左上角相对于屏幕左上角的X坐标
	private int posY;             // 游戏简介场景左上角相对于屏幕左上角的Y坐标
	private Image introImage;
	
	public IntroScene(IEngine engine, int width, int height){
		super(engine, width, height);
		posX = 0;
		posY = height;
		try{
			introImage = Image.createImage("/image/gameIntro.png");
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
	 * 场景逻辑处理
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		gra.drawImage(introImage, posX, posY, Graphics.TOP|Graphics.LEFT);
		
		if(0 == posY){
			// 停留一段时间
			try{
				Thread.sleep(1500);
			}catch(Exception e){}
			
			// 进入游戏主菜单界面
			engine.setGameState(IEngine.GAME_STATE_MENU);
		}else{
			// 确保纵坐标不小于0
			posY = (posY - 2 > 0) ? posY - 2 : 0;
		}
	}


	public void keyEvent(int gameAction){}
	public void menuEvent(int menu) {}
}