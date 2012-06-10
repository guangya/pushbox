import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class InfoScene extends Scene {

	private Image textImage;
	private Menu rightMenu;
	
	public InfoScene(IEngine engine, int width, int height){
		super(engine, width, height);
		
		rightMenu = new Menu("");
		rightMenu.setBoundEvent(IEngine.GAME_STATE_MENU);
		
		try{
			Image inlineMenu = Image.createImage("/image/inlineMenus.png");
			int menuWidth = inlineMenu.getWidth();
			int menuHeight = inlineMenu.getHeight() / 4;
			rightMenu.setBackground(Image.createImage(inlineMenu, 0, 0, menuWidth, menuHeight, Sprite.TRANS_NONE));
			rightMenu.setPosition(canvasWidth - menuWidth / 2, canvasHeight - menuHeight / 2);
		}catch(Exception e){}		
	}
	
	/**
	 * 设置待显示的信息
	 * 
	 * @param textImage 包含待显示信息的图片
	 */
	public void settextImage(Image textImage){
		this.textImage = textImage;
	}

	/**
	 * 场景逻辑处理
	 */
	public void process(Graphics gra) {
		// 如果设置了待显示的信息的图片，则将其绘制到显示设备上
		if(null != textImage){
			gra.drawImage(textImage, posX, posY, Graphics.LEFT|Graphics.TOP);
		}
		
		// 绘制右键菜单
		rightMenu.paint(gra);
	}

	/**
	 * 键盘事件处理
	 */
	public void keyEvent(int gameAction) {	}
	
	/**
	 * 菜单键事件处理
	 */
	public void menuEvent(int menu) {
		switch(menu){
		case IEngine.MENU_RIGHT:
			engine.setGameState(rightMenu.getBoundEvent());
			break;
		}
	}
}
