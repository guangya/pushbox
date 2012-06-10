
public interface IEngine {
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
	
	/**
	 *  定义全局左右菜单标识，用于解决平台兼容行问题
	 *  
	 *  在引擎中，根据不同平台，处理左右菜单键键码，并将其转换成下面两个常量，在全局使用
	 */
	public static final int MENU_LEFT = 0;
	public static final int MENU_RIGHT = 1;

	/**
	 * 启动引擎
	 */
	public void start();
	
	/**
	 * 暂停引擎
	 */
	public void puase();
	
	/**
	 * 关闭引擎
	 */
	public void stop();
	
	/**
	 * 获得游戏运行状态标识
	 * 
	 * @return
	 */
	public int getGameState();
	
	/**
	 * 获得游戏当前所在的关卡
	 * 
	 * @return
	 */
	public int getCurrentLevel();
	
	/**
	 * 获得游戏的总关卡数
	 *  
	 * @return
	 */
	public int getTotalLevels();
	
	/**
	 *  设置游戏的运行状态，该方法会触发StateChange事件
	 *  
	 * @param state
	 */
	public void setGameState(int state);
	
	/**
	 * 设置游戏当前所处的关卡
	 */
	public void setCurrentLevel(int level);

	
	/**
	 * 游戏音效开关
	 * 
	 * 游戏音效为独立子线程，可以通过kill thread的方法禁用音效
	 * 
	 * @param isOn
	 */
	public void setSoundSwitch(boolean isOn);
}
