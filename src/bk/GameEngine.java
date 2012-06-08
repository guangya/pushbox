package bk;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;


public class GameEngine
{
	// 游戏状态标识
	public static final int GAME_STATE_EXIT  = 0;
	public static final int GAME_STATE_START = 1;
	public static final int GAME_STATE_MENU  = 2;
	public static final int GAME_STATE_RUN   = 4;	
	public static final int GAME_STATE_WIN   = 8;
	
	// 游戏总关卡数（后续计划的处理方法为：地图目录内地图数即为游戏总关卡数）
	private static final int gameTotalLevel = 3;
	
	// 游戏运行状态参数
	private static int gameState = GAME_STATE_START;
	private static int gameLevel = 1;
	private static int gameScore = 0;
	
	
	private boolean RUN = true;	
	private Display dis;
	private MIDlet midlet;

	
	// 游戏场景
	StartScene startScene;
	MenuScene menuScene;
	RunScene runScene;
	
	private GameEngine(MIDlet midlet2){
		this.midlet = midlet2;
		dis = Display.getDisplay(midlet2);
	}
	
	/**
	 * 获得游戏引擎实例（单例模式）
	 * 
	 * @param midlet
	 * @return
	 */
	public static GameEngine getInstance(MIDlet midlet){
		GameEngine engine = new GameEngine(midlet);
		return engine;
	}
	
	public static void setState(int state){
		gameState = state;
	}
	public static int getState(){
		return gameState;
	}
	
	public static void setLevel(int level){
		gameLevel = level;
	}
	public static int getLevel(){
		return gameLevel;
	}
	
	public static void setScore(int score){
		gameScore = score;
	}
	public static int getScore(){
		return gameScore;
	}
	
	public static int getTotalLevel(){
		return gameTotalLevel;
	}
	
	public void startEngine(){
		while(RUN){
		    switch(gameState){
		        case GAME_STATE_START:
		        	gameStart();
		        	break;
		        case GAME_STATE_MENU:
		        	gameMenu();
		        	break;
		        case GAME_STATE_RUN:
		        	gameRunning();
		        	break;
		        case GAME_STATE_WIN:
		        	gameWin();
		        	break;
	    	    case GAME_STATE_EXIT:
	    	    default:
	    	    	gameExit();
	    	    	break;
		    }
		    
		    try{
		    	Thread.sleep(100);
		    }catch(Exception e){}
		}
	}
	
	public void gameStart(){
		if(null == startScene || !startScene.isAlive()){
			startScene = new StartScene();
		}
		
		// 显示游戏开始动画
		if(!startScene.isShown()){
			dis.setCurrent(startScene);
		}
		startScene.show();
		
		// 进入游戏关卡选择和系统设置界面
		setState(GAME_STATE_MENU);
	}
	
	public void gameMenu(){
		if(null == menuScene || !menuScene.isAlive()){
			menuScene = new MenuScene();
		}
		
		// 显示游戏开始动画
		if(!menuScene.isShown()){
			dis.setCurrent(menuScene);
		}
		menuScene.show();
	}
	
	public void gameRunning(){
		if(null == runScene || !runScene.isAlive()){
			runScene = new RunScene();
		}
		
		// 显示游戏开始动画
		if(!runScene.isShown()){
			dis.setCurrent(runScene);
		}
		runScene.show();
	}
	
	public void gameWin(){
	}
	
	public void gameExit(){
    	RUN = false;
    	midlet.notifyDestroyed();
	}
}
