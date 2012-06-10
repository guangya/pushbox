import javax.microedition.lcdui.Graphics;


public abstract class Scene {
	
	// 游戏引擎
	protected IEngine engine;
	// 游戏画布尺寸
	protected int canvasWidth;
	protected int canvasHeight;
	// 当前场景在屏幕中的坐标（参考点为屏幕左上角）
	protected int posX;
	protected int posY;

	public Scene(IEngine engine, int width, int height){
		this.engine = engine;
		this.canvasWidth = width;
		this.canvasHeight = height;
		posX = 0;
		posY = 0;
	}
	
	/**
	 * 处理场景内部逻辑并完成场景绘制
	 * 
	 * @param gra
	 */
	public abstract void process(Graphics gra);
	
	/**
	 * 处理场景的按键
	 * 
	 * @param gameAction
	 */
	public abstract void keyEvent(int gameAction);
	
	/**
	 * 处理菜单事件
	 * 
	 * @param menu 左右菜单标识
	 */
	public abstract void menuEvent(int menu);
	
	/**
	 * 修改场景的坐标位置
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
}
