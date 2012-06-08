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
	private TiledLayer background; // ����ͼ��
	
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
		
		// ����ϵͳʹ�õ�����
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
	 * ������Ϸ���棬ʹ��Ϸ��������״̬
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
	 * ������Ϸ���ڵ�״̬����������Ϣ������Ӧ�ĳ�����Ԫ����
	 * 
	 * @param keyCode ����
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
	 * ������Ϸ����ˢ�µȴ�ʱ��
	 * 
	 * @param sleepTime ��λ��millisecond
	 */
	public void setSleepTime(int sleepTime){
		this.sleepTime = sleepTime;
	}
	
	/**
	 * �ı���Ϸ״̬
	 * 
	 * @param state
	 */
	public void setGameState(int state){
		this.gameState = state;
	}
	
	/**
	 * �����Ϸ��ǰ������״̬
	 * 
	 * ״̬��ʶ��̬�����������Ա���Բ���
	 * 
	 * @return
	 */
	public int getGameState(){
		return gameState;
	}
	
	/**
	 * �޸���Ϸ��ǰ�ؿ�
	 * 
	 * @param level
	 */
	public void setGameLevel(int level){
		gameLevel = level;
	}
	
	/**
	 * ��ȡ��Ϸ��ǰ�����Ĺؿ�
	 * 
	 * @return
	 */
	public int getGameLevel(){
		return gameLevel;
	}
	
	/**
	 * ϵͳ��������
	 * 
	 * @param isOn ����Ϊtrueʱ���������������򣬹ر���Ϸ������Ч
	 */
	public void setSoundSwitch(boolean isOn){
		
	}
	
	/**
	 * �жϵ�ǰ�İ����ǲ��ǲ˵���
	 * 
	 * δ���ƽ̨���������⣬����sun�ֻ�ģ�����в���ͨ��
	 * 
	 * @param keyCode ����
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
