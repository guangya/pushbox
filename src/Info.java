import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


// 1、待显示的图片（设置为独立接口，方便日后增加显示文字信息的接口）；
// 2、返回主菜单的按钮（可选，提供设置接口，如果未设置，使用普通文本代替。）；
// 3、游戏引擎（以便于改变游戏运行状态）；
// 4、画布尺寸；
// 5、当前场景在游戏画布中的坐标（可选，提供设置接口，如果未设置，默认使用（0，0）

class Info{

	private Engine engine;              // 游戏引擎
	private int canvasWidth;            // 游戏画布的宽
	private int canvasHeight;           // 游戏画布的高
	private int posX;                   // 当前场景左上角相对于画布左上角的X坐标
	private int posY;                   // 当前场景左上角相对于画布左上角的Y坐标
	
	private Image imgMsg;               // 界面主体显示的信息
	private Image imgLeftMenu;          // 左菜单使用的图象
	private Image imgRightMenu;         // 右菜单使用的图象
	private int leftMenuBoundState;     // 左菜单绑定的游戏状态
	private int rightMenuBoundState;	// 右菜单绑定的游戏状态
//	private int leftMenuWidth;          // 左菜单的宽度
	private int leftMenuHeight;         // 左菜单的高度
	private int rightMenuWidth;         // 右菜单的宽度
	private int rightMenuHeight;        // 右菜单的高度
	
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
//		leftMenuWidth = imgMenu.getWidth();
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