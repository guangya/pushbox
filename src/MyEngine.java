import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;


/**
 * 本类既是游戏画布，又扮演者游戏引擎的作用
 * 
 * @author guangya
 * @email  guangyait@gmail.com
 *
 */

public class MyEngine extends GameCanvas implements IEngine{

	// 当前场景
	private Scene curScene;
	// 前一个场景，用于场景切换
	private Scene preScene;
	// 游戏逻辑循环
	private boolean RUN;
	// 线程刷新等待时间
	private int sleepTime;
	// 系统画笔
	private Graphics sysGra;
	// 游戏画布尺寸
	private int canvasWidth;
	private int canvasHeight;
	// 游戏背景、地图、玩家等图片的集合
	private Image images;
	// 系统背景
	private TiledLayer sysBackground;
	// 游戏当前的运行状态
	private int gameState;
	// 游戏当前所在的关卡
	private int gameLevel;
	
	
	public MyEngine() {
		// 我们要监听左右菜单的按键消息以实现自定义的左右菜单
		super(false);
		// 游戏进入全屏
		this.setFullScreenMode(true);
		
		RUN = false;
		sleepTime = 100;
		canvasWidth = this.getWidth();
		canvasHeight = this.getHeight();
		
		// 获取系统画笔
		sysGra = this.getGraphics();
		// 设定系统默认字体
		sysGra.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_LARGE));
		
		gameLevel = 1;
		
		try{
			images = Image.createImage("/image/map&player&box.png");
		}catch(Exception e){}
		
		// 设定游戏背景
		sysBackground = new TiledLayer(15, 20, images, 16, 16);
		sysBackground.fillCells(0, 0, 15, 20, 1);
		
		// 设置游戏启动时的第一个界面
		gameState = GAME_STATE_INTRO;
		stateChange();
	}	
	
	/**
	 * 处理键盘释放按键消息
	 */
	protected void keyReleased(int keyCode){
		// -6和-7是sun wtk2.5.2中实测的左菜单键和右菜单键键码，其他平台暂未考虑
		if(keyCode == -7){
			curScene.menuEvent(MENU_RIGHT);
		}else if(keyCode == -6){
			curScene.menuEvent(MENU_LEFT);
		}else{			
			curScene.keyEvent(getGameAction(keyCode));
		}		
	}
	
	/**
	 * 根据游戏当前的状态，切换到相应的界面
	 */
	private void stateChange(){
		switch(gameState){
		
		// 游戏主菜单界面
		case GAME_STATE_MENU:
			curScene = new MainMenuScene(this, canvasWidth, canvasHeight);
			break;
			
		// 游戏运行主界面
		case GAME_STATE_RUN:
			curScene = new RunningScene(this, canvasWidth, canvasHeight);
			break;
			
		// 游戏开始动画界面
		case GAME_STATE_INTRO:
			curScene = new IntroScene(this, canvasWidth, canvasHeight);
			break;
			
		// 游戏关于界面
		case GAME_STATE_ABOUT:
			InfoScene aboutScene = new InfoScene(this, canvasWidth, canvasHeight);
			try{
				aboutScene.settextImage(Image.createImage("/image/gameAbout.png"));
			}catch(Exception e){}
			
			curScene = aboutScene;
			break;
			
		// 游戏操作说明界面
		case GAME_STATE_OPERATION:
			InfoScene operationScene = new InfoScene(this, canvasWidth, canvasHeight);
			try{
				operationScene.settextImage(Image.createImage("/image/gameOperation.png"));
			}catch(Exception e){}
			
			curScene = operationScene;
			break;
			
		// 退出游戏
		case GAME_STATE_EXIT:
			RUN = false;
			break;
			
		// 游戏暂停
		case GAME_STATE_PAUSE:
			break;
			
		// 游戏开始
		case GAME_STATE_START:
			break;
		}
	}
	
	/**
	 * 游戏开始
	 */
	public void start(){
		RUN = true;
		while(RUN){
			// 绘制系统背景
			sysBackground.paint(sysGra);
			// 绘制当前的场景
			curScene.process(sysGra);
			// 刷新显示
			this.flushGraphics();
			
			try{
				Thread.sleep(sleepTime);
			}catch(Exception e){}
		}
	}
	
	/**
	 * 游戏暂停
	 */
	public void puase(){}
	
	/**
	 * 游戏停止
	 */
	public void stop(){}
	
	/**
	 * 设置游戏的运行状态
	 * 
	 * 触发GameStateChange事件
	 * 
	 * @param state 游戏的新状态
	 */
	public void setGameState(int state){
		gameState = state;
		this.stateChange();
	}
	
	/**
	 * 游戏音效开关
	 * 
	 * 游戏音效设置为独立子线程，可以通过kill thread的方法禁用音效
	 * 
	 * @param isOn
	 */
	public void setSoundSwitch(boolean isOn){}

	/**
	 * 设置游戏当前所在的关卡
	 */
	public void setCurrentLevel(int level) {
		gameLevel = level;
	}
	
	/**
	 * 获得游戏当前的运行状态
	 * 
	 * @return
	 */
	public int getGameState(){ 
		return gameState;
	}
	
	/**
	 * 获取游戏当前所在的关卡
	 */
	public int getCurrentLevel(){
		return gameLevel;
	}
	
	/**
	 * 获得游戏的总关卡数
	 */
	public int getTotalLevels(){
		return gameLevel;
	}
}
