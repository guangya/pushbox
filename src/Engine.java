import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class Engine extends GameCanvas{

	// 游戏运行状态标识
	public static final int GAME_STATE_EXIT      = 0;
	public static final int GAME_STATE_PAUSE     = 1;
	public static final int GAME_STATE_START     = 2;
	public static final int GAME_STATE_MENU      = 3;
	public static final int GAME_STATE_RUN       = 4;	
	public static final int GAME_STATE_WIN       = 5;
	public static final int GAME_STATE_INTRO     = 6;
	public static final int GAME_STATE_ABOUT     = 7;
	public static final int GAME_STATE_OPERATION = 8;
	// 左右菜单键对应的键码
	public static final int MENU_LEFT  = -6;
	public static final int MENU_RIGHT = -7;
	// 控制游戏主循环
	private boolean RUN;
	// 游戏场景刷新等待时间
	private int sleepTime;
	// 游戏各场景中使用的画笔
	private Graphics gra;
	// 画布尺寸
	private int canvasWidth;
	private int canvasHeight;
	// 游戏当前的运行状态，控制游戏场景的显示
	private int gameState;
	// 游戏的当前关卡
	private int gameLevel;
	// 场景菜单使用的图片
	private Image menusImage;
	// 游戏开始动画
	private IntroScene introScene;
	// 游戏主菜单
	private MainMenuScene mainMenuScene;
	// 关于界面
	private Info aboutScene;
	// 操作说明界面
	private Info operationScene;
	// 游戏运行主界面
	private RunningScene runningScene;
	// 游戏地图、玩家和箱子所使用的图片的汇总
	private Image imges;
	// 系统背景图层
	private TiledLayer background;
	
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
		
		// 设定系统使用的字体
		Font font = Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_LARGE);
		gra.setFont(font);
		
		background = new TiledLayer(15, 20, imges, 16, 16);
		background.fillCells(0, 0, 15, 20, 1);
	}
	
	/**
	 * 根据游戏所处状态的不同，启用不同的逻辑处理模块
	 */
	private void process(){
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
	 * 设置系统声音开关
	 * 
	 * @param isOn 如果设置为true，则开启系统声效
	 */
	public void setSoundSwitch(boolean isOn){
		
	}
	
	/**
	 * 判断当前的按键是不是菜单键
	 * 
	 * 为实现自定义风格的左右菜单，我们监听键盘左右命令键，并行处理该按键事件
	 * 
	 * 未考虑平台兼容性问题，在sun公司提供的wtk2.5.2下测试通过
	 * 
	 * @param keyCode
	 * @return
	 */
	private boolean isMenuKey(int keyCode){
		return (MENU_LEFT == keyCode || MENU_RIGHT == keyCode);
	}
	
	/**
	 * 初始化游戏简介场景（开始动画）
	 */
	private void initIntroScene(){
		if(null == introScene){
			introScene = new IntroScene(this, canvasWidth, canvasHeight);
		}
	}
	
	/**
	 * 初始化主菜单
	 */
	private void initMainMenuScene(){
		if(null == mainMenuScene){
			mainMenuScene = new MainMenuScene(this, canvasWidth, canvasHeight);
		}
	}
	
	/**
	 * 初始化操作说明场景
	 */
	private void initOperationScene(){
		if(null == operationScene){
			operationScene = new Info(this, canvasWidth, canvasHeight);
			try{
				operationScene.setMsg(Image.createImage("/image/gameOperation.png"));
				operationScene.setRightMenu(Image.createImage(menusImage, 0, 0, 55, 13, Sprite.TRANS_NONE), GAME_STATE_MENU);
			}catch(Exception e){}
		}
	}
	
	/**
	 * 初始化关于场景
	 */
	private void initAboutScene(){
		if(null == aboutScene){
			aboutScene = new Info(this, canvasWidth, canvasHeight);
			try{
				aboutScene.setMsg(Image.createImage("/image/gameAbout.png"));
				aboutScene.setRightMenu(Image.createImage(menusImage, 0, 0, 55, 13, Sprite.TRANS_NONE), GAME_STATE_MENU);
			}catch(Exception e){}
		}		
	}
	
	/**
	 * 初始化游戏主场景
	 */
	private void initRunningScene(){
		if(null == runningScene){
			runningScene = new RunningScene(this, canvasWidth, canvasHeight);
		}
	}
}
