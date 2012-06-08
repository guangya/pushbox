import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;



class Info{

	// 游戏引擎
	private Engine engine;
	// 游戏画布尺寸
	private int canvasWidth;
	private int canvasHeight;
	// 当前场景相对于屏幕左上角的坐标
	private int posX;
	private int posY;
	// 要显示的信息图
	private Image imgMsg;
	// 左右菜单使用的图片
	private Image imgLeftMenu;
	private Image imgRightMenu;
	// 左右菜单绑定的游戏状态
	private int leftMenuBoundState;
	private int rightMenuBoundState;
	// 左右菜单的尺寸
	private int leftMenuHeight;
	private int rightMenuWidth;
	private int rightMenuHeight;
	
	public Info(Engine engine, int width, int height){
		this.engine = engine;
		this.canvasWidth = width;
		this.canvasHeight = height;
		
		posX = 0;
		posY = 0;
	}
	
	/**
	 * 设置当前场景要显示的消息
	 * 
	 * @param imgMsg
	 */
	public void setMsg(Image imgMsg){
		this.imgMsg = imgMsg;
	}
	
	/**
	 * 设置界面左菜单参数
	 * 
	 * @param imgMenu   左菜单显示的信息图片
	 * @param boundGameState 左菜单绑定的游戏状态
	 */
	public void setLeftMenu(Image imgMenu, int boundGameState){
		imgLeftMenu = imgMenu;
		leftMenuBoundState = boundGameState;
		leftMenuHeight = imgMenu.getHeight();
	}
	
	/**
	 * 设置界面右菜单参数
	 * 
	 * @param imgMenu   菜单显示的信息图片
	 * @param boundGameState 菜单绑定的游戏状态
	 */
	public void setRightMenu(Image imgMenu, int boundGameState){
		imgRightMenu = imgMenu;
		rightMenuBoundState = boundGameState;
		rightMenuWidth = imgMenu.getWidth();
		rightMenuHeight = imgMenu.getHeight();
	}
	
	/**
	 * 设置场景在画布中相对于画布左上角的坐标
	 * 
	 * @param x 场景左上角相对于画布左上角的X坐标
	 * @param y 场景左上角相对于画布左上角的Y坐标
	 */
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	/**
	 * 处理当前场景的逻辑及场景绘制
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		// 绘制主要信息
		if(null != imgMsg){
			gra.drawImage(imgMsg, posX, posY, Graphics.LEFT|Graphics.TOP);
		}
		
		// 绘制左菜单
		if(null != imgLeftMenu){
			gra.drawImage(imgLeftMenu, posX, posY + canvasHeight - leftMenuHeight, Graphics.LEFT|Graphics.TOP);
		}
		
		// 绘制右菜单
		if(null != imgRightMenu){
			gra.drawImage(imgRightMenu, posX + canvasWidth - rightMenuWidth, posY + canvasHeight - rightMenuHeight, Graphics.LEFT|Graphics.TOP);
		}
	}
	

	/**
	 * 处理当前场景的按键事件
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case Engine.MENU_LEFT:
			if(null != imgLeftMenu){
				engine.setGameState(leftMenuBoundState);
			}
			break;
		case Engine.MENU_RIGHT:
			if(null != imgRightMenu){
				engine.setGameState(rightMenuBoundState);
			}
			break;
		}
	}
}