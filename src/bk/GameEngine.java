package bk;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;


public class GameEngine
{
	// ��Ϸ״̬��ʶ
	public static final int GAME_STATE_EXIT  = 0;
	public static final int GAME_STATE_START = 1;
	public static final int GAME_STATE_MENU  = 2;
	public static final int GAME_STATE_RUN   = 4;	
	public static final int GAME_STATE_WIN   = 8;
	
	// ��Ϸ�ܹؿ����������ƻ��Ĵ�����Ϊ����ͼĿ¼�ڵ�ͼ����Ϊ��Ϸ�ܹؿ�����
	private static final int gameTotalLevel = 3;
	
	// ��Ϸ����״̬����
	private static int gameState = GAME_STATE_START;
	private static int gameLevel = 1;
	private static int gameScore = 0;
	
	
	private boolean RUN = true;	
	private Display dis;
	private MIDlet midlet;

	
	// ��Ϸ����
	StartScene startScene;
	MenuScene menuScene;
	RunScene runScene;
	
	private GameEngine(MIDlet midlet2){
		this.midlet = midlet2;
		dis = Display.getDisplay(midlet2);
	}
	
	/**
	 * �����Ϸ����ʵ��������ģʽ��
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
		
		// ��ʾ��Ϸ��ʼ����
		if(!startScene.isShown()){
			dis.setCurrent(startScene);
		}
		startScene.show();
		
		// ������Ϸ�ؿ�ѡ���ϵͳ���ý���
		setState(GAME_STATE_MENU);
	}
	
	public void gameMenu(){
		if(null == menuScene || !menuScene.isAlive()){
			menuScene = new MenuScene();
		}
		
		// ��ʾ��Ϸ��ʼ����
		if(!menuScene.isShown()){
			dis.setCurrent(menuScene);
		}
		menuScene.show();
	}
	
	public void gameRunning(){
		if(null == runScene || !runScene.isAlive()){
			runScene = new RunScene();
		}
		
		// ��ʾ��Ϸ��ʼ����
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
