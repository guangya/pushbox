import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class Engine extends GameCanvas{

	public static final int GAME_STATE_EXIT      = 0;
	public static final int GAME_STATE_PAUSE     = 1;
	public static final int GAME_STATE_START     = 2;
	public static final int GAME_STATE_MENU      = 3;
	public static final int GAME_STATE_RUN       = 4;	
	public static final int GAME_STATE_WIN       = 5;
	public static final int GAME_STATE_INTRO     = 6;
	public static final int GAME_STATE_ABOUT     = 7;
	public static final int GAME_STATE_OPERATION = 8;
	
	public static final int MENU_LEFT  = -6;
	public static final int MENU_RIGHT = -7;
	
	private boolean RUN;
	private int sleepTime;
	private Graphics gra;
	private int canvasWidth;
	private int canvasHeight;	
	private int gameState;
	private int gameLevel;	
	private Image menusImage;	
	private IntroScene introScene;
	private MainMenuScene mainMenuScene;
	private Info aboutScene;
	private Info operationScene;
	private RunningScene runningScene;	
	private Image imges;
	private TiledLayer background; // 背景图层
	
	public Engine(){
		super(false);
		this.setFullScreenMode(true);
		RUN = true;
		sleepTime = 100;
		gra = this.getGraphics();
		canvasWidth = this.getWidth();
		canvasHeight = this.getHeight();
		
		gameState = GAME_STATE_MENU;
		gameLevel = 1;
		
		try{
			menusImage = Image.createImage("/image/inlineMenus.png");
			imges = Image.createImage("/image/map&player&box.png");
		}catch(Exception e){}
		
		// 设置系统使用的字体
		Font font = Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_LARGE);
		gra.setFont(font);
		
		background = new TiledLayer(15, 20, imges, 16, 16);
		background.fillCells(0, 0, 15, 20, 1);
	}
	
	public void process(){
		background.paint(gra);
		
		switch(gameState){
		case GAME_STATE_RUN:
			initRunningScene();
			runningScene.process(gra);
			break;
		case GAME_STATE_INTRO:
			initIntroScene();
			introScene.process(gra);
			break;
		case GAME_STATE_MENU:
			initMainMenuScene();
			mainMenuScene.process(gra);
			break;
		case GAME_STATE_ABOUT:
			initAboutScene();
			aboutScene.process(gra);
			break;
		case GAME_STATE_OPERATION:
			initOperationScene();
			operationScene.process(gra);
			break;
		case GAME_STATE_EXIT:
			RUN = false;
			break;
		}
	}
	
	/**
	 * 启动游戏引擎，使游戏进入运行状态
	 */
	public void run(){
		RUN = true;
		while(RUN){
			process();
			this.flushGraphics();
			try{
				Thread.sleep(sleepTime);
			}catch(Exception e){}
		}
	}
	
	/**
	 * 根据游戏所在的状态，将按键消息交给相应的场景单元处理
	 * 
	 * @param keyCode 键码
	 * @return void
	 */
	protected void keyReleased(int keyCode){
		int gameAction = isMenuKey(keyCode) ? keyCode : getGameAction(keyCode);
		
		switch(gameState){
		case GAME_STATE_RUN:
			runningScene.keyEvent(gameAction);
			break;
		case GAME_STATE_INTRO:
			introScene.keyEvent(gameAction);
			break;
		case GAME_STATE_MENU:
			mainMenuScene.keyEvent(gameAction);
			break;
		case GAME_STATE_ABOUT:
			aboutScene.keyEvent(gameAction);
			break;
		case GAME_STATE_OPERATION:
			operationScene.keyEvent(gameAction);
			break;
		}
	}
	
	/**
	 * 设置游戏界面刷新等待时间
	 * 
	 * @param sleepTime 单位：millisecond
	 */
	public void setSleepTime(int sleepTime){
		this.sleepTime = sleepTime;
	}
	
	/**
	 * 改变游戏状态
	 * 
	 * @param state
	 */
	public void setGameState(int state){
		this.gameState = state;
	}
	
	/**
	 * 获得游戏当前所处的状态
	 * 
	 * 状态标识静态常量见本类成员属性部分
	 * 
	 * @return
	 */
	public int getGameState(){
		return gameState;
	}
	
	/**
	 * 修改游戏当前关卡
	 * 
	 * @param level
	 */
	public void setGameLevel(int level){
		gameLevel = level;
	}
	
	/**
	 * 获取游戏当前所处的关卡
	 * 
	 * @return
	 */
	public int getGameLevel(){
		return gameLevel;
	}
	
	/**
	 * 系统声音开关
	 * 
	 * @param isOn 设置为true时，播放声音，否则，关闭游戏所有音效
	 */
	public void setSoundSwitch(boolean isOn){
		
	}
	
	/**
	 * 判断当前的按键是不是菜单键
	 * 
	 * 未解决平台兼容性问题，仅在sun手机模拟器中测试通过
	 * 
	 * @param keyCode 键码
	 * @return
	 */
	private boolean isMenuKey(int keyCode){
		return (MENU_LEFT == keyCode || MENU_RIGHT == keyCode);
	}
	
	private void initIntroScene(){
		if(null == introScene){
			introScene = new IntroScene(this, canvasWidth, canvasHeight);
		}
	}
	
	private void initMainMenuScene(){
		if(null == mainMenuScene){
			mainMenuScene = new MainMenuScene(this, canvasWidth, canvasHeight);
		}
	}
	
	private void initOperationScene(){
		if(null == operationScene){
			operationScene = new Info(this, canvasWidth, canvasHeight);
			try{
				operationScene.setMsg(Image.createImage("/image/gameOperation.png"));
				operationScene.setRightMenu(Image.createImage(menusImage, 0, 0, 55, 13, Sprite.TRANS_NONE), GAME_STATE_MENU);
			}catch(Exception e){}
		}
	}
	
	private void initAboutScene(){
		if(null == aboutScene){
			aboutScene = new Info(this, canvasWidth, canvasHeight);
			try{
				aboutScene.setMsg(Image.createImage("/image/gameAbout.png"));
				aboutScene.setRightMenu(Image.createImage(menusImage, 0, 0, 55, 13, Sprite.TRANS_NONE), GAME_STATE_MENU);
			}catch(Exception e){}
		}		
	}
	
	private void initRunningScene(){
		if(null == runningScene){
			runningScene = new RunningScene(this, canvasWidth, canvasHeight);
		}
	}
}
