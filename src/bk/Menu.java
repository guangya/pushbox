package bk;

import javax.microedition.lcdui.Graphics;



public class Menu{
	
	private int width;
	private int height;	
	private GameEngine engine;
	
	public Menu(GameEngine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
	}
	
	/**
	 * 设置菜单界面的左上角相对于屏幕的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		
	}
	
	/**
	 * 按键消息处理
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		
	}
	
	/**
	 * 场景逻辑处理
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		
	}
}